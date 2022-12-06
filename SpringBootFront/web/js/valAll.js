// 用户code
var userCode;

// 访问地址和端口
var url = "http://127.0.0.1:9898/";
//var url = "http://175.178.43.79:9898/";

//列表一页显示多少条
var pagesize = 15;


//自定义ajax函数
var ajaxResult;
function myAjax(url,data) {
    $.ajax({
        url: url,
        type: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        //  默认情况下，标准的跨域请求是不会发送cookie的
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        data: JSON.stringify(data) ,
        cache: false,
        async: false,
        success: function(result){
            ajaxResult =  result;
        },
        error: function(){
            ajaxResult =  -999;
        }
    });
}

//导出excel
function exportExcelWithParams (url,params,fileName) {
    var xhr = new XMLHttpRequest();
    xhr.open('post', url, true);
    xhr.responseType = 'blob';
    xhr.setRequestHeader('content-type', 'application/json');
    xhr.withCredentials = true;
    xhr.onload = function () {
        if (this.status == '200') {
            // 获取响应文件流
            var blob = this.response;
            var reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onload = function (e) {
                var a = document.createElement('a');
                a.download = fileName;
                a.href = e.target.result;
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            }
        }
    }
    xhr.send(JSON.stringify(params));
}

//获取当前时间
function getCurrentTime(i) {
    var today = new Date(); //获得当前时间
    //获得年、月、日，Date()函数中的月份是从0－11计算
    var year = today.getFullYear();
    var month = today.getMonth() + 1;
    var date = today.getDate();
    var hour = today.getHours(); //获得小时、分钟、秒
    var minute = today.getMinutes();
    var second = today.getSeconds();
    //当前时间
    var curTime;
    if(i == 0){
        curTime = year + repair(month) + repair(date) + repair(hour) + repair(minute) + repair(second);
    }
    return curTime;
}
//补0
function repair(i){
    if (i >= 0 && i <= 9) {
        return "0" + i;
    } else {
        return i;
    }
}

// 屏蔽F12 审查元素
document.onkeydown = function () {
    if (window.event && window.event.keyCode == 123) {
        //alert("F12被禁用");
        event.keyCode = 0;
        event.returnValue = false;
    }
}

//屏蔽右键菜单
document.oncontextmenu = function (event) {
    if (window.event) {
        event = window.event;
    }
    try {
        var the = event.srcElement;
        if (!((the.tagName == "INPUT" && the.type.toLowerCase() == "text") || the.tagName == "TEXTAREA")) {
            return false;
        }
        return true;
    } catch (e) {
        return false;
    }
}

//屏蔽粘贴
// document.onpaste = function (event) {
//     if (window.event) {
//         event = window.event;
//     }
//     try {
//         var the = event.srcElement;
//         if (!((the.tagName == "INPUT" && the.type.toLowerCase() == "text") || the.tagName == "TEXTAREA")) {
//             return false;
//         }
//         return true;
//     } catch (e) {
//         return false;
//     }
// }

//屏蔽复制
// document.oncopy = function (event) {
//     if (window.event) {
//         event = window.event;
//     }
//     try {
//         var the = event.srcElement;
//         if (!((the.tagName == "INPUT" && the.type.toLowerCase() == "text") || the.tagName == "TEXTAREA")) {
//             return false;
//         }
//         return true;
//     } catch (e) {
//         return false;
//     }
// }

//屏蔽剪切
// document.oncut = function (event) {
//     if (window.event) {
//         event = window.event;
//     }
//     try {
//         var the = event.srcElement;
//         if (!((the.tagName == "INPUT" && the.type.toLowerCase() == "text") || the.tagName == "TEXTAREA")) {
//             return false;
//         }
//         return true;
//     } catch (e) {
//         return false;
//     }
// }

//屏蔽选中
// document.onselectstart = function (event) {
//     if (window.event) {
//         event = window.event;
//     }
//     try {
//         var the = event.srcElement;
//         if (!((the.tagName == "INPUT" && the.type.toLowerCase() == "text") || the.tagName == "TEXTAREA")) {
//             return false;
//         }
//         return true;
//     } catch (e) {
//         return false;
//     }
// }