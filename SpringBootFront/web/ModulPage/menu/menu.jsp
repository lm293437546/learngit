<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>XXX管理系统</title>
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
<link rel="stylesheet" href="../../css/model/menu/global.css" media="all">
<link rel="stylesheet" type="text/css" href="../../css/model/menu/font-awesome.min.css">

</head>
<body>
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header header header-demo">
			<div class="layui-main">
				<div class="admin-login-box">
					<a class="logo" style="left: 0;" href="#"> <span
						style="font-size: 22px;">后台管理系统</span>
					</a>
					<div class="admin-side-toggle">
						<i class="fa fa-bars" aria-hidden="true"></i>
					</div>
				</div>
				<ul class="layui-nav admin-header-item">
					<li class="layui-nav-item"><a style="font-size: 20px"
						id="myclock"></a></li>
					<!-- <li class="layui-nav-item">
							<a href="javascript:;">清除缓存</a>
						</li>
						<li class="layui-nav-item">
							<a href="javascript:;">浏览网站</a>
						</li> -->
					<!-- <li class="layui-nav-item" id="userInfor">
							<a href="javascript:;">个人信息</a>
						</li> -->
					<li class="layui-nav-item"><a href="javascript:;"
						class="admin-header-user"> <img src="../../images/0.jpg" /> <span id="userName"></span>
					</a>
						<dl class="layui-nav-child">
							<%--<dd>--%>
								<%--<a href="javascript:;" id="userInfor"><i--%>
									<%--class="fa fa-user-circle" aria-hidden="true"></i> 个人信息</a>--%>
							<%--</dd>--%>
							<dd>
								<a href="javascript:;" id="changePwd"><i class="fa fa-gear"
									aria-hidden="true"></i> 修改密码</a>
							</dd>
							<dd>
								<a href="javascript:;" id="cancellation"><i class="fa fa-sign-out"
									aria-hidden="true"></i> 注销</a>
							</dd>
						</dl></li>
				</ul>
				<!-- <ul class="layui-nav admin-header-item-mobile">
						<li class="layui-nav-item">
							<a href="login.html"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
						</li>
					</ul> -->
			</div>
		</div>
		<div class="layui-side layui-bg-black" id="admin-side">
			<div class="layui-side-scroll" id="admin-navbar-side"
				lay-filter="side"></div>
		</div>
		<div class="layui-body"
			style="bottom: 0;border-left: solid 2px #1AA094;" id="admin-body">
			<div class="layui-tab admin-nav-card layui-tab-brief"
				lay-filter="admin-tab">
				<ul class="layui-tab-title">
					<li class="layui-this"><i class="fa fa-dashboard"
						aria-hidden="true"></i> <cite>首页</cite></li>
				</ul>
				<div class="layui-tab-content"
					style="min-height: 150px; padding: 5px 0 0 0;">
					<div class="layui-tab-item layui-show" id="myiframe">
					</div>
				</div>
			</div>
		</div>
		<div class="layui-footer footer footer-demo" id="admin-footer">
			<div class="layui-main">
				<p>
					2022 &copy; <a href="#">XXX后台管理系统</a>
				</p>
			</div>
		</div>
		<div class="site-tree-mobile layui-hide">
			<i class="layui-icon">&#xe602;</i>
		</div>
		<div class="site-mobile-shade"></div>

		<script type="text/javascript" src="../../menuStyle/layui/layui.js"></script>
		<!-- 获取菜单url -->
		<script type="text/javascript" src="../../js/model/menu/nav.js"></script>
		<script src="../../js/model/menu/index.js"></script>
		<script src="menu.js"></script>
	</div>
</body>
</html>