package com.zhl.service.impl;

import com.zhl.mapper.TbItemCatMapper;
import com.zhl.pojo.EasyUITreeNode;
import com.zhl.pojo.TbItemCat;
import com.zhl.pojo.TbItemCatExample;
import com.zhl.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;


    @Override
    public List<EasyUITreeNode> getCatList(long parentId) {

        //根据parentId查询节点列表
        TbItemCatExample example = new TbItemCatExample();

        //设置查询条件
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);

        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {

            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setState(tbItemCat.getIsParent()?"closed" : "open");
            node.setText(tbItemCat.getName());

            //添加列表
            resultList.add(node);
        }
        //返回
        return resultList;
    }
}
