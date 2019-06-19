package com.zhl.controller;

import com.zhl.content.service.ContentCategoryService;
import com.zhl.content.service.ContentService;
import com.zhl.pojo.TbContent;
import com.zhl.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        List<TbContent> ad1List = contentService.getContentList(89);
        model.addAttribute("ad1List",ad1List);
        return "index";
    }
}
