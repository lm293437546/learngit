window.onload=function(){
    doLoad();
}

// the data table itself
var dataTable = null;

jQuery(document).ready(function () {

    dataTable = jQuery("#dataTable").raytable({
        datasource: { data: [], keyfield: 'id' },
        columns: [
            { field: "menuName", title: "菜单名称" },
            { field: "addr", title: "地址"},
            { field: "createTime", title: "创建时间" },
            { field: "cz", title: "操作" },
        ],
        pagesize: 1000,
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
    myAjax(url + "api/menuManage/selectMenuList",datalist);
    if(ajaxResult == '-999'){
        window.wxc.xcConfirm("系统异常，请联系管理员");
    }else{
        var res = ajaxResult;
        if(res.code == "0"){
            //第一级
            var firstmenu = res.data;
            for (var i=0; i< firstmenu.length; i++){
                myData.push({"menuName":firstmenu[i].menuname,"addr":firstmenu[i].addr,"createTime":firstmenu[i].createtime});
                //第二级
                var secondmenu = firstmenu[i].children;
                for (var j=0; j<secondmenu.length; j++){
                    myData.push({"menuName":"<img src=\"../../../images/level.gif\" />"+secondmenu[j].menuname,"addr":secondmenu[j].addr,"createTime":secondmenu[j].createtime,"cz":"<a href='javascript:edit(\""
                            + secondmenu[j].menuid + "\");'>编辑</a> | <a href='javascript:deleteMenu(\""
                            + secondmenu[j].menuid + "\");'>删除</a>"});
                    //第三级
                    var thirdmenu = secondmenu[j].children;
                    for (var k=0; k<thirdmenu.length; k++){
                        myData.push({"menuName":"<img src=\"../../../images/level1.gif\" />"+thirdmenu[k].menuname,"addr":thirdmenu[k].addr,"createTime":thirdmenu[k].createtime,"cz":"<a href='javascript:edit(\""
                                + thirdmenu[k].menuid + "\");'>编辑</a> | <a href='javascript:deleteMenu(\""
                                + thirdmenu[k].menuid + "\");'>删除</a>"});
                    }
                }
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

function edit(id){
    window.location.href = "menuForm.jsp?userCode=" + userCode + "&id=" + id;
}

function deleteMenu(id){

    var deleData = {
        userCode: userCode,
        menuId: id
    }

    var popup = new Popup({
        'type': 'submit',
        'title': '',
        'text': '确定要删除该菜单吗',
        'cancelbutton': true,
        'closeCallBack': function(){
            //alert('点击了关闭')
        },
        'submitCallBack': function(){
            //alert('点击了确定');
            myAjax(url + "api/menuManage/deleteMenu",deleData);
            if(ajaxResult == '-999'){
                window.wxc.xcConfirm("系统异常，请联系管理员");
            }else{
                var res = ajaxResult;
                if(res.code == "0"){
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    doLoad();
                }else{
                    window.wxc.xcConfirm(res.message);
                }
            }
        }
    })
}