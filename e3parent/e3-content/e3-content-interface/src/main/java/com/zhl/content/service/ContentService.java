package com.zhl.content.service;

import com.zhl.pojo.EasyUIResult;
import com.zhl.pojo.TbContent;
import com.zhl.utils.E3Result;

import java.util.List;

public interface ContentService {

    public List<TbContent> getContentList(long categoryId);

    /**
     * 根据分类内容ID，查询内容列表，分页显示
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    public EasyUIResult gertContentList(Long categoryId, Integer page, Integer rows);

    /**
     * 添加内容
     * @param content
     * @return
     */
    public E3Result addContent(TbContent content);

    /**
     * 修改内容
     * @param content
     * @return
     */
    public E3Result editContent(TbContent content);

    /**
     * 回显富文本内容
     * @param id
     * @return
     */
    public TbContent selectByIdContent(Long id);

    /**
     * 根据Id删除
     * @param ids
     * @return
     */
    public E3Result deleteBatchContent(long[] ids);
}
