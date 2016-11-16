package com.plateno.booking.internal.bean.contants;

public enum OperateLogEnum {
	
	CANCEL_ORDER(1,"取消订单操作"),
	
	DELIVER_ORDER(2,"发货操作"),
	
	REFUNDING_OP(3,"申请退款操作"),
	
	MODIFY_DELIVER_OP(4,"修改发货操作"),
	
	AGREE_REFUND_OP(5,"同意退款操作"),
	
	REFUSE_REFUNDING(6,"拒绝退款操作"),
	
	ORDER_MODIFY(7,"订单信息修改操作"),
	
	ENTER_RECEIPT(8,"确认收货操作"),
	;
	
	private Integer operateType;
	
	private String operateName;
	
	private OperateLogEnum(Integer operateType,String operateName){
		this.operateType=operateType;
		this.operateName=operateName;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public String getOperateName() {
		return operateName;
	}

	public static String  getOperateName(Integer operateType){
		OperateLogEnum[]  values=OperateLogEnum.values();
		for(OperateLogEnum enums:values){
			if(operateType.equals(enums.getOperateType())){
				return enums.getOperateName();
			}
		}
		return null;
	}
	

}
