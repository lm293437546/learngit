<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>权限管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <!-- 公共 -->
    <script src="../../../js/valAll.js"></script>
    <link rel="stylesheet" type="text/css" href="../../../css/valAll.css">
    <script src="../../../js/jquery-3.2.1.min.js"></script>
    <!-- 弹出信息框 -->
    <script type="text/javascript" src="../../../js/xcConfirmMap.js" ></script>
    <link rel="stylesheet" href="../../../css/xcConfirmMap.css" />

    <link rel="stylesheet" href="../../../zTree/css/demo.css" type="text/css">
    <link rel="stylesheet" href="../../../zTree/css/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../../../zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="../../../zTree/js/jquery.ztree.excheck.js"></script>

</head>

<script type="text/javascript">

    userCode = "${param.userCode}";
    var powid = "${param.id}";

    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeCheck: beforeCheck,
            onCheck: onCheck
        }
    };

    var zNodes =[];

    var pwdata = {
        userCode: userCode,
        id: powid
    }
    myAjax(url + "api/roleManage/power",pwdata);
    if(ajaxResult == '-999'){
        window.wxc.xcConfirm("系统异常，请联系管理员");
    }else{
        var res = ajaxResult;
        if(res.code == "0"){
            var alllist = res.data.menuList[0].children;
            var uslist = res.data.menuRoleList;
            for (var i=0; i<alllist.length; i++){
                //第二级
                var flag1 = 1;
                for(var j=0; j<uslist.length; j++){
                    if(alllist[i].menuid == uslist[j].menuid){
                        flag1 = 0;
                        break;
                    }
                }
                if(flag1 == 0){
                    zNodes.push({id:alllist[i].menuid,pId:0,name:alllist[i].menuname,open:true,checked:true});
                }else{
                    zNodes.push({id:alllist[i].menuid,pId:0,name:alllist[i].menuname,open:true});
                }
                //第三级
                var uschlist = alllist[i].children;
                for (var ii=0; ii<uschlist.length; ii++){
                    var flag2 = 1;
                    for(var jj=0; jj<uslist.length; jj++){
                        if(uschlist[ii].menuid == uslist[jj].menuid){
                            flag2 = 0;
                            break;
                        }
                    }
                    if(flag2 == 0){
                        zNodes.push({id:uschlist[ii].menuid,pId:uschlist[ii].parentid,name:uschlist[ii].menuname,open:true,checked:true});
                    }else{
                        zNodes.push({id:uschlist[ii].menuid,pId:uschlist[ii].parentid,name:uschlist[ii].menuname,open:true});
                    }
                }
            }
        }else{
            window.wxc.xcConfirm(res.message);
        }
    }
    var code, log, className = "dark";
    function beforeCheck(treeId, treeNode) {
        className = (className === "dark" ? "":"dark");
        showLog("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
        return (treeNode.doCheck !== false);
    }
    function onCheck(e, treeId, treeNode) {
        showLog("[ "+getTime()+" onCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
    }
    function showLog(str) {
        if (!log) log = $("#log");
        log.append("<li class='"+className+"'>"+str+"</li>");
        if(log.children("li").length > 6) {
            log.get(0).removeChild(log.children("li")[0]);
        }
    }
    function getTime() {
        var now= new Date(),
            h=now.getHours(),
            m=now.getMinutes(),
            s=now.getSeconds(),
            ms=now.getMilliseconds();
        return (h+":"+m+":"+s+ " " +ms);
    }

    function checkNode(e) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            type = e.data.type,
            nodes = zTree.getSelectedNodes();
        if (type.indexOf("All")<0 && nodes.length == 0) {
            alert("请先选择一个节点");
        }

        if (type == "checkAllTrue") {
            zTree.checkAllNodes(true);
        } else if (type == "checkAllFalse") {
            zTree.checkAllNodes(false);
        } else {
            var callbackFlag = $("#callbackTrigger").attr("checked");
            for (var i=0, l=nodes.length; i<l; i++) {
                if (type == "checkTrue") {
                    zTree.checkNode(nodes[i], true, false, callbackFlag);
                } else if (type == "checkFalse") {
                    zTree.checkNode(nodes[i], false, false, callbackFlag);
                } else if (type == "toggle") {
                    zTree.checkNode(nodes[i], null, false, callbackFlag);
                }else if (type == "checkTruePS") {
                    zTree.checkNode(nodes[i], true, true, callbackFlag);
                } else if (type == "checkFalsePS") {
                    zTree.checkNode(nodes[i], false, true, callbackFlag);
                } else if (type == "togglePS") {
                    zTree.checkNode(nodes[i], null, true, callbackFlag);
                }
            }
        }
    }

    function setAutoTrigger(e) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.setting.check.autoCheckTrigger = $("#autoCallbackTrigger").attr("checked");
        $("#autoCheckTriggerValue").html(zTree.setting.check.autoCheckTrigger ? "true" : "false");
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $("#checkTrue").bind("click", {type:"checkTrue"}, checkNode);
        $("#checkFalse").bind("click", {type:"checkFalse"}, checkNode);
        $("#toggle").bind("click", {type:"toggle"}, checkNode);
        $("#checkTruePS").bind("click", {type:"checkTruePS"}, checkNode);
        $("#checkFalsePS").bind("click", {type:"checkFalsePS"}, checkNode);
        $("#togglePS").bind("click", {type:"togglePS"}, checkNode);
        $("#checkAllTrue").bind("click", {type:"checkAllTrue"}, checkNode);
        $("#checkAllFalse").bind("click", {type:"checkAllFalse"}, checkNode);

        $("#autoCallbackTrigger").bind("change", {}, setAutoTrigger);
    });

    function doSubmiss(){
        var id = powid;
        var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
        nodes=treeObj.getCheckedNodes(true);
        var jd = [];
        jd.push(1);   //自动添加顶级菜单
        for(var i=0;i<nodes.length;i++){
            jd.push(nodes[i].id);
            //alert("节点id:"+nodes[i].id+",节点名称"+nodes[i].name); //获取选中节点的值
        }

        var editpodata = {
            userCode: userCode,
            nodes: jd,
            id: powid
        }
        myAjax(url + "api/roleManage/editPower",editpodata);
        if(ajaxResult == '-999'){
            window.wxc.xcConfirm("系统异常，请联系管理员");
        }else{
            var res = ajaxResult;
            if(res.code == "0"){
                window.parent.location.reload();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }else{
                window.wxc.xcConfirm(res.message);
            }
        }
    }

</script>

<body>

<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
    <div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-left: 200px;margin-top: 20px;">
            <legend><button class="layui-btn layui-btn-danger" onclick="doSubmiss()">提交</button></legend>
        </fieldset>
    </div>
</div>

</body>
</html>