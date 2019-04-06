$(function() {
	var shopId = getQueryStrValByName("shopId");
	var loadConsumerPointsListUri = "loadconsumerpointslist";
	var paging = initPaging(".row-paging", loadConsumerPointsList);
	var $contentWrap = $(".content-wrap");
	var $searchIpt = $("#search");
	//初始化页面组件
	init();
	
	function init() {
		//1、加载列表
		loadConsumerPointsList();

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
				loadConsumerPointsList();
				return;
			}
			t = setTimeout(function() {
				paging.reset();
				loadConsumerPointsList();
			}, 2000);
		});
		//4、搜索栏 - 点击右侧的“取消”按钮
		$(".searchbar > a.searchbar-cancel").on("click", function() {
			$searchIpt.val("");
			paging.reset();
			loadConsumerPointsList();
		});
	}
	
	function loadConsumerPointsList() {
		$.showIndicator();
		$.ajax({
			url: loadConsumerPointsListUri,
			type: "GET",
			data: {
				shopId: shopId,
				searchKey: $searchIpt.val() || null,
				pageNo: paging.pageNo,
				pageSize: paging.pageSize
			},
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("loadConsumerPointsListUri - returned data", data);
				if(data.state < 0) { //请求失败
					$.hideIndicator();
					$.toast(data.msg);
					return;
				}
				paging.refreshStatus(data.total);
				//用加载到的列表渲染页面
				var rowsHtml = "";
				data.rows.forEach(function(row, index) {
					rowsHtml += "<div class='row row-content'><div class='col-50 text-ellipsis'>"
						+ row.consumer_name + "</div><div class='col-50'>" + row.total_points
						+ "</div></div>";
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
