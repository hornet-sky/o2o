$(function() {
	var loadPointsExpenditureListUri = "loadpointsexpenditurelist";
	var loadPointsExpenditureInitDataUri = "loadpointsexpenditureinitdata";
	var pageNo = 1, pageSize = 10;
	var loading = false;
	var $infiniteScroll = $('.infinite-scroll-bottom');
	var $infiniteScrollPreloader = $('.infinite-scroll-preloader');
	var $cardsContainer = $(".list-container");
	var infiniteScrollEnabled = true;
	var $searchIpt = $("#search");
	var ctxPath;
	//初始化页面组件
	init();
	constructBottomToolbar();
	constructRightPanel();
	
	function init() {
		$.init();
		$.getJSON(loadPointsExpenditureInitDataUri, function(data) {
			console.log("loadPointsExpenditureInitData - returned data", data);
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
		//2、加载兑换记录列表
		loadPointsExpenditureList();
		//3、搜索栏 - 点击软键盘的“搜索”按钮
		var t;
		$searchIpt.on("keyup", function(e) {
			console.log("e.keyCode", e.keyCode);
			clearTimeout(t);
			if(e.keyCode == '13') {
				e.preventDefault();
				loadPointsExpenditureListFirstPage();
				return;
			}
			t = setTimeout(function() {
				loadPointsExpenditureListFirstPage();
			}, 2000);
		});
		//4、搜索栏 - 点击右侧的“取消”按钮
		$(".searchbar > a.searchbar-cancel").on("click", function() {
			$searchIpt.val("");
			loadPointsExpenditureListFirstPage();
		});
	}
	
	function loadPointsExpenditureListFirstPage() {
		if(loading) {
			return;
		}
		if(!infiniteScrollEnabled) {
			infiniteScrollEnabled = true;
			$infiniteScrollPreloader.show();
		}
		$cardsContainer.empty();
		pageNo = 1;
		loadPointsExpenditureList();
	}
	
	function loadPointsExpenditureList() {
		if(loading) {
			return;
		}
		loading = true;
		var params = generateQueryParamsObj();
		console.log("queryParamsObj", params);
		$.ajax({
			url: loadPointsExpenditureListUri,
			type: "GET",
			data: params,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("loadPointsExpenditureList - returned data", data);
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
					$.alert("未查找到任何兑换记录 ~");
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
			+ '      <div class="list-block media-list">'
			+ '        <ul>'
			+ '          <li class="item-content">'
			+ '            <div class="item-media">'
			+ '              <img src="' + (row.award_img ? ctxPath + '/' + row.award_img : '../resources/image/noimage.ico') + '" width="44">'
			+ '            </div>'
			+ '            <div class="item-inner">'
			+ '              <div class="item-title-row">'
			+ '                <div class="item-title">' + row.award_name + '</div>'
			+ '              </div>'
			+ '              <div class="item-subtitle color-gray">' + row.shop_name + '</div>'
			+ '            </div>'
			+ '          </li>'
			+ '        </ul>'
			+ '      </div>'
			+ '    </div>'
			+ '    <div class="card-footer">'
			+ '      <span>' + new Date(row.create_time).format("yyyy-MM-dd HH:mm") + '</span>'
			+ '      <span>消耗 ' + row.points + ' 积分</span>'
			+ '    </div>'
			+ '</div>';
	}
});