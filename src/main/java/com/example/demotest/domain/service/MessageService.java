package com.example.demotest.domain.service;

import com.example.demotest.domain.entity.Book;
import com.example.demotest.domain.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MessageService {
    List<Message> selectOrderByLimitAndOffset(@Param("content") String content,
                                              @Param("orderByField") String orderByField,
                                              @Param("limit") Integer limit,
                                              @Param("offset") Integer offset);
    int addMessage(Message message);

    int selectOrderByLimitCount (@Param("content") String content);
}
