package com.plateno.booking.internal.common.util.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;


/**
 * 
 * 功能描述：图片与base64位编码互转
 * @author Joe
 */
public class ImgFileUtil {


	public static void main(String[] args) throws IOException {
		String imgStr = imageToBase64String("c://22.jpg");
		System.out.println(imgStr);
		base64StringToImage(imgStr, "c://11_new.jpg", "jpg");
	}

	public static String imageToBase64String(String imgPath) throws IOException {
		try {
			File f = new File(imgPath);
			BufferedImage bi;
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", baos);
			byte[] bytes = baos.toByteArray();

			return Base64.encodeBase64String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public static String imageToBase64String(BufferedImage bi) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, "png", baos);
		byte[] bytes = baos.toByteArray();
		return Base64.encodeBase64String(bytes);
	}

	public static boolean base64StringToImage(String base64String, String imgPath, String type) {
		try {
			byte[] bytes1 = Base64.decodeBase64(base64String);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			// BufferedImage bi1 = ImageIO.read(bais);
			System.setProperty("java.awt.headless", "true");
			File outPutFile = new File(imgPath);// 可以是jpg,png,gif格式
			// System.out.println(imgPath);
			// ImageIO.write(bi1, "jpg", outPutFile);//不管输出什么格式图片，此处不需改动
			java.awt.image.BufferedImage bufImg = ImageIO.read(bais);
			if (type.equalsIgnoreCase("png")) {
				bufImg = ImageUtil.zoomAlpha(bufImg, bufImg.getWidth(), bufImg.getHeight());
			} else {
				bufImg = ImageUtil.zoom(bufImg, bufImg.getWidth(), bufImg.getHeight());
			}
			System.out.println("type=" + type);
			ImageIO.write(bufImg, type, outPutFile);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
