package com.jk.service.impl;

import com.jk.entity.User;
import com.jk.mapper.UserMapper;
import com.jk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public String insert(User user) {
        if(!redisTemplate.hasKey("zhuce"+user.getPhone())){
            return "yzmygq";
        }else {
            Object object = redisTemplate.opsForValue().get("zhuce" + user.getPhone());
            if (object.toString().equals(user.getPhoneyzm())) {
                String[] arr = {"zhuce" + user.getPhone(),"shijian"+user.getPhone()};
                redisTemplate.delete(CollectionUtils.arrayToList(arr));
                userMapper.insert(user);
                return "yes";
            }else {
                return "cuowu";
            }
        }
    }




}
