package com.plateno.booking.internal.service.log;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.GsOrderLogMapper;
import com.plateno.booking.internal.base.mapper.MOrderLogMapper;
import com.plateno.booking.internal.base.pojo.GsOrderLog;
import com.plateno.booking.internal.base.pojo.MOrderLogWithBLOBs;


/**
 * 订单异常日志
 * 
 * @author user
 *
 */
@Service("orderLogService")
public class OrderLogService {

    protected final Logger log = Logger.getLogger(OrderLogService.class);

    @Autowired
    private MOrderLogMapper mOrderLogMapper;
    @Autowired
    private GsOrderLogMapper gsOrderLogMapper;

    /**
     * 保存同程创建订单日志 异步保存
     */
    public void saveTCCreateOrderLog(final String exception, final String request,
            final String response, final Integer channel) {
        try {
            MOrderLogWithBLOBs orderLog = new MOrderLogWithBLOBs();
            orderLog.setInfo("创建订单日志");
            if (exception != null) {
                int len = exception.length() > 20000 ? 20000 : exception.length();
                orderLog.setException(exception.substring(0, len));
            }
            orderLog.setRequest(request);
            orderLog.setResponse(response);
            orderLog.setInterfaceName("CreateOrderService");
            orderLog.setChannel(channel);
            orderLog.setUpTime(new Date());
            mOrderLogMapper.insert(orderLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format("saveTCCreateOrderLog exception[%s]", e));
        }
    }

    public void saveGSOrderLog(String tradeNo, Integer orderStatus, String order_desc,
            String remark, Integer channel, Integer viewStatus) {
        try {
            GsOrderLog orderLog = new GsOrderLog();
            orderLog.setChannel(channel);
            orderLog.setCreateTime(new Date());
            orderLog.setRemark(remark);
            orderLog.setStatus(orderStatus);
            orderLog.setStatusDesc(order_desc);
            orderLog.setTradeNo(tradeNo);
            orderLog.setViewStatus(viewStatus);
            orderLog.setClienttype(2);
            gsOrderLogMapper.insert(orderLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format("saveGSOrderLog exception[%s]", e));
        }
    }


    public void saveGSOrderLogWithOrderSubNo(String tradeNo, Integer status, String statusDesc,
            String remark, Integer channel, Integer viewStatus, String orderSubNo) {
        try {
            GsOrderLog orderLog = new GsOrderLog();
            orderLog.setChannel(channel);
            orderLog.setCreateTime(new Date());
            orderLog.setRemark(remark);
            orderLog.setStatus(status);
            orderLog.setStatusDesc(statusDesc);
            orderLog.setTradeNo(tradeNo);
            orderLog.setViewStatus(viewStatus);
            orderLog.setOrderSubNo(orderSubNo);
            orderLog.setClienttype(2);
            gsOrderLogMapper.insert(orderLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format("saveGSOrderLog exception[%s]", e));
        }
    }

    public void saveGSOrderLog(String tradeNo, Integer orderStatus, String order_desc,
            String remark, Integer channel, Integer viewStatus, String description) {
        try {
            GsOrderLog orderLog = new GsOrderLog();
            orderLog.setChannel(channel);
            orderLog.setCreateTime(new Date());
            orderLog.setRemark(remark);
            orderLog.setStatus(orderStatus);
            orderLog.setStatusDesc(order_desc);
            orderLog.setTradeNo(tradeNo);
            orderLog.setViewStatus(viewStatus);
            orderLog.setClienttype(2);
            orderLog.setDescription(description);

            gsOrderLogMapper.insert(orderLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format("saveGSOrderLog exception[%s]", e));
        }
    }

}
