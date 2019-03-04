/**
 * 构建底部的工具栏（首页、收藏、购物车、我）
 * @param config 配置参数：
 *                       pageSelector   page组件的选择器，默认是".page"。
 *                       activeIndex    要激活的按钮的序号，首页0 收藏1 购物车2 我3，被激活的按钮显示蓝色，未被激活的按钮显示灰色。
 *                       initCartBadge  是否需要加载购物车商品数，true表示加载购物车商品数。
 * @returns undefined
 */
function constructBottomToolbar(config) {
	var pageSelector = config && config.pageSelector || ".page";
	var activeIndex = config && config.activeIndex;
	var initCartBadge = config && config.initCartBadge;
	//1、渲染工具栏
	var $bottomToolbar = $('<nav class="bar bar-tab bottom-toolbar"><a class="tab-item external" href="index"><span class="icon icon-home"></span><span class="tab-label">首页</span></a><a class="tab-item external" href="javascript:void(0);"><span class="icon icon-star"></span><span class="tab-label">收藏</span></a><a class="tab-item external" href="javascript:void(0);"><span class="icon icon-cart"></span><span class="tab-label">购物车</span></a><a class="tab-item external open-panel" href="javascript:void(0);" data-panel=".panel-right"><span class="icon icon-me"></span><span class="tab-label">我</span></a></nav>');
	$(pageSelector).append($bottomToolbar);
	//2、激活指定序号的按钮
	if(activeIndex != undefined && activeIndex != null) {
		$bottomToolbar.find("a.tab-item").eq(activeIndex).addClass("active");
	}
	//3、给按钮绑定点击事件处理函数，点击按钮后立即激活按钮
	$bottomToolbar.on("click", "a.tab-item", function() {
		$bottomToolbar.find(".active").removeClass("active");
		$(this).addClass("active");
	});
	//4、加载购物车商品数
	if(initCartBadge) {
		//TODO 后期再实现加载购物车商品数功能
		//<span class="badge">2</span>
	}
}

/**
 * 构建右侧面板
 * @param config 配置参数：
 *                       containerSelector   放置右侧面板的容器，默认是"body"。
 *                       getLoginInfoUri     加载用户登录信息的Uri，默认是"getlogininfo"。
 * @returns undefined
 */
function constructRightPanel(config) {
	var containerSelector = config && config.containerSelector || "body";
	var getLoginInfoUri = config && config.getLoginInfoUri || "getlogininfo";
	//1、渲染右侧面板
	var $rightPanel = $('<div class="panel-overlay"></div><div class="panel panel-right panel-reveal"><div class="content-block"><p><a class="close-panel" href="javascript:void(0);" ><span class="icon icon-left"></span>返回</a></p></div><div class="content-block menu-box"></div></div>');
	$(containerSelector).append($rightPanel);
	//2、加载用户登录信息，并根据加载的结果初始化右侧面板
	$.getJSON(getLoginInfoUri, function(data) {
		console.log("getlogininfo - returned data", data);
		if(data.state < 0) { //请求失败
			$.toast(data.msg);
			return;
		}
		var user = data.entity.user;
		var account = data.entity.account;
		var $menuBox = $(".menu-box");
		if(!user) { //未登录
			$menuBox.append('<p><a href="../local/login" external>登录系统</a></p>');
		} else { //已登录
			var btnsHtml = '';
			if(!account) { //已登录但没有本地账号
				btnsHtml += '<p><a href="../local/auth" external>绑定本地账号</a></p>';
			}
			btnsHtml += '<p><a href="javascript:$.alert(\'消费记录功能正在建设中...\');" external>消费记录</a></p><p><a href="javascript:$.alert(\'我的积分功能正在建设中...\');" external>我的积分</a></p><p><a href="javascript:$.alert(\'兑换记录功能正在建设中...\');" external>兑换记录</a></p>';
			if(account) { //已登录并且绑定了本地账号
				btnsHtml += '<p><a href="../local/changepassword?account=' + account + '" external>修改密码</a></p><p><a href="../local/logout" external>退出系统</a></p>';
			}
			$menuBox.html(btnsHtml);
		}
	});
}