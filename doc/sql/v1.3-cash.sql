ALTER table order_pay_log add currency_deposit_amount int(11) DEFAULT NULL COMMENT '储值金额';
ALTER TABLE order_pay_log ADD gateway_amount INT(11) DEFAULT NULL COMMENT '支付网关金额';
ALTER TABLE m_order ADD currency_deposit_amount INT(11) DEFAULT NULL COMMENT '储值金额';
ALTER TABLE m_order ADD gateway_amount INT(11) DEFAULT NULL COMMENT '支付网关金额';
UPDATE order_pay_log SET gateway_amount = amount;
update m_order set gateway_amount = pay_money;


CREATE TABLE `m_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_key` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'key',
  `order_value` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'value',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
);

INSERT INTO m_dict VALUES(NULL,'sid','31566,31567',NOW(),1);