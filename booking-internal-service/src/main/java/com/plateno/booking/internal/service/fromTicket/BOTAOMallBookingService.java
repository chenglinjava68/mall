package com.plateno.booking.internal.service.fromTicket;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.annotation.service.ParamValid;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingServiceEnumConstants;
import com.plateno.booking.internal.bean.request.convert.ConvertRefundDetailParam;
import com.plateno.booking.internal.bean.request.custom.CannelBookingParam;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.gateway.PayNotifyReturnParam;
import com.plateno.booking.internal.bean.response.custom.CannelBookResponse;
import com.plateno.booking.internal.bean.response.custom.MAddBookResponse;
import com.plateno.booking.internal.dao.helper.BookingRedisHelper;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.service.chain.ServiceChain;
import com.plateno.booking.internal.service.abs.AbsBookingService;
import com.plateno.booking.internal.service.fromTicket.vo.AddBookingIncomeVo;
import com.plateno.booking.internal.service.fromTicket.vo.CancelBookingIncomeVo;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;
import com.plateno.booking.internal.service.fromTicket.vo.PayTicketIncomeVo;
import com.plateno.booking.internal.service.fromTicket.vo.RefundAccountIncomeVo;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.sms.SMSSendService;
import com.plateno.cache.lock.LockCallback;





@Service
@ServiceErrorCode(BookingConstants.CODE_OTA_BOOK_ERROR)
public class BOTAOMallBookingService extends AbsBookingService {

	private final static Logger log = Logger.getLogger(BOTAOMallBookingService.class);

	@Autowired
	private ServiceChain serviceChain;
	@Autowired
	private OrderLogService orderLogService;

	@Autowired
	public TaskExecutor taskExecutor;
	
	@Autowired
	private SMSSendService sendService;
	
	/**
	 * 新增订单
	 * 
	 * @param addBookInfo
	 * @return
	 * @author 
	 */
	@ParamValid
	public ResultVo<MAddBookResponse> addBooking(MAddBookingParam  addBookingParam) {
		ResultVo<MAddBookResponse> resultVo = new ResultVo<MAddBookResponse>();
		resultVo = addBookingLock(addBookingParam);
		return resultVo;
	}
	
	
	/**
	 * 取消订单
	 * 
	 * @param addBookInfo
	 * @return
	 * @author 
	 * @throws Exception 
	 */
	@ParamValid
	public ResultVo<CannelBookResponse> cancelBooking(CannelBookingParam cancelBookingParam) throws Exception {
		/*ResultVo<CannelBookResponse> resultVo = new ResultVo<CannelBookResponse>();
		filterCannelOrder(cancelBookingParam, resultVo);
		if (!resultVo.success()) {
			return resultVo;
		}
		resultVo = cancelBookingLock(cancelBookingParam, resultVo);*/
		return null;
	}
	
	/**
	 * 退款
	 * 
	 * @param convertRefundDetailParam
	 * @return
	 * @author 
	 * @throws Exception 
	 */
	@ParamValid
	public ResultVo<Boolean> refund(ConvertRefundDetailParam convertRefundDetailParam) throws Exception {
	/*	ResultVo<Boolean> resultVo = new ResultVo<Boolean>();
		RefundAccountIncomeVo refundAccountIncomeVo = new RefundAccountIncomeVo();
		filterRefundOrder(convertRefundDetailParam, refundAccountIncomeVo ,resultVo);
		
		if (!convertRefundDetailParam.getCode().equals(BookingConstants.TC_SUCCESS_CODE)) {
			
			//更新订单状态和退款失败原因
			updateBill(refundAccountIncomeVo,convertRefundDetailParam);
			//更新订单生命周期日志
			orderLogService.saveGSOrderLog(refundAccountIncomeVo.getTradeNo(), BookingConstants.PAY_STATUS_402, "OTA退款失败", convertRefundDetailParam.getRefundReason(),refundAccountIncomeVo.getChannel(), ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode());
			resultVo.setData(false);
			resultVo.setResultCode(getClass(), BookingConstants.CODE_601001002);
			//发短信
			taskExecutor.execute(new SMSRunnable(refundAccountIncomeVo));
			return resultVo;
		}
		
		if (!resultVo.success()) {
			return resultVo;
		}
		resultVo = addRefundLock(refundAccountIncomeVo, resultVo);
		return resultVo;*/
		return null;
	}
	
	
	
	
	
	/**
	 * 第三方支付
	 * 
	 * @param payNotifyReturnParam
	 * @return
	 * @throws Exception
	 */
	@ParamValid
	public ResultVo<Boolean> pay(PayNotifyReturnParam payNotifyReturnParam) throws Exception {
		ResultVo<Boolean> resultVo = new ResultVo<Boolean>();
		if (!payNotifyReturnParam.getCode().equals(BookingConstants.GATEWAY_PAY_SUCCESS_CODE)) {
			resultVo.setData(false);
			resultVo.setResultCode(getClass(), BookingConstants.CODE_201010);
			return resultVo;
		}
		PayTicketIncomeVo payTicketIncomeVo = new PayTicketIncomeVo();
		filterPayOrder(payNotifyReturnParam, payTicketIncomeVo ,resultVo);
		if (!resultVo.success()) {
			return resultVo;
		}
		resultVo = addPayLock(payTicketIncomeVo, resultVo);
		return resultVo;
	}

	/**
	 * 下单加会员ID锁
	 * 
	 * @param addBookInfo
	 * @return
	 */
	public ResultVo<MAddBookResponse> addBookingLock(MAddBookingParam addBookingParam) {
		//设置final 防止死锁出现
		final MAddBookingParam addBook = addBookingParam;
		ResultVo<MAddBookResponse> resultVo = BookingRedisHelper.distributedLock(new LockCallback<ResultVo<MAddBookResponse>>() {
			@Override
			public ResultVo<MAddBookResponse> exec() {
				ResultVo<MAddBookResponse> resultVo = new ResultVo<MAddBookResponse>();
				String serviceEnum = getBookingService(addBook.getChanelId(), BookingServiceEnumConstants.ADD);
				MAddBookingIncomeVo addBookingIncomeVo = initAddBookingIncomeVo(addBook);
				serviceChain.doServer(addBookingIncomeVo, resultVo, serviceEnum);
				return resultVo;
			}
		}, BookingConstants.PLATENO_ADD_BOOKING_LOCK + addBookingParam.getMemberId(), 100, BookingConstants.CODE_100001, "请勿频繁下单");

		return resultVo;
	}
	
//	/**
//	 * 取消订单订单号加锁
//	 * 
//	 * @param cancelBookingParam
//	 * @return
//	 */
//	public ResultVo<CannelBookResponse> cancelBookingLock(CannelBookingParam cancelBookingParam, ResultVo<CannelBookResponse> resultVo) {
//		final CannelBookingParam cancelBook = cancelBookingParam;
//
//		resultVo = BookingRedisHelper.distributedLock(new LockCallback<ResultVo<CannelBookResponse>>() {
//			@Override
//			public ResultVo<CannelBookResponse> exec() {
//				ResultVo<CannelBookResponse> resultVo = new ResultVo<CannelBookResponse>();
//				String serviceEnum = getBookingService(cancelBook.getChannel(), BookingServiceEnumConstants.CANCEL);
//				CancelBookingIncomeVo cancelBookingIncomeVo = initCancelBookingIncomeVo(cancelBook);
//				serviceChain.doServer(cancelBookingIncomeVo, resultVo, serviceEnum);
//				return resultVo;
//			}
//		}, BookingConstants.SEVENDAYS_CANCEL_BOOKING_LOCK + cancelBookingParam.getTradeNo(), 100, BookingConstants.CODE_100001, "请勿频繁取消订单");
//
//		return resultVo;
//	}
	
	/**
	 * 退款加订单号锁
	 * 
	 * @param addBookInfo
	 * @return
	 */
	public ResultVo<Boolean> addRefundLock(RefundAccountIncomeVo refundDetailParam,ResultVo<Boolean> resultVo) {
		//设置final 防止死锁出现
		final RefundAccountIncomeVo refund = refundDetailParam;
		resultVo = BookingRedisHelper.distributedLock(new LockCallback<ResultVo<Boolean>>() {
			@Override
			public ResultVo<Boolean> exec() {
				ResultVo<Boolean> resultVo = new ResultVo<Boolean>();
				String serviceEnum = BookingConstants.BOTAO_2_TICKET_REFUND;
				serviceChain.doServer(refund, resultVo, serviceEnum);
				return resultVo;
			}
		}, BookingConstants.SEVENDAYS_REFUND_LOCK + refund.getPartnerOrderId(), 100, BookingConstants.CODE_100001, "请勿多次拉起退款");

		return resultVo;
	}
	
	public ResultVo<Boolean> addPayLock(PayTicketIncomeVo payTicketIncomeVo,ResultVo<Boolean> resultVo) {
		//设置final 防止死锁出现
		final PayTicketIncomeVo pay = payTicketIncomeVo;
		resultVo = BookingRedisHelper.distributedLock(new LockCallback<ResultVo<Boolean>>() {
			@Override
			public ResultVo<Boolean> exec() {
				ResultVo<Boolean> resultVo = new ResultVo<Boolean>();
				String serviceEnum = BookingConstants.BOTAO_2_TICKET_PAY;
				serviceChain.doServer(pay, resultVo, serviceEnum);
				return resultVo;
			}
		}, BookingConstants.SEVENDAYS_REFUND_LOCK + pay.getBillLogtradeNo(), 100, BookingConstants.CODE_100001, "请勿拉起支付");

		return resultVo;
	}
	
	private void filterCannelOrder(CannelBookingParam cancelBookingParam,ResultVo<CannelBookResponse> resultVo) throws Exception{
		/*OrderParam orderParam = new OrderParam();
		orderParam.setTradeNo(cancelBookingParam.getTradeNo());
		
		ResultVo<CustomBillDetail> vo = orderService.getBillDetail(orderParam);
		if (!vo.success()) {
			resultVo.copyResult(vo);
			return;
		}
		if (null == vo.getData()) {
			String msg = "没有找到该订单orderCode:" + cancelBookingParam.getTradeNo();
			log.error(msg);
			resultVo.setResultCode(this.getClass(), BookingConstants.CODE_701015);
			resultVo.setResultMsg(msg);
			return;
		}
		CustomBillDetail customBillDetail = vo.getData();
		cancelBookingParam.setChannel(customBillDetail.getChannel());
		cancelBookingParam.setMemberId(customBillDetail.getMemberId());
		cancelBookingParam.setOrderNo(customBillDetail.getOrderNo());
		cancelBookingParam.setGoodId(customBillDetail.getGoodsId());*/
	}
	
	/*private void filterRefundOrder(ConvertRefundDetailParam refundDetailParam,RefundAccountIncomeVo refundAccountIncomeVo,ResultVo<Boolean> resultVo) throws Exception{
		Integer type = BookingConstants.PAYCODE_INTEGER;
		Integer status = 2;
		BillDetails billDetails = billMapper.getBillLogByPartnerOrderId(refundDetailParam.getPartnerOrderId(), type, status);
		if(billDetails == null){
			resultVo.setResultCode(getClass(), BookingConstants.CODE_701015);
			resultVo.setResultMsg("退款发起失败,获取不到订单");
			resultVo.setData(false);
			return;
		}
		if (billDetails.getStatus().equals(BookingConstants.PAY_STATUS_500)) {
			resultVo.setData(true);
			return;
		}
		if (billDetails.getTicketStatus().equals(BookingConstants.TICKET_STATE_USE)) {
			resultVo.setResultCode(getClass(), BookingConstants.CODE_701015);
			resultVo.setResultMsg("退款发起失败,订单状态已经核销");
			resultVo.setData(false);
			return;
		}
		if (!(billDetails.getStatus().equals(BookingResultCodeContants.PAY_STATUS_400) || billDetails.getStatus().equals(BookingResultCodeContants.PAY_STATUS_303))) {
			resultVo.setResultCode(getClass(), BookingConstants.CODE_701015);
			resultVo.setResultMsg("退款发起失败,订单状态不是OTA退款中,不能发起退款");
			resultVo.setData(false);
			return;
		}
		refundAccountIncomeVo.setMemberId(billDetails.getMemberId());
		refundAccountIncomeVo.setTradeNo(billDetails.getTradeNo());
		refundDetailParam.setRefundPrice(billDetails.getAmount() - refundDetailParam.getRefundCharge());	//本地订单总金额 - 手续费
		refundAccountIncomeVo.setRefundDetailParam(refundDetailParam);
		refundAccountIncomeVo.setChannel(billDetails.getChannel());
		refundAccountIncomeVo.setBillId(Long.valueOf(billDetails.getBillId()));
		refundAccountIncomeVo.setStatus(billDetails.getStatus());
		refundAccountIncomeVo.setMoney(billDetails.getAmount());
	}*/
	
	
	private void filterPayOrder(PayNotifyReturnParam param,PayTicketIncomeVo payTicketIncomeVo,ResultVo<Boolean> resultVo) throws Exception{
	/*	BillDetails billDetails = billMapper.getBillByBillLogTradeNo(param.getOrderNo());
		if(billDetails == null){
			resultVo.setResultCode(getClass(), BookingConstants.CODE_201011);
			resultVo.setResultMsg("支付网关支付回调失败,获取不到对应的账单信息");
			resultVo.setData(true);
			return;
		}
		if (!billDetails.getStatus().equals(BookingConstants.PAY_STATUS_200)) {
			resultVo.setResultCode(getClass(), BookingConstants.CODE_201012);
			resultVo.setResultMsg("支付网关支付回调失败,订单状态有误");
			resultVo.setData(true);
			return;
		}
		if (billDetails.getAmount().equals(param.getOrderAmount())) {
			resultVo.setResultCode(getClass(), BookingConstants.CODE_201013);
			resultVo.setResultMsg("支付网关支付回调,支付金额不匹配");
			resultVo.setData(true);
			return;
		}
		payTicketIncomeVo.setBillDetails(billDetails);
		payTicketIncomeVo.setMemberId(billDetails.getMemberId());
		payTicketIncomeVo.setTradeNo(billDetails.getTradeNo());
		payTicketIncomeVo.setChannel(billDetails.getChannel());
		payTicketIncomeVo.setBillId(billDetails.getBillId());
		payTicketIncomeVo.setAmount(billDetails.getAmount());
		payTicketIncomeVo.setBillLogtradeNo(param.getOrderNo());
		payTicketIncomeVo.setGoodsId(billDetails.getGoodsId());
		payTicketIncomeVo.setOpenId(billDetails.getOpenId());
		payTicketIncomeVo.setOrderNo(billDetails.getOrderNo());
		payTicketIncomeVo.setStatus(billDetails.getStatus());
		payTicketIncomeVo.setReferenceId(param.getReferenceId());*/
	}
	

	/**
	 * 初始化AddBookingIncomeVo
	 * 
	 * @param addBook
	 * @return
	 */
	private MAddBookingIncomeVo initAddBookingIncomeVo(MAddBookingParam addBook) {
		MAddBookingIncomeVo addBookingIncomeVo = new MAddBookingIncomeVo();
		MAddBookingParam addBookingParam = new MAddBookingParam();
		BeanUtils.copyProperties(addBook, addBookingParam);
		addBookingIncomeVo.setAddBookingParam(addBookingParam);
		return addBookingIncomeVo;
	}
	
	/**
	 * 初始化CancelBookingIncomeVo
	 * 
	 * @param cancelBook
	 * @return
	 */
	protected CancelBookingIncomeVo initCancelBookingIncomeVo(CannelBookingParam cancelBook) {
		CancelBookingIncomeVo cancelBookingIncomeVo = new CancelBookingIncomeVo();
		cancelBookingIncomeVo.setCancelBookingParam(cancelBook);
		return cancelBookingIncomeVo;
	}
}
