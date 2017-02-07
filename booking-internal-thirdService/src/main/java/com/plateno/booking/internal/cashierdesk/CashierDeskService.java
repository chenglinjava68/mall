package com.plateno.booking.internal.cashierdesk;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.cashierdesk.vo.CashierBaseParam;
import com.plateno.booking.internal.cashierdesk.vo.RefundOrderReq;
import com.plateno.booking.internal.cashierdesk.vo.RefundOrderResponse;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.MD5Maker;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.util.CashierJaxrsJacksonJsonObjectMapper;

@Service
@ServiceErrorCode(BookingConstants.CODE_GATEWAY_ERROR)
public class CashierDeskService {

    protected final static Logger logger = LoggerFactory.getLogger(CashierDeskService.class);
    
    /**
     * 
    * @Title: refundOrder 
    * @Description: 网关退款操作
    * @param @param req
    * @param @return
    * @param @throws IOException    
    * @return RefundOrderResponse    
    * @throws
     */
    public RefundOrderResponse refundOrder(RefundOrderReq req) throws IOException {
        req.setAppId("mall");// 商城为mall
        String postData = sign(req);
        logger.info("拉起支付网关退款，传参：{}",postData);
        String result = HttpUtils.httpPostRequest(Config.MERCHANT_CASHIER_PAY_URL + "/pay/refund", postData);
        logger.info("拉起支付网关退款，支付网关返回信息：{}",result);
        if (!StringUtils.isEmpty(result)) {
            RefundOrderResponse refundOrderResponse =
                    JsonUtils.jsonToObject(result, RefundOrderResponse.class);
            return refundOrderResponse;
        }
        return null;
    }

    
    
    /**
     * @param req 接口请求参数的对象
     * @param key 商户私有签名密钥
     * @return 接口请求参数带签名的JSON字符串
     * @throws PlatenoException
     * @throws Exception
     */
    public String sign(CashierBaseParam req) throws IOException {
        req.setSignData(Config.MERCHANT_PAY_KEY);
        CashierJaxrsJacksonJsonObjectMapper jacksonMapper = new CashierJaxrsJacksonJsonObjectMapper();
        String signString = jacksonMapper.writeValueAsString(req);
        req.setSignData(MD5Maker.getMD5(signString));

        return jacksonMapper.writeValueAsString(req);

    }

}
