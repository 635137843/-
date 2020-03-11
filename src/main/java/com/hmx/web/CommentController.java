package com.hmx.web;

import com.hmx.pojo.Comment;
import com.hmx.pojo.User;
import com.hmx.service.BlogService;
import com.hmx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName CommentController
 * @Description 评论的控制器层
 * @Author xin
 * @Date 2020/3/10 19:03
 * @Version 1.0
 **/

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog::commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
