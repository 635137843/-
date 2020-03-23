package com.hmx.web;

import com.hmx.pojo.Tag;
import com.hmx.service.BlogService;
import com.hmx.service.TagService;
import com.hmx.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName TagShowController
 * @Description TODO
 * @Author xin
 * @Date 2020/3/23 21:02
 * @Version 1.0
 **/
@Controller
public class TagsShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                       @PathVariable Long id, Pageable pageable, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
