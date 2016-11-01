package com.plateno.booking.internal.bean.contants;


/**
 * @author user
 *
 */
public interface BookingServiceEnumConstants {

	public final static String ADD = "ADD";

	public final static String CANCEL = "CANCEL";

	public final static String PAY = "PAY";

	public final static String REFUND = "REFUND";


	public final static String BOTAO_CST_LVMAMA = "1";
	
	public final static String BOTAO_CST_TC = "2";
	
	public final static String BOTAO_CST_YCF = "3";
	
	public final static String BOTAO_CST_BOTAO = "1";
	
	public final static String BOTAO_CST_ACT = "100";
	
	public final static String BOTAO_MALL_BOTAO = "101";


	public final static String LVMAMA = "_LvMaMa_";

	public final static String TONGCHENG = "_TongCheng_";

	public final static String YAOCHUFA = "_YaoChuFa_";
	
	public final static String PTATENO = "_PLATENO_";
	
	public final static String ACTIVITY = "_Activity_";
	
	public final static String TICKET = "Ticket_";
	
	
	/**
	 * botao-ticket-refund
	 */
	public final static String BOTAO_2_TICKET_REFUND = TICKET + REFUND; // Ticket_REFUND
	
	/**
	 * botao-ticket-pay
	 */
	public final static String BOTAO_2_TICKET_PAY = TICKET + PAY; // Ticket_REFUND
	
	/**
	 * botao-lvmama
	 */
	public final static String BOTAO_2_LVMAMA_ADD_BOOKING = BOTAO_CST_LVMAMA + LVMAMA + ADD; // 1_LvMaMa_ADD

	public final static String BOTAO_2_LVMAMA_CANCEL_BOOKING = BOTAO_CST_LVMAMA + LVMAMA + CANCEL; // 1_LvMaMa_CANCEL

	public final static String BOTAO_2_LVMAMA_ADD_ACCTRAN = BOTAO_CST_LVMAMA + LVMAMA + PAY; // 1_LvMaMa_PAY

	public final static String BOTAO_2_LVMAMA_REFUND = BOTAO_CST_LVMAMA + LVMAMA + REFUND; // 1_LvMaMa_REFUND




	
	/**
	 * botao-tongcheng
	 */
	public final static String BOTAO_2_TONGCHENG_ADD_BOOKING = BOTAO_CST_TC + TONGCHENG + ADD; // 2_TongCheng_ADD

	public final static String BOTAO_2_TONGCHENG_CANCEL_BOOKING = BOTAO_CST_TC + TONGCHENG + CANCEL; // 2_TongCheng_CANCEL

	public final static String BOTAO_2_TONGCHENG_ADD_ACCTRAN = BOTAO_CST_TC + TONGCHENG + PAY; // 2_TongCheng_PAY

	public final static String BOTAO_2_TONGCHENG_REFUND = BOTAO_CST_TC + TONGCHENG + REFUND; // 2_TongCheng_REFUND



	/**
	 *botao-yaochufa
	 */
	public final static String BOTAO_2_YAOCHUFA_ADD_BOOKING = BOTAO_CST_YCF + YAOCHUFA + ADD; // 1_YaoChuFa_ADD

	public final static String BOTAO_2_YAOCHUFA_CANCEL_BOOKING = BOTAO_CST_YCF + YAOCHUFA + CANCEL; // 1_YaoChuFa_CANCEL

	public final static String BOTAO_2_YAOCHUFA_ADD_ACCTRAN = BOTAO_CST_YCF + YAOCHUFA + PAY; // 1_YaoChuFa_PAY

	public final static String BOTAO_2_YAOCHUFA_REFUND = BOTAO_CST_YCF + YAOCHUFA + REFUND; // 1_YaoChuFa_REFUND



	/**
	 *botao-botao
	 */
	public final static String BOTAO_2_BOTAO_ADD_BOOKING = BOTAO_MALL_BOTAO + PTATENO + ADD; // 1_BoTao_ADD

	public final static String BOTAO_2_BOTAO_CANCEL_BOOKING = BOTAO_MALL_BOTAO + PTATENO + CANCEL; // 1_BoTao_CANCEL

	public final static String BOTAO_2_BOTAO_ADD_ACCTRAN = BOTAO_MALL_BOTAO + PTATENO + PAY; // 1_BoTao_ADD_PAY

	public final static String BOTAO_2_BOTAO_REFUND = BOTAO_CST_BOTAO + YAOCHUFA + REFUND; // 1_BoTao_REFUND


	
	/**
	 * botao-activity
	 */
	public final static String BOTAO_2_ACTIVITY_ADD_BOOKING = BOTAO_CST_ACT + ACTIVITY + ADD; // 2_TongCheng_ADD
	
	/**
	 * botao-ticket-pay
	 */
	public final static String BOTAO_2_ACTIVITY_PAY = ACTIVITY + PAY; // Ticket_PAY
	
	/**
	 * botao-ticket-refund
	 */
	public final static String BOTAO_2_ACTIVITY_REFUND = ACTIVITY + REFUND; // Ticket_REFUND
	
	/**
	 * UNKNOW
	 */
	public final static String UNKNOW = "__";
	
	
	
	/**
	 *botao-mall
	 */
	public final static String BOTAO_MALL_BOTAO_ADD_BOOKING = BOTAO_MALL_BOTAO + PTATENO + ADD; // 1_BoTao_ADD
	

}
