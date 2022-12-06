package com.xws.bootpro.collect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.xws.bootpro.utils.Alipay.AlipayBean;
import com.xws.bootpro.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 *@Author：lm
 *@Date：2022/11/22 17:22
 *@Description 支付宝支付接口
*/

@Slf4j
@RestController
@RequestMapping("login/alipay")
public class AlipayController {

    //应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    private String appId;

    //商户私钥 您的PKCS8格式RSA2私钥
    private String privateKey;

    //支付宝公钥
    private String publicKey;

    //服务器异步通知页面路径
    private String notifyUrl;

    //页面跳转同步通知页面路径（交易成功返回的页面）
    private String returnUrl;

    //签名方式
    private String signType;

    //字符编码格式
    private String charset;

    //支付宝网关
    private String gatewayUrl;

    private final String format = "json";

    AlipayController(){
        //应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
        appId = "2021000121688834";
        //商户私钥 您的PKCS8格式RSA2私钥
        privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJrrjEWT4OkIO+pYJ52hOeOCZNfzxj95WXZzyn2m7NBygC4Z0XiaaKeazZWspw/RdNMCknPvrufzXhQ8o1gI5zHstNcDWtq54Ufk5fnoODyFU91jcG/5TPiN+G8B0TRyM0Yzs35+Q90Yg5D94szfwt3FSizwULTuua633nq6G634nJCrdBx8y/dlxooUj/9dmRbn3wrBZWGWHGFYRatQN3HmzkEqsIWSlDpwLgLt1QoIy75ys5DSoOtx+wGk0zXP4fT8SJcM6vuTAu3nfvYQ6Adz8JPu8tq6Awcn0yMAjmB7BwAmqOxAteGa1w2d6ahkiHu/cKH6UIsDzr1neCZrxrAgMBAAECggEABCUbcOXS5XNaVAH9lJeJIalFFe4vrj7E5SL5g4Ai988wWC3codnGBsM0/fIqyA4Gho9dnc3alhWEVLzKuKOfJj4WFsW7B/hzsOBAkcZe5TfBwTPSwkYtf7X6LMDLz0nD+I7IOJ2llGrbdARTRvwIjITmk2QyHa4Jj8dpKfFT1CFdYT3YpbLszOPyAqI6jcpQ9dbEHLTuLDg3PMV0mfPEj1qOcTBhN27E0uisdTQPMMB/M04wfh9w95jPaiO8JJq/edwX2FUmMlaSn0WWqv+NF80Uh58dcdqwTytVbyBJOEsrcAFYx0+emhm0dkoZBGzAqIdxnSuzk0ZM4/aFVNZMkQKBgQC/Gtjhm7zAw4nZniDIweO0z2k8Jeo6nJy4XMCxF5Tc9Jrf4DsAkVoochivixo0tDxiyuOu7GB0zF+9xd8/6tmo3qGh7kkkQbUeOSuRzwGsKXlMB0HGvSa72X2uiMD6wpgz2z9OLOm/1x5552liVUOQkeQS21inX3iZOHY7E8xSaQKBgQC4b8E5fJP/8O2P0iBUpLiltf03/9H2kE4mwNLlPqqHayS6l0Nv12dKTMFma/rJdmojesJgXMW9Cg7ZjXBX2K5mbkdpAYedAIkSHBP2AoBUR+sXou1R1ZQNOtiz35OZrQ0rdrAQv73CzizSviz5iveU5A3Zdp5pW6RZlA1qk8KVswKBgQCkN3G/DJC6JE1Ejzkkylfde422fe40x3d0hV+fzd/Brv+W+Zw59K1ArCrCTgoK2AY77pSQL9cDBswOywX4atr23biO6bA4U65Ldl0WQofxZQIybtn66XdrXI0/eYw67xTTGsffaYDDtRsfLdfFA/e3N2aBPbOEv+ll4LrYMT8WYQKBgHfAV/0zXgT4MccH7+YFbb5qg5cwiutElvmiiUw/fAmhD5/3Rtn/Shl77LbLtXHJaPDQbBSVJ+6t8SZgJ2liTxpC0Crly1+tUVJo2K0Kwrf2WjZmmyBfRFmys7FWoUdOjRaDYZ3+YP9zTrcms5zzFf2edT+VPRW7YuKmtm9UsSrxAoGAd0PCF2qW0QjgP+ldlsi2OaJxt5ui76rX3/DlT1Urk23Raf2m+8wJs5ydC3G5sBhHncSA4O6+KAy7P114muouao31aOpqQNzZgW4huwAw4EsyKHwBWTxuPS3uZsxAu9uKz4BdRNL+W1kzl5+Sr78g/QaBOKP2zFVxZKZuDtG82zs=";
        //支付宝公钥
        publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsDpDxwsZR5obaU1tIkaQ5cP2zyvZwENOKDh5i6E54b/S+cs718k97K6RftPNgibfCbXgi5lI50vx4fs64KULD7v4O/0c9zOySe7HE820U04njNKM78zOUIgnfHlWpBEqitms+sLLD6zb/87wMBhvCzi8dUjC53HAKcmXOPZIHDuSYP2Zt8oTsMiEM+euy/K6/p1XruiS/njPKFEv1ip1+m+00oKXncO08KQMTc8V4WsyL3nrRT6vhHeZt27cuW5V4di2kBWOvXFP1gMWNgcNKnHrslGz5nrO1LxZXVdMLV4wP4FxcucgypbMJ33YHpCetvaba1CwKpCV1FzUzSOVEQIDAQAB";
        //服务器异步通知页面路径
        notifyUrl = "http://10.28.103.179:9898/login/alipay/ZFBcallback";
        //页面跳转同步通知页面路径（交易成功返回的页面）
        returnUrl = "http://localhost:8090/alipay.jsp";
        //签名方式
        signType = "RSA2";
        //字符编码格式
        charset = "utf-8";
        //支付宝网关
        gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    }

	//PC网页段支付，返回的是支付宝账号的登录页面
    @RequestMapping("pay")
    public String pay(@RequestBody AlipayBean alipayBean) throws AlipayApiException {
    	//模拟数据
		alipayBean.setOut_trade_no(UUID.randomUUID().toString().replaceAll("-",""));
        alipayBean.setSubject("订单名称");
        alipayBean.setTotal_amount(String.valueOf(new Random().nextInt(100)));
        alipayBean.setBody("商品描述");
        alipayBean.setProduct_code("FAST_INSTANT_TRADE_PAY");

        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, publicKey, signType);
        //PC网页支付使用AlipayTradePagePayRequest传参，下面调用的是pageExecute方法
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        //log.info("封装请求支付宝付款参数为:{}", JSON.toJSONString(alipayRequest));
        
        // 调用SDK生成表单
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //log.info("请求支付宝付款返回参数为:{}", result);
        return result;
    }

	/**
     * 手机扫码支付
     * @param alipayBean
     * @return
     * @throws Exception
     */
    @RequestMapping("pay2")
    public Result pay2(@RequestBody AlipayBean alipayBean) throws Exception {
        //接口模拟数据
        alipayBean.setOut_trade_no(UUID.randomUUID().toString().replaceAll("-",""));
        alipayBean.setSubject("订单名称");
        alipayBean.setTotal_amount(String.valueOf(new Random().nextInt(100)));
        alipayBean.setBody("商品描述");
        alipayBean.setProduct_code("FACE_TO_FACE_PAYMENT");

        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, publicKey, signType);
        //扫码支付使用AlipayTradePrecreateRequest传参，下面调用的是execute方法
        AlipayTradePrecreateRequest precreateRequest = new AlipayTradePrecreateRequest();
        precreateRequest.setReturnUrl(returnUrl);
        precreateRequest.setNotifyUrl(notifyUrl);
        precreateRequest.setBizContent(JSON.toJSONString(alipayBean));
        //log.info("封装请求支付宝付款参数为:{}", JSON.toJSONString(precreateRequest));

        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(precreateRequest);
        } catch (AlipayApiException e) {
            log.error(String.format("下单失败 错误代码:[%s], 错误信息:[%s]", e.getErrCode(), e.getErrMsg()));
            throw new Exception(String.format("下单失败 错误代码:[%s], 错误信息:[%s]", e.getErrCode(), e.getErrMsg()));
        }
        //log.info("AlipayTradePrecreateResponse = {}", response.getBody());

        /*
        {
        "code": "10000",
        "msg": "Success",
        "out_trade_no": "815259610498863104",
        "qr_code": "https://qr.alipay.com/bax09455sq1umiufbxf4503e"
        }
        */
        if (!response.isSuccess()) {
            log.error(String.format("下单失败 错误代码:[%s], 错误信息:[%s]", response.getCode(), response.getMsg()));
            throw new Exception(String.format("下单失败 错误代码:[%s], 错误信息:[%s]", response.getCode(), response.getMsg()));
        }
        // TODO 下单记录保存入库
        // 返回结果，主要是返回 qr_code，前端根据 qr_code 进行重定向或者生成二维码引导用户支付
        JSONObject jsonObject = new JSONObject();
        //支付宝响应的订单号
        String outTradeNo = response.getOutTradeNo();
        jsonObject.put("outTradeNo",outTradeNo);
        //二维码地址，页面使用二维码工具显示出来就可以了
        jsonObject.put("qrCode",response.getQrCode());
        return Result.ok(jsonObject);
    }

    /**
     * 支付回调函数（当用户支付成功之后，支付宝会自动调用该方法）
     * 此接口需要可以被外网访问而且必须是POST请求，并且注意拦截器是否拦截（如果被被您的登录拦截器拦截了，支付宝就无法访问此方法了）
     * @param request
     */
    @RequestMapping("ZFBcallback")
    public void ZFBcallback(HttpServletRequest request) throws IOException {
        try {
            //支付宝公钥
            String alipay_public_key = publicKey;
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            //循环遍历支付宝请求过来的参数存入到params中
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //异步验签：切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, alipay_public_key, "utf-8","RSA2");
            if (flag){
                //说明是支付宝调用的本接口
                if (params.get("trade_status").equals("TRADE_SUCCESS") || params.get("trade_status").equals("TRADE_FINISHED")) {
                    System.out.println("收到回调结果，用户已经完成支付");
                    /***
                     * 这里写您的业务逻辑代码
                     */
                    System.out.println("success");
                }
            }else {
                //验签失败该接口被别人调用
                System.out.println("支付宝异步回调验签失败，请留意");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 交易状态查询
     */
    @RequestMapping("findZFB_tradeAction")
    public Map<String,Object> findZFB_tradeAction(@RequestBody AlipayBean beans){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try {
            //(必填)商户唯一订单编号
            AlipayBean alipayBean= new AlipayBean();
            alipayBean.setOut_trade_no(beans.getOut_trade_no());
            //查询交易状态
            String json= findZFB_trade(alipayBean);
            System.out.println(json);
            JSONObject jsonObject= JSONObject.parseObject(json);
            JSONObject jsonobj_two=(JSONObject)jsonObject.get("alipay_trade_query_response");
            //网关返回码,详见文档 https://opendocs.alipay.com/open/common/105806
            String ZFBCode=(String)jsonobj_two.get("code");
            //业务返回码
            String ZFBSubCode=(String)jsonobj_two.get("sub_code");
            //业务返回码描述
            String sub_msg=(String)jsonobj_two.get("sub_msg");
            //交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
            String trade_status=(String)jsonobj_two.get("trade_status");
            if (ZFBCode.equals("40004") && ZFBSubCode.equals("ACQ.TRADE_NOT_EXIST")) {
                //订单未创建（用户未扫码）
                resultMap.put("code", ZFBCode);
                resultMap.put("data", "用户未扫码");
            } else if (ZFBCode.equals("10000") && trade_status.equals("WAIT_BUYER_PAY")) {
                //订单已经创建但未支付（用户扫码后但是未支付）
                resultMap.put("code", ZFBCode);
                resultMap.put("data", "non-payment");
            } else if (ZFBCode.equals("10000") && (trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED"))) {
                //判断ZFBCode是否等于”10000“ 并且 trade_status等于TRADE_SUCCESS（交易支付成功）或者 trade_status等于TRADE_FINISHED（交易结束，不可退款）
                //订单已支付（用户扫码完成并且支付成功之后）
                resultMap.put("code", ZFBCode);
                resultMap.put("data", "yes-payment");
            } else {
                resultMap.put("code", ZFBCode);
                resultMap.put("data", sub_msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public String findZFB_trade(AlipayBean alipayBean) throws Exception{
        /** 支付宝网关 **/
        String URL = gatewayUrl;

        /** 应用id，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/190/201602493024 **/
        String APP_ID = appId;

        /** 应用私钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602469554 **/
        String APP_PRIVATE_KEY = privateKey;

        /** 支付宝公钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602487431 **/
        String ALIPAY_PUBLIC_KEY = publicKey;

        /** 初始化 **/
        AlipayClient alipayClient = new DefaultAlipayClient(URL,APP_ID,APP_PRIVATE_KEY,"json","UTF-8",ALIPAY_PUBLIC_KEY,"RSA2");

        /** 实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.query（统一收单线下交易查询） **/
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        /** 设置业务参数 **/
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();

        /** 注：交易号（TradeNo）与订单号（OutTradeNo）二选一传入即可，如果2个同时传入，则以交易号为准 **/
        /** 支付接口传入的商户订单号。如：2020061601290011200000140004 **/
        model.setOutTradeNo(alipayBean.getOut_trade_no());

        /** 异步通知/查询接口返回的支付宝交易号，如：2020061622001473951448314322 **/
        //model.setTradeNo("2020061622001473951448314322");

        /** 将业务参数传至request中 **/
        request.setBizModel(model);

        /** 第三方调用（服务商模式），必须传值与支付接口相同的app_auth_token **/
        //request.putOtherTextParam("app_auth_token", "传入获取到的app_auth_token值");

        /** 通过alipayClient调用API，获得对应的response类  **/
        AlipayTradeQueryResponse response = alipayClient.execute(request);

        /** 获取接口调用结果，如果调用失败，可根据返回错误信息到该文档寻找排查方案：https://opensupport.alipay.com/support/helpcenter/101 **/
        return response.getBody();
    }

}
