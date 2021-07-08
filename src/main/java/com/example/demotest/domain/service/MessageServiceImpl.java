package com.example.demotest.domain.service;

import com.example.demotest.domain.entity.Message;
import com.example.demotest.domain.mapper.MessageMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService
{

    @Autowired
    private MessageMapper messageMapper;
    public List<Message> selectOrderByLimitAndOffset(
            @Param("content") String content,
            @Param("orderByField") String orderByField,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset)
    {

        return messageMapper.selectOrderByLimitAndOffset(content,orderByField,limit,offset);
    }

    public int addMessage(Message message)
    {
       return messageMapper.addMessage(message);
    }

    public int selectOrderByLimitCount (@Param("content") String content)
    {
        return  messageMapper.selectOrderByLimitCount(content);
    }
}
