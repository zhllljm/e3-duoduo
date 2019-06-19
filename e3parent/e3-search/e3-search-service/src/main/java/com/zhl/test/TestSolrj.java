package com.zhl.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class TestSolrj {
    /**
     * 添加数据到solr测试
     * @throws Exception
     */
    @Test
    public void  addDdocument() throws Exception{
        //创建一个SolrServer对象，创建连接。参数solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        //创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须咋schema.xml中定义。
        document.addField("id","doc01");
        document.addField("item_title","测试商品01");
        document.addField("item_price",1000);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    /**
     * 删除solr数据测试
     * @throws Exception
     */
    @Test
    public void  delDdocument() throws Exception{
        //创建一个SolrServer对象，创建连接。参数solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        //删除一个文档对象SolrInputDocument
      /*  solrServer.deleteById("doc01");*/
        solrServer.deleteByQuery("id:doc01");
        //提交
        solrServer.commit();
    }

    /**
     * 简单查询solr中数据测试
     * @throws Exception
     */
    @Test
    public  void  queryIndex() throws Exception{
        //创建一个SolrServer连接对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        //创建一个SolrQuery封装查询 对象
        SolrQuery  query = new SolrQuery();
        //设置查询条件
        /*query.setQuery("*:*");*/
        query.set("q","*:*");
        //执行查询，QueryResponse对象
        QueryResponse queryResponse = solrServer.query(query);
        //取文档列表。取查询结果的总记录数
        SolrDocumentList solrInputDocument = queryResponse.getResults();
        System.out.println("查询总记录数:"+solrInputDocument.getNumFound());
        //遍历文档列表，从取域的内容
        for (SolrDocument so:solrInputDocument) {
            System.out.println(so.get("id"));
            System.out.println(so.get("item_title"));
            System.out.println(so.get("item_sell_point"));
            System.out.println(so.get("item_price"));
            System.out.println(so.get("item_image"));
            System.out.println(so.get("item_category_name"));
        }
    }

    /**
     * 复杂查询solr中的数据
     * @throws Exception
     */
    @Test
    public  void IndexFuZa() throws Exception{
        //创建一个SolrServer连接对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        //创建一个SolrQuery封装查询 对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("手机");
        query.setStart(0);
        query.setRows(20);
        query.set("df","item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        //执行查询
        QueryResponse queryResponse = solrServer.query(query);
        //取文档列表。取查询结果的总记录数
        SolrDocumentList solrInputDocument = queryResponse.getResults();
        System.out.println("查询总记录数:"+solrInputDocument.getNumFound());
        //遍历文档列表，从取域的内容
        for (SolrDocument so:solrInputDocument) {
            System.out.println(so.get("id"));
            //取高亮显示
            Map<String,Map<String, List<String>>> high = queryResponse.getHighlighting();
           List<String> list = high.get(so.get("id")).get("item_title");
           String title = "";
           if(list != null && list.size()>0){
               title = list.get(0);
           }else {
               title = (String) so.get("item_title");
           }
            System.out.println(title);
            System.out.println(so.get("item_sell_point"));
            System.out.println(so.get("item_price"));
            System.out.println(so.get("item_image"));
            System.out.println(so.get("item_category_name"));
        }
    }
}
