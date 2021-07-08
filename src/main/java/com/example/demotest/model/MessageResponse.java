package com.example.demotest.model;

import com.example.demotest.domain.entity.Message;

import java.util.List;

public class MessageResponse {
    public Integer page;
    public Integer count;
    public List<Message> messageList;
}
