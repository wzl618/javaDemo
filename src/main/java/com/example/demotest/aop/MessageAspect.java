package com.example.demotest.aop;

import com.alibaba.fastjson.JSON;
import com.example.demotest.domain.entity.User;
import com.example.demotest.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Aspect//这个注解的作用是:将一个类定义为一个切面类
@Component//这个注解的作用：把切面类加入到IOC容器中
@Order(1)//这个注解的作用是:标记切面类的处理优先级,i值越小,优先级别越高.PS:可以注解类,也能注解到方法上
@Slf4j
public class MessageAspect {
    @Autowired
    private RedisUtils redisUtils;

    //申明一个切点 里面是excution表达式
    @Pointcut("execution(* com.example.demotest.controller.MessageController.AddMessage(..))")
    private void webLog() {
    }

    @Around(value = "webLog()")//这个注解的作用是:在切点前执行方法,内容为指定的切点
    public Object methodBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //打印请求内容
        log.info("========================请求内容======================");
        log.info("请求地址:" + request.getRequestURI().toString());
        log.info("请求方式" + request.getMethod());
        log.info("请求类方法" + joinPoint.getSignature());
        log.info("请求类方法参数" + Arrays.toString(joinPoint.getArgs()));
        HttpSession session= request.getSession();
        String token= (String) session.getAttribute("token");
        log.info("token:" + token);
        if (token==null || ("").equals(token)){
            return ResponseEntity.ok("无权限");
        }
        User user= (User) redisUtils.get(token);
        if (user==null){
            return ResponseEntity.ok("无权限");
        }
        log.info("========================请求内容======================");
        Object result =new Object();
        try {
            result=joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
