package com.jk.service;

import com.alibaba.fastjson.JSONObject;
import com.jk.entity.UserTwo;

import java.util.List;

public interface UserTwoService {
    List<UserTwo> tableData(Integer page, Integer limit, UserTwo user, String datetimeqvjian);
    Integer sum(UserTwo user, String datetimeqvjian);
    void addUser(UserTwo use);
    void updateUser(UserTwo user);
    void delete(UserTwo user);
    void deleteAll(String[] array);
    JSONObject search(Integer page, Integer limit, UserTwo user, String datetimeqvjian);
    UserTwo edit(String id);
}
