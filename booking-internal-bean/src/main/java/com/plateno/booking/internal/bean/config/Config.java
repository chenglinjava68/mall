package com.plateno.booking.internal.bean.config;

import org.apache.log4j.Logger;


public class Config {

   protected final static Logger log = Logger.getLogger(Config.class);
	//载入配置文件
   public final static String PROFILE_CXF = "config.properties";
   
   public static String LVMAMA_URL="";
   
   public static String APPKEY="";
   
   public static String SECRET="";
   
   public static String TC_URL="";
   public static String TC_AGENTACCOUNT="";
   public static String TC_KEY="";
   

   
   public static int CONNECT_SOKET_TIME_OUT_LONG; 
   public static int CONNECT_TIME_OUT_LONG; 
   public static int VERIFICATION_CODE_DEBUG; 
   
   //微信用户在铂涛会微信公众平台的openid，不是微信开发者的openid
   public static final Integer PROPERTY_TYPE_WEIXIN_MCH_OPENID = 142;
 	
   public static final Integer PROPERTY_TYPE_MOBILE = 40;
   
   public static final Integer LOCAL_LISTEN_PORT = 8083;

   public static final String LOCAL_LISTEN_IP = "0.0.0.0";
   
   public static final int POINT_TYPE = 617;//积分类型
   
   public static final int GROW_TYPE = 6;//成长值类型
   

   
   //#铂涛旅行微信
   public static int WX_PLATFORM_ID;

   
   //供应商渠道
   public final static int CHANNEL_LVMAMA = 1;
   public final static int CHANNEL_TC= 2;
   public final static int CHANNEL_YCF= 3;
   
   public static String MERCHANT_PAY_KEY;
   public static String	MERCHANT_NO;
   public static String MERCHANT_PAY_URL;
   
   public static String PAYGATE_REFUND_CALLBACK;
   
   public static String TEMPLATEID;
   
   public static String WECHAT_TEMPLATE_URL;
   
   //景点信息服务
   public static String SCENIC_TRIP_URL;
   
   
   //商品服务
   public static String MALL_GOODS_URL;
   
   public static String BILL_DETAIL_URL;
   
   public static long WS_CONNECTION_TIME;
   public static long WS_READ_TIME;
   
   public static Integer START_JOB;
   
   /**要出发配置**/
   public static String YCF_URL;
   public static String YCF_PARTNERID;
   public static String YCF_ACCESSTOKEN;
   public static String YCF_SECRETKEY;
   public static String YCF_VERSION;
   
   /**铂涛酒店预定配置**/
   public static String BT_URL;
   
   /**短信配置**/
   public static String SMS_SERVICE_URL;
   public static String SMS_SERVICE_APPID;
   public static String SMS_SERVICE_TOKEN;
   public static String SMS_SERVICE_TEMPLATE_ONE;
   public static String SMS_SERVICE_TEMPLATE_TWO;
   public static String SMS_SERVICE_TEMPLATE_THREE;
   public static String SMS_SERVICE_TEMPLATE_FOUR;
   public static String SMS_SERVICE_TEMPLATE_FIVE;
   public static String SMS_SERVICE_TEMPLATE_SIX;
   public static String SMS_SERVICE_TEMPLATE_SEVEN;
   public static String SMS_SERVICE_TEMPLATE_EIGHT;
   public static String SMS_SERVICE_TEMPLATE_NINE;
   
   	//操作类型
   public static  final int PRESENT_STATUS_SUCCESS = 1;
   public static  final int PRESENT_STATUS_FAIL = 2;
   //默认送积分
   public static  final int FIRST_CONSUME_POINT = 300;
 	
   //操作类型
   public static  final int PROPERTY_TYPE_PRESENT_POINT = 1;
   //操作类型 赠送成长值
   public static  final int PROPERTY_TYPE_PRESENT_GROW = 2;
   //操作类型 扣减成长值
   public static  final int PROPERTY_TYPE_MINUMS_GROW = 4;
   //操作类型 扣减积分
   public static  final int PROPERTY_TYPE_MINUMS_POINT = 3;
   
   //日志表类型(1表示 积分赠送，2表示成长值赠送,3表示积分扣减，4表示成长值扣减)
 	public static  final int POINT_PRESENT = 1;
 	public static  final int GROW_PRESENT = 2;
 	public static  final int POINT_MINUS = 3;
 	public static  final int GROW_MINUS = 4;
   
    public static  String MEMBERGROW_X_AUTH_HEADER;
   
    public static  String MEMBER_GROWVALUE_URL;
   
    //是否启动定时任务
    public static  Integer _JOBDAYS;
    
  //旅行业务群组邮件地址：
  	public static String EMAIL_GROUP;
   
  /* 载入配置 */
  static {
  	try {

		
  		CONNECT_SOKET_TIME_OUT_LONG= Integer.parseInt(PropertiesUtil.readPropertiesTools(PROFILE_CXF, "connect.soket.timeout"));
  		CONNECT_TIME_OUT_LONG= Integer.parseInt(PropertiesUtil.readPropertiesTools(PROFILE_CXF, "connect.timeout"));
  		VERIFICATION_CODE_DEBUG= Integer.parseInt(PropertiesUtil.readPropertiesTools(PROFILE_CXF, "verification.code.debug"));
  		
  		
  		MERCHANT_PAY_KEY= PropertiesUtil.readPropertiesTools(PROFILE_CXF, "MERCHANT_PAY_KEY");
  	    MERCHANT_NO= PropertiesUtil.readPropertiesTools(PROFILE_CXF, "MERCHANT_NO");
  	    MERCHANT_PAY_URL = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "MERCHANT_PAY_URL");
  	    PAYGATE_REFUND_CALLBACK = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "PAYGATE_REFUND_CALLBACK");
  	    
  	    TEMPLATEID = PropertiesUtil.readPropertiesTools(PROFILE_CXF,"WEIXIN_TEMPLATEID");
  	    
  	    WECHAT_TEMPLATE_URL = PropertiesUtil.readPropertiesTools(PROFILE_CXF,"WECHAT_TEMPLATE_URL");
  	      	    
  	    //商城商品服务
  	    MALL_GOODS_URL = PropertiesUtil.readPropertiesTools(PROFILE_CXF,"MALL_GOODS_URL");
  	    
  	    BILL_DETAIL_URL = PropertiesUtil.readPropertiesTools(PROFILE_CXF,"BILL_DETAIL_URL");
  	    
  	    WS_CONNECTION_TIME = Long.valueOf(PropertiesUtil.readPropertiesTools(PROFILE_CXF,"WS_CONNECTION_TIME"));
  	    
  	    WS_READ_TIME = Long.valueOf(PropertiesUtil.readPropertiesTools(PROFILE_CXF,"WS_READ_TIME"));
  	    
  	    START_JOB = Integer.valueOf(PropertiesUtil.readPropertiesTools(PROFILE_CXF,"START_JOB"));
  	    
		
		BT_URL = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "config.bt.url");
		
		SMS_SERVICE_URL = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_URL");
		SMS_SERVICE_APPID = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_APPID");
		SMS_SERVICE_TOKEN = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TOKEN");
		SMS_SERVICE_TEMPLATE_ONE = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_ONE");
		SMS_SERVICE_TEMPLATE_TWO = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_TWO");
		SMS_SERVICE_TEMPLATE_THREE = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_THREE");
		SMS_SERVICE_TEMPLATE_FOUR = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_FOUR");
		SMS_SERVICE_TEMPLATE_FIVE = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_FIVE");
		SMS_SERVICE_TEMPLATE_SIX = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_SIX");
		SMS_SERVICE_TEMPLATE_SEVEN = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_SEVEN");
		SMS_SERVICE_TEMPLATE_EIGHT = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_EIGHT");
		SMS_SERVICE_TEMPLATE_NINE = PropertiesUtil.readPropertiesTools(PROFILE_CXF, "SMS_SERVICE_TEMPLATE_NINE");
		WX_PLATFORM_ID= Integer.parseInt(PropertiesUtil.readPropertiesTools(PROFILE_CXF, "wx_platform_id"));
		
		MEMBERGROW_X_AUTH_HEADER = PropertiesUtil.readPropertiesTools(PROFILE_CXF,"MEMBERGROW_X_AUTH_HEADER");
		MEMBER_GROWVALUE_URL= PropertiesUtil.readPropertiesTools(PROFILE_CXF,"URL_MEMBERGROW_SERVICE");
		
		_JOBDAYS = Integer.parseInt(PropertiesUtil.readPropertiesTools(PROFILE_CXF,"days"));;
  	}
		catch (Exception e) {
			e.printStackTrace();
			log.error("初始化配置异常", e);
		}
  }

}
