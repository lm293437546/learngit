window.onload = function() {
    var data = {
        userCode: userCode
    };

    myAjax(url + "api/menu/getUserName",data);
    if(ajaxResult == '-999'){
        window.wxc.xcConfirm("系统异常，请联系管理员");
    }else{
        if(ajaxResult.code == "0"){
            $("#userName").text(ajaxResult.data.username);
            if(ajaxResult.data.ischange == 0){
                $("#changePwd").trigger("click");
            }
        }else{
            window.wxc.xcConfirm(ajaxResult.message);
        }
    }

    var myiframe = document.getElementById("myiframe");
    var myif = "<iframe src='../home/homePage.jsp?userCode=" + userCode + "'></iframe>"
    myiframe.innerHTML = myif;

}
