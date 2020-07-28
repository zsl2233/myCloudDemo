package com.example.demo.advice.mail;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailSendController {

    @RequestMapping("/mailSend")
    public String mailSend(){
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setTitle("邮件标题");
        emailInfo.setContent("尊敬的用户您好！\r\n <br />"+"邮件内容");
        emailInfo.getReceivers().add("3428172774@qq.com");
        Mailer.sendMail(emailInfo);
        return "发送成功";
    }
}
