package com.plateno.booking.internal.base.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderProductExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andSkuidIsNull() {
            addCriterion("skuid is null");
            return (Criteria) this;
        }

        public Criteria andSkuidIsNotNull() {
            addCriterion("skuid is not null");
            return (Criteria) this;
        }

        public Criteria andSkuidEqualTo(Integer value) {
            addCriterion("skuid =", value, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidNotEqualTo(Integer value) {
            addCriterion("skuid <>", value, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidGreaterThan(Integer value) {
            addCriterion("skuid >", value, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("skuid >=", value, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidLessThan(Integer value) {
            addCriterion("skuid <", value, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidLessThanOrEqualTo(Integer value) {
            addCriterion("skuid <=", value, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidIn(List<Integer> values) {
            addCriterion("skuid in", values, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidNotIn(List<Integer> values) {
            addCriterion("skuid not in", values, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidBetween(Integer value1, Integer value2) {
            addCriterion("skuid between", value1, value2, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuidNotBetween(Integer value1, Integer value2) {
            addCriterion("skuid not between", value1, value2, "skuid");
            return (Criteria) this;
        }

        public Criteria andSkuCountIsNull() {
            addCriterion("sku_count is null");
            return (Criteria) this;
        }

        public Criteria andSkuCountIsNotNull() {
            addCriterion("sku_count is not null");
            return (Criteria) this;
        }

        public Criteria andSkuCountEqualTo(Integer value) {
            addCriterion("sku_count =", value, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountNotEqualTo(Integer value) {
            addCriterion("sku_count <>", value, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountGreaterThan(Integer value) {
            addCriterion("sku_count >", value, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("sku_count >=", value, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountLessThan(Integer value) {
            addCriterion("sku_count <", value, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountLessThanOrEqualTo(Integer value) {
            addCriterion("sku_count <=", value, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountIn(List<Integer> values) {
            addCriterion("sku_count in", values, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountNotIn(List<Integer> values) {
            addCriterion("sku_count not in", values, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountBetween(Integer value1, Integer value2) {
            addCriterion("sku_count between", value1, value2, "skuCount");
            return (Criteria) this;
        }

        public Criteria andSkuCountNotBetween(Integer value1, Integer value2) {
            addCriterion("sku_count not between", value1, value2, "skuCount");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Integer value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Integer value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Integer value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Integer value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Integer value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Integer> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Integer> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Integer value1, Integer value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Integer value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Integer value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Integer value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Integer value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Integer value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Integer> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Integer> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Integer value1, Integer value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Integer value1, Integer value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductPropertyIsNull() {
            addCriterion("product_property is null");
            return (Criteria) this;
        }

        public Criteria andProductPropertyIsNotNull() {
            addCriterion("product_property is not null");
            return (Criteria) this;
        }

        public Criteria andProductPropertyEqualTo(String value) {
            addCriterion("product_property =", value, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyNotEqualTo(String value) {
            addCriterion("product_property <>", value, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyGreaterThan(String value) {
            addCriterion("product_property >", value, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyGreaterThanOrEqualTo(String value) {
            addCriterion("product_property >=", value, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyLessThan(String value) {
            addCriterion("product_property <", value, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyLessThanOrEqualTo(String value) {
            addCriterion("product_property <=", value, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyLike(String value) {
            addCriterion("product_property like", value, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyNotLike(String value) {
            addCriterion("product_property not like", value, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyIn(List<String> values) {
            addCriterion("product_property in", values, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyNotIn(List<String> values) {
            addCriterion("product_property not in", values, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyBetween(String value1, String value2) {
            addCriterion("product_property between", value1, value2, "productProperty");
            return (Criteria) this;
        }

        public Criteria andProductPropertyNotBetween(String value1, String value2) {
            addCriterion("product_property not between", value1, value2, "productProperty");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeIsNull() {
            addCriterion("up_time is null");
            return (Criteria) this;
        }

        public Criteria andUpTimeIsNotNull() {
            addCriterion("up_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpTimeEqualTo(Date value) {
            addCriterion("up_time =", value, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeNotEqualTo(Date value) {
            addCriterion("up_time <>", value, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeGreaterThan(Date value) {
            addCriterion("up_time >", value, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("up_time >=", value, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeLessThan(Date value) {
            addCriterion("up_time <", value, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeLessThanOrEqualTo(Date value) {
            addCriterion("up_time <=", value, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeIn(List<Date> values) {
            addCriterion("up_time in", values, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeNotIn(List<Date> values) {
            addCriterion("up_time not in", values, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeBetween(Date value1, Date value2) {
            addCriterion("up_time between", value1, value2, "upTime");
            return (Criteria) this;
        }

        public Criteria andUpTimeNotBetween(Date value1, Date value2) {
            addCriterion("up_time not between", value1, value2, "upTime");
            return (Criteria) this;
        }

        public Criteria andPointIsNull() {
            addCriterion("point is null");
            return (Criteria) this;
        }

        public Criteria andPointIsNotNull() {
            addCriterion("point is not null");
            return (Criteria) this;
        }

        public Criteria andPointEqualTo(Integer value) {
            addCriterion("point =", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotEqualTo(Integer value) {
            addCriterion("point <>", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointGreaterThan(Integer value) {
            addCriterion("point >", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointGreaterThanOrEqualTo(Integer value) {
            addCriterion("point >=", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointLessThan(Integer value) {
            addCriterion("point <", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointLessThanOrEqualTo(Integer value) {
            addCriterion("point <=", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointIn(List<Integer> values) {
            addCriterion("point in", values, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotIn(List<Integer> values) {
            addCriterion("point not in", values, "point");
            return (Criteria) this;
        }

        public Criteria andPointBetween(Integer value1, Integer value2) {
            addCriterion("point between", value1, value2, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotBetween(Integer value1, Integer value2) {
            addCriterion("point not between", value1, value2, "point");
            return (Criteria) this;
        }

        public Criteria andSellStrategyIsNull() {
            addCriterion("sell_strategy is null");
            return (Criteria) this;
        }

        public Criteria andSellStrategyIsNotNull() {
            addCriterion("sell_strategy is not null");
            return (Criteria) this;
        }

        public Criteria andSellStrategyEqualTo(Integer value) {
            addCriterion("sell_strategy =", value, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyNotEqualTo(Integer value) {
            addCriterion("sell_strategy <>", value, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyGreaterThan(Integer value) {
            addCriterion("sell_strategy >", value, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyGreaterThanOrEqualTo(Integer value) {
            addCriterion("sell_strategy >=", value, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyLessThan(Integer value) {
            addCriterion("sell_strategy <", value, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyLessThanOrEqualTo(Integer value) {
            addCriterion("sell_strategy <=", value, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyIn(List<Integer> values) {
            addCriterion("sell_strategy in", values, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyNotIn(List<Integer> values) {
            addCriterion("sell_strategy not in", values, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyBetween(Integer value1, Integer value2) {
            addCriterion("sell_strategy between", value1, value2, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andSellStrategyNotBetween(Integer value1, Integer value2) {
            addCriterion("sell_strategy not between", value1, value2, "sellStrategy");
            return (Criteria) this;
        }

        public Criteria andDisImagesIsNull() {
            addCriterion("dis_images is null");
            return (Criteria) this;
        }

        public Criteria andDisImagesIsNotNull() {
            addCriterion("dis_images is not null");
            return (Criteria) this;
        }

        public Criteria andDisImagesEqualTo(String value) {
            addCriterion("dis_images =", value, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesNotEqualTo(String value) {
            addCriterion("dis_images <>", value, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesGreaterThan(String value) {
            addCriterion("dis_images >", value, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesGreaterThanOrEqualTo(String value) {
            addCriterion("dis_images >=", value, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesLessThan(String value) {
            addCriterion("dis_images <", value, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesLessThanOrEqualTo(String value) {
            addCriterion("dis_images <=", value, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesLike(String value) {
            addCriterion("dis_images like", value, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesNotLike(String value) {
            addCriterion("dis_images not like", value, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesIn(List<String> values) {
            addCriterion("dis_images in", values, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesNotIn(List<String> values) {
            addCriterion("dis_images not in", values, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesBetween(String value1, String value2) {
            addCriterion("dis_images between", value1, value2, "disImages");
            return (Criteria) this;
        }

        public Criteria andDisImagesNotBetween(String value1, String value2) {
            addCriterion("dis_images not between", value1, value2, "disImages");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyIsNull() {
            addCriterion("price_strategy is null");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyIsNotNull() {
            addCriterion("price_strategy is not null");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyEqualTo(Byte value) {
            addCriterion("price_strategy =", value, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyNotEqualTo(Byte value) {
            addCriterion("price_strategy <>", value, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyGreaterThan(Byte value) {
            addCriterion("price_strategy >", value, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyGreaterThanOrEqualTo(Byte value) {
            addCriterion("price_strategy >=", value, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyLessThan(Byte value) {
            addCriterion("price_strategy <", value, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyLessThanOrEqualTo(Byte value) {
            addCriterion("price_strategy <=", value, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyIn(List<Byte> values) {
            addCriterion("price_strategy in", values, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyNotIn(List<Byte> values) {
            addCriterion("price_strategy not in", values, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyBetween(Byte value1, Byte value2) {
            addCriterion("price_strategy between", value1, value2, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyNotBetween(Byte value1, Byte value2) {
            addCriterion("price_strategy not between", value1, value2, "priceStrategy");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescIsNull() {
            addCriterion("price_strategy_desc is null");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescIsNotNull() {
            addCriterion("price_strategy_desc is not null");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescEqualTo(String value) {
            addCriterion("price_strategy_desc =", value, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescNotEqualTo(String value) {
            addCriterion("price_strategy_desc <>", value, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescGreaterThan(String value) {
            addCriterion("price_strategy_desc >", value, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescGreaterThanOrEqualTo(String value) {
            addCriterion("price_strategy_desc >=", value, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescLessThan(String value) {
            addCriterion("price_strategy_desc <", value, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescLessThanOrEqualTo(String value) {
            addCriterion("price_strategy_desc <=", value, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescLike(String value) {
            addCriterion("price_strategy_desc like", value, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescNotLike(String value) {
            addCriterion("price_strategy_desc not like", value, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescIn(List<String> values) {
            addCriterion("price_strategy_desc in", values, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescNotIn(List<String> values) {
            addCriterion("price_strategy_desc not in", values, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescBetween(String value1, String value2) {
            addCriterion("price_strategy_desc between", value1, value2, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andPriceStrategyDescNotBetween(String value1, String value2) {
            addCriterion("price_strategy_desc not between", value1, value2, "priceStrategyDesc");
            return (Criteria) this;
        }

        public Criteria andDeductPriceIsNull() {
            addCriterion("deduct_price is null");
            return (Criteria) this;
        }

        public Criteria andDeductPriceIsNotNull() {
            addCriterion("deduct_price is not null");
            return (Criteria) this;
        }

        public Criteria andDeductPriceEqualTo(Integer value) {
            addCriterion("deduct_price =", value, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceNotEqualTo(Integer value) {
            addCriterion("deduct_price <>", value, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceGreaterThan(Integer value) {
            addCriterion("deduct_price >", value, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("deduct_price >=", value, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceLessThan(Integer value) {
            addCriterion("deduct_price <", value, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceLessThanOrEqualTo(Integer value) {
            addCriterion("deduct_price <=", value, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceIn(List<Integer> values) {
            addCriterion("deduct_price in", values, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceNotIn(List<Integer> values) {
            addCriterion("deduct_price not in", values, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceBetween(Integer value1, Integer value2) {
            addCriterion("deduct_price between", value1, value2, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andDeductPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("deduct_price not between", value1, value2, "deductPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostIsNull() {
            addCriterion("product_cost is null");
            return (Criteria) this;
        }

        public Criteria andProductCostIsNotNull() {
            addCriterion("product_cost is not null");
            return (Criteria) this;
        }

        public Criteria andProductCostEqualTo(Integer value) {
            addCriterion("product_cost =", value, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostNotEqualTo(Integer value) {
            addCriterion("product_cost <>", value, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostGreaterThan(Integer value) {
            addCriterion("product_cost >", value, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_cost >=", value, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostLessThan(Integer value) {
            addCriterion("product_cost <", value, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostLessThanOrEqualTo(Integer value) {
            addCriterion("product_cost <=", value, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostIn(List<Integer> values) {
            addCriterion("product_cost in", values, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostNotIn(List<Integer> values) {
            addCriterion("product_cost not in", values, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostBetween(Integer value1, Integer value2) {
            addCriterion("product_cost between", value1, value2, "productCost");
            return (Criteria) this;
        }

        public Criteria andProductCostNotBetween(Integer value1, Integer value2) {
            addCriterion("product_cost not between", value1, value2, "productCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostIsNull() {
            addCriterion("express_cost is null");
            return (Criteria) this;
        }

        public Criteria andExpressCostIsNotNull() {
            addCriterion("express_cost is not null");
            return (Criteria) this;
        }

        public Criteria andExpressCostEqualTo(Integer value) {
            addCriterion("express_cost =", value, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostNotEqualTo(Integer value) {
            addCriterion("express_cost <>", value, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostGreaterThan(Integer value) {
            addCriterion("express_cost >", value, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostGreaterThanOrEqualTo(Integer value) {
            addCriterion("express_cost >=", value, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostLessThan(Integer value) {
            addCriterion("express_cost <", value, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostLessThanOrEqualTo(Integer value) {
            addCriterion("express_cost <=", value, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostIn(List<Integer> values) {
            addCriterion("express_cost in", values, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostNotIn(List<Integer> values) {
            addCriterion("express_cost not in", values, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostBetween(Integer value1, Integer value2) {
            addCriterion("express_cost between", value1, value2, "expressCost");
            return (Criteria) this;
        }

        public Criteria andExpressCostNotBetween(Integer value1, Integer value2) {
            addCriterion("express_cost not between", value1, value2, "expressCost");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountIsNull() {
            addCriterion("return_sku_count is null");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountIsNotNull() {
            addCriterion("return_sku_count is not null");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountEqualTo(Integer value) {
            addCriterion("return_sku_count =", value, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountNotEqualTo(Integer value) {
            addCriterion("return_sku_count <>", value, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountGreaterThan(Integer value) {
            addCriterion("return_sku_count >", value, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_sku_count >=", value, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountLessThan(Integer value) {
            addCriterion("return_sku_count <", value, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountLessThanOrEqualTo(Integer value) {
            addCriterion("return_sku_count <=", value, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountIn(List<Integer> values) {
            addCriterion("return_sku_count in", values, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountNotIn(List<Integer> values) {
            addCriterion("return_sku_count not in", values, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountBetween(Integer value1, Integer value2) {
            addCriterion("return_sku_count between", value1, value2, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andReturnSkuCountNotBetween(Integer value1, Integer value2) {
            addCriterion("return_sku_count not between", value1, value2, "returnSkuCount");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoIsNull() {
            addCriterion("order_sub_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoIsNotNull() {
            addCriterion("order_sub_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoEqualTo(String value) {
            addCriterion("order_sub_no =", value, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoNotEqualTo(String value) {
            addCriterion("order_sub_no <>", value, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoGreaterThan(String value) {
            addCriterion("order_sub_no >", value, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_sub_no >=", value, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoLessThan(String value) {
            addCriterion("order_sub_no <", value, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoLessThanOrEqualTo(String value) {
            addCriterion("order_sub_no <=", value, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoLike(String value) {
            addCriterion("order_sub_no like", value, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoNotLike(String value) {
            addCriterion("order_sub_no not like", value, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoIn(List<String> values) {
            addCriterion("order_sub_no in", values, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoNotIn(List<String> values) {
            addCriterion("order_sub_no not in", values, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoBetween(String value1, String value2) {
            addCriterion("order_sub_no between", value1, value2, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andOrderSubNoNotBetween(String value1, String value2) {
            addCriterion("order_sub_no not between", value1, value2, "orderSubNo");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(Integer value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(Integer value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(Integer value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(Integer value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(Integer value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<Integer> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<Integer> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(Integer value1, Integer value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdIsNull() {
            addCriterion("provided_id is null");
            return (Criteria) this;
        }

        public Criteria andProvidedIdIsNotNull() {
            addCriterion("provided_id is not null");
            return (Criteria) this;
        }

        public Criteria andProvidedIdEqualTo(Integer value) {
            addCriterion("provided_id =", value, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdNotEqualTo(Integer value) {
            addCriterion("provided_id <>", value, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdGreaterThan(Integer value) {
            addCriterion("provided_id >", value, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("provided_id >=", value, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdLessThan(Integer value) {
            addCriterion("provided_id <", value, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdLessThanOrEqualTo(Integer value) {
            addCriterion("provided_id <=", value, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdIn(List<Integer> values) {
            addCriterion("provided_id in", values, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdNotIn(List<Integer> values) {
            addCriterion("provided_id not in", values, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdBetween(Integer value1, Integer value2) {
            addCriterion("provided_id between", value1, value2, "providedId");
            return (Criteria) this;
        }

        public Criteria andProvidedIdNotBetween(Integer value1, Integer value2) {
            addCriterion("provided_id not between", value1, value2, "providedId");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountIsNull() {
            addCriterion("coupou_reduce_amount is null");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountIsNotNull() {
            addCriterion("coupou_reduce_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountEqualTo(Integer value) {
            addCriterion("coupou_reduce_amount =", value, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountNotEqualTo(Integer value) {
            addCriterion("coupou_reduce_amount <>", value, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountGreaterThan(Integer value) {
            addCriterion("coupou_reduce_amount >", value, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("coupou_reduce_amount >=", value, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountLessThan(Integer value) {
            addCriterion("coupou_reduce_amount <", value, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountLessThanOrEqualTo(Integer value) {
            addCriterion("coupou_reduce_amount <=", value, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountIn(List<Integer> values) {
            addCriterion("coupou_reduce_amount in", values, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountNotIn(List<Integer> values) {
            addCriterion("coupou_reduce_amount not in", values, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountBetween(Integer value1, Integer value2) {
            addCriterion("coupou_reduce_amount between", value1, value2, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andCoupouReduceAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("coupou_reduce_amount not between", value1, value2, "coupouReduceAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountIsNull() {
            addCriterion("express_amount is null");
            return (Criteria) this;
        }

        public Criteria andExpressAmountIsNotNull() {
            addCriterion("express_amount is not null");
            return (Criteria) this;
        }

        public Criteria andExpressAmountEqualTo(Integer value) {
            addCriterion("express_amount =", value, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountNotEqualTo(Integer value) {
            addCriterion("express_amount <>", value, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountGreaterThan(Integer value) {
            addCriterion("express_amount >", value, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("express_amount >=", value, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountLessThan(Integer value) {
            addCriterion("express_amount <", value, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountLessThanOrEqualTo(Integer value) {
            addCriterion("express_amount <=", value, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountIn(List<Integer> values) {
            addCriterion("express_amount in", values, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountNotIn(List<Integer> values) {
            addCriterion("express_amount not in", values, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountBetween(Integer value1, Integer value2) {
            addCriterion("express_amount between", value1, value2, "expressAmount");
            return (Criteria) this;
        }

        public Criteria andExpressAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("express_amount not between", value1, value2, "expressAmount");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}