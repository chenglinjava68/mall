package com.plateno.booking.internal.service.order;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.base.mapper.MLogisticsMapper;
import com.plateno.booking.internal.base.mapper.OperatelogMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.mapper.SmsLogMapper;
import com.plateno.booking.internal.base.model.NotifyReturn;
import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.model.bill.OrderProductInfo;
import com.plateno.booking.internal.base.model.bill.ProdSellAmountData;
import com.plateno.booking.internal.base.pojo.MLogistics;
import com.plateno.booking.internal.base.pojo.MLogisticsExample;
import com.plateno.booking.internal.base.pojo.Operatelog;
import com.plateno.booking.internal.base.pojo.OperatelogExample;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderExample;
import com.plateno.booking.internal.base.pojo.OrderExample.Criteria;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.LogisticsEnum;
import com.plateno.booking.internal.bean.contants.OperateLogEnum;
import com.plateno.booking.internal.bean.contants.PayGateCode;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ModifyOrderParams;
import com.plateno.booking.internal.bean.request.custom.ReceiptParam;
import com.plateno.booking.internal.bean.request.gateway.RefundOrderParam;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.bean.response.custom.MOperateLogResponse;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ConsigneeInfo;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.DeliverDetail;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.OrderInfo;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.bean.response.gateway.pay.PayQueryResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundOrderResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundQueryResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.common.util.redis.RedisLock;
import com.plateno.booking.internal.common.util.redis.RedisLock.Holder;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.email.model.DeliverGoodContent;
import com.plateno.booking.internal.email.model.RefundSuccessContent;
import com.plateno.booking.internal.email.service.PhoneMsgService;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;
import com.plateno.booking.internal.service.log.OperateLogService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.service.order.state.OrderStatusContext;
import com.plateno.booking.internal.sms.SMSSendService;
import com.plateno.booking.internal.validator.order.MOrderValidate;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;



@Service
@ServiceErrorCode(BookingConstants.CODE_DB_BOOK_ERROR)
public class MOrderService{

	protected final static Logger logger = LoggerFactory.getLogger(MOrderService.class);
	
	@Autowired
	private MOrderValidate orderValidate;

	@Autowired
	private RedisUtils redisUtils;


	@Autowired
	private OrderStatusContext orderStatusContext;
	
	
	@Autowired
	private OrderMapper mallOrderMapper;
	
	@Autowired
	private MLogisticsMapper mLogisticsMapper;
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	
	@Autowired
	private MallGoodsService mallGoodsService;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private SMSSendService sendService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private OrderPayLogMapper orderPayLogMapper;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private OperatelogMapper operatelogMapper;
	
	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	private SmsLogMapper smsLogMapper;
	
	@Autowired
	private PhoneMsgService phoneMsgService;
	

	/**
	 * 查询订单信息,并支持分页处理
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ResultVo<LstOrder<SelectOrderResponse>> queryOrderByPage(SelectOrderParam param) throws Exception {
		LstOrder<SelectOrderResponse> lst = new LstOrder<SelectOrderResponse>();
		ResultVo<LstOrder<SelectOrderResponse>> vo ;
		List<SelectOrderResponse> list = new ArrayList<SelectOrderResponse>();
		if(param.getPageNo()==null||param.getPageNo()==0)param.setPageNo(1);
		lst.setPageIndex(param.getPageNo());
		
		vo = orderValidate.customQueryValidate(param,lst);
		if(vo!=null){
			return vo;
		}
		
		//商城前端查询，不显示删除的订单
		if(param.getPlateForm() == PlateFormEnum.USER.getPlateForm()) {
			param.setQueryDel(false);
		}
		
		//显示状态转变成数据库记录的状态
		if(param.getViewStatus() != null) {
			List<Integer> payStatus = PayStatusEnum.toPayStatus(param.getViewStatus());
			if(param.getStatusList() != null) {
				param.getStatusList().addAll(payStatus);
			} else {
				param.setStatusList(payStatus);
			}
		}
		
		List<Order> orderReturns = mallOrderMapper.getPageOrders(param);
		vo=new ResultVo<LstOrder<SelectOrderResponse>>();
		if (CollectionUtils.isEmpty(orderReturns)){
			return vo;
		}
		
		for(Order order:orderReturns){
			paramsDeal(order,list);
		}
		
		//获取符合条件的订单总数
		Integer count=mallOrderMapper.getCountOrder(param);
		Double num = (Double.valueOf(count)/Double.valueOf(param.getPageNumber()));
		lst.setPageSize(param.getPageNumber());
		lst.setTotal(count);
		lst.setOrderList(list);
		lst.setTotalPage(new Double(Math.ceil(num)).intValue());
		vo.setData(lst);
		return vo;
	}


	@SuppressWarnings("unchecked")
	private void paramsDeal(Order order,List<SelectOrderResponse> list) {
		SelectOrderResponse sc=new SelectOrderResponse();
		OrderProductExample example=new OrderProductExample();
		example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
		//Integer count = orderProductMapper.countByExample(example);
		List<OrderProduct> listProduct=orderProductMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(listProduct)){
			sc.setGoodsName(listProduct.get(0).getProductName());
			sc.setGoodsProperties(listProduct.get(0).getProductProperty());
			sc.setQuatity(listProduct.get(0).getSkuCount());
			sc.setDisImage(listProduct.get(0).getDisImages());
		}
		sc.setPoint(order.getPoint());
		sc.setAmount(order.getAmount());
		sc.setMemberId(Long.parseLong(order.getMemberId().toString()));
		sc.setOrderNo(order.getOrderNo());
		sc.setPayStatus(order.getPayStatus());
		sc.setBookingName(order.getName());
		sc.setResource(order.getResource());
		sc.setMobile(order.getMobile());
		sc.setPayType(order.getPayType());
		sc.setCreateTime(order.getCreateTime().getTime());
		
		sc.setPayMoney(order.getPayMoney());
		
		//退款金额（如果已经生成退款金额，就是实际退款的金额，否则是可以退款的金额）
		int refundAmount = 0;
		if(order.getRefundAmount() != null && order.getRefundAmount() > 0) {
			refundAmount = order.getRefundAmount();
		} else {
			refundAmount = order.getPayMoney();
		}
		
		sc.setRefundAmount(refundAmount);
		sc.setViewStatus(PayStatusEnum.toViewStatus(order.getPayStatus()));
		
		list.add(sc);
	}
	
	
	/**
	 * 获取订单信息
	 * 
	 * @param orderParam
	 * @return
	 * @throws OrderException
	 * @throws Exception
	 */
	public ResultVo<OrderDetail> getOrderDetail(MOrderParam orderParam) throws OrderException, Exception {
		ResultVo<OrderDetail> output = new ResultVo<OrderDetail>();
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		OrderDetail orderDetail = beansDeal(listOrder, orderParam.getPlateForm());
		output.setData(orderDetail);
		return output;
	}
	
	/**
	 * 获取支付成功信息
	 * 
	 * @param orderParam
	 * @return
	 * @throws OrderException
	 * @throws Exception
	 */
	public ResultVo<OrderDetail> getPaySuccessDetail(MOrderParam orderParam) throws OrderException, Exception {
		ResultVo<OrderDetail> output = new ResultVo<OrderDetail>();
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		OrderDetail orderDetail = beansDeal(listOrder, orderParam.getPlateForm());
		output.setData(orderDetail);
		return output;
	}
	
	public ResultVo<Object> saveOperateLog(MOperateLogParam orderParam) throws OrderException, Exception {
		ResultVo<Object> output = new ResultVo<Object>();
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderCode(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		Operatelog logs=new Operatelog();
		BeanUtils.copyProperties(orderParam, logs);
		operatelogMapper.insertSelective(logs);
		return output;
	}
	
	/**
	 * 允许修改成的状态
	 */
	private static final List<Integer> CAN_MODIFY_STATUS = Arrays.asList(3, 4, 5, 6, 7);
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor=Exception.class)
	public ResultVo<Object> modifyOrder(ModifyOrderParams modifyOrderParams) throws OrderException, Exception {
		
		logger.info("修改订单，参数:" + JsonUtils.toJsonString(modifyOrderParams));
		
		ResultVo<Object> output = new ResultVo<Object>();
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(modifyOrderParams.getOrderNo(), modifyOrderParams.getMemberId(), modifyOrderParams.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		
		Order order=listOrder.get(0);
		
		int oldStatus = order.getPayStatus();
		
		//所有订单状态都能修改，
		/*orderValidate.checkModifyOrder(order, output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}*/
		
		//但是只能修改成代发货、待收货、已完成、退款审核中、已退款
		if(!CAN_MODIFY_STATUS.contains(modifyOrderParams.getNewStatus())) {
			logger.info(String.format("orderNo:%s, new status:%s, 不支持修改成该状态", order.getOrderNo(), modifyOrderParams.getNewStatus()));
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("不支持修改成该状态");
			return output;
		}
		
		//相同状态不允许修改
		if(order.getPayStatus().equals(modifyOrderParams.getNewStatus())) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("相同状态不支持修改");
			return output;
		}
		
		if(modifyOrderParams.getNewStatus().equals(BookingConstants.PAY_STATUS_6)){//如果状态要变成退款中,需要修改一下字段
			
			//退款中和审核中状态，不支持直接触发支付网关退款
			if (BookingResultCodeContants.PAY_STATUS_6 == order.getPayStatus() || BookingResultCodeContants.PAY_STATUS_10 == order.getPayStatus()) {
				logger.info(String.format("orderNo:%s, now status:%s, 不支持直接退款", order.getOrderNo(), order.getPayStatus()));
				output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
				output.setResultMsg("审核中和退款中，不支持直接退款");
				return output;
			}
			
			order.setRefundReason(modifyOrderParams.getRemark());
			order.setRefundAmount(order.getPayMoney());
			order.setRefundTime(new Date());
			order.setRefundPoint(order.getPoint());
			order.setUpTime(new Date());
			order.setRefundReason(modifyOrderParams.getRemark());
			
			//插入支付流水
			OrderPayLog orderPayLog=new OrderPayLog();
			orderPayLog.setAmount(-order.getPayMoney());
			orderPayLog.setType(2);//支出
			orderPayLog.setPoint(order.getPoint());
			orderPayLog.setClientType(1);
			orderPayLog.setCreateTime(new Date());
			orderPayLog.setTrandNo(StringUtil.getCurrentAndRamobe("L"));
			orderPayLog.setReferenceid("");
			orderPayLog.setRemark(modifyOrderParams.getRemark());
			orderPayLog.setStatus(1);//状态 1初始化，2成功，3失败
			orderPayLog.setUpTime(new Date());
			orderPayLog.setOrderId(order.getId());
			orderPayLogMapper.insertSelective(orderPayLog);
		}
		
		order.setPayStatus(modifyOrderParams.getNewStatus());
		order.setRemark(modifyOrderParams.getRemark());
		order.setUpTime(new Date());
		
		if(mallOrderMapper.updateByPrimaryKeySelective(order)>0)
		orderLogService.saveGSOrderLog(modifyOrderParams.getOrderNo(), modifyOrderParams.getNewStatus(), "客服修改状态", "客服修改状成功:" + StringUtils.trimToEmpty(modifyOrderParams.getRemark()), 0,ViewStatusEnum.VIEW_STATUS_PAYING.getCode());

		
		MOperateLogParam paramlog=new MOperateLogParam();
		paramlog.setOperateType(OperateLogEnum.ORDER_MODIFY.getOperateType());
		paramlog.setOperateUserid(modifyOrderParams.getOperateUserid());
		paramlog.setOperateUsername(modifyOrderParams.getOperateUsername());
		paramlog.setOrderCode(modifyOrderParams.getOrderNo());
		paramlog.setPlateForm(modifyOrderParams.getPlateForm());
		
		String remark = OperateLogEnum.ORDER_MODIFY.getOperateName() + String.format(":%s, 修改前状态:%s, 修改后状态:%s", StringUtils.trimToEmpty(modifyOrderParams.getRemark()), PayStatusEnum.from(oldStatus).getDesc(), PayStatusEnum.from(modifyOrderParams.getNewStatus()).getDesc());
		remark = remark.length() > 99 ?  remark.substring(0, 99) : remark;
		
		paramlog.setRemark(remark);
		operateLogService.saveOperateLog(paramlog);
		
		output.setData(order.getMemberId());
		return output;
	}
	
	public ResultVo<List<MOperateLogResponse>> selectOperateLog(MOperateLogParam params) throws Exception {
		ResultVo<List<MOperateLogResponse>> output = new ResultVo<List<MOperateLogResponse>>();
		List<MOperateLogResponse> lisLogs=new ArrayList<MOperateLogResponse>();
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(params.getOrderCode(), params.getMemberId(), params.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		OperatelogExample operatelogExample=new OperatelogExample();
		operatelogExample.createCriteria().andOrderCodeEqualTo(params.getOrderCode());
		List<Operatelog> listlogs=operatelogMapper.selectByExample(operatelogExample);
		for(Operatelog log:listlogs){
			MOperateLogResponse response=new MOperateLogResponse();
			//BeanUtils.copyProperties(log, response);
			response.setOperateTime(log.getOperateTime().getTime());
			response.setOperateType(log.getOperateType());
			response.setOperateUserid(log.getOperateUserid());
			response.setOperateUserName(log.getOperateUsername());
			response.setOrderCode(log.getOrderCode());
			response.setPlateForm(log.getPlateForm());
			response.setRemark(log.getRemark());
			lisLogs.add(response);
		}
		output.setData(lisLogs);
		return output;
	}
	
	
	@SuppressWarnings("unchecked")
	public ResultVo<Object> modifyReceiptInfo(ReceiptParam receiptParam) throws OrderException, Exception {
		ResultVo<Object> output = new ResultVo<Object>();
		
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(receiptParam.getOrderNo(), receiptParam.getMemberId(), receiptParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		
		MLogisticsExample mLogisticsExample = new MLogisticsExample();
		mLogisticsExample.createCriteria().andOrderNoEqualTo(receiptParam.getOrderNo());
		List<MLogistics> listLogistic=mLogisticsMapper.selectByExample(mLogisticsExample);
		if(CollectionUtils.isEmpty(listLogistic)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("查询不到对应的收货信息");
			return output;
		}
		MLogistics logc=listLogistic.get(0);
		/*logc.setConsigneeName(receiptParam.getReceiptName());
		logc.setConsigneeAddress(receiptParam.getReceiptAddress());
		logc.setConsigneeMobile(receiptParam.getReceiptMobile());*/
		
		//原始的地址不修改，只是修改最新的地址
		logc.setConsigneeNewaddress(receiptParam.getReceiptAddress());
		logc.setConsigneeNewMobile(receiptParam.getReceiptMobile());
		logc.setConsigneeNewName(receiptParam.getReceiptName());
		mLogisticsMapper.updateByPrimaryKeySelective(logc);
		
		
		MOperateLogParam paramlog=new MOperateLogParam();
		paramlog.setOperateType(OperateLogEnum.MODIFY_DELIVER_OP.getOperateType());
		paramlog.setOperateUserid(receiptParam.getOperateUserid());
		paramlog.setOperateUsername(receiptParam.getOperateUsername());
		paramlog.setOrderCode(receiptParam.getOrderNo());
		paramlog.setPlateForm(receiptParam.getPlateForm());
		String remark = OperateLogEnum.MODIFY_DELIVER_OP.getOperateName() + String.format(":%s|%s|%s", receiptParam.getReceiptName(), receiptParam.getReceiptMobile(), receiptParam.getReceiptAddress());
		remark = remark.length() > 99 ?  remark.substring(0, 99) : remark;
		paramlog.setRemark(remark);
		operateLogService.saveOperateLog(paramlog);
		
		return output;
	}


	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor=OrderException.class)
	public String insertOrder(MAddBookingIncomeVo income) throws OrderException {
		try {
			
			logger.info("封装参数开始...");
			
			com.plateno.booking.internal.base.pojo.Order ordes=new com.plateno.booking.internal.base.pojo.Order();
			MAddBookingParam book = income.getAddBookingParam();
			String orderNo=StringUtil.getCurrentAndRamobe("O");
			
			//扣减库存
			boolean modifyStock = mallGoodsService.modifyStock(book.getGoodsId().toString(), -book.getQuantity());
			if(!modifyStock) {
				logger.error("扣减库存失败， {}", modifyStock);
				throw new OrderException("系统正忙，扣减库存失败，请重试！");
			}
			
			//扣减积分
			if(book.getSellStrategy().equals(2)) {
				logger.info("下单扣减积分， sellStrategy:{}, point:{}", book.getSellStrategy(), book.getPoint());
				boolean minusPoint = minusPoint(book.getMemberId(), book.getPoint());
				if(!minusPoint) {
					logger.error("扣积分失败， {}， {}", book.getMemberId(), minusPoint);
					throw new OrderException("系统正忙，扣减积分，请重试！");
				}
			}
			
			//商品接口获取参数
			//SkuBean skubean=mallGoodsService.getSkuProperty(book.getGoodsId().toString());
			//SkuStock stock=mallGoodsService.getSkuStock(book.getGoodsId().toString(), book.getSkuProperties());
			ProductSkuBean pskubean=mallGoodsService.getProductAndskuStock(book.getGoodsId().toString());
			if(pskubean == null) {
				logger.error("获取商品信息失败");
				throw new OrderException("获取商品信息失败");
			}
			/*if(book.getSellStrategy().equals(1)){
				if(book.getTotalAmount().equals((book.getQuantity()*pskubean.getRegularPrice()))){
				}
			}*/
			ordes.setResource(book.getResource());
			//商品非积分的总的价格，不包含运费
			ordes.setAmount(book.getQuantity()*pskubean.getRegularPrice() + (pskubean.getExpressFee() != null ? pskubean.getExpressFee() : 0));
			//ordes.setChanelid(book.getChanelId());
			//渠道从商品服务获取
			ordes.setChanelid(pskubean.getChannelId());
			ordes.setCreateTime(new Date());
			ordes.setItemId(0);
			ordes.setMemberId(book.getMemberId());
			ordes.setMobile(book.getMobile());
			ordes.setName(book.getName());
			ordes.setOrderNo(orderNo);
			ordes.setPayTime(new Date());
			ordes.setPayType(1);// 默认1微信支付、2支付宝支付
			ordes.setPayStatus(BookingResultCodeContants.PAY_STATUS_1);
			if(pskubean.getSellStrategy()==2) {
				//ordes.setPoint(pskubean.getFavorPoints());
				ordes.setPoint(book.getPoint());
			}
			//ordes.setPayMoney(pskubean.getSellStrategy()==1?pskubean.getRegularPrice():pskubean.getFavorPrice());
			ordes.setPayMoney(book.getTotalAmount());

			ordes.setRefundAmount(0);
			ordes.setSid(0);
			ordes.setUpTime(new Date());
			long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
			ordes.setWaitPayTime(new Date(currentTime));//加上30分钟
			
			
			OrderProduct op=new OrderProduct();
			op.setOrderNo(orderNo);
			op.setPrice(book.getSellStrategy()==1?pskubean.getRegularPrice():pskubean.getFavorPrice());
			op.setProductId(pskubean.getProductId());
			op.setProductName(pskubean.getTitle());
			op.setProductProperty(JsonUtils.toJsonString(pskubean.getSkuPropertyInfos()));
			op.setSkuCount(book.getQuantity());
			op.setSkuid(book.getGoodsId().intValue());
			op.setCreateTime(new Date());
			op.setUpTime(new Date());
			if(pskubean.getSellStrategy()==2) {
				op.setPoint(pskubean.getFavorPoints());
			} else {
				op.setPoint(0);
			}
			op.setSellStrategy(pskubean.getSellStrategy());
			op.setDisImages(pskubean.getImgPath());
			op.setPriceStrategy(pskubean.getPriceStrategy() == null ? 1 : pskubean.getPriceStrategy());
			op.setPriceStrategyDesc(StringUtils.trimToEmpty(pskubean.getPriceName()));
			
			MLogistics logistics=new MLogistics();
			logistics.setOrderNo(orderNo);
			logistics.setShippingType(1);//1包邮,2普通快递
			logistics.setConsigneeName(book.getConsigneeName());
			logistics.setConsigneeAddress(book.getConsigneeAddress());
			logistics.setConsigneeMobile(book.getConsigneeMobile());
			logistics.setExpressFee(pskubean.getExpressFee());
			logistics.setLogisticsType(1);//物流类型(1 圆通、2申通、3韵达、4百事通、5顺丰、6 EMS),默认圆通
			
			logger.info("插入数据");
			
			mallOrderMapper.insertSelective(ordes);
			mLogisticsMapper.insertSelective(logistics);
			orderProductMapper.insertSelective(op);
			
			return orderNo;
			
		} catch (Exception e) {
			//LogUtils.sysErrorLoggerError("订单创建失败", e);
			//e.printStackTrace();
			logger.error("订单创建失败", e);
			throw new OrderException("订单创建失败");
		}
	}
	
	
	/**
	 * 更新订单状态(删除)
	 * 
	 * @param orderParam
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ResultVo<Object> deleteOrder(final MOrderParam orderParam) throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		
		//校验订单是否可被处理
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		orderValidate.checkDeleteOrder(listOrder.get(0), output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		
		//构造sql的过滤语句
		CallMethod<Order> call = new CallMethod<Order>() {
			@Override
			 void call(Criteria criteria, Order order) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo",orderParam.getOrderNo());
			}
		};
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_9);
		updateOrderStatusByNo(order, call);
		
		orderLogService.saveGSOrderLog(orderParam.getOrderNo(), BookingResultCodeContants.PAY_STATUS_9, "删除订单", "删除订单成功", 0,ViewStatusEnum.VIEW_STATUS_CANNEL.getCode());
		return output;
	}
	
	
	
	/**
	 * 客服确定退款
	 * 
	 * @param orderParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor=Exception.class)
	public ResultVo<Object> refundOrder(MOrderParam orderParam) throws Exception{
		
		logger.info(String.format("确认退款，参数:%s", JsonUtils.toJsonString(orderParam)));
		
		ResultVo<Object> output = new ResultVo<Object>();
		final List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		final Order dbOrder=listOrder.get(0);
		orderValidate.checkRefund(dbOrder, output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			logger.info("output:" + output);
			return output;
		}
		
		//更新为退款中，防并发
		Order order = new Order();
		order.setUpTime(new Date());
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_10);//退款中
		List<Integer> oldStatus = Arrays.asList(BookingResultCodeContants.PAY_STATUS_6);
		Integer row = updateOrderStatusByNo(order, orderParam.getOrderNo(), oldStatus);
		if(row < 1) {
			logger.info("订单退款已经处理，orderNo:" + orderParam.getOrderNo());
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单已经处理，请勿重复请求！");
			return output;
		}
		
		final String orderNo = orderParam.getOrderNo();
		//构造sql的过滤语句
		CallMethod<Order> call = new CallMethod<Order>() {
			@Override
			 void call(Criteria criteria, Order order) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo", orderNo);
			}
		};
		
		//调用网关退款接口 1先检查是否存在支付成功的流水、2申请退款  、3自动job查询网关退款中的订单
		OrderPayLogExample logExample=new OrderPayLogExample();
		logExample.createCriteria().andOrderIdEqualTo(dbOrder.getId()).andTypeEqualTo(1).andStatusEqualTo(BookingConstants.BILL_LOG_SUCCESS); //下单直流的流水，且是支付成功的
		List<OrderPayLog> listPayLog = orderPayLogMapper.selectByExample(logExample);
		if(CollectionUtils.isEmpty(listPayLog)) {
			logger.info(String.format("orderNo:%s, 没有支付成功的流水，尝试退款", orderParam.getOrderNo()));
			throw new Exception("支付成功的流水记录不存在");
		}
		
		//查询退款的申请的支付流水
		logExample=new OrderPayLogExample();
		logExample.createCriteria().andOrderIdEqualTo(dbOrder.getId()).andTypeEqualTo(2).andStatusEqualTo(1);
		List<OrderPayLog> refundLogList = orderPayLogMapper.selectByExample(logExample);
		if(CollectionUtils.isEmpty(refundLogList)) {
			logger.info(String.format("orderNo:%s, 没有处理中的退款流水，尝试退款", orderParam.getOrderNo()));
			throw new Exception("申请退款且状态是初始化的支付流水不存在");
		}
		
		if(refundLogList.size() != 1) {
			logger.info(String.format("orderNo:%s, 存在不止一条的退款流水:%s", orderParam.getOrderNo(), refundLogList.size()));
			throw new Exception("存在不止一条的退款流水");
		}
		
		OrderPayLog orderPayLog = refundLogList.get(0);
		
		//封装退款参数
		RefundOrderParam refundOrderParam=new  RefundOrderParam();
		refundOrderParam.setRefundAmount(-orderPayLog.getAmount());
		refundOrderParam.setRefundOrderNo(orderPayLog.getTrandNo());  //退款申请的订单号
		refundOrderParam.setRemark(orderPayLog.getRemark());
		refundOrderParam.setOrderNo(listPayLog.get(0).getTrandNo()); //原交易订单号
		
		//调用支付网关退款
		RefundOrderResponse response = null;
		try {
			response = paymentService.refundOrder(refundOrderParam);
		} catch (Exception e) {
			logger.error("支付网关申请退款异常:" + orderPayLog.getTrandNo(), e);
		}
		
		logger.info(String.format("orderNo:%s, 网关申请退款, 返回:%s", orderPayLog.getTrandNo(), JsonUtils.toJsonString(response)));
		
		MOperateLogParam paramlog=new MOperateLogParam();
		paramlog.setOperateType(OperateLogEnum.AGREE_REFUND_OP.getOperateType());
		paramlog.setOperateUserid(orderParam.getOperateUserid());
		paramlog.setOperateUsername(orderParam.getOperateUsername());
		paramlog.setOrderCode(orderParam.getOrderNo());
		paramlog.setPlateForm(orderParam.getPlateForm());
		paramlog.setRemark(OperateLogEnum.AGREE_REFUND_OP.getOperateName());
		operateLogService.saveOperateLog(paramlog);		

		output = updatePaystatusRefunding(orderParam, output, dbOrder, call, logExample);
		
		return output;
		
//		if(null != response && response.getCode().equals("0000") && response.getReferenceId()!=null){ //退款成功
//			//退款流水更新状态
//			refundSucess(orderParam, dbOrder, call, logExample);
//
//		}else{ //退款状态不确定，定义为退款中
//			return updatePaystatusRefunding(orderParam, output, dbOrder,
//					call, logExample);
//		}
//		
//		//积分返还
//		returnPoint(dbOrder);
//		
//		//更新库存
//		final ProductSkuBean bean = updateStock(orderParam, output);
//		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
//			return output;
//		}
//		
//		//发送退款短信
//		taskExecutor.execute(new Runnable() {
//			@Override
//			public void run() {
//				
//				SmsMessageReq messageReq = new SmsMessageReq();
//				Map<String, String> params = new HashMap<String, String>();
//				if(dbOrder.getPoint()>0){
//					messageReq.setPhone(dbOrder.getMobile());
//					params.put("orderCode", dbOrder.getOrderNo());
//					params.put("name", bean.getTitle());
//					params.put("money", dbOrder.getPayMoney()+"");
//					params.put("jf",dbOrder.getPoint()+"");
//					messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_NINE));
//				}else{
//					params.remove("jf");
//					messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_EIGHT));
//				}
//				Boolean res=sendService.sendMessage(messageReq);
//				
//				//记录短信日志
//				SmsLog smslog=new SmsLog();
//				smslog.setCreateTime(new Date());
//				smslog.setIsSuccess(res==true?1:0);
//				smslog.setContent(bean.getTitle());
//				smslog.setObjectNo(dbOrder.getOrderNo());
//				smslog.setPhone(dbOrder.getMobile());
//				smslog.setUpdateTime(new Date());
//				smsLogMapper.insertSelective(smslog);
//				
//			}
//		});
//		
//		MOperateLogParam paramlog=new MOperateLogParam();
//		paramlog.setOperateType(OperateLogEnum.AGREE_REFUND_OP.getOperateType());
//		paramlog.setOperateUserid(orderParam.getOperateUserid());
//		paramlog.setOperateUserName(orderParam.getOperateUsername());
//		paramlog.setOrderCode(orderParam.getOrderNo());
//		paramlog.setPlateForm(orderParam.getPlateForm());
//		paramlog.setRemark(OperateLogEnum.AGREE_REFUND_OP.getOperateName());
//		operateLogService.saveOperateLog(paramlog);		
//		
//		return output;
	}


	private void refundSucess(final MOrderParam orderParam,
			final Order dbOrder, CallMethod<Order> call,
			OrderPayLogExample logExample) throws Exception {
		OrderPayLog payLog=new OrderPayLog();
		payLog.setStatus(2);
		payLog.setPoint(dbOrder.getPoint());
		orderPayLogMapper.updateByExampleSelective(payLog, logExample);
		
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_7);//退款审核中==>退款成功
		order.setRefundSuccesstime(new Date());
		updateOrderStatusByNo(order,call);
		
		orderLogService.saveGSOrderLog(orderParam.getOrderNo(), BookingResultCodeContants.PAY_STATUS_7, "退款操作", "退款成功", 0,ViewStatusEnum.VIEW_STATUS_REFUND.getCode());
	}


	private ResultVo<Object> updatePaystatusRefunding(
			final MOrderParam orderParam, ResultVo<Object> output,
			final Order dbOrder, CallMethod<Order> call,
			OrderPayLogExample logExample) throws Exception {
		/*OrderPayLog payLog=new OrderPayLog();
		payLog.setStatus(3);
		payLog.setPoint(dbOrder.getPoint());
		orderPayLogMapper.updateByExampleSelective(payLog, logExample);*/
		//一律看成请求成功，具体结果看job同步结果
		//output.setResultCode(getClass(), MsgCode.REFUND_HANDLING.getMsgCode());
		output.setResultMsg(MsgCode.REFUND_HANDLING.getMessage());
		
		//更新为退款中
		Order order = new Order();
		order.setUpTime(new Date());
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_10);//退款中
		updateOrderStatusByNo(order, call);
		orderLogService.saveGSOrderLog(orderParam.getOrderNo(), BookingResultCodeContants.PAY_STATUS_10, "退款操作", "OTA退款中", 0,ViewStatusEnum.VIEW_STATUS_GATE_REFUNDING.getCode());
		return output;
	}


	private ProductSkuBean updateStock(final MOrderParam orderParam,
			ResultVo<Object> output) throws OrderException {
		OrderProductExample orderProductExample=new OrderProductExample();
		orderProductExample.createCriteria().andOrderNoEqualTo(orderParam.getOrderNo());
		List<OrderProduct> productOrderList=orderProductMapper.selectByExample(orderProductExample);
		if(CollectionUtils.isEmpty(productOrderList)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单获取不到对应的产品信息");
		}
		final ProductSkuBean bean=mallGoodsService.getProductAndskuStock(productOrderList.get(0).getSkuid().toString());
		
		if(!mallGoodsService.modifyStock(productOrderList.get(0).getSkuid().toString(), productOrderList.get(0).getSkuCount())){
			LogUtils.sysLoggerInfo("更新库存失败");
		}
		return bean;
	}


	private void returnPoint(final Order dbOrder) {
		if(dbOrder.getPoint()>0){
			ValueBean vb=new ValueBean();
			vb.setPointvalue(dbOrder.getRefundPoint());
			vb.setMebId(dbOrder.getMemberId());
			vb.setTrandNo(dbOrder.getOrderNo());
			pointService.mallAddPoint(vb);
		}
	}
	
	/**
	 * 扣减积分
	 * 
	 * @param income
	 * @param output
	 * @throws Exception 
	 */
	public boolean minusPoint(int memberId, int point) throws Exception{
		ValueBean v=new ValueBean();
		v.setMebId(memberId);
		v.setPointvalue(-point);
		int r = pointService.mallMinusPoint(v);
		if(r > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * 更新订单状态(取消)
	 * 
	 * @param orderParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor=OrderException.class)
	public ResultVo<Object> cancelOrderLock(final MOrderParam orderParam) throws Exception{
		
		String lockName = "MALL_CANEL_ORDER_" + orderParam.getOrderNo();
		
		Holder holder = new RedisLock.Holder() {
			@Override
			public Object exec() throws Exception {
				//取消订单
				return cancelOrder(orderParam);
			}
		};
		
		return (ResultVo<Object>) RedisLock.lockExec(lockName, holder );
	}


	/**
	 * 取消订单
	 * @param orderParam
	 * @return
	 * @throws Exception
	 */
	private ResultVo<Object> cancelOrder(final MOrderParam orderParam)
			throws Exception {
		ResultVo<Object> output = new ResultVo<Object>();
		
		//校验订单是否可被处理
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		orderValidate.checkCancelOrder(listOrder.get(0), output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		
		String desc = "手动取消订单";
		//判断取消类型
		if(orderParam.getType() != null && orderParam.getType() == 1) {
			
			desc = "超时取消订单";
			
			logger.info(String.format("orderNo:%s, 超时取消订单", orderParam.getOrderNo()));
			long now = new Date().getTime();
			long createTime = listOrder.get(0).getCreateTime().getTime();
			//29分钟，避免时间存在误差
			if(now - createTime <= 29*60*1000) {
				logger.info(String.format("orderNo:%s, 超期取消时间错误:%s", orderParam.getOrderNo(), listOrder.get(0).getCreateTime()));
				output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
				output.setResultMsg("超时取消时间未到达30分钟");
				return output;
			}
		}
		
		//构造sql的过滤语句
		CallMethod<Order> call = new CallMethod<Order>() {
			@Override
			 void call(Criteria criteria, Order order) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo",orderParam.getOrderNo());
			}
		};
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_2);
		order.setUpTime(new Date());
		updateOrderStatusByNo(order, call);
		
		orderLogService.saveGSOrderLog(orderParam.getOrderNo(), BookingResultCodeContants.PAY_STATUS_2, "取消操作", "取消成功", 0,ViewStatusEnum.VIEW_STATUS_CANNEL.getCode(), desc);

		//退还积分
		if(listOrder.get(0).getPoint()>0){
			
			logger.info("取消订单，退还积分，orderNo:{}, point:{}", listOrder.get(0).getOrderNo(), listOrder.get(0).getPoint());
			
			ValueBean vb=new ValueBean();
			vb.setPointvalue(listOrder.get(0).getPoint());
			vb.setMebId(listOrder.get(0).getMemberId());
			vb.setTrandNo(listOrder.get(0).getOrderNo());
			pointService.mallAddPoint(vb);
		}
		
		//退还库存
		try {
			logger.info("取消订单，退还库存，orderNo:{}", listOrder.get(0).getOrderNo());
			updateStock(orderParam, output);
		} catch (Exception e) {
			logger.error("退还库存生异常:" + orderParam.getOrderNo(), e);
		}
		
		//如果是后台操作，取消记录操作日志
		if(orderParam.getPlateForm() != null && (orderParam.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || orderParam.getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
			MOperateLogParam paramlog=new MOperateLogParam();
			paramlog.setOperateType(OperateLogEnum.CANCEL_ORDER.getOperateType());
			paramlog.setOperateUserid(orderParam.getOperateUserid());
			paramlog.setOperateUsername(orderParam.getOperateUsername());
			paramlog.setOrderCode(orderParam.getOrderNo());
			paramlog.setPlateForm(orderParam.getPlateForm());
			paramlog.setRemark(OperateLogEnum.CANCEL_ORDER.getOperateName());
			operateLogService.saveOperateLog(paramlog);		
		}

		return output;
	}
	
	/**
	 * 更新订单状态(发货通知)
	 * 
	 * @param orderParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ResultVo<Object> deliverOrder(final MOrderParam orderParam) throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		//校验订单是否可被处理
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		orderValidate.checkDeliverOrder(listOrder.get(0), output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		
		//构造sql的过滤语句
		CallMethod<Order> call = new CallMethod<Order>() {
			@Override
			 void call(Criteria criteria, Order order) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo",orderParam.getOrderNo());
			}
		};
		CallLogisticMethod<MLogistics> callLogistic = new CallLogisticMethod<MLogistics>() {
			@Override
			 void call(com.plateno.booking.internal.base.pojo.MLogisticsExample.Criteria criteria, MLogistics logistics) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo",orderParam.getOrderNo());
			}
		};
		
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_4);//待发货==>待收货
		order.setDeliverTime(new Date());
		updateOrderStatusByNo(order, call);
		orderLogService.saveGSOrderLog(orderParam.getOrderNo(), BookingResultCodeContants.PAY_STATUS_4, "发货操作", "发货成功", 0,ViewStatusEnum.VIEW_STATUS_DELIVERS.getCode());

		
		MLogistics logistics=new MLogistics();
		logistics.setLogisticsNo(StringUtils.trimToEmpty(orderParam.getLogisticsNo()));
		logistics.setLogisticsType(orderParam.getLogisticsType());
		updatLogisticsNoByNo(logistics,callLogistic);
		
		
		//短信通知发货
		final Order od=listOrder.get(0);
		OrderProductExample orderProductExample=new OrderProductExample();
		orderProductExample.createCriteria().andOrderNoEqualTo(orderParam.getOrderNo());
		List<OrderProduct> productOrderList=orderProductMapper.selectByExample(orderProductExample);
		if(CollectionUtils.isEmpty(productOrderList)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单获取不到对应的产品信息");
			return output;
		}
		final ProductSkuBean bean=mallGoodsService.getProductAndskuStock(productOrderList.get(0).getSkuid().toString());

		//final String remark="尊敬的铂涛用户，您的订单【"+od.getOrderNo()+"】，商品【大白创意汽车摆件水晶车内香水可爱饰品小玩偶车载摇头公仔娃娃】【已发货】，快递公司为【申通公司】，单号为：【882454079083338721】，请留意电话查收快递。";
		/*taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				
				SmsMessageReq messageReq = new SmsMessageReq();
				messageReq.setPhone(od.getMobile());
				Map<String, String> params = new HashMap<String, String>();
				params.put("code", od.getOrderNo());
				params.put("name", bean.getTitle());
				params.put("express", LogisticsEnum.getNameBytype(orderParam.getLogisticsType()));
				params.put("expressCode", orderParam.getLogisticsNo());//物流号
				
				messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_SEVEN));
				messageReq.setParams(params);
				boolean res=sendService.sendMessage(messageReq);
				
				//记录短信日志
				SmsLog smslog=new SmsLog();
				smslog.setCreateTime(new Date());
				smslog.setIsSuccess(res==true?1:0);
				smslog.setContent(bean.getTitle());
				smslog.setObjectNo(od.getOrderNo());
				smslog.setPhone(od.getMobile());
				smslog.setUpdateTime(new Date());
				smsLogMapper.insertSelective(smslog);
			}
		});*/
		
		//发送到短信
		DeliverGoodContent content = new DeliverGoodContent();
		content.setObjectNo(od.getOrderNo());
		content.setOrderCode(od.getOrderNo());
		content.setName(bean.getTitle());
		content.setExpress(LogisticsEnum.getNameBytype(orderParam.getLogisticsType()));
		content.setExpressCode(orderParam.getLogisticsNo());
		phoneMsgService.sendPhoneMessageAsync(od.getMobile(), Config.SMS_SERVICE_TEMPLATE_SEVEN, content);
		
		MOperateLogParam paramlog=new MOperateLogParam();
		paramlog.setOperateType(OperateLogEnum.DELIVER_ORDER.getOperateType());
		paramlog.setOperateUserid(orderParam.getOperateUserid());
		paramlog.setOperateUsername(orderParam.getOperateUsername());
		paramlog.setOrderCode(orderParam.getOrderNo());
		paramlog.setPlateForm(orderParam.getPlateForm());
		paramlog.setRemark(OperateLogEnum.DELIVER_ORDER.getOperateName());
		operateLogService.saveOperateLog(paramlog);		
		
		
		return output;
	}
	
	
	public ResultVo<Object> modifydeliverOrder(final MOrderParam orderParam) throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		//校验订单是否可被处理
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		
		//构造sql的过滤语句
		CallLogisticMethod<MLogistics> callLogistic = new CallLogisticMethod<MLogistics>() {
			@Override
			 void call(com.plateno.booking.internal.base.pojo.MLogisticsExample.Criteria criteria, MLogistics logistics) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo",orderParam.getOrderNo());
			}
		};
		
		
		MLogistics logistics=new MLogistics();
		logistics.setLogisticsNo(orderParam.getLogisticsNo());
		logistics.setLogisticsType(orderParam.getLogisticsType());
		updatLogisticsNoByNo(logistics,callLogistic);
		
		
		MOperateLogParam paramlog=new MOperateLogParam();
		paramlog.setOperateType(OperateLogEnum.MODIFY_DELIVER_OP.getOperateType());
		paramlog.setOperateUserid(orderParam.getOperateUserid());
		paramlog.setOperateUsername(orderParam.getOperateUsername());
		paramlog.setOrderCode(orderParam.getOrderNo());
		paramlog.setPlateForm(orderParam.getPlateForm());
		String remark = OperateLogEnum.MODIFY_DELIVER_OP.getOperateName() + String.format(":%s|%s", orderParam.getLogisticsNo(), LogisticsEnum.from(orderParam.getLogisticsType()));
		remark = remark.length() > 99 ?  remark.substring(0, 99) : remark;
		paramlog.setRemark(remark);
		operateLogService.saveOperateLog(paramlog);		
		
		
		return output;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public ResultVo<Object> enterReceipt(final MOrderParam orderParam) throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		//校验订单是否可被处理
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		orderValidate.checkEnterReceiptStatus(listOrder.get(0), output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		
		//构造sql的过滤语句
		CallMethod<Order> call = new CallMethod<Order>() {
			@Override
			 void call(Criteria criteria, Order order) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo",orderParam.getOrderNo());
			}
		};
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_5);//确定收货操作==>已完成
		order.setUpTime(new Date());
		updateOrderStatusByNo(order, call);
		orderLogService.saveGSOrderLog(orderParam.getOrderNo(), BookingResultCodeContants.PAY_STATUS_5, "收货操作", "收货成功", 0,ViewStatusEnum.VIEW_STATUS_PAY_USE.getCode());
		//如果是后台操作，取消记录操作日志
		if(orderParam.getPlateForm() != null && (orderParam.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || orderParam.getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
			
			MOperateLogParam paramlog=new MOperateLogParam();
			paramlog.setOperateType(OperateLogEnum.ENTER_RECEIPT.getOperateType());
			paramlog.setOperateUserid(orderParam.getOperateUserid());
			paramlog.setOperateUsername(orderParam.getOperateUsername());
			paramlog.setOrderCode(orderParam.getOrderNo());
			paramlog.setPlateForm(orderParam.getPlateForm());
			paramlog.setRemark(OperateLogEnum.ENTER_RECEIPT.getOperateName());
			operateLogService.saveOperateLog(paramlog);
		}

		return output;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public ResultVo<Object> adminRefuseRefund(final MOrderParam orderParam) throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		//校验订单是否可被处理
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		orderValidate.checkAdminRefund(listOrder.get(0), output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		
		//构造sql的过滤语句
		CallMethod<Order> call = new CallMethod<Order>() {
			@Override
			 void call(Criteria criteria, Order order) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo",orderParam.getOrderNo());
			}
		};
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_8);//退款审核中==>退款审核不通过
		order.setRefundSuccesstime(new Date());
		order.setRefundFailReason(StringUtils.trimToEmpty(orderParam.getRefundRemark())); //退款失败原因
		updateOrderStatusByNo(order, call);
		
		//更新退款流水为失败
		OrderPayLog record = new OrderPayLog();
		record.setStatus(3);
		OrderPayLogExample example = new OrderPayLogExample();
		example.createCriteria().andOrderIdEqualTo(listOrder.get(0).getId()).andTypeEqualTo(2).andStatusEqualTo(1);
		orderPayLogMapper.updateByExampleSelective(record , example);
		
		
		orderLogService.saveGSOrderLog(orderParam.getOrderNo(), BookingResultCodeContants.PAY_STATUS_8, "拒绝退款操作", StringUtils.trimToEmpty(orderParam.getRefundRemark()), 0,ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode());

		MOperateLogParam paramlog=new MOperateLogParam();
		paramlog.setOperateType(OperateLogEnum.REFUSE_REFUNDING.getOperateType());
		paramlog.setOperateUserid(orderParam.getOperateUserid());
		paramlog.setOperateUsername(orderParam.getOperateUsername());
		paramlog.setOrderCode(orderParam.getOrderNo());
		paramlog.setPlateForm(orderParam.getPlateForm());
		paramlog.setRemark(OperateLogEnum.REFUSE_REFUNDING.getOperateName());
		operateLogService.saveOperateLog(paramlog);		
		
		return output;
	}
	
	
	@Transactional
	public ResultVo<Object> userRefund(final MOrderParam orderParam) throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		//校验订单是否可被处理
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		orderValidate.checkUserRefund(listOrder.get(0), output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		
		//构造sql的过滤语句
		CallMethod<Order> call = new CallMethod<Order>() {
			@Override
			 void call(Criteria criteria, Order order) throws Exception { 
				invoke(criteria,"andOrderNoEqualTo",orderParam.getOrderNo());
			}
		};
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_6);//用户申请退款==>退款审核中
		order.setRefundAmount(listOrder.get(0).getPayMoney());
		order.setPoint(listOrder.get(0).getPoint());
		//order.setRefundTime(listOrder.get(0).getRefundTime());
		order.setRefundTime(new Date());
		order.setRefundReason(orderParam.getRefundRemark());
		order.setRefundPoint(listOrder.get(0).getPoint());
		order.setUpTime(new Date());
		updateOrderStatusByNo(order, call);
		
		
		OrderPayLog orderPayLog=new OrderPayLog();
		orderPayLog.setAmount(-listOrder.get(0).getPayMoney());
		orderPayLog.setType(2);//支出
		orderPayLog.setPoint(listOrder.get(0).getPoint());
		orderPayLog.setClientType(1);
		orderPayLog.setCreateTime(new Date());
		orderPayLog.setTrandNo(StringUtil.getCurrentAndRamobe("L"));
		orderPayLog.setReferenceid("");
		orderPayLog.setRemark(orderParam.getRefundRemark());
		orderPayLog.setStatus(1);//状态 1初始化，2成功，3失败
		orderPayLog.setUpTime(new Date());
		orderPayLog.setOrderId(listOrder.get(0).getId());
		orderPayLogMapper.insertSelective(orderPayLog);
		orderLogService.saveGSOrderLog(orderParam.getOrderNo(), BookingResultCodeContants.PAY_STATUS_6, "用户申请退款操作", "", 0,ViewStatusEnum.VIEW_STATUS_REFUNDING.getCode());
		
		//后台操作记录操作日志
		if(orderParam.getPlateForm() != null && (orderParam.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || orderParam.getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
			MOperateLogParam paramlog=new MOperateLogParam();
			paramlog.setOperateType(OperateLogEnum.REFUNDING_OP.getOperateType());
			paramlog.setOperateUserid(orderParam.getOperateUserid());
			paramlog.setOperateUsername(orderParam.getOperateUsername());
			paramlog.setOrderCode(orderParam.getOrderNo());
			paramlog.setPlateForm(orderParam.getPlateForm());
			paramlog.setRemark(OperateLogEnum.REFUNDING_OP.getOperateName() + ":" + StringUtils.trimToEmpty(orderParam.getRefundRemark()));
			operateLogService.saveOperateLog(paramlog);
		}

		return output;
	}
	
	@Transactional
	public ResultVo<Object> refunddealWithOrder(final NotifyReturn notifyReturn) throws Exception {
		ResultVo<Object> output = new ResultVo<Object>();
		if (notifyReturn.getCode().equals("0000")) {
				List<Order> listOrder=mallOrderMapper.getOrderByNo(notifyReturn.getOrderNo());
				if(CollectionUtils.isEmpty(listOrder)) {
					output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
					output.setResultMsg("订单查询失败,获取不到订单");
					return output;
				}
				if (listOrder.get(0).getPayStatus().equals(7)) {
					return output;
				}
				//构造sql的过滤语句
				CallMethod<Order> call = new CallMethod<Order>() {
					@Override
					 void call(Criteria criteria, Order order) throws Exception { 
						invoke(criteria,"andOrderNoEqualTo",notifyReturn.getOrderNo());
					}
				};
				Order order = new Order();
				order.setPayStatus(7);//客服同意申请退款==>已退款
				updateOrderStatusByNo(order, call);
				
				//回退积分
				
		} else {//如果调用网关失败，订单状态还是审核中
			output.setResultCode(getClass(), MsgCode.GATEWAY_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.GATEWAY_ERROR.getMessage());
			return output;
		}
		return output;
	}
	
	
	@SuppressWarnings("unchecked")
	public void updateOrderStatusByNo(Order order,CallMethod<Order> call) throws Exception{
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();
		call.call(criteria, order);
		try {
			mallOrderMapper.updateByExampleSelective(order, example);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.sysErrorLoggerError("更新数据库失败", e);
		}
	}
	

	@SuppressWarnings("unchecked")
	private void updatLogisticsNoByNo(MLogistics logistics,CallLogisticMethod<MLogistics> call) throws Exception{
		MLogisticsExample example = new MLogisticsExample();
		com.plateno.booking.internal.base.pojo.MLogisticsExample.Criteria criteria = example.createCriteria();
		call.call(criteria, logistics);
		try {
			mLogisticsMapper.updateByExampleSelective(logistics, example);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.sysErrorLoggerError("更新数据库失败", e);
		}
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
	
	abstract class CallLogisticMethod<T> {
		abstract void call(com.plateno.booking.internal.base.pojo.MLogisticsExample.Criteria criteria, T t) throws Exception;
	}
	
	
	@SuppressWarnings("unchecked")
	private OrderDetail beansDeal(List<Order> listOrder, Integer plateForm) {
		Order order=listOrder.get(0);
		OrderDetail  orderDetail=new OrderDetail();
		
		OrderInfo orderInfo=new OrderInfo();
		orderInfo.setCreateDate(order.getCreateTime().getTime());
		orderInfo.setOrderNo(order.getOrderNo());
		orderInfo.setPayAmount(order.getPayMoney());
		orderInfo.setPoint(order.getPoint());
		orderInfo.setOrderAmount(order.getAmount());
		orderInfo.setPayType(order.getPayType());
		
		orderInfo.setPayStatus(order.getPayStatus());
		//待付款， 如果是已经有支付记录，显示成已经失败
		if(order.getPayStatus().equals(PayStatusEnum.PAY_STATUS_1.getPayStatus())) {
			if(plateForm != null && plateForm == 3) {
				OrderPayLogExample example = new OrderPayLogExample();
				example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(1).andStatusEqualTo(3);
				List<OrderPayLog> listpayLog = orderPayLogMapper.selectByExample(example);
				if(listpayLog.size() > 0) {
					orderInfo.setPayStatus(PayStatusEnum.PAY_STATUS_12.getPayStatus());
				}
			}
		}
		
		orderInfo.setPayTime(order.getPayTime().getTime());
		orderInfo.setName(order.getName());
		orderInfo.setMobile(order.getMobile());
		
		MLogisticsExample mLogisticsExample = new MLogisticsExample();
		mLogisticsExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
		List<MLogistics> listLogistic=mLogisticsMapper.selectByExample(mLogisticsExample);
		if(CollectionUtils.isNotEmpty(listLogistic)){
			MLogistics logc=listLogistic.get(0);
			orderInfo.setFee(logc.getExpressFee());
			ConsigneeInfo consigneeInfo=new ConsigneeInfo();
			DeliverDetail deliverDetail=new DeliverDetail();
			consigneeInfo.setConsigneeAddress(logc.getConsigneeAddress());
			consigneeInfo.setConsigneeName(logc.getConsigneeName());
			consigneeInfo.setMobile(logc.getConsigneeMobile());
			
			//针对商城前端，如果地址已经修改了，返回修改后的地址
			if(plateForm != null && plateForm == 3 && StringUtils.isNotBlank(logc.getConsigneeNewMobile())) {
				consigneeInfo.setConsigneeAddress(logc.getConsigneeNewaddress());
				consigneeInfo.setConsigneeName(logc.getConsigneeNewName());
				consigneeInfo.setMobile(logc.getConsigneeNewMobile());
			}
			consigneeInfo.setNewAddress(logc.getConsigneeNewaddress());
			consigneeInfo.setNewName(logc.getConsigneeNewName());
			consigneeInfo.setNewMobile(logc.getConsigneeNewMobile());
			deliverDetail.setDeliverNo(logc.getLogisticsNo());
			deliverDetail.setLogisticsType(logc.getLogisticsType());
			if(order.getDeliverTime()!=null)deliverDetail.setDeliverDate(order.getDeliverTime().getTime());
			orderDetail.setConsigneeInfo(consigneeInfo);
			orderDetail.setDeliverDetail(deliverDetail);
		}
		
		OrderProductExample orderProductExample=new OrderProductExample();
		orderProductExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
		List<OrderProduct> list=orderProductMapper.selectByExample(orderProductExample);
		List<ProductInfo> productInfoList=new ArrayList<ProductInfo>();
		if(CollectionUtils.isNotEmpty(list)){
			for(OrderProduct orderProduct:list){
				ProductInfo productInfo=new ProductInfo();
				productInfo.setProductId(orderProduct.getProductId());
				productInfo.setCount(orderProduct.getSkuCount());
				productInfo.setPrice(orderProduct.getPrice());
				productInfo.setProductName(orderProduct.getProductName());
				productInfo.setProductPropertis(orderProduct.getProductProperty());
				productInfo.setPoint(orderProduct.getPoint());
				productInfo.setSellStrategy(orderProduct.getSellStrategy());
				productInfo.setDisImages(orderProduct.getDisImages());
				productInfoList.add(productInfo);
			}
		}
		orderDetail.setProductInfo(productInfoList);

		
		if(order.getPayStatus().equals(1)){
			orderInfo.setOrderDetailRemark("待付款，请你在30分钟内支付，否则订单取消");
		}else if(order.getPayStatus().equals(2)){
			orderInfo.setOrderDetailRemark("已取消，由于未在规定时间内进行支付，订单已自动取消");
		}else if(order.getPayStatus().equals(3)){
			orderInfo.setOrderDetailRemark("待发货，快递公司将会在三个工作日内进行发货");
		}else if(order.getPayStatus().equals(4)){
			orderInfo.setOrderDetailRemark("待收货，请留意电话进行快递查收");
		}else if(order.getPayStatus().equals(5)){
			orderInfo.setOrderDetailRemark("已完成，已确认收货，欢迎下次购买");
		}else if(order.getPayStatus().equals(6)){
			orderInfo.setOrderDetailRemark("退款中，您的退款正在申请中");
		}else if(order.getPayStatus().equals(7)){
			orderInfo.setOrderDetailRemark("已退款，退款金额￥"+order.getPayMoney()+"元,+积分,"+order.getPoint()+"已原路退回您支付时使用的账户");
		}else if(order.getPayStatus().equals(8) || order.getPayStatus().equals(BookingConstants.PAY_STATUS_13)){
			orderInfo.setOrderDetailRemark("审核不通过，如有问题，请联系铂涛会客服");
		}else if(order.getPayStatus().equals(BookingConstants.PAY_STATUS_13)){ //退款失败
			orderInfo.setOrderDetailRemark("审核失败，如有问题，请联系铂涛会客服");
		}
		orderInfo.setFailReason(order.getRefundFailReason());
		orderInfo.setRefundTime(order.getRefundTime() == null ? null : order.getRefundTime().getTime());
		orderInfo.setRefundSuccessTime(order.getRefundSuccesstime() == null ? null : order.getRefundSuccesstime().getTime());
		orderInfo.setRefundAmount(order.getRefundAmount());
		orderInfo.setRefundReason(order.getRefundReason());
		orderInfo.setViewStatus(PayStatusEnum.toViewStatus(order.getPayStatus()));
		
		orderDetail.setOrderInfo(orderInfo);
		return orderDetail;
	}
	
	public Integer  updateOrderStatusByNo(Order order,String orderNo) throws Exception{
		OrderExample example = new OrderExample();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		try {
			return mallOrderMapper.updateByExampleSelective(order, example);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.sysErrorLoggerError("更新数据库失败", e);
		}
		return 0;
	}
	
	/**
	 * 更新状态，并判断旧的状态
	 * @param order
	 * @param orderNo
	 * @param oldStatus
	 * @return
	 * @throws Exception
	 */
	public Integer  updateOrderStatusByNo(Order order,String orderNo, List<Integer> oldStatus) throws Exception{
		OrderExample example = new OrderExample();
		example.createCriteria().andOrderNoEqualTo(orderNo).andPayStatusIn(oldStatus);
		try {
			return mallOrderMapper.updateByExampleSelective(order, example);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.sysErrorLoggerError("更新数据库失败", e);
		}
		return 0;
	}
	
	
	@SuppressWarnings("unchecked")
	public ResultVo<Object> updateOrderStatus(ModifyOrderParams modifyOrderParams) throws OrderException, Exception {
		ResultVo<Object> output = new ResultVo<Object>();
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(modifyOrderParams.getOrderNo(), modifyOrderParams.getMemberId(), modifyOrderParams.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		
		Order order=listOrder.get(0);
		//orderValidate.checkModifyOrder(order, output);
		
		order.setPayStatus(modifyOrderParams.getNewStatus());
		order.setUpTime(new Date());

		
		if(mallOrderMapper.updateByPrimaryKeySelective(order)>0)
		orderLogService.saveGSOrderLog(modifyOrderParams.getOrderNo(), modifyOrderParams.getNewStatus(), "更新订单状态", "更新订单状态", 0,0);

		return output;
	}
	
	
	/**
	 * 查询订单状态
	 * 
	 * @param orderParam
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ResultVo<Object> getOrderInfo(final MOrderParam orderParam) throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		//校验订单是否可被处理
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		OrderExample example=new OrderExample();
		example.createCriteria().andOrderNoEqualTo(listOrder.get(0).getOrderNo());
		List<OrderProduct> listPro=orderProductMapper.selectByExample(example);
		
		OrderProductInfo orderProductInfo=new OrderProductInfo();
		orderProductInfo.setOrder(listOrder.get(0));
		orderProductInfo.setOrderProductList(listPro);
		output.setData(orderProductInfo);		

		return output;
	}
	
	
	public ResultVo<Object> getPruSellAmountByPreDay(final Integer  days) throws Exception{
		
		ResultVo<Object> output = new ResultVo<Object>();
		List<ProdSellAmountData> listPro=mallOrderMapper.getPruSellAmountByPreDay(days);
		if(CollectionUtils.isEmpty(listPro)){
			return output;
		}
		output.setData(listPro);
		return output;
	}
	
	
	/**
	 * 确认收货
	 * @param order
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void handleReceiveGoods(String orderNo) throws Exception {
		
		logger.info(String.format("job 已发货-->已完成, orderNo:%s", orderNo));
		
		//更新订单状态
		Order o = new Order();
		o.setPayStatus(BookingResultCodeContants.PAY_STATUS_5);
		o.setUpTime(new Date());
		List<Integer> list = new ArrayList<>(1);
		list.add(BookingResultCodeContants.PAY_STATUS_4);
		int row = this.updateOrderStatusByNo(o, orderNo, list);
		//订单已经处理
		if(row < 1) {
			logger.info("job 已发货-->已完成,订单已经处理, orderNo：" + orderNo);
			return ;
		}
		
		orderLogService.saveGSOrderLog(orderNo, BookingConstants.PAY_STATUS_5, "已完成", "已完成", 0, ViewStatusEnum.VIEW_STATUS_COMPLETE.getCode(), "扫单job维护");
	}
	
	
	/**
	 * 退款处理
	 * @param order
	 * @throws Exception
	 */
	@Transactional(rollbackFor=OrderException.class)
	public void handleGateWayefund(Order order)throws Exception{
		
		String orderNo = order.getOrderNo();
		
		//获取记录并上锁，防止并发
		order = mallOrderMapper.getByOrderNoForUpdate(orderNo);
		
		if(order == null || order.getPayStatus() != PayStatusEnum.PAY_STATUS_10.getPayStatus()) {
			logger.info("退款确认，订单已经处理， orderNo:{}, payStatus:{}", orderNo, order != null ? order.getPayStatus() + "" : "");
			return ;
		}
		
		/*if(!validate(order,BookingConstants.PAY_STATUS_10)) 
			return ;*/
		
		logger.info(String.format("退款中订单处理开始, orderNo:%s", order.getOrderNo()));
		
		OrderPayLogExample example=new OrderPayLogExample();
		example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(2).andStatusEqualTo(1);
		List<OrderPayLog> listpayLog=orderPayLogMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(listpayLog))		return;
		
		boolean success = false;
		boolean fail = false;
		for(OrderPayLog orderPayLog:listpayLog){
			
			//获取网关的订单状态
			RefundQueryResponse response = paymentService.refundOrderQuery(orderPayLog.getTrandNo());
			
			logger.info(String.format("orderNo:%s, 查询退款状态，返回：%s", order.getOrderNo(), JsonUtils.toJsonString(response)));
			
			if (response == null || StringUtils.isBlank(response.getCode())) {
				logger.error("查询支付网关订单失败, trandNo:" + orderPayLog.getTrandNo());
				return;
			}
			
			if(response.getCode().equals(PayGateCode.HADNLING) || response.getCode().equals(PayGateCode.PAY_HADNLING)) {
				logger.error(String.format("退款支付网关订单支付中, trandNo:%s, code:%s", orderPayLog.getTrandNo(), response.getCode()));
				return;
			}
			
			example=new OrderPayLogExample();
			example.createCriteria().andIdEqualTo(orderPayLog.getId());
			
			if(response.getCode().equals(BookingConstants.GATEWAY_REFUND_SUCCESS_CODE)){ //退款成功
				
				logger.info(String.format("orderNo:%s, 退款成功", order.getOrderNo()));
				
				//更新支付流水状态(success == 2)
				OrderPayLog record=new OrderPayLog();
				record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
				//record.setRemark("退款成功");
				record.setUpTime(new Date());
				record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
				orderPayLogMapper.updateByExampleSelective(record, example);
				
				success=true;
			}else if((response.getCode().equals(PayGateCode.REFUND_FAIL) || response.getCode().equals(PayGateCode.REQUEST_EXCEPTION))){ //退款失败
				
				logger.info(String.format("orderNo:%s, 退款失败", order.getOrderNo()));
				
				//更新支付流水状态(fail == 3)
				OrderPayLog record=new OrderPayLog();
				//record.setRemark(String.format("退款失败:%s", response.getMessage()));
				record.setStatus(BookingConstants.BILL_LOG_FAIL);
				record.setUpTime(new Date());
				record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
				orderPayLogMapper.updateByExampleSelective(record, example);
				
				fail = true;
			}
		}
		
		Order record = new Order();
		record.setRefundSuccesstime(new Date());
		if(success){
			
			logger.info(String.format("orderNo:%s, 退款成功", order.getOrderNo()));
			
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_7);
			orderLogService.saveGSOrderLog(orderNo, BookingResultCodeContants.PAY_STATUS_7, "网关退款成功", "网关退款成功",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_REFUND.getCode(),"扫单job维护");
			//更新账单状态
			this.updateOrderStatusByNo(record, orderNo);
			
			//退款归还下单积分
			logger.info("orderNo:{}， 退还积分，point:{}", orderNo, order.getRefundPoint());
			returnPoint(order);
			
			OrderProduct productByOrderNo = getProductByOrderNo(orderNo);
			if(productByOrderNo == null) {
				logger.error(String.format("orderNo:%s, 退款退库存失败, 找不到购买的商品信息", orderNo));
			} else {
				//更新库存
				logger.info(String.format("orderNo:%s， 退还库存，skuid:%s, count:%s", orderNo, productByOrderNo.getSkuid(), productByOrderNo.getSkuCount()));
				boolean modifyStock = mallGoodsService.modifyStock(productByOrderNo.getSkuid().toString(), productByOrderNo.getSkuCount());
				if(!modifyStock){
					logger.error(String.format("orderNo:%s, 调用商品服务失败", orderNo));
					LogUtils.sysLoggerInfo(String.format("orderNo:%s, 调用商品服务失败", orderNo));
				}
				
				final Order dbOrder = order;
				final OrderProduct product = productByOrderNo;
				//发送退款短信
				/*taskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						
						logger.info("发送退款成功短信:{}", dbOrder.getOrderNo());
						
						SmsMessageReq messageReq = new SmsMessageReq();
						Map<String, String> params = new HashMap<String, String>();
						if(dbOrder.getPoint() > 0){
							messageReq.setPhone(dbOrder.getMobile());
							params.put("orderCode", dbOrder.getOrderNo());
							params.put("name", product.getProductName());
							params.put("money", dbOrder.getPayMoney()+"");
							params.put("jf",dbOrder.getPoint()+"");
							messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_NINE));
						}else{
							params.remove("jf");
							messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_EIGHT));
						}
						messageReq.setParams(params);
						Boolean res=sendService.sendMessage(messageReq);
						
						logger.info("发送退款成功短信:{}, res:{}", dbOrder.getOrderNo(), res);
						
						//记录短信日志
						SmsLog smslog=new SmsLog();
						smslog.setCreateTime(new Date());
						smslog.setIsSuccess(res==true?1:0);
						smslog.setContent(product.getProductName());
						smslog.setObjectNo(dbOrder.getOrderNo());
						smslog.setPhone(dbOrder.getMobile());
						smslog.setUpdateTime(new Date());
						smsLogMapper.insertSelective(smslog);
					}
				});*/
				
				String templateId;
				RefundSuccessContent content = new RefundSuccessContent();
				content.setObjectNo(dbOrder.getOrderNo());
				content.setOrderCode(dbOrder.getOrderNo());
				content.setMoney(new BigDecimal(dbOrder.getPayMoney()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_DOWN).toString());
				content.setName(product.getProductName());
				if(dbOrder.getRefundPoint() > 0){
					content.setJf(dbOrder.getRefundPoint() + "");
					templateId = Config.SMS_SERVICE_TEMPLATE_NINE;
				}else{
					templateId = Config.SMS_SERVICE_TEMPLATE_EIGHT;
				}
				phoneMsgService.sendPhoneMessageAsync(dbOrder.getMobile(), templateId, content);
			}
		}else if(fail){
			
			logger.info(String.format("orderNo:%s, 退款失败", order.getOrderNo()));
			
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_13);
			record.setRefundFailReason("网关退款失败");
			orderLogService.saveGSOrderLog(orderNo, BookingConstants.PAY_STATUS_13, "网关退款失败", "网关退款失败",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode(),"扫单job维护");
			//更新账单状态
			this.updateOrderStatusByNo(record, orderNo);
		}
	}
	
	/**
	 * 获取订单的商品信息
	 * @param orderNo
	 * @return
	 */
	public OrderProduct getProductByOrderNo(String orderNo) {
		OrderProductExample orderProductExample=new OrderProductExample();
		orderProductExample.createCriteria().andOrderNoEqualTo(orderNo);
		@SuppressWarnings("unchecked")
		List<OrderProduct> productOrderList = orderProductMapper.selectByExample(orderProductExample);
		if(CollectionUtils.isEmpty(productOrderList)) {
			return null;
		}
		
		return productOrderList.get(0);
	}
	
	/**
	 * 支付中处理
	 * @param order
	 * @throws Exception
	 */
	@Transactional(rollbackFor=OrderException.class)
	public void handlePaying(Order order)throws Exception{
		
		String orderNo = order.getOrderNo();
		
		//获取记录并上锁，防止并发
		order = mallOrderMapper.getByOrderNoForUpdate(orderNo);
		
		if(order == null || order.getPayStatus() != PayStatusEnum.PAY_STATUS_11.getPayStatus()) {
			logger.info("支付确认，订单已经处理， orderNo:{}, payStatus:{}", orderNo, order != null ? order.getPayStatus() + "" : "");
			return ;
		}
				
		/*if(!validate(order,BookingConstants.PAY_STATUS_11)) 
			return ;*/
		
		logger.info(String.format("支付中订单处理开始, orderNo:%s", order.getOrderNo()));
		
		OrderPayLogExample example=new OrderPayLogExample();
		example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(1).andStatusEqualTo(1);
		List<OrderPayLog> listpayLog=orderPayLogMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(listpayLog))	{
			logger.error("订单状态异常, 订单状态支付中，但是找不到支付流水, orderNo:" + order.getOrderNo());
			return;
		}
		
		boolean success = false;
		for(OrderPayLog orderPayLog:listpayLog){
			
			//获取网关的订单状态
			PayQueryResponse response = paymentService.payOrderQuery(orderPayLog.getTrandNo());
			
			logger.info("orderNo:{}, 查询支付网关支付状态:{}", order.getOrderNo(), JsonUtils.toJsonString(response));
			
			if (response == null || StringUtils.isBlank(response.getCode())) {
				logger.error("查询支付网关订单失败, trandNo:" + orderPayLog.getTrandNo());
				return;
			}
			
			if(response.getCode().equals(PayGateCode.HADNLING) || response.getCode().equals(PayGateCode.PAY_HADNLING) || response.getCode().equals(PayGateCode.UNKNOWN_STATUS)) {
				logger.error(String.format("支付网关订单不是最终状态, trandNo:%s, code:%s", orderPayLog.getTrandNo(), response.getCode()));
				return;
			}
			
			example = new OrderPayLogExample();
			example.createCriteria().andIdEqualTo(orderPayLog.getId());
				
			if(response.getCode().equals(BookingConstants.GATEWAY_PAY_SUCCESS_CODE)){
				
				//判断金额是否相等，防止支付时篡改
				if(response.getOrderAmount() == null || !response.getOrderAmount().equals(orderPayLog.getAmount())) {
					logger.error("orderNo:{}, 订单金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}", orderPayLog.getTrandNo(), orderPayLog.getAmount(), response.getOrderAmount());
					return ;
				}
				
				logger.info(String.format("orderNo:%s, 支付成功", order.getOrderNo()));
				//更新支付流水状态(success == 2)
				OrderPayLog record=new OrderPayLog();
				record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
				record.setRemark("支付成功");
				record.setUpTime(new Date());
				record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
				orderPayLogMapper.updateByExampleSelective(record, example);
				
				success=true;
			}else{
				logger.info(String.format("orderNo:%s, 支付失败", order.getOrderNo()));
				//更新支付流水状态(fail == 3)
				OrderPayLog record=new OrderPayLog();
				record.setStatus(BookingConstants.BILL_LOG_FAIL);
				record.setUpTime(new Date());
				record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
				record.setRemark(String.format("支付失败:%s", response.getMessage()));
				orderPayLogMapper.updateByExampleSelective(record, example);
			}
		}
		
		Order record = new Order();
		if(success){
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
			orderLogService.saveGSOrderLog(order.getOrderNo(), BookingResultCodeContants.PAY_STATUS_3, "网关支付成功", "网关支付成功",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_WATIDELIVER.getCode(),"扫单job维护");
		}else{
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_1);
			orderLogService.saveGSOrderLog(order.getOrderNo(), BookingConstants.PAY_STATUS_1, "网关支付失败", "网关支付失败",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_PAYFAIL.getCode(),"扫单job维护");
		}
		//更新账单状态
		this.updateOrderStatusByNo(record, order.getOrderNo());
	  }
}