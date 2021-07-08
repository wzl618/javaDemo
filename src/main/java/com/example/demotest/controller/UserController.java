package com.example.demotest.controller;

import com.example.demotest.domain.entity.User;
import com.example.demotest.domain.service.EmailService;
import com.example.demotest.domain.service.UserService;
import com.example.demotest.model.LoginRequest;
import com.example.demotest.utils.MD5Utils;
import com.example.demotest.utils.RedisUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api("Userapi")
@RestController
@RequestMapping("User")
public class UserController {

   @Autowired
   private UserService userService;

   @Autowired
    private RedisUtils redisUtils;

   @Autowired
   private EmailService emailService;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Object> resultMap = new HashMap<>();

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody String email){

        String newEmail=email.replace("\"","");
        String uuid=UUID.randomUUID().toString();
        StringBuffer sf=new StringBuffer();
        sf.append("<a href=\"http://localhost:8080/User/activateEmail?email=");
        sf.append(newEmail);
        sf.append("&validateCode=");
        sf.append(uuid);
        sf.append("\">");
        sf.append(" <FONT   face=\"MS   UI   Gothic\"   size=\"3\"><b>【留言板验证】点击这里</b></FONT>");
        sf.append("</a>");
        sf.append("激活账号，30分钟生效，否则重新验证，请尽快激活！<br>");
        emailService.sendHtmlMail(newEmail,"留言板验证链接",sf.toString());
        //缓存token
        redisUtils.set(newEmail,uuid,30L, TimeUnit.MINUTES);
        return "发送成功，请验证";
    }

    @GetMapping("/activateEmail")
    public String activateEmail(@RequestParam("email") String email, @RequestParam("validateCode") String validateCode){
        String token = (String) redisUtils.get(email.replace("\"",""));
        if (!validateCode.equals(token)){
            return "验证失败，验证码不匹配";
        }
        List<User> list= userService.getUserByEmail(email);
        if (list.stream().count()>0){
            return "邮箱重复，请重新填写邮箱后再验证";
        }
        User user=new User();
        user.setEmail(email);
        user.setToken(token);
        user.setActive(true);
        user.setPassword("");
        userService.RegisterUser(user);
        return "验证成功";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user)
    {
        if (user.getUsername()==null || user.getUsername().equals("")){
            return "用户名为空";
        }
        if (user.getPassword()==null || user.getPassword().equals("")){
            return "密码为空";
        }

        if (user.getEmail()==null || user.getEmail().equals("")){
            return "邮箱为空";
        }
        if (user.getPhoneNumber()==null || user.getPhoneNumber().equals("")){
            return "手机号码为空";
        }
        List<User> list= userService.getUserByEmail(user.getEmail());
        user.setId(list.get(0).getId());
        String message="注册失败";

        int result= userService.UpdateUser(user);
        if (result>0){
            message="注册成功";
        }
        return message;
    }

    @PostMapping("/Login")
    public String Login(HttpServletRequest request, @RequestBody LoginRequest loginRequest)
    {
        if (loginRequest.getUsername()==null || loginRequest.getUsername().equals("")){
            return "用户名为空";
        }
        if (loginRequest.getPassword()==null || loginRequest.getPassword().equals("")){
            return "密码为空";
        }
        User user= userService.Login(loginRequest.getUsername(),loginRequest.getPassword());
        if (user==null){
            return "登陆失败";
        }

        //session及redis设置
        HttpSession session=request.getSession();
        session.setAttribute("token",user.getToken());
        redisUtils.set(user.getToken(),user,10L, TimeUnit.MINUTES);
        return "登陆成功";
    }


}
