package com.xiaojie.service.check;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaojie.annotation.MyTransactional;
import com.xiaojie.service.OrderService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class TranInvalidCaseByThrowCheckException {

    @Autowired
    private OrderService orderService;


    @MyTransactional
    public void add() throws FileNotFoundException {
        orderService.insert();
        new FileInputStream("1.txt");
        orderService.insert();
    }


   



}
