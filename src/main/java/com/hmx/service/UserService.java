package com.hmx.service;

import com.hmx.pojo.User;

/**
 * @ClassName UserService
 * @Description 用户Service接口
 * @Author xin
 * @Date 2020/3/8 14:00
 * @Version 1.0
 **/
public interface UserService {
    User checkUser(String username, String password);
}
