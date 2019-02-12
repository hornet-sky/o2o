$(function() {
	var shopId = getQueryStrValByName("shopId");
	var getProductCategoryListUri = "getproductcategorylist";
	var delProductCategoryUri = "delproductcategory";
	var batchInsertProductCategoryUri = "batchinsertproductcategory";
	var $productCategoryWrap = $(".product-category-wrap");
	//初始化页面组件
	init();
	
	function init() {
		//1、加载商品类别列表
		loadProductCategoryList();
		
		//2、绑定删除事件处理函数
		$productCategoryWrap.on("click", "a.button-del", function() {
			var $delBtn = $(this); //注意this是“删除”按钮而不是外层的wrap组件
			var cid = $delBtn.data("cid");
			var cname = $delBtn.data("cname");
			console.log("cid=" + cid + ", cname=" + cname);
			if(!cid) { //直接删除新增的临时条目，不需要询问。
				$delBtn.parents(".row-product-category").remove();
				return;
			}
			$.confirm('确定删除 ' + cname + ' 吗?', function () {
				$.showPreloader('正在删除中...');
				$.ajax({
					url: delProductCategoryUri,
					type: "GET",
					data: {productCategoryId: cid},
					dataType: "json",
					success: function(data, textStatus, jqXHR) {
						console.log("delProductCategory - returned data", data);
						$.hidePreloader();
						if(data.state < 0) { //请求失败
							$.toast(data.msg);
							return;
						}
						$delBtn.parents(".row-product-category").remove();
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
		
		//3、绑定新增事件处理函数
		$("#add-btn").click(function() {
			var rowHtml = "<div class='row row-product-category temp'>" 
				+ "<div class='col-40'><input class='category-input category' type='text' placeholder='类别名'></div>" 
				+ "<div class='col-40'><input class='category-input priority' type='number' placeholder='优先级'></div>"
				+ "<div class='col-20'><a class='button button-danger button-del'>删除</a></div></div>";
			$productCategoryWrap.append(rowHtml);
		});
		
		//4、绑定提交事件处理函数
		$("#submit-btn").click(function() {
			var categoryArr = new Array();
			var $item;
			var category, categoryName, priority;
			$.each($(".temp"), function(i, item) {
				$item = $(item);
				categoryName = $item.find(".category").val();
				priority = $item.find(".priority").val();
				if(categoryName) {
					category = {
						productCategoryName: categoryName,
						priority: priority || 0
					};
					categoryArr.push(category);
				}
			});
			if(categoryArr.length === 0) {
				$.toast("请先新增商品类别");
				return;
			}
			$.showPreloader('提交中...');
			$.ajax({
				url: batchInsertProductCategoryUri,
				type: "POST",
				data: JSON.stringify(categoryArr),
				contentType: "application/json",
				dataType: "json",
				success: function(data, textStatus, jqXHR) {
					console.log("batchInsertProductCategory - returned data", data);
					$.hidePreloader();
					if(data.state < 0) { //请求失败
						$.toast(data.msg);
						return;
					}
					//reload商品类别列表
					loadProductCategoryList();
				},
				error: function (xhr, textStatus, errorThrown) {
					printErr(xhr, textStatus, errorThrown);
					$.hidePreloader();
				    var result = getParsedResultFromXhr(xhr);
				    $.toast(result ? result.msg : "服务器出错~");
				}
			});
		});
	}
	
	function loadProductCategoryList() {
		$.ajax({
			url: getProductCategoryListUri,
			type: "GET",
			data: {
				shopId: shopId,
				pageNo: 1,
				pageSize: 10
			},
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				console.log("getProductCategoryList - returned data", data);
				if(data.state < 0) { //请求失败
					$.toast(data.msg);
					return;
				}
				//用加载到的商品类别列表渲染页面
				var rowsHtml = "";
				data.rows.forEach(function(row, index) {
					rowsHtml += "<div class='row row-product-category'><div class='col-40 product-category-name'>"
						+ row.productCategoryName + "</div><div class='col-40'>" + row.priority
						+ "</div><div class='col-20'><a class='button button-danger button-del' data-cid='" + row.productCategoryId + "' data-cname='" + row.productCategoryName + "' href='javascript:void(0);'>删除</a></div></div>";
				});
				$productCategoryWrap.html(rowsHtml);
			},
			error: function (xhr, textStatus, errorThrown) {
				printErr(xhr, textStatus, errorThrown);
			    var result = getParsedResultFromXhr(xhr);
			    $.toast(result ? result.msg : "服务器出错~");
			}
		});
	}
});
