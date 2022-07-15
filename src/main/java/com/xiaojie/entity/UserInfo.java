package com.xiaojie.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInfo implements Serializable{

    private Integer id;//主键
    private String userName;//用户姓名
    private String accessToken;//用户唯一token
}
