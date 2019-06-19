package com.zhl.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;

public class TestSolrCloud {


    @Test
    public  void  testAddDocument() throws  Exception{
        //创建一个集群连接，应该使用CloudSolrServer创建。
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183");
        //zkHost:zookeeper的地址列表
        //设置一个defaultCollection属性。
        solrServer.setDefaultCollection("collection2");
        //设置一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档添加域
        document.setField("id","solrcloud01");
        document.setField("item_title","测试商品01");
        document.setField("item_price",123);
        //把文件写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public  void testQueryDo() throws Exception{
        //创建一个CloudSolrServer对象
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183");
        //设置默认的Collection
        solrServer.setDefaultCollection("collection2");
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("*:*");
        //执行查询
        QueryResponse response = solrServer.query(query);
        //取查询结果
        SolrDocumentList documents = response.getResults();
        System.out.println("查询总记录数：" + documents.getNumFound());
        //打印（遍历结果集）
        for (SolrDocument s: documents) {
            System.out.println(s.get("id"));
            System.out.println(s.get("title"));
            System.out.println(s.get("item_title"));
            System.out.println(s.get("item_price"));

        }
    }
}
