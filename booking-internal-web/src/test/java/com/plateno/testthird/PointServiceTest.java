package com.plateno.testthird;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.member.PointService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PointServiceTest {
    @Autowired
    private PointService pointService;
    
    @Test
    public void getSum(){
        System.out.println(pointService.getPointSum(181295151 ));
    }
    
}
