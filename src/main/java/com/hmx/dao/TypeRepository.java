package com.hmx.dao;

import com.hmx.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName TypeRepository
 * @Description 类型持久化层
 * @Author xin
 * @Date 2020/3/8 9:31
 * @Version 1.0
 **/
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query("SELECT t from Type t")
    List<Type> findTop(Pageable pageable);

}
