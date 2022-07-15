package com.xiaojie.gitlab4j.api.models;

import com.xiaojie.gitlab4j.api.utils.JacksonJson;

public class TaskCompletionStatus {

    private Integer count;
    private Integer completedCount;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    @Override
    public String toString() {
        return (JacksonJson.toJsonString(this));
    }

}
