--新增字段
ALTER TABLE m_order_product ADD `order_sub_no` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '子订单code';
ALTER TABLE m_order_product ADD `sun_order_flag` INT(11) DEFAULT 1 COMMENT '子订单状态'; 
ALTER TABLE m_order_product ADD `package_id` INT(11) DEFAULT NULL COMMENT '包裹id'; 
UPDATE m_order_product SET order_sub_no = order_no;
CREATE TABLE `m_logistics_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `logistics_type` int(11) DEFAULT NULL COMMENT '物流类型(1 圆通、2申通、3韵达、4百事通、5顺丰、6 EMS,7自提),默认1',
  `logistics_no` varchar(200) DEFAULT NULL COMMENT '物流编号',
  `order_no` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `status` int(11) DEFAULT NULL COMMENT '包裹状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
ALTER TABLE order_pay_log ADD `currency_deposit_amount` INT(11) DEFAULT NULL COMMENT '储值总支付金额(单位：分)';
ALTER TABLE m_order ADD `currency_deposit_amount` INT(11) DEFAULT NULL COMMENT '储值总支付金额(单位：分)'
