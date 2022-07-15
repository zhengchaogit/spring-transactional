package com.xiaojie.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaojie.entity.CodeCommitDetail;
import com.xiaojie.mapper.CodeCommitDetailMapper;
import com.xiaojie.service.CodeCommitDetailService;

@Service
public class CodeCommitDetailServiceImpl implements CodeCommitDetailService{

    
    @Autowired
    private CodeCommitDetailMapper codeCommitDetailMapper;

    @Override
    public void save(CodeCommitDetail codeCommitDetail)  {
        codeCommitDetailMapper.save(codeCommitDetail);
    }

    @Override
    public Integer deteleByCommitTime(Date startCommitTime, Date endCommitTime) {
       return codeCommitDetailMapper.deleteByCommitTime(startCommitTime, endCommitTime);
        
    }
    
}
