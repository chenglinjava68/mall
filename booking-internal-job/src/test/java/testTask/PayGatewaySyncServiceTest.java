package testTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.job.order.abnormalSweepJob.service.PayGatewaySyncService;
import com.plateno.booking.internal.service.order.MOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PayGatewaySyncServiceTest {

	@Autowired
	public PayGatewaySyncService service;
	
	@Autowired
	private MOrderService service2;

	@Test
	public void testHandlePaying() throws Exception {
		
		Order order = new Order();
		order.setOrderNo("O1478830070265633486");
//		service2.handlePaying(order);

	}
}
