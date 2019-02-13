$(function() {
	var shopId = getQueryStrValByName("shopId");
	var getProductListUri = "getproductlist";
	var shelveProductUri = "shelveproduct";
	var addOrEditProductUri = "addoreditproduct";
	var viewProductUri = "viewproduct";
	var paging = initPaging(".row-paging", loadProductList);
	var $contentWrap = $(".content-wrap");
	//初始化页面组件
	init();
	
	function init() {
		//1、加载商品列表
		loadProductList();
		
		//2、绑定上架、下架操作处理函数
		$contentWrap.on("click", "a.button-shelve", function() {
			var $unshelveBtn = $(this);
			var pid = $unshelveBtn.data("pid");
			var pname = $unshelveBtn.data("pname");
			var enableStatus = $unshelveBtn.data("enableStatus");
			var operationDesc = enableStatus ? "下架" : "上架";
			console.log("pid=" + pid + ", pname=" + pname + ", enableStatus=" + enableStatus);
			$.confirm("确定" + operationDesc + " " + pname + " 吗?", function () {
				$.showPreloader("正在" + operationDesc + "中...");
				$.ajax({
					url: shelveProductUri,
					type: "GET",
					data: {
						productId: pid,
						enableStatus: enableStatus === 0 ? 1 : 0
					},
					dataType: "json",
					success: function(data, textStatus, jqXHR) {
						console.log("shelveProduct - returned data", data);
						$.hidePreloader();
						if(data.state < 0) { //请求失败
							$.toast(data.msg);
							return;
						}
						loadProductList();
					},
					error: function (xhr, textStatus, errorThrown) {
						printErr(xhr, textStatus, errorThrown);
						$.hidePreloader();
					    var result = getParsedResultFromXhr(xhr);
					    $.toast(result ? result.msg : "服务器出错~");
					}
				});
		    });
		});
		
		//3、给“新增”按钮绑定点击事件处理函数
		$("#add-btn").click(function() {
			location.href = "productoperation?shopId=" + shopId;
		});
	}
	
	function loadProductList() {
		$.showIndicator();
		$.ajax({
			url: getProductListUri,
			type: "GET",
			data: {
				shopId: shopId,
				pageNo: paging.pageNo,
				pageSize: paging.pageSize
			},
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("getProductList - returned data", data);
				if(data.state < 0) { //请求失败
					$.hideIndicator();
					$.toast(data.msg);
					return;
				}
				paging.refreshStatus(data.total);
				//用加载到的商品列表渲染页面
				var rowsHtml = "";
				data.rows.forEach(function(row, index) {
					rowsHtml += "<div class='row row-content'><div class='col-33 text-ellipsis'>"
						+ row.productName + "</div><div class='col-20'>" + row.priority
						+ "</div><div class='col-46'><p class='buttons-row no-margin'><a class='button' href='productoperation?shopId=" + shopId + "&productId=" 
						+ row.productId + "'>编辑</a><a class='button button-shelve' data-pid='" 
						+ row.productId + "' data-pname='" + row.productName  + "' data-enable-status='" + row.enableStatus 
						+ "' href='javascript:void(0);'>" + (row.enableStatus ? "下架" : "上架") + "</a><a class='button' href='#'>预览</a></p></div></div>";
				});
				$contentWrap.html(rowsHtml);
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
});
