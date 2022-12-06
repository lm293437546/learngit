<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>

    <!-- 公共 -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/valAll.js"></script>
    <link rel="stylesheet" type="text/css" href="css/valAll.css">
    <!-- 弹出信息框 -->
    <script type="text/javascript" src="js/xcConfirmMap.js" ></script>
    <link rel="stylesheet" href="css/xcConfirmMap.css" />

</head>

<body>

<pre id="conent" style="font-size: 16px"></pre>

</body>

<script>
    myAjax(url + "login/getViewersInfo",null);
    $("#conent").text(ajaxResult.data);
</script>

</html>