ALTER table order_pay_log add currency_deposit_amount int(11) DEFAULT NULL COMMENT '储值金额';
ALTER TABLE order_pay_log ADD gateway_amount INT(11) DEFAULT NULL COMMENT '支付网关金额';
ALTER TABLE m_order ADD currency_deposit_amount INT(11) DEFAULT NULL COMMENT '储值金额';
ALTER TABLE m_order ADD gateway_amount INT(11) DEFAULT NULL COMMENT '支付网关金额';
UPDATE order_pay_log SET gateway_amount = amount;
update m_order set gateway_amount = pay_money;