package com.hmx.dao;

import com.hmx.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName BlogRepository
 * @Description 博客持久化层
 * @Author xin
 * @Date 2020/3/9 0:16
 * @Version 1.0
 **/
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("SELECT b FROM Blog b WHERE b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("SELECT b from Blog b WHERE b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Blog b SET b.views = b.views+1 WHERE b.id = ?1")
    int updateViews(Long id);
}
