$(function() {
	var shopId = getQueryStrValByName("shopId");
	var loadAwardListUri = "loadawardlist";
	var pageNo = 1, pageSize = 5;
	var loading = false;
	var $infiniteScroll = $('.infinite-scroll-bottom');
	var $infiniteScrollPreloader = $('.infinite-scroll-preloader');
	var $cardsContainer = $("#list-container");
	var infiniteScrollEnabled = true;
	var ctxPath;
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
				loadAwardList();
			}
		});
		//2、加载奖品列表
		loadAwardList();
		//3、搜索栏 - 点击软键盘的“搜索”按钮
		var t;
		$searchIpt.on("keyup", function(e) {
			console.log("e.keyCode", e.keyCode);
			clearTimeout(t);
			if(e.keyCode == '13') {
				e.preventDefault();
				loadAwardListFirstPage();
				return;
			}
			t = setTimeout(function() {
				loadAwardListFirstPage();
			}, 2000);
		});
		//4、搜索栏 - 点击右侧的“取消”按钮
		$(".searchbar > a.searchbar-cancel").on("click", function() {
			$searchIpt.val("");
			loadAwardListFirstPage();
		});
	}
	
	function loadAwardListFirstPage() {
		if(loading) {
			return;
		}
		if(!infiniteScrollEnabled) {
			infiniteScrollEnabled = true;
			$infiniteScrollPreloader.show();
		}
		$cardsContainer.empty();
		pageNo = 1;
		loadAwardList();
	}
	
	function loadAwardList() {
		if(loading) {
			return;
		}
		loading = true;
		var params = generateQueryParamsObj();
		console.log("queryParamsObj", params);
		$.ajax({
			url: loadAwardListUri,
			type: "GET",
			data: params,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("loadAwardList - returned data", data);
				loading = false;
				if(data.state < 0) { //请求失败
					$.toast(data.msg);
					return;
				}
				ctxPath = data.entity.resourcesServerContextPath;
				//奖品列表
				var awardList = data.entity.pagingResult.rows;
				var cardsHtml = '';
				if($.isArray(awardList)) {
					awardList.forEach(function(award, index) {
						cardsHtml += generateCardHtml(award);
					});
					$cardsContainer.append(cardsHtml);
				}
				var cardsTotal = $('#list-container > div.card').length;
				if(cardsTotal === 0) {
					$.alert("该店铺未添加任何奖品 ~");
				}
				if(cardsTotal >= data.entity.pagingResult.total) {
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
			shopId: shopId,
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
		return '<div class="card demo-card-header-pic" data-award-id="' + row.awardId + '">'
			+ '    <div valign="bottom" class="card-header color-white no-border no-padding">'
			+ '        <img class="card-cover" src="' + (row.awardImg ? ctxPath + '/' + row.awardImg : '../resources/image/noimage.ico') + '" alt="' + row.awardName + '">'
			+ '    </div>'
			+ '    <div class="card-content">'
			+ '        <div class="card-content-inner">'
			+ '            <p class="clearfix"><span class="color-gray pull-left">' + (row.lastEditTime ? new Date(row.lastEditTime).format('yyyy/MM/dd') + ' 更新' : new Date(row.createTime).format('yyyy/MM/dd') + ' 添加') + '</span>'
			+ '            <span class="color-gray pull-right">需要消耗 ' + row.points + ' 积分</span></p>'
			+ '            <p>' + row.awardName + '</p>'
			+ '            <p>' + row.awardDesc + '</p>'
			+ '        </div>'
			+ '    </div>'
			+ '    <div class="card-footer">'
			+ '        <a href="javascript: $.alert(\'点赞功能还在建设中...\');" class="link">赞</a>'
			+ '        <a href="#" class="link">加入购物车</a>'
			+ '    </div>'
			+ '</div>';
	}
});