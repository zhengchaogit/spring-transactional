package com.xiaojie.service;

import java.util.List;

import com.xiaojie.entity.ProjectInfo;

public interface ProjectInfoService {

    public void save(ProjectInfo projectInfo);
    
    public List<ProjectInfo> selectByUserIdAndProjectName(Integer userId, String projectId);
}
