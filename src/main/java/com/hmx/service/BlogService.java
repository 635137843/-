package com.hmx.service;

import com.hmx.pojo.Blog;
import com.hmx.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName BlogService
 * @Description 博客Service接口
 * @Author xin
 * @Date 2020/3/8 18:43
 * @Version 1.0
 **/
public interface BlogService {

    Blog getBlog(Long id);

    Blog getBlogAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
