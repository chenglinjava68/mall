/*package com.plateno.testthird;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.request.ycf.Vocher;
import com.plateno.booking.internal.bean.request.ycf.YcfOrderStatusRequest;
import com.plateno.booking.internal.service.order.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestYaoChuFa {
	
	@Autowired
	private OrderService orderService;
	
	@Test
	public void test(){
		YcfOrderStatusRequest request = new YcfOrderStatusRequest();
		request.setOrderStatus(20);
		request.setPartnerOrderId("O1469588763989248");
		request.setRemark("");
		List<Vocher> vochers = new ArrayList<>();
		Vocher vocher = new Vocher();
		vocher.setVocherNo("12345678");
		vocher.setVocherUrl("http://ycf.com/123");
		vochers.add(vocher);
		request.setVochers(vochers);
		orderService.modifOrderStatus(request);
	}

}
*/