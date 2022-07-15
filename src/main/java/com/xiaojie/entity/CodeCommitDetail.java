package com.xiaojie.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CodeCommitDetail implements Serializable{

    private Integer id;//主键
    private Integer codeCommitId;//代码提交id
    private String codeCommitContent;//提交内容差异对比
    private Date commitTime;//提交时间
    private String taskExecutionTime;//任务执行时间
}
