/*package com.plateno.booking.internal.job.order.abnormalSweepJob.service;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.MBillLogMapper;
import com.plateno.booking.internal.base.mapper.MBillMapper;
import com.plateno.booking.internal.base.pojo.MBill;
import com.plateno.booking.internal.base.pojo.MBillLog;
import com.plateno.booking.internal.base.pojo.MBillLogExample;
import com.plateno.booking.internal.base.pojo.MBillLogExample.Criteria;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.request.custom.BillLogParam;
import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.bean.response.gateway.pay.PayQueryResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundQueryResponse;
import com.plateno.booking.internal.bean.response.lvmama.order.LvMaMaOrderBean.SuccessOrder;
import com.plateno.booking.internal.bean.response.tc.order.OrderListResponse.OrderList;
import com.plateno.booking.internal.bean.response.ycf.YcfOrderStatusResponse;
import com.plateno.booking.internal.dao.mapper.BillMapper;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.api.ApiService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.service.order.BillService;

@Service
public class ExceptionFlowService {
	protected final static Logger logger = LoggerFactory.getLogger(ExceptionFlowService.class);
	
	@Autowired
	private BillMapper billMapper;
	@Autowired
	private BillService billService;
	@Autowired
	private MBillMapper mBillMapper;
	@Autowired
	private OrderLogService orderLogService;
	@Autowired
	private MBillLogMapper billLogMapper;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private ApiService apiService;
	
	public void handleException() throws Exception {
		
		//获取故障订单集合(200,300,500)
		//15分钟以前
		List<BillDetails> billDetails=billMapper.selectErrorBillByStatus(15, BookingConstants.ERROR_ORDER_STATUS);
		if(CollectionUtils.isNotEmpty(billDetails)){
			handleEach(billDetails);
		}
		
		//超过30分钟未支付 ==> 100 --> 101
		billMapper.updateInitStatus(101, 30);
		List<BillDetails> cannelBills=billMapper.selectErrorBillByStatus(30, BookingConstants.READY_ORDER_STATUS);
		if(CollectionUtils.isNotEmpty(billDetails)){
			for(BillDetails billDetail:cannelBills){
				orderLogService.saveGSOrderLog(billDetail.getTradeNo(), BookingConstants.PAY_STATUS_101, "已取消", "订单取消成功",billDetail.getChannel(),ViewStatusEnum.VIEW_STATUS_CANNEL.getCode(),"扫单job维护");
			}
		}
	}
	
	private void handleEach(List<BillDetails> listBill)throws Exception{
		for(Iterator<BillDetails> iter=listBill.iterator();iter.hasNext();){
			BillDetails billDetail = (BillDetails)iter.next();
			Integer status=billDetail.getStatus();
			switch(status){
			
			//处理账单状态：200,验证网关订单状态 ==> 202/300
			case 200:
					handleGateway200Paying(billDetail);
				break;
			
			//处理账单状态：300,验证OTA订单状态 ==>301/302/303
			case 300:
					handleOTA300Paying(billDetail);
				break;
			
			//处理账单状态：500,验证网关退款查询接口 ==>501/502
			case 500:
					handleGateWay500Refund(billDetail);
				break;
			}
		}
	}
	
	private void handleGateway200Paying(BillDetails billDetails)throws Exception{
		if(!validate(billDetails,BookingConstants.PAY_STATUS_200)) 
			return ;
		dealWithMbill(billDetails);
		handleOTA300Paying(billDetails);
	}
	
	*//**
	 * 物理确认订单的最终状态
	 * 
	 * @param mbill
	 * @param status
	 * @return
	 *//*
	private boolean validate(BillDetails billDetails,Integer status){
		MBill mbills= mBillMapper.selectByPrimaryKey(Long.valueOf(billDetails.getBillId()));
		if(mbills==null) 
			return false;
		if(!mbills.getStatus().equals(status)) 
			return false;
		return true;
	}
	
	private void dealWithMbill(BillDetails billDetails) throws Exception {
		//构造sql的过滤语句
		CallMethod<BillDetails> call = new CallMethod<BillDetails>() {
			@Override
			void call(Criteria criteria, BillDetails billDetails) throws Exception { 
				invoke(criteria,"andBillIdEqualTo",Long.valueOf(billDetails.getBillId()));
				invoke(criteria,"andTypeEqualTo",BookingConstants.PAYCODE_INTEGER);
			}
		};
		//获取符合条件的所有支付流水记录
		List<MBillLog> billLogs = selectBillLogByBillId(billDetails,call);
		
		if(CollectionUtils.isEmpty(billLogs)) 	
			return ;
		boolean success=false;
		for(Iterator<MBillLog> iterator=billLogs.iterator();iterator.hasNext();){
			MBillLog mBillLog  = iterator.next();
			//获取网关的订单状态
			PayQueryResponse response = paymentService.payOrderQuery(mBillLog.getTradeNo());
			if (response == null)
				return;
			BillLogParam billLogParam = new BillLogParam();
			billLogParam.setTradeNo(mBillLog.getTradeNo());
			if(response.getCode().equals(BookingConstants.GATEWAY_PAY_SUCCESS_CODE)){
				//更新支付流水状态(success == 2)
				billLogParam.setStatus(BookingConstants.BILL_LOG_SUCCESS);
				billService.updateBillLog(billLogParam);
				success=true;
			}else{
				//更新支付流水状态(fail == 3)
				billLogParam.setStatus(BookingConstants.BILL_LOG_FAIL);
				billService.updateBillLog(billLogParam);
			}
		}
		MBill record = new MBill();
		if(success){
			record.setStatus(BookingConstants.PAY_STATUS_300);
			orderLogService.saveGSOrderLog(billDetails.getTradeNo(), BookingConstants.PAY_STATUS_300, "OTA支付中", "OTA支付中",billDetails.getChannel(),ViewStatusEnum.VIEW_STATUS_PAYING.getCode(),"扫单job维护");
		}else{
			record.setStatus(BookingConstants.PAY_STATUS_202);
			orderLogService.saveGSOrderLog(billDetails.getTradeNo(), BookingConstants.PAY_STATUS_202, "网关支付失败", "订单取消成功",billDetails.getChannel(),ViewStatusEnum.VIEW_STATUS_CANNEL.getCode(),"扫单job维护");
		}
		//更新账单状态
		billService.updateBillByTradeNo(billDetails.getTradeNo(), record, null, null);
	}
	
	
	private void handleOTA300Paying(BillDetails billDetails)throws Exception{
		//断言账单是否处于300(OTA支付中状态)
		if(!validate(billDetails,BookingConstants.PAY_STATUS_300)) 
			return ;
		OrderParam orderParam = new OrderParam();
		ResultVo output = new ResultVo();
		orderParam.setChannel(billDetails.getChannel());
		orderParam.setTradeNo(billDetails.getTradeNo());
		apiService.QueryOrderListByOrderNo(orderParam, output);
		if (output.success() && output.getData() != null) {
			//更新订单状态
			chooseChannelQueryOrder(orderParam,output);
		}
	}
	
	//TODO 需要增加要出发的逻辑
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void chooseChannelQueryOrder(OrderParam param,ResultVo output){
		MBill record = new MBill();
		switch (param.getChannel()) {
			case Config.CHANNEL_LVMAMA:
				List<SuccessOrder> pay = (List<SuccessOrder>) output.getData();
				if (pay == null || pay.size() == 0){
					break;
				}else if (pay.get(0).getPaymentStatus().equals("PAYED")) {
					record.setStatus(BookingConstants.PAY_STATUS_301);
					orderLogService.saveGSOrderLog(param.getTradeNo(), BookingConstants.PAY_STATUS_301, "OTA支付成功", "OTA支付成功",param.getChannel(),ViewStatusEnum.VIEW_STATUS_PAY_UNUSE.getCode(),"扫单job维护");
				}else if (pay.get(0).getPaymentStatus().equals("UNPAY")){
					record.setStatus(BookingConstants.PAY_STATUS_302);
					orderLogService.saveGSOrderLog(param.getTradeNo(), BookingConstants.PAY_STATUS_302, "OTA支付失败", "OTA支付失败",param.getChannel(),ViewStatusEnum.VIEW_STATUS_PAYING.getCode(),"扫单job维护");
				}
				break;
			case Config.CHANNEL_TC:
				ResultVo vo = apiService.QueryOrderList(param, output);
				if (!vo.success())
					break;
				List<OrderList> list = (List<OrderList>) vo.getData();
				if (list == null || list.size() == 0) {
					break;
				}else if(list.get(0).getPayState().equals("P")){
					record.setStatus(BookingConstants.PAY_STATUS_301);
					orderLogService.saveGSOrderLog(param.getTradeNo(), BookingConstants.PAY_STATUS_301, "OTA支付成功", "OTA支付成功",param.getChannel(),ViewStatusEnum.VIEW_STATUS_PAY_UNUSE.getCode(),"扫单job维护");
				}else if(list.get(0).getPayState().equals("N")){
					record.setStatus(BookingConstants.PAY_STATUS_302);
					orderLogService.saveGSOrderLog(param.getTradeNo(), BookingConstants.PAY_STATUS_302, "OTA支付失败", "OTA支付失败",param.getChannel(),ViewStatusEnum.VIEW_STATUS_PAYING.getCode(),"扫单job维护");
				}
				break;
			case Config.CHANNEL_YCF:
				YcfOrderStatusResponse response = (YcfOrderStatusResponse) output.getData();
				if (response == null)
					break;
				if (response.getOrderStatus() == 20) {
					record.setStatus(BookingConstants.PAY_STATUS_301);
					orderLogService.saveGSOrderLog(param.getTradeNo(), BookingConstants.PAY_STATUS_301, "OTA支付成功", "OTA支付成功",param.getChannel(),ViewStatusEnum.VIEW_STATUS_PAY_UNUSE.getCode(),"扫单job维护");
				}
				if (response.getOrderStatus() == 10) {
					record.setStatus(BookingConstants.PAY_STATUS_300);
					orderLogService.saveGSOrderLog(param.getTradeNo(), BookingConstants.PAY_STATUS_300, "待确认：支付订单成功,要出发确认流程中", "待确认：支付订单成功,要出发确认流程中",param.getChannel(),ViewStatusEnum.VIEW_STATUS_PAYING.getCode(),"扫单job维护");
				}
				if (response.getOrderStatus() == 11) {
					record.setStatus(BookingConstants.PAY_STATUS_300);
					orderLogService.saveGSOrderLog(param.getTradeNo(), BookingConstants.PAY_STATUS_300, "待确认（申请取消）：合作方申请取消,要出发在审核状态", "待确认（申请取消）：合作方申请取消,要出发在审核状态",param.getChannel(),ViewStatusEnum.VIEW_STATUS_PAYING.getCode(),"扫单job维护");
				}
				if (response.getOrderStatus() == 40) {
					record.setStatus(BookingConstants.PAY_STATUS_302);
					orderLogService.saveGSOrderLog(param.getTradeNo(), BookingConstants.PAY_STATUS_302, "OTA支付失败", "OTA支付失败",param.getChannel(),ViewStatusEnum.VIEW_STATUS_PAYING.getCode(),"扫单job维护");
				}
				break;
			default:
				System.out.println("空");
				break;
			}
		if (record.getStatus() != null && record.getStatus() > 0) {
			billService.updateBillByTradeNo(param.getTradeNo(), record, null, null);
		}
	}
	
	private void handleGateWay500Refund(BillDetails billDetail)throws Exception{
		if(!validate(billDetail,BookingConstants.PAY_STATUS_500)) 
			return ;
		//构造sql的过滤语句
		CallMethod<BillDetails> call = new CallMethod<BillDetails>() {
			@Override
			void call(Criteria criteria, BillDetails billDetails) throws Exception { 
				invoke(criteria,"andBillIdEqualTo",Long.valueOf(billDetails.getBillId()));
				invoke(criteria,"andTypeEqualTo",BookingConstants.REFUNDCODE_INTEGER);
			}
		};
		//获取符合条件的所有支付流水记录
		List<MBillLog> billLogs = selectBillLogByBillId(billDetail,call);
		
		if(CollectionUtils.isEmpty(billLogs)) 	
			return ;
		  
		boolean success = false;
		for(MBillLog billLog:billLogs){
			
			//获取网关的订单状态
			RefundQueryResponse response = paymentService.refundOrderQuery(billLog.getTradeNo());
			if (response == null)
				return;
			BillLogParam billLogParam = new BillLogParam();
			billLogParam.setTradeNo(billLog.getTradeNo());
			if(response.getCode().equals(BookingConstants.GATEWAY_REFUND_SUCCESS_CODE)){
				//更新支付流水状态(success == 2)
				billLogParam.setStatus(BookingConstants.BILL_LOG_SUCCESS);
				billService.updateBillLog(billLogParam);
				success=true;
			}else{
				//更新支付流水状态(fail == 3)
				billLogParam.setStatus(BookingConstants.BILL_LOG_FAIL);
				billService.updateBillLog(billLogParam);
			}
		}
		
		MBill record = new MBill();
		if(success){
			record.setStatus(BookingConstants.PAY_STATUS_501);
			orderLogService.saveGSOrderLog(billDetail.getTradeNo(), BookingConstants.PAY_STATUS_501, "网关退款成功", "网关退款成功",billDetail.getChannel(),ViewStatusEnum.VIEW_STATUS_REFUND.getCode(),"扫单job维护");
		}else{
			record.setStatus(BookingConstants.PAY_STATUS_502);
			orderLogService.saveGSOrderLog(billDetail.getTradeNo(), BookingConstants.PAY_STATUS_502, "网关退款失败", "网关退款失败",billDetail.getChannel(),ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode(),"扫单job维护");
		}
		//更新账单状态
		billService.updateBillByTradeNo(billDetail.getTradeNo(), record, null, null);
	  }
	
	private List<MBillLog> selectBillLogByBillId(BillDetails billDetails,CallMethod<BillDetails> call) throws Exception{
		MBillLogExample example = new MBillLogExample();
		Criteria criteria = example.createCriteria();
		call.call(criteria,billDetails);
		return billLogMapper.selectByExample(example);
	}
	
	//通过反射获取方法
	private void invoke(Object criteria ,String methodName,Object obj) throws Exception{
		if (obj == null || "".equals(obj)) {
			return;
		}
		Method method = criteria.getClass().getDeclaredMethod(methodName,obj.getClass());
		method.invoke(criteria, obj);
		}
		
	abstract class CallMethod<T> {
		abstract void call(Criteria criteria, T t) throws Exception;
	}
}
*/