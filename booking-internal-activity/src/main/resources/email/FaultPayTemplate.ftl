<html>
<head>(旅行业务)OTA支付故障告急</head>
<title>(旅行业务)OTA支付故障告急</title>
<body>
	<div><b>会员ID:</b>${memberId}</div>
	<div><b>会员姓名:</b>${username}</div>
	<div><b>会员手机号码:</b>${mobile}</div>
	<div ><b>铂涛账单ID:</b><span style="color: Red;">${tradeNo}</span></div>
	<div><b>游玩日期:</b>${visitDate}</div>
	<div><b>下单时间:</b>${createTime}</div>
	<div><b>所属渠道:</b>${channel}</div>
	<div><b>订单状态:</b>${status}</div>
	<div><b>故障原因:</b>${errorMsg}</div>
	<div><b>订单状态描述:</b>100-初始;101-超时取消;200-网关支付中;201-网关支付成功;202-网关支付失败;300-OTA支付中;301-OTA支付成功;302-OTA支付失败;400-OTA退款申请中;401-OTA退款成功;402-OTA退款失败;500-网关退款申请中;501-网关退款成功;502-网关退款失败</div>
	<div><b>所属渠道描述:</b>1.驴妈妈; 2.同程; 3.要出发; 4.方特</div>
</body>
</html>