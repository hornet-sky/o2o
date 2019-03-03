$(function() {
	var getInitDataUri = "getshoplistinitdata";
	var getShopListUri = "getshoplist";
	var paging = initPaging(".row-paging", loadShopList);
	//初始化页面组件
	init();
	
	function init() {
		$.getJSON(getInitDataUri, function(data) {
			console.log("initData - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				return;
			}
			$("#user-name").text(data.entity.owner.name);
			if(data.entity.existsLocalAuth) {
				var btnsHtml = '<div class="col-50"><a href="javascript:void(0);" id="logout-btn" class="button button-big button-fill button-danger">退出系统</a></div>'
					+ '<div class="col-50"><a href="javascript:void(0);" id="change-password-btn" class="button button-big button-fill button-success">修改密码</a></div>';
				$("#btns").html(btnsHtml);
				//初始化“退出系统”按钮
				$("#logout-btn").click(function() {
					$.toast("“退出系统”正在建设中");
				});
				//初始化“修改密码”按钮
				$("#change-password-btn").click(function() {
					$.toast("“修改密码”正在建设中");
				});
				return;
			}
			var btnsHtml = '<div class="col-100"><a href="../local/auth" class="button button-big button-fill" external>绑定本地账号</a></div>';
			$("#btns").html(btnsHtml);
		});
		
		//加载店铺列表
		loadShopList();
	}
	
	function loadShopList() {
		$.showIndicator();
		$.ajax({
			url: getShopListUri,
			type: "GET",
			data: {
				pageNo: paging.pageNo,
				pageSize: paging.pageSize
			},
			//cache: false,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("getShopList - returned data", data);
				if(data.state < 0) { //请求失败
					$.hideIndicator();
					$.toast(data.msg);
					return;
				}
				paging.refreshStatus(data.total);
				var rowsHtml = "";
				data.rows.forEach(function(row, index) {
					rowsHtml += "<div class='row row-content'><div class='col-40 text-ellipsis'>"
						+ row.shopName + "</div><div class='col-40'>"
						+ getShopStatusInfo(row.enableStatus)
						+ "</div><div class='col-20'><a class='button' href='shopmanagement?shopId=" 
						+ row.shopId + "' external>进入</a></div></div>";
				});
				$(".content-wrap").html(rowsHtml);
				$.hideIndicator();
			},
			error: function (xhr, textStatus, errorThrown) {
				printErr(xhr, textStatus, errorThrown);
				$.hideIndicator();
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