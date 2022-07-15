package com.xiaojie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaojie.entity.ProjectInfo;
import com.xiaojie.mapper.ProjectInfoMapper;
import com.xiaojie.service.ProjectInfoService;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService{

    @Autowired
    private ProjectInfoMapper projectInfoMapper;
    
    @Override
    public void save(ProjectInfo projectInfo) {
        projectInfoMapper.save(projectInfo);
    }

    @Override
    public List<ProjectInfo> selectByUserIdAndProjectName(Integer userId, String projectId) {
        return projectInfoMapper.selectByUserIdAndProjectName(userId, projectId);
        
    }

}
