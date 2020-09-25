package com.fit2cloud.ticket.dao.model;

import java.util.ArrayList;
import java.util.List;

public class TicketTagMappingExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public TicketTagMappingExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
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

        public Criteria andTagKeyIsNull() {
            addCriterion("tag_key is null");
            return (Criteria) this;
        }

        public Criteria andTagKeyIsNotNull() {
            addCriterion("tag_key is not null");
            return (Criteria) this;
        }

        public Criteria andTagKeyEqualTo(String value) {
            addCriterion("tag_key =", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyNotEqualTo(String value) {
            addCriterion("tag_key <>", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyGreaterThan(String value) {
            addCriterion("tag_key >", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyGreaterThanOrEqualTo(String value) {
            addCriterion("tag_key >=", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyLessThan(String value) {
            addCriterion("tag_key <", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyLessThanOrEqualTo(String value) {
            addCriterion("tag_key <=", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyLike(String value) {
            addCriterion("tag_key like", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyNotLike(String value) {
            addCriterion("tag_key not like", value, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyIn(List<String> values) {
            addCriterion("tag_key in", values, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyNotIn(List<String> values) {
            addCriterion("tag_key not in", values, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyBetween(String value1, String value2) {
            addCriterion("tag_key between", value1, value2, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTagKeyNotBetween(String value1, String value2) {
            addCriterion("tag_key not between", value1, value2, "tagKey");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdIsNull() {
            addCriterion("ticket_config_id is null");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdIsNotNull() {
            addCriterion("ticket_config_id is not null");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdEqualTo(String value) {
            addCriterion("ticket_config_id =", value, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdNotEqualTo(String value) {
            addCriterion("ticket_config_id <>", value, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdGreaterThan(String value) {
            addCriterion("ticket_config_id >", value, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdGreaterThanOrEqualTo(String value) {
            addCriterion("ticket_config_id >=", value, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdLessThan(String value) {
            addCriterion("ticket_config_id <", value, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdLessThanOrEqualTo(String value) {
            addCriterion("ticket_config_id <=", value, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdLike(String value) {
            addCriterion("ticket_config_id like", value, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdNotLike(String value) {
            addCriterion("ticket_config_id not like", value, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdIn(List<String> values) {
            addCriterion("ticket_config_id in", values, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdNotIn(List<String> values) {
            addCriterion("ticket_config_id not in", values, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdBetween(String value1, String value2) {
            addCriterion("ticket_config_id between", value1, value2, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andTicketConfigIdNotBetween(String value1, String value2) {
            addCriterion("ticket_config_id not between", value1, value2, "ticketConfigId");
            return (Criteria) this;
        }

        public Criteria andSqlCriterion(String value) {
            addCriterion("(" + value + ")");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
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