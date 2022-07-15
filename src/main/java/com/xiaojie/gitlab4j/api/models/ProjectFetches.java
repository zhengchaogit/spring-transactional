package com.xiaojie.gitlab4j.api.models;

import java.util.Date;
import java.util.List;

import com.xiaojie.gitlab4j.api.utils.JacksonJson;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ProjectFetches {

    public static class DateCount {

        private Integer count;

        @JsonSerialize(using = JacksonJson.DateOnlySerializer.class)
        private Date date;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    public static class Fetches {

        private Integer total;
        private List<DateCount> days;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<DateCount> getDays() {
            return days;
        }

        public void setDays(List<DateCount> days) {
            this.days = days;
        }
    }

    private Fetches fetches;

    public Fetches getFetches() {
        return fetches;
    }

    public void setFetches(Fetches fetches) {
        this.fetches = fetches;
    }

    @Override
    public String toString() {
        return (JacksonJson.toJsonString(this));
    }
}
