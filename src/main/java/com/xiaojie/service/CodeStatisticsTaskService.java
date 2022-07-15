package com.xiaojie.service;


import java.util.Date;
import java.util.List;

import com.xiaojie.entity.CodeStatisticsTask;
import com.xiaojie.gitlab4j.api.GitLabApiException;


public interface CodeStatisticsTaskService {

    void updateStatusByTaskExecutionTime(Integer processStatus, Date updateTime,String taskExecutionTime);
    
    public void  save(CodeStatisticsTask codeStatisticsTask);
    
    List<CodeStatisticsTask> selectByTaskExecutionTime(String taskExecutionTime);
}
