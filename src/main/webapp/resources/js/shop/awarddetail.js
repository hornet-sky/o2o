$(function() {
	var awardId = getQueryStrValByName("awardId");
	var getAwardDetailUri = "getawarddetail";
	var ctxPath;
	//初始化页面组件
	init();
	
	function init() {
		$.getJSON(getAwardDetailUri, {awardId: awardId}, function(data) {
			console.log("getAwardDetail - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				return;
			}
			var detail = data.entity.awardDetail;
			ctxPath = data.entity.resourcesServerContextPath;
			//1、初始化奖品详情卡片
			//标题
			$("header > h1.title").text(detail.awardName);
			//奖品图片
			$(".content > .card img.card-img").attr("src", detail.awardImg ? ctxPath + "/" + detail.awardImg : "../resources/image/noimage.ico")
				.attr("alt", detail.awardName);
			//创建或更新日期
			$("#operation-date").text(detail.lastEditTime ? new Date(detail.lastEditTime).format('yyyy-MM-dd') + ' 更新' : new Date(detail.createTime).format('yyyy-MM-dd') + ' 新上');
			//积分
			if(detail.points != null && detail.points != undefined) {
				$("#spend-points").text("需要消耗" + detail.points + "积分");
			}
			//商品描述
			if(detail.awardDesc) {
				$("#award-desc").text(detail.awardDesc);
			}
		});
	}
	
});