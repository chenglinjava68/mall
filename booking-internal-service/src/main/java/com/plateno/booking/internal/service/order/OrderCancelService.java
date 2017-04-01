package com.plateno.booking.internal.service.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.OperateLogEnum;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.cashierdesk.CashierDeskService;
import com.plateno.booking.internal.cashierdesk.vo.CancelOrderReq;
import com.plateno.booking.internal.cashierdesk.vo.CashierCancelOrderResponse;
import com.plateno.booking.internal.cashierdesk.vo.CashierDeskConstant;
import com.plateno.booking.internal.common.util.redis.RedisLock;
import com.plateno.booking.internal.common.util.redis.RedisLock.Holder;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.log.OperateLogService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.validator.order.MOrderValidate;

@Service
public class OrderCancelService {

    protected final static Logger logger = LoggerFactory.getLogger(OrderCancelService.class);

    @Autowired
    private OrderMapper mallOrderMapper;

    @Autowired
    private MOrderValidate orderValidate;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private OrderStockService orderStockService;
    
    @Autowired
    private OrderSubService orderSubService;
    
    @Autowired
    private OrderRefundActorService orderRefundService;
    
    @Autowired
    private CashierDeskService cashierDeskService;
    
    @Autowired
    private OrderPayLogService orderPayLogService;
    
    /**
     * 更新订单状态(取消)
     * 
     * @param orderParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
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
     * 
     * @param orderParam
     * @return
     * @throws Exception
     */
    private ResultVo<Object> cancelOrder(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        //查询订单
        queryOrder(orderParam, output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }
        Order order = (Order) output.getData();
        int olderOrderStatus = order.getPayStatus();
        //查询订单是否可以取消
        orderValidate.checkCancelOrder(order, output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }
        //定时任务取消，判断是否到可取消时间
        checkCancelTime(orderParam, order,output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }
        String desc = (String) output.getData();
        
        updateOrderStatus(order, PayStatusEnum.PAY_STATUS_2.getPayStatus());
        orderSubService.updateToPayStatus(orderParam.getOrderNo(), PayStatusEnum.PAY_STATUS_2.getPayStatus());
        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                PayStatusEnum.PAY_STATUS_2.getPayStatus(), PayStatusEnum.PAY_STATUS_2.getDesc(), desc,
                0, ViewStatusEnum.VIEW_STATUS_CANNEL.getCode(), desc);
        //返还利益
        orderRefundService.returnBenefit(order);
        // 退还库存
        orderStockService.returnStock( order.getOrderNo());
        //调用支付取消接口，如果部分储值已支付，则可主动触发，调用返回
        payCancelOrder(order,olderOrderStatus);
        // 如果是后台操作，取消记录操作日志
        recordCancelLog(orderParam);
        return output;
    }

    /**
     * 
    * @Title: payCancelOrder 
    * @Description: 支付中的订单，需要查看流水，并主动通知收银台，发起部分退款，如储值已支付，第三方支付不成功等情况
    * @param @param order    
    * @return void    
    * @throws
     */
    private void payCancelOrder(Order order,int olderOrderStatus){
        if(PayStatusEnum.PAY_STATUS_11.getPayStatus() != olderOrderStatus)
            return;
        List<OrderPayLog> orderPayLogs = orderPayLogService.queryOrderPayLogInPayByOrderId(order.getId());
        for(OrderPayLog orderPayLog : orderPayLogs){
           //有储值支付的金额
            if(null != orderPayLog.getCurrencyDepositAmount() && orderPayLog.getCurrencyDepositAmount() > 0){
                CancelOrderReq req = new CancelOrderReq();
                req.setTradeNo(orderPayLog.getTrandNo());
                CashierCancelOrderResponse response = cashierDeskService.cancelOrder(req);
                if(null == response || CashierDeskConstant.SUCCESS_MSG_CODE.compareTo(response.getMsgCode()) != 0){
                    logger.warn("orderId:{},发起主动取消失败,req:{}，返回:{}",order.getId(),req.toString(),response.toString());
                }
            }
        }
    }
    
    /**
     * 
    * @Title: queryOrder 
    * @Description: 查询订单
    * @param @param orderParam
    * @param @param output    
    * @return void    
    * @throws
     */
    private void queryOrder(MOrderParam orderParam,ResultVo<Object> output){
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return;
        }
        output.setData(listOrder.get(0));
        return;
    }
    
    /**
     * 
    * @Title: checkCancelTime 
    * @Description: 检查是否到超时取消时间
    * @param @param orderParam
    * @param @param order
    * @param @return    
    * @return ResultVo<Object>    
    * @throws
     */
    private void checkCancelTime(MOrderParam orderParam,Order order,ResultVo<Object> output){
        String desc = "手动取消订单";
        // 判断取消类型
        if (orderParam.getType() != null && orderParam.getType() == 1) {
            desc = "超时取消订单";
            logger.info("超时取消订单，orderNo：{}",orderParam.getOrderNo());
            long now = new Date().getTime();
            long createTime = order.getCreateTime().getTime();
            // 29分钟，避免时间存在误差
            if (now - createTime <= 29 * 60 * 1000) {
                logger.info("orderNo:{}, 超期取消时间错误:{}",orderParam.getOrderNo(),
                        order.getCreateTime());
                output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                output.setResultMsg("超时取消时间未到达30分钟");
                return;
            }
        }
        output.setData(desc);
        return;
    }
    
    private void recordCancelLog(MOrderParam orderParam){
        if (orderParam.getPlateForm() != null
                && (orderParam.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || orderParam
                        .getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
            MOperateLogParam paramlog = new MOperateLogParam();
            paramlog.setOperateType(OperateLogEnum.CANCEL_ORDER.getOperateType());
            paramlog.setOperateUserid(orderParam.getOperateUserid());
            paramlog.setOperateUsername(orderParam.getOperateUsername());
            paramlog.setOrderCode(orderParam.getOrderNo());
            paramlog.setPlateForm(orderParam.getPlateForm());
            paramlog.setRemark(OperateLogEnum.CANCEL_ORDER.getOperateName());
            operateLogService.saveOperateLog(paramlog);
        }
    }
    
    private void updateOrderStatus(Order order,int payStatus){
        order.setPayStatus(payStatus);
        order.setUpTime(new Date());
        mallOrderMapper.updateByPrimaryKeySelective(order);
    }
    
}
