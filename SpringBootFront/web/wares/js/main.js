jQuery(document).ready(function($){
	//final width --> this is the quick view image slider width
	//maxQuickWidth --> this is the max-width of the quick-view panel
	var sliderFinalWidth = 400,
		maxQuickWidth = 900;

	//open the quick view panel
	$('.cd-trigger').on('click', function(event){
		var selectedImage = $(this).parent('.cd-item').children('img'),
			slectedImageUrl = selectedImage.attr('src');

		var warescode;

		var conent;

		if(slectedImageUrl == 'wares/img/p1.png'){
            conent = "(1)查看课程  (2)选课<br \\>(3)课程管理  (4)选课管理<br \\>(5)学院维护  (6)人员管理<br \\>(7)修改密码";
			$("#bh").text("编号：A1");
			$("#mc").text("名称：学生选课系统");
			$("#nr").html("(1)查看课程\n" +
                "(2)选课\n" +
                "(3)课程管理\n" +
                "(4)选课管理\n" +
                "(5)学院维护\n" +
                "(6)人员管理\n" +
                "(7)修改密码");
            warescode = 'A1';
		}else if(slectedImageUrl == 'wares/img/p2.png'){
            conent = "(1)快递入库  (2)快递信息维护<br \\>(3)快递查询  (4)待出库快递<br \\>(5)出入库记录管理  (6)用户管理<br \\>(7)修改密码";
			$("#bh").text("编号：A2");
			$("#mc").text("名称：快递收发系统");
			$("#nr").html("(1)快递入库\n" +
                "(2)快递信息维护\n" +
                "(3)快递查询\n" +
                "(4)待出库快递\n" +
                "(5)出入库记录管理\n" +
                "(6)用户管理\n" +
                "(7)修改密码");
            warescode = 'A2';
		}else if(slectedImageUrl == 'wares/img/p3.png'){
            conent = "<br \\>";
			$("#bh").text("编号：A3");
			$("#mc").text("名称：酒店管理系统");
			$("#nr").text("(1)客房类型管理\n" +
                "(2)客房信息维护\n" +
                "(3)客房预订\n" +
                "(4)入住登记\n" +
                "(5)退房登记\n" +
                "(6)客房查询\n" +
                "(7)用户管理\n" +
                "(8)修改密码");
            warescode = 'A3';
		}else if(slectedImageUrl == 'wares/img/p4.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A4");
            $("#mc").text("名称：二手车交易管理系统");
            $("#nr").text("(1)车辆类别管理\n" +
                "(2)车辆信息维护\n" +
                "(3)收车管理\n" +
                "(4)车辆预订\n" +
                "(5)卖出管理\n" +
                "(6)车辆查询\n" +
                "(7)用户管理\n" +
                "(8)修改密码");
            warescode = 'A4';
        }else if(slectedImageUrl == 'wares/img/p5.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A5");
            $("#mc").text("名称：人力资源管理系统");
            $("#nr").text("(1)部门维护\n" +
                "(2)员工管理\n" +
                "(3)薪资管理\n" +
                "(4)薪资发放\n" +
                "(5)招聘管理\n" +
                "(6)培训管理\n" +
                "(7)修改密码");
            warescode = 'A5';
        }else if(slectedImageUrl == 'wares/img/p6.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A6");
            $("#mc").text("名称：疫情信息管理系统");
            $("#nr").text("(1)确诊患者管理\n" +
                "(2)密切接触者管理\n" +
                "(3)死亡者信息\n" +
                "(4)治愈者信息\n" +
                "(5)黑名单\n" +
                "(6)用户管理\n" +
                "(7)修改密码");
            warescode = 'A6';
        }else if(slectedImageUrl == 'wares/img/p7.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A7");
            $("#mc").text("名称：共享单车管理系统");
            $("#nr").text("(1)单车信息维护\n" +
                "(2)服务点管理\n" +
                "(3)租赁信息\n" +
                "(4)还车记录\n" +
                "(5)单车查询\n" +
                "(6)用户管理\n" +
                "(7)修改密码");
            warescode = 'A7';
        }else if(slectedImageUrl == 'wares/img/p8.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A8");
            $("#mc").text("名称：药店管理系统");
            $("#nr").text("(1)药品入库\n" +
                "(2)药品维护\n" +
                "(3)销售管理\n" +
                "(4)退单管理\n" +
                "(5)销售记录\n" +
                "(6)积分兑换管理\n" +
                "(7)用户管理\n" +
                "(8)修改密码");
            warescode = 'A8';
        }else if(slectedImageUrl == 'wares/img/p9.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A9");
            $("#mc").text("名称：堂食点餐管理系统");
            $("#nr").text("(1)餐桌管理\n" +
                "(2)菜品列表\n" +
                "(3)开始点单\n" +
                "(4)退单管理\n" +
                "(5)订单详情\n" +
                "(6)公告管理\n" +
                "(7)用户管理\n" +
                "(8)修改密码");
            warescode = 'A9';
        }else if(slectedImageUrl == 'wares/img/p10.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A10");
            $("#mc").text("名称：医院挂号管理系统");
            $("#nr").text("(1)科室管理\n" +
                "(2)就诊预约\n" +
                "(3)我的预约\n" +
                "(4)医生信息\n" +
                "(5)评价\n" +
                "(6)预约信息管理\n" +
                "(7)用户管理\n" +
                "(8)修改密码");
            warescode = 'A10';
        }else if(slectedImageUrl == 'wares/img/p11.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A11");
            $("#mc").text("名称：房屋租赁管理系统");
            $("#nr").text("(1)房源管理\n" +
                "(2)租赁管理\n" +
                "(3)看房申请\n" +
                "(4)故障处理\n" +
                "(5)退房管理\n" +
                "(6)房源查询\n" +
                "(7)用户管理\n" +
                "(8)修改密码");
            warescode = 'A11';
        }else if(slectedImageUrl == 'wares/img/p12.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A12");
            $("#mc").text("名称：城市公交管理系统");
            $("#nr").text("(1)公交线路管理\n" +
                "(2)公交车管理\n" +
                "(3)公交动态管理\n" +
                "(4)信息发布管理\n" +
                "(5)查询线路\n" +
                "(6)用户管理\n" +
                "(7)修改密码");
            warescode = 'A12';
        }else if(slectedImageUrl == 'wares/img/p13.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A13");
            $("#mc").text("名称：洗衣店管理系统");
            $("#nr").text("(1)洗衣类型管理\n" +
                "(2)收取衣物管理\n" +
                "(3)领取衣服管理\n" +
                "(4)衣物查询\n" +
                "(5)公告管理\n" +
                "(6)用户管理\n" +
                "(7)修改密码");
            warescode = 'A13';
        }else if(slectedImageUrl == 'wares/img/p14.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A14");
            $("#mc").text("名称：旅游管理系统");
            $("#nr").text("(1)产品管理\n" +
                "(2)订单管理\n" +
                "(3)退单管理\n" +
                "(4)订单查询\n" +
                "(5)公告管理\n" +
                "(6)用户管理\n" +
                "(7)修改密码");
            warescode = 'A14';
        }else if(slectedImageUrl == 'wares/img/p15.png'){
            conent = "<br \\>";
            $("#bh").text("编号：A15");
            $("#mc").text("名称：花店销售系统");
            $("#nr").text("(1)商品类型管理\n" +
                "(2)商品管理\n" +
                "(3)订单管理\n" +
                "(4)退单管理\n" +
                "(5)公告管理\n" +
                "(6)用户管理\n" +
                "(7)修改密码");
            warescode = 'A15';
        }

        var data = {
            warescode: warescode
        };

        myAjax(url + "login/browseWares",data);
        if(ajaxResult == '-999'){
            window.wxc.xcConfirm("系统异常，请联系管理员");
        }else{
        	var res = ajaxResult;
            $('body').addClass('overlay-layer');
            animateQuickView(selectedImage, sliderFinalWidth, maxQuickWidth, 'open');

            //update the visible slider image in the quick view panel
            //you don't need to implement/use the updateQuickView if retrieving the quick view data with ajax
            updateQuickView(slectedImageUrl);
		}

	});

	//close the quick view panel
	$('body').on('click', function(event){
		if( $(event.target).is('.cd-close') || $(event.target).is('body.overlay-layer')) {
			closeQuickView( sliderFinalWidth, maxQuickWidth);
		}
	});
	$(document).keyup(function(event){
		//check if user has pressed 'Esc'
    	if(event.which=='27'){
			closeQuickView( sliderFinalWidth, maxQuickWidth);
		}
	});

	//quick view slider implementation
	$('.cd-quick-view').on('click', '.cd-slider-navigation a', function(){
		updateSlider($(this));
	});

	//center quick-view on window resize
	$(window).on('resize', function(){
		if($('.cd-quick-view').hasClass('is-visible')){
			window.requestAnimationFrame(resizeQuickView);
		}
	});

	function updateSlider(navigation) {
		var sliderConatiner = navigation.parents('.cd-slider-wrapper').find('.cd-slider'),
			activeSlider = sliderConatiner.children('.selected').removeClass('selected');
		if ( navigation.hasClass('cd-next') ) {
			( !activeSlider.is(':last-child') ) ? activeSlider.next().addClass('selected') : sliderConatiner.children('li').eq(0).addClass('selected'); 
		} else {
			( !activeSlider.is(':first-child') ) ? activeSlider.prev().addClass('selected') : sliderConatiner.children('li').last().addClass('selected');
		} 
	}

	function updateQuickView(url) {
		$('.cd-quick-view .cd-slider li').removeClass('selected').find('img[src="'+ url +'"]').parent('li').addClass('selected');
	}

	function resizeQuickView() {
		var quickViewLeft = ($(window).width() - $('.cd-quick-view').width())/2,
			quickViewTop = ($(window).height() - $('.cd-quick-view').height())/2;
		$('.cd-quick-view').css({
		    "top": quickViewTop,
		    "left": quickViewLeft,
		});
	} 

	function closeQuickView(finalWidth, maxQuickWidth) {
		var close = $('.cd-close'),
			activeSliderUrl = close.siblings('.cd-slider-wrapper').find('.selected img').attr('src'),
			selectedImage = $('.empty-box').find('img');
		//update the image in the gallery
		if( !$('.cd-quick-view').hasClass('velocity-animating') && $('.cd-quick-view').hasClass('add-content')) {
			selectedImage.attr('src', activeSliderUrl);
			animateQuickView(selectedImage, finalWidth, maxQuickWidth, 'close');
		} else {
			closeNoAnimation(selectedImage, finalWidth, maxQuickWidth);
		}
	}

	function animateQuickView(image, finalWidth, maxQuickWidth, animationType) {
		//store some image data (width, top position, ...)
		//store window data to calculate quick view panel position
		var parentListItem = image.parent('.cd-item'),
			topSelected = image.offset().top - $(window).scrollTop(),
			leftSelected = image.offset().left,
			widthSelected = image.width(),
			heightSelected = image.height(),
			windowWidth = $(window).width(),
			windowHeight = $(window).height(),
			finalLeft = (windowWidth - finalWidth)/2,
			finalHeight = finalWidth * heightSelected/widthSelected,
			finalTop = (windowHeight - finalHeight)/2,
			quickViewWidth = ( windowWidth * .8 < maxQuickWidth ) ? windowWidth * .8 : maxQuickWidth ,
			quickViewLeft = (windowWidth - quickViewWidth)/2;

		if( animationType == 'open') {
			//hide the image in the gallery
			parentListItem.addClass('empty-box');
			//place the quick view over the image gallery and give it the dimension of the gallery image
			$('.cd-quick-view').css({
			    "top": topSelected,
			    "left": leftSelected,
			    "width": widthSelected,
			}).velocity({
				//animate the quick view: animate its width and center it in the viewport
				//during this animation, only the slider image is visible
			    'top': finalTop+ 'px',
			    'left': finalLeft+'px',
			    'width': finalWidth+'px',
			}, 1000, [ 400, 20 ], function(){
				//animate the quick view: animate its width to the final value
				$('.cd-quick-view').addClass('animate-width').velocity({
					'left': quickViewLeft+'px',
			    	'width': quickViewWidth+'px',
				}, 300, 'ease' ,function(){
					//show quick view content
					$('.cd-quick-view').addClass('add-content');
				});
			}).addClass('is-visible');
		} else {
			//close the quick view reverting the animation
			$('.cd-quick-view').removeClass('add-content').velocity({
			    'top': finalTop+ 'px',
			    'left': finalLeft+'px',
			    'width': finalWidth+'px',
			}, 300, 'ease', function(){
				$('body').removeClass('overlay-layer');
				$('.cd-quick-view').removeClass('animate-width').velocity({
					"top": topSelected,
				    "left": leftSelected,
				    "width": widthSelected,
				}, 500, 'ease', function(){
					$('.cd-quick-view').removeClass('is-visible');
					parentListItem.removeClass('empty-box');
				});
			});
		}
	}
	function closeNoAnimation(image, finalWidth, maxQuickWidth) {
		var parentListItem = image.parent('.cd-item'),
			topSelected = image.offset().top - $(window).scrollTop(),
			leftSelected = image.offset().left,
			widthSelected = image.width();

		//close the quick view reverting the animation
		$('body').removeClass('overlay-layer');
		parentListItem.removeClass('empty-box');
		$('.cd-quick-view').velocity("stop").removeClass('add-content animate-width is-visible').css({
			"top": topSelected,
		    "left": leftSelected,
		    "width": widthSelected,
		});
	}
});