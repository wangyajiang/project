var Login = (function() {
	var loginBoxId = "loginBox";
	var loginBtnId = "loginBtn";
	
	function resizeWindow() {
		var win_width = $(window).width();
		var win_height = $(window).height();
		var loginBox = $("#" + loginBoxId);
		var login_width = loginBox.width();
		var login_height = loginBox.height();
		loginBox.css("left", (win_width - login_width) / 2 + "px");
		loginBox.css("top", (win_height - login_height) / 2 + "px");
	};
	
	function addEvent() {
		$(window).resize(function() {
			resizeWindow();
		});
		$("#" + loginBtnId).unbind().bind("click", function() {
			Page.go('main');
		});
	};
	
	function init() {
		resizeWindow();
		addEvent();
		AniBase.addAnis($("#" + loginBoxId));
	};
	
	return {
		load : init
	};
})();
Login.load();