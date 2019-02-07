/**
 * 验证码图片点击事件处理函数
 * @param img 验证码图片
 * @returns
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
 * @returns 解析后的ajax结果
 */
function getParsedResultFromXhr(xhr) {
	if(!xhr || !xhr.responseText) {
    	return null;
    }
	try {
		return $.parseJSON(xhr.responseText);
	} catch(e) {
		console.error(e);
		return null;
	}
}