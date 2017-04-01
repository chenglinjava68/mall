package com.plateno.booking.internal.base.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderExample() {
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Integer value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Integer value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Integer value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Integer value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Integer value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Integer> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Integer> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Integer value1, Integer value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Integer value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Integer value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Integer value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Integer value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Integer> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Integer> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andSidIsNull() {
            addCriterion("sid is null");
            return (Criteria) this;
        }

        public Criteria andSidIsNotNull() {
            addCriterion("sid is not null");
            return (Criteria) this;
        }

        public Criteria andSidEqualTo(Integer value) {
            addCriterion("sid =", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotEqualTo(Integer value) {
            addCriterion("sid <>", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThan(Integer value) {
            addCriterion("sid >", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThanOrEqualTo(Integer value) {
            addCriterion("sid >=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThan(Integer value) {
            addCriterion("sid <", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThanOrEqualTo(Integer value) {
            addCriterion("sid <=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidIn(List<Integer> values) {
            addCriterion("sid in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotIn(List<Integer> values) {
            addCriterion("sid not in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidBetween(Integer value1, Integer value2) {
            addCriterion("sid between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotBetween(Integer value1, Integer value2) {
            addCriterion("sid not between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andChanelidIsNull() {
            addCriterion("chanelid is null");
            return (Criteria) this;
        }

        public Criteria andChanelidIsNotNull() {
            addCriterion("chanelid is not null");
            return (Criteria) this;
        }

        public Criteria andChanelidEqualTo(Integer value) {
            addCriterion("chanelid =", value, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidNotEqualTo(Integer value) {
            addCriterion("chanelid <>", value, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidGreaterThan(Integer value) {
            addCriterion("chanelid >", value, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidGreaterThanOrEqualTo(Integer value) {
            addCriterion("chanelid >=", value, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidLessThan(Integer value) {
            addCriterion("chanelid <", value, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidLessThanOrEqualTo(Integer value) {
            addCriterion("chanelid <=", value, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidIn(List<Integer> values) {
            addCriterion("chanelid in", values, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidNotIn(List<Integer> values) {
            addCriterion("chanelid not in", values, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidBetween(Integer value1, Integer value2) {
            addCriterion("chanelid between", value1, value2, "chanelid");
            return (Criteria) this;
        }

        public Criteria andChanelidNotBetween(Integer value1, Integer value2) {
            addCriterion("chanelid not between", value1, value2, "chanelid");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNull() {
            addCriterion("item_id is null");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNotNull() {
            addCriterion("item_id is not null");
            return (Criteria) this;
        }

        public Criteria andItemIdEqualTo(Integer value) {
            addCriterion("item_id =", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotEqualTo(Integer value) {
            addCriterion("item_id <>", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThan(Integer value) {
            addCriterion("item_id >", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_id >=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThan(Integer value) {
            addCriterion("item_id <", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThanOrEqualTo(Integer value) {
            addCriterion("item_id <=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdIn(List<Integer> values) {
            addCriterion("item_id in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotIn(List<Integer> values) {
            addCriterion("item_id not in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdBetween(Integer value1, Integer value2) {
            addCriterion("item_id between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotBetween(Integer value1, Integer value2) {
            addCriterion("item_id not between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(Integer value) {
            addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(Integer value) {
            addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(Integer value) {
            addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(Integer value) {
            addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(Integer value) {
            addCriterion("pay_type <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<Integer> values) {
            addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<Integer> values) {
            addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(Integer value1, Integer value2) {
            addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_type not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeIsNull() {
            addCriterion("wait_pay_time is null");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeIsNotNull() {
            addCriterion("wait_pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeEqualTo(Date value) {
            addCriterion("wait_pay_time =", value, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeNotEqualTo(Date value) {
            addCriterion("wait_pay_time <>", value, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeGreaterThan(Date value) {
            addCriterion("wait_pay_time >", value, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("wait_pay_time >=", value, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeLessThan(Date value) {
            addCriterion("wait_pay_time <", value, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("wait_pay_time <=", value, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeIn(List<Date> values) {
            addCriterion("wait_pay_time in", values, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeNotIn(List<Date> values) {
            addCriterion("wait_pay_time not in", values, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeBetween(Date value1, Date value2) {
            addCriterion("wait_pay_time between", value1, value2, "waitPayTime");
            return (Criteria) this;
        }

        public Criteria andWaitPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("wait_pay_time not between", value1, value2, "waitPayTime");
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

        public Criteria andRefundAmountIsNull() {
            addCriterion("refund_amount is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNotNull() {
            addCriterion("refund_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountEqualTo(Integer value) {
            addCriterion("refund_amount =", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotEqualTo(Integer value) {
            addCriterion("refund_amount <>", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThan(Integer value) {
            addCriterion("refund_amount >", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("refund_amount >=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThan(Integer value) {
            addCriterion("refund_amount <", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThanOrEqualTo(Integer value) {
            addCriterion("refund_amount <=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIn(List<Integer> values) {
            addCriterion("refund_amount in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotIn(List<Integer> values) {
            addCriterion("refund_amount not in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountBetween(Integer value1, Integer value2) {
            addCriterion("refund_amount between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("refund_amount not between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIsNull() {
            addCriterion("refund_time is null");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIsNotNull() {
            addCriterion("refund_time is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTimeEqualTo(Date value) {
            addCriterion("refund_time =", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotEqualTo(Date value) {
            addCriterion("refund_time <>", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeGreaterThan(Date value) {
            addCriterion("refund_time >", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("refund_time >=", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeLessThan(Date value) {
            addCriterion("refund_time <", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeLessThanOrEqualTo(Date value) {
            addCriterion("refund_time <=", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIn(List<Date> values) {
            addCriterion("refund_time in", values, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotIn(List<Date> values) {
            addCriterion("refund_time not in", values, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeBetween(Date value1, Date value2) {
            addCriterion("refund_time between", value1, value2, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotBetween(Date value1, Date value2) {
            addCriterion("refund_time not between", value1, value2, "refundTime");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("pay_status is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("pay_status is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(Integer value) {
            addCriterion("pay_status =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(Integer value) {
            addCriterion("pay_status <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(Integer value) {
            addCriterion("pay_status >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_status >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(Integer value) {
            addCriterion("pay_status <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pay_status <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<Integer> values) {
            addCriterion("pay_status in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<Integer> values) {
            addCriterion("pay_status not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(Integer value1, Integer value2) {
            addCriterion("pay_status between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_status not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayMoneyIsNull() {
            addCriterion("pay_money is null");
            return (Criteria) this;
        }

        public Criteria andPayMoneyIsNotNull() {
            addCriterion("pay_money is not null");
            return (Criteria) this;
        }

        public Criteria andPayMoneyEqualTo(Integer value) {
            addCriterion("pay_money =", value, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyNotEqualTo(Integer value) {
            addCriterion("pay_money <>", value, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyGreaterThan(Integer value) {
            addCriterion("pay_money >", value, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_money >=", value, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyLessThan(Integer value) {
            addCriterion("pay_money <", value, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("pay_money <=", value, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyIn(List<Integer> values) {
            addCriterion("pay_money in", values, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyNotIn(List<Integer> values) {
            addCriterion("pay_money not in", values, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyBetween(Integer value1, Integer value2) {
            addCriterion("pay_money between", value1, value2, "payMoney");
            return (Criteria) this;
        }

        public Criteria andPayMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_money not between", value1, value2, "payMoney");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeIsNull() {
            addCriterion("deliver_time is null");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeIsNotNull() {
            addCriterion("deliver_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeEqualTo(Date value) {
            addCriterion("deliver_time =", value, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeNotEqualTo(Date value) {
            addCriterion("deliver_time <>", value, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeGreaterThan(Date value) {
            addCriterion("deliver_time >", value, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("deliver_time >=", value, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeLessThan(Date value) {
            addCriterion("deliver_time <", value, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeLessThanOrEqualTo(Date value) {
            addCriterion("deliver_time <=", value, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeIn(List<Date> values) {
            addCriterion("deliver_time in", values, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeNotIn(List<Date> values) {
            addCriterion("deliver_time not in", values, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeBetween(Date value1, Date value2) {
            addCriterion("deliver_time between", value1, value2, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andDeliverTimeNotBetween(Date value1, Date value2) {
            addCriterion("deliver_time not between", value1, value2, "deliverTime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeIsNull() {
            addCriterion("refund_successtime is null");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeIsNotNull() {
            addCriterion("refund_successtime is not null");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeEqualTo(Date value) {
            addCriterion("refund_successtime =", value, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeNotEqualTo(Date value) {
            addCriterion("refund_successtime <>", value, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeGreaterThan(Date value) {
            addCriterion("refund_successtime >", value, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeGreaterThanOrEqualTo(Date value) {
            addCriterion("refund_successtime >=", value, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeLessThan(Date value) {
            addCriterion("refund_successtime <", value, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeLessThanOrEqualTo(Date value) {
            addCriterion("refund_successtime <=", value, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeIn(List<Date> values) {
            addCriterion("refund_successtime in", values, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeNotIn(List<Date> values) {
            addCriterion("refund_successtime not in", values, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeBetween(Date value1, Date value2) {
            addCriterion("refund_successtime between", value1, value2, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundSuccesstimeNotBetween(Date value1, Date value2) {
            addCriterion("refund_successtime not between", value1, value2, "refundSuccesstime");
            return (Criteria) this;
        }

        public Criteria andRefundReasonIsNull() {
            addCriterion("refund_reason is null");
            return (Criteria) this;
        }

        public Criteria andRefundReasonIsNotNull() {
            addCriterion("refund_reason is not null");
            return (Criteria) this;
        }

        public Criteria andRefundReasonEqualTo(String value) {
            addCriterion("refund_reason =", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonNotEqualTo(String value) {
            addCriterion("refund_reason <>", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonGreaterThan(String value) {
            addCriterion("refund_reason >", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonGreaterThanOrEqualTo(String value) {
            addCriterion("refund_reason >=", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonLessThan(String value) {
            addCriterion("refund_reason <", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonLessThanOrEqualTo(String value) {
            addCriterion("refund_reason <=", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonLike(String value) {
            addCriterion("refund_reason like", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonNotLike(String value) {
            addCriterion("refund_reason not like", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonIn(List<String> values) {
            addCriterion("refund_reason in", values, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonNotIn(List<String> values) {
            addCriterion("refund_reason not in", values, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonBetween(String value1, String value2) {
            addCriterion("refund_reason between", value1, value2, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonNotBetween(String value1, String value2) {
            addCriterion("refund_reason not between", value1, value2, "refundReason");
            return (Criteria) this;
        }

        public Criteria andResourceIsNull() {
            addCriterion("resource is null");
            return (Criteria) this;
        }

        public Criteria andResourceIsNotNull() {
            addCriterion("resource is not null");
            return (Criteria) this;
        }

        public Criteria andResourceEqualTo(Integer value) {
            addCriterion("resource =", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotEqualTo(Integer value) {
            addCriterion("resource <>", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceGreaterThan(Integer value) {
            addCriterion("resource >", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource >=", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceLessThan(Integer value) {
            addCriterion("resource <", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceLessThanOrEqualTo(Integer value) {
            addCriterion("resource <=", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceIn(List<Integer> values) {
            addCriterion("resource in", values, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotIn(List<Integer> values) {
            addCriterion("resource not in", values, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceBetween(Integer value1, Integer value2) {
            addCriterion("resource between", value1, value2, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotBetween(Integer value1, Integer value2) {
            addCriterion("resource not between", value1, value2, "resource");
            return (Criteria) this;
        }

        public Criteria andRefundPointIsNull() {
            addCriterion("refund_point is null");
            return (Criteria) this;
        }

        public Criteria andRefundPointIsNotNull() {
            addCriterion("refund_point is not null");
            return (Criteria) this;
        }

        public Criteria andRefundPointEqualTo(Integer value) {
            addCriterion("refund_point =", value, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointNotEqualTo(Integer value) {
            addCriterion("refund_point <>", value, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointGreaterThan(Integer value) {
            addCriterion("refund_point >", value, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointGreaterThanOrEqualTo(Integer value) {
            addCriterion("refund_point >=", value, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointLessThan(Integer value) {
            addCriterion("refund_point <", value, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointLessThanOrEqualTo(Integer value) {
            addCriterion("refund_point <=", value, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointIn(List<Integer> values) {
            addCriterion("refund_point in", values, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointNotIn(List<Integer> values) {
            addCriterion("refund_point not in", values, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointBetween(Integer value1, Integer value2) {
            addCriterion("refund_point between", value1, value2, "refundPoint");
            return (Criteria) this;
        }

        public Criteria andRefundPointNotBetween(Integer value1, Integer value2) {
            addCriterion("refund_point not between", value1, value2, "refundPoint");
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

        public Criteria andRefundFailReasonIsNull() {
            addCriterion("refund_fail_reason is null");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonIsNotNull() {
            addCriterion("refund_fail_reason is not null");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonEqualTo(String value) {
            addCriterion("refund_fail_reason =", value, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonNotEqualTo(String value) {
            addCriterion("refund_fail_reason <>", value, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonGreaterThan(String value) {
            addCriterion("refund_fail_reason >", value, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonGreaterThanOrEqualTo(String value) {
            addCriterion("refund_fail_reason >=", value, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonLessThan(String value) {
            addCriterion("refund_fail_reason <", value, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonLessThanOrEqualTo(String value) {
            addCriterion("refund_fail_reason <=", value, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonLike(String value) {
            addCriterion("refund_fail_reason like", value, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonNotLike(String value) {
            addCriterion("refund_fail_reason not like", value, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonIn(List<String> values) {
            addCriterion("refund_fail_reason in", values, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonNotIn(List<String> values) {
            addCriterion("refund_fail_reason not in", values, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonBetween(String value1, String value2) {
            addCriterion("refund_fail_reason between", value1, value2, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andRefundFailReasonNotBetween(String value1, String value2) {
            addCriterion("refund_fail_reason not between", value1, value2, "refundFailReason");
            return (Criteria) this;
        }

        public Criteria andLogicDelIsNull() {
            addCriterion("logic_del is null");
            return (Criteria) this;
        }

        public Criteria andLogicDelIsNotNull() {
            addCriterion("logic_del is not null");
            return (Criteria) this;
        }

        public Criteria andLogicDelEqualTo(Byte value) {
            addCriterion("logic_del =", value, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelNotEqualTo(Byte value) {
            addCriterion("logic_del <>", value, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelGreaterThan(Byte value) {
            addCriterion("logic_del >", value, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelGreaterThanOrEqualTo(Byte value) {
            addCriterion("logic_del >=", value, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelLessThan(Byte value) {
            addCriterion("logic_del <", value, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelLessThanOrEqualTo(Byte value) {
            addCriterion("logic_del <=", value, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelIn(List<Byte> values) {
            addCriterion("logic_del in", values, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelNotIn(List<Byte> values) {
            addCriterion("logic_del not in", values, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelBetween(Byte value1, Byte value2) {
            addCriterion("logic_del between", value1, value2, "logicDel");
            return (Criteria) this;
        }

        public Criteria andLogicDelNotBetween(Byte value1, Byte value2) {
            addCriterion("logic_del not between", value1, value2, "logicDel");
            return (Criteria) this;
        }

        public Criteria andSubResourceIsNull() {
            addCriterion("sub_resource is null");
            return (Criteria) this;
        }

        public Criteria andSubResourceIsNotNull() {
            addCriterion("sub_resource is not null");
            return (Criteria) this;
        }

        public Criteria andSubResourceEqualTo(Integer value) {
            addCriterion("sub_resource =", value, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceNotEqualTo(Integer value) {
            addCriterion("sub_resource <>", value, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceGreaterThan(Integer value) {
            addCriterion("sub_resource >", value, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_resource >=", value, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceLessThan(Integer value) {
            addCriterion("sub_resource <", value, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceLessThanOrEqualTo(Integer value) {
            addCriterion("sub_resource <=", value, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceIn(List<Integer> values) {
            addCriterion("sub_resource in", values, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceNotIn(List<Integer> values) {
            addCriterion("sub_resource not in", values, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceBetween(Integer value1, Integer value2) {
            addCriterion("sub_resource between", value1, value2, "subResource");
            return (Criteria) this;
        }

        public Criteria andSubResourceNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_resource not between", value1, value2, "subResource");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostIsNull() {
            addCriterion("total_product_cost is null");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostIsNotNull() {
            addCriterion("total_product_cost is not null");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostEqualTo(Integer value) {
            addCriterion("total_product_cost =", value, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostNotEqualTo(Integer value) {
            addCriterion("total_product_cost <>", value, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostGreaterThan(Integer value) {
            addCriterion("total_product_cost >", value, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_product_cost >=", value, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostLessThan(Integer value) {
            addCriterion("total_product_cost <", value, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostLessThanOrEqualTo(Integer value) {
            addCriterion("total_product_cost <=", value, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostIn(List<Integer> values) {
            addCriterion("total_product_cost in", values, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostNotIn(List<Integer> values) {
            addCriterion("total_product_cost not in", values, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostBetween(Integer value1, Integer value2) {
            addCriterion("total_product_cost between", value1, value2, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalProductCostNotBetween(Integer value1, Integer value2) {
            addCriterion("total_product_cost not between", value1, value2, "totalProductCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostIsNull() {
            addCriterion("total_express_cost is null");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostIsNotNull() {
            addCriterion("total_express_cost is not null");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostEqualTo(Integer value) {
            addCriterion("total_express_cost =", value, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostNotEqualTo(Integer value) {
            addCriterion("total_express_cost <>", value, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostGreaterThan(Integer value) {
            addCriterion("total_express_cost >", value, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_express_cost >=", value, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostLessThan(Integer value) {
            addCriterion("total_express_cost <", value, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostLessThanOrEqualTo(Integer value) {
            addCriterion("total_express_cost <=", value, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostIn(List<Integer> values) {
            addCriterion("total_express_cost in", values, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostNotIn(List<Integer> values) {
            addCriterion("total_express_cost not in", values, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostBetween(Integer value1, Integer value2) {
            addCriterion("total_express_cost between", value1, value2, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andTotalExpressCostNotBetween(Integer value1, Integer value2) {
            addCriterion("total_express_cost not between", value1, value2, "totalExpressCost");
            return (Criteria) this;
        }

        public Criteria andCouponAmountIsNull() {
            addCriterion("coupon_amount is null");
            return (Criteria) this;
        }

        public Criteria andCouponAmountIsNotNull() {
            addCriterion("coupon_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCouponAmountEqualTo(Integer value) {
            addCriterion("coupon_amount =", value, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountNotEqualTo(Integer value) {
            addCriterion("coupon_amount <>", value, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountGreaterThan(Integer value) {
            addCriterion("coupon_amount >", value, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("coupon_amount >=", value, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountLessThan(Integer value) {
            addCriterion("coupon_amount <", value, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountLessThanOrEqualTo(Integer value) {
            addCriterion("coupon_amount <=", value, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountIn(List<Integer> values) {
            addCriterion("coupon_amount in", values, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountNotIn(List<Integer> values) {
            addCriterion("coupon_amount not in", values, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountBetween(Integer value1, Integer value2) {
            addCriterion("coupon_amount between", value1, value2, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCouponAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("coupon_amount not between", value1, value2, "couponAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountIsNull() {
            addCriterion("currency_deposit_amount is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountIsNotNull() {
            addCriterion("currency_deposit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountEqualTo(Integer value) {
            addCriterion("currency_deposit_amount =", value, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountNotEqualTo(Integer value) {
            addCriterion("currency_deposit_amount <>", value, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountGreaterThan(Integer value) {
            addCriterion("currency_deposit_amount >", value, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("currency_deposit_amount >=", value, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountLessThan(Integer value) {
            addCriterion("currency_deposit_amount <", value, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountLessThanOrEqualTo(Integer value) {
            addCriterion("currency_deposit_amount <=", value, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountIn(List<Integer> values) {
            addCriterion("currency_deposit_amount in", values, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountNotIn(List<Integer> values) {
            addCriterion("currency_deposit_amount not in", values, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountBetween(Integer value1, Integer value2) {
            addCriterion("currency_deposit_amount between", value1, value2, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andCurrencyDepositAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("currency_deposit_amount not between", value1, value2, "currencyDepositAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountIsNull() {
            addCriterion("gateway_amount is null");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountIsNotNull() {
            addCriterion("gateway_amount is not null");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountEqualTo(Integer value) {
            addCriterion("gateway_amount =", value, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountNotEqualTo(Integer value) {
            addCriterion("gateway_amount <>", value, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountGreaterThan(Integer value) {
            addCriterion("gateway_amount >", value, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("gateway_amount >=", value, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountLessThan(Integer value) {
            addCriterion("gateway_amount <", value, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountLessThanOrEqualTo(Integer value) {
            addCriterion("gateway_amount <=", value, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountIn(List<Integer> values) {
            addCriterion("gateway_amount in", values, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountNotIn(List<Integer> values) {
            addCriterion("gateway_amount not in", values, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountBetween(Integer value1, Integer value2) {
            addCriterion("gateway_amount between", value1, value2, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andGatewayAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("gateway_amount not between", value1, value2, "gatewayAmount");
            return (Criteria) this;
        }

        public Criteria andPointMoneyIsNull() {
            addCriterion("point_money is null");
            return (Criteria) this;
        }

        public Criteria andPointMoneyIsNotNull() {
            addCriterion("point_money is not null");
            return (Criteria) this;
        }

        public Criteria andPointMoneyEqualTo(Integer value) {
            addCriterion("point_money =", value, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyNotEqualTo(Integer value) {
            addCriterion("point_money <>", value, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyGreaterThan(Integer value) {
            addCriterion("point_money >", value, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("point_money >=", value, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyLessThan(Integer value) {
            addCriterion("point_money <", value, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("point_money <=", value, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyIn(List<Integer> values) {
            addCriterion("point_money in", values, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyNotIn(List<Integer> values) {
            addCriterion("point_money not in", values, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyBetween(Integer value1, Integer value2) {
            addCriterion("point_money between", value1, value2, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andPointMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("point_money not between", value1, value2, "pointMoney");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountIsNull() {
            addCriterion("total_express_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountIsNotNull() {
            addCriterion("total_express_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountEqualTo(Integer value) {
            addCriterion("total_express_amount =", value, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountNotEqualTo(Integer value) {
            addCriterion("total_express_amount <>", value, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountGreaterThan(Integer value) {
            addCriterion("total_express_amount >", value, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_express_amount >=", value, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountLessThan(Integer value) {
            addCriterion("total_express_amount <", value, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountLessThanOrEqualTo(Integer value) {
            addCriterion("total_express_amount <=", value, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountIn(List<Integer> values) {
            addCriterion("total_express_amount in", values, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountNotIn(List<Integer> values) {
            addCriterion("total_express_amount not in", values, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountBetween(Integer value1, Integer value2) {
            addCriterion("total_express_amount between", value1, value2, "totalExpressAmount");
            return (Criteria) this;
        }

        public Criteria andTotalExpressAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("total_express_amount not between", value1, value2, "totalExpressAmount");
            return (Criteria) this;
        }
        public Criteria andOfflineIsNull() {
            addCriterion("offline is null");
            return (Criteria) this;
        }

        public Criteria andOfflineIsNotNull() {
            addCriterion("offline is not null");
            return (Criteria) this;
        }

        public Criteria andOfflineEqualTo(Integer value) {
            addCriterion("offline =", value, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineNotEqualTo(Integer value) {
            addCriterion("offline <>", value, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineGreaterThan(Integer value) {
            addCriterion("offline >", value, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineGreaterThanOrEqualTo(Integer value) {
            addCriterion("offline >=", value, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineLessThan(Integer value) {
            addCriterion("offline <", value, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineLessThanOrEqualTo(Integer value) {
            addCriterion("offline <=", value, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineIn(List<Integer> values) {
            addCriterion("offline in", values, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineNotIn(List<Integer> values) {
            addCriterion("offline not in", values, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineBetween(Integer value1, Integer value2) {
            addCriterion("offline between", value1, value2, "offline");
            return (Criteria) this;
        }

        public Criteria andOfflineNotBetween(Integer value1, Integer value2) {
            addCriterion("offline not between", value1, value2, "offline");
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