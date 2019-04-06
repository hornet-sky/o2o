$(function() {
	var shopId = getQueryStrValByName("shopId");
	var loadConsumptionRecordListUri = "loadconsumptionrecordlist";
	var paging = initPaging(".row-paging", loadConsumptionRecordList);
	var $contentWrap = $(".content-wrap");
	var $searchIpt = $("#search");
	//初始化页面组件
	init();
	
	function init() {
		//1、加载列表
		loadConsumptionRecordList();

		//2、给“返回”按钮绑定点击事件处理函数
		$("#return-btn").click(function() {
			location.href = "shopmanagement?shopId=" + shopId;
		});
		
		//3、搜索栏 - 点击软键盘的“搜索”按钮
		var t;
		$searchIpt.on("keyup", function(e) {
			console.log("e.keyCode", e.keyCode);
			clearTimeout(t);
			if(e.keyCode == '13') {
				e.preventDefault();
				paging.reset();
				loadConsumptionRecordList();
				return;
			}
			t = setTimeout(function() {
				paging.reset();
				loadConsumptionRecordList();
			}, 2000);
		});
		//4、搜索栏 - 点击右侧的“取消”按钮
		$(".searchbar > a.searchbar-cancel").on("click", function() {
			$searchIpt.val("");
			paging.reset();
			loadConsumptionRecordList();
		});
	}
	
	function loadConsumptionRecordList() {
		$.showIndicator();
		$.ajax({
			url: loadConsumptionRecordListUri,
			type: "GET",
			data: {
				shopId: shopId,
				searchKey: $searchIpt.val() || null,
				pageNo: paging.pageNo,
				pageSize: paging.pageSize
			},
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("loadConsumptionRecordListUri - returned data", data);
				if(data.state < 0) { //请求失败
					$.hideIndicator();
					$.toast(data.msg);
					return;
				}
				paging.refreshStatus(data.total);
				//用加载到的列表渲染页面
				var rowsHtml = "";
				data.rows.forEach(function(row, index) {
					rowsHtml += "<div class='row row-content'><div class='col-33 text-ellipsis'>"
						+ row.productName + "</div><div class='col-33'>" + new Date(row.createTime).format("yyyy-MM-dd HH:mm:ss")
						+ "</div><div class='col-33 text-ellipsis'>" + row.consumerName + "</div></div>";
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
