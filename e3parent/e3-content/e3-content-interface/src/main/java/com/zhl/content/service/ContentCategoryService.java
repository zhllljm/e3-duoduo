package com.zhl.content.service;

import com.zhl.pojo.EasyUITreeNode;
import com.zhl.pojo.TbContent;
import com.zhl.utils.E3Result;

import java.util.List;

public interface ContentCategoryService {

    public List<EasyUITreeNode> getContentCategoryList(long parentId);

    public E3Result addContentCategory(long parentId, String name);

    public E3Result editContentCategoryName(Long id, String name);

    public E3Result deleteContentCategory(Long id);
}
