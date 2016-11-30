package com.plateno.testservice;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.pojo.MLogisticsType;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.service.conf.MLogisticsTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MLogisticsTypeServiceTest {
	
	@Autowired
	private MLogisticsTypeService service;
	
	@Test
	public void testListAll() throws IOException {
		List<MLogisticsType> listAll = service.listAll();
		System.out.println(JsonUtils.toJsonString(listAll));
	}

}