package com.plateno.booking.internal.service.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.model.BookingPayQueryVo;
import com.plateno.booking.internal.base.model.PayNotifyVo;
import com.plateno.booking.internal.base.model.RefundNotifyVo;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.common.util.bean.BeanUtils;

/**
 * 
 * @ClassName: PayNotifyService
 * @Description: 收银台通知处理
 * @author zhengchubin
 * @date 2017年1月23日 下午5:24:15
 *
 */
@Service
public class PayNotifyService {
    private final static Logger logger = LoggerFactory.getLogger(PayNotifyService.class);

    @Autowired
    private OrderPayLogMapper orderPayLogMapper;
    @Autowired
    private OrderMapper mallOrderMapper;

    @Autowired
    private OrderProductMapper orderProductMapper;

    @Autowired
    private OrderRefundActorService orderRefundActorService;

    @Autowired
    private PayService payService;
    
    /**
     * 支付网关回调，更新订单的状态
     * 
     * @param notifyReturn
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payNotify(PayNotifyVo payNotifyVo) throws Exception {

        OrderPayLog orderPayLog = orderPayLogMapper.getByTrandNo(payNotifyVo.getTradeNo());
        // 查询订单是否存在
        if (orderPayLog == null) {
            logger.warn("支付网关支付回调,获取不到对应的流水信息：" + payNotifyVo.getTradeNo());
            throw new RuntimeException("找不到支付流水信息");
        }
       
        BookingPayQueryVo bookingPayQueryVo = new BookingPayQueryVo();
        BeanUtils.copyProperties(bookingPayQueryVo, payNotifyVo);
        
        payService.doWithOrderPayLogAndOrder(orderPayLog, bookingPayQueryVo);
    }

    /**
     * 
     * @Title: payNotifyRefund
     * @Description: 退款回调通知处理
     * @param @param refundNotifyVo
     * @param @throws Exception
     * @return void
     * @throws
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payNotifyRefund(RefundNotifyVo refundNotifyVo) throws Exception {
        // 根据商户订单号，获取记录
        OrderPayLog orderPayLog = orderPayLogMapper.getByTrandNo(refundNotifyVo.getRefundTradeNo());
        Order order = mallOrderMapper.getByOrderIdForUpdate(orderPayLog.getOrderId());
        // 检查订单状态是否为退款中
        if (null == order) {
            logger.warn("订单不存在，订单退款回调通知失败，订单主键为:{},tranNo为：{}", orderPayLog.getOrderId(),
                    orderPayLog.getTrandNo());
            return;
        }
        if (order.getPayStatus() != PayStatusEnum.PAY_STATUS_10.getPayStatus()) {
            logger.warn("订单状态不为退款中，订单退款回调通知失败， orderNo:{}, payStatus:{}", order.getOrderNo(),
                    order.getPayStatus());
            return;
        }

        parseRefundNotify(refundNotifyVo, orderPayLog, order);
        logger.info(String.format("orderNo:%s, 收银台退款回调通知成功，进行后续操作", order.getOrderNo()));
        orderRefundActorService.doSuccessOrderRefundActor(order);


    }

    /**
     * 
     * @Title: parseRefundNotify
     * @Description: 解析退款回调通知数据
     * @param @param refundNotifyVo
     * @param @param orderPayLog
     * @param @param order
     * @param @return
     * @return boolean
     * @throws
     */
    private void parseRefundNotify(RefundNotifyVo refundNotifyVo, OrderPayLog orderPayLog,
            Order order) {
        // 更新支付流水状态(success == 2)
        orderPayLog.setStatus(BookingConstants.BILL_LOG_SUCCESS);
        // record.setRemark("退款成功");
        orderPayLog.setUpTime(new Date());
        orderPayLogMapper.updateByPrimaryKeySelective(orderPayLog);
        logger.info("order_pay_log,orderNo:{}, 退款成功", order.getOrderNo());
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



}
