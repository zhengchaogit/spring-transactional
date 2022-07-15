package com.xiaojie.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaojie.entity.CodeStatisticsTask;
import com.xiaojie.gitlab4j.api.GitLabApiException;
import com.xiaojie.mapper.CodeStatisticsTaskMapper;
import com.xiaojie.service.CodeStatisticsTaskService;

@Service
public class CodeStatisticsTaskServiceImpl implements CodeStatisticsTaskService{
    
    @Autowired
    private CodeStatisticsTaskMapper codeStatisticsTaskMapper;

    @Override
    public void updateStatusByTaskExecutionTime(Integer processStatus, Date updateTime,String taskExecutionTime) {
        codeStatisticsTaskMapper.updateStatusByTaskExecutionTime(processStatus, updateTime,taskExecutionTime);
        
    }

    @Override
    public void save(CodeStatisticsTask codeStatisticsTask){
        codeStatisticsTaskMapper.save(codeStatisticsTask);
    }

    @Override
    public List<CodeStatisticsTask> selectByTaskExecutionTime(String taskExecutionTime) {
        return codeStatisticsTaskMapper.selectByTaskExecutionTime(taskExecutionTime);
    }


}
