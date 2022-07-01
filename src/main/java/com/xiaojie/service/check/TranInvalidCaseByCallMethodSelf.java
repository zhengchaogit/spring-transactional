package com.xiaojie.service.check;


import com.xiaojie.annotation.MyTransactional;
import com.xiaojie.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranInvalidCaseByCallMethodSelf {


    @Autowired
    private OrderService orderService;

    @Autowired
    private TranInvalidCaseByCallMethodSelf tranInvalidCaseByCallMethodSelf;


    public void save() {
        //0、调用本类方法
        this.add();
    }


    @MyTransactional
    public void add() {
        orderService.insert();
        int i = 1 % 0;
        orderService.insert();
    }
}
