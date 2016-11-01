package com.plateno.booking.internal.member;

import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.www.webservice.service.userinterface.MemberProperty;
import com.plateno.www.webservice.service.userinterface.RAllMemberInfo;
import com.plateno.www.webservice.service.userinterface.UserInterfaceSoap;


@Service
public class MemberService {

	
	@Resource
	private UserInterfaceSoap userInterfaceSoap;
	@Autowired
	private RedisUtils redisUtils;
	
	
	
	/**
	 * 根据会员ID,获取会员信息
	 * 
	 * 
	 * @param memberId
	 * @param type  140 : 根据memberId查询openId
	 * @return
	 */
	public MemberProperty getMemberProperty(int memberId, int type){
		MemberProperty memberProperty = userInterfaceSoap.getMemberProperty(memberId, type);
		return memberProperty;
	}
	
	/**
	 * 根据手机号码查询会员信息
	 * 
	 * @param mobile
	 * @return
	 */
	public com.plateno.www.webservice.service.userinterface.MemberProperty getMemerByMobile(String mobile){
		return userInterfaceSoap.getMemberProperty2(Config.PROPERTY_TYPE_MOBILE, mobile);
	}
	
	/**
	 * 获取会员所有信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception 
	 */
	public RAllMemberInfo getAllMemberInfo(final Integer memberId) throws Exception{
		
		final String ALLMEMBERINFO = BookingConstants.MEMBER_INFO_ALL + memberId;
		
		final Callable<String> refreshSource = new Callable<String>() {
			public String call() throws Exception {
				RAllMemberInfo member = userInterfaceSoap.getAllMemberInfo(Integer.valueOf(memberId));
				return JsonUtils.toJsonString(member);
			}
		};
		
		String value = redisUtils.get(ALLMEMBERINFO, refreshSource, 60*10);
		RAllMemberInfo member = JsonUtils.jsonToObject(value, RAllMemberInfo.class);
		return member;
	}
	
}
