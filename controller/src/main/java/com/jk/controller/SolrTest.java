package com.jk.controller;

import com.jk.entity.UserTwo;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

import java.io.IOException;
import java.util.Date;

public class SolrTest {

    public static void main(String[] args) {
        //创建solr服务
        HttpSolrServer core = new HttpSolrServer("http://localhost:8080/solr/diy/");
        core.setMaxRetries(1);
        core.setConnectionTimeout(5000);
        core.setParser(new XMLResponseParser()); // binary parser is used by default
        core.setSoTimeout(1000); // socket read timeout
        core.setDefaultMaxConnectionsPerHost(100);
        core.setMaxTotalConnections(100);
        core.setFollowRedirects(false); // defaults to false
        core.setAllowCompression(true);
        //document 模式
        /*SolrInputDocument doc1 = new SolrInputDocument();
        doc1.addField("id", "1");
        doc1.addField("name", "于洋的朋友");
        doc1.addField("datetime", new Date());
        try {
            core.add(doc1);
            core.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //注解模式
        UserTwo userTwo = new UserTwo();
        userTwo.setId("2");
        userTwo.setName("西游记");
        userTwo.setDatetime(new Date());
        try {
            core.addBean(userTwo);
            core.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        //删除索引 加时间为自动提交
        /*try {
            core.deleteById("2", 10000);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
