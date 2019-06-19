package com.zhl.controller;

import com.zhl.content.service.ContentService;
import com.zhl.pojo.EasyUIResult;
import com.zhl.pojo.TbContent;
import com.zhl.pojo.TbContentExample;
import com.zhl.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 根据分类内容ID，查询内容列表，分页显示
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIResult gertContentList(Long categoryId,Integer page,Integer rows){
        EasyUIResult result = contentService.gertContentList(categoryId,page,rows);
        return result;
    }

    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result addContent(TbContent content) {
        E3Result result = contentService.addContent(content);
        return result;
    }

    /**
     *修改内容功能实现
     * @param content
     * @return: com.e3mall.common.utils.E3Result
     * @Description:
     */
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public E3Result editContent(TbContent content){
        //调用服务修改内容
        E3Result result= contentService.editContent(content);
        return result;
    }

    /**
     * 异步重新回显内容数据
     * @return
     */
    @RequestMapping("/query/content/{id}")
    @ResponseBody
    public TbContent selectContent(@PathVariable  Long id){
        TbContent result=contentService.selectByIdContent(id);
        return result;
    }


    /**
     * 删除内容信息
     * @param ids
     * @return
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public E3Result deleteContent(long[] ids){
        E3Result result=contentService.deleteBatchContent(ids);
        return result;
    }



}
