layui.use(['form', 'layedit', 'laydate'], function() {
    var form = layui.form(),
        layer = layui.layer,
        layedit = layui.layedit,
        laydate = layui.laydate;

    //监听提交
    form.on('submit(demo1)', function(data) {
        if(data.field["password1"].length == '0'){
            layer.alert('旧密码不能为空', {
                title: '密码验证'
            })
            //document.getElementById("changeP").reset();
            return false;
        }else if (data.field["password2"].length == '0'){
            layer.alert('请输入新密码', {
                title: '密码验证'
            })
            //document.getElementById("changeP").reset();
            return false;
        }else if (data.field["password3"].length == '0'){
            layer.alert('请重新输入新密码', {
                title: '密码验证'
            })
            //document.getElementById("changeP").reset();
            return false;
        }else if (data.field["password1"] == data.field["password2"]){
            layer.alert('新密码不能和旧密码相同', {
                title: '密码验证'
            })
            //document.getElementById("changeP").reset();
            return false;
        }else{
            if(data.field["password2"] != data.field["password3"]){
                layer.alert('两次输入的密码不正确', {
                    title: '密码验证'
                })
                //document.getElementById("changeP").reset();
                return false;
            }else{
                var data2 = {
                    userCode: userCode,
                    oldPwd: data.field["password1"],
                    newPwd: data.field["password2"]
                }
                myAjax(url + "api/menu/changePwd",data2);
                if(ajaxResult == '-999'){
                    window.wxc.xcConfirm("系统异常，请联系管理员");
                }else{
                    var res = ajaxResult;
                    if(res.code == "0"){
                        //刷新主页面
                        window.parent.location.reload();
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        var data3 = {
                            userCode: userCode
                        };
                        //注销
                        myAjax(url + "api/menu/cancellation",data3);
                    }else{
                        layer.alert('旧密码错误,请重新输入', {
                            title: '密码验证'
                        })
                        document.getElementById("changeP").reset();
                        return false;
                    }
                }
                return false;
            }
        }

    });
});