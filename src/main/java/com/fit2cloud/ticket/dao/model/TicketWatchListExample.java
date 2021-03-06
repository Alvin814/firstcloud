package com.fit2cloud.ticket.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TicketWatchListExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    public TicketWatchListExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
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
     * This method corresponds to the database table ticket_watch_list
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
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
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
     * This class corresponds to the database table ticket_watch_list
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andDayUserIdIsNull() {
            addCriterion("day_user_id is null");
            return (Criteria) this;
        }

        public Criteria andDayUserIdIsNotNull() {
            addCriterion("day_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andDayUserIdEqualTo(String value) {
            addCriterion("day_user_id =", value, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdNotEqualTo(String value) {
            addCriterion("day_user_id <>", value, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdGreaterThan(String value) {
            addCriterion("day_user_id >", value, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("day_user_id >=", value, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdLessThan(String value) {
            addCriterion("day_user_id <", value, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdLessThanOrEqualTo(String value) {
            addCriterion("day_user_id <=", value, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdLike(String value) {
            addCriterion("day_user_id like", value, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdNotLike(String value) {
            addCriterion("day_user_id not like", value, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdIn(List<String> values) {
            addCriterion("day_user_id in", values, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdNotIn(List<String> values) {
            addCriterion("day_user_id not in", values, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdBetween(String value1, String value2) {
            addCriterion("day_user_id between", value1, value2, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserIdNotBetween(String value1, String value2) {
            addCriterion("day_user_id not between", value1, value2, "dayUserId");
            return (Criteria) this;
        }

        public Criteria andDayUserNameIsNull() {
            addCriterion("day_user_name is null");
            return (Criteria) this;
        }

        public Criteria andDayUserNameIsNotNull() {
            addCriterion("day_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andDayUserNameEqualTo(String value) {
            addCriterion("day_user_name =", value, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameNotEqualTo(String value) {
            addCriterion("day_user_name <>", value, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameGreaterThan(String value) {
            addCriterion("day_user_name >", value, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("day_user_name >=", value, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameLessThan(String value) {
            addCriterion("day_user_name <", value, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameLessThanOrEqualTo(String value) {
            addCriterion("day_user_name <=", value, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameLike(String value) {
            addCriterion("day_user_name like", value, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameNotLike(String value) {
            addCriterion("day_user_name not like", value, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameIn(List<String> values) {
            addCriterion("day_user_name in", values, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameNotIn(List<String> values) {
            addCriterion("day_user_name not in", values, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameBetween(String value1, String value2) {
            addCriterion("day_user_name between", value1, value2, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andDayUserNameNotBetween(String value1, String value2) {
            addCriterion("day_user_name not between", value1, value2, "dayUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserIdIsNull() {
            addCriterion("night_user_id is null");
            return (Criteria) this;
        }

        public Criteria andNightUserIdIsNotNull() {
            addCriterion("night_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andNightUserIdEqualTo(String value) {
            addCriterion("night_user_id =", value, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdNotEqualTo(String value) {
            addCriterion("night_user_id <>", value, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdGreaterThan(String value) {
            addCriterion("night_user_id >", value, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("night_user_id >=", value, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdLessThan(String value) {
            addCriterion("night_user_id <", value, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdLessThanOrEqualTo(String value) {
            addCriterion("night_user_id <=", value, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdLike(String value) {
            addCriterion("night_user_id like", value, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdNotLike(String value) {
            addCriterion("night_user_id not like", value, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdIn(List<String> values) {
            addCriterion("night_user_id in", values, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdNotIn(List<String> values) {
            addCriterion("night_user_id not in", values, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdBetween(String value1, String value2) {
            addCriterion("night_user_id between", value1, value2, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserIdNotBetween(String value1, String value2) {
            addCriterion("night_user_id not between", value1, value2, "nightUserId");
            return (Criteria) this;
        }

        public Criteria andNightUserNameIsNull() {
            addCriterion("night_user_name is null");
            return (Criteria) this;
        }

        public Criteria andNightUserNameIsNotNull() {
            addCriterion("night_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andNightUserNameEqualTo(String value) {
            addCriterion("night_user_name =", value, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameNotEqualTo(String value) {
            addCriterion("night_user_name <>", value, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameGreaterThan(String value) {
            addCriterion("night_user_name >", value, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("night_user_name >=", value, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameLessThan(String value) {
            addCriterion("night_user_name <", value, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameLessThanOrEqualTo(String value) {
            addCriterion("night_user_name <=", value, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameLike(String value) {
            addCriterion("night_user_name like", value, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameNotLike(String value) {
            addCriterion("night_user_name not like", value, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameIn(List<String> values) {
            addCriterion("night_user_name in", values, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameNotIn(List<String> values) {
            addCriterion("night_user_name not in", values, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameBetween(String value1, String value2) {
            addCriterion("night_user_name between", value1, value2, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andNightUserNameNotBetween(String value1, String value2) {
            addCriterion("night_user_name not between", value1, value2, "nightUserName");
            return (Criteria) this;
        }

        public Criteria andWatchDateIsNull() {
            addCriterion("watch_date is null");
            return (Criteria) this;
        }

        public Criteria andWatchDateIsNotNull() {
            addCriterion("watch_date is not null");
            return (Criteria) this;
        }

        public Criteria andWatchDateEqualTo(Date value) {
            addCriterionForJDBCDate("watch_date =", value, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("watch_date <>", value, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateGreaterThan(Date value) {
            addCriterionForJDBCDate("watch_date >", value, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("watch_date >=", value, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateLessThan(Date value) {
            addCriterionForJDBCDate("watch_date <", value, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("watch_date <=", value, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateIn(List<Date> values) {
            addCriterionForJDBCDate("watch_date in", values, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("watch_date not in", values, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("watch_date between", value1, value2, "watchDate");
            return (Criteria) this;
        }

        public Criteria andWatchDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("watch_date not between", value1, value2, "watchDate");
            return (Criteria) this;
        }

        public Criteria andSqlCriterion(String value) {
            addCriterion("(" + value + ")");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ticket_watch_list
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
     * This class corresponds to the database table ticket_watch_list
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