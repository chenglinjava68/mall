--新增字段
ALTER TABLE m_order ADD `point_money` INT(11)  COMMENT '积分抵扣的金额';
ALTER TABLE m_order ADD `total_express_amount` INT(11)  COMMENT '总快递费';

ALTER TABLE m_order_product ADD `order_sub_no` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '子订单code';
ALTER TABLE m_order_product ADD `channel_id` INT(11)  COMMENT '仓库id';
ALTER TABLE m_order_product ADD `provided_id` INT(11)  COMMENT '供应商id';
ALTER TABLE m_order_product ADD `express_amount` INT(11)  COMMENT '快递费';
ALTER TABLE m_order_product ADD `coupou_reduce_amount` INT(11)  COMMENT '优惠券优惠金额，单位（分）';
ALTER TABLE m_order_product ADD `point_reduce_amount` INT(11)  COMMENT '积分优惠金额，单位（分）';

ALTER TABLE m_order_coupon ADD `config_id` INT(11)  COMMENT '优惠券配置id';
ALTER TABLE operate_log ADD `order_sub_no` VARCHAR(255)   DEFAULT '' COMMENT '子订单code';
ALTER TABLE gs_order_log ADD `order_sub_no` VARCHAR(255)   DEFAULT '' COMMENT '子订单code';

CREATE TABLE m_order_sub
(
   id                   INT NOT NULL AUTO_INCREMENT,
   order_no             VARCHAR(45) COMMENT '父订单号',
   order_sub_no         VARCHAR(45) COMMENT '子订单号',
   sub_flag             INT COMMENT '子订单状态',
   sub_price            INT COMMENT '子订单金额',
   channel_id           INT COMMENT '仓库id',
   provided_id          INT COMMENT '供应商id',
   PRIMARY KEY (id)
);

ALTER TABLE m_order_sub COMMENT '子订单表';

create table m_logistics_package
(
   id                   int not null auto_increment,
   order_no             varchar(45) comment '父订单号',
   order_sub_no         varchar(45) comment '子订单号',
   logistics_type       int comment '快递类型',
   logistics_no         varchar(45) comment '快递单号',
   express_fee          int comment '快递费用',
   package_flag         int comment '包裹状态',
   create_time datetime DEFAULT NULL COMMENT '创建时间',
   up_time datetime DEFAULT NULL COMMENT '最近修改时间',
   primary key (id)
);

alter table m_logistics_package comment '订单包裹表';


--主订单的仓库存储到子订单中
SELECT 
  CONCAT(
    'update m_order_product set channel_id = ',o.`chanelid`,' where order_no = "',o.`order_no`,'";'
  ) 
FROM
  m_order o 
  LEFT JOIN m_order_product p 
    ON o.order_no = p.order_no WHERE o.order_no IS NOT NULL;

    INSERT INTO m_order_sub(order_no,order_sub_no,sub_flag,sub_price,channel_id) SELECT order_no,CONCAT(order_no,chanelid),pay_status,pay_money,chanelid FROM m_order;
    
UPDATE m_order_product SET order_sub_no = CONCAT(order_no,channel_id) WHERE order_no IS NOT NULL AND channel_id IS NOT NULL;
--子订单的积分抵扣金额，存储到父订单中
 SELECT 
  CONCAT(
    'update m_order set point_money = ',
    product.`deduct_price`,' where order_no = "',m.`order_no`,'";'
  ) 
FROM
  m_order_product product,
  m_order m 
WHERE product.`order_no` = m.`order_no` 
  AND product.`deduct_price` > 0 ;
  
  CREATE TABLE `m_logistics_product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `package_id` INT(11) DEFAULT NULL,
  `order_product_id` INT(11) DEFAULT NULL COMMENT '订单商品表主键',
  PRIMARY KEY (`id`)
)  COMMENT='包裹对应的商品';
  
create  index key_order_sub_no on m_order_sub (order_sub_no);
CREATE  INDEX key_order_no ON m_order_sub (order_no);

create  index key_order_sub_no on m_logistics_package (order_sub_no);
CREATE  INDEX key_order_no ON m_logistics_package (order_no);
CREATE  INDEX key_package_id ON m_logistics_product (package_id);