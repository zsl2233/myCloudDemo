package com.example.demo.advice.sms;

import java.net.URLEncoder;

/**
 * 短信API发送
 * @author JiangPengFei
 * @version $Id: javaHttpNewApiDemo, v 0.1 2019/1/23 11:42 JiangPengFei Exp $$
 */
public class SmsApiHttpSendTest {

    /**
     * 短信发送(验证码通知，会员营销)
     * 接口文档地址：http://www.miaodiyun.com/doc/https_sms.html
     */
    public void execute() throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("accountSid").append("=").append(Config.ACCOUNT_SID);
        sb.append("&to").append("=").append("13713999974");
        sb.append("&param").append("=").append(URLEncoder.encode("","UTF-8"));
        /*sb.append("&templateid").append("=").append("1251");*/
		sb.append("&smsContent").append("=").append( URLEncoder.encode("【秒嘀科技】您的验证码为123456，该验证码5分钟内有效。请勿泄漏于他人。","UTF-8"));
        String body = sb.toString() + HttpUtil.createCommonParam(Config.ACCOUNT_SID, Config.AUTH_TOKEN);
        String result = HttpUtil.post(Config.BASE_URL, body);
        System.out.println(result);

    }

    public static void main(String[] args) {
        SmsApiHttpSendTest am = new SmsApiHttpSendTest();
        try {
            am.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
