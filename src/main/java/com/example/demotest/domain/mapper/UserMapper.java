package com.example.demotest.domain.mapper;

import com.example.demotest.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User user);

    User getUserByUserNameAndPwd(String userName,String password);

    int updateUserToken(User user);

    List<User> getUserByEmail(String email);

    int updateUser(User user);
}
