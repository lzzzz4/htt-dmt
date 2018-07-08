package cn.dubidubi.controller;


import org.apache.commons.lang3.RandomUtils;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author lzj
 * @Description:
 * @date 18-6-23下午8:33
 */
@Controller
@RequestMapping("/v1")
public class Solr {
    @Autowired
    public HttpSolrClient SolrClient;

    /**
     * @param
     * @return
     * @Description:新增文档
     * @author lzj
     * @date 18-6-25 下午3:11
     */
    @RequestMapping("/add")
    public void add() throws IOException, SolrServerException {
        SolrInputDocument SolrInputDocument = new SolrInputDocument();
        StringBuilder text = new StringBuilder();
        StringBuilder follow = new StringBuilder();
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < RandomUtils.nextInt(1, 10); i++) {
            String[] index = {"黄婷婷", "黄婷婷", "黄婷婷", "黄婷婷", "黄婷婷", "黄婷婷", "黄婷婷", "大道凤凰公园", "的飞洒发啥都会", "高考辅导了"};
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

    @RequestMapping("query")
    public void query() throws IOException, SolrServerException {
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


    public void delete() throws IOException, SolrServerException {
        SolrClient.deleteByQuery("text:高考");
        SolrClient.commit();
    }

    public static void update() {

    }
}
