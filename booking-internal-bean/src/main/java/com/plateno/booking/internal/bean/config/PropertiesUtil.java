
package com.plateno.booking.internal.bean.config;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * 
* @ClassName: UtilTools 
* @Description: TODO(读取propertie工具类) 
* @author  jerome.jiang@pp.plateno.cc
* @date 2014年10月14日 上午10:00:54 
*
 */
@Service
public class PropertiesUtil {
    
    /** 读取 properties 类型的对象 */
    private static Properties properties = null;
    
    /** 初始化 properties 成员 */
    static {
        properties = new Properties();
    }
    
    /**
     * 读取各类 properties 类型配置文件信息
     * @param file 配置文件名称
     * @param info 读取的配置信息条件
     * @return 读取的配置信息
     * @throws Exception
     * @date : 2012-7-22
     * @author : HHB
     */
    public static String readPropertiesTools(String file,String info) throws Exception {
        
        if (file == null) {
            throw new Exception("读取配置文件时，配置文件名称为空");
        }
        if (info == null) {
            throw new Exception("读取配置文件时，被读取信息ID为空");
        }

        /* 如果对象为空构造对象 */
        if (properties == null) {
            properties = new Properties();
        }
        
        try {
            /* 读取配置文件 */
            properties.load(Thread.currentThread().
                    getContextClassLoader().
                    getResourceAsStream(file));
        }
        catch (IOException e) {
            throw new Exception("读取配置文件键值时捕获IO异常，配置文件为"+file+
                    ",异常为"+e.getMessage());
        }
        
        /* 获得数据条件 */
        return properties.getProperty(info.trim());
    }
    
    /**
     * 读取各类 properties 类型配置文件的所有键值
     * @param file 配置文件名称
     * @return 键值列表
     * @throws Exception
     * @date : 2012-7-21
     * @author : jy
     */
    public static Set<Object> readPropertiesKeys(String file)throws Exception {
        
        /* 判定入参的合法性 */
        if (file == null) {
            throw new Exception("读取配置文件时，配置文件名称为空!");
        }

        /* 如果对象为空构造对象 */
        if (properties == null) {
            properties = new Properties();
        }

        /* 读取配置文件 */
        try {
            properties.load(Thread.currentThread().
                    getContextClassLoader().
                    getResourceAsStream(file));
        }
        catch (IOException e) {
            throw new Exception("读取配置文件键值时捕获IO异常，配置文件为"+file+",异常为"+e.getMessage());
        }
        
        /* 获得键值列表 */
        return properties.keySet();
    }
}
