package com.plateno.booking.internal.base.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperatelogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OperatelogExample() {
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

        public Criteria andOperateTypeIsNull() {
            addCriterion("operate_type is null");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIsNotNull() {
            addCriterion("operate_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperateTypeEqualTo(Integer value) {
            addCriterion("operate_type =", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotEqualTo(Integer value) {
            addCriterion("operate_type <>", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeGreaterThan(Integer value) {
            addCriterion("operate_type >", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("operate_type >=", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLessThan(Integer value) {
            addCriterion("operate_type <", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLessThanOrEqualTo(Integer value) {
            addCriterion("operate_type <=", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIn(List<Integer> values) {
            addCriterion("operate_type in", values, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotIn(List<Integer> values) {
            addCriterion("operate_type not in", values, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeBetween(Integer value1, Integer value2) {
            addCriterion("operate_type between", value1, value2, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("operate_type not between", value1, value2, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateUseridIsNull() {
            addCriterion("operate_userid is null");
            return (Criteria) this;
        }

        public Criteria andOperateUseridIsNotNull() {
            addCriterion("operate_userid is not null");
            return (Criteria) this;
        }

        public Criteria andOperateUseridEqualTo(String value) {
            addCriterion("operate_userid =", value, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridNotEqualTo(String value) {
            addCriterion("operate_userid <>", value, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridGreaterThan(String value) {
            addCriterion("operate_userid >", value, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridGreaterThanOrEqualTo(String value) {
            addCriterion("operate_userid >=", value, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridLessThan(String value) {
            addCriterion("operate_userid <", value, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridLessThanOrEqualTo(String value) {
            addCriterion("operate_userid <=", value, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridLike(String value) {
            addCriterion("operate_userid like", value, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridNotLike(String value) {
            addCriterion("operate_userid not like", value, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridIn(List<String> values) {
            addCriterion("operate_userid in", values, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridNotIn(List<String> values) {
            addCriterion("operate_userid not in", values, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridBetween(String value1, String value2) {
            addCriterion("operate_userid between", value1, value2, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUseridNotBetween(String value1, String value2) {
            addCriterion("operate_userid not between", value1, value2, "operateUserid");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameIsNull() {
            addCriterion("operate_username is null");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameIsNotNull() {
            addCriterion("operate_username is not null");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameEqualTo(String value) {
            addCriterion("operate_username =", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameNotEqualTo(String value) {
            addCriterion("operate_username <>", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameGreaterThan(String value) {
            addCriterion("operate_username >", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("operate_username >=", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameLessThan(String value) {
            addCriterion("operate_username <", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameLessThanOrEqualTo(String value) {
            addCriterion("operate_username <=", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameLike(String value) {
            addCriterion("operate_username like", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameNotLike(String value) {
            addCriterion("operate_username not like", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameIn(List<String> values) {
            addCriterion("operate_username in", values, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameNotIn(List<String> values) {
            addCriterion("operate_username not in", values, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameBetween(String value1, String value2) {
            addCriterion("operate_username between", value1, value2, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameNotBetween(String value1, String value2) {
            addCriterion("operate_username not between", value1, value2, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andPlateFormIsNull() {
            addCriterion("plate_form is null");
            return (Criteria) this;
        }

        public Criteria andPlateFormIsNotNull() {
            addCriterion("plate_form is not null");
            return (Criteria) this;
        }

        public Criteria andPlateFormEqualTo(Integer value) {
            addCriterion("plate_form =", value, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormNotEqualTo(Integer value) {
            addCriterion("plate_form <>", value, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormGreaterThan(Integer value) {
            addCriterion("plate_form >", value, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormGreaterThanOrEqualTo(Integer value) {
            addCriterion("plate_form >=", value, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormLessThan(Integer value) {
            addCriterion("plate_form <", value, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormLessThanOrEqualTo(Integer value) {
            addCriterion("plate_form <=", value, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormIn(List<Integer> values) {
            addCriterion("plate_form in", values, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormNotIn(List<Integer> values) {
            addCriterion("plate_form not in", values, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormBetween(Integer value1, Integer value2) {
            addCriterion("plate_form between", value1, value2, "plateForm");
            return (Criteria) this;
        }

        public Criteria andPlateFormNotBetween(Integer value1, Integer value2) {
            addCriterion("plate_form not between", value1, value2, "plateForm");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNull() {
            addCriterion("order_code is null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNotNull() {
            addCriterion("order_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("order_code =", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("order_code <>", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("order_code >", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("order_code >=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("order_code <", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("order_code <=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLike(String value) {
            addCriterion("order_code like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("order_code not like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("order_code in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("order_code not in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("order_code between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("order_code not between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIsNull() {
            addCriterion("operate_time is null");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIsNotNull() {
            addCriterion("operate_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperateTimeEqualTo(Date value) {
            addCriterion("operate_time =", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotEqualTo(Date value) {
            addCriterion("operate_time <>", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeGreaterThan(Date value) {
            addCriterion("operate_time >", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("operate_time >=", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeLessThan(Date value) {
            addCriterion("operate_time <", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeLessThanOrEqualTo(Date value) {
            addCriterion("operate_time <=", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIn(List<Date> values) {
            addCriterion("operate_time in", values, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotIn(List<Date> values) {
            addCriterion("operate_time not in", values, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeBetween(Date value1, Date value2) {
            addCriterion("operate_time between", value1, value2, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotBetween(Date value1, Date value2) {
            addCriterion("operate_time not between", value1, value2, "operateTime");
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