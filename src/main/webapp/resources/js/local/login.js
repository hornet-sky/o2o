$(function() {
	var loginUri = "login";
	var $accountIpt = $("#account-ipt");
	var $passwordIpt = $("#password-ipt");
	var $kaptchaIpt = $("#kaptcha-ipt");
	var $kaptchaImg = $("#kaptcha-img");
	var $kaptchaLi = $kaptchaIpt.parents("li.hidden");
	var accountRegExp = /^\w{3,}$/; // \w 字母、数字、下划线
	var passwordRegExp = /^[\x20-\x7E]{6,}$/; //0x20 - 0x7E 可见的拉丁字符
	var loginFailureCount = 0;
	var maxFailureCount = 3;
	var defaultAccount = getQueryStrValByName("account"); //用于本地账号输入框反显
	var msg = getQueryStrValByName("msg"); //登录前显示的消息
	var targetUri = getQueryStrValByName("targetUri"); //用户登录成功后重定向到指定路径
	if(defaultAccount) {
		$accountIpt.val(defaultAccount);
	}
	if(msg) {
		$.alert(msg);
	}
	$("#login-btn").on("click", function() {
		if(!isLegalInput()) {
			if(++loginFailureCount >= maxFailureCount && $kaptchaLi.is(".hidden")) {
				$kaptchaLi.removeClass("hidden");
			}
			return;
		}
		//发起登录请求
		$.showIndicator();
		$.ajax({
			url: loginUri,
			type: "POST",
			data: {
				account: $accountIpt.val(),
				password: $passwordIpt.val(),
				kaptch: $kaptchaIpt.val(),
				checkKaptch: loginFailureCount >= maxFailureCount
			},
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("login - returned data", data);
				changeVerifyCode($kaptchaImg.get(0));
				$.hideIndicator();
				if(data.state < 0) { //请求失败
					$.alert(data.msg);
					if(++loginFailureCount >= maxFailureCount && $kaptchaLi.is(".hidden")) {
						$kaptchaLi.removeClass("hidden");
					}
					return;
				}
				if(targetUri) {
					location.href = targetUri;
					return;
				}
				var auth = data.entity;
				var userType = auth.userInfo.userType;
				if(isCustomer(userType) && isShopOwner(userType)) {
					//弹框选择要进入的系统
					$.modal({
						title: '请选择要进入的系统',
						//text: '',
						verticalButtons: true,
						buttons: [
					        {
					          text: '购物系统',
					          onClick: function() {
					        	  location.href = "../frontend/index";
					          }
					        },
					        {
					          text: '店铺管理系统',
					          onClick: function() {
					        	  location.href = "../shopadmin/shoplist";
					          }
					        }
					    ]
					});
					return;
				}
				if(isCustomer(userType)) {
					//直接进入商城购物系统
					location.href = "../frontend/index";
				}
				if(isShopOwner(userType)) {
					//直接进入店铺管理系统
					location.href = "../shopadmin/shoplist";
				}
			},
			error: function (xhr, textStatus, errorThrown) {
				printErr(xhr, textStatus, errorThrown);
				changeVerifyCode($kaptchaImg.get(0));
				$.hideIndicator();
				if(++loginFailureCount >= maxFailureCount && $kaptchaLi.is(".hidden")) {
					$kaptchaLi.removeClass("hidden");
				}
			    var result = getParsedResultFromXhr(xhr);
			    $.alert(result ? result.msg : "服务器出错~");
			}
		});
	});
	
	function isLegalInput() {
		var acc = $accountIpt.val();
		var pwd = $passwordIpt.val();
		var kap = $kaptchaIpt.val();
		if(!accountRegExp.test(acc)) {
			$.alert("本地账号只能由字母、数字、下划线组成，长度至少3位");
			return false;
		}
		if(!passwordRegExp.test(pwd)) {
			$.alert("密码不能包含汉字及其他全角字符，长度至少6位");
			return false;
		}
		if(loginFailureCount >= maxFailureCount && !kap) {
			$.alert("验证码不能为空");
			return false;
		}
		return true;
	}
	
	$kaptchaImg.click(function() {
		changeVerifyCode(this); //changeVerifyCode方法来自common.js
	});
});