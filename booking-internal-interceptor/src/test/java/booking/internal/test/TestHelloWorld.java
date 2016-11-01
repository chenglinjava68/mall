package booking.internal.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.service.chain.ServiceChain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:applicationContext.xml" })
public class TestHelloWorld {

	@Autowired
	private ServiceChain serviceChain;

	//@Test
	public void test() {
		String serviceEnum = "test";
		ResultVo<String> resultVo = new ResultVo<String>();
		resultVo.setData("hehe");
		serviceChain.doServer("aaaaaaaa", resultVo, serviceEnum);
	}

}
