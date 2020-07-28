package com.example.demo.advice.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class Mailer {
    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    private static String mailserver = "smtp.qq.com"; // qq邮箱的 SMTP 服务器地址为: smtp.qq.com
    private static String sender = "1242556348@qq.com"; // 发件人
    private static int smtpPort = 587;
    // smtp服务器的认证资料
    private static String username = "1242556348@qq.com"; // 发件人邮箱账号
    // 发件人邮箱的授权码（qq邮箱用第三方发邮件需要在邮箱里开启smtp服务并获取授权码）
    private static String password = "frcbjkddommdghhh";
    //是否初始化
    private static boolean isInited = false;
    private static boolean isUse = true;

    public static int sendMail(EmailInfo info) {
        if (!isInited) {
            isInited = true;
        }
        if (!isUse) {
            System.err.println("邮箱设置不启用");
            return 1;
        }
        HtmlEmail email = new HtmlEmail();
        // 通过Gmail Server 发送邮件
        email.setHostName(mailserver);
        email.setSmtpPort(smtpPort);
        //设定smtp服务器的认证资料信息
        email.setAuthentication(username, password);
        email.setStartTLSEnabled(false);
        email.setSSLOnConnect(false);
        try {
            email.setFrom(sender); // 设定发件人
            email.addTo(info.getReceivers().toArray(new String[info.getReceivers().size()])); // 设定收件人
            email.setCharset("UTF-8"); // 设定内容的语言集
            email.setDebug(true);
            email.setSubject(info.getTitle()); // 设定主题
            email.setHtmlMsg(info.getContent()); // 设定邮件内容
            email.send();// 发送邮件

        } catch (EmailException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
