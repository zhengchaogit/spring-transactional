package com.xiaojie.controller;

import com.xiaojie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/save")
    public String save(Integer a){
        return orderService.saveOrder(a);
    }
}
