package com.example.demotest.domain.service;

import com.example.demotest.domain.entity.User;
import com.example.demotest.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    public int RegisterUser(User user)
    {
        return userMapper.insert(user);
    }

    public User Login(String userName,String password){
        User user= userMapper.getUserByUserNameAndPwd(userName,password);
        String a=UUID.randomUUID().toString();
        if (user!=null){
            user.setToken(UUID.randomUUID().toString());
        }
        else {
            return null;
        }
        int result= userMapper.updateUserToken(user);
        if (result>0){
            return user;
        }
        return null;
    }

    public List<User> getUserByEmail(String email){
        return userMapper.getUserByEmail(email);
    }

    public int UpdateUser(User user){

        return userMapper.updateUser(user);
    }
}
