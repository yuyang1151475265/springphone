package com.jk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jk.common.UtfDate;
import com.jk.entity.UserTwo;
import com.jk.mapper.UserTwoMapper;
import com.jk.service.UserTwoService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserTwoServiceImpl implements UserTwoService {

    @Autowired
    private UserTwoMapper userMapper;
    @Autowired
    private HttpSolrServer httpSolrServer;



    @Override
    public List<UserTwo> tableData(Integer page, Integer limit, UserTwo user, String datetimeqvjian) {
        Integer on = (page-1)*limit;
        String[] split = {"",""};
        if(datetimeqvjian!=null && !datetimeqvjian.equals("")) {
            split = datetimeqvjian.split(" - ");
        }
        return userMapper.tableData(on,limit,user,split[0],split[1]);
    }

    @Override
    public Integer sum(UserTwo user, String datetimeqvjian) {
        String[] split = {"",""};
        if(datetimeqvjian!=null && !datetimeqvjian.equals("")) {
            split = datetimeqvjian.split(" - ");
        }
        return userMapper.sum(user,split[0],split[1]);
    }

    @Override
    public void addUser(UserTwo use) {
        String string = UUID.randomUUID().toString();
        String all = string.replaceAll("-", "");
        use.setId(all);
        userMapper.addUser(use);
        try {
            httpSolrServer.addBean(use);
            httpSolrServer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(UserTwo user) {
        userMapper.updateUser(user);
        try {
            httpSolrServer.addBean(user);
            httpSolrServer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(UserTwo user) {
        userMapper.delete(user);
        try {
            httpSolrServer.deleteById(user.getId(),1000);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteAll(String[] array) {
        userMapper.deleteAll(array);
        //将String[]转换为List<String>
        List<String> strings = Arrays.asList(array);
        try {
            httpSolrServer.deleteById(strings,1000);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject search(Integer page, Integer limit, UserTwo user, String datetimeqvjian) {
        JSONObject jsonObject = new JSONObject();
        if(user.getName().equals("")) {
            user.setName("*:*");
        }
        //查询索引库!!!!!!

        //创建查询条件
        SolrQuery query = new SolrQuery();






        //设置主查询
        query.setQuery(user.getName());





        //设置排序 时间倒叙
        query.addSort("datetime", SolrQuery.ORDER.desc);


        //设置分页
        query.setStart((page-1)*limit);
        query.setRows(limit);



          //设置时间区间
                if(datetimeqvjian!=null && !datetimeqvjian.equals("")) {
                    String[] split = datetimeqvjian.split(" - ");
                    try {
                        String s1 = UtfDate.utfTime(split[0]);
                String s2 = UtfDate.utfTime(split[1]);
                query.addFilterQuery("datetime:["+s1+" TO "+s2+"]");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 开启高亮
        query.setHighlight(true);
        // 指定设置高亮字段
        query.addHighlightField("name");
        // 指定设置高亮显示前缀
        query.setHighlightSimplePre("<font color='red'>");
        //指定设置高亮显示后缀
        query.setHighlightSimplePost("</font>");

        //总条数
        long count = 0;
        try {
            QueryResponse rsp = httpSolrServer.query(query);
            SolrDocumentList docs = rsp.getResults();
            count= docs.getNumFound();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }


        //转型
        QueryResponse query1 = null;
        try {
            query1 = httpSolrServer.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        //得到的数据
        List<UserTwo> beans = query1.getBeans(UserTwo.class);

        //高亮的文本
        Map<String, Map<String, List<String>>> highlightresult = query1.getHighlighting();



        // 数据和数据相互比较 转换
        for (int i = 0; i < beans.size(); ++i) {
            String id = beans.get(i).getId();
            if (highlightresult.get(id) != null && highlightresult.get(id).get("name") != null) {
                beans.get(i).setName(highlightresult.get(id).get("name").get(0));
            }
        }


        //查询索引库 结束!!!!!!!!!!!!!
        jsonObject.put("data",beans);
        jsonObject.put("count",count);
        jsonObject.put("code",0);
        jsonObject.put("msg","");


        return jsonObject;
    }

    @Cacheable(value = "common",key = "'student'")
    @Override
    public UserTwo edit(String id) {
        UserTwo edit = userMapper.edit(id);
        return edit;
    }
}
