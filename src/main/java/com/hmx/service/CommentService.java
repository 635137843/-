package com.hmx.service;

import com.hmx.pojo.Comment;

import java.util.List;

/**
 * @ClassName CommentService
 * @Description 评论Service接口
 * @Author xin
 * @Date 2020/3/10 19:08
 * @Version 1.0
 **/
public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);

}
