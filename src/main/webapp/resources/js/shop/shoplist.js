$(function() {
	var getInitDataUri = "getshoplistinitdata";
	var getShopListUri = "getshoplist";
	//初始化页面组件
	init();
	
	function init() {
		$.getJSON(getInitDataUri, function(data) {
			console.log("init - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				return;
			}
			$("#user-name").text(data.entity.name);
			//初始化“退出系统”按钮
			$("#logout-btn").click(function() {
				$.toast("“退出系统”正在建设中");
			});
			//初始化“修改密码”按钮
			$("#change-password-btn").click(function() {
				$.toast("“修改密码”正在建设中");
			});
		});
		
		//加载店铺列表
		$.ajax({
			url: getShopListUri,
			type: "GET",
			data: {
				pageNo: 1,
				pageSize: 10
			},
			//cache: false,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("getShopList - returned data", data);
				if(data.state < 0) { //请求失败
					$.toast(data.msg);
					return;
				}
				var rowsHtml = "";
				data.rows.forEach(function(row, index) {
					rowsHtml += "<div class='row row-shop'><div class='col-40 shop-name'>"
						+ row.shopName + "</div><div class='col-40'>"
						+ getShopStatusInfo(row.enableStatus)
						+ "</div><div class='col-20'><a class='button' href='shopmanagement?shopId=" 
						+ row.shopId + "'>进入</a></div></div>";
				});
				$(".show-wrap").html(rowsHtml);
			},
			error: function (xhr, textStatus, errorThrown) {
				printErr(xhr, textStatus, errorThrown);
			    var result = getParsedResultFromXhr(xhr);
			    $.toast(result ? result.msg : "服务器出错~");
			}
		});
	}
	
	function getShopStatusInfo(status) {
		if(status === -1) {
			return "审核不通过";
		}
		if(status === 0) {
			return "审核中";
		}
		if(status === 1) {
			return "审核通过";
		}
		return "未知";
	}
	
});