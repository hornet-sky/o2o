$(function() {
	var awardId = getQueryStrValByName("awardId");
	var shopId = getQueryStrValByName("shopId");
	var getInitDataUri = "getawardoperationinitdata";
	var submitAwardInfoUri = "addormodifyaward";
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
			awardId: awardId
		}, function(data) {
			console.log("init - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				setTimeout("location.href='awardmanagement?shopId=" + shopId + "'", 1500);
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
			//2、绑定“积分”输入事件处理函数
			var $points = $("#points").on("input", function() {
				var val = this.value;
				if(val) {
					val = parseInt(val.replace(/[^\d]/g, ""));
					if(val > 99999) {
						val = 99999;
					}
				}
				this.value = val;
			});
			//3、初始化“提交”按钮
			var $submitBtn = $("#submit-btn").click(function() {
				submitAwardInfo();
			});
			//4、绑定“验证码”图片点击事件处理函数
			var $kaptchaImg = $("#kaptcha-img").click(function() {
				changeVerifyCode(this); //changeVerifyCode方法来自common.js
			});
			//5、如果是修改奖品信息，则还需要一些特殊的处理
			if(awardId) {
				var award = data.entity.award;
				$("#award-name").val(award.awardName);
				$("#priority").val(award.priority);
				$("#points").val(award.points);
				$("#award-desc").val(award.awardDesc);
			}
		});
	}
	
	function submitAwardInfo() {
		var formData = checkInputAndGenerateFormData();
		if(formData) {
			$.showPreloader('提交中...');
			$.ajax({
				url: submitAwardInfoUri,
				type: "POST",
				data: formData,
				contentType: false,
				processData: false,
				cache: false,
				dataType: "json",
				success: function(data, textStatus, jqXHR) {
					console.log("submitAwardInfo - returned data", data);
					changeVerifyCode($("#kaptcha-img").get(0));
					$.hidePreloader();
					if(data.state < 0) { //操作失败
						$.toast(data.msg);
						return;
					}
					$.toast(data.msg);
					setTimeout("location.href='awardmanagement?shopId=" + shopId + "'", 1500);
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
		if(awardId) { //修改奖品信息
			formData.append("awardId", awardId);
		}
		//验证奖品名称
		var awardName = $("#award-name").val();
		if(!awardName) {
			$.toast("奖品名称不能为空！");
			return null;
		}
		formData.append("awardName", awardName);
		//奖品优先级
		var priority = $("#priority").val();
		if(!priority) {
			priority = 0;
		}
		formData.append("priority", priority);
		//积分
		var points = $("#points").val();
		if(!points) {
			$.toast("积分不能为空！");
			return null;
		}
		formData.append("points", points);
		//奖品图片
		var awardImg = $("#award-img")[0].files[0];
		if(awardImg) {
			if(!isAccepted(awardImg)) {
				return null;
			}
			formData.append("awardImg", awardImg);
		}
		//奖品描述
		var awardDesc = $("#award-desc").val();
		formData.append("awardDesc", awardDesc);
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