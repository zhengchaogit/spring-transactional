package com.xiaojie.mapper;

import com.xiaojie.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface OrderMapper {
    @Insert("INSERT INTO `t_order`(name,order_createtime,order_state,order_money,commodity_id) VALUES(#{name},now(),1,#{orderMoney},#{commodityId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(Order order);
}
