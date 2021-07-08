package com.example.demotest.domain.mapper;

import com.example.demotest.domain.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> selectOrderByLimitAndOffset(
            @Param("content") String content,
            @Param("orderByField") String orderByField,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset);

    int addMessage(Message message);

    int selectOrderByLimitCount(@Param("content") String content);
}
