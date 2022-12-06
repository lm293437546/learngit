<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<!-- 公共 -->
<script src="../../js/jquery-1.7.2.min.js"></script>
<script src="../../js/valAll.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/valAll.css">
<!-- 弹出信息框 -->
<script type="text/javascript" src="../../js/xcConfirmMap.js" ></script>
<link rel="stylesheet" href="../../css/xcConfirmMap.css" />

<script>
	userCode = "${param.userCode}";
</script>

<link rel="stylesheet" href="../../menuStyle/layui/css/layui.css" media="all" />
<link rel="stylesheet" type="text/css" href="../../css/model/menu/font-awesome.min.css">

</head>
<body>
	<div style="margin: 15px;">
			<form id="changeP" class="layui-form" action="">
				<div class="layui-form-item">
					<label class="layui-form-label">旧密码</label>
					<div class="layui-input-inline">
						<input type="password" name="password1" lay-verify="pass1" placeholder="请输入密码" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">新密码</label>
					<div class="layui-input-inline">
						<input type="password" name="password2" lay-verify="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">确认密码</label>
					<div class="layui-input-inline">
						<input type="password" name="password3" lay-verify="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
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
		<script type="text/javascript" src="../../menuStyle/layui/layui.js"></script>
		<script src="ChangePwd.js"></script>
</body>
</html>