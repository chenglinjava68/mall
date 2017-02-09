package com.plateno.booking.internal.common.util.number;


import org.apache.log4j.Logger;

import com.plateno.booking.internal.base.model.BaseNotifyVo;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.interceptor.adam.common.util.CashierJaxrsJacksonJsonObjectMapper;

public class CashierUtil {
    
    private static Logger log = Logger.getLogger(CashierUtil.class);
    
    /**
     * 
    * @Title: checkSign 
    * @Description: 签名验证方法
    * @param @param baseNotifyVo
    * @param @return
    * @param @throws IOException    
    * @return boolean    
    * @throws
     */
    public static boolean validateSign(BaseNotifyVo baseNotifyVo){
        try{
            String oldSign = baseNotifyVo.getSignData();
            baseNotifyVo.setSignData(Config.MERCHANT_PAY_KEY);
            CashierJaxrsJacksonJsonObjectMapper jacksonMapper = new CashierJaxrsJacksonJsonObjectMapper();
            String signString = jacksonMapper.writeValueAsString(baseNotifyVo);
            if(MD5Maker.getMD5(signString).equals(oldSign))
                return true;
        }catch(Exception e){
            log.error("验证签名失败",e);
        }
        return false;
    }
    
    
}
