package com.plateno.booking.internal.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil {
	
	public static byte[] toByteArray(Object object){
		byte[] b = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try{
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
			b = bos.toByteArray();
			oos.close();
			bos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return b;
	}
	
	public static Object toObject(byte[] b){
		Object object = null;
		try{
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			ObjectInputStream ois = new ObjectInputStream(bis);
			object = ois.readObject();
			ois.close();
			bis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return object;
	}

}
