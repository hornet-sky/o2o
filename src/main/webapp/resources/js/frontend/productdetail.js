$(function() {
	var productId = getQueryStrValByName("productId");
	var getProductDetailUri = "getproductdetail";
	var $cardsContainer = $(".list-container");
	var ctxPath;
	//初始化页面组件
	init();
	constructBottomToolbar();
	constructRightPanel();
	
	function init() {
		$.getJSON(getProductDetailUri, {productId: productId}, function(data) {
			console.log("getProductDetail - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				return;
			}
			var detail = data.entity.productDetail;
			ctxPath = data.entity.resourcesServerContextPath;
			//1、初始化商品详情卡片
			//标题
			$("header > h1.title").text(detail.productName);
			//商品封面图
			$(".content > .card img.card-cover").attr("src", detail.imgAddr ? ctxPath + "/" + detail.imgAddr : "../resources/image/noimage.ico")
				.attr("alt", detail.productName);
			//创建或更新日期
			$("#operation-date").text(detail.lastEditTime ? new Date(detail.lastEditTime).format('yyyy-MM-dd') + ' 更新' : new Date(detail.createTime).format('yyyy-MM-dd') + ' 新上');
			//积分
			if(detail.rewardsPoints != null && detail.rewardsPoints != undefined) {
				$("#rewards-points").text("购买可得" + detail.rewardsPoints + "积分");
			}
			//原来价格
			var $normalPriceSpan = $("#normal-price").text(detail.normalPrice);
			//特惠价格
			if(detail.promotionPrice) {
				$("#promotion-price").text(detail.promotionPrice)
					.addClass("promotion-price");
				$normalPriceSpan.addClass("discarded-price");
			}
			//商品描述
			if(detail.productDesc) {
				$("#product-desc").text(detail.productDesc);
			}
			
			//2、商品详情图列表
			var detailImgList = data.entity.productDetail.productImgList;
			if($.isArray(detailImgList)) {
				var cardsHtml = '';
				detailImgList.forEach(function(detailImg, index) {
					cardsHtml += generateCardHtml(detailImg);
				});
				$cardsContainer.append(cardsHtml);
			}
		});
	}
	
	function generateCardHtml(row) {
		return  '<li class="card">'
				+ '  <div class="card-content">'
				+ '    <div class="card-content-inner"><img src="' + ctxPath + '/' + row.imgAddr + '" alt="' + row.imgDesc + '" width="100%"/></div>'
				+ '  </div>'
				'</li>';
	}
});