package com.xiaojie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaojie.entity.UserInfo;
import com.xiaojie.mapper.UserInfoMapper;
import com.xiaojie.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> getUsers() {
        return userInfoMapper.findUsers();
    }

}
