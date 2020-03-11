package com.hmx.service;

import com.hmx.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName TagService
 * @Description 标签Service接口
 * @Author xin
 * @Date 2020/3/10 12:47
 * @Version 1.0
 **/
public interface TagService {

    Tag saveTag(Tag type);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag type);

    void deleteTag(Long id);

    List<Tag> listTagTop(int size);
}
