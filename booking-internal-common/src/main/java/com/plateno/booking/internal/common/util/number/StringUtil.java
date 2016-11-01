package com.plateno.booking.internal.common.util.number;

import java.util.Random;

public class StringUtil {

	/**
	 * @Description: 生成17位的数字,生成规则：'type'+时间戳+6位数随机数
	 * @param type
	 * @return String  
	 * @throws
	 * @author liulianyuan
	 * @date 2016年4月21日
	 */
	public static String getCurrentAndRamobe(String type) {
		String current = String.valueOf(System.currentTimeMillis());
		int max = 999999;
		int min = 100000;
		Random random = new Random();
		int randomNumber = random.nextInt(max) % (max - min + 1) + min;
		current = type.toUpperCase() + current + randomNumber;
		return current;
	}
}