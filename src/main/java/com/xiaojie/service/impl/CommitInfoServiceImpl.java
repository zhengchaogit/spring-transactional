package com.xiaojie.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaojie.entity.CodeCommitRecord;
import com.xiaojie.mapper.CodeCommitRecordMapper;
import com.xiaojie.service.CommitInfoService;

@Service
public class CommitInfoServiceImpl implements CommitInfoService {
    
    @Autowired
    private CodeCommitRecordMapper commitInfoMapper;
    
    
    @Override
    public void save (CodeCommitRecord commitInfo) {
        commitInfoMapper.save(commitInfo);
    }


    @Override
    public Integer deteleByCommitTime(Date startCommitTime, Date endCommitTime) {
        return commitInfoMapper.deleteByCommitTime(startCommitTime, endCommitTime);
    }
        


    
}
