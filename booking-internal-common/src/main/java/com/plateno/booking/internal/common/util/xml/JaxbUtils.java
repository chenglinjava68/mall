package com.plateno.booking.internal.common.util.xml;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class JaxbUtils {
	
	/**
	 * JavaBean to XML
	 * @param obj
	 * @return
	 */
	 public static String convertToXml(Object obj) {  
	        return convertToXml(obj, "UTF-8");  
	    }  
	  
	    /** 
	     * JavaBean to XML
	     * @param obj 
	     * @param encoding  
	     * @return  
	     */  
	  private static String convertToXml(Object obj, String encoding) {  
	        String result = null;  
	     try {  
	            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
	            Marshaller marshaller = context.createMarshaller();  
	            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);  
	            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
	            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
	            StringWriter writer = new StringWriter(); 
	            marshaller.marshal(obj, writer);  
	            result = writer.toString();  
	      } catch (Exception e) {  
	            e.printStackTrace();  
	      }  
	      return result;  
	    } 
	    
	    @SuppressWarnings("unchecked")
	    public static <T>T convery2Bean(String xml, Class<T> c) {  
	        T t = null;  
	        try {  
	            JAXBContext context = JAXBContext.newInstance(c);  
	            Unmarshaller unmarshaller = context.createUnmarshaller();  
	            t = (T) unmarshaller.unmarshal(new StringReader(xml));  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return t;  
	    } 
	    

	  public static String convertToXml(Object obj, String encoding,Boolean xmlFlag) {  
		        String result = null;  
		     try {  
		            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
		            Marshaller marshaller = context.createMarshaller();  
		            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);  
		            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
		            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, xmlFlag);
		            StringWriter writer = new StringWriter(); 
		            marshaller.marshal(obj, writer);  
		            result = writer.toString();  
		      } catch (Exception e) {  
		            e.printStackTrace();  
		      }  
		      return result;  
		    } 
}
