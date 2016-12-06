package com.plateno.testservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.email.model.DeliverGoodContent;
import com.plateno.booking.internal.email.model.RefundSuccessContent;
import com.plateno.booking.internal.email.service.PhoneMsgService;

/**
 * 
 * @author mogt
 * @date 2016年11月1日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PhoneMsgServiceTest {
	
	@Autowired
	private PhoneMsgService phoneMsgService;
	
	@Autowired
	private TaskExecutor taskExecutor;

	@Test
	public void testSendPhoneMessage() throws OrderException, Exception{
		RefundSuccessContent content = new RefundSuccessContent();
		content.setObjectNo("213333333");
		content.setMoney("100.00");
		content.setName("雨伞");
		content.setOrderCode("323232323");
		//content.setJf("50");
		phoneMsgService.sendPhoneMessage("13533048661", Config.SMS_SERVICE_TEMPLATE_EIGHT, content);
		
		/*DeliverGoodContent content2 = new DeliverGoodContent();
		content2.setObjectNo("213333333");
		content2.setOrderCode("333333333");
		content2.setName("雨伞");
		content2.setExpress("韵达");
		content2.setExpressCode("32333333333333333");
		phoneMsgService.sendPhoneMessageAsync("13533048661", Config.SMS_SERVICE_TEMPLATE_SEVEN, content2);*/
		
		Thread.sleep(7000);
		
	}
}
