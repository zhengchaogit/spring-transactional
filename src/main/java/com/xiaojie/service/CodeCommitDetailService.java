package com.xiaojie.service;

import java.util.Date;

import com.xiaojie.entity.CodeCommitDetail;
import com.xiaojie.gitlab4j.api.GitLabApiException;

public interface CodeCommitDetailService {

    public void  save(CodeCommitDetail codeCommitDetail) ;
    
    Integer deteleByCommitTime(Date startCommitTime, Date endCommitTime);
}
