package cn.dubidubi.model;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * @author lzj
 * @Description: solr的模型层
 * @date 18-6-23下午9:43
 */
public class SolrModel implements Serializable {
    @Field
    private String username;
    @Field("follow_comment")
    private String comment;
    @Field
    private String text;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
