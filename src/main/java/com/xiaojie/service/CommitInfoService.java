package com.xiaojie.service;

import java.util.Date;

import com.xiaojie.entity.CodeCommitRecord;
import com.xiaojie.gitlab4j.api.GitLabApiException;

public interface CommitInfoService {
    
    public void  save(CodeCommitRecord commitInfo);
    
    Integer deteleByTaskExecutionTime(String taskExecutionTime);

    
}
