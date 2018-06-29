package cn.dubidubi.controller;


import org.apache.commons.lang3.RandomUtils;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author lzj
 * @Description:
 * @date 18-6-23下午8:33
 */
//@Controller
//@RequestMapping("/solr")
public class Solr {
    public static HttpSolrClient SolrClient;

    public static void Init() {
        try {
            System.out.println("初始化");
            SolrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/lzj").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param
     * @return
     * @Description:新增文档
     * @author lzj
     * @date 18-6-25 下午3:11
     */
    public static void add() throws IOException, SolrServerException {
        SolrInputDocument SolrInputDocument = new SolrInputDocument();
        StringBuilder text = new StringBuilder();
        StringBuilder follow = new StringBuilder();
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < RandomUtils.nextInt(1, 10); i++) {
            String[] index = {"美国", "还打算", "大三", "的撒打算", "大", "更好的风格", "个地方官", "大道凤凰公园", "的飞洒发啥都会", "高考辅导了"};
            text.append(index[RandomUtils.nextInt(1, 10)]);
            follow.append(index[RandomUtils.nextInt(1, 10)]);
            username.append(index[RandomUtils.nextInt(1, 10)]);
        }
        //必须要id
        SolrInputDocument.addField("id", UUID.randomUUID().toString());
        SolrInputDocument.addField("text", text.toString());
        SolrInputDocument.addField("follow_comment", follow.toString());
        SolrInputDocument.addField("username", username.toString());
        SolrClient.add(SolrInputDocument);
        SolrClient.commit();
        System.out.println("完成插入");
    }


    public static void query() throws IOException, SolrServerException {
        SolrQuery SolrQuery = new SolrQuery("text:*");
        //SolrQuery.set("fq", "follow_comment:公园");
        //SolrQuery.addFilterQuery("username:高考");
        SolrQuery.setSort("id", org.apache.solr.client.solrj.SolrQuery.ORDER.desc);
        // SolrQuery.set("fl", "text,username");
        //高亮
        SolrQuery.setHighlight(true);
        SolrQuery.setHighlightSimplePre("<em>");
        SolrQuery.setHighlightSimplePost("</em>");
        SolrQuery.addHighlightField("text");
        //SolrQuery.setStart(0);
        //SolrQuery.setRows(10);
        QueryResponse query = SolrClient.query(SolrQuery);
        SolrDocumentList results = query.getResults();
        System.out.println(results.getNumFound());
        Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
            Map<String, List<String>> id = highlighting.get(result.get("id"));
            if (id != null) {
                System.out.println("high:" + id.get("text").get(0));
            }
            System.out.println("text" + result.get("text"));
            System.out.println("username" + result.get("username"));
            System.out.println("follow_comment" + result.get("follow_comment"));
            System.out.println("-------------------------------");
        }
    }


    public static void delete() throws IOException, SolrServerException {
        SolrClient.deleteByQuery("text:高考");
        SolrClient.commit();
    }

    public static void update() {

    }

    /**
     * @param
     * @return
     * @Description: solr测试方法
     * @author lzj
     * @date 18-6-23 下午8:36
     */
    public static void main(String[] args) throws IOException, SolrServerException {
        Init();
        // delete();
        query();
    }
}
