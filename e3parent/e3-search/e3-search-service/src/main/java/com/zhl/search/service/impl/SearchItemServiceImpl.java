package com.zhl.search.service.impl;

import com.zhl.pojo.SearchItem;
import com.zhl.search.mapper.ItemMapper;
import com.zhl.search.service.SearchItemService;
import com.zhl.utils.E3Result;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer cloudSolrServer;


    @Override
    public E3Result importAllItems() {
        try {
            //查询商品列表
            List<SearchItem> list = itemMapper.getItemList();
            //遍历商品列表
            for (SearchItem s : list) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档对象添加域
                document.addField("id", s.getId());
                document.addField("item_title", s.getTitle());
                document.addField("item_sell_point", s.getPrice());
                document.addField("item_price", s.getPrice());
                document.addField("item_image", s.getImage());
                document.addField("item_category_name", s.getCategory_name());
                // 把文档对象写入索引库
                cloudSolrServer.add(document);
            }
            //提交
            cloudSolrServer.commit();
            //返回导入成功
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500,"数据导入时发生异常");
        }
    }
}
