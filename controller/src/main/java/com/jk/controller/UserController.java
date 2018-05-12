package com.jk.controller;

import com.jk.common.BanDysmsUtil;
import com.jk.common.DysmsUtil;
import com.jk.common.RandomNumber;
import com.jk.common.RedisUtil;
import com.jk.entity.User;
import com.jk.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("phone")
    @ResponseBody
    public String phone(User user) {
        if(redisTemplate.hasKey("shijian"+user.getPhone())){
            Long expire = redisTemplate.getExpire("shijian" + user.getPhone(), TimeUnit.SECONDS);
            return expire+"";
        }
        String data = RandomNumber.createData(4);
        System.out.println(data+"-------------");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("zhuce"+user.getPhone(),data,60,TimeUnit.SECONDS);
        valueOperations.set("shijian"+user.getPhone(),"no",60,TimeUnit.SECONDS);
        BanDysmsUtil.sendSms(user.getPhone(),data);
        return "gc";
    }
    @PostMapping("insert")
    @ResponseBody
    public String insert(User user) {
        return userService.insert(user);
    }



}
