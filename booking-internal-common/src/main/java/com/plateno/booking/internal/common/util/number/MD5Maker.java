package com.plateno.booking.internal.common.util.number;

import java.security.MessageDigest;

public class MD5Maker {
	/**
	 * 将传入的字符串生成为MD5摘要后返回给调用者
	 * @param inStr 传入的任意长度字符串
	 * @return 传入字符串的MD5摘要，长度为32个字符的摘要结果，字符均为0-9，A-F十六进制。如果传入的参数为null则返回也为null。
	 */
	public static String getMD5(String inStr) {
		if (inStr == null) {
			return null;
		} else {
			return MD5(inStr);
		}
	}
    private static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4',
                             '5', '6', '7', '8', '9',
                             'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            
            //获得密文
            byte[] md = mdInst.digest();
            
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
