$(function() {
	var loadPointsRecordListUri = "loadpointsrecordlist";
	var pageNo = 1, pageSize = 5;
	var loading = false;
	var $infiniteScroll = $('.infinite-scroll-bottom');
	var $infiniteScrollPreloader = $('.infinite-scroll-preloader');
	var $cardsContainer = $("#list-container");
	var infiniteScrollEnabled = true;
	var $searchIpt = $("#search");
	//初始化页面组件
	init();
	constructBottomToolbar();
	constructRightPanel();
	
	function init() {
		$.init();
		//1、注册'infinite'事件处理函数
		$infiniteScroll.on('infinite', function() {
			console.log("滚动触发请求数据...");
			if(infiniteScrollEnabled) {
				loadPointsRecordList();
			}
		});
		//2、加载积分记录列表
		loadPointsRecordList();
		//3、搜索栏 - 点击软键盘的“搜索”按钮
		var t;
		$searchIpt.on("keyup", function(e) {
			console.log("e.keyCode", e.keyCode);
			clearTimeout(t);
			if(e.keyCode == '13') {
				e.preventDefault();
				loadPointsRecordListFirstPage();
				return;
			}
			t = setTimeout(function() {
				loadPointsRecordListFirstPage();
			}, 2000);
		});
		//4、搜索栏 - 点击右侧的“取消”按钮
		$(".searchbar > a.searchbar-cancel").on("click", function() {
			$searchIpt.val("");
			loadPointsRecordListFirstPage();
		});
	}
	
	function loadPointsRecordListFirstPage() {
		if(loading) {
			return;
		}
		if(!infiniteScrollEnabled) {
			infiniteScrollEnabled = true;
			$infiniteScrollPreloader.show();
		}
		$cardsContainer.empty();
		pageNo = 1;
		loadPointsRecordList();
	}
	
	function loadPointsRecordList() {
		if(loading) {
			return;
		}
		loading = true;
		var params = generateQueryParamsObj();
		console.log("queryParamsObj", params);
		$.ajax({
			url: loadPointsRecordListUri,
			type: "GET",
			data: params,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("loadPointsRecordList - returned data", data);
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
				var cardsTotal = $('#list-container > div.card').length;
				if(cardsTotal === 0) {
					$.alert("未查找到任何积分记录 ~");
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
			pageSize: pageSize
		};
		//关键字
		var searchKey = $searchIpt.val();
		if(searchKey) {
			params.searchKey = searchKey;
		}
		return params;
	}
	
	function generateCardHtml(row) {
		return '<div class="card">'
			+ '    <div class="card-content">'
			+ '        <div class="card-content-inner">'
			+ '            <p class="clearfix item-title"><span class="pull-left">' + row.shop_name + '</span>'
			+ '            <span class="pull-right">' + row.total_points + ' 积分</span></p>'
			+ '        </div>'
			+ '    </div>'
			+ '    <div class="card-footer">'
			+ '        <span class="color-gray">更新时间&nbsp;&nbsp;' + new Date(row.last_update_time).format("yyyy-MM-dd HH:mm") + '</span>'
			+ '        <a href="#" class="color-gray">详情</a>'
			+ '    </div>'
			+ '</div>';
	}
});