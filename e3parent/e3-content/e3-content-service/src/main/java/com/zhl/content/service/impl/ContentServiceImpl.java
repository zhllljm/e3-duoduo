package com.zhl.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhl.content.service.ContentService;
import com.zhl.mapper.TbContentMapper;
import com.zhl.pojo.*;
import com.zhl.redis.JedisClient;
import com.zhl.utils.E3Result;
import com.zhl.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {


    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    @Override
    public List<TbContent> getContentList(long categoryId) {

        //查询缓存
        try {
            String json = jedisClient.hget(CONTENT_KEY, categoryId + "");
            //判断json是否为空
            if (StringUtils.isNotBlank(json)) {
                //把json转换成list
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //根据分类id查询内容列表
        //设置查询条件
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //执行查询
        List<TbContent> list = tbContentMapper.selectByExample(example);
        //向缓存中添加数据
        try{
            jedisClient.hset(CONTENT_KEY,categoryId +"",JsonUtils.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public EasyUIResult gertContentList(Long categoryId, Integer page, Integer rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);

        //执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = tbContentMapper.selectByExample(example);

        //取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);

        //创建返回结果对象
        EasyUIResult result = new EasyUIResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);

        return result;
    }

    @Override
    public E3Result addContent(TbContent content) {
        //补全属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入数据
        tbContentMapper.insert(content);
        //缓存同步
        jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());
        return E3Result.ok();

    }

    /**
     * 修改内容
     *
     * @param content
     * @return
     */
    @Override
    public E3Result editContent(TbContent content) {
        //设置修改时间
        content.setUpdated(new Date());
        //执行修改
        tbContentMapper.updateByPrimaryKeySelective(content);

        return E3Result.ok();
    }

    /**
     * 回显富文本内容
     *
     * @param id
     * @return
     */
    @Override
    public TbContent selectByIdContent(Long id) {
        //执行查询
        TbContent tbContent = tbContentMapper.selectByPrimaryKey(id);
        return tbContent;
    }

    /**
     * 根据ID删除
     *
     * @param ids
     * @return
     */
    @Override
    public E3Result deleteBatchContent(long[] ids) {
        //遍历集合
        for (long id : ids) {
            //执行操作数据库
            tbContentMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
        return E3Result.ok();
    }
}
