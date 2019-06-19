package com.zhl.controller;

import com.zhl.content.service.ContentCategoryService;
import com.zhl.pojo.EasyUITreeNode;
import com.zhl.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
        return list;
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public E3Result addContentCategory(long parentId, String name){
        E3Result result = contentCategoryService.addContentCategory(parentId,name);
        return  result;
    }

    /**
     * 重命名节点名
     * @param id 节点id
     * @param name 节点名
     * @return
     */
    @RequestMapping(value = "/content/category/update")
    @ResponseBody
    public E3Result editContentCategoryName(Long id ,String name){
        //调用服务重命名节点
        E3Result result= contentCategoryService.editContentCategoryName(id,name);
        return result;
    }

    /**
     * 删除内容节点
     * @param id
     * @return
     */
    @RequestMapping(value = "/content/category/delete", method= RequestMethod.POST)
    @ResponseBody
    public E3Result deleteContentCategory(Long id){
        //调用服务删除节点
        E3Result result=contentCategoryService.deleteContentCategory(id);
        return result;
    }
}
