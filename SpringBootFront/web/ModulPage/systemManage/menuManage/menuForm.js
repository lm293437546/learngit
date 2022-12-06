layui.use(['form', 'layedit', 'laydate'], function() {
    var form = layui.form(),
        layer = layui.layer,
        layedit = layui.layedit,
        laydate = layui.laydate;

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');

    //初始父级菜单id
    var usparent;

    menuinfo();

    function menuinfo(){
        var infodata = {
            userCode: userCode,
            id: menuId
        }
        myAjax(url + "api/menuManage/menuinfo",infodata);
        if(ajaxResult == '-999'){
            window.wxc.xcConfirm("系统异常，请联系管理员");
        }else{
            var res = ajaxResult;
            if(res.code == "0"){
                usparent = res.data.parentid;

                //初始化角色
                var str = "";
                var  select = $("#parent");
                for (var i=0; i<res.data.children.length; i++){
                    if(res.data.children[i].menuid == res.data.parentid){
                        str+="<option selected='' value='"+res.data.children[i].menuid+"'>"+res.data.children[i].menuname+"</option>";
                    }else{
                        str+="<option value='"+res.data.children[i].menuid+"'>"+res.data.children[i].menuname+"</option>";
                    }
                }
                select.append(str);
                if(menuId != '-1'){
                    select.attr("disabled", "disabled");
                }
                //初始化其他
                document.getElementById("menuname").value = res.data.menuname;
                document.getElementById("addr").value = res.data.addr;
                form.render();
            }else{
                window.wxc.xcConfirm(res.message);
            }
        }
    }

    //监听提交
    form.on('submit(demo1)', function(data) {
        if(data.field["menuname"] == ''){
            var txt="菜单名称不能为空";
            window.wxc.xcConfirm(txt);
        }else if(data.field["parent"] == ''){
            var txt="请选择父级菜单";
            window.wxc.xcConfirm(txt);
        }else{

            var type;
            if(menuId == '-1'){
                type = "add";
            }else{
                type = "update";
            }
            var editdata = {
                userCode: userCode,
                menuId: menuId,
                parentId: data.field["parent"],
                menuName: data.field["menuname"],
                addr: data.field["addr"],
                type: type
            }
            myAjax(url + "api/menuManage/editMenu",editdata);
            if(ajaxResult == '-999'){
                window.wxc.xcConfirm("系统异常，请联系管理员");
            }else{
                var res = ajaxResult;
                if(res.code == "0"){
                    doback();
                }else{
                    window.wxc.xcConfirm(res.message);
                }
            }
        }
        return false;
    });
});

function doback(){
    window.location.href = "../../../ModulPage/systemManage/menuManage/menuList.jsp?userCode=" + userCode;
}