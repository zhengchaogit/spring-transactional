package com.xiaojie.service.check;


import com.xiaojie.annotation.MyTransactional;
import com.xiaojie.service.OrderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TranInvalidCaseWithFinalAndStaticMethod {



    @MyTransactional
    public final void add(OrderService orderService) {
        orderService.insert();
        int i = 1 % 0;
        orderService.insert();
    }


    @MyTransactional
    public static void save(OrderService orderService) {
        orderService.insert();
        int i = 1 % 0;
        orderService.insert();
    }
}
