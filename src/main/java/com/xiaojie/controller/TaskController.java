package com.xiaojie.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaojie.service.impl.CodeStatistics;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 手动触发任务
 * @date 2022年7月15日下午12:17:07
 */
@Slf4j
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
        //校验执行任务时间是否是周日时间
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            Date date =simpleDateFormat.parse(taskExecutionTime+" 00:00:00");
            int compareResult = DateUtil.compare(new Date(), date);
            if(compareResult==1) {
                if(isWeekend(taskExecutionTime)) {
                    codeStatistics.task(taskExecutionTime,0);
                }else {
                    return new ResponseEntity<>("日期必须输入周末的日期",HttpStatus.OK);
                }
            }else {
                return new ResponseEntity<>("当前时间不可触发定时任务，手动触发时间必须为以往的周末时日期",HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("手动触发代码统计定时任务出现异常",e);
            return new ResponseEntity<>("请稍后再试，日期格式为：yyy-MM-dd",HttpStatus.OK);
        }
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }

    public static Boolean isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }
 }
    public static void main(String[] args) throws ParseException {
        String taskExecutionTime = "2022-07-15";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        Date date =simpleDateFormat.parse(taskExecutionTime+" 15:00:00");
        System.out.println(DateUtil.compare(new Date(), date));
    }


}
