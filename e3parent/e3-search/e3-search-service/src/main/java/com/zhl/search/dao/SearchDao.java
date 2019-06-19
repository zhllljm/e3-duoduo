package com.zhl.search.dao;

import com.zhl.pojo.SearchItem;
import com.zhl.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索dao
 */
@Repository
public class SearchDao {

    @Autowired
    private HttpSolrServer solrServer;

    /**
     * 根据查询条件查询索引
     *
     * @param query
     * @return
     */
    public SearchResult search(SolrQuery query) throws  Exception{
        //根据query查询索引库
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocuments = queryResponse.getResults();
        // 取查询结果总记录数
        long numFound = solrDocuments.getNumFound();
        SearchResult result = new SearchResult();
        result.setRecordCount((int) numFound);
        //取商品列表，需要取高亮显示
        List<SearchItem> itemList = new ArrayList<>();
        Map<String, Map<String, List<String>>> high = queryResponse.getHighlighting();
        for (SolrDocument ss: solrDocuments) {
            SearchItem item = new SearchItem();
            item.setId((String) ss.get("id"));
            item.setCategory_name((String) ss.get("item_category_name"));
            item.setImage((String) ss.get("item_image"));
            item.setPrice((Long) ss.get("item_price"));
            item.setSell_point((String) ss.get("item_sell_point"));
            //取高亮显示
            List<String> list = high.get(ss.get("id")).get("item_title");
            String title = "";
            if(list != null && list.size()>0){
                title = list.get(0);
            }else {
                title = (String) ss.get("item_title");
            }
            item.setTitle(title);
            //添加到商品列表
            itemList.add(item);
        }
        result.setItemList(itemList);
        //返回结果
        return  result;
    }

}
