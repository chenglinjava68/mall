package com.plateno.booking.internal.base.pojo;

import java.util.ArrayList;
import java.util.List;

public class OrderSubExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderSubExample() {
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

        public Criteria andSubFlagIsNull() {
            addCriterion("sub_flag is null");
            return (Criteria) this;
        }

        public Criteria andSubFlagIsNotNull() {
            addCriterion("sub_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSubFlagEqualTo(Integer value) {
            addCriterion("sub_flag =", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagNotEqualTo(Integer value) {
            addCriterion("sub_flag <>", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagGreaterThan(Integer value) {
            addCriterion("sub_flag >", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_flag >=", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagLessThan(Integer value) {
            addCriterion("sub_flag <", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagLessThanOrEqualTo(Integer value) {
            addCriterion("sub_flag <=", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagIn(List<Integer> values) {
            addCriterion("sub_flag in", values, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagNotIn(List<Integer> values) {
            addCriterion("sub_flag not in", values, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagBetween(Integer value1, Integer value2) {
            addCriterion("sub_flag between", value1, value2, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_flag not between", value1, value2, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubPriceIsNull() {
            addCriterion("sub_price is null");
            return (Criteria) this;
        }

        public Criteria andSubPriceIsNotNull() {
            addCriterion("sub_price is not null");
            return (Criteria) this;
        }

        public Criteria andSubPriceEqualTo(Integer value) {
            addCriterion("sub_price =", value, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceNotEqualTo(Integer value) {
            addCriterion("sub_price <>", value, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceGreaterThan(Integer value) {
            addCriterion("sub_price >", value, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_price >=", value, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceLessThan(Integer value) {
            addCriterion("sub_price <", value, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceLessThanOrEqualTo(Integer value) {
            addCriterion("sub_price <=", value, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceIn(List<Integer> values) {
            addCriterion("sub_price in", values, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceNotIn(List<Integer> values) {
            addCriterion("sub_price not in", values, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceBetween(Integer value1, Integer value2) {
            addCriterion("sub_price between", value1, value2, "subPrice");
            return (Criteria) this;
        }

        public Criteria andSubPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_price not between", value1, value2, "subPrice");
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