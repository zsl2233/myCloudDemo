package com.example.demo.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class ALiPayTestController {

    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }


    @RequestMapping("/pay")
    public void pay(HttpServletResponse httpResponse) throws Exception {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("WIDout_trade_no","zsl3");
        map.put("WIDtotal_amount","1");
        map.put("WIDsubject","测试支付宝支付");
        map.put("WIDbody","支付");
        String form =  pay(map);
        httpResponse.setContentType( "text/html;charset=UTF-8");
        httpResponse.getWriter().write(form); //直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @ResponseBody
    @RequestMapping("/notify")
    public String pay_notify(HttpServletRequest request) {
        System.out.println("支付完成进入异步通知");
        Map<String, String> paramsMap = convertRequestParamsToMap(request);
        String out_trade_no= paramsMap.get("out_trade_no");
        String trade_status= paramsMap.get("trade_status");
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);
            //无论同步异步都要验证签名
            if(signVerified){
                if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                    //处理自己系统的业务逻辑，如：将支付记录状态改为成功，需要返回一个字符串success告知支付宝服务器

                    return "异步 success";
                } else {
                    //支付失败不处理业务逻辑
                    return "failure";
                }
            }else {
                //签名验证失败不处理业务逻辑
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    @RequestMapping("/return")
    @ResponseBody
    public String pay_return(HttpServletRequest request) {
        Map<String, String> paramsMap = convertRequestParamsToMap(request);
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);
            if(signVerified){
                //跳转支付成功界面
                return "支付成功页面";

            }else {
                //跳转支付失败界面
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "success 同步";
    }



    //支付宝支付页面
    public String pay(Map<String,Object> hap) throws Exception {
        //获取要向支付宝支付的参数,由页面传过来
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = hap.get("WIDout_trade_no").toString();
        //付款金额，必填
        String total_amount = hap.get("WIDtotal_amount").toString();
        //订单名称，必填
        String subject = hap.get("WIDsubject").toString();
        //商品描述，可空
        String body = hap.get("WIDbody").toString();
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL,
                AliPayConfig.APP_ID, AliPayConfig.MERCHANT_PRIVATE_KEY,
                "json", AliPayConfig.CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.SIGN_TYPE);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AliPayConfig.RETURN_URL);  //设置同步回调通知
        alipayRequest.setNotifyUrl(AliPayConfig.NOTIFY_URL);  //设置异步回调通知
        //设置支付参数
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String form = null;
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return form;
    }

    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }
}
