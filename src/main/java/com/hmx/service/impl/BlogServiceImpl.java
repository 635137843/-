package com.hmx.service.impl;

import com.hmx.NotFoundException;
import com.hmx.dao.BlogRepository;
import com.hmx.pojo.Blog;
import com.hmx.pojo.Type;
import com.hmx.service.BlogService;
import com.hmx.utils.MarkdownUtils;
import com.hmx.utils.MyBeanUtils;
import com.hmx.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @ClassName BlogServiceImpl
 * @Description 博客Service实现类
 * @Author xin
 * @Date 2020/3/8 18:43
 * @Version 1.0
 **/
@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll((Specification<Blog>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
            }
            if (blog.getTypeId() != null) {
                predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
            }
            if (blog.isRecommend()) {
                predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll((Specification<Blog>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.join("tags").get("id"), tagId), pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "updateTime"));
        return blogRepository.findTop(pageable);
    }

    @Override
    public List<Map.Entry<String, List<Blog>>> archiveBlog() {
        List<String> years = blogRepository.findByGroup();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }

        List<Map.Entry<String, List<Blog>>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> Integer.parseInt(o2.getKey()) - Integer.parseInt(o1.getKey()));
        return list;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).get();
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
