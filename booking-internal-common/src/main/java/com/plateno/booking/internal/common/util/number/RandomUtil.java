package com.plateno.booking.internal.common.util.number;

import java.util.Random;

public class RandomUtil {
	
	private static Random random = new Random();

	/**
     * 根据类型产生随机字符
     * @param type
     * @return
     */
    public static String randomType(int type) {
        int index = 0;
        char result = 0;
        if (type == 0) { // 0-9:48-57
            index = random.nextInt(10);
            result = (char) (index + 48);
        }
        else if (type == 1) { // A-Z:65-90
            index = random.nextInt(26);
            result = (char) (index + 65);
        }
        else if (type == 2) { // a-z:97-123
            index = random.nextInt(26);
            result = (char) (index + 97);
        }
        return String.valueOf(result);
    }
    
    /**
     * 生成随机字符
     * @param length
     * @return
     */
    public static String random(int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++)
            buf.append(randomType(random.nextInt(3)));
        return buf.toString();
    }
    
    public static String randomNum(int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++)
            buf.append(randomType(0));
        return buf.toString();
    }
    
}
