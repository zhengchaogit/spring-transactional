package com.xiaojie.mapper;

import com.xiaojie.entity.CodeCommitRecord;

import java.util.Date;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface CodeCommitRecordMapper {
    
    @Insert("INSERT INTO `code_commit_record`(code_commit_title,code_commit_total,code_commit_additions,code_commit_deletions, commit_branch_name, project_id,project_name,user_id,user_name,task_execution_time,commit_time) VALUES(#{codeCommitTitle},#{codeCommitTotal},#{codeCommitAdditions},#{codeCommitDeletions},#{commitBranchName},#{projectId},#{projectName},#{userId},#{userName},#{taskExecutionTime},#{commitTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    Integer save(CodeCommitRecord codeCommitRecord);
    
    @Delete("delete from code_commit_record where task_execution_time =  #{taskExecutionTime}")
    Integer deleteByTaskExecutionTime(@Param("taskExecutionTime") String taskExecutionTime);
}
