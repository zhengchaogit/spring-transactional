package com.xiaojie.mapper;

import com.xiaojie.entity.ProjectInfo;
import com.xiaojie.entity.UserInfo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProjectInfoMapper {
    
    @Insert("INSERT INTO `project_info`(user_id,project_id,project_name,project_url) VALUES(#{userId},#{projectId},#{projectName},#{projectUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(ProjectInfo projectInfo);
    
    @Select("select id, user_id as userId, project_name as projectName, project_url as projectUrl from project_info where user_id = #{userId} and project_id = #{projectId}")
    List<ProjectInfo> selectByUserIdAndProjectName(@Param(value = "userId") Integer userId,@Param(value = "projectId")String projectId);
}
