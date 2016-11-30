package com.plateno.testservice;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.vo.MOrderSearchVO;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.LogisticsEnum;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ModifyOrderParams;
import com.plateno.booking.internal.bean.request.custom.ReceiptParam;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.sms.SMSSendService;
import com.plateno.booking.internal.sms.model.SmsMessageReq;
import com.plateno.booking.internal.util.vo.PageInfo;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MOrderServiceTest {
	
	@Autowired
	private MOrderService service;
	
	@Autowired
	private MallGoodsService mallGoodsService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private SMSSendService sendService;
	
	@Test
	public void testModifyReceiptInfo() throws OrderException, Exception{
		
		ReceiptParam receiptParam = new ReceiptParam();
		receiptParam.setOrderNo("O1474959953609872686");
		receiptParam.setReceiptName("张思");
		receiptParam.setReceiptMobile("13888888888");
		receiptParam.setReceiptAddress("银河系");
		receiptParam.setOperateUserid("123456");
		receiptParam.setOperateUsername("管理员");
		receiptParam.setPlateForm(1);
		
		ResultVo<Object> modifyReceiptInfo = service.modifyReceiptInfo(receiptParam );
		System.out.println("结果：" + modifyReceiptInfo);
	}
	
	@Test
	public void testGetOrderDetail() throws OrderException, Exception{
		
		
		MOrderParam orderParam = new MOrderParam();
		orderParam.setOrderNo("O1479265160204863145");
		//orderParam.setMemberId(135964714);
		//orderParam.setChannelId(1);
		orderParam.setPlateForm(3);
		ResultVo<OrderDetail> orderDetail = service.getOrderDetail(orderParam );
		System.out.println("结果：" + orderDetail);
	}
	
	@Test
	public void testGetProductAndskuStock() throws OrderException, Exception{
		
		
		ProductSkuBean productAndskuStock = mallGoodsService.getProductAndskuStock("14");
		System.out.println(productAndskuStock);
		
	}
	
	@Test
	public void testQueryOrderByPage() throws OrderException, Exception{
		
		
		SelectOrderParam param = new SelectOrderParam();
		param.setPlateForm(3);
		param.setOrderNo("O1478778911490995236");
		param.setResource(2);
		//param.setBookingStartDate(DateUtil.dateToFormatStr("2016-11-03 08:00:01", "yyyy-MM-dd HH:mm:ss"));
		//param.setBookingEndDate(DateUtil.dateToFormatStr("2016-11-09 08:00:01", "yyyy-MM-dd HH:mm:ss"));
		param.setViewStatus(0);
		//param.setMemberId("181295316");
		/*param.setChannelId(1);
		List<Integer> statusList = new ArrayList<>();
		statusList.add(2);
		param.setStatusList(statusList);*/
		//param.setName("四");
		
		ResultVo<LstOrder<SelectOrderResponse>> queryOrderByPage = service.queryOrderByPage(param);
		System.out.println(queryOrderByPage);
		
	}
	
	@Test
	public void testUserRefund() throws OrderException, Exception{
		
		MOrderParam param = new MOrderParam();
		param.setOrderNo("O1478076012273383901");
		ResultVo<Object> userRefund = service.userRefund(param );
		System.out.println(userRefund);
		
	}
	
	@Test
	public void testConsentRefund() throws OrderException, Exception{
		
		MOrderParam param = new MOrderParam();
		param.setOrderNo("O1478768094167457080");
		ResultVo<Object> userRefund = service.refundOrder(param);
		System.out.println(userRefund);
		
	}
	
	@Test
	public void testSms() throws OrderException, Exception{
		
		System.out.println(3123123);
		
		//发送退款短信
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				
				Logger logger = LoggerFactory.getLogger("httpLogger");

				logger.info("323232323");
				
				SmsMessageReq messageReq = new SmsMessageReq();
				Map<String, String> params = new HashMap<String, String>();
				messageReq.setPhone("13533048661");
				params.put("orderCode", "12345678");
				params.put("name", "商品");
				params.put("money", "50000");
				params.put("jf","10");
				messageReq.setParams(params);
				messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_NINE));
				Boolean res=sendService.sendMessage(messageReq);
				System.out.println(res);
				
				
			}
		});
		
		
		Thread.sleep(5000);
		
	}
	
	
	@Test
	public void testCancelOrder() throws OrderException, Exception{
		
		MOrderParam orderParam = new MOrderParam();
		orderParam.setOrderNo("O1479952202860664675");
		orderParam.setMemberId(181295316);
		orderParam.setType(2);
		ResultVo<Object> userRefund = service.cancelOrderLock(orderParam);
		System.out.println(userRefund);
		
	}
	
	@Test
	public void testModifyOrder() throws OrderException, Exception{
		ModifyOrderParams modifyOrderParams = new ModifyOrderParams();
		modifyOrderParams.setNewStatus(BookingConstants.PAY_STATUS_5);
		modifyOrderParams.setOrderNo("O1478568730093888087");
		modifyOrderParams.setRemark("测试修改状态");
		modifyOrderParams.setOperateUserid("32323");
		modifyOrderParams.setOperateUsername("xiaoming");
		modifyOrderParams.setPlateForm(1);
		ResultVo<Object> modifyOrder = service.modifyOrder(modifyOrderParams );
		
		if(modifyOrder.success() && modifyOrderParams.getNewStatus() == BookingConstants.PAY_STATUS_6) {
			MOrderParam orderParam = new MOrderParam();
			orderParam.setOrderNo(modifyOrderParams.getOrderNo());
			orderParam.setMemberId((int)modifyOrder.getData());
			orderParam.setRefundRemark("");
			orderParam.setOperateUserid(modifyOrderParams.getOperateUserid());
			orderParam.setOperateUsername(modifyOrderParams.getOperateUsername());
			orderParam.setPlateForm(modifyOrderParams.getPlateForm());
			
			try {
				ResultVo<Object> refundOrder = service.refundOrder(orderParam);
				System.out.println(String.format("orderNo:%s, 执行退款，结果：%s", modifyOrderParams.getOrderNo(), refundOrder));
			} catch (Throwable e) {
				System.out.println("退款审核失败:" + modifyOrderParams.getOrderNo());
			}
		}
		
		System.out.println(modifyOrder);
	}
	
	@Test
	public void testAdminRefuseRefund() throws OrderException, Exception{
		
		MOrderParam orderParam = new MOrderParam();
		orderParam.setOrderNo("O1478568730093888087");
		orderParam.setMemberId(181295316);
		orderParam.setOperateUserid("3232323");
		orderParam.setOperateUsername("xiaoming");
		orderParam.setPlateForm(1);
		ResultVo<Object> adminRefuseRefund = service.adminRefuseRefund(orderParam );
		System.out.println(adminRefuseRefund);
		
	}
	
	
	@Test
	public void testLog() throws OrderException, Exception{
		
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {

				Logger logger = LoggerFactory.getLogger("httpLogger");

				logger.info("323232323");

			}
		});
		
		
	}
	
	
	@Test
	public void testDeliverOrder() throws OrderException, Exception{
		
		MOrderParam orderParam = new MOrderParam();
		orderParam.setOrderNo("O1479265160204863145");
		orderParam.setLogisticsType(1);
		orderParam.setLogisticsNo("2222222222");
		
		service.deliverOrder(orderParam);
		
		
	}
	
	
	@Test
	public void testQueryOrderList() throws OrderException, Exception{
		
		
		MOrderSearchVO svo = new MOrderSearchVO();
		
		svo.setMemberId(181295316);
		
		ResultVo<PageInfo<SelectOrderResponse>> queryOrderList = service.queryOrderList(svo );
		
		System.out.println(queryOrderList);
		
		
	}
	
	@Test
	public void testModifydeliverOrder() throws OrderException, Exception{
		
		
		MOrderParam orderParam = new MOrderParam();
		orderParam.setOrderNo("O1479265160204863145");
		orderParam.setLogisticsType(LogisticsEnum.ZT.getType());
		orderParam.setLogisticsNo(null);
		orderParam.setOperateUserid("12345");
		orderParam.setOperateUsername("23456");
		
		ResultVo<Object> modifydeliverOrder = service.modifydeliverOrder(orderParam );
		System.out.println(modifydeliverOrder);
	}
	
	@Test
	public void testDeleteOrderr() throws OrderException, Exception{
		
		
		MOrderParam orderParam = new MOrderParam();
		orderParam.setOrderNo("O1480328556337869185");
		ResultVo<Object> modifydeliverOrder = service.deleteOrder(orderParam);
	}
	
	@Test
	public void testGetPruSellAmountByPreDay() throws OrderException, Exception{
		
		ResultVo<Object> pruSellAmountByPreDay = service.getPruSellAmountByPreDay(1);
		System.out.println(pruSellAmountByPreDay);
	}

}