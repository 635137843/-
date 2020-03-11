package com.hmx.dao;

import com.hmx.pojo.Tag;
import com.hmx.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName TagRepository
 * @Description 标签持久化层
 * @Author xin
 * @Date 2020/3/8 14:31
 * @Version 1.0
 **/
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    @Query("SELECT t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
