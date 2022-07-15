package com.xiaojie.gitlab4j.api.models;

import com.xiaojie.gitlab4j.api.utils.JacksonJson;

public class Markdown {

    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return (JacksonJson.toJsonString(this));
    }
}
