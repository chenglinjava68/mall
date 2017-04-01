package com.plateno.booking.internal.email.service;

import com.plateno.booking.internal.email.model.BaseContent;

/**
 * 短信发送
 * 
 * @author mogt
 * @date 2016年11月14日
 */
public interface PhoneMsgService {

    /**
     * 发送短信
     * 
     * @param phone 手机号码
     * @param templateId 模板ID
     * @param content 内容
     * @return
     */
    public void sendPhoneMessage(String phone, String templateId, BaseContent content);

    /**
     * 发送短信 异步
     * 
     * @param phone 手机号码
     * @param templateId 模板ID
     * @param content 内容
     * @return
     */
    public void sendPhoneMessageAsync(String phone, String templateId, BaseContent content);



}
