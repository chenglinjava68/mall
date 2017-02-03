package com.plateno.booking.internal.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
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
        long start = System.currentTimeMillis();
        log.info("支付网关支付结果回调开始时间：" + start);
        try {
            PayNotifyVo payNotifyVo = resolvePayNotifyParam(request);
            log.info("支付网关支付结果回调,请求入参:" + JsonUtils.toJsonString(payNotifyVo));
            if(StringUtils.isBlank(payNotifyVo.getMerchantOrderNo())) {
                log.error("支付网关支付结果回调,订单号为空");
                return "FAILURE";
            }
            //支付网关回调订单处理
            payNotifyService.payNotify(payNotifyVo);
            return "SUCCESS";
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            log.error("支付网关支付结果回调："+ e);
            return "FAILURE";
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
        long start = System.currentTimeMillis();
        log.info("支付网关退款通知回调开始时间：" + start);
        try {
            RefundNotifyVo refundNotifyVo = resolveRefundNotifyParam(request);
            log.info("支付网关退款通知回调,请求入参:" + JsonUtils.toJsonString(refundNotifyVo));
            if(StringUtils.isBlank(refundNotifyVo.getMerchantOrderNo())) {
                log.error("支付网关退款通知回调,订单号为空");
                return "FAILURE";
            }
            payNotifyService.payNotifyRefund(refundNotifyVo);
            return "SUCCESS";
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            log.error("异常日志："+ e);
            return "FAILURE";
        }
    }
    

    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
    

    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
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
            String jsonString = getRequestPostStr(request);
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
            String jsonString = getRequestPostStr(request);
            refundNotifyVo = JsonUtils.jsonToObject(jsonString, RefundNotifyVo.class);
        }catch (IOException e) {
            log.error("序列化失败",e);
        }
        return refundNotifyVo;
    }
    
}
