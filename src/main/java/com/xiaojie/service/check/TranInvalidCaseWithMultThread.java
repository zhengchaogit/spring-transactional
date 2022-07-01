package com.xiaojie.service.check;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaojie.annotation.MyTransactional;
import com.xiaojie.service.OrderService;

@Service
public class TranInvalidCaseWithMultThread {


    @Autowired
    private OrderService orderService;

    @MyTransactional
    public void save()  {
        orderService.insert();
     // 使用匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                    orderService.saveOrder(0);
               
            }
        }).start();
    }
}
