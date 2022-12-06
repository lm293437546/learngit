<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>支付宝支付</title>
</head>

<!-- 公共 -->
<script src="js/valAll.js"></script>
<link rel="stylesheet" type="text/css" href="css/valAll.css">
<script src="js/jquery-3.2.1.min.js"></script>
<!-- 弹出信息框 -->
<script type="text/javascript" src="js/xcConfirmMap.js" ></script>
<link rel="stylesheet" href="css/xcConfirmMap.css" />

<!-- 二维码生成前端插件 -->
<script src="js/jquery.qrcode.min.js"></script>

<body>

<a href="javascript:void(0);" onclick="pay1()">支付宝PC网页支付</a>

<a href="javascript:void(0);" onclick="pay2()">点击生成付款二维码</a>
<div id="Orcode_div" style="height: 165px;width: 165px"></div>

</body>

<script type="text/javascript">
    //商户订单编号
    var outTradeNo;
    function pay1() {
        var payData ={};
        myAjax(url + "login/alipay/pay",payData);
        if(ajaxResult == '-999'){
            window.wxc.xcConfirm("系统异常，请联系管理员");
        }else{
            document.querySelector("body").innerHTML = ajaxResult.data; // 查找到当前页面的body，将后台返回的form替换掉他的内容
            document.forms[0].submit(); // 执行submit表单提交，让页面重定向，跳转到支付宝页面
        }
    }

    function pay2() {
        var payData ={};
        myAjax(url + "login/alipay/pay2",payData);
        if(ajaxResult == '-999'){
            window.wxc.xcConfirm("系统异常，请联系管理员");
        }else{
            outTradeNo = ajaxResult.data.data.outTradeNo;
            //创建订单二维码
            createQrcode(ajaxResult.data.data.qrCode);
        }
    }

    var zfbQRCode;
    //生成付款二维码
    function createQrcode(url){
        if (zfbQRCode!=undefined && zfbQRCode!='') {
            //清空之前的二维码
            $("#Orcode_div canvas").remove()
            $("#yes_qrcode").hide();
        }
        //生成二维码放入”Orcode_div“ div
        zfbQRCode=$('#Orcode_div').qrcode({
            width: 168, //宽度
            height: 168, //高度
            text:url
        });
        findZFB_trade();
    }

    //定时任务
    var trade;
    //记录是否通知页面“用户已扫码”
    var findNumber=true
    /**
     * 查询交易状态
     */
    function findZFB_trade(){
        trade = setInterval(function(){
            var payData ={
                "out_trade_no": outTradeNo
            };
            console.log("每3秒执行一次");

            myAjax(url + "login/alipay/findZFB_tradeAction",payData);
            if(ajaxResult == '-999'){
                window.wxc.xcConfirm("系统异常，请联系管理员");
            }else{
                if (ajaxResult.code=='10000' && ajaxResult.data=='non-payment'){
                    //订单已经创建但未支付（用户扫码后但是未支付）
                    if (findNumber){
                        console.log("用户已扫码但是未支付");
                        findNumber=false;
                    }
                }else if (ajaxResult.code=='10000' && ajaxResult.data=='yes-payment'){
                    //阻止定时
                    window.clearInterval(trade);
                    alert("订单已支付，感谢支持。。。");
                }
            }
        },3000);
    }


</script>

</html>
