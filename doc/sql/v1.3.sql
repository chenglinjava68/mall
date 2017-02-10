--新增字段
ALTER TABLE m_order_product ADD `order_sub_no` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '子订单code';
ALTER TABLE m_order_product ADD `channel_id` INT(11)  COMMENT '仓库id';
ALTER TABLE m_order_product ADD `provided_id` INT(11)  COMMENT '供应商id';
ALTER TABLE m_order_product ADD `coupou_reduce_amount` INT(11)  COMMENT '优惠券优惠金额，单位（分）';
UPDATE m_order_product SET order_sub_no = order_no;

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
   order_no             varchar(45) comment '订单号',
   logistics_type       int comment '快递类型',
   logistics_no         varchar(45) comment '快递单号',
   express_fee          int comment '快递费用',
   package_flag         int comment '包裹状态',
   primary key (id)
);

alter table m_logistics_package comment '订单包裹表';

CREATE TABLE m_logistics_product
(
   id                   INT NOT NULL AUTO_INCREMENT,
   package_id           INT,
   order_product_id     INT COMMENT '订单商品表主键',
   PRIMARY KEY (id)
);

ALTER TABLE m_logistics_product COMMENT '包裹对应的商品';