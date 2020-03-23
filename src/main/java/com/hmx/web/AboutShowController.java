package com.hmx.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName AboutShowController
 * @Description TODO
 * @Author xin
 * @Date 2020/3/24 1:27
 * @Version 1.0
 **/
@Controller
public class AboutShowController {

    @RequestMapping("/about")
    public String about() {
        return "about";
    }
}
