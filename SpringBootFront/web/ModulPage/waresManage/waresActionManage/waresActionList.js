window.onload=function(){
    doLoad();
}

// the data table itself
var dataTable = null;

jQuery(document).ready(function () {

    dataTable = jQuery("#dataTable").raytable({
        datasource: { data: [], keyfield: 'id' },
        columns: [
            { field: "userip", title: "用户ip" },
            { field: "useraddr", title: "用户地址"},
            { field: "warescode", title: "商品编码"},
            { field: "waresname", title: "商品名称"},
            { field: "actiontime", title: "浏览时间"},
            { field: "reserve1", title: "备注" }
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

    var keyword = $("#keyword").val();

    var datalist = {
        userCode: userCode,
        keyword: keyword
    };

    myAjax(url + "api/waresManage/selectWaresActionList",datalist);
    if(ajaxResult == '-999'){
        window.wxc.xcConfirm("系统异常，请联系管理员");
    }else{
        var res = ajaxResult;
        if(res.code == "0"){
            for (var i=0; i<res.data.length; i++){
                myData.push({"userip":res.data[i].userip,"useraddr":res.data[i].useraddr,"warescode":res.data[i].warescode,"waresname":res.data[i].waresname,"actiontime":res.data[i].actiontime,"reserve1":res.data[i].reserve1});
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
    exportExcelWithParams(url + "api/waresManage/ExportWaresActionList",excellist,"商品浏览信息表_" + getCurrentTime(0) + "_" + userCode + ".xls");
}