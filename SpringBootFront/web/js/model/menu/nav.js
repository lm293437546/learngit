
var firstdata = [];

var data = {
    userCode: userCode
};

myAjax(url + "api/menu/getMenus",data);
if(ajaxResult == '-999'){
    window.wxc.xcConfirm("系统异常，请联系管理员");
}else{
    if(ajaxResult.code == "0"){
        for(var i=0;i<ajaxResult.data[0].children.length;i++){
            if(ajaxResult.data[0].children[i].addr == null){
                var senddata = [];
                var si = ajaxResult.data[0].children[i].children.length;
                var silist = ajaxResult.data[0].children[i].children;
                for(var j=0;j<si;j++){
                    senddata.push({"title":silist[j].menuname,"icon": silist[j].icon,"href":silist[j].addr + "?userCode=" + userCode});
                }
                firstdata.push({"title": ajaxResult.data[0].children[i].menuname,"icon": ajaxResult.data[0].children[i].icon,"spread": false,"children":senddata});
            }else{
                firstdata.push({"title":ajaxResult.data[0].children[i].menuname,"icon": ajaxResult.data[0].children[i].icon,"href":ajaxResult.data[0].children[i].addr + "?userCode=" + userCode});
            }
        }
    }else{
        window.wxc.xcConfirm(res.message);
    }
}

var navs = firstdata;