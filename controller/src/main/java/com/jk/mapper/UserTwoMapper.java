package com.jk.mapper;

import com.jk.entity.UserTwo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserTwoMapper {
    List<UserTwo> tableData(@Param(value = "page") Integer page, @Param(value = "limit") Integer limit, @Param(value = "user") UserTwo user, @Param(value = "kaishi") String kaishi, @Param(value = "jieshu") String jieshu);
    Integer sum(@Param(value = "user") UserTwo user, @Param(value = "kaishi") String kaishi, @Param(value = "jieshu") String jieshu);
    void addUser(UserTwo use);
    void updateUser(UserTwo user);
    void delete(UserTwo user);
    void deleteAll(String[] array);
    UserTwo edit(String id);
}
