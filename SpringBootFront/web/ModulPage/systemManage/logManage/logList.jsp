<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录信息</title>
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

    <link rel="stylesheet" type="text/css" href="../../../css/htmleaf-demo.css">
    <link rel="stylesheet" type="text/css" href="../../../css/normalize.css" /><!--CSS RESET-->
    <link rel="stylesheet" type="text/css" href="../../../css/popup.css">
    <link rel="stylesheet" href="../../../css/style.css">
    <link rel="stylesheet" type="text/css" href="../../../css/model/menu/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="../../../css/bootstrap.min.css">

    <script>
        userCode = "${param.userCode}";
    </script>

</head>
<style type="text/css">
    #dataTable table tr:hover td { background-color: palegoldenrod; }
</style>
<body>

<div class="htmleaf-container">
    <div style="margin:auto; width:80%;padding:2em 0;">
        <div style="padding-left: 0%;padding-bottom: 10px">
            <button class="btn btn-info" id="export" onclick="exportExcel()"><i class="fa fa-cloud-download"></i>导出</button>
        </div>
        <div id="dataTable" class="table table-striped table-bordered" cellspacing="0" width="80%">
            <!--<thead>
                <tr>
                    <th data-ray-field="firstName">First Name</th>
                    <th data-ray-field="lasName">Last Name</th>
                    <th data-ray-field="gender">Gender</th>
                    <th data-ray-field="email">Email</th>
                    <th data-ray-field="title">Title</th>
                    <th data-ray-field="city">City</th>
                </tr>
            </thead>-->
        </div>
    </div>

</div>

<script src="../../../js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script>window.jQuery || document.write('<script src="../../../js/jquery-1.7.2.min.js"><\/script>')</script>
<script src="../../../js/bootstrap.min.js"></script>
<script src="../../../js/raydreams.js"></script>
<script src="../../../js/data.js"></script>
<script type="text/javascript" src="../../../js/popup.js"></script>
<script type="text/javascript" src="logList.js"></script>

</body>
</html>