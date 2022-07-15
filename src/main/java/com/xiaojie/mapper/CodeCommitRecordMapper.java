package com.xiaojie.mapper;

import com.xiaojie.entity.CodeCommitRecord;

import java.util.Date;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface CodeCommitRecordMapper {
    
    @Insert("INSERT INTO `code_commit_record`(code_commit_title,code_commit_total,code_commit_additions,code_commit_deletions, commit_branch_name, project_id,project_name,user_id,user_name,commit_time) VALUES(#{codeCommitTitle},#{codeCommitTotal},#{codeCommitAdditions},#{codeCommitDeletions},#{commitBranchName},#{projectId},#{projectName},#{userId},#{userName},#{commitTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    Integer save(CodeCommitRecord codeCommitRecord);
    
    @Delete("delete from code_commit_record where commit_time between #{startCommitTime} and #{endCommitTime}")
    Integer deleteByCommitTime(@Param("startCommitTime") Date startCommitTime,@Param("endCommitTime") Date endCommitTime);
}
