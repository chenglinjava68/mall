package com.plateno.booking.internal.common.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * <pre>
 * bean操作工具
 * org.apache.commons.beanutils.BeanUtils基础上扩展
 * </pre>
 * @author mogt
 * @date 2016年11月15日
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
    
    /**
     * 把bean拷贝成map
     * @param dest
     * @param orig
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void beanToMap(Map<String, String> dest, Object orig)
            throws IllegalAccessException {
        
        if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        
        if(orig instanceof Map) {
            @SuppressWarnings("rawtypes")
            Map map = (Map) orig;
            for(Object key : map.keySet()) {
                dest.put(key + "", map.get(key) + "");
            }
            return;
        }
        
        Class<? extends Object> class1 = orig.getClass();
        
        //递归拷贝属性
        copyProperties(dest, orig, class1);
    
    }

    /**
     * 递归拷贝属性
     * @param dest
     * @param orig
     * @param class1
     * @throws IllegalAccessException
     */
    private static void copyProperties(Map<String, String> dest, Object orig,
            Class<? extends Object> class1) throws IllegalAccessException {
        
        Class<?> superclass = class1.getSuperclass();

        if(superclass != null && !superclass.getName().equals("java.lang.Object")) {
            //递归调用
            copyProperties(dest, orig, superclass);
        }
        
        for(Field field : class1.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            String value = field.get(orig) == null ? null : field.get(orig).toString();
            if(value == null) {
                continue;
            }
            dest.put(name, value);
        }
    }

}
