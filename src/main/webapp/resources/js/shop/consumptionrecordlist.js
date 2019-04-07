$(function() {
	var shopId = getQueryStrValByName("shopId");
	var loadConsumptionRecordListUri = "loadconsumptionrecordlist";
	var loadStatisticsInfoForChartUri = "loadstatisticsinfoforchart";
	var paging = initPaging(".row-paging", loadConsumptionRecordList);
	var $contentWrap = $(".content-wrap");
	var $searchIpt = $("#search");
	//初始化页面组件
	init();
	
	function init() {
		//1、加载列表
		loadConsumptionRecordList();
		
		//2、加载图表
		loadStatisticsInfoForChart();

		//3、给“返回”按钮绑定点击事件处理函数
		$("#return-btn").click(function() {
			location.href = "shopmanagement?shopId=" + shopId;
		});
		
		//4、搜索栏 - 点击软键盘的“搜索”按钮
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
		
		//5、搜索栏 - 点击右侧的“取消”按钮
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
				console.log("loadConsumptionRecordList - returned data", data);
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
	
	function loadStatisticsInfoForChart() {
		$.ajax({
			url: loadStatisticsInfoForChartUri,
			type: "GET",
			data: {
				shopId: shopId
			},
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("loadStatisticsInfoForChart - returned data", data);
				if(data.state < 0) { //请求失败
					$.toast(data.msg);
					return;
				}
				var width = $("#tab3").width();
				var colors = ['#a5c2d5', '#cbab4f', '#76a871', '#a56f8f', '#c12c44', '#9f7961', '#6f83a5', '#ff7f00', '#38b0de', '#b87333'];
				var salesVolumeForThreeDays = data.entityList;
				//定义数据
				var datas = new Array();
				var data;
				var dailySalesVolumes;
				var salesVolume;
				var maxSalesVolume = 0;
				for(var i = 0, len = salesVolumeForThreeDays.length; i < len; i++) {
					dailySalesVolumes = salesVolumeForThreeDays[i];
					data = new Array();
					for(var j = 0, size = dailySalesVolumes.length; j < size; j++) {
						salesVolume = dailySalesVolumes[j];
						data.push({
							name: salesVolume.product_name,
							value: salesVolume.count,
							color: colors[j]
						});
						if(j === 0 && salesVolume.count > maxSalesVolume) {
							maxSalesVolume = salesVolume.count;
						}
					}
					datas.push(data);
				}
				for(var i = 0, len = datas.length; i < len; i++) {
					data = datas[i];
					var chart = new iChart.Column2D({
						render: 'canvasDiv' + (i + 1),//渲染的Dom目标,canvasDiv为Dom的ID
						data: data,//绑定数据
						title: '销量TOP10',//设置标题
						width: width,//设置宽度，默认单位为px
						height: 400,//设置高度，默认单位为px
						shadow: true,//激活阴影
						shadow_color: '#c7c7c7',//设置阴影颜色
						coordinate: {//配置自定义坐标轴
							scale: [{//配置自定义值轴
								 position: 'left',//配置左值轴	
								 start_scale: 0,//设置开始刻度为0
								 end_scale: maxSalesVolume,//设置结束刻度为26
								 scale_space: 1,//设置刻度间距
								 listeners: {//配置事件
									parseText: function(t, x, y) {//设置解析值轴文本
										return {text: t};
									}
								}
							}]
						}
					});
					//调用绘图方法开始绘图
					chart.draw();
				}
			},
			error: function (xhr, textStatus, errorThrown) {
				printErr(xhr, textStatus, errorThrown);
			    var result = getParsedResultFromXhr(xhr);
			    $.toast(result ? result.msg : "服务器出错~");
			}
		});
	}
});
