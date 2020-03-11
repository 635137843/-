package com.hmx.service.impl;

import com.hmx.dao.CommentRepository;
import com.hmx.pojo.Comment;
import com.hmx.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CommentServiceImpl
 * @Description 评论Service实现类
 * @Author xin
 * @Date 2020/3/10 19:10
 * @Version 1.0
 **/

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentIsNull(id, Sort.by(Sort.Direction.ASC, "createTime"));
        return eachComment(comments);
    }

    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> list = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            list.add(c);
        }
        combineChildren(list);
        return list;
    }

    private List<Comment> combineChildren(List<Comment> comments) {
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            List<Comment> list = new ArrayList<>();
            recursively(list, comment);
            comment.setReplyComments(list);
            commentList.add(comment);
        }
        return commentList;
    }

    private void recursively(List<Comment> comments, Comment comment) {
        List<Comment> replies = comment.getReplyComments();
        if (replies.size() > 0) {
            for (Comment reply : replies) {
                comments.add(reply);
                recursively(comments, reply);
            }
        }
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
