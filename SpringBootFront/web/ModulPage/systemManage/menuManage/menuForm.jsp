<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>编辑菜单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <!-- 公共 -->
    <script src="../../../js/jquery-3.2.1.min.js"></script>
    <script src="../../../js/valAll.js"></script>
    <link rel="stylesheet" type="text/css" href="../../../css/valAll.css">
    <!-- 弹出信息框 -->
    <script type="text/javascript" src="../../../js/xcConfirmMap.js" ></script>
    <link rel="stylesheet" href="../../../css/xcConfirmMap.css" />

    <link rel="stylesheet" href="../../../menuStyle/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="../../../css/model/menu/global.css" media="all">
    <link rel="stylesheet" type="text/css" href="../../../css/normalize.css" /><!--CSS RESET-->
    <link rel="stylesheet" type="text/css" href="../../../css/popup.css">
    <link rel="stylesheet" type="text/css" href="../../../css/model/menu/font-awesome.min.css">

    <script>
        userCode = "${param.userCode}";
        var menuId = "${param.id}";
    </script>

</head>
<body>
<div style="margin: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend><button class="layui-btn layui-btn-danger" onclick="doback()">返回</button></legend>
    </fieldset>

    <form class="layui-form">

        <div class="layui-form-item">
            <label class="layui-form-label">父级菜单</label>
            <div class="layui-input-block">
                <select name="parent" id="parent" lay-filter="aihao">
                    <option value=""></option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">菜单名称</label>
            <div class="layui-input-block">
                <input type="text" name="menuname" id="menuname" value="" lay-verify="title" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-block">
                <input type="text" name="addr" id="addr" value="" lay-verify="title" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>

    </form>
</div>
<script type="text/javascript" src="../../../menuStyle/layui/layui.js"></script>
<script type="text/javascript" src="../../../js/popup.js"></script>
<script type="text/javascript" src="menuForm.js"></script>
</body>
</html>