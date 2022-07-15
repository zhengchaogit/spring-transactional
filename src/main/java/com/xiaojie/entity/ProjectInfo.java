package com.xiaojie.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class ProjectInfo implements Serializable {
    
    private Integer id;//主键
    private Integer userId;//用户id
    private String projectId;//项目Id
    private String projectName;//项目名称
    private String projectUrl;//项目地址
}
