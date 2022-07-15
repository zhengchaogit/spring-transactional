package com.xiaojie.service;

import java.util.Date;

import com.xiaojie.entity.CodeCommitDetail;
import com.xiaojie.gitlab4j.api.GitLabApiException;

public interface CodeCommitDetailService {

    public void  save(CodeCommitDetail codeCommitDetail) ;
    
    Integer deteleByTaskExecutionTime(String taskExecutionTime);
}
