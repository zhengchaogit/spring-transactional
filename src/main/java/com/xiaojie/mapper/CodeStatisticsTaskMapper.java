package com.xiaojie.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.xiaojie.entity.CodeStatisticsTask;

public interface CodeStatisticsTaskMapper {
    
    @Insert("INSERT INTO `code_statistics_task`(process_status,process_msg,task_execution_time,create_time,update_time) VALUES(#{processStatus},#{processMsg},#{taskExecutionTime},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(CodeStatisticsTask codeStatisticsTask);
    
    @Update("update code_statistics_task set process_status = #{processStatus},update_time = #{updateTime} where task_execution_time = #{taskExecutionTime}")
    void updateStatusByTaskExecutionTime(@Param("processStatus") Integer processStatus, @Param("updateTime") Date updateTime, @Param("taskExecutionTime") String taskExecutionTime);
    
    
    @Select("select id, process_status as processStatus, process_msg as processMsg, task_execution_time as taskExecutionTime  from code_statistics_task where task_execution_time = #{taskExecutionTime}")
    List<CodeStatisticsTask> selectByTaskExecutionTime(@Param(value = "taskExecutionTime") String taskExecutionTime);
}
