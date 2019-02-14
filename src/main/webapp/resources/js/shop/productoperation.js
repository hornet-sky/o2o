$(function() {
	var productId = getQueryStrValByName("productId");
	var shopId = getQueryStrValByName("shopId");
	var getInitDataUri = "getproductoperationinitdata";
	var submitProductInfoUri = "addormodifyproduct";
	var imageUploadProps = { //有关图片上传的默认参数，比如允许图片的大小、格式等。
		maxUploadSize: 6291456, //6MB
		acceptImageTypes: ["image/jpg", "image/jpeg", "image/png", "image/bmp", "image/gif"],
		maxImageCount: 6
	}; 
	//初始化页面组件
	init();
	
	function init() {
		$.getJSON(getInitDataUri, {
			shopId: shopId,
			productId: productId
		}, function(data) {
			console.log("init - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				setTimeout("location.href='productmanagement?shopId=" + shopId + "'", 1500);
				return;
			}
			//1、有关图片上传的参数
			var imageUploadPropsFromServer = data.entity.imageUploadProps;
			if(imageUploadPropsFromServer) {
				//替换默认参数
				if(imageUploadPropsFromServer.maxUploadSize != undefined && imageUploadPropsFromServer.maxUploadSize != null) {
					imageUploadProps.maxUploadSize = imageUploadPropsFromServer.maxUploadSize;
				}
				if($.isArray(imageUploadPropsFromServer.acceptImageTypes)) {
					imageUploadProps.acceptImageTypes = imageUploadPropsFromServer.acceptImageTypes;
				}
				if(imageUploadPropsFromServer.maxImageCount != undefined && imageUploadPropsFromServer.maxImageCount != null) {
					imageUploadProps.maxImageCount = imageUploadPropsFromServer.maxImageCount;
				}
			}
			//2、初始化“商品类型”下拉框
			var productCategoryList = data.entity.productCategoryList;
			if(!$.isArray(productCategoryList) || productCategoryList.length == 0) {
				$.toast("未找到任何商品类型");
				setTimeout("location.href='productmanagement?shopId=" + shopId + "'", 1500);
				return;
			}
			var $productCategory = $("#product-category");
			productCategoryList.forEach(function(item, index) {
				$productCategory.append("<option value='" + item.productCategoryId + "'>" + item.productCategoryName + "</option>");
			});
			//3、初始化“提交”按钮
			var $submitBtn = $("#submit-btn").click(function() {
				submitProductInfo();
			});
			//4、绑定“验证码”图片点击事件
			var $kaptchaImg = $("#kaptcha-img").click(function() {
				changeVerifyCode(this); //changeVerifyCode方法来自common.js
			});
			//5、可添加多个商品详情图
			var $detailImgContainer = $("#detail-img-container");
			$detailImgContainer.on("change", ".detail-img:last-child", function() {
				var detailImgs = $detailImgContainer.children();
				if(detailImgs.length >= imageUploadProps.maxImageCount) {
					return;
				}
				$detailImgContainer.append('<input type="file" class="detail-img" accept="image/*" />');
			});
			//6、如果是修改产品信息，则还需要一些特殊的处理
			if(productId) {
				var product = data.entity.product;
				var $productName = $("#product-name").val(product.productName);
				var $priority = $("#priority").val(product.priority);
				var $normalPrice = $("#normal-price").val(product.normalPrice);
				var $promotionPrice = $("#promotion-price").val(product.promotionPrice);
				var $rewardsPoints = $("#rewards-points").val(product.rewardsPoints);
				var $productDesc = $("#product-desc").val(product.productDesc);
				$productCategory.find("option[value='" + product.productCategory.productCategoryId + "']")
					.attr("selected", "selected");
			}
		});
	}
	
	function submitProductInfo() {
		var formData = checkInputAndGenerateFormData();
		if(formData) {
			$.showPreloader('提交中...');
			$.ajax({
				url: submitProductInfoUri,
				type: "POST",
				data: formData,
				contentType: false,
				processData: false,
				cache: false,
				dataType: "json",
				success: function(data, textStatus, jqXHR) {
					console.log("submitProductInfo - returned data", data);
					changeVerifyCode($("#kaptcha-img").get(0));
					$.hidePreloader();
					if(data.state < 0) { //操作失败
						$.toast(data.msg);
						return;
					}
					$.toast(data.msg);
					setTimeout("location.href='productmanagement?shopId=" + shopId + "'", 1500);
				},
				error: function (xhr, textStatus, errorThrown) {
					printErr(xhr, textStatus, errorThrown);
				    changeVerifyCode($("#kaptcha-img").get(0));
				    $.hidePreloader();
				    var result = getParsedResultFromXhr(xhr);
				    $.toast(result ? result.msg : "服务器出错~");
				}
			});
		}
	}
	
	function checkInputAndGenerateFormData() {
		var formData = new FormData();
		formData.append("shopId", shopId);
		if(productId) { //修改商品信息
			formData.append("productId", productId);
		}
		//验证商品名称
		var productName = $("#product-name").val();
		if(!productName) {
			$.toast("商品名称不能为空！");
			return null;
		}
		formData.append("productName", productName);
		//验证商品类型
		var productCategory = $("#product-category").val();
		if(!productCategory) {
			$.toast("商品类型不能为空！");
			return null;
		}
		formData.append("productCategory", productCategory);
		//商品优先级
		var priority = $("#priority").val();
		if(!priority) {
			priority = 0;
		}
		formData.append("priority", priority);
		//商品原价
		var normalPrice = $("#normal-price").val();
		formData.append("normalPrice", normalPrice);
		//商品现价
		var promotionPrice = $("#promotion-price").val();
		formData.append("promotionPrice", promotionPrice);
		//商品积分
		var rewardsPoints = $("#rewards-points").val();
		formData.append("rewardsPoints", rewardsPoints);
		//商品封面图
		var coverImg = $("#cover-img")[0].files[0];
		if(coverImg) {
			if(!isAccepted(coverImg)) {
				return null;
			}
			formData.append("coverImg", coverImg);
		}
		//商品详情图
		var detailImgs = $("#detail-img-container .detail-img");
		var detailImg;
		for(var i = 0, len = detailImgs.length; i < len; i++) {
			detailImg = detailImgs[i].files[0];
			console.log("detailImg", detailImg);
			if(detailImg) {
				if(!isAccepted(detailImg)) {
					return null;
				}
				formData.append("detailImgs", detailImg); //追加多个同名参数，后台可以用数组接收
			}
		}
		//商品描述
		var productDesc = $("#product-desc").val();
		formData.append("productDesc", productDesc);
		//验证验证码
		var verifyCodeActual = $("#kaptcha-ipt").val();
		if(!verifyCodeActual) {
			$.toast("验证码不能为空！");
			return null;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		return formData;
	}
	
	function isAccepted(targetImg) {
		var imgSize = targetImg.size;
		var imgType = targetImg.type;
		var imgName = targetImg.name;
		var imgSizeBoundary = imageUploadProps.maxUploadSize;
		var acceptTypes = imageUploadProps.acceptImageTypes;
		console.log("imgSize={}, imgType={}, imgName={}", imgSize, imgType, imgName);
		if(-1 == $.inArray(imgType, acceptTypes)) {
			$.toast(imgName + " 图片格式不能是 " + imgType);
			return false;
		}
		if(imgSize > imgSizeBoundary) {
			$.toast(imgName + " 图片大小不能超过 " + humanizeByteSize(imgSizeBoundary));
			return false;
		}
		return true;
	}
});