package com.xws.bootpro.ScheduledTasks;
 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
public class ScheduledExample {
    /**
     * scheduled
     */
    protected String orderByClause;
 
    /**
     * scheduled
     */
    protected boolean distinct;
 
    /**
     * scheduled
     */
    protected List<Criteria> oredCriteria;
 
    /**
     * This method corresponds to the database table scheduled
     */
    public ScheduledExample() {
        oredCriteria = new ArrayList<Criteria>();
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public String getOrderByClause() {
        return orderByClause;
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public boolean isDistinct() {
        return distinct;
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }
 
    /**
     * This method corresponds to the database table scheduled
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }
 
    /**
     * This class corresponds to the database table scheduled
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
 
        public Criteria andTaskKeyIsNull() {
            addCriterion("task_key is null");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyIsNotNull() {
            addCriterion("task_key is not null");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyEqualTo(String value) {
            addCriterion("task_key =", value, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyNotEqualTo(String value) {
            addCriterion("task_key <>", value, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyGreaterThan(String value) {
            addCriterion("task_key >", value, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyGreaterThanOrEqualTo(String value) {
            addCriterion("task_key >=", value, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyLessThan(String value) {
            addCriterion("task_key <", value, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyLessThanOrEqualTo(String value) {
            addCriterion("task_key <=", value, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyLike(String value) {
            addCriterion("task_key like", value, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyNotLike(String value) {
            addCriterion("task_key not like", value, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyIn(List<String> values) {
            addCriterion("task_key in", values, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyNotIn(List<String> values) {
            addCriterion("task_key not in", values, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyBetween(String value1, String value2) {
            addCriterion("task_key between", value1, value2, "taskKey");
            return (Criteria) this;
        }
 
        public Criteria andTaskKeyNotBetween(String value1, String value2) {
            addCriterion("task_key not between", value1, value2, "taskKey");
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
 
        public Criteria andCronIsNull() {
            addCriterion("cron is null");
            return (Criteria) this;
        }
 
        public Criteria andCronIsNotNull() {
            addCriterion("cron is not null");
            return (Criteria) this;
        }
 
        public Criteria andCronEqualTo(String value) {
            addCriterion("cron =", value, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronNotEqualTo(String value) {
            addCriterion("cron <>", value, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronGreaterThan(String value) {
            addCriterion("cron >", value, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronGreaterThanOrEqualTo(String value) {
            addCriterion("cron >=", value, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronLessThan(String value) {
            addCriterion("cron <", value, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronLessThanOrEqualTo(String value) {
            addCriterion("cron <=", value, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronLike(String value) {
            addCriterion("cron like", value, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronNotLike(String value) {
            addCriterion("cron not like", value, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronIn(List<String> values) {
            addCriterion("cron in", values, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronNotIn(List<String> values) {
            addCriterion("cron not in", values, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronBetween(String value1, String value2) {
            addCriterion("cron between", value1, value2, "cron");
            return (Criteria) this;
        }
 
        public Criteria andCronNotBetween(String value1, String value2) {
            addCriterion("cron not between", value1, value2, "cron");
            return (Criteria) this;
        }
 
        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }
 
        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }
 
        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }
 
        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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
 
        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }
 
        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }
 
    /**
     * scheduled
     */
    public static class Criteria extends GeneratedCriteria {
 
        protected Criteria() {
            super();
        }
    }
 
    /**
     * This class corresponds to the database table scheduled
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