package com.xiaojie.enums;

public enum TaskStatusEnum {

    PROCESSING(1, "处理中"), 
    SUCCESS(2, "成功"), 
    FAIL(3, "失败");

    private Integer code;

    private String name;

    private TaskStatusEnum(Integer code, String name) {

        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
