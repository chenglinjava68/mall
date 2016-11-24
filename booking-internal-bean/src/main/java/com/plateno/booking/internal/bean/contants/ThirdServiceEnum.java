package com.plateno.booking.internal.bean.contants;


public enum ThirdServiceEnum {
	
	LVMAMA_SERVICE(1,"lvmamaService","驴妈妈服务"),
	TONGCHENG_SERVICE(2,"tongchengService","同程服务"),
	YAOCHUFA_SERVICE(3,"yaochufaService","要出发服务"),
	BOTAO_SERVICE(4,"botao","铂物馆");
	
	private Integer channel;
	
	private String  serviceName;
	
	private String desc;

	private ThirdServiceEnum(Integer channel, String serviceName, String desc) {
		this.channel = channel;
		this.serviceName = serviceName;
		this.desc = desc;
	}
	
	public Integer getChannel() {
		return channel;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getDesc() {
		return desc;
	}

	public static String getServiceNameByChannel(Integer channel){
		for(ThirdServiceEnum thirdServiceEnum : ThirdServiceEnum.values()){
			if(thirdServiceEnum.getChannel().equals(channel)){
				return thirdServiceEnum.getServiceName();
			}
		}
		return null;
	}
}
