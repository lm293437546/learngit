
var dataAxis = [];
var data = [];

var datalist = {
    userCode: userCode,
};
myAjax(url + "api/waresManage/homePageCount",datalist);
if(ajaxResult == '-999'){
    window.wxc.xcConfirm("系统异常，请联系管理员");
}else{
    var res = ajaxResult;
    if(res.code == "0"){
        //首页总数
        $("#userCount").text(res.data.homeCount1Dto.userCount);
        $("#waresCount").text(res.data.homeCount1Dto.waresCount);
        $("#browseCount").text(res.data.homeCount1Dto.browseCount);
        $("#recommendCount").text(res.data.homeCount1Dto.recommendCount);

        //各商品浏览次数
        for (var i=0; i<res.data.homeCount2Dtos.length; i++) {
            dataAxis.push(res.data.homeCount2Dtos[i].waresType);
            data.push(res.data.homeCount2Dtos[i].waresCount);
        }

        //收益数据
        $("#revenueTotal").text(res.data.homeCount3Dto.revenueTotal);
        $("#revenueMonth").text(res.data.homeCount3Dto.revenueMonth);
        $("#revenueDay").text(res.data.homeCount3Dto.revenueDay);
        $("#dealTotal").text(res.data.homeCount3Dto.dealTotal);
        $("#dealMonth").text(res.data.homeCount3Dto.dealMonth);
        $("#dealDay").text(res.data.homeCount3Dto.dealDay);

        //商品信息
        var spxx = document.getElementById("spxx");
        var sp;
        for (var i=0; i<res.data.homeCount4Dtos.length; i++) {
            sp += "<ul class='ul_con'><li>" + res.data.homeCount4Dtos[i].bh + "</li><li>" +
                res.data.homeCount4Dtos[i].deal + "</li><li>"+ res.data.homeCount4Dtos[i].recommend +
                "</li><li>" + res.data.homeCount4Dtos[i].price+ "</li><li>" +
                res.data.homeCount4Dtos[i].income + "</li></ul>";
        }
        spxx.innerHTML = sp;

    }else{
        window.wxc.xcConfirm(res.message);
    }
}

//指定图表的配置项和数据

option = {
    tooltip: {
        formatter:'{b}:{c}次'
    },
    title: {
        text: '',
        subtext: ''
    },
    grid:{
        x:40,
        y:40,
        x2:20,
        y2:30,

    },
    xAxis: {
        data: dataAxis,
        axisLabel: {
            /*inside: true,*/
            interval:0,
            textStyle: {
                color: '#fff',
                fontSize: 12
            },
        },
        axisTick: {
            show: false,
        },
        axisLine: {
            show: true,
            symbol:['none', 'arrow'],
            symbolOffset: 12,
            lineStyle:{
                color: '#fff',
            }
        },
        z: 10
    },
    yAxis: {
        type: 'value',
        name: '单位：次',
        axisLine: {
            show: true,
            symbol: ['none', 'arrow'],
            symbolOffset: 12,
            lineStyle:{
                color: '#fff',
            }
        },
        axisTick: {
            show: false
        },
        axisLabel: {
            textStyle: {
                color: '#fff',
                fontSize: 12
            }
        }
    },

    dataZoom: [
        {
            type: 'inside'
        }
    ],
    series: [
        {
            type: 'bar',
            itemStyle: {
                color: new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                        {offset: 0, color: '#0efdff'},
                        {offset: 0.5, color: '#188df0'},
                        {offset: 1, color: '#188df0'}
                    ]
                )
            },
            emphasis: {
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#2378f7'},
                            {offset: 0.7, color: '#2378f7'},
                            {offset: 1, color: '#0efdff'}
                        ]
                    )
                }
            },
            data: data
        }
    ]
};

// Enable data zoom when user click bar.
/*var zoomSize = 6;
myChart.on('click', function (params) {
    console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
    myChart.dispatchAction({
        type: 'dataZoom',
        startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
        endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
    });
});*/

//获取dom容器
var myChart = echarts.init(document.getElementById('chartmain'));
// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);

