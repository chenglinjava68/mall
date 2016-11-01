-- 修改后的表结构
 CREATE TABLE `m_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `order_no` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '订单编码',
  logistics_id int(11) NOT NULL DEFAULT 0 COMMENT '物流信息ID',
  `amount` int(11) DEFAULT NULL COMMENT '订单总价',
  `chanelid` int(11) DEFAULT NULL COMMENT '渠道id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `up_time` datetime DEFAULT NULL COMMENT '最近修改时间',
  `point` int(11) DEFAULT '0' COMMENT '订单总消耗积分',
  `refund_amount` int(11) DEFAULT NULL COMMENT '退款金额',
  `refund_time` datetime DEFAULT NULL COMMENT '退款申请时间',
  `pay_status` int(11) DEFAULT NULL COMMENT '支付状态1待付款、2已取消 3待发货，4待收货，5已完成 6退款审核中、7已退款，8退款审核不通过, 9已删除,10 退款中,11支付中,12支付失败,13退款失败',
  `pay_money` int(11) DEFAULT NULL COMMENT '已付金额(单位：分)',
  `deliver_time` datetime DEFAULT NULL COMMENT '发货时间',
  `refund_successtime` datetime DEFAULT NULL COMMENT '退款成功时间',
  `refund_reason` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '退款失败原因',
  `refund_point` int(11) DEFAULT NULL COMMENT '回退积分',
  `remark` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY Key_blanket_no(blanket_no)，
  UNIQUE KEY `Unique_order_code` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4; 