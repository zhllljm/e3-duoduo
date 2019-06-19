package com.zhl.controller;

import com.zhl.pojo.Item;
import com.zhl.pojo.TbItem;
import com.zhl.pojo.TbItemDesc;
import com.zhl.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品详情页展示Controller
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable long itemId, Model model) {
        System.out.println(itemId);
        //调用服务商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        //取商品描述信息
        TbItemDesc itemDesc = itemService.findTbItemDescById(itemId);
        //把信息传递给页面
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        // 返回逻辑试图
        return  "item";
    }
}
