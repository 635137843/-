package com.hmx.dao;

import com.hmx.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName CommentRepository
 * @Description 评论持久化层
 * @Author xin
 * @Date 2020/3/10 19:11
 * @Version 1.0
 **/
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBlogIdAndParentCommentIsNull(Long blogId, Sort sort);


}
