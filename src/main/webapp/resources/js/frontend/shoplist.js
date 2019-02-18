$(function() {
	var parentShopCategoryId = getQueryStrValByName("parentShopCategoryId");
	var listShopInitInfoUri = "listshopinitinfo";
	var listShopUri = "listshop";
	var pageNo = 1, pageSize = 10;
	var loading = false;
	var $cardsContainer = $(".list-container");
	var $infiniteScroll = $('.infinite-scroll-bottom');
	var $infiniteScrollPreloader = $('.infinite-scroll-preloader');
	var infiniteScrollEnabled = true;
	var ctxPath;
	//初始化页面组件
	init();
	
	function init() {
		$.init();
		$.getJSON(listShopInitInfoUri, {parentShopCategoryId: parentShopCategoryId}, function(data) {
			console.log("listShopInitInfo - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				return;
			}
			var shopCategoryList = data.entity.shopCategoryList;
			var areaList = data.entity.areaList;
			ctxPath = data.entity.resourcesServerContextPath;
			//初始化店铺类别
			var $contentPadded = $(".content > .content-padded");
			var shopCategorysHtml = '<div class="row"><div class="col-33"><a href="javascript:void(0);" class="button button-fill">全部类别</a></div>';
			if($.isArray(shopCategoryList)) {
				var category;
				for(var i = 0, len = shopCategoryList.length; i < len; i++) {
					category = shopCategoryList[i];
					shopCategorysHtml += '<div class="col-33"><a href="javascript:void(0);" data-category-id="' + category.shopCategoryId + '" class="button">' + category.shopCategoryName + '</a></div>';
					if((i + 2) % 3 === 0 && i < len - 1) {
						shopCategorysHtml += '</div><div class="row">';
					}
				}
			}
			shopCategorysHtml += '</div>';
			$contentPadded.prepend(shopCategorysHtml);
			//初始化区域下拉框
			var $areaSel = $(".content > .content-padded .select");
			if($.isArray(areaList) && areaList.length > 0) {
				var areasHtml = '';
				areaList.forEach(function(area, index) {
					areasHtml += '<option value="' + area.areaId + '">' + area.areaName + '</option>';
				});
				$areaSel.append(areasHtml);
			}
			//初始化事件监听
			//1、店铺类别按钮
			$contentPadded.on("click", "a.button", function() {
				$contentPadded.find("a.button-fill").removeClass("button-fill");
				$(this).addClass("button-fill");
				appendShopItemsFirstPage();
			});
			//2、区域下拉框
			$areaSel.on("change", function() {
				appendShopItemsFirstPage();
			});
			//3、搜索栏 - 点击软键盘的“搜索”按钮
			var t;
			var $searchIpt = $("#search").on("keyup", function(e) {
				console.log("e.keyCode", e.keyCode);
				clearTimeout(t);
				if(e.keyCode == '13') {
					e.preventDefault();
					appendShopItemsFirstPage();
					return;
				}
				t = setTimeout(function() {
					appendShopItemsFirstPage();
				}, 2000);
			});
			//4、搜索栏 - 点击右侧的“取消”按钮
			$(".searchbar-cancel").on("click", function() {
				$searchIpt.val("");
				appendShopItemsFirstPage();
			});
			//5、店铺卡片点击事件
			$cardsContainer.on("click", "li.card", function() {
				var currShopId = $(this).data("shopId");
				if(currShopId) {
					location.href="productlist?shopId=" + currShopId;
				}
			});
			//6、注册'infinite'事件处理函数
			$infiniteScroll.on('infinite', function() {
				console.log("滚动触发请求数据...", infiniteScrollEnabled);
				if(infiniteScrollEnabled) {
					appendShopItems();
				}
			});
			//初次查询店铺列表并以卡片的形式在页面展示
			appendShopItems();
		});
	}
	
	function appendShopItemsFirstPage() {
		if(loading) {
			return;
		}
		if(!infiniteScrollEnabled) {
			infiniteScrollEnabled = true;
			$infiniteScrollPreloader.show();
		}
		$cardsContainer.empty();
		pageNo = 1;
		appendShopItems();
	}
	
	function appendShopItems() {
		if(loading) {
			return;
		}
		loading = true;
		var params = generateQueryParamsObj();
		console.log("queryParamsObj", params);
		$.ajax({
			url: listShopUri,
			type: "GET",
			data: params,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("listShop - returned data", data);
				loading = false;
				if(data.state < 0) { //请求失败
					$.toast(data.msg);
					return;
				}
				var cardsHtml = "";
				data.rows.forEach(function(row, index) {
					cardsHtml += generateCardHtml(row);
				});
				$cardsContainer.append(cardsHtml);
				console.log("li.card", $('.list-container > li.card'));
				if($('.list-container > li.card').length >= data.total) {
	            	//所有数据加载完毕，则注销无限加载事件，以防不必要的加载
	            	//$.detachInfiniteScroll($infiniteScroll);
					infiniteScrollEnabled = false;
	            	//隐藏加载提示符
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
		//关键字
		var searchKey = $("#search").val();
		//店铺类别
		var shopCategoryId = $(".content > .content-padded a.button-fill").data("categoryId");
		//区域
		var areaId = $(".content > .content-padded .select").val();
		var params = {
			pageNo: pageNo,
			pageSize: pageSize
		};
		if(searchKey) {
			params.searchKey = searchKey;
		}
		if(areaId) {
			params.areaId = areaId;
		}
		if(parentShopCategoryId) {
			params.parentShopCategoryId = parentShopCategoryId;
			if(shopCategoryId)
				params.shopCategoryId = shopCategoryId;
		}
		if(!parentShopCategoryId && shopCategoryId) {
			params.parentShopCategoryId = shopCategoryId;
		}
		return params;
	}
	
	function generateCardHtml(row) {
		return  '<li class="card" data-shop-id="' + row.shopId + '">'
			+ '    <div class="card-header">' + row.shopName + '</div>'
			+ '    <div class="card-content">'
			+ '      <div class="list-block media-list">'
			+ '        <ul>'
			+ '          <li class="item-content">'
			+ '            <div class="item-media">'
			+ '              <img src="' + ctxPath + '/' + row.shopImg + '" width="60">'
			+ '            </div>'
			+ '            <div class="item-inner">'
			+ '              <div class="item-title-row">'
			+ '                <div class="item-title">' + row.shopDesc + '</div>'
			+ '              </div>'
			+ '              <div class="item-subtitle"></div>'
			+ '            </div>'
			+ '          </li>'
			+ '        </ul>'
			+ '      </div>'
			+ '    </div>'
			+ '    <div class="card-footer">'
			+ '      <span>' + (row.lastEditTime ? new Date(row.lastEditTime).format('yyyy-MM-dd') + ' 更新' : new Date(row.createTime).format('yyyy-MM-dd') + ' 入驻') + '</span>'
			+ '      <span>点击查看</span>'
			+ '    </div>'
			+ '</li>';
	}
});