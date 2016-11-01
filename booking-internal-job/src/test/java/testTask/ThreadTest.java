/*package testTask;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.request.common.EmailExecl;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.poi.Data2Execl;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.email.MailService;
import com.plateno.booking.internal.email.model.EmailObject;
import com.plateno.booking.internal.job.order.pushJob.callback.RunnableCallBack;
import com.plateno.booking.internal.job.order.pushJob.runnable.MyRunnanleClass;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ThreadTest {
	
	@Autowired
	public TaskExecutor taskExecutor;
	@Autowired
	public RedisUtils redisUtils;
	@Autowired
	public MailService mailService;
	
	private static List<EmailExecl> mailList= new CopyOnWriteArrayList<EmailExecl>();
	
	@Test
	public void test() throws Exception{
		List<Integer> list = BookingConstants.PUSH_ORDER_STATUS;
		Set<Integer> set = new HashSet<Integer>();
		for(Integer key : list){
			Set<String> keys =  redisUtils.smembers(BookingConstants.ORDER_STATUS+key);
			if (key != null && keys.size() > 0) {
				set.add(key);
			}
		}
		
		Iterator<Integer> it = set.iterator();
		CountDownLatch countDownLatch = new CountDownLatch(set.size());
		while (it.hasNext()) {
			taskExecutor.execute(new MyRunnanleClass(it.next(),new RunnableCallBack<EmailExecl>() {
				@Override
				public void call(List<EmailExecl> emailExecls) throws Exception {
					mailList.addAll(emailExecls);
				}
			},countDownLatch));
		}
		
		countDownLatch.await();
		Data2Execl.saveWithDisks(mailList, "errorOrder");
		
		
		EmailObject eo = new EmailObject();
		String[] email = (String[]) Arrays.asList(Config.EMAIL_GROUP).toArray();
		eo.setSendTo(email);
		eo.setSubject("异常订单邮件推送");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", "异常订单邮件推送");
		eo.setContentMap(map);
		
		String time = DateUtil.dateToFormatStr(new Date(), DateUtil.YYYY_MM_DD);
		File f = new File(System.getProperty("user.dir")+"//send//errorOrder_"+time+".xls");
		
		Resource resource = new ClassPathResource("/email/mailTemplate.ftl");
		File f = resource.getFile();
		eo.addAttachment("故障订单描述说明.xls", f);

		mailService.sendMimeMailAsyn(eo, "mailTemplate.ftl");
		
		Thread.sleep(10 * 1000);
	}
}

*/