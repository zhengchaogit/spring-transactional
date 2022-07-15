package com.xiaojie.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.xiaojie.entity.UserInfo;

@Mapper
public interface UserInfoMapper {
    
    
    @Select("select id, user_name as userName, access_token as accessToken from user_info")
    List<UserInfo> findUsers();
}
