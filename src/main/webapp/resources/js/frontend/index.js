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
			var user = data.entity.user;
			var account = data.entity.account;
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
						+ "        <a href='shoplist?parentShopCategoryId=" + category.shopCategoryId + "' class='item-link item-content' external>"
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
			var $rightPanel = $(".panel-right > .content-block").eq(1);
			if(!user) { //未登录
				$rightPanel.append('<p><a href="../local/login" external>登录系统</a></p>');
				return;
			}
			//已登录
			var btnsHtml = '';
			if(!account) { //已登录但没有本地账号
				btnsHtml += '<p><a href="../local/auth" external>绑定本地账号</a></p>';
			}
			btnsHtml += '<p><a href="javascript:$.alert(\'消费记录功能正在建设中...\');" external>消费记录</a></p>';
			btnsHtml += '<p><a href="javascript:$.alert(\'我的积分功能正在建设中...\');" external>我的积分</a></p>';
			btnsHtml += '<p><a href="javascript:$.alert(\'兑换记录功能正在建设中...\');" external>兑换记录</a></p>';
			if(account) { //已登录并且绑定了本地账号
				btnsHtml += '<p><a href="../local/changepassword?account=' + account + '" external>修改密码</a></p>';
				btnsHtml += '<p><a href="../local/logout" external>退出系统</a></p>';
			}
			$rightPanel.html(btnsHtml);
		});
	}
	
	var $barTab = $(".bar-tab").on("click", ".tab-item", function() {
		$barTab.find(".active").removeClass("active");
		$(this).addClass("active");
	});
});