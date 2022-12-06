// window.onload = function() {
//     userCode = "cs";
// }

$(document).keyup(function (event) {
    if (event.keyCode == 13) {
        $("#logbutt").trigger("click");
    }
})

function login() {
    var userCode = $("#userCode").val();
    var password = $("#password").val();

    if(userCode.length < 1){
        window.wxc.xcConfirm("请输入用户名")
        return;
    }
    if(password.length < 1){
        window.wxc.xcConfirm("请输入密码");
        return;
    }

    var data = {
            userCode: userCode,
            password: password
        };
    myAjax(url + "login/userlogin",data);
    if(ajaxResult == '-999'){
        window.wxc.xcConfirm("系统异常，请联系管理员");
    }else{
        var res = ajaxResult;
        if(res.code == "0"){
            //动态创建表单，隐藏参数
            var f=document.createElement('form');
            f.style.display='none';
            f.action='ModulPage/menu/menu.jsp';
            f.method='post';
            f.innerHTML='<input type="hidden" name="userCode" value="'+res.data+'"/>';
            document.body.appendChild(f);
            f.submit();
            // 页面跳转
            //window.location.replace("ModulPage/menu/menu.jsp?userCode=" + res.data);
        }else{
            window.wxc.xcConfirm(res.message);
        }
    }
}