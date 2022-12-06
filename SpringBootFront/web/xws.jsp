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
  <script src="js/jquery-3.2.1.min.js"></script>
  <script src="js/valAll.js"></script>
  <link rel="stylesheet" type="text/css" href="css/valAll.css">
  <!-- 弹出信息框 -->
  <script type="text/javascript" src="js/xcConfirmMap.js" ></script>
  <link rel="stylesheet" href="css/xcConfirmMap.css" />

  <!-- 引入bootstrap -->
  <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="xws.css">
  <!-- 引入JQuery  bootstrap.js-->
  <script src="js/bootstrap.min.js"></script>
  <script src="xws.js"></script>
</head>
<body>
<div class="container" id="top">
  <div class="row" style="margin-top: 100px; ">
    <h1 style="text-align: center">XXX管理系统</h1>
    <div class="col-md-4"></div>
    <div class="col-md-4" id="login-box">
      <div class="form-horizontal" role="form" id="from1" method="post">
        <div class="form-group">
          <label class="col-sm-3 control-label">用户名</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="userCode" placeholder="请输入用户名" name="username" />
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3 control-label">密码</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password" />
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <%--<div class="checkbox">--%>
              <%--<label class="checkbox-inline">--%>
                <%--<input type="radio" name="role" value="0" checked>管理员--%>
              <%--</label>--%>
              <%--<label class="checkbox-inline">--%>
                <%--<input type="radio" name="role" value="1">教师--%>
              <%--</label>--%>
              <%--<label class="checkbox-inline">--%>
                <%--<input type="radio" name="role" value="2">学生--%>
              <%--</label>--%>
            <%--</div>--%>
          </div>
        </div>
        <div class="form-group pull-right" style="margin-right: 15px">
          <div class="col-sm-offset-2 col-sm-10">
            <button onclick="login()" id="logbutt" class="btn btn-default btn-info">登录</button>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-4"></div>
  </div>
</div>
</body>
</html>