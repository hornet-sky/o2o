$(function() {
	var shopId = getQueryStrValByName("shopId");
	var getAwardListUri = "getawardlist";
	var shelveAwardUri = "shelveaward";
	var addOrEditAwardUri = "addoreditaward";
	var viewAwardUri = "viewaward";
	var paging = initPaging(".row-paging", loadAwardList);
	var $contentWrap = $(".content-wrap");
	//初始化页面组件
	init();
	
	function init() {
		//1、加载奖品列表
		loadAwardList();
		
		//2、绑定上架、下架操作处理函数
		$contentWrap.on("click", "a.button-shelve", function() {
			var $unshelveBtn = $(this);
			var awardId = $unshelveBtn.data("awardId");
			var awardName = $unshelveBtn.data("awardName");
			var enableStatus = $unshelveBtn.data("enableStatus");
			var operationDesc = enableStatus ? "下架" : "上架";
			console.log("awardId=" + awardId + ", awardName=" + awardName + ", enableStatus=" + enableStatus);
			$.confirm("确定" + operationDesc + " " + awardName + " 吗?", function () {
				$.showPreloader("正在" + operationDesc + "中...");
				$.ajax({
					url: shelveAwardUri,
					type: "GET",
					data: {
						awardId: awardId,
						enableStatus: enableStatus === 0 ? 1 : 0
					},
					dataType: "json",
					success: function(data, textStatus, jqXHR) {
						console.log("shelveAward - returned data", data);
						$.hidePreloader();
						if(data.state < 0) { //请求失败
							$.toast(data.msg);
							return;
						}
						loadAwardList();
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
			location.href = "awardoperation?shopId=" + shopId;
		});
		
		//4、给“返回”按钮绑定点击事件处理函数
		$("#return-btn").click(function() {
			location.href = "shopmanagement?shopId=" + shopId;
		});
	}
	
	function loadAwardList() {
		$.showIndicator();
		$.ajax({
			url: getAwardListUri,
			type: "GET",
			data: {
				shopId: shopId,
				pageNo: paging.pageNo,
				pageSize: paging.pageSize
			},
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("getAwardList - returned data", data);
				if(data.state < 0) { //请求失败
					$.hideIndicator();
					$.toast(data.msg);
					return;
				}
				paging.refreshStatus(data.total);
				//用加载到的奖品列表渲染页面
				var rowsHtml = "";
				data.rows.forEach(function(row, index) {
					rowsHtml += "<div class='row row-content'><div class='col-33 text-ellipsis'>"
						+ row.awardName + "</div><div class='col-20'>" + (row.points == null ? 0 : row.points)
						+ "</div><div class='col-46'><p class='buttons-row no-margin'><a class='button' href='awardoperation?shopId=" + shopId + "&awardId=" 
						+ row.awardId + "'>编辑</a><a class='button button-shelve' data-award-id='" 
						+ row.awardId + "' data-award-name='" + row.awardName  + "' data-enable-status='" + row.enableStatus 
						+ "' href='javascript:void(0);'>" + (row.enableStatus ? "下架" : "上架") 
						+ "</a><a class='button' href='awarddetail?awardId=" + row.awardId + "'>预览</a></p></div></div>";
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
