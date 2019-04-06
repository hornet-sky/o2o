function initPaging(pagingComponentSelector, loadDataListFun) {
	var $pagingComponent = $(pagingComponentSelector);
	if($pagingComponent.length === 0) {
		console.log("未找到待初始化的分页组件");
		return;
	}
	$pagingComponent.addClass("hidden")
		.html('<div class="col-25"><a href="javascript:void(0);" class="button paging-prev-btn">上一页</a></div>'
				+ '<div class="col-50 text-center paging-desc">第 页/共 页</div>'
				+ '<div class="col-25"><a href="javascript:void(0);" class="button paging-next-btn">下一页</a></div>');
	
	var paging = {pageNo: 1, pageSize: 6, totalRows: 0, totalPages: 0};
	
	//绑定上一页、下一页操作处理函数
	var $prevBtn = $pagingComponent.find(".paging-prev-btn").click(function() {
		if(paging.pageNo <= 1) {
			return;
		}
		paging.pageNo--;
		loadDataListFun();
	});
	var $nextBtn = $pagingComponent.find(".paging-next-btn").click(function() {
		if(paging.pageNo >= paging.totalPages) {
			return;
		}
		paging.pageNo++;
		loadDataListFun();
	});
	var $pagingDesc = $pagingComponent.find(".paging-desc");
	paging.refreshStatus = function(totalRows) {
		this.totalRows = totalRows;
		this.totalPages = Math.ceil(this.totalRows / this.pageSize);
		$pagingDesc.text("第" + this.pageNo + "页/共" + this.totalPages + "页");
		if(this.totalPages > 1) {
			$pagingComponent.removeClass("hidden");
		}
	};
	paging.reset = function() {
		$pagingComponent.addClass("hidden");
		paging = {pageNo: 1, pageSize: 6, totalRows: 0, totalPages: 0};
	};
	return paging;
}