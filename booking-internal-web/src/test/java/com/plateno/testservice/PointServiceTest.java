package com.plateno.testservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.member.PointService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PointServiceTest {
	
	@Autowired
	private PointService pointService;
	
	@Test
	public void testGetPoint() throws OrderException, Exception{
		int pointSum = pointService.getPointSum(181295316);
		System.out.println(pointSum);
		
	}
	
	@Test
	public void testMallAddPoint() throws OrderException, Exception{
		ValueBean vb=new ValueBean();
		vb.setPointvalue(55555);
		vb.setMebId(181295316);
		pointService.mallAddPoint(vb );
		
	}
}