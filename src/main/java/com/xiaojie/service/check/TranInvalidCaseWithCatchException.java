package com.xiaojie.service.check;


import com.xiaojie.annotation.MyTransactional;
import com.xiaojie.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranInvalidCaseWithCatchException {

    @Autowired
    private OrderService orderService;


    @MyTransactional
    public void add() {
        orderService.insert();
        try {
            int i = 1 % 0;
        } catch (Exception e) {

        }
        orderService.insert();
    }
    

}
