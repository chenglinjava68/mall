package com.plateno.booking.internal.service.order;

import java.io.IOException;
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

import com.plateno.booking.internal.base.constant.LogicDelEnum;
import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.base.mapper.MLogisticsMapper;
import com.plateno.booking.internal.base.mapper.MOrderCouponMapper;
import com.plateno.booking.internal.base.mapper.OperatelogMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.mapper.SmsLogMapper;
import com.plateno.booking.internal.base.model.NotifyReturn;
import com.plateno.booking.internal.base.model.bill.ProdSellAmountData;
import com.plateno.booking.internal.base.pojo.Dict;
import com.plateno.booking.internal.base.pojo.MLogistics;
import com.plateno.booking.internal.base.pojo.MLogisticsExample;
import com.plateno.booking.internal.base.pojo.MOrderCouponPO;
import com.plateno.booking.internal.base.pojo.Operatelog;
import com.plateno.booking.internal.base.pojo.OperatelogExample;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderExample;
import com.plateno.booking.internal.base.pojo.OrderExample.Criteria;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.base.vo.MOrderCouponSearchVO;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.OperateLogEnum;
import com.plateno.booking.internal.bean.contants.PayGateCode;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.exception.BizException;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ModifyOrderParams;
import com.plateno.booking.internal.bean.request.custom.ReceiptParam;
import com.plateno.booking.internal.bean.request.gateway.RefundOrderParam;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.bean.response.custom.MOperateLogResponse;
import com.plateno.booking.internal.bean.response.gateway.pay.PayQueryResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundOrderResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundQueryResponse;
import com.plateno.booking.internal.cashierdesk.CashierDeskService;
import com.plateno.booking.internal.cashierdesk.vo.CashierDeskConstant;
import com.plateno.booking.internal.cashierdesk.vo.CashierRefundOrderResponse;
import com.plateno.booking.internal.cashierdesk.vo.CashierRefundQueryReq;
import com.plateno.booking.internal.cashierdesk.vo.CashierRefundQueryResponse;
import com.plateno.booking.internal.cashierdesk.vo.RefundOrderReq;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.common.util.redis.RedisLock;
import com.plateno.booking.internal.common.util.redis.RedisLock.Holder;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.conf.data.LogisticsTypeData;
import com.plateno.booking.internal.coupon.constant.CouponEnum;
import com.plateno.booking.internal.coupon.constant.CouponPlatformType;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.coupon.vo.BaseResponse;
import com.plateno.booking.internal.coupon.vo.CancelParam;
import com.plateno.booking.internal.coupon.vo.CancelResponse;
import com.plateno.booking.internal.coupon.vo.Conditions;
import com.plateno.booking.internal.coupon.vo.UseParam;
import com.plateno.booking.internal.email.model.DeliverGoodContent;
import com.plateno.booking.internal.email.service.PhoneMsgService;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.dict.DictService;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;
import com.plateno.booking.internal.service.log.OperateLogService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.service.order.state.OrderStatusContext;
import com.plateno.booking.internal.sms.SMSSendService;
import com.plateno.booking.internal.validator.order.MOrderValidate;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;


@Service
@ServiceErrorCode(BookingConstants.CODE_DB_BOOK_ERROR)
public class MOrderService {

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

    @Autowired
    private CouponService couponService;

    @Autowired
    private MOrderCouponMapper mOrderCouponMapper;

    @Autowired
    private CashierDeskService cashierDeskService;

    @Autowired
    private OrderRefundActorService orderRefundActorService;
    @Autowired
    private DictService dictService;
    
    public ResultVo<Object> saveOperateLog(MOperateLogParam orderParam) throws OrderException,
            Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderCode(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        Operatelog logs = new Operatelog();
        BeanUtils.copyProperties(orderParam, logs);
        operatelogMapper.insertSelective(logs);
        return output;
    }

    /**
     * 允许修改成的状态
     */
    private static final List<Integer> CAN_MODIFY_STATUS = Arrays.asList(3, 4, 5, 6, 7);

    /**
     * 修改订单
     * 
     * @param modifyOrderParams
     * @return
     * @throws OrderException
     * @throws Exception
     */
    private ResultVo<Object> modifyOrder(ModifyOrderParams modifyOrderParams)
            throws OrderException, Exception {

        logger.info("修改订单，参数:" + JsonUtils.toJsonString(modifyOrderParams));

        ResultVo<Object> output = new ResultVo<Object>();
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(modifyOrderParams.getOrderNo(),
                        modifyOrderParams.getMemberId(), modifyOrderParams.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        Order order = listOrder.get(0);

        int oldStatus = order.getPayStatus();

        // 所有订单状态都能修改，
        /*
         * orderValidate.checkModifyOrder(order, output); if
         * (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) { return output; }
         */

        // 但是只能修改成代发货、待收货、已完成、退款审核中、已退款
        if (!CAN_MODIFY_STATUS.contains(modifyOrderParams.getNewStatus())) {
            logger.info(String.format("orderNo:%s, new status:%s, 不支持修改成该状态", order.getOrderNo(),
                    modifyOrderParams.getNewStatus()));
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("不支持修改成该状态");
            return output;
        }

        // 相同状态不允许修改
        if (order.getPayStatus().equals(modifyOrderParams.getNewStatus())) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("相同状态不支持修改");
            return output;
        }

        if (modifyOrderParams.getNewStatus().equals(BookingConstants.PAY_STATUS_6)) {// 如果状态要变成退款中,需要修改一下字段

            // 退款中和审核中状态，不支持直接触发支付网关退款
            if (BookingResultCodeContants.PAY_STATUS_6 == order.getPayStatus()
                    || BookingResultCodeContants.PAY_STATUS_10 == order.getPayStatus()) {
                logger.info(String.format("orderNo:%s, now status:%s, 不支持直接退款", order.getOrderNo(),
                        order.getPayStatus()));
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

            // 插入支付流水
            OrderPayLog orderPayLog = new OrderPayLog();
            orderPayLog.setAmount(-order.getPayMoney());
            orderPayLog.setType(2);// 支出
            orderPayLog.setPoint(order.getPoint());
            orderPayLog.setClientType(1);
            orderPayLog.setCreateTime(new Date());
            orderPayLog.setTrandNo(StringUtil.getCurrentAndRamobe("L"));
            orderPayLog.setReferenceid("");
            orderPayLog.setRemark(modifyOrderParams.getRemark());
            orderPayLog.setStatus(1);// 状态 1初始化，2成功，3失败
            orderPayLog.setUpTime(new Date());
            orderPayLog.setOrderId(order.getId());
            orderPayLogMapper.insertSelective(orderPayLog);
        }

        order.setPayStatus(modifyOrderParams.getNewStatus());
        order.setRemark(modifyOrderParams.getRemark());
        order.setUpTime(new Date());

        if (mallOrderMapper.updateByPrimaryKeySelective(order) > 0)
            orderLogService.saveGSOrderLog(modifyOrderParams.getOrderNo(),
                    modifyOrderParams.getNewStatus(), "客服修改状态",
                    "客服修改状成功:" + StringUtils.trimToEmpty(modifyOrderParams.getRemark()), 0,
                    ViewStatusEnum.VIEW_STATUS_PAYING.getCode());


        MOperateLogParam paramlog = new MOperateLogParam();
        paramlog.setOperateType(OperateLogEnum.ORDER_MODIFY.getOperateType());
        paramlog.setOperateUserid(modifyOrderParams.getOperateUserid());
        paramlog.setOperateUsername(modifyOrderParams.getOperateUsername());
        paramlog.setOrderCode(modifyOrderParams.getOrderNo());
        paramlog.setPlateForm(modifyOrderParams.getPlateForm());

        String remark =
                OperateLogEnum.ORDER_MODIFY.getOperateName()
                        + String.format(":%s, 修改前状态:%s, 修改后状态:%s",
                                StringUtils.trimToEmpty(modifyOrderParams.getRemark()),
                                PayStatusEnum.from(oldStatus).getDesc(),
                                PayStatusEnum.from(modifyOrderParams.getNewStatus()).getDesc());
        remark = remark.length() > 99 ? remark.substring(0, 99) : remark;

        paramlog.setRemark(remark);
        operateLogService.saveOperateLog(paramlog);

        output.setData(order.getMemberId());
        return output;
    }

    /**
     * 修改订单加锁
     * 
     * @param modifyOrderParams
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = OrderException.class)
    public ResultVo<Object> modifyOrderLock(final ModifyOrderParams modifyOrderParams)
            throws Exception {

        String lockName = "MALL_MODIFY_ORDER_" + modifyOrderParams.getOrderNo();

        Holder holder = new RedisLock.Holder() {
            @Override
            public Object exec() throws Exception {
                // 修改订单
                return modifyOrder(modifyOrderParams);
            }
        };

        return (ResultVo<Object>) RedisLock.lockExec(lockName, holder);
    }

    public ResultVo<List<MOperateLogResponse>> selectOperateLog(MOperateLogParam params)
            throws Exception {
        ResultVo<List<MOperateLogResponse>> output = new ResultVo<List<MOperateLogResponse>>();
        List<MOperateLogResponse> lisLogs = new ArrayList<MOperateLogResponse>();
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(params.getOrderCode(),
                        params.getMemberId(), params.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        OperatelogExample operatelogExample = new OperatelogExample();
        operatelogExample.createCriteria().andOrderCodeEqualTo(params.getOrderCode());
        List<Operatelog> listlogs = operatelogMapper.selectByExample(operatelogExample);
        for (Operatelog log : listlogs) {
            MOperateLogResponse response = new MOperateLogResponse();
            // BeanUtils.copyProperties(log, response);
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


    public ResultVo<Object> modifyReceiptInfo(ReceiptParam receiptParam) throws OrderException,
            Exception {
        ResultVo<Object> output = new ResultVo<Object>();

        receiptParam.setReceiptAddress(StringUtils.trimToEmpty(receiptParam.getReceiptAddress()));
        receiptParam.setReceiptMobile(StringUtils.trimToEmpty(receiptParam.getReceiptMobile()));
        receiptParam.setReceiptName(StringUtils.trimToEmpty(receiptParam.getReceiptName()));
        receiptParam.setProvince(StringUtils.trimToEmpty(receiptParam.getProvince()));
        receiptParam.setCity(StringUtils.trimToEmpty(receiptParam.getCity()));
        receiptParam.setArea(StringUtils.trimToEmpty(receiptParam.getArea()));

        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(receiptParam.getOrderNo(),
                        receiptParam.getMemberId(), receiptParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        MLogisticsExample mLogisticsExample = new MLogisticsExample();
        mLogisticsExample.createCriteria().andOrderNoEqualTo(receiptParam.getOrderNo());
        List<MLogistics> listLogistic = mLogisticsMapper.selectByExample(mLogisticsExample);
        if (CollectionUtils.isEmpty(listLogistic)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("查询不到对应的收货信息");
            return output;
        }
        MLogistics logc = listLogistic.get(0);
        /*
         * logc.setConsigneeName(receiptParam.getReceiptName());
         * logc.setConsigneeAddress(receiptParam.getReceiptAddress());
         * logc.setConsigneeMobile(receiptParam.getReceiptMobile());
         */

        // 原始的地址不修改，只是修改最新的地址
        logc.setConsigneeNewaddress(receiptParam.getReceiptAddress());
        logc.setConsigneeNewMobile(receiptParam.getReceiptMobile());
        logc.setConsigneeNewName(receiptParam.getReceiptName());
        logc.setNewProvince(receiptParam.getProvince());
        logc.setNewCity(receiptParam.getCity());
        logc.setNewArea(receiptParam.getArea());
        mLogisticsMapper.updateByPrimaryKeySelective(logc);


        MOperateLogParam paramlog = new MOperateLogParam();
        paramlog.setOperateType(OperateLogEnum.MODIFY_DELIVER_OP.getOperateType());
        paramlog.setOperateUserid(receiptParam.getOperateUserid());
        paramlog.setOperateUsername(receiptParam.getOperateUsername());
        paramlog.setOrderCode(receiptParam.getOrderNo());
        paramlog.setPlateForm(receiptParam.getPlateForm());
        String remark =
                OperateLogEnum.MODIFY_DELIVER_OP.getOperateName()
                        + String.format(":%s|%s|%s|%s|%s|%s", receiptParam.getReceiptName(),
                                receiptParam.getReceiptMobile(), receiptParam.getProvince(),
                                receiptParam.getCity(), receiptParam.getArea(),
                                receiptParam.getReceiptAddress());
        remark = remark.length() > 99 ? remark.substring(0, 99) : remark;
        paramlog.setRemark(remark);
        operateLogService.saveOperateLog(paramlog);

        return output;
    }



    @Transactional(rollbackFor = OrderException.class)
    public com.plateno.booking.internal.base.pojo.Order insertOrder(MAddBookingIncomeVo income)
            throws OrderException {
        try {

            logger.info("封装参数开始...");

            com.plateno.booking.internal.base.pojo.Order ordes =
                    new com.plateno.booking.internal.base.pojo.Order();
            MAddBookingParam book = income.getAddBookingParam();
            String orderNo = StringUtil.getCurrentAndRamobe("O");

            // 商品接口获取参数
            ProductSkuBean pskubean =
                    mallGoodsService.getProductAndskuStock(book.getGoodsId().toString());
            if (pskubean == null) {
                logger.error("获取商品信息失败");
                throw new OrderException("获取商品信息失败");
            }

            int expressFee = 0;
            int price = 0;
            if (pskubean.getExpressFee() != null && pskubean.getExpressFee() > 0) {
                expressFee = pskubean.getExpressFee();
            }
            // 判断是否有促销价
            if (pskubean.getPromotPrice() != null && pskubean.getPromotPrice() > 0) {
                price = pskubean.getPromotPrice();
            } else {
                price = pskubean.getRegularPrice();
            }

            int orderStatus = BookingResultCodeContants.PAY_STATUS_1;
            int payType = 0;
            // 当优惠券的金额大于商品需要支付的金额的时候，如果包邮，需要支付的金额将会是0，这是直接把订单的状态变成代发货
            if (book.getTotalAmount() <= 0) {
                orderStatus = PayStatusEnum.PAY_STATUS_3.getPayStatus();
                payType = 3; // 支付方式，无需支付
                if(null != book.getSubResource()){
                    Dict dict = dictService.findDictByKey("sid");
                    if (null != dict) {
                        String value = dict.getOrderValue();
                        String[] valueArr = value.split(",");
                        for (String temp : valueArr) {
                            //判断sid是否符合
                            if (book.getSubResource().compareTo(Integer.valueOf(temp)) == 0){
                                orderStatus = BookingResultCodeContants.PAY_STATUS_4;
                            }
                        }
                    }
                }
            }

            ordes.setResource(book.getResource());
            // 商品非积分的总的价格，不包含运费
            ordes.setAmount(book.getQuantity() * price + expressFee);
            // ordes.setChanelid(book.getChanelId());
            // 渠道从商品服务获取
            ordes.setChanelid(pskubean.getChannelId());
            ordes.setCreateTime(new Date());
            ordes.setItemId(0);
            ordes.setMemberId(book.getMemberId());
            ordes.setMobile(book.getMobile());
            ordes.setName(book.getName());
            ordes.setOrderNo(orderNo);
            ordes.setPayTime(new Date());
            ordes.setPayType(payType);// 默认1微信支付、2支付宝支付 3无需支付
            ordes.setPayStatus(orderStatus);
            ordes.setPoint(book.getPoint());
            // ordes.setPayMoney(pskubean.getSellStrategy()==1?pskubean.getRegularPrice():pskubean.getFavorPrice());
            ordes.setPayMoney(book.getTotalAmount());

            ordes.setRefundAmount(0);
            ordes.setSid(0);
            ordes.setUpTime(new Date());
            long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
            ordes.setWaitPayTime(new Date(currentTime));// 加上30分钟

            // 订单子来源（不同的入口）
            ordes.setSubResource(book.getSubResource() == null ? 0 : book.getSubResource());

            // 记录订单商品成本和发货成本
            ordes.setTotalExpressCost(pskubean.getCostExpress() * book.getQuantity());
            ordes.setTotalProductCost(pskubean.getCostPrice() * book.getQuantity());

            // 优惠券抵扣金额
            ordes.setCouponAmount(book.getValidCouponAmount() == null ? 0 : book
                    .getValidCouponAmount().multiply(new BigDecimal("100")).intValue());

            OrderProduct op = new OrderProduct();
            op.setOrderNo(orderNo);
            op.setPrice(price);
            op.setProductId(pskubean.getProductId());
            op.setProductName(pskubean.getTitle());
            op.setProductProperty(JsonUtils.toJsonString(pskubean.getSkuPropertyInfos()));
            op.setSkuCount(book.getQuantity());
            op.setSkuid(book.getGoodsId().intValue());
            op.setCreateTime(new Date());
            op.setUpTime(new Date());
            if (book.getSellStrategy() == 2) {
                op.setPoint(pskubean.getFavorPoints());
            } else {
                op.setPoint(0);
            }
            op.setSellStrategy(book.getSellStrategy());
            op.setDisImages(pskubean.getImgPath());
            op.setPriceStrategy(pskubean.getPriceStrategy() == null ? 1 : pskubean
                    .getPriceStrategy());
            op.setPriceStrategyDesc(StringUtils.trimToEmpty(pskubean.getPriceName()));
            op.setDeductPrice(pskubean.getDeductPrice() == null || book.getSellStrategy() == 1 ? 0
                    : pskubean.getDeductPrice());
            op.setProductCost(pskubean.getCostPrice());
            op.setExpressCost(pskubean.getCostExpress());

            MLogistics logistics = new MLogistics();
            logistics.setOrderNo(orderNo);
            logistics.setShippingType(1);// 1包邮,2普通快递
            logistics.setConsigneeName(book.getConsigneeName());
            logistics.setConsigneeAddress(book.getConsigneeAddress());
            logistics.setConsigneeMobile(book.getConsigneeMobile());
            logistics.setExpressFee(pskubean.getExpressFee());
            logistics.setLogisticsType(1);// 物流类型(1 圆通、2申通、3韵达、4百事通、5顺丰、6 EMS),默认圆通
            logistics.setProvince(book.getProvince());
            logistics.setCity(book.getCity());
            logistics.setArea(book.getArea());

            logger.info("插入数据");

            // 如果使用优惠券，记录优惠券的使用信息
            if (book.getCouponId() != null && book.getCouponId() > 0) {
                MOrderCouponPO mOrderCouponPO = new MOrderCouponPO();
                mOrderCouponPO.setCouponId(book.getCouponId());
                mOrderCouponPO.setOrderNo(orderNo);
                mOrderCouponPO.setCouponType(CouponEnum.MONEY_COUPON.getType());
                mOrderCouponPO.setSubCouponType(CouponEnum.MONEY_COUPON.getSubType());
                mOrderCouponPO.setCouponName(book.getCouponName());
                mOrderCouponPO.setAmount(book.getCouponAmount());
                mOrderCouponPO.setOrderCouponAmount(book.getValidCouponAmount());
                mOrderCouponPO.setCreateTime(new Date());
                mOrderCouponMapper.insert(mOrderCouponPO);
            }

            mallOrderMapper.insertSelective(ordes);
            mLogisticsMapper.insertSelective(logistics);
            orderProductMapper.insertSelective(op);

            // 扣减库存
            boolean modifyStock =
                    mallGoodsService.modifyStock(book.getGoodsId().toString(), -book.getQuantity());
            if (!modifyStock) {
                logger.error("扣减库存失败， {}", modifyStock);
                throw new OrderException("系统正忙，扣减库存失败，请重试！");
            }

            // 扣减积分
            if (book.getSellStrategy().equals(2)) {
                logger.info("下单扣减积分， sellStrategy:{}, point:{}", book.getSellStrategy(),
                        book.getPoint());
                boolean minusPoint = minusPoint(book.getMemberId(), book.getPoint());
                if (!minusPoint) {
                    logger.error("扣积分失败， {}， {}", book.getMemberId(), minusPoint);

                    // 事务回滚，归还库存
                    boolean result =
                            mallGoodsService
                                    .modifyStock(book.getGoodsId() + "", book.getQuantity());
                    if (!result) {
                        LogUtils.DISPERSED_ERROR_LOGGER.error(
                                "下单扣减积分失败，回滚事务，归还库存失败，skuId:{}, num:{}", book.getGoodsId(),
                                book.getQuantity());
                        logger.error("下单扣减积分失败，回滚事务，归还库存失败，skuId:{}, num:{}", book.getGoodsId(),
                                book.getQuantity());
                    }

                    throw new OrderException("系统正忙，扣减积分，请重试！");
                }
            }

            // 使用优惠券
            if (book.getCouponId() != null && book.getCouponId() > 0) {
                UseParam useCouponParam = new UseParam();
                useCouponParam.setCouponId(book.getCouponId());
                useCouponParam.setMebId(book.getMemberId());
                useCouponParam.setOrderCode(orderNo);
                useCouponParam.setPlatformId(CouponPlatformType.fromResource(book.getResource())
                        .getPlatformId());
                Conditions conditions = new Conditions();
                useCouponParam.setConditions(conditions);
                conditions.setOrderAmount(new BigDecimal((book.getQuantity() * price) + "").divide(
                        new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_DOWN));
                conditions.setProductId(pskubean.getProductId());
                conditions.setCategoryId(pskubean.getCategoryId());

                ResultVo<BaseResponse> useCouponResult = couponService.useCoupon(useCouponParam);
                if (!useCouponResult.success()) {

                    logger.error("优惠券使用失败, memberId:{}, couponId:{}, result:{}",
                            book.getMemberId(), book.getCouponId(), useCouponResult);

                    // 事务回滚，归还库存
                    boolean result =
                            mallGoodsService
                                    .modifyStock(book.getGoodsId() + "", book.getQuantity());
                    if (!result) {
                        LogUtils.DISPERSED_ERROR_LOGGER.error(
                                "下单扣减积分失败，回滚事务，归还库存失败，skuId:{}, num:{}", book.getGoodsId(),
                                book.getQuantity());
                        logger.error("下单扣减积分失败，回滚事务，归还库存失败，skuId:{}, num:{}", book.getGoodsId(),
                                book.getQuantity());
                    }

                    // 事务回滚，退还积分
                    if (book.getSellStrategy().equals(2)) {
                        logger.info("使用优惠券失败，退还积分， memberId:{}, point:{}", book.getMemberId(),
                                book.getPoint());

                        ValueBean vb = new ValueBean();
                        vb.setPointvalue(book.getPoint());
                        vb.setMebId(book.getMemberId());
                        vb.setTrandNo(orderNo);
                        int mallAddPoint = pointService.mallAddPoint(vb);
                        if (mallAddPoint > 0) {
                            logger.error("下单事务回滚，退还积分失败，orderNo:{}, memberId:{}, point:{}",
                                    orderNo, book.getMemberId(), book.getPoint());
                            LogUtils.DISPERSED_ERROR_LOGGER.error(
                                    "下单事务回滚，退还积分失败，orderNo:{}, memberId:{}, point:{}", orderNo,
                                    book.getMemberId(), book.getPoint());
                        }
                    }

                    throw new OrderException("系统正忙，使用优惠券失败，请重试！");
                }
            }

            return ordes;

        } catch (Exception e) {
            // LogUtils.sysErrorLoggerError("订单创建失败", e);
            // e.printStackTrace();
            logger.error("订单创建失败", e);
            throw new OrderException("订单创建失败:" + e.getMessage());
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
    public ResultVo<Object> deleteOrder(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();

        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        orderValidate.checkDeleteOrder(listOrder.get(0), output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }

        // 构造sql的过滤语句
        CallMethod<Order> call = new CallMethod<Order>() {
            @Override
            void call(Criteria criteria, Order order) throws Exception {
                invoke(criteria, "andOrderNoEqualTo", orderParam.getOrderNo());
            }
        };
        Order order = new Order();
        // order.setPayStatus(BookingResultCodeContants.PAY_STATUS_9);
        order.setLogicDel(LogicDelEnum.DEL.getType());
        updateOrderStatusByNo(order, call);

        // orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
        // BookingResultCodeContants.PAY_STATUS_9, "删除订单", "删除订单成功",
        // 0,ViewStatusEnum.VIEW_STATUS_CANNEL.getCode());

        return output;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultVo<Object> userConsentRefund(MOrderParam param) throws Exception {
        ResultVo<Object> resultVo = userRefund(param);
        // c端用户申请退款，直接发起退款流程，不用审核
        if (resultVo.success()
                && (param.getPlateForm() == PlateFormEnum.APP.getPlateForm() || param
                        .getPlateForm() == PlateFormEnum.USER.getPlateForm())) {
            logger.info("渠道：{}，发起退款申请，不需要审核，直接发起退款申请，进入到退款中状态",
                    PlateFormEnum.from(param.getPlateForm()).getDesc());
            return refundOrder(param);
        }
        return resultVo;
    }

    /**
     * 客服确定退款
     * 
     * @param orderParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<Object> refundOrder(MOrderParam orderParam) throws Exception {

        logger.info(String.format("确认退款，参数:%s", JsonUtils.toJsonString(orderParam)));

        ResultVo<Object> output = new ResultVo<Object>();
        final List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        final Order dbOrder = listOrder.get(0);
        orderValidate.checkRefund(dbOrder, output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            logger.info("output:" + output);
            return output;
        }

        OrderProduct productByOrderNo = getProductByOrderNo(dbOrder.getOrderNo());
        if (productByOrderNo == null) {
            logger.error("orderNo:{}, 查找商品信息失败", dbOrder.getOrderNo());
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("获取商品信息失败！");
            return output;
        }

        ResultVo<Object> result = null;

        // 下单有支付行为
        if (dbOrder.getPayMoney() > 0) {
            result = refundOrderWithMoney(orderParam, dbOrder);
        } else { // 没有支付行为，只是退还优惠券
            result = refundOrderWithoutMoney(orderParam, dbOrder);
        }

        if (!result.success()) {
            return result;
        }

        // 更新已经返还的库存
        int row =
                orderProductMapper.updateReturnSkuCount(productByOrderNo.getSkuCount(),
                        productByOrderNo.getId());
        // 异常单修改，有可能会进行多次退款，避免多次退还库存
        if (row > 0) {
            // 退还库存
            logger.info("orderNo:{}， 退还库存，skuid:{}, count:{}", dbOrder.getOrderNo(),
                    productByOrderNo.getSkuid(), productByOrderNo.getSkuCount());
            boolean modifyStock =
                    mallGoodsService.modifyStock(productByOrderNo.getSkuid().toString(),
                            productByOrderNo.getSkuCount());
            if (!modifyStock) {
                logger.error(String.format("orderNo:%s, 调用商品服务失败", dbOrder.getOrderNo()));
                LogUtils.DISPERSED_ERROR_LOGGER.error("退款归还库存失败, orderNo:{}, skuId:{}, count:{}",
                        dbOrder.getOrderNo(), productByOrderNo.getSkuid(),
                        productByOrderNo.getSkuCount());
            }
        }

        // 记录操作日志
        MOperateLogParam paramlog = new MOperateLogParam();
        paramlog.setOperateType(OperateLogEnum.AGREE_REFUND_OP.getOperateType());
        paramlog.setOperateUserid(orderParam.getOperateUserid());
        paramlog.setOperateUsername(orderParam.getOperateUsername());
        paramlog.setOrderCode(orderParam.getOrderNo());
        paramlog.setPlateForm(orderParam.getPlateForm());
        paramlog.setRemark(OperateLogEnum.AGREE_REFUND_OP.getOperateName());
        operateLogService.saveOperateLog(paramlog);

        return result;
    }

    /**
     * 退款，无需和支付网关交互
     * 
     * @param orderParam
     * @param dbOrder
     * @return
     * @throws Exception
     */
    private ResultVo<Object> refundOrderWithoutMoney(MOrderParam orderParam, Order dbOrder)
            throws Exception {

        // 更新为成功，防并发
        Order order = new Order();
        order.setUpTime(new Date());
        order.setPayStatus(PayStatusEnum.PAY_STATUS_7.getPayStatus());// 退款中
        List<Integer> oldStatus = Arrays.asList(PayStatusEnum.PAY_STATUS_6.getPayStatus());
        int row = updateOrderStatusByNo(order, orderParam.getOrderNo(), oldStatus);
        if (row < 1) {
            logger.info("订单退款已经处理，orderNo:" + orderParam.getOrderNo());
            return new ResultVo<Object>(ResultCode.FAILURE, null, "订单已经处理，请勿重复请求！");
        }

        // 记录操作日志
        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_7, "退款操作", "无需退还金额，返回优惠券", 0,
                ViewStatusEnum.VIEW_STATUS_GATE_REFUNDING.getCode());

        // 如果使用了优惠券，退还优惠券
        if (dbOrder.getCouponAmount() > 0) {
            ResultVo<String> returnCoupon =
                    returnCoupon(dbOrder.getOrderNo(), dbOrder.getMemberId());
            if (!returnCoupon.success()) {
                logger.info("orderNo:{}, 退款失败，返还优惠券失败:{}", orderParam.getOrderNo(), returnCoupon);
                throw new Exception("退款失败，" + returnCoupon.getResultMsg());
            }
        }

        return new ResultVo<Object>(ResultCode.SUCCESS);
    }


    /**
     * 退款，需要和支付网关交互
     * 
     * @param orderParam
     * @param dbOrder
     * @return
     * @throws Exception
     * @throws IOException
     */
    private ResultVo<Object> refundOrderWithMoney(MOrderParam orderParam, final Order dbOrder)
            throws Exception, IOException {
        // 更新为退款中，防并发
        Order order = new Order();
        order.setUpTime(new Date());
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_10);// 退款中
        List<Integer> oldStatus = Arrays.asList(BookingResultCodeContants.PAY_STATUS_6);
        Integer row = updateOrderStatusByNo(order, orderParam.getOrderNo(), oldStatus);
        if (row < 1) {
            logger.info("订单退款已经处理，orderNo:" + orderParam.getOrderNo());
            return new ResultVo<Object>(ResultCode.FAILURE, null, "订单已经处理，请勿重复请求！");
        }

        // 记录操作日志
        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_10, "退款操作", "支付网关退款中", 0,
                ViewStatusEnum.VIEW_STATUS_GATE_REFUNDING.getCode());

        // 调用网关退款接口 1先检查是否存在支付成功的流水、2申请退款 、3自动job查询网关退款中的订单
        OrderPayLogExample logExample = new OrderPayLogExample();
        logExample.createCriteria().andOrderIdEqualTo(dbOrder.getId()).andTypeEqualTo(1)
                .andStatusEqualTo(BookingConstants.BILL_LOG_SUCCESS); // 下单直流的流水，且是支付成功的
        List<OrderPayLog> listPayLog = orderPayLogMapper.selectByExample(logExample);
        if (CollectionUtils.isEmpty(listPayLog)) {
            logger.info(String.format("orderNo:%s, 没有支付成功的流水，尝试退款", orderParam.getOrderNo()));
            throw new Exception("支付成功的流水记录不存在");
        }

        // 查询退款的申请的支付流水
        logExample = new OrderPayLogExample();
        logExample.createCriteria().andOrderIdEqualTo(dbOrder.getId()).andTypeEqualTo(2)
                .andStatusEqualTo(1);
        List<OrderPayLog> refundLogList = orderPayLogMapper.selectByExample(logExample);
        if (CollectionUtils.isEmpty(refundLogList)) {
            logger.info(String.format("orderNo:%s, 没有处理中的退款流水，尝试退款", orderParam.getOrderNo()));
            throw new Exception("申请退款且状态是初始化的支付流水不存在");
        }

        if (refundLogList.size() != 1) {
            logger.info(String.format("orderNo:%s, 存在不止一条的退款流水:%s", orderParam.getOrderNo(),
                    refundLogList.size()));
            throw new Exception("存在不止一条的退款流水");
        }

        OrderPayLog refundOrderPayLog = refundLogList.get(0);
        OrderPayLog successOrderPayLog = listPayLog.get(0);

        // 旧的网关退款数据，走旧的退款平台，收银台referenceid是空的
        if (StringUtils.isNotBlank(refundOrderPayLog.getReferenceid())) {
            // 封装退款参数
            RefundOrderParam refundOrderParam = new RefundOrderParam();
            refundOrderParam.setRefundAmount(-refundOrderPayLog.getAmount());
            refundOrderParam.setRefundOrderNo(refundOrderPayLog.getTrandNo()); // 退款申请的订单号
            refundOrderParam.setRemark(refundOrderPayLog.getRemark());
            refundOrderParam.setOrderNo(successOrderPayLog.getTrandNo()); // 原交易订单号

            // 调用支付网关退款
            RefundOrderResponse response = null;
            try {
                response = paymentService.refundOrder(refundOrderParam);
            } catch (Exception e) {
                logger.error("支付网关申请退款异常:" + successOrderPayLog.getTrandNo(), e);
            }
            logger.info(String.format("refundOrderNo:%s, 网关申请退款, 返回:%s",
                    refundOrderPayLog.getTrandNo(), JsonUtils.toJsonString(response)));
            return new ResultVo<Object>(ResultCode.SUCCESS, null,
                    MsgCode.REFUND_HANDLING.getMessage());
        } else {
            RefundOrderReq refundOrderReq = new RefundOrderReq();
            refundOrderReq.setTradeNo(successOrderPayLog.getTrandNo());// 支付流水号
            refundOrderReq.setRefundTradeNo(refundOrderPayLog.getTrandNo());
            refundOrderReq.setRefundOrderNo(orderParam.getOrderNo());
            refundOrderReq.setAmount(-refundOrderPayLog.getAmount());// 金额
            refundOrderReq.setMemberId(orderParam.getMemberId());// 会员id
            CashierRefundOrderResponse refundOrderResponse =
                    cashierDeskService.refundOrder(refundOrderReq);
            // 发起退款判断
            if (null == refundOrderResponse || refundOrderResponse.getMsgCode() != 100) {
                logger.warn("支付网关，发起退款失败，tranNo:{},req:{},res:{}", refundOrderPayLog.getTrandNo(),
                        JsonUtils.toJsonString(refundOrderReq),
                        JsonUtils.toJsonString(refundOrderResponse));
                throw new BizException("支付网关，发起退款失败，" + refundOrderResponse.getMessage());
            } else {
                logger.info("orderNo:{}, 网关申请退款成功, 返回:{}", refundOrderPayLog.getTrandNo(),
                        JsonUtils.toJsonString(refundOrderResponse));
            }
            return new ResultVo<Object>(ResultCode.SUCCESS, null,
                    MsgCode.REFUND_HANDLING.getMessage());
        }
    }


    /**
     * 扣减积分
     * 
     * @param income
     * @param output
     * @throws Exception
     */
    public boolean minusPoint(int memberId, int point) throws Exception {
        ValueBean v = new ValueBean();
        v.setMebId(memberId);
        v.setPointvalue(-point);
        int r = pointService.mallMinusPoint(v);
        if (r > 0) {
            return false;
        } else {
            return true;
        }
    }



    /**
     * 更新订单状态(发货通知)
     * 
     * @param orderParam
     * @return
     * @throws Exception
     */
    @Transactional
    public ResultVo<Object> deliverOrder(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        orderValidate.checkDeliverOrder(listOrder.get(0), output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }

        // 构造sql的过滤语句
        CallMethod<Order> call = new CallMethod<Order>() {
            @Override
            void call(Criteria criteria, Order order) throws Exception {
                invoke(criteria, "andOrderNoEqualTo", orderParam.getOrderNo());
            }
        };
        CallLogisticMethod<MLogistics> callLogistic = new CallLogisticMethod<MLogistics>() {
            @Override
            void call(com.plateno.booking.internal.base.pojo.MLogisticsExample.Criteria criteria,
                    MLogistics logistics) throws Exception {
                invoke(criteria, "andOrderNoEqualTo", orderParam.getOrderNo());
            }
        };

        Order order = new Order();
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_4);// 待发货==>待收货
        order.setDeliverTime(new Date());
        updateOrderStatusByNo(order, call);
        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_4, "发货操作", "发货成功", 0,
                ViewStatusEnum.VIEW_STATUS_DELIVERS.getCode());


        MLogistics logistics = new MLogistics();
        logistics.setLogisticsNo(StringUtils.trimToEmpty(orderParam.getLogisticsNo()));
        logistics.setLogisticsType(orderParam.getLogisticsType());
        updatLogisticsNoByNo(logistics, callLogistic);


        // 短信通知发货
        final Order od = listOrder.get(0);
        OrderProductExample orderProductExample = new OrderProductExample();
        orderProductExample.createCriteria().andOrderNoEqualTo(orderParam.getOrderNo());
        List<OrderProduct> productOrderList =
                orderProductMapper.selectByExample(orderProductExample);
        if (CollectionUtils.isEmpty(productOrderList)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单获取不到对应的产品信息");
            return output;
        }
        final ProductSkuBean bean =
                mallGoodsService.getProductAndskuStock(productOrderList.get(0).getSkuid()
                        .toString());
        // 发送到短信
        DeliverGoodContent content = new DeliverGoodContent();
        content.setObjectNo(od.getOrderNo());
        content.setOrderCode(od.getOrderNo());
        content.setName(bean.getTitle());
        content.setExpress(LogisticsTypeData.getDataMap().get(orderParam.getLogisticsType()));
        content.setExpressCode(StringUtils.isBlank(orderParam.getLogisticsNo()) ? "无" : orderParam
                .getLogisticsNo());
        phoneMsgService.sendPhoneMessageAsync(od.getMobile(), Config.SMS_SERVICE_TEMPLATE_SEVEN,
                content);

        MOperateLogParam paramlog = new MOperateLogParam();
        paramlog.setOperateType(OperateLogEnum.DELIVER_ORDER.getOperateType());
        paramlog.setOperateUserid(orderParam.getOperateUserid());
        paramlog.setOperateUsername(orderParam.getOperateUsername());
        paramlog.setOrderCode(orderParam.getOrderNo());
        paramlog.setPlateForm(orderParam.getPlateForm());
        paramlog.setRemark(OperateLogEnum.DELIVER_ORDER.getOperateName());
        operateLogService.saveOperateLog(paramlog);


        return output;
    }


    public ResultVo<Object> modifydeliverOrder(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        // 构造sql的过滤语句
        CallLogisticMethod<MLogistics> callLogistic = new CallLogisticMethod<MLogistics>() {
            @Override
            void call(com.plateno.booking.internal.base.pojo.MLogisticsExample.Criteria criteria,
                    MLogistics logistics) throws Exception {
                invoke(criteria, "andOrderNoEqualTo", orderParam.getOrderNo());
            }
        };


        MLogistics logistics = new MLogistics();
        // 物流改成自提，需要把原来的快递单号设置成空
        logistics.setLogisticsNo(StringUtils.trimToEmpty(orderParam.getLogisticsNo()));
        logistics.setLogisticsType(orderParam.getLogisticsType());
        updatLogisticsNoByNo(logistics, callLogistic);


        MOperateLogParam paramlog = new MOperateLogParam();
        paramlog.setOperateType(OperateLogEnum.MODIFY_DELIVER_OP.getOperateType());
        paramlog.setOperateUserid(orderParam.getOperateUserid());
        paramlog.setOperateUsername(orderParam.getOperateUsername());
        paramlog.setOrderCode(orderParam.getOrderNo());
        paramlog.setPlateForm(orderParam.getPlateForm());
        String remark =
                OperateLogEnum.MODIFY_DELIVER_OP.getOperateName()
                        + String.format(":%s|%s|%s", orderParam.getLogisticsNo(),
                                orderParam.getLogisticsType(),
                                LogisticsTypeData.getDataMap().get(orderParam.getLogisticsType()));
        remark = remark.length() > 99 ? remark.substring(0, 99) : remark;
        paramlog.setRemark(remark);
        operateLogService.saveOperateLog(paramlog);


        return output;
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultVo<Object> enterReceipt(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        orderValidate.checkEnterReceiptStatus(listOrder.get(0), output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }

        // 构造sql的过滤语句
        CallMethod<Order> call = new CallMethod<Order>() {
            @Override
            void call(Criteria criteria, Order order) throws Exception {
                invoke(criteria, "andOrderNoEqualTo", orderParam.getOrderNo());
            }
        };
        Order order = new Order();
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_5);// 确定收货操作==>已完成
        order.setUpTime(new Date());
        updateOrderStatusByNo(order, call);
        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_5, "确认收货", "手动确定收货", 0,
                ViewStatusEnum.VIEW_STATUS_COMPLETE.getCode());
        // 如果是后台操作，取消记录操作日志
        if (orderParam.getPlateForm() != null
                && (orderParam.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || orderParam
                        .getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {

            MOperateLogParam paramlog = new MOperateLogParam();
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


    @Transactional(rollbackFor = Exception.class)
    public ResultVo<Object> adminRefuseRefund(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        orderValidate.checkAdminRefund(listOrder.get(0), output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }

        // 构造sql的过滤语句
        CallMethod<Order> call = new CallMethod<Order>() {
            @Override
            void call(Criteria criteria, Order order) throws Exception {
                invoke(criteria, "andOrderNoEqualTo", orderParam.getOrderNo());
            }
        };
        Order order = new Order();
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_8);// 退款审核中==>退款审核不通过
        order.setRefundSuccesstime(new Date());
        order.setRefundFailReason(StringUtils.trimToEmpty(orderParam.getRefundRemark())); // 退款失败原因
        updateOrderStatusByNo(order, call);

        // 更新退款流水为失败
        OrderPayLog record = new OrderPayLog();
        record.setStatus(3);
        OrderPayLogExample example = new OrderPayLogExample();
        example.createCriteria().andOrderIdEqualTo(listOrder.get(0).getId()).andTypeEqualTo(2)
                .andStatusEqualTo(1);
        orderPayLogMapper.updateByExampleSelective(record, example);


        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_8, "拒绝退款操作",
                StringUtils.trimToEmpty(orderParam.getRefundRemark()), 0,
                ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode());

        MOperateLogParam paramlog = new MOperateLogParam();
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
    public ResultVo<Object> userRefund(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        orderValidate.checkUserRefund(listOrder.get(0), output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }

        // 构造sql的过滤语句
        CallMethod<Order> call = new CallMethod<Order>() {
            @Override
            void call(Criteria criteria, Order order) throws Exception {
                invoke(criteria, "andOrderNoEqualTo", orderParam.getOrderNo());
            }
        };
        Order order = new Order();
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_6);// 用户申请退款==>退款审核中
        order.setRefundAmount(listOrder.get(0).getPayMoney());
        order.setPoint(listOrder.get(0).getPoint());
        // order.setRefundTime(listOrder.get(0).getRefundTime());
        order.setRefundTime(new Date());
        order.setRefundReason(orderParam.getRefundRemark());
        order.setRefundPoint(listOrder.get(0).getPoint());
        order.setUpTime(new Date());
        updateOrderStatusByNo(order, call);

        // 使用优惠券，有可能支付金额是0，这时退款不需要和支付网关交互
        if (listOrder.get(0).getPayMoney() > 0) {
            OrderPayLog orderPayLog = new OrderPayLog();
            orderPayLog.setAmount(-listOrder.get(0).getPayMoney());
            orderPayLog.setType(2);// 支出
            orderPayLog.setPoint(listOrder.get(0).getPoint());
            orderPayLog.setClientType(1);
            orderPayLog.setCreateTime(new Date());
            orderPayLog.setTrandNo(StringUtil.getCurrentAndRamobe("L"));
            orderPayLog.setReferenceid("");
            orderPayLog.setRemark(orderParam.getRefundRemark());
            orderPayLog.setStatus(1);// 状态 1初始化，2成功，3失败
            orderPayLog.setUpTime(new Date());
            orderPayLog.setOrderId(listOrder.get(0).getId());
            orderPayLogMapper.insertSelective(orderPayLog);
        }

        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_6, PayStatusEnum.PAY_STATUS_6.getDesc(),
                "申请退款操作", 0, ViewStatusEnum.VIEW_STATUS_REFUNDING.getCode());

        // 后台操作记录操作日志
        if (orderParam.getPlateForm() != null
                && (orderParam.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || orderParam
                        .getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
            MOperateLogParam paramlog = new MOperateLogParam();
            paramlog.setOperateType(OperateLogEnum.REFUNDING_OP.getOperateType());
            paramlog.setOperateUserid(orderParam.getOperateUserid());
            paramlog.setOperateUsername(orderParam.getOperateUsername());
            paramlog.setOrderCode(orderParam.getOrderNo());
            paramlog.setPlateForm(orderParam.getPlateForm());
            paramlog.setRemark(OperateLogEnum.REFUNDING_OP.getOperateName() + ":"
                    + StringUtils.trimToEmpty(orderParam.getRefundRemark()));
            operateLogService.saveOperateLog(paramlog);
        }

        return output;
    }

    @Transactional
    public ResultVo<Object> refunddealWithOrder(final NotifyReturn notifyReturn) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        if (notifyReturn.getCode().equals("0000")) {
            List<Order> listOrder = mallOrderMapper.getOrderByNo(notifyReturn.getOrderNo());
            if (CollectionUtils.isEmpty(listOrder)) {
                output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                output.setResultMsg("订单查询失败,获取不到订单");
                return output;
            }
            if (listOrder.get(0).getPayStatus().equals(7)) {
                return output;
            }
            // 构造sql的过滤语句
            CallMethod<Order> call = new CallMethod<Order>() {
                @Override
                void call(Criteria criteria, Order order) throws Exception {
                    invoke(criteria, "andOrderNoEqualTo", notifyReturn.getOrderNo());
                }
            };
            Order order = new Order();
            order.setPayStatus(7);// 客服同意申请退款==>已退款
            updateOrderStatusByNo(order, call);

            // 回退积分

        } else {// 如果调用网关失败，订单状态还是审核中
            output.setResultCode(getClass(), MsgCode.GATEWAY_ERROR.getMsgCode());
            output.setResultMsg(MsgCode.GATEWAY_ERROR.getMessage());
            return output;
        }
        return output;
    }


    public void updateOrderStatusByNo(Order order, CallMethod<Order> call) throws Exception {
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


    private void updatLogisticsNoByNo(MLogistics logistics, CallLogisticMethod<MLogistics> call)
            throws Exception {
        MLogisticsExample example = new MLogisticsExample();
        com.plateno.booking.internal.base.pojo.MLogisticsExample.Criteria criteria =
                example.createCriteria();
        call.call(criteria, logistics);
        try {
            mLogisticsMapper.updateByExampleSelective(logistics, example);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.sysErrorLoggerError("更新数据库失败", e);
        }
    }


    // 通过反射获取方法
    private void invoke(Object criteria, String methodName, Object obj) throws Exception {
        if (obj == null || "".equals(obj)) {
            return;
        }
        Method method = criteria.getClass().getDeclaredMethod(methodName, obj.getClass());
        method.invoke(criteria, obj);
    }


    abstract class CallMethod<T> {
        abstract void call(Criteria criteria, T t) throws Exception;
    }

    abstract class CallLogisticMethod<T> {
        abstract void call(
                com.plateno.booking.internal.base.pojo.MLogisticsExample.Criteria criteria, T t)
                throws Exception;
    }



    public Integer updateOrderStatusByNo(Order order, String orderNo) throws Exception {
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
     * 
     * @param order
     * @param orderNo
     * @param oldStatus
     * @return
     * @throws Exception
     */
    public Integer updateOrderStatusByNo(Order order, String orderNo, List<Integer> oldStatus)
            throws Exception {
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


    public ResultVo<Object> updateOrderStatus(ModifyOrderParams modifyOrderParams)
            throws OrderException, Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(modifyOrderParams.getOrderNo(),
                        modifyOrderParams.getMemberId(), modifyOrderParams.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        Order order = listOrder.get(0);
        // orderValidate.checkModifyOrder(order, output);

        order.setPayStatus(modifyOrderParams.getNewStatus());
        order.setUpTime(new Date());


        if (mallOrderMapper.updateByPrimaryKeySelective(order) > 0)
            orderLogService.saveGSOrderLog(modifyOrderParams.getOrderNo(),
                    modifyOrderParams.getNewStatus(), "更新订单状态", "更新订单状态", 0, 0);

        return output;
    }

    public ResultVo<Object> getPruSellAmountByPreDay(final Integer days) throws Exception {

        ResultVo<Object> output = new ResultVo<Object>();
        List<ProdSellAmountData> listPro = mallOrderMapper.getPruSellAmountByPreDay(days);
        if (CollectionUtils.isEmpty(listPro)) {
            return output;
        }
        output.setData(listPro);
        return output;
    }


    /**
     * 确认收货
     * 
     * @param order
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleReceiveGoods(String orderNo) throws Exception {

        logger.info(String.format("job 已发货-->已完成, orderNo:%s", orderNo));

        // 更新订单状态
        Order o = new Order();
        o.setPayStatus(BookingResultCodeContants.PAY_STATUS_5);
        o.setUpTime(new Date());
        List<Integer> list = new ArrayList<>(1);
        list.add(BookingResultCodeContants.PAY_STATUS_4);
        int row = this.updateOrderStatusByNo(o, orderNo, list);
        // 订单已经处理
        if (row < 1) {
            logger.info("job 已发货-->已完成,订单已经处理, orderNo：" + orderNo);
            return;
        }

        orderLogService.saveGSOrderLog(orderNo, BookingConstants.PAY_STATUS_5, "已完成", "超时确定收货", 0,
                ViewStatusEnum.VIEW_STATUS_COMPLETE.getCode(), "扫单job维护");
    }


    /**
     * 退款处理
     * 
     * @param order
     * @throws Exception
     */
    @Transactional(rollbackFor = OrderException.class)
    public void handleGateWayefund(Order order) throws Exception {

        String orderNo = order.getOrderNo();

        // 获取记录并上锁，防止并发
        order = mallOrderMapper.getByOrderNoForUpdate(orderNo);

        if (order == null || order.getPayStatus() != PayStatusEnum.PAY_STATUS_10.getPayStatus()) {
            logger.info("退款确认，订单已经处理， orderNo:{}, payStatus:{}", orderNo,
                    order != null ? order.getPayStatus() + "" : "");
            return;
        }

        logger.info(String.format("退款中订单处理开始, orderNo:%s", order.getOrderNo()));

        OrderPayLogExample example = new OrderPayLogExample();
        example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(2)
                .andStatusEqualTo(1);
        List<OrderPayLog> listpayLog = orderPayLogMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(listpayLog))
            return;

        for (OrderPayLog orderPayLog : listpayLog) {

            if (StringUtils.isNotBlank(orderPayLog.getReferenceid())) {
                // 获取网关的订单状态
                RefundQueryResponse response =
                        paymentService.refundOrderQuery(orderPayLog.getTrandNo());
                logger.info(String.format("orderNo:%s, 查询退款状态，返回：%s", order.getOrderNo(),
                        JsonUtils.toJsonString(response)));
                if (response == null || StringUtils.isBlank(response.getCode())) {
                    logger.error("查询支付网关订单失败, trandNo:" + orderPayLog.getTrandNo());
                    return;
                }
                if (response.getCode().equals(PayGateCode.HADNLING)
                        || response.getCode().equals(PayGateCode.PAY_HADNLING)) {
                    logger.error(String.format("退款支付网关订单支付中, trandNo:%s, code:%s",
                            orderPayLog.getTrandNo(), response.getCode()));
                    orderPayLog.setStatus(BookingConstants.BILL_LOG_FAIL);
                    orderPayLog.setUpTime(new Date());
                    orderPayLog.setRemark(String.format("退款支付网关订单支付中, trandNo:%s, code:%s",
                            orderPayLog.getTrandNo(), response.getCode()));
                    orderPayLogMapper.updateByPrimaryKeySelective(orderPayLog);
                    return;
                }
            } else {
                CashierRefundQueryReq cashierRefundQueryReq = new CashierRefundQueryReq();
                cashierRefundQueryReq.setRefundTradeNo(orderPayLog.getTrandNo());
                cashierRefundQueryReq.setUpdatePayStatusFlag(1);
                CashierRefundQueryResponse cashierRefundQueryResponse =
                        cashierDeskService.refundQuery(cashierRefundQueryReq);
                logger.info("orderNo:{},refundTraNo:{}, 查询退款状态，返回：{}", order.getOrderNo(),
                        orderPayLog.getTrandNo(),
                        JsonUtils.toJsonString(cashierRefundQueryResponse));

                if (cashierRefundQueryResponse == null
                        || cashierRefundQueryResponse.getMsgCode() != CashierDeskConstant.SUCCESS_MSG_CODE) {
                    logger.warn("查询支付网关订单失败,tranNo:{}", orderPayLog.getTrandNo());
                    // 明确退款失败则记录失败，其他情况继续轮询,0200退款失败
                    if (null != cashierRefundQueryResponse.getResult()
                            && cashierRefundQueryResponse.getResult().getCode().equals("0020")) {
                        String remark = "查询支付网关订单失败,";
                        if (null != cashierRefundQueryResponse
                                && null != cashierRefundQueryResponse.getResult())
                            remark += cashierRefundQueryResponse.getResult().toString();
                        orderPayLog.setStatus(BookingConstants.BILL_LOG_FAIL);
                        orderPayLog.setUpTime(new Date());
                        orderPayLog.setRemark(remark);
                        orderPayLogMapper.updateByPrimaryKeySelective(orderPayLog);

                    }
                    return;

                }
            }

            example = new OrderPayLogExample();
            example.createCriteria().andIdEqualTo(orderPayLog.getId());
            logger.info(String.format("orderNo:%s, 退款成功", order.getOrderNo()));
            OrderPayLog record = new OrderPayLog();
            record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
            record.setUpTime(new Date());
            orderPayLogMapper.updateByExampleSelective(record, example);
        }
        orderRefundActorService.doSuccessOrderRefundActor(order);

    }

    /**
     * 返回优惠券
     * 
     * @param orderNo
     */
    private ResultVo<String> returnCoupon(String orderNo, Integer memberId) {

        logger.info("退还优惠券, orderNo:{}", orderNo);

        MOrderCouponSearchVO svo = new MOrderCouponSearchVO();
        svo.setOrderNo(orderNo);
        // 查询优惠券信息
        List<MOrderCouponPO> couponList = mOrderCouponMapper.list(svo);

        if (couponList.size() <= 0) {
            logger.error("订单orderNo:{}, 找不到优惠券的使用信息", orderNo);
            return new ResultVo<String>(ResultCode.FAILURE, null, "查询优惠券信息失败");
        } else {

            MOrderCouponPO mOrderCouponPO = couponList.get(0);

            CancelParam param = new CancelParam();
            param.setCouponId(mOrderCouponPO.getCouponId());
            param.setMebId(memberId);

            ResultVo<CancelResponse> cancelCouponResult = couponService.cancelCoupon(param);

            if (!cancelCouponResult.success()) {
                logger.error("退还优惠券失败，orderNo:{}, memberId:{}, couponId:{}, result:{}", orderNo,
                        memberId, mOrderCouponPO.getCouponId(), cancelCouponResult);
                LogUtils.DISPERSED_ERROR_LOGGER.error(
                        "退还优惠券失败，orderNo:{}, memberId:{}, couponId:{}, result:{}", orderNo,
                        memberId, mOrderCouponPO.getCouponId(), cancelCouponResult);

                return new ResultVo<String>(ResultCode.FAILURE, null, "返回优惠券失败，"
                        + cancelCouponResult.getResultMsg());
            }

            return new ResultVo<String>(ResultCode.SUCCESS);
        }
    }

    /**
     * 获取订单的商品信息
     * 
     * @param orderNo
     * @return
     */
    public OrderProduct getProductByOrderNo(String orderNo) {
        OrderProductExample orderProductExample = new OrderProductExample();
        orderProductExample.createCriteria().andOrderNoEqualTo(orderNo);
        List<OrderProduct> productOrderList =
                orderProductMapper.selectByExample(orderProductExample);
        if (CollectionUtils.isEmpty(productOrderList)) {
            return null;
        }

        return productOrderList.get(0);
    }

    /**
     * 支付中处理 过期，迁移到PayService.handlePaying方法
     * 
     * @param order
     * @throws Exception
     */
    @Deprecated
    @Transactional(rollbackFor = OrderException.class)
    public void handlePaying(Order order) throws Exception {

        String orderNo = order.getOrderNo();

        // 获取记录并上锁，防止并发
        order = mallOrderMapper.getByOrderNoForUpdate(orderNo);

        if (order == null || order.getPayStatus() != PayStatusEnum.PAY_STATUS_11.getPayStatus()) {
            logger.info("支付确认，订单已经处理， orderNo:{}, payStatus:{}", orderNo,
                    order != null ? order.getPayStatus() + "" : "");
            return;
        }

        /*
         * if(!validate(order,BookingConstants.PAY_STATUS_11)) return ;
         */

        logger.info(String.format("支付中订单处理开始, orderNo:%s", order.getOrderNo()));

        OrderPayLogExample example = new OrderPayLogExample();
        example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(1)
                .andStatusEqualTo(1);
        List<OrderPayLog> listpayLog = orderPayLogMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(listpayLog)) {
            logger.error("订单状态异常, 订单状态支付中，但是找不到支付流水, orderNo:" + order.getOrderNo());
            return;
        }

        boolean success = false;
        for (OrderPayLog orderPayLog : listpayLog) {

            // 获取网关的订单状态
            PayQueryResponse response = paymentService.payOrderQuery(orderPayLog.getTrandNo());

            logger.info("orderNo:{}, 查询支付网关支付状态:{}", order.getOrderNo(),
                    JsonUtils.toJsonString(response));

            if (response == null || StringUtils.isBlank(response.getCode())) {
                logger.error("查询支付网关订单失败, trandNo:" + orderPayLog.getTrandNo());
                return;
            }

            if (response.getCode().equals(PayGateCode.HADNLING)
                    || response.getCode().equals(PayGateCode.PAY_HADNLING)
                    || response.getCode().equals(PayGateCode.UNKNOWN_STATUS)) {
                logger.error(String.format("支付网关订单不是最终状态, trandNo:%s, code:%s",
                        orderPayLog.getTrandNo(), response.getCode()));
                return;
            }

            example = new OrderPayLogExample();
            example.createCriteria().andIdEqualTo(orderPayLog.getId());

            if (response.getCode().equals(BookingConstants.GATEWAY_PAY_SUCCESS_CODE)) {

                // 判断金额是否相等，防止支付时篡改
                if (response.getOrderAmount() == null
                        || !response.getOrderAmount().equals(orderPayLog.getAmount())) {
                    logger.error("orderNo:{}, 订单金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}",
                            orderPayLog.getTrandNo(), orderPayLog.getAmount(),
                            response.getOrderAmount());
                    return;
                }

                logger.info(String.format("orderNo:%s, 支付成功", order.getOrderNo()));
                // 更新支付流水状态(success == 2)
                OrderPayLog record = new OrderPayLog();
                record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
                record.setRemark("支付成功");
                record.setUpTime(new Date());
                record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
                orderPayLogMapper.updateByExampleSelective(record, example);

                success = true;
            } else {
                logger.info(String.format("orderNo:%s, 支付失败", order.getOrderNo()));
                // 更新支付流水状态(fail == 3)
                OrderPayLog record = new OrderPayLog();
                record.setStatus(BookingConstants.BILL_LOG_FAIL);
                record.setUpTime(new Date());
                record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
                record.setRemark(String.format("支付失败:%s", response.getMessage()));
                orderPayLogMapper.updateByExampleSelective(record, example);
            }
        }

        Order record = new Order();
        if (success) {
            record.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
            orderLogService.saveGSOrderLog(order.getOrderNo(),
                    BookingResultCodeContants.PAY_STATUS_3, "网关支付成功", "网关支付成功", 0,
                    ViewStatusEnum.VIEW_STATUS_WATIDELIVER.getCode(), "扫单job维护");
        } else {
            record.setPayStatus(BookingResultCodeContants.PAY_STATUS_1);
            record.setPayType(0); // 支付方式设置为未支付
            orderLogService.saveGSOrderLog(order.getOrderNo(), BookingConstants.PAY_STATUS_1,
                    "网关支付失败", "网关支付失败", 0, ViewStatusEnum.VIEW_STATUS_PAYFAIL.getCode(), "扫单job维护");
        }
        // 更新账单状态
        this.updateOrderStatusByNo(record, order.getOrderNo());
    }



    /**
     * 查询订单已经购买的数量（只要有支付成功，就算是退款也算是已经购买的数量了）
     * 
     * @param memberId 会员ID
     * @param productId 产品ID
     * @return
     */
    public int queryUserProductSum(int memberId, int productId) {
        return orderProductMapper.queryUserProductSum(memberId, productId);
    }

    /**
     * 查询库存已售数量
     * 
     * @param skuId
     * @return
     */
    public ResultVo<Integer> querySkuSoldNum(Integer skuId) {
        int num = orderProductMapper.querySkuSoldNum(skuId);
        return new ResultVo<Integer>(ResultCode.SUCCESS, num);
    }

}
