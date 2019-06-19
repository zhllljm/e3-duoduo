package com.zhl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhl.mapper.TbItemDescMapper;
import com.zhl.pojo.EasyUIResult;
import com.zhl.mapper.TbItemMapper;
import com.zhl.pojo.TbItem;
import com.zhl.pojo.TbItemDesc;
import com.zhl.pojo.TbItemExample;
import com.zhl.redis.JedisClient;
import com.zhl.service.ItemService;
import com.zhl.utils.E3Result;
import com.zhl.utils.IDUtils;
import com.zhl.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 *
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;

    @Autowired
    private JedisClient jedisClient;
    @Value("${ITEM_INFO_PRE}")
    private String ITEM_INFO_PRE;
    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;

    @Override
    public TbItem getItemById(long itemId) {
        try {
            //查询缓存
            String json = jedisClient.get(ITEM_INFO_PRE + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                //把json转换为java对象
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据商品id查询商品信息
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        //设置查询条件
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = tbItemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            TbItem item = list.get(0);
            try {
                //把数据保存到缓存
                jedisClient.set(ITEM_INFO_PRE + ":" + itemId + ":BASE", JsonUtils.objectToJson(item));
                //设置缓存的有效期
                jedisClient.expire(ITEM_INFO_PRE + ":" + itemId + ":BASE", ITEM_INFO_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list.get(0);
        }
        return null;
    }

    @Override
    public EasyUIResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);

        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);


        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        System.out.println(pageInfo.getPageSize());
        System.out.println(list.size());


        //创建返回结果对象
        EasyUIResult result = new EasyUIResult();
        System.out.println(result);
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);

        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {

        //生成商品ID
        final long itemId = IDUtils.genItemId();
        //补全item的属性
        item.setId(itemId);
        //1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(item);
        //创建一个商品描述表对应的pojo对象
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);
        //发送添加商品消息
        jmsTemplate.send(topicDestination, new
                MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        TextMessage textMessage = session.createTextMessage(itemId + "");
                        return textMessage;
                    }
                });
        //返回成功
        return E3Result.ok();
    }

    @Override
    public TbItemDesc findTbItemDescById(long itemId) {
        try {
            //查询缓存
            String json = jedisClient.get(ITEM_INFO_PRE + ":" + itemId + ":DESC");
            if (StringUtils.isNotBlank(json)) {
                //把json转换为java对象
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

        try {
            //把数据保存到缓存
            jedisClient.set(ITEM_INFO_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(tbItemDesc));
            //设置缓存的有效期
            jedisClient.expire(ITEM_INFO_PRE + ":" + itemId + ":DESC", ITEM_INFO_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  tbItemDesc;
    }

    @Override
    public TbItem findTbItemById(long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public E3Result updateShop(TbItem tbItem, String desc) {
        tbItem.setUpdated(new Date());
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        return E3Result.ok();
    }


    @Override
    public E3Result deleteItem(long[] ids) {
        for (long id : ids) {
            //执行删除操作
            tbItemMapper.deleteByPrimaryKey(id);
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        }
        return E3Result.ok();

    }

    @Override
    public E3Result updateStatusDown(long[] ids) {
        for (long id : ids) {
            TbItem item = tbItemMapper.selectByPrimaryKey(id);
            item.setStatus((byte) 2);
            //保存到数据库
            tbItemMapper.updateByPrimaryKey(item);
        }
        return E3Result.ok();
    }


    @Override
    public E3Result updateStatusUp(long[] ids) {
        for (long id : ids) {
            TbItem item = tbItemMapper.selectByPrimaryKey(id);
            item.setStatus((byte) 1);
            //保存到数据库
            tbItemMapper.updateByPrimaryKey(item);
        }
        return E3Result.ok();
    }
}

