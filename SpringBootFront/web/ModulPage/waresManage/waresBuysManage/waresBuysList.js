/*
Bootstable
 @description  Javascript library to make HMTL tables editable, using Bootstrap
 @version 1.2
*/
"use strict";
//Global variables
var params = null;
var newColHtml = '<div class="btn-group pull-right">'+
    '<button id="bEdit" type="button" class="btn btn-sm btn-default" onclick="rowEdit(this);">' +
    '<span class="glyphicon glyphicon-pencil" > </span>'+
    '</button>'+
    '<button id="bElim" type="button" class="btn btn-sm btn-default" onclick="rowElim(this);">' +
    '<span class="glyphicon glyphicon-trash" > </span>'+
    '</button>'+
    '<button id="bAcep" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="rowAcep(this);">' +
    '<span class="glyphicon glyphicon-ok" > </span>'+
    '</button>'+
    '<button id="bCanc" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="rowCancel(this);">' +
    '<span class="glyphicon glyphicon-remove" > </span>'+
    '</button>'+
    '</div>';
var colEdicHtml = '<td name="buttons">'+newColHtml+'</td>';
var SetEditable = function (options) {
    var defaults = {
        tabedId:null,					//tableId对列
        onEdit: function() {},			//编辑后回调
        onBeforeDelete: function() {},	//删除前回调
        onDelete: function() {},		//删除后回调
        onAdd: function() {}			//添加新行后调用
    };
    params = $.extend(defaults, options);
    let tabedId = params.tabedId.split(',');
    if(tabedId){
        $.each(tabedId,function(k, v) {
            $('#'+v+'').find('thead tr').append('<th name="buttons"></th>');  	//添加按钮的标题
            $('#'+v+'').find('tbody tr').append(colEdicHtml);					//添加按钮
            let addButton = $('#'+v+'').attr('addButton');						//添加按钮
            if (addButton) {
                $('#'+addButton+'').click(function() {
                    rowAddNew(v);
                });
            }
        });
    }
};
//设置可编辑字段
function IterarCamposEdit($cols, tarea) {
    let n = 0;
    let columnsEd = $($cols).closest('table').attr('columnsEd');//当前可编辑对列
    let colsEdi = columnsEd ? columnsEd.split(',') : null;
    $cols.each(function() {
        n++;
        if ($(this).attr('name')=='buttons') return;  //排除列按钮
        if ($(this).attr('name')=='nochange') return;  //不可修改项
        if (!EsEditable(n-1,colsEdi)) return;
        tarea($(this));
    });
    function EsEditable(idx,colsEdi) {
        //判断列是否可编辑
        if (colsEdi==null) {//null代表全部编辑
            return true;
        } else {
            for (var i = 0; i < colsEdi.length; i++) {
                if (idx == colsEdi[i]) return true;
            }
            return false;
        }
    }
}
//编辑状态恢复
function FijModoNormal(but) {
    $(but).parent().find('#bAcep').hide();
    $(but).parent().find('#bCanc').hide();
    $(but).parent().find('#bEdit').show();
    $(but).parent().find('#bElim').show();
    $(but).parents('tr').attr('id', '');
}
//校验编辑状态
function ModoEdicion($row) {
    return $row.attr('id')=='editing' ? true : false;
}
//编辑事件
function rowEdit(but) {
    let $row = $(but).parents('tr');
    let $cols = $row.find('td');
    if (ModoEdicion($row)) return; //获取是否编辑中
    IterarCamposEdit($cols, function($td) {
        let cont = $td.html();
        let div = '<div style="display: none;">' + cont + '</div>';
        let input;
        if($td.attr('name') == 'isnumber'){
            //只能输入数字
            input = '<input class="form-control input-sm" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')"  value="' + cont + '">';
        }else if($td.attr('name') == 'istime'){
            //时间控件
            input = '<input class="form-control input-sm" type="text" onClick="WdatePicker({el:this,dateFmt:\'yyyy-MM-dd\'})" value="' + cont +'">';
        }else if($td.attr('name') == 'selectop'){
            //下拉框
            var infodata = {
                userCode: userCode,
                id: '-1'
            }
            myAjax(url + "api/waresManage/buysinfo",infodata);
            if(ajaxResult == '-999'){
                window.wxc.xcConfirm("系统异常，请联系管理员");
                return;
            }else{
                var res = ajaxResult;
                if(res.code == "0"){
                    var str = "<option value=''></option>";
                    for (var i=0; i<res.data.types.length; i++){
                        if(res.data.types[i].warescode == cont){
                            str+="<option selected='' value='"+res.data.types[i].warescode+"'>"+ res.data.types[i].warescode + "-" + res.data.types[i].waresname+"</option>";
                        }else{
                            str+="<option value='"+res.data.types[i].warescode+"'>"+ res.data.types[i].warescode + "-" + res.data.types[i].waresname+"</option>";
                        }
                    }
                    input = '<select class="form-control input-sm">' + str + '</select>';
                }else{
                    window.wxc.xcConfirm(res.message);
                    return;
                }
            }
        }else{
            input = '<input class="form-control input-sm" value="' + cont + '">';
        }
        $td.html(div + input);
    });
    $(but).parent().find('#bAcep').show();
    $(but).parent().find('#bCanc').show();
    $(but).parent().find('#bEdit').hide();
    $(but).parent().find('#bElim').hide();
    $row.attr('id', 'editing'); //设置编辑状态
}
//确定编辑
function rowAcep(but) {
    let $row = $(but).parents('tr');
    let $cols = $row.find('td');
    if (!ModoEdicion($row)) return;

    let tableId = $(but).closest('table').attr('id');
    let values={};

    $cols.each(function(i){
        if ($(this).attr('name') != 'buttons'){
            if($(this).attr('name') == 'selectop'){
                values[i] = $(this).find('select').val();
            }else{
                values[i] = $(this).find('input').val();
            }
            //console.log("第" + i + "个参数为：" + values[i]);
        }
    });
    if(values[1] == ''){
        window.wxc.xcConfirm("购买人不能为空");
        return;
    }
    if(values[2] == ''){
        window.wxc.xcConfirm("商品编码不能为空");
        return;
    }
    if(values[4] == ''){
        window.wxc.xcConfirm("成交金额不能为空");
        return;
    }
    if(values[5] == ''){
        window.wxc.xcConfirm("购买时间不能为空");
        return;
    }

    var type;
    if(null == values[0] || values[0] == ''){
        //新增
        type = "add";
    }else{
        //修改
        type = "update";
    }
    var editdata = {
        userCode: userCode,
        buyno: values[0],
        buyer: values[1],
        warescode: values[2],
        buytime: values[5],
        gmv: values[4],
        reserve1: values[6],
        type: type
    }

    myAjax(url + "api/waresManage/editBuys",editdata);
    if(ajaxResult == '-999'){
        window.wxc.xcConfirm("系统异常，请联系管理员");
    }else{
        var res = ajaxResult;
        if(res.code == "0"){
            IterarCamposEdit($cols, function($td) {
                let cont = $td.find('input').val();
                $td.html(cont);
            });
            FijModoNormal(but);
            //编辑后回调函数
            params.onEdit($row,values,tableId);
        }else{
            window.wxc.xcConfirm(res.message);
        }
    }
}
//取消编辑
function rowCancel(but) {
    let $row = $(but).parents('tr');
    let $cols = $row.find('td');
    if (!ModoEdicion($row)) return;
    IterarCamposEdit($cols, function($td) {
        let cont = $td.find('div').html();
        $td.html(cont);
    });
    FijModoNormal(but);
}
//删除事件
function rowElim(but) {
    let $row = $(but).parents('tr');
    let tableId = $(but).closest('table').attr('id');
    let values={};
    $row.each(function(i){
        $(this).children('td').each(function(j){
            if($(this).text()!='    '){
                values[j] = $(this).text();
            }
        });
    });

    if(values[0] == null || values[0] == ''){
        //删除表格前事件
        params.onBeforeDelete($row,values,tableId);
        $row.remove();
        //删除表格后事件
        params.onDelete(tableId);
        return;
    }

    var deleData = {
        userCode: userCode,
        buyno: values[0]
    }
    var popup = new Popup({
        'type': 'submit',
        'title': '',
        'text': '确定要删除购买编号为：' + values[0] + ' 的成交记录吗',
        'cancelbutton': true,
        'closeCallBack': function(){
            //alert('点击了关闭')
        },
        'submitCallBack': function(){
            //alert('点击了确定');
            myAjax(url + "api/waresManage/deleteBuys",deleData);
            if(ajaxResult == '-999'){
                window.wxc.xcConfirm("系统异常，请联系管理员");
            }else{
                var res = ajaxResult;
                if(res.code == "0"){
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);

                    //删除表格前事件
                    params.onBeforeDelete($row,values,tableId);
                    $row.remove();
                    //删除表格后事件
                    params.onDelete(tableId);

                }else{
                    window.wxc.xcConfirm(res.message);
                }
            }
        }
    })

}
//添加新的表格行
function rowAddNew(tabId) {
    let $tab_en_edic = $("#" + tabId);
    let $filas = $tab_en_edic.find('tbody tr');
    if ($filas.length==0) {
        let $cols = $tab_en_edic.find('thead tr').find('th');
        let htmlDat = '';
        $cols.each(function(i) {
            if ($(this).attr('name')=='buttons') {
                htmlDat = htmlDat + colEdicHtml;
            } else {
                if(htmlDat){
                    if(i == 1){
                        htmlDat = htmlDat + '<td name="hideid"></td>';
                    }else if(i == 2){
                        htmlDat = htmlDat + '<td name="selectop"></td>';
                    }else if(i == 3){
                        htmlDat = htmlDat + '<td name="nochange"></td>';
                    }else if(i == 4){
                        htmlDat = htmlDat + '<td name="isnumber"></td>';
                    }else if(i == 5){
                        htmlDat = htmlDat + '<td name="istime"></td>';
                    }else{
                        htmlDat = htmlDat + '<td></td>';
                    }
                }else{
                    htmlDat = htmlDat + '<td class="hide"></td>';
                }
            }
        });
        $tab_en_edic.find('tbody').append('<tr>'+htmlDat+'</tr>');
        let $ultFila = $tab_en_edic.find('tr:last');
        let $colss = $ultFila.find('td');
        IterarCamposEdit($colss, function($td) {
            let cont = $td.html();
            let div = '<div style="display: none;">' + cont + '</div>';
            let input;
            if($td.attr('name') == 'isnumber'){
                //只能输入数字
                input = '<input class="form-control input-sm" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')"  value="' + cont + '">';
            }else if($td.attr('name') == 'istime'){
                //时间控件
                input = '<input class="form-control input-sm" type="text" onClick="WdatePicker({el:this,dateFmt:\'yyyy-MM-dd\'})" value="' + cont +'">';
            }else if($td.attr('name') == 'selectop'){
                //下拉框
                var infodata = {
                    userCode: userCode,
                    id: '-1'
                }
                myAjax(url + "api/waresManage/buysinfo",infodata);
                if(ajaxResult == '-999'){
                    window.wxc.xcConfirm("系统异常，请联系管理员");
                    return;
                }else{
                    var res = ajaxResult;
                    if(res.code == "0"){
                        var str = "<option value=''></option>";
                        for (var i=0; i<res.data.types.length; i++){
                            if(res.data.types[i].warescode == res.data.warescode){
                                str+="<option selected='' value='"+res.data.types[i].warescode+"'>"+ res.data.types[i].warescode + "-" + res.data.types[i].waresname+"</option>";
                            }else{
                                str+="<option value='"+res.data.types[i].warescode+"'>"+ res.data.types[i].warescode + "-" + res.data.types[i].waresname+"</option>";
                            }
                        }
                        input = '<select class="form-control input-sm">' + str + '</select>';
                    }else{
                        window.wxc.xcConfirm(res.message);
                        return;
                    }
                }
            }else{
                input = '<input class="form-control input-sm" value="' + cont + '">';
            }
            $td.html(div + input);
        });
        $ultFila.find('#bAcep').show();
        $ultFila.find('#bCanc').show();
        $ultFila.find('#bEdit').hide();
        $ultFila.find('#bElim').hide();
        $ultFila.attr('id', 'editing'); //设置编辑状态

    } else {
        let $ultFila = $tab_en_edic.find('tr:last');

        var fl = 0;
        let $cols2 = $ultFila.find('td');
        $cols2.each(function() {
            if ($(this).attr('name') == 'hideid') {
                fl = 1;
            }
        });
        if(fl == 0){
            window.wxc.xcConfirm("请先新增上一条数据!");
            return;
        }

        $ultFila.clone().appendTo($ultFila.parent());
        $ultFila = $tab_en_edic.find('tr:last');

        let $cols = $ultFila.find('td');
        $cols.each(function() {
            if ($(this).attr('name') == 'hideid') {
                $(this).attr('name', '');
            }
            if ($(this).attr('name')!='buttons') {
                $(this).html('');
            }
        });

        IterarCamposEdit($cols, function($td) {
            let cont = $td.html();
            let div = '<div style="display: none;">' + cont + '</div>';
            let input;
            if($td.attr('name') == 'isnumber'){
                //只能输入数字
                input = '<input class="form-control input-sm" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')"  value="' + cont + '">';
            }else if($td.attr('name') == 'istime'){
                //时间控件
                input = '<input class="form-control input-sm" type="text" onClick="WdatePicker({el:this,dateFmt:\'yyyy-MM-dd\'})" value="' + cont +'">';
            }else if($td.attr('name') == 'selectop'){
                //下拉框
                var infodata = {
                    userCode: userCode,
                    id: '-1'
                }
                myAjax(url + "api/waresManage/buysinfo",infodata);
                if(ajaxResult == '-999'){
                    window.wxc.xcConfirm("系统异常，请联系管理员");
                    return;
                }else{
                    var res = ajaxResult;
                    if(res.code == "0"){
                        var str = "<option value=''></option>";
                        for (var i=0; i<res.data.types.length; i++){
                            if(res.data.types[i].warescode == res.data.warescode){
                                str+="<option selected='' value='"+res.data.types[i].warescode+"'>"+ res.data.types[i].warescode + "-" + res.data.types[i].waresname+"</option>";
                            }else{
                                str+="<option value='"+res.data.types[i].warescode+"'>"+ res.data.types[i].warescode + "-" + res.data.types[i].waresname+"</option>";
                            }
                        }
                        input = '<select class="form-control input-sm">' + str + '</select>';
                    }else{
                        window.wxc.xcConfirm(res.message);
                        return;
                    }
                }
            }else{
                input = '<input class="form-control input-sm" value="' + cont + '">';
            }
            $td.html(div + input);
        });
        $ultFila.find('#bAcep').show();
        $ultFila.find('#bCanc').show();
        $ultFila.find('#bEdit').hide();
        $ultFila.find('#bElim').hide();
        $ultFila.attr('id', 'editing'); //设置编辑状态

    }
    //添加行后回调
    params.onAdd(tabId);
}