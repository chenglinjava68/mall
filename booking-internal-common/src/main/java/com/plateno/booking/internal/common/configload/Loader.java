package com.plateno.booking.internal.common.configload;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 加载配置、资源接口，用于统一管理配置文件
 * 配置可以从文件、数据库中加载
 */

public abstract class Loader implements InitializingBean {

    @Autowired
    protected ConfigLoader configLoader;

    //属性设置完成后，注册加载服务
    public void afterPropertiesSet() throws Exception {
    	System.out.println("加载===>"+this.getClass().getName());
        configLoader.register(this);
    }

    //重新加载
	public abstract boolean reload();
	
	//加载
	public abstract boolean load();
	
}
