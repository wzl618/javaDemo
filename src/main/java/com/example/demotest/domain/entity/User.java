package com.example.demotest.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springfox.documentation.spring.web.plugins.CombinedRequestHandler;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private String token;

    private String email;

    private String phoneNumber;

    private Date createTime;

    private boolean isActive;

    public User(String username,String token,String email,String phoneNumber)
    {
        this.username=username;
        this.token=token;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }

}
