package testTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.cashierdesk.CashierDeskService;
import com.plateno.booking.internal.cashierdesk.vo.CashierRefundQueryReq;
import com.plateno.booking.internal.cashierdesk.vo.PayQueryReq;
import com.plateno.booking.internal.cashierdesk.vo.RefundOrderReq;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestCashierDeskService {
    @Autowired
    private CashierDeskService cashierDeskService;
    
    @Test
    public void testPayQuery(){
        PayQueryReq req = new PayQueryReq();
        req.setTradeNo("L1486456181447341843");
        req.setUpdatePayStatusFlag(1);
        cashierDeskService.payQuery(req);
    }
    
    @Test
    public void testRefundOrder(){
        RefundOrderReq req = new RefundOrderReq();
        req.setAmount(6);
        req.setMemberId(135944358);
        req.setRefundOrderNo("O1486456177539718741");
        req.setRefundTradeNo("L1486456222418361347");
        req.setTradeNo("L1486456181447341846");
        cashierDeskService.refundOrder(req);
    }
    
    @Test
    public void testRefundQuery(){
        CashierRefundQueryReq req = new CashierRefundQueryReq();
        req.setRefundTradeNo("L1486527054500868949");
        req.setUpdatePayStatusFlag(1);
        cashierDeskService.refundQuery(req);
    }
    
    
}
