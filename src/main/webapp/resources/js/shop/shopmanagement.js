$(function() {
	var shopId = getQueryStrValByName("shopId");
	var getInitDataUri = "getshopmanagementinitdata";
	init();
	function init() {
		$.getJSON(getInitDataUri, {
			shopId: shopId //用于拦截器校验
		}, function(data) {
			console.log("init - returned data", data);
			if(data.state < 0) { //请求失败
				$.alert(data.msg, function() {
					location.href='shoplist';
				});
				return;
			}
			var $shopInfoBtn = $("#shop-info-btn").attr("href", "shopoperation?shopId=" + shopId);
			var shop = data.entity;
			if(shop != null && shop.enableStatus === 1) { //审核通过的店铺
				var btnsHtml = '<div class="col-50 mb"><a class="button button-big button-fill" href="productmanagement?shopId=' + shopId + '">商品管理</a></div>'
					+ '<div class="col-50 mb"><a class="button button-big button-fill" href="productcategorymanagement?shopId=' + shopId + '">类别管理</a></div>'
					+ '<div class="col-50 mb"><a class="button button-big button-fill" href="awardmanagement?shopId=' + shopId + '">奖品管理</a></div>'
					+ '<div class="col-50 mb"><a class="button button-big button-fill" href="consumptionrecordlist?shopId=' + shopId + '">消费记录</a></div>'
					+ '<div class="col-50 mb"><a class="button button-big button-fill" href="pointsexpenditurerecordlist?shopId=' + shopId + '">积分兑换</a></div>'
					+ '<div class="col-50 mb"><a class="button button-big button-fill" href="consumerpointslist?shopId=' + shopId + '">顾客积分</a></div>';
				$shopInfoBtn.parent().after(btnsHtml);
			}
		});
	}
});