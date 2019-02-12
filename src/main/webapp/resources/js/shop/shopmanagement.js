$(function() {
	var shopId = getQueryStrValByName("shopId");
	var getInitDataUri = "getshopmanagementinitdata";
	init();
	function init() {
		$.getJSON(getInitDataUri, {
			shopId: shopId
		}, function(data) {
			console.log("init - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				setTimeout("location.href='shoplist'", 1500);
				return;
			}
			var $shopInfoBtn = $("#shop-info-btn").attr("href", "shopoperation?shopId=" + shopId);
			var shop = data.entity;
			if(shop != null && shop.enableStatus === 1) { //审核通过的店铺
				var btnsHtml = '<div class="col-50 mb"><a class="button button-big button-fill" href="#">商品管理</a></div>'
					+ '<div class="col-50 mb"><a class="button button-big button-fill" href="productcategorymanagement?shopId=' + shopId + '">类别管理</a></div>';
				$shopInfoBtn.parent().after(btnsHtml);
			}
		});
	}
});