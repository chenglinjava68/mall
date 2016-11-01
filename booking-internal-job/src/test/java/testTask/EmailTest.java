package testTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.email.MailService;
import com.plateno.booking.internal.email.model.EmailObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EmailTest {

	@Autowired
	public MailService mailService;

	@Test
	public void testx() throws IOException, InterruptedException {

		EmailObject eo = new EmailObject();
		eo.setSendTo(new String[] { "2412903432@qq.com" });
		eo.setSubject("异常订单邮件推送");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", "异常订单邮件推送");
		eo.setContentMap(map);

		Resource resource = new ClassPathResource("/email/mailTemplate.ftl");
		File f = resource.getFile();
		eo.addAttachment("故障订单描述说明", f);

		mailService.sendMimeMailAsyn(eo, "mailTemplate.ftl");

		Thread.sleep(10 * 1000);
	}
}
