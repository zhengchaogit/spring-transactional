package com.xiaojie.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CodeStatisticsTask implements Serializable{

    private Integer id;//主键
    private Integer processStatus;
    private String taskExecutionTime;
    private String processMsg;
    private Date createTime;
    private Date updateTime;
    
}
