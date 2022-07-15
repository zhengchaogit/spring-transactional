package com.xiaojie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaojie.service.impl.CodeStatistics;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 手动触发任务
 * @date 2022年7月15日下午12:17:07
 */
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    
    @Autowired
    private CodeStatistics codeStatistics;


    /**
     * 
     * 手动触发指定日期定时任务
     * @date 2022年7月15日下午12:17:30
     * 
     * @param taskExecutionTime
     * @return
     */
    @GetMapping(value = "/trigger")
    public ResponseEntity<Object> task(String taskExecutionTime){
        codeStatistics.task(taskExecutionTime,0);
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }



}
