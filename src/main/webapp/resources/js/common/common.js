/**
 * 验证码图片点击事件处理函数
 * @param img 验证码图片
 * @returns undefined
 */
function changeVerifyCode(img) {
	img.src = "../kaptcha?r=" + Math.random();
}

/**
 * 将byte大小转换成更易读的方式
 * @param size 原始的byte大小，例如1048576
 * @returns 可读性更高的结果，例如1.21KB
 */
function humanizeByteSize(size) {
	if(size < 0) {
		return null;
	} else if(size < 1024) {
		return size + "B";
	} else if(size < 1024 * 1024) {
		var num = (size / 1024).toFixed(3);
		return num.substring(0, num.lastIndexOf(".") + 3) + "KB";
	} else if(size < 1024 * 1024 * 1024) {
		var num = (size / 1024 / 1024).toFixed(3);
		return num.substring(0, num.lastIndexOf(".") + 3) + "MB";
	} else if(size < 1024 * 1024 * 1024 * 1024) {
		var num = (size / 1024 / 1024 / 1024).toFixed(3);
		return num.substring(0, num.lastIndexOf(".") + 3) + "GB";
	}
	return size + "B";
}

/**
 * 获得解析后的ajax结果（application/json）
 * @param xhr XMLHttpRequest
 * @returns 解析后的结果
 */
function getParsedResultFromXhr(xhr) {
	if(!xhr || !xhr.responseText) {
    	return null;
    }
	try {
		return $.parseJSON(xhr.responseText);
	} catch(e) {
		console.error(e);
	}
}

/**
 * 通过解析请求参数串获取指定名字的请求参数值
 * @param name 参数名
 * @returns 参数值
 */
function getQueryStrValByName(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    if(result == null) { //没匹配到
        return null;
    }
    return decodeURIComponent(result[2]);
}

/**
 * 打印请求失败响应信息
 * @param xhr XMLHttpRequest
 * @param textStatus 文本状态
 * @param errorThrown 异常
 * @returns undefined
 */
function printErr(xhr, textStatus, errorThrown) {
	console.log("XMLHttpRequest", xhr);
    console.log("textStatus", textStatus);
    console.log("errorThrown", errorThrown);
}

//给Date实例添加一个format方法，用于格式化日期
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}