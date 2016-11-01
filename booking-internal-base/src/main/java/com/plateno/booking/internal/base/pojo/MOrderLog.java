package com.plateno.booking.internal.base.pojo;

import java.util.Date;

public class MOrderLog {
    private Long id;

    /**
	 *中文描述
	 */
    private String info;

    /**
   	 *接口名称
   	 */
    private String interfaceName;

    /**
   	 *渠道 1 驴妈妈  2同程 3要出发
   	 */
    private Integer channel;

    /**
   	 *更新时间
   	 */
    private Date upTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName == null ? null : interfaceName.trim();
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }
}