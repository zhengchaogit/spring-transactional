package com.xiaojie.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Order implements Serializable {
    private Integer id;//主键
    private String name;//订单名称
    private Date orderCreateTime;//订单时间
    private Double orderMoney;//订单钱数
    private Integer orderState;//订单状态
    private Integer commodityId;//商品id
}
