package com.jk.controller;

import com.jk.entity.Solr;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Controller
@RequestMapping("solr")
public class SolrController {


    @Autowired
    private HttpSolrServer httpSolrServer;

    @RequestMapping("insertGood")
    @ResponseBody
    public String insertGood() throws IOException, SolrServerException {
        //访问数据库，添加操作
        //添加成功，访问solr生成索引
        Solr solr = new Solr();
        solr.setId("于洋哥哥");
        solr.setName("于洋哥哥");
        httpSolrServer.addBean(solr);
        httpSolrServer.commit();
        return "success";
    }

    public static void main(String[] args) {
        //生成索引
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

        Solr solr = new Solr();
        solr.setId("00003");
        solr.setName("皮革大狗");
        try {
            core.addBean(solr);
            core.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

}
