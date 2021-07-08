package com.example.demotest.controller;

import com.example.demotest.domain.entity.Book;
import com.example.demotest.domain.entity.Message;
import com.example.demotest.domain.entity.User;
import com.example.demotest.domain.service.MessageService;
import com.example.demotest.domain.service.UserService;
import com.example.demotest.model.MessageResponse;
import com.example.demotest.utils.RedisUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Api("messageapi")
@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("getMessage")
    public ResponseEntity getMessage(
            @RequestParam("content") String content,
            @RequestParam(defaultValue = "update_datetime") String sort,
            @RequestParam Integer page){
        MessageResponse response=new MessageResponse();


        Integer offset = (page - 1) * 20;

        List<Message> messages = messageService.selectOrderByLimitAndOffset(
                content, sort, 20, offset);
        response.page=page;
        response.messageList=messages;
        response.count=messageService.selectOrderByLimitCount(content);
        return ResponseEntity.ok(response);
    }

    @GetMapping("AddMessage")
    public ResponseEntity AddMessage(HttpServletRequest request,@RequestParam("content") String content)
    {
        HttpSession session= request.getSession();
        String token=(String)session.getAttribute("token");
        User user= (User) redisUtils.get(token);
        Message message=new Message(content,user.getUsername());
        long id= messageService.addMessage(message);
        return ResponseEntity.ok(message.getId());
    }
}
