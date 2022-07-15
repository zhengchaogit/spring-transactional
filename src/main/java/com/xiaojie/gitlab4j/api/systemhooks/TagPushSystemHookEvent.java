package com.xiaojie.gitlab4j.api.systemhooks;

import com.xiaojie.gitlab4j.api.utils.JacksonJson;
import com.xiaojie.gitlab4j.api.webhook.AbstractPushEvent;

public class TagPushSystemHookEvent extends AbstractPushEvent implements SystemHookEvent {

    public static final String TAG_PUSH_EVENT = "tag_push";

    @Override
    public String toString() {
        return (JacksonJson.toJsonString(this));
    }
}
