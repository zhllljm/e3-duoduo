package com.zhl.service;

import com.zhl.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

    public List<EasyUITreeNode>  getCatList(long parentId);
}
