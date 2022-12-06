<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <!-- 公共 -->
    <script src="../../../js/jquery-1.7.2.min.js"></script>
    <script src="../../../js/valAll.js"></script>
    <link rel="stylesheet" type="text/css" href="../../../css/valAll.css">
    <!-- 弹出信息框 -->
    <script type="text/javascript" src="../../../js/xcConfirmMap.js" ></script>
    <link rel="stylesheet" href="../../../css/xcConfirmMap.css" />

    <link rel="stylesheet" type="text/css" href="../../../css/popup.css">
    <link rel="stylesheet" href="../../../css/bootstrap-3.3.4.css" />
    <link rel="stylesheet" href="../../../css/model/menu/font-awesome.min.css" />
    <link rel="stylesheet" href="../../../css/style.css">

    <!-- 时间控件 -->
    <script type="text/javascript" src="../../../js/My97DatePicker/WdatePicker.js"></script>

    <style type="text/css">
        .box {
            width: 20px;
            height: 20px;
            padding: 2px;
            border: 1px solid #ccc;
            border-radius: 2px;
        }
        .hide{
            display:none;
        }
    </style>

    <script>
        userCode = "${param.userCode}";
    </script>

</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-12"  style="padding-top:2em;">
            <div class="search d1" style="margin-bottom:10px;">
                <div method="post">
                    <input style="color:#000;" type="text" name="keyword" id="keyword" placeholder="请输入用户账号或者用户名称">
                    <button type="submit" onclick="doLoad()"></button>
                </div>
            </div>
        </div>
        <div class="col-md-12"  style="">
            <button class="btn btn-info" id="add"><i class="fa fa-plus"></i>新增</button>
            <button class="btn btn-info" id="export" onclick="exportExcel()"><i class="fa fa-cloud-download"></i>导出</button>
        </div>
        <div class="col-md-12" style="padding:1em 0;">
            <div class="table-responsive">
                <table class="table table-bordered table-striped" id="mytable" columnsEd="" addButton="add">
                    <thead>
                    <tr>
                        <th class="hide">ID</th>
                        <th>用户账号</th>
                        <th>用户名称</th>
                        <th>角色</th>
                        <th>状态</th>
                        <th>创建时间</th>
                    </tr>
                    </thead>
                    <tbody id="tableInfos">
                    </tbody>
                </table>
            </div>
        </div>

    </div>

</div>
<script type="text/javascript" src="../../../js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="../../../js/popup.js"></script>
<script type="text/javascript" src="userList.js"></script>
<script type="text/javascript">

    selectList();

    function selectList() {
        var keyword = $("#keyword").val();
        var datalist = {
            userCode: userCode,
            keyword: keyword
        };
        myAjax(url + "api/userManage/selectUserList",datalist);
        if(ajaxResult == '-999'){
            window.wxc.xcConfirm("系统异常，请联系管理员");
        }else{
            var res = ajaxResult;
            if(res.code == "0"){
                var  select = $("#tableInfos");
                var str = "";
                for (var i=0; i<res.data.length; i++){
                    var state = "无效";
                    if(res.data[i].state == '0'){
                        state = "有效";
                    }else if(res.data[i].state == '2'){
                        state = "锁定";
                    }
                    str += "<tr><td class='hide'>"+ res.data[i].usercode +"</td><td name='hideid'>" + res.data[i].usercode + "</td><td>" + res.data[i].username + "</td><td name='selectoprole'>" + res.data[i].rolename + "</td><td name='selectopstate'>" + state + "</td><td name='hidetime'>" + res.data[i].createtime + "</td></tr>";
                }
                select.text("");
                select.append(str);
            }else{
                window.wxc.xcConfirm(res.message);
            }
        }
    }

    //加载列表
    function loadList(tabedid) {
        let tabedId = tabedid.split(',');
        $.each(tabedId,function(k, v) {
            $('#'+v+'').find('tbody tr').append(colEdicHtml);     //添加按钮
        });
    }

    //搜索
    function doLoad() {
        var tableid = "mytable";
        selectList();
        loadList(tableid);
    }

    //导出
    function exportExcel() {
        var keyword = $("#keyword").val();
        var excellist = {
            userCode: userCode,
            keyword: keyword
        };
        exportExcelWithParams(url + "api/userManage/ExportUserList",excellist,"用户信息表_" + getCurrentTime(0) + "_" + userCode + ".xls");
    }

    var table = SetEditable({
        tabedId:'mytable',
        onEdit: function(row,values,tableid) {
            //编辑后回调
            selectList();
            loadList(tableid);
            window.scrollTo(0,0);
        },
        onBeforeDelete: function(row,values,tableid) {
            //删除前回调

            //表格id
            //console.log(tableid);
            //获取第一个单元格隐藏的id
            //console.log(row.children('td').eq(0).text());
        },
        onDelete: function(tableid) {
            //删除后回调
            selectList();
            loadList(tableid);
        },
        onAdd: function(tableid) {
            //添加表格后回调
            window.scrollTo(0,document.body.scrollHeight);
            //console.log(tableid);
        }
    });
</script>

</body>
</html>