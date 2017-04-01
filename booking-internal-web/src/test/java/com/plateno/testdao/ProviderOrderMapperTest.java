package com.plateno.testdao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.model.ProviderOrderParam;
import com.plateno.booking.internal.dao.mapper.ProviderOrderMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ProviderOrderMapperTest {

    @Autowired
    private ProviderOrderMapper mapper;
    
    @Test
    public void testQuery(){
        System.out.println(mapper.queryProviderOrder(new ProviderOrderParam()));
    }
    
    
}
