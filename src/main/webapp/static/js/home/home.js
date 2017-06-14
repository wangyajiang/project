//============>首页
var Home = (function() {
	var footerMenuBoxId = "footerMenuBox";
	var userLoginBoxId = "userLoginBox";
	var isPc = false;
	var type = 0;//0元云购，
	
	function init() {
		Page.go('main');
		Utils.loadJs(Utils.getDomain() + "/static/js/linescroll.js", 100);
		setTimeout(function() {
			isPc = Adept.isPC();
			addEvent();
		}, 10);
	};
	
	function userLogin() {
		$("#" + userLoginBoxId).addClass("active");
	};
	
	function addEvent() {
		var eventClick = "touchstart";
		if (isPc) {
			eventClick = "click";
		}
		$("#" + footerMenuBoxId + " li").each(function(i, obj) {
			$(this).unbind().bind(eventClick, {index : i}, function(event) {
				switch(event.data.index) {
					case 0 : Page.go('main');break;
					case 1 : Page.go('latestActive');break;
					case 4 : userLogin();break;
				}
			});
		});
	};
	
	return {
		load : init
	};
})();
Home.load();