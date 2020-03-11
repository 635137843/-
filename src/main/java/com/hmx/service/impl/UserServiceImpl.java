package com.hmx.service.impl;

import com.hmx.dao.UserRepository;
import com.hmx.pojo.User;
import com.hmx.service.UserService;
import com.hmx.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description UserService实现类
 * @Author xin
 * @Date 2020/3/8 14:01
 * @Version 1.0
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.md5(password));
        return user;
    }
}
