$(function() {
	var parentShopCategoryId = getQueryStrValByName("parentShopCategoryId");
	var listShopInitInfoUri = "listshopinitinfo";
	var listShopUri = "listshop";
	var pageNo = 1;
	var pageSize = 10;
	var total = 0;
	var $cardsContainer = $(".content > .cards");
	var ctxPath;
	//初始化页面组件
	init();
	
	function init() {
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
			if($.isArray(areaList) && areaList.length > 0) {
				var $areaSel = $(".content > .content-padded .select");
				var areasHtml = '';
				areaList.forEach(function(area, index) {
					areasHtml += '<option value="' + area.areaId + '">' + area.areaName + '</option>';
				});
				$areaSel.append(areasHtml);
			}
			//初始化事件监听
			$contentPadded.on("click", "a.button", function() {
				$contentPadded.find("a.button-fill").removeClass("button-fill");
				$(this).addClass("button-fill");
				$cardsContainer.empty();
				appendShopItems();
			});
			//初次查询店铺列表并以卡片的形式在页面展示
			appendShopItems();
		});
	}
	
	function appendShopItems() {
		var params = generateQueryParamsObj();
		console.log("queryParamsObj", params);
		$.ajax({
			url: listShopUri,
			type: "GET",
			data: params,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("listShop - returned data", data);
				if(data.state < 0) { //请求失败
					$.toast(data.msg);
					return;
				}
				total = data.total;
				var cardsHtml = "";
				data.rows.forEach(function(row, index) {
					cardsHtml += generateCardHtml(row);
				});
				$cardsContainer.append(cardsHtml);
			},
			error: function (xhr, textStatus, errorThrown) {
				printErr(xhr, textStatus, errorThrown);
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
		return  '<div class="card">'
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
			+ '</div>';
	}
});