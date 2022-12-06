window.onload=function(){
    doLoad();
}

// the data table itself
var dataTable = null;

jQuery(document).ready(function () {

    dataTable = jQuery("#dataTable").raytable({
        datasource: { data: [], keyfield: 'id' },
        columns: [
            { field: "userCode", title: "登录用户名" },
            { field: "userName", title: "用户姓名" },
            { field: "ip", title: "IP"},
            { field: "addr", title: "地址"},
            { field: "logtime", title: "登录时间" },
        ],
        pagesize: pagesize,
        maxPageButtons: 5,
        //rowNumbers: true,
        rowClickHandler: rowAction
    });

    jQuery(".glyphicon").css('cursor', 'pointer');

    // load some default
    //doLoad(jQuery("#dataTable"));

});

// load some data
function doLoad(sender) {
    var myData = [];

    var datalist = {
        userCode: userCode
    };

    myAjax(url + "api/logManage/selectLogList",datalist);
    if(ajaxResult == '-999'){
        window.wxc.xcConfirm("系统异常，请联系管理员");
    }else{
        var res = ajaxResult;
        if(res.code == "0"){
            for (var i=0; i<res.data.length; i++){
                myData.push({"userCode":res.data[i].usercode,"userName":res.data[i].username,"ip":res.data[i].ip,"addr":res.data[i].addr,"logtime":res.data[i].logtime});
            }
        }else{
            window.wxc.xcConfirm(res.message);
        }
    }
    dataTable.data(myData,'id');
}

// icon clicked event handler
function iconAction(event)
{
    // jquery to get the record ID back out
    var data = jQuery(event.target).data('ray-data');
    //alert('You clicked the icon with data = ' + event.data.id + ' on row ' + event.data.rowIdx );
}

// row clicked handler
function rowAction(event)
{
    // jquery to get the record ID back out
    //var id = jQuery(event.target).data('ray-key');
}

// boolean handler to determine if the cell content is rendered
function isManager(item)
{
    return (item.grade > 4);
}

// custom format handler
function parseDate(item)
{
    // source is ISO 8601
    var d = new Date(item.birthDate);
    return d.toDateString();
}

//导出
function exportExcel() {
    var keyword = $("#keyword").val();
    var excellist = {
        userCode: userCode,
        keyword: keyword
    };
    exportExcelWithParams(url + "api/logManage/ExportLogList",excellist,"登录信息表_" + getCurrentTime(0) + "_" + userCode + ".xls");
}