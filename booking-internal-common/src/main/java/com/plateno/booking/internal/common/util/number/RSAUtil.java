package com.plateno.booking.internal.common.util.number;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.plateno.booking.internal.common.util.io.StreamUtil;

public class RSAUtil{

	public static final String SIGN_TYPE_RSA = "RSA";

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	public static String rsaSign(String content,String privateKey,String charset) throws Exception{
		try{
			PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA,new ByteArrayInputStream(privateKey.getBytes()));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);

			if(StringUtils.isEmpty(charset)){
				signature.update(content.getBytes());
			}else{
				signature.update(content.getBytes(charset));
			}

			byte[] signed = signature.sign();

			return new String(Base64.encodeBase64(signed));
		}catch(Exception e){
			throw new Exception("RSAcontent = " + content + "; charset = " + charset,e);
		}
	}

	public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,InputStream ins) throws Exception{
		if(ins == null || StringUtils.isEmpty(algorithm)){
			return null;
		}

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		byte[] encodedKey = StreamUtil.readText(ins).getBytes();

		encodedKey = Base64.decodeBase64(encodedKey);

		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}
	
	public static boolean rsaCheckContent(String content,String sign,String publicKey,String charset) throws Exception{
		try{
			PublicKey pubKey = getPublicKeyFromX509(SIGN_TYPE_RSA,new ByteArrayInputStream(publicKey.getBytes()));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);

			if(StringUtils.isEmpty(charset)){
				signature.update(content.getBytes());
			}else{
				signature.update(content.getBytes(charset));
			}

			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		}catch(Exception e){
			throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset,e);
		}
	}
	
	public static PublicKey getPublicKeyFromX509(String algorithm,InputStream ins) throws Exception{
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		StringWriter writer = new StringWriter();
		StreamUtil.io(new InputStreamReader(ins),writer);

		byte[] encodedKey = writer.toString().getBytes();

		encodedKey = Base64.decodeBase64(encodedKey);

		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}

}
