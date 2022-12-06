layui.config({
	base: 'js/'
}).use(['element', 'layer', 'navbar', 'tab'], function() {
	var element = layui.element(),
		$ = layui.jquery,
		layer = layui.layer,
		navbar = layui.navbar(),
		tab = layui.tab({
			elem: '.admin-nav-card' //设置选项卡容器
		});
	//iframe自适应
	$(window).on('resize', function() {
		var $content = $('.admin-nav-card .layui-tab-content');
		$content.height($(this).height() - 147);
		$content.find('iframe').each(function() {
			$(this).height($content.height());
		});
	}).resize();

	//设置navbar
	navbar.set({
		spreadOne: true,
		elem: '#admin-navbar-side',
		cached: true,
		data: navs
			/*cached:true,
			url: 'datas/nav.json'*/
	});
	//渲染navbar
	navbar.render();
	//监听点击事件
	navbar.on('click(side)', function(data) {
		tab.tabAdd(data.field);
	});

	$('.admin-side-toggle').on('click', function() {
		var sideWidth = $('#admin-side').width();
		if(sideWidth === 200) {
			$('#admin-body').animate({
				left: '0'
			}); //admin-footer
			$('#admin-footer').animate({
				left: '0'
			});
			$('#admin-side').animate({
				width: '0'
			});
		} else {
			$('#admin-body').animate({
				left: '200px'
			});
			$('#admin-footer').animate({
				left: '200px'
			});
			$('#admin-side').animate({
				width: '200px'
			});
		}
	});

	//锁屏
	$(document).on('keydown', function() {
		var e = window.event;
		if(e.keyCode === 76 && e.altKey) {
			//alert("你按下了alt+l");
			lock($, layer);
		}
	});
	$('#lock').on('click', function() {
		lock($, layer);
	});

	//手机设备的简单适配
	var treeMobile = $('.site-tree-mobile'),
		shadeMobile = $('.site-mobile-shade');
	treeMobile.on('click', function() {
		$('body').addClass('site-mobile');
	});
	shadeMobile.on('click', function() {
		$('body').removeClass('site-mobile');
	});
});

function lock($, layer) {
	//自定页
	layer.open({
		title: false,
		type: 1,
		closeBtn: 0,
		anim: 6,
		content: $('#lock-temp').html(),
		shade: [0.9, '#393D49'],
		success: function(layero, lockIndex) {
			//给显示用户名赋值
			layero.find('div#lockUserName').val();
			layero.find('input[name=lockPwd]').on('focus', function() {
					var $this = $(this);
					if($this.val() === '输入密码解锁..') {
						$this.val('').attr('type', 'password');
					}
				})
				.on('blur', function() {
					var $this = $(this);
					if($this.val() === '' || $this.length === 0) {
						$this.attr('type', 'text').val('输入密码解锁..');
					}
				});
			//在此处可以写一个请求到服务端删除相关身份认证，因为考虑到如果浏览器被强制刷新的时候，身份验证还存在的情况			
			//do something...
			//e.g. 
			$.ajax({
				url:'MenuAction_back.jspx',
				type: 'post', dataType:'json', cache: false, async:false,
			});
			
			//绑定解锁按钮的点击事件
			layero.find('button#unlock').on('click', function() {
				var $lockBox = $('div#lock-box');

				var userName = $lockBox.find('div#lockUserName').text();
				var pwd = $lockBox.find('input[name=lockPwd]').val();
				if(pwd === '输入密码解锁..' || pwd.length === 0) {
					layer.msg('请输入密码..', {
						icon: 2,
						time: 1000
					});
					return;
				}
				unlock(userName, pwd);
			});
			/**
			 * 解锁操作方法
			 * @param {String} 用户名
			 * @param {String} 密码
			 */
			var unlock = function(un, pwd) {
				//这里可以使用ajax方法解锁
				$.ajax({
					url:'LoginAction_toLogin.jspx',
					type: 'post', dataType:'json', cache: false, async:false,
					data: {"username":un, "password":pwd},
					success: function(result){
						if(result["flag"] == '0'){
							//关闭锁屏层
							layer.close(lockIndex);
						}else if(result["flag"] == '1'){
							layer.msg('密码输入错误,请重新输入',{icon:2,time:1000});
						}
					}
				});

				//演示：默认输入密码都算成功
				//关闭锁屏层
				//layer.close(lockIndex);
			};
		}
	});
};

var data = {
    userCode: userCode
};

layui.use('layer', function() {
    var $ = layui.jquery,
        layer = layui.layer;

    $('#changePwd').on('click', function() {
        layer.open({
            title : '修改密码',
            maxmin : true,
            type : 2,
            content : '../../../ModulPage/changePwd/ChangePwd.jsp?userCode='+userCode,
            area : [ '500px', '300px' ],
            cancel: function () {
            	//右上角关闭回调
                myAjax(url + "api/menu/getUserName",data);
                if(ajaxResult.code == "0"){
                    if(ajaxResult.data.ischange == 1){
                        return true;
                    }
                }
                layer.alert('请修改初始密码!', {
                    title: '验证'
                })
                return false;

            }
        });
    });
    $('#cancellation').on('click', function() {
        myAjax(url + "api/menu/cancellation",data);
        if(ajaxResult == '-999'){
            window.wxc.xcConfirm("系统异常，请联系管理员");
        }else{
        	var res = ajaxResult;
            if(res.code == "0"){
                // 页面跳转
                window.location.replace("../../../xws.jsp");
            }else{
                window.wxc.xcConfirm(res.message);
            }
		}
    });

});

//当前时间
function clock_12h() {
    var today = new Date(); //获得当前时间
    //获得年、月、日，Date()函数中的月份是从0－11计算
    var year = today.getFullYear();
    var month = today.getMonth() + 1;
    var date = today.getDate();
    var hour = today.getHours(); //获得小时、分钟、秒
    var minute = today.getMinutes();
    var second = today.getSeconds();

    var apm = "AM"; //默认显示上午: AM
    if (hour > 12) //按12小时制显示
    {
        hour = hour - 12;
        apm = "PM"  ;
    }
    if(minute < 10){
        minute = "0" + minute
    }
    if(second < 10){
        second = "0" + second
    }
    var weekday = 0;
    switch (today.getDay()) {
        case 0:
            weekday = "星期日";
            break;
        case 1:
            weekday = "星期一";
            break;
        case 2:
            weekday = "星期二";
            break;
        case 3:
            weekday = "星期三";
            break;
        case 4:
            weekday = "星期四";
            break;
        case 5:
            weekday = "星期五";
            break;
        case 6:
            weekday = "星期六";
            break;
    }
    /*设置div的内容为当前时间*/
    document.getElementById("myclock").innerHTML = year + "年" + month + "月" + date + "日&nbsp;" + hour + ":" + minute + ":" + second + "&nbsp;" + apm + "&nbsp;" + weekday;
}
/*使用setInterval()每间隔指定毫秒后调用clock_12h()*/
var myTime = setInterval("clock_12h()",1000);