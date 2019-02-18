$(function() {
	var shopId = getQueryStrValByName("shopId");
	var listProductInitInfoUri = "listproductinitinfo";
	var listProductUri = "listproduct";
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
		$.getJSON(listProductInitInfoUri, {shopId: shopId}, function(data) {
			console.log("listProductInitInfo - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				return;
			}
			var shop = data.entity.shop;
			var productCategoryList = data.entity.productCategoryList;
			ctxPath = data.entity.resourcesServerContextPath;
			//初始化店铺详情卡片
			$("header > h1.title").text(shop.shopName);
			$(".content > .card img.card-cover").attr("src", ctxPath + "/" + shop.shopImg)
				.attr("alt", shop.shopName);
			$cardContentItems = $(".content > .card .card-content-inner > p");
			$cardContentItems.eq(0).text(shop.lastEditTime ? new Date(shop.lastEditTime).format('yyyy-MM-dd') + ' 更新' : new Date(shop.createTime).format('yyyy-MM-dd') + ' 入驻');
			$cardContentItems.eq(1).text(shop.shopDesc);
			$cardFooterItems = $(".content > .card > .card-footer > span");
			$cardFooterItems.eq(0).text(shop.shopAddr);
			$cardFooterItems.eq(1).text(shop.phone);
			//初始化商品类别
			var $contentPadded = $(".content > .content-padded");
			var productCategorysHtml = '<div class="row"><div class="col-33"><a href="javascript:void(0);" class="button button-fill">全部类别</a></div>';
			if($.isArray(productCategoryList)) {
				var category;
				for(var i = 0, len = productCategoryList.length; i < len; i++) {
					category = productCategoryList[i];
					productCategorysHtml += '<div class="col-33"><a href="javascript:void(0);" data-category-id="' + category.productCategoryId + '" class="button">' + category.productCategoryName + '</a></div>';
					if((i + 2) % 3 === 0 && i < len - 1) {
						productCategorysHtml += '</div><div class="row">';
					}
				}
			}
			productCategorysHtml += '</div>';
			$contentPadded.prepend(productCategorysHtml);
			//初始化事件监听
			//1、店铺类别按钮
			$contentPadded.find(".row > .col-33 > a.button").on("click", function() {
				console.log("点击了“店铺类别”");
				$contentPadded.find("a.button-fill").removeClass("button-fill");
				$(this).addClass("button-fill");
				appendProductItemsFirstPage();
			});
			//2、搜索栏 - 点击软键盘的“搜索”按钮
			var t;
			var $searchIpt = $("#search").on("keyup", function(e) {
				console.log("e.keyCode", e.keyCode);
				clearTimeout(t);
				if(e.keyCode == '13') {
					e.preventDefault();
					appendProductItemsFirstPage();
					return;
				}
				t = setTimeout(function() {
					appendProductItemsFirstPage();
				}, 2000);
			});
			//4、搜索栏 - 点击右侧的“取消”按钮
			$contentPadded.find(".searchbar > a.searchbar-cancel").on("click", function() {
				$searchIpt.val("");
				appendProductItemsFirstPage();
			});
			//5、注册'infinite'事件处理函数
			$infiniteScroll.on('infinite', function() {
				console.log("滚动触发请求数据...");
				if(infiniteScrollEnabled) {
					appendProductItems();
				}
			});
			//初次查询商品列表并以卡片的形式在页面展示
			appendProductItems();
		});
	}
	
	function appendProductItemsFirstPage() {
		if(loading) {
			return;
		}
		if(!infiniteScrollEnabled) {
			infiniteScrollEnabled = true;
			$infiniteScrollPreloader.show();
		}
		$cardsContainer.empty();
		pageNo = 1;
		appendProductItems();
	}
	
	function appendProductItems() {
		if(loading) {
			return;
		}
		loading = true;
		var params = generateQueryParamsObj();
		console.log("queryParamsObj", params);
		$.ajax({
			url: listProductUri,
			type: "GET",
			data: params,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("listProduct - returned data", data);
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
				if($('.list-container > li.card').length >= data.total) {
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
		//关键字
		var searchKey = $("#search").val();
		//商品类别
		var productCategoryId = $(".content > .content-padded a.button-fill").data("categoryId");
		var params = {
			shopId: shopId,
			pageNo: pageNo,
			pageSize: pageSize
		};
		if(searchKey) {
			params.searchKey = searchKey;
		}
		if(productCategoryId) {
			params.productCategoryId = productCategoryId;
		}
		return params;
	}
	
	function generateCardHtml(row) {
		return  '<li class="card" data-product-id="' + row.productId + '">'
			+ '    <div class="card-header">' + row.productName + '</div>'
			+ '    <div class="card-content">'
			+ '      <div class="list-block media-list">'
			+ '        <ul>'
			+ '          <li class="item-content">'
			+ '            <div class="item-media">'
			+ '              <img src="' + ctxPath + '/' + row.imgAddr + '" width="60">'
			+ '            </div>'
			+ '            <div class="item-inner">'
			+ '              <div class="item-title-row">'
			+ '                <div class="item-title">' + row.productDesc + '</div>'
			+ '              </div>'
			+ '              <div class="item-subtitle"></div>'
			+ '            </div>'
			+ '          </li>'
			+ '        </ul>'
			+ '      </div>'
			+ '    </div>'
			+ '    <div class="card-footer">'
			+ '      <span>' + (row.lastEditTime ? new Date(row.lastEditTime).format('yyyy-MM-dd') + ' 更新' : new Date(row.createTime).format('yyyy-MM-dd') + ' 新上') + '</span>'
			+ '      <span>点击查看</span>'
			+ '    </div>'
			+ '</li>';
	}
});