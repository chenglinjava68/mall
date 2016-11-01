package com.plateno.booking.internal.bean.response.ycf;

import org.apache.commons.lang.StringUtils;

/**
 * 要出发goodsId转换
 * @author yi.wang
 * @date 2016年6月14日上午9:55:18
 * @version 1.0
 * @Description
 */
public class YcfGoodsIdUtil {

	/**
	 * 加密
	 * @param id
	 * @return
	 */
	public static String enc(String id) {
		String replace = StringUtils.replace(id, "_", "");
		int index = id.indexOf("_");
		if (index > 0) {
			return replace + frontCompWithZore(index, 2);
		}
		return null;
	}

	/**
	 * 解密
	 * @param id
	 * @return
	 */
	public static String dec(String id) {
		String right = StringUtils.right(id, 2);
		String left = StringUtils.left(id, id.length() - 2);
		if (StringUtils.isNotBlank(right) && StringUtils.isNotBlank(left)) {
			StringBuffer stringBuffer = new StringBuffer(left);
			return stringBuffer.insert(Integer.valueOf(right), "_").toString();
		}
		return null;
	}

	/**
	 * 将原数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 * @param sourceDate
	 * @param formatLength
	 * @return 重组后的数据
	 */
	private static String frontCompWithZore(int sourceDate, int formatLength) {
		//0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
		String newString = String.format("%0" + formatLength + "d", sourceDate);
		return newString;
	}

	public static void main(String[] args) {
		String str = "68567676767665665_4894206576541";
		String enc = enc(str);
		System.out.println(enc);
		System.out.println(dec(enc));
		System.out.println(str);
	}
}
