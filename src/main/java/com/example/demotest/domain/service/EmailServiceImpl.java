package com.example.demotest.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("emailService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmailServiceImpl implements EmailService{
    @Value("${spring.mail.username}")
    //使用@Value注入application.properties中指定的用户名
    private String username;
    @Autowired
    //用于发送文件
    private JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    public EmailServiceImpl() {
        super();
    }

    /**
     *
     * @param theRecipient 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String theRecipient, String subject, String content) {
        logger.info("发送邮件");
        MimeMessage msg = mailSender.createMimeMessage();

        try {
            msg.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse("wzltet618@163.com"));
            MimeMessageHelper helper = new MimeMessageHelper(msg,true,"utf-8");
            //helper.setFrom(new InternetAddress(theRecipient));
            helper.setTo(theRecipient);//收信人
            helper.setSubject(subject);//主题
            helper.setText(content);//内容
            helper.setFrom(username);//发信人
            mailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        logger.info("邮件发送成功");
    }

    /**
     * 【发送HTML文件】HTML文件就是指在文件内容中可以添加<html>等标签，收件人收到邮件后显示内容也和网页一样，比较丰富多彩
     * @param theRecipient 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    @Override
    public void sendHtmlMail(String theRecipient, String subject, String content) {

        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse("wzltet618@163.com"));
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(username);
            helper.setTo(theRecipient);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送HTML邮件失败：", e);
        }
    }

    /**
     *发送带附件的邮件】带附件的邮件在HTML邮件上添加一些参数即可
     * @param theRecipient 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    @Override
    public void sendAttachmentMail(String theRecipient, String subject, String content, String filePath) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(username);
            helper.setTo(theRecipient);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送HTML邮件失败：", e);
        }


    }

    /**
     * 【发送带图片的邮件】带图片即在正文中使用<img>标签，并设置我们需要发送的图片，也是在HTML基础上添加一些参数
     * @param theRecipient 收件人
     * @param subject 主题
     * @param content 文本
     * @param rscPath 图片路径
     * @param rscId 图片ID，用于在<img>标签中使用，从而显示图片
     */
    @Override
    public void sendInlineResourceMail(String theRecipient, String subject, String content, String rscPath, String rscId) {
        //logger.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(theRecipient);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);//重复使用添加多个图片
            mailSender.send(message);
            logger.info("发送带图片邮件成功");
        } catch (MessagingException e) {
            logger.error("发送带图片邮件失败", e);
        }
    }
}
