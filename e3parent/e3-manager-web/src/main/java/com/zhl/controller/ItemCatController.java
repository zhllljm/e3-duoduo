package com.zhl.controller;

import com.zhl.pojo.EasyUITreeNode;
import com.zhl.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> geItemCatList(@RequestParam(value = "id",defaultValue = "0")Long parsentId){

        List<EasyUITreeNode> list = itemCatService.getCatList(parsentId);
        return list;
    }
}
