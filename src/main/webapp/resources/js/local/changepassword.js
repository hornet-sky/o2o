$(function() {
	var changePasswordUri = "changepassword";
	var $accountIpt = $("#account-ipt");
	var $oldPasswordIpt = $("#old-password-ipt");
	var $newPasswordIpt = $("#new-password-ipt");
	var $repasswordIpt = $("#repassword-ipt");
	var $kaptchaIpt = $("#kaptcha-ipt");
	var $kaptchaImg = $("#kaptcha-img");
	var $changePwdBtn = $("#change-password-btn");
	var accountRegExp = /^\w{3,}$/; // \w 字母、数字、下划线
	var passwordRegExp = /^[\x20-\x7E]{6,}$/; //0x20 - 0x7E 可见的拉丁字符
	var defaultAccount = getQueryStrValByName("account");
	if(defaultAccount) {
		$accountIpt.val(defaultAccount);
	}
	$changePwdBtn.on("click", function() {
		if(!isLegalInput()) {
			return;
		}
		//发起登录请求
		$.showIndicator();
		$.ajax({
			url: changePasswordUri,
			type: "POST",
			data: {
				account: $accountIpt.val(),
				oldPassword: $oldPasswordIpt.val(),
				newPassword: $newPasswordIpt.val(),
				kaptch: $kaptchaIpt.val()
			},
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("changePassword - returned data", data);
				changeVerifyCode($kaptchaImg.get(0));
				$.hideIndicator();
				$.alert(data.msg);
				if(data.state < 0) { //请求失败
					return;
				}
				setTimeout("location.href='logout'", 1500);
			},
			error: function (xhr, textStatus, errorThrown) {
				printErr(xhr, textStatus, errorThrown);
				changeVerifyCode($kaptchaImg.get(0));
				$.hideIndicator();
			    var result = getParsedResultFromXhr(xhr);
			    $.alert(result ? result.msg : "服务器出错~");
			}
		});
	});
	
	function isLegalInput() {
		var acc = $accountIpt.val();
		var oldPwd = $oldPasswordIpt.val();
		var newPwd = $newPasswordIpt.val();
		var rep = $repasswordIpt.val();
		var kap = $kaptchaIpt.val();
		if(!accountRegExp.test(acc)) {
			$.alert("本地账号只能由字母、数字、下划线组成，长度至少3位");
			return false;
		}
		if(!passwordRegExp.test(oldPwd)) {
			$.alert("旧密码不能包含汉字及其他全角字符，长度至少6位");
			return false;
		}
		if(!passwordRegExp.test(newPwd)) {
			$.alert("新密码不能包含汉字及其他全角字符，长度至少6位");
			return false;
		}
		if(newPwd !== rep) {
			$.alert("两次输入的新密码不一致");
			return false;
		}
		if(newPwd === oldPwd) {
			$.alert("新密码不能与旧密码相同");
			return false;
		}
		if(!kap) {
			$.alert("验证码不能为空");
			return false;
		}
		return true;
	}
	
	$kaptchaImg.click(function() {
		changeVerifyCode(this); //changeVerifyCode方法来自common.js
	});
});