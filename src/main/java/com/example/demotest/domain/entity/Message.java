package com.example.demotest.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Integer id;

    private String content;

    private String username;

    private Date createDatetime;

    private Date updateDatetime;

    public Message(String content,String username){
        this.content=content;
        this.username=username;
    }
}
