$(function() {
	var shopId = getQueryStrValByName("shopId");
	var loadPointsDetailListUri = "loadpointsdetaillist";
	var loadPointsDetailInitDataUri = "loadpointsdetailinitdata";
	var pageNo = 1, pageSize = 10;
	var loading = false;
	var $infiniteScroll = $('.infinite-scroll-bottom');
	var $infiniteScrollPreloader = $('.infinite-scroll-preloader');
	var $cardsContainer = $(".card-content ul");
	var $contentPadded = $(".content-padded");
	var infiniteScrollEnabled = true;
	var $searchIpt = $("#search");
	var $beginDateIpt = $("#beginDate");
	var $endDateIpt = $("#endDate");
	var ctxPath;
	var pointsShowType = 0;
	//初始化页面组件
	init();
	constructBottomToolbar();
	constructRightPanel();
	
	function init() {
		$.init();
		$.getJSON(loadPointsDetailInitDataUri, function(data) {
			console.log("loadPointsDetailInitData - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				return;
			}
			ctxPath = data.entity.resourcesServerContextPath;
		});
		//1、注册'infinite'事件处理函数
		$infiniteScroll.on('infinite', function() {
			console.log("滚动触发请求数据...");
			if(infiniteScrollEnabled) {
				loadPointsDetailList();
			}
		});
		//2、加载积分详情列表
		loadPointsDetailList();
		//3、搜索栏 - 点击软键盘的“搜索”按钮
		var t;
		$searchIpt.on("keyup", function(e) {
			console.log("e.keyCode", e.keyCode);
			clearTimeout(t);
			if(e.keyCode == '13') {
				e.preventDefault();
				loadPointsDetailListFirstPage();
				return;
			}
			t = setTimeout(function() {
				loadPointsDetailListFirstPage();
			}, 2000);
		});
		//4、搜索栏 - 点击右侧的“取消”按钮
		$(".searchbar > a.searchbar-cancel").on("click", function() {
			$searchIpt.val("");
			loadPointsDetailListFirstPage();
		});
		//5、积分获取、使用筛选按钮（积分展示类型）
		$contentPadded.on("click", "a.button", function() {
			$contentPadded.find("a.button-fill").removeClass("button-fill");
			pointsShowType = $(this).addClass("button-fill").data("pointsShowType");
			loadPointsDetailListFirstPage();
		});
		//6、起止日期控件
		$beginDateIpt.on("change", function() {
			loadPointsDetailListFirstPage();
		});
		$endDateIpt.on("change", function() {
			loadPointsDetailListFirstPage();
		});
	}
	
	function loadPointsDetailListFirstPage() {
		if(loading) {
			return;
		}
		if(!infiniteScrollEnabled) {
			infiniteScrollEnabled = true;
			$infiniteScrollPreloader.show();
		}
		$cardsContainer.empty();
		pageNo = 1;
		loadPointsDetailList();
	}
	
	function loadPointsDetailList() {
		if(loading) {
			return;
		}
		loading = true;
		var params = generateQueryParamsObj();
		console.log("queryParamsObj", params);
		$.ajax({
			url: loadPointsDetailListUri,
			type: "GET",
			data: params,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("loadPointsDetailList - returned data", data);
				loading = false;
				if(data.state < 0) { //请求失败
					$.toast(data.msg);
					return;
				}
				//积分记录列表
				var rows = data.rows;
				var cardsHtml = '';
				if($.isArray(rows)) {
					rows.forEach(function(row, index) {
						cardsHtml += generateCardHtml(row);
					});
					$cardsContainer.append(cardsHtml);
				}
				var cardsTotal = $cardsContainer.children().length;
				if(cardsTotal === 0) {
					$.alert("未查找到任何积分详情 ~");
				}
				if(cardsTotal >= data.total) {
	            	//所有数据加载完毕，则注销无限加载事件，以防不必要的加载
	            	//$.detachInfiniteScroll($('.infinite-scroll-bottom'));
					infiniteScrollEnabled = false;
	            	//删除加载提示符
	            	$infiniteScrollPreloader.hide();
	            	return;
	            }
				$.refreshScroller();
				pageNo++;
			},
			error: function (xhr, textStatus, errorThrown) {
				printErr(xhr, textStatus, errorThrown);
				loading = false;
			    var result = getParsedResultFromXhr(xhr);
			    $.toast(result ? result.msg : "服务器出错~");
			}
		});
	}
	
	function generateQueryParamsObj() {
		var params = {
			pageNo: pageNo,
			pageSize: pageSize,
			shopId: shopId
		};
		//关键字
		var searchKey = $searchIpt.val();
		if(searchKey) {
			params.searchKey = searchKey;
		}
		//积分展示类型
		params.pointsShowType = pointsShowType;
		//时间段
		params.beginDate = $beginDateIpt.val();
		params.endDate = $endDateIpt.val();
		return params;
	}
	
	function generateCardHtml(row) {
		return '<li class="item-content">'
			+ '  <div class="item-media">'
			+ '    <img src="' + (row.img_addr ? ctxPath + '/' + row.img_addr : '../resources/image/noimage.ico') + '" width="44">'
			+ '  </div>'
			+ '  <div class="item-inner">'
			+ '    <div class="item-title-row">'
			+ '      <div class="item-title">' + (row.oper_type === 1 ? '购买 ' : '兑换 ') + row.product_name + '</div>'
			+ '      <div class="item-title pull-right ' + (row.oper_type === 1 ? 'color-in' : 'color-gray') + '">' + (row.oper_type === 1 ? '+ ' : '- ') + row.points + '</div>'
			+ '    </div>'
			+ '    <div class="item-subtitle">' + new Date(row.create_time).format("yyyy-MM-dd HH:mm") + '</div>'
			+ '  </div>'
			+ '</li>';
	}
});