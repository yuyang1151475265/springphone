package com.jk.controller;

import com.alibaba.fastjson.JSONObject;
import com.jk.entity.UserTwo;
import com.jk.service.UserTwoService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class LayuiController {

    @Resource
    private UserTwoService userService;
    @Autowired
    private HttpSolrServer httpSolrServer;


    @GetMapping("/layui")
    public String index() {
        return "indextwo";
    }

    @GetMapping("tableData")
    @ResponseBody
    public JSONObject tableData(Integer page, Integer limit, UserTwo user, String datetimeqvjian) {
        JSONObject jsonObject = new JSONObject();
        List<UserTwo> list = userService.tableData(page,limit,user,datetimeqvjian);
        Integer count = userService.sum(user,datetimeqvjian);
        jsonObject.put("data",list);
        jsonObject.put("count",count);
        jsonObject.put("code",0);
        jsonObject.put("msg","");
        return jsonObject;
    }


    @GetMapping("search")
    @ResponseBody
    public JSONObject search(Integer page, Integer limit, UserTwo user, String datetimeqvjian) {
        JSONObject jsonObject = userService.search(page,limit,user,datetimeqvjian);
        return jsonObject;
    }


    @GetMapping("add")
    public String tableData() {
        return "add";
    }

    @PostMapping("/addUser")
    @ResponseBody
    public Integer addUser(UserTwo user){
        userService.addUser(user);
        return 1;
    }


    @PostMapping("edit")
    public String edit(String id,HttpServletRequest request) {
        UserTwo user = userService.edit(id);
        String toJSONString = JSONObject.toJSONString(user);
        request.setAttribute("data",toJSONString);
        request.setAttribute("datas",123456);
        return "edit";
    }

    @PostMapping("updateUser")
    @ResponseBody
    public Integer updateUser(UserTwo user) {
        userService.updateUser(user);
        return 1;
    }

    @PostMapping("delete")
    @ResponseBody
    public Integer delete(UserTwo user,HttpServletRequest request) {
        request.setAttribute("biaoshi","top");
        userService.delete(user);
        return 1;
    }


    @PostMapping("deleteAll")
    @ResponseBody
    public Integer deleteAll(@RequestParam(value = "array") String[] array) {
        userService.deleteAll(array);
        return 1;
    }



    public static String test(){
        System.out.println("123");
        return "456";
    }

    public static void main(String[] args) {
        System.out.println(test());
    }




}
