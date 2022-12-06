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

    <link type="text/css" href="css/public.css" rel="stylesheet">
    <link type="text/css" href="css/index.css" rel="stylesheet">
    <script type="text/javascript">
        document.documentElement.style.fontSize = document.documentElement.clientWidth /768*100 + 'px';
    </script>

    <script src="js/echarts-4.2.1.min.js"></script>

</head>
<style>
    .abc{

    }
</style>
<script>
    userCode = "${param.userCode}";
</script>
<body>
<div class="bg">
    <div class="title">数据统计</div>
    <div class="leftMain">
        <div class="leftMain_top">
            <div class="leftMain_topIn">
                <ul>
                    <li>
                        <div class="liIn">
                            <h3>用户数量</h3>
                            <p class="shu"><span class="shu1" id="userCount"></span><i>个</i></p>
                            <div class="zi"><span class="span1"></span><span></span></div>
                            <span class="border_bg_leftTop"></span>
                            <span class="border_bg_rightTop"></span>
                            <span class="border_bg_leftBottom"></span>
                            <span class="border_bg_rightBottom"></span>
                        </div>
                    </li>
                    <li>
                        <div class="liIn">
                            <h3>商品数量</h3>
                            <p class="shu"><span class="shu2" id="waresCount"></span><i>个</i></p>
                            <div class="zi"><span class="span1"></span><span></span></div>
                            <span class="border_bg_leftTop"></span>
                            <span class="border_bg_rightTop"></span>
                            <span class="border_bg_leftBottom"></span>
                            <span class="border_bg_rightBottom"></span>
                        </div>
                    </li>
                    <li>
                        <div class="liIn">
                            <h3>用户浏览商品次数</h3>
                            <p class="shu"><span class="shu3" id="browseCount"></span><i>次</i></p>
                            <div class="zi"><span class="span1"></span><span></span></div>
                            <span class="border_bg_leftTop"></span>
                            <span class="border_bg_rightTop"></span>
                            <span class="border_bg_leftBottom"></span>
                            <span class="border_bg_rightBottom"></span>
                        </div>
                    </li>
                    <li>
                        <div class="liIn">
                            <h3>推荐成交次数</h3>
                            <p class="shu"><span class="shu4" id="recommendCount"></span><i>次</i></p>
                            <div class="zi"><span class="span1"></span><span></span></div>
                            <span class="border_bg_leftTop"></span>
                            <span class="border_bg_rightTop"></span>
                            <span class="border_bg_leftBottom"></span>
                            <span class="border_bg_rightBottom"></span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="leftMain_middle">
            <div class="leftMain_middle_left">
                <div class="leftMain_middle_leftIn">
                    <h3>各商品浏览次数</h3>
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div class="biaoge" style="width:100%; height:25vh" id="chartmain"></div>
                    <span class="border_bg_leftTop"></span>
                    <span class="border_bg_rightTop"></span>
                    <span class="border_bg_leftBottom"></span>
                    <span class="border_bg_rightBottom"></span>
                </div>
            </div>

            <div class="leftMain_middle_right">
                <div class="leftMain_middle_rightIn">
                    <h3>收益数据</h3>
                    <div class="biaoge biaoge_bi" style="width:100%; height:25vh">
                        <ul>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu1" id="revenueTotal"></p>
                                    <p class="zi">总收益</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu2" id="revenueMonth"></p>
                                    <p class="zi">当月收益</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu3" id="revenueDay"></p>
                                    <p class="zi">当日收益</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu4" id="dealTotal"></p>
                                    <p class="zi">总成交量</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu5" id="dealMonth"></p>
                                    <p class="zi">当月成交量</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu6" id="dealDay"></p>
                                    <p class="zi">当日成交量</p>
                                </div>
                            </li>
                        </ul>

                    </div>
                    <span class="border_bg_leftTop"></span>
                    <span class="border_bg_rightTop"></span>
                    <span class="border_bg_leftBottom"></span>
                    <span class="border_bg_rightBottom"></span>
                </div>
            </div>
        </div>
    </div>
    <div class="rightMain">
        <div class="rightMain_bottom">
            <div class="rightMain_bottomIn">
                <h3>商品信息</h3>
                <div class="biaoge biaoge_list" style="width:100%; height:36vh">
                    <div class="biaoge_listIn">
                        <ul class="ul_title">
                            <li>商品</li>
                            <li>总成交</li>
                            <li>推荐成交</li>
                            <li>单价</li>
                            <li>总收入</li>
                        </ul>
                        <div class="ul_list">
                            <div class="ul_listIn" id="spxx">
                            </div>
                        </div>
                    </div>

                </div>
                <span class="border_bg_leftTop"></span>
                <span class="border_bg_rightTop"></span>
                <span class="border_bg_leftBottom"></span>
                <span class="border_bg_rightBottom"></span>
            </div>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
<!--数字增长累加动画-->
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery.numscroll.js" type="text/javascript" charset="utf-8"></script>
<script src="homePage.js"></script>
<script type="text/javascript">
    $(".shu1").numScroll();
    $(".shu2").numScroll();
    $(".shu3").numScroll();
    $(".shu4").numScroll();
    $(".shu5").numScroll();
    $(".shu6").numScroll();

    /*$(".num2").numScroll({
        time:5000
    });*/
</script>
</body>
</html>
