package com.plateno.booking.internal.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.model.PayNotifyVo;
import com.plateno.booking.internal.base.model.RefundNotifyVo;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.service.order.PayNotifyService;

@RequestMapping("/cashier")
@RestController
public class PayNotifyController extends BaseController{

    private final static Logger log = Logger.getLogger(PayNotifyController.class);
    
    @Autowired
    private PayNotifyService payNotifyService;
    
    private final static String SUCCES = "SUCCESS";
    
    private final static String FAILURE = "FAILURE";
    
    /**
     * 
    * @Title: payNotifyUrl 
    * @Description: 支付结果回调通知
    * @param @param request
    * @param @return
    * @param @throws Exception
    * @param @throws OrderException    
    * @return String    
    * @throws
     */
    @RequestMapping(value = "/payNotifyUrl",method = RequestMethod.POST)
    public String payNotifyUrl(HttpServletRequest request) throws Exception, OrderException{
        try {
            PayNotifyVo payNotifyVo = resolvePayNotifyParam(request);
            log.info("支付网关支付结果回调,请求入参:" + JsonUtils.toJsonString(payNotifyVo));
            if(StringUtils.isBlank(payNotifyVo.getTradeNo())) {
                log.error("支付网关支付结果回调,订单号为空");
                return FAILURE;
            }
            //支付网关回调订单处理
            payNotifyService.payNotify(payNotifyVo);
            return SUCCES;
        } catch (Exception e) {
            log.error("支付网关支付结果回调异常："+ e);
            return FAILURE;
        }
    }
    
    /**
     * 
    * @Title: refundNotifyUrl 
    * @Description: 退款接口回调通知
    * @param @param request
    * @param @return
    * @param @throws Exception
    * @param @throws OrderException    
    * @return String    
    * @throws
     */
    @RequestMapping(value = "/refundNotifyUrl",method = RequestMethod.POST)
    public String refundNotifyUrl(HttpServletRequest request) throws Exception, OrderException{
        try {
            RefundNotifyVo refundNotifyVo = resolveRefundNotifyParam(request);
            log.info("支付网关退款通知回调,请求入参:" + JsonUtils.toJsonString(refundNotifyVo));
            if(StringUtils.isBlank(refundNotifyVo.getRefundTradeNo())) {
                log.error("支付网关退款通知回调,订单号为空");
                return FAILURE;
            }
            payNotifyService.payNotifyRefund(refundNotifyVo);
            return SUCCES;
        } catch (Exception e) {
            log.error("支付网关退款通知异常："+ e);
            return FAILURE;
        }
    }
    


    
    /**
     * 
    * @Title: resolvePayNotifyParam 
    * @Description: 解析支付回调通知对象
    * @param @param request
    * @param @return    
    * @return PayNotifyVo    
    * @throws
     */
    private PayNotifyVo resolvePayNotifyParam(HttpServletRequest request){
        PayNotifyVo notifyReturn = null;
        try {
            String jsonString = getRequestString(request);
            notifyReturn = JsonUtils.jsonToObject(jsonString, PayNotifyVo.class);
        } catch (IOException e) {
            log.error("序列化失败",e);
        }
        return notifyReturn;
    }
    
    /**
     * 
    * @Title: resolveRefundNotifyParam 
    * @Description: 解析退款对象
    * @param @param request
    * @param @return    
    * @return RefundNotifyVo    
    * @throws
     */
    private RefundNotifyVo resolveRefundNotifyParam(HttpServletRequest request){
        RefundNotifyVo refundNotifyVo = null;
        try{
            String jsonString = getRequestString(request);
            refundNotifyVo = JsonUtils.jsonToObject(jsonString, RefundNotifyVo.class);
        }catch (IOException e) {
            log.error("序列化失败",e);
        }
        return refundNotifyVo;
    }
    
}
