$(function() {
	var listMainPageInfoUri = "listmainpageinfo";
	//初始化页面组件
	init();
	
	function init() {
		$.getJSON(listMainPageInfoUri, function(data) {
			console.log("listMainPageInfo - returned data", data);
			if(data.state < 0) { //请求失败
				$.toast(data.msg);
				return;
			}
			var headLineList = data.entity.headLineList;
			var rootCategoryList = data.entity.rootCategoryList;
			var ctxPath = data.entity.resourcesServerContextPath;
			if($.isArray(headLineList)) {
				var headLinesHtml = '';
				headLineList.forEach(function(headLine, index) {
					headLinesHtml += '<div class="swiper-slide"><a href="' + headLine.lineLink + '" external><img src="' + ctxPath + "/" + headLine.lineImg + '" alt="' + headLine.lineName + '"></a></div>';
				});
				$(".swiper-wrapper").html(headLinesHtml);
				//设定轮播图轮换时间为3秒
				$(".swiper-container").swiper({
					autoplay : 3000,
					//用户对轮播图进行操作时，是否自动停止autoplay
					autoplayDisableOnInteraction : false
				});
			}
			if($.isArray(rootCategoryList)) {
				var rootCategorysHtml = '';
				rootCategoryList.forEach(function(category, index) {
					rootCategorysHtml += "<div class='col-50'>"
						+ "  <div class='list-block media-list'>"
						+ "    <ul>"
						+ "      <li>"
						+ "        <a href='#' class='item-link item-content'>"
						+ "          <div class='item-media'><img src='" + ctxPath + "/" + category.shopCategoryImg + "' style='width: 2.2rem;'></div>"
						+ "          <div class='item-inner'>"
						+ "            <div class='item-title-row'>"
						+ "              <div class='item-title'>" + category.shopCategoryName + "</div>"
						+ "            </div>"
						+ "            <div class='item-subtitle'>" + category.shopCategoryDesc + "</div>"
						+ "          </div>"
						+ "        </a>"
						+ "      </li>"
						+ "    </ul>"
						+ "  </div>"
						+ "</div>";
				});
				$(".content > .row").html(rootCategorysHtml);
			}
		});

	}
});