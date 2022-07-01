package com.xiaojie.check;


import org.springframework.beans.factory.annotation.Autowired;

import com.xiaojie.annotation.MyTransactional;
import com.xiaojie.service.OrderService;

public class TranInvalidCaseWithoutInjectSpring {

    private OrderService orderService;

    public TranInvalidCaseWithoutInjectSpring(OrderService orderService) {
        this.orderService = orderService;
    }

    @MyTransactional
    public boolean add(int a){
        orderService.insert();
        int i = 1/a;
        orderService.insert();
        return false;
    }
}
