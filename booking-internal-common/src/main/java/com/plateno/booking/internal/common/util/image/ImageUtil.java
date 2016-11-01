package com.plateno.booking.internal.common.util.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 功能描述：图片帮助类
 * @author Joe
 */
public class ImageUtil {

	/**
	 * 
	 * 功能描述：缩放
	 * 
	 * @param srcImage
	 * @param width
	 * @param height
	 * @return 
	 * @author <a href="mailto:chenfenghai@oristartech.com">chenfenghai</a>
	 * @version
	 * @throws IOException 
	 * @since 程序版本号
	 * create on: 2012-8-17
	 *
	 */
	public static byte[] zoom(byte[] srcImage, String imageType, int width, int height) throws IOException{
		System.setProperty("java.awt.headless", "true");
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(srcImage));
		//缩放
		bufImg = zoom(bufImg, width, height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufImg, getImagePostfix(imageType), baos);
		return baos.toByteArray();
	}
	
	//用该方法缩放PNG格式图片时候，透明区域将不会变黑，改bug
	public static byte[] zoomAlpha(byte[] srcImage, String imageType, int width, int height) throws IOException{
		System.setProperty("java.awt.headless", "true");
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(srcImage));
		//缩放
		bufImg = zoomAlpha(bufImg, width, height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufImg, getImagePostfix(imageType), baos);
		return baos.toByteArray();
	}
	
	/**
	 * 
	 * 功能描述：缩放
	 * 
	 * @param srcImage
	 * @param width
	 * @param height
	 * @return 
	 * @author <a href="mailto:chenfenghai@oristartech.com">chenfenghai</a>
	 * @version
	 * @since 程序版本号
	 * create on: 2012-8-17
	 *
	 */
	public static BufferedImage zoom(BufferedImage srcImage, int width, int height){
		BufferedImage newBufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gs = newBufImg.createGraphics();
		gs.drawImage(srcImage, 0, 0, width, height, null);
		gs.dispose();
		newBufImg.flush();
		return newBufImg;
	}

	/**
	 * 
	 * 
	 * 功能描述：用该方法缩放PNG格式图片时候，透明区域将不会变黑，改bug
	 * 
	 * @param srcImage
	 * @param width
	 * @param height
	 * @return 
	 * @author <a href="mailto:chezuyuan@ceopen.cn">chezuyuan</a>
	 * @version
	 * @since 程序版本号
	 * create on: 2013-11-18
	 *
	 */
	public static BufferedImage zoomAlpha(BufferedImage srcImage, int width, int height){
		BufferedImage newBufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gs = newBufImg.createGraphics();
		gs.drawImage(srcImage, 0, 0, width, height, null);
		gs.dispose();
		newBufImg.flush();
		return newBufImg;
	}
	
	//获取图片类型
	public static String getImageType(String postfix){
		if(POSTFIX_GIF.equalsIgnoreCase(postfix)){
			return IMAGE_TYPE_GIF;
		}else if(POSTFIX_PNG.equalsIgnoreCase(postfix)){
			return IMAGE_TYPE_PNG;
		}else{
			return IMAGE_TYPE_JPEG;
		}
	}
	
	//获取图片后缀
	public static String getImagePostfix(String imageType){
		if(IMAGE_TYPE_GIF.equalsIgnoreCase(imageType)){
			return POSTFIX_GIF;
		}else if(IMAGE_TYPE_PNG.equalsIgnoreCase(imageType)){
			return POSTFIX_PNG;
		}else{
			return POSTFIX_JPG;
		}
	}
	
	public static final String POSTFIX_GIF = "gif";
	public static final String POSTFIX_JPG = "jpg";
	public static final String POSTFIX_JPEG = "jpeg";
	public static final String POSTFIX_PNG = "png";
	
	public static final String IMAGE_TYPE_GIF = "image/gif";
	public static final String IMAGE_TYPE_JPEG = "image/jpeg";
	public static final String IMAGE_TYPE_PNG = "image/png";
}
