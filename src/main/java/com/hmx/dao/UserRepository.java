package com.hmx.dao;

import com.hmx.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName UserRepository
 * @Description User持久化层
 * @Author xin
 * @Date 2020/3/8 21:32
 * @Version 1.0
 **/

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

}
