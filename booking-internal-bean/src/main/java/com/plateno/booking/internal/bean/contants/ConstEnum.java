package com.plateno.booking.internal.bean.contants;

public interface ConstEnum {

	public enum ChannelCode {
		
		LVMAMA_CODE(1,"门票 Ⅰ -"),
		TONGCHENG_CODE(2,"门票 Ⅱ-"),
		YAOCHUFA_CODE(3,"门票 Ⅲ -"),;

		private ChannelCode(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		private final int code;
		private final String desc;
		
		public static String getByCode(int c){
			for(ChannelCode p : ChannelCode.values()){
				if(p.getCode() == c){
					return p.getDesc();
				}
			}
			return null;
		}
		
		public int getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}	
	
	
	/**
	 * 票类型
	 * 
	 * @author user
	 *
	 */
	public enum TicketpCode {
		
		TICKETTYP_SON(1,"亲子票"),
		TICKETTYP_FAMILLY(2,"家庭票"),
		TICKETTYP_LOVER(3,"情侣票"),
		TICKETTYP_COUPE(4,"双人票"),
		TICKETTYP_ADULT(5,"成人票"),
		TICKETTYP_CHILD(6,"儿童票"),
		TICKETTYP_ELDER(7,"老人票"),
		TICKETTYP_STUDENT(8,"学生票"),
		TICKETTYP_ACTIVE(9,"活动票"),
		TICKETTYP_SOLDIER(10,"军人票"),
		TICKETTYP_MALE(11,"男士票"),
		TICKETTYP_FEMALE(12,"女士票"),
		TICKETTYP_TEACHER(13,"教师票"),
		TICKETTYP_DEFORMITY(14,"残疾票"),
		TICKETTYP_GROUP(15,"团体票"),
		TICKETTYP_CUSTOM(16,"自定义"),
		TICKETTYP_TREATMENT(17,"优待票"),
		TICKETTYP_COMMON(18,"普通票"),
		TICKETTYP_SPECIAL(19,"特殊票,"),
		TICKETTYP_BENEFIT(20,"优惠套票");

		private TicketpCode(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		private final int code;
		private final String desc;
		
		
		public static String getByCode(int c){
			for(TicketpCode p : TicketpCode.values()){
				if(p.getCode() == c){
					return p.getDesc();
				}
			}
			return null;
		}
		
		public int getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}
	
	/**
	 * OTA 渠道  
	 * 驴妈妈=1
	 * 同程=2
	 * 要出发=3
	 * @author lingjw
	 * @version 创建时间：2016年4月29日 上午9:53:25 
	 * 说明:
	 */
	public enum ChannelEnum {

		LVMAMA(1,"驴妈妈"),
		TONGCHENG(2,"同程"),
		YAOCHUFA(3,"要出发"),
		
		
		CUSTOM(100,"自定义");
		
		private Integer channel;
		private String channelName;
		
		private ChannelEnum(Integer channel,String channelName){
			this.channel = channel;
			this.channelName = channelName;
		}

		public Integer getChannel() {
			return channel;
		}

		public void setChannel(Integer channel) {
			this.channel = channel;
		}
		
		public String getChannelName() {
			return channelName;
		}

		public void setChannelName(String channelName) {
			this.channelName = channelName;
		}

		public static ChannelEnum getChannel(Integer channel){
			ChannelEnum[] values = ChannelEnum.values();
			for(ChannelEnum c:values){
				if(channel == c.getChannel()){
					return c;
				}
			}
			return null;
		}
	}
	
	public enum TCOrderState {

		CUSTOMER_SUBMIT("N"),//客户已提交
		FAX_SEND("I"),//已经发传真
		TONGCHENG_COMMIT("F"),//已确认
		SCENIC_COMMIT("J"),//已确认
		CHECKED("V"),//已经核单
	    CANCEL("C"),//取消
	    NOSHOW("S");//
		
	    private String state;
		
		private TCOrderState(String state){
			this.state = state;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
	
	public enum TCPayState {

		NOT_PAY("N"),//未支付
		ALREADY_PAY("P"),//已经支付
		PART_PAY("P1"),//部分支付
		ALREADY_REFUND("R"),//已退款R
		part_refund("R1"),//申请部分退款
	    all_refund("R2");//申请全额退款
		
	    private String state;
		
		private TCPayState(String state){
			this.state = state;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
	
	
}
