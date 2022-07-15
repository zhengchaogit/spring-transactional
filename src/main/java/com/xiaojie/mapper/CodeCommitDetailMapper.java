package com.xiaojie.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.xiaojie.entity.CodeCommitDetail;

public interface CodeCommitDetailMapper {
    
    @Insert("INSERT INTO `code_commit_detail`(code_commit_id,task_execution_time,code_commit_content,commit_time) VALUES(#{codeCommitId},#{taskExecutionTime},#{codeCommitContent},#{commitTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(CodeCommitDetail codeCommitDetail);
    
    @Delete("delete from code_commit_detail where  task_execution_time =  #{taskExecutionTime}")
    Integer deleteByTaskExecutionTime(@Param("taskExecutionTime") String taskExecutionTime);
}
