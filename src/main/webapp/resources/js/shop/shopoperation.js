$(function() {
	var shopId = getQueryStrValByName("shopId");
	
	var getInitDataUri = "getshopoperationinitdata";
	var submitShopInfoUri = "registerormodifyshop";
	var imageUploadProps = { //有关图片上传的默认参数，比如允许图片的大小、格式等。
		maxUploadSize: 6291456, //6MB
		acceptImageTypes: ["image/jpg", "image/jpeg", "image/png", "image/bmp", "image/gif"],
	}; 
	//初始化页面组件
	init();
	
	function init() {
		$.getJSON(getInitDataUri, {
			parentShopCategoryId: 1, //TODO 目前先写死1，即父类是“美食”
			shopId: shopId
		}, function(data) {
			console.log("init - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				setTimeout("location.href='shoplist'", 1500);
				return;
			}
			//1、有关图片上传的参数
			var imageUploadPropsFromServer = data.entity.imageUploadProps;
			console.log("imageUploadPropsFromServer", imageUploadPropsFromServer);
			if(imageUploadPropsFromServer) {
				//替换默认参数
				if(imageUploadPropsFromServer.maxUploadSize != undefined && imageUploadPropsFromServer.maxUploadSize != null) {
					imageUploadProps.maxUploadSize = imageUploadPropsFromServer.maxUploadSize;
				}
				if($.isArray(imageUploadPropsFromServer.acceptImageTypes)) {
					imageUploadProps.acceptImageTypes = imageUploadPropsFromServer.acceptImageTypes;
				}
			}
			//2、初始化“店铺分类”下拉框
			var shopCategoryList = data.entity.shopCategoryList;
			if(!$.isArray(shopCategoryList) || shopCategoryList.length == 0) {
				$.toast("未找到任何店铺分类");
				return;
			}
			var $shopCategory = $("#shop-category");
			shopCategoryList.map(function(item, index) {
				$shopCategory.append("<option value='" + item.shopCategoryId + "'>" + item.shopCategoryName + "</option>");
			});
			//3、初始化“所在区域”下拉框
			var areaList = data.entity.areaList;
			if(!$.isArray(areaList) || areaList.length == 0) {
				$.toast("未找到任何区域信息");
				return;
			}
			var $shopArea = $("#shop-area");
			areaList.map(function(item, index) {
				$shopArea.append("<option value='" + item.areaId + "'>" + item.areaName + "</option>");
			});
			//4、初始化“提交”按钮
			var $submitBtn = $("#submit-btn").click(function() {
				submitShopInfo();
			});
			//5、绑定“验证码”图片点击事件
			var $kaptchaImg = $("#kaptcha-img").click(function() {
				changeVerifyCode(this); //changeVerifyCode方法来自common.js
			});
			//6、如果是修改店铺信息，则还需要一些特殊的处理
			if(shopId) {
				var shop = data.entity.shop;
				var $shopName = $("#shop-name").val(shop.shopName);
				var $shopAddr = $("#shop-addr").val(shop.shopAddr);
				var $shopPhone = $("#shop-phone").val(shop.phone);
				var $shopDesc = $("#shop-desc").val(shop.shopDesc);
				$shopCategory.find("option[value='" + shop.shopCategory.shopCategoryId + "']")
					.attr("selected", "selected");
				if(shop.enableStatus !== 0) { // -1 审核不通过，0 审核中，1 审核通过
					$shopCategory.attr("disabled", "disabled"); //只有审核中的可以改
				}
				$shopArea.find("option[value='" + shop.area.areaId + "']")
					.attr("selected", "selected");
				if(shop.enableStatus === -1) {
					$shopArea.attr("disabled", "disabled");
					$shopName.attr("disabled", "disabled");
					$shopAddr.attr("disabled", "disabled");
					$shopPhone.attr("disabled", "disabled");
					$shopDesc.attr("disabled", "disabled");
					$submitBtn.parent().remove();
					$kaptchaImg.parents("li").remove();
					$("#shop-img").parents("li").remove();
					$("#return-btn").parent().addClass("col-100").removeClass("col-50");
				}
			}
		});
	}
	
	var inProcess = false;
	function submitShopInfo() {
		var formData = checkInputAndGenerateFormData();
		if(formData) {
			if(inProcess) { //防止在网络不好的情况下连续点击“提交”按钮
				$.toast("信息已提交...");
				return;
			}
			inProcess = true;
			$.ajax({
				url: submitShopInfoUri,
				type: "POST",
				data: formData,
				contentType: false,
				processData: false,
				cache: false,
				dataType: "json",
				success: function(data, textStatus, jqXHR) {
					console.log("submitShopInfo - returned data", data);
					changeVerifyCode($("#kaptcha-img").get(0));
					inProcess = false;
					if(data.state < 0) { //操作失败
						$.toast(data.msg);
						return;
					}
					$.toast(data.msg);
					setTimeout("location.href='shoplist'", 1500);
				},
				error: function (xhr, textStatus, errorThrown) {
				    console.log("XMLHttpRequest", xhr);
				    console.log("textStatus", textStatus);
				    console.log("errorThrown", errorThrown);
				    changeVerifyCode($("#kaptcha-img").get(0));
				    inProcess = false;
				    var result = getParsedResultFromXhr(xhr);
				    $.toast(result ? result.msg : "服务器出错~");
				}
			});
		}
	}
	
	function checkInputAndGenerateFormData() {
		var formData = new FormData();
		if(shopId) { //修改店铺信息
			formData.append("shopId", shopId);
		}
		
		var shopName = $("#shop-name").val();
		if(!shopName) {
			$.toast("店铺名称不能为空！");
			return null;
		}
		formData.append("shopName", shopName);
		
		var shopCategory = $("#shop-category").val();
		if(!shopCategory) {
			$.toast("店铺分类不能为空！");
			return null;
		}
		formData.append("shopCategory", shopCategory);
		
		var shopArea = $("#shop-area").val();
		if(!shopArea) {
			$.toast("所在区域不能为空！");
			return null;
		}
		formData.append("shopArea", shopArea);
		
		var shopAddr = $("#shop-addr").val();
		if(!shopAddr) {
			$.toast("详细地址不能为空！");
			return null;
		}
		formData.append("shopAddr", shopAddr);
		
		var shopPhone = $("#shop-phone").val();
		if(!shopPhone) {
			$.toast("联系电话不能为空！");
			return null;
		}
		formData.append("shopPhone", shopPhone);
		
		var shopImg = $("#shop-img")[0].files[0];
		if(shopImg) {
			var imgSize = shopImg.size;
			var imgType = shopImg.type;
			var imgSizeBoundary = imageUploadProps.maxUploadSize;
			var acceptTypes = imageUploadProps.acceptImageTypes;
			console.log("imgSize={}, imgType={}", imgSize, imgType);
			if(-1 == $.inArray(imgType, acceptTypes)) {
				$.toast("照片格式不能是 " + imgType);
				return null;
			}
			if(imgSize > imgSizeBoundary) {
				$.toast("照片大小不能超过 " + humanizeByteSize(imgSizeBoundary));
				return null;
			}
			formData.append("shopImg", shopImg);
		}
		
		var shopDesc = $("#shop-desc").val();
		formData.append("shopDesc", shopDesc);
		
		var verifyCodeActual = $("#kaptcha-ipt").val();
		if(!verifyCodeActual) {
			$.toast("验证码不能为空！");
			return null;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		
		return formData;
	}
});