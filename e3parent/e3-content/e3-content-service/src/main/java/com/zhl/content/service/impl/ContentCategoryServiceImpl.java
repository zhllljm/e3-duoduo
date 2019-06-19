package com.zhl.content.service.impl;

import com.zhl.content.service.ContentCategoryService;
import com.zhl.mapper.TbContentCategoryMapper;
import com.zhl.mapper.TbContentMapper;
import com.zhl.pojo.*;
import com.zhl.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        // 1、取查询参数id，parentId
        // 2、根据parentId查询tb_content_category，查询子节点列表。
        TbContentCategoryExample example = new TbContentCategoryExample();
        //设置查询条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        // 3、得到List<TbContentCategory>
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        // 4、把列表转换成List<EasyUITreeNode>ub
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            //添加到列表
            resultList.add(node);
        }
        return resultList;

    }

    @Override
    public E3Result addContentCategory(long parentId, String name) {
        // 1、接收两个参数：parentId、name
        // 2、向tb_content_category表中插入数据。
        // a)创建一个TbContentCategory对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        // b)补全TbContentCategory对象的属性
        tbContentCategory.setIsParent(false);
        tbContentCategory.setName(name);
        tbContentCategory.setParentId(parentId);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        tbContentCategory.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        tbContentCategory.setStatus(1);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        // c)向tb_content_category表中插入数据
        contentCategoryMapper.insert(tbContentCategory);
        // 3、判断父节点的isparent是否为true，不是true需要改为true。
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        // 4、需要主键返回。
        // 5、返回E3Result，其中包装TbContentCategory对象
        return E3Result.ok(tbContentCategory);
    }

    /**
     * 修改内容节点信息
     * @param id
     * @param name
     * @return
     */
    @Override
    public E3Result editContentCategoryName(Long id, String name) {
        //通过id查询
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        //设置查询到的内容节点名称为修改名称
        tbContentCategory.setName(name);
        //执行修改
        contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        //返回状态
        return E3Result.ok();
    }

    /**
     * 删除内容节点信息
     * @param id
     * @return
     */

    @Override
    public E3Result deleteContentCategory(Long id) {
        //删除节点时需要判断是否有子节点
        //通过id查询内容节点并且获取到父节点
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = tbContentCategory.getParentId();
        if (tbContentCategory.getIsParent()){
            return E3Result.build(1,"失败");
        }else {
            contentCategoryMapper.deleteByPrimaryKey(id);
            TbContentCategoryExample example=new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            List<TbContentCategory> childs = contentCategoryMapper.selectByExample(example);
            if (childs.size()==0) {
                //判断父节点的isParent属性是否为true如果不是就修改为true
                TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
                if (parent.getIsParent()) {
                    parent.setIsParent(false);
                    //更新到数据库
                    contentCategoryMapper.updateByPrimaryKey(parent);
                }
            }
            //返回结果，返回E3Result，包含pojo
            return E3Result.ok(tbContentCategory);
        }
    }


}
