package com.zhl.controller;

import com.zhl.search.service.SearchItemService;
import com.zhl.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchService;

    @RequestMapping("/index/item/import")
    @ResponseBody
public E3Result importItemList(){
        E3Result e3Result = searchService.importAllItems();
        return e3Result;
    }
}