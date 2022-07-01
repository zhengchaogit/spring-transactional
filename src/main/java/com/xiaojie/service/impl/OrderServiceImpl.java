package com.xiaojie.service.impl;

import com.xiaojie.annotation.MyTransactional;
import com.xiaojie.entity.Order;
import com.xiaojie.mapper.OrderMapper;
import com.xiaojie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    
    @Override
    public String insert() {
        Order order=new Order();
        order.setName("测试订单1");
        order.setOrderMoney(100D);
        order.setCommodityId(10000);
        orderMapper.save(order);
        return "订单添加成功";
    }

    @Override
    @MyTransactional
    public  String  saveOrder(int a) {
        Order order=new Order();
        order.setName("测试订单2");
        order.setCommodityId(20000);
        order.setOrderMoney(200D);
        orderMapper.save(order);
        a=1/a;
        Order order2=new Order();
        order2.setName("测试订单3");
        order2.setOrderMoney(300D);
        order2.setCommodityId(30000);
        orderMapper.save(order2);
        return "订单添加成功";
    }

    
}
