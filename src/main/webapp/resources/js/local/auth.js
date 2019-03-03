$(function() {
	var createAndBindAuthUri = "createandbindauth";
	var $accountIpt = $("#account-ipt");
	var $passwordIpt = $("#password-ipt");
	var $repasswordIpt = $("#repassword-ipt");
	var $kaptchaIpt = $("#kaptcha-ipt");
	var $kaptchaImg = $("#kaptcha-img");
	var accountRegExp = /^\w{3,}$/; // \w 字母、数字、下划线
	var passwordRegExp = /^[\x20-\x7E]{6,}$/; //0x20 - 0x7E 可见的拉丁字符
	$("#bind-btn").on("click", function() {
		if(isLegalInput()) {
			$.showIndicator();
			$.ajax({
				url: createAndBindAuthUri,
				type: "POST",
				data: {
					account: $accountIpt.val(),
					password: $passwordIpt.val(),
					kaptch: $kaptchaIpt.val()
				},
				dataType: "json",
				success: function(data, textStatus, jqXHR) {
					console.log("createAndBindAuth - returned data", data);
					changeVerifyCode($kaptchaImg.get(0));
					$.hideIndicator();
					$.alert(data.msg);
					if(data.state < 0) { //请求失败
						return;
					}
					setTimeout("history.go(-1);", 1500);
				},
				error: function (xhr, textStatus, errorThrown) {
					printErr(xhr, textStatus, errorThrown);
					changeVerifyCode($kaptchaImg.get(0));
					$.hideIndicator();
				    var result = getParsedResultFromXhr(xhr);
				    $.alert(result ? result.msg : "服务器出错~");
				}
			});
		}
	});
	function isLegalInput() {
		var acc = $accountIpt.val();
		var pwd = $passwordIpt.val();
		var rep = $repasswordIpt.val();
		var kap = $kaptchaIpt.val();
		if(!accountRegExp.test(acc)) {
			$.alert("本地账号只能由字母、数字、下划线组成，长度至少3位");
			return false;
		}
		if(!passwordRegExp.test(pwd)) {
			$.alert("密码不能包含汉字及其他全角字符，长度至少6位");
			return false;
		}
		if(pwd !== rep) {
			$.alert("两次输入的密码不一致");
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