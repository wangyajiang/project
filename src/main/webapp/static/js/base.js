var Base = (function() {
	var domain = "";
	var proName = 'panicbuy';
	
	function start() {
		domain = location.origin + "/" + proName;
		MyAjax.load();
		Page.load();
		$("#menuBarBox li:gt(0)").remove();
		MyAjax.post("/getItemTypes.do", null, nav);
	};
	
	function nav(json, error) {
		var menuBarBox = $("#menuBarBox");
		if (error) {
			alert("系统错误" + error.message);
			return;
		}
		var list = json.data; 
		for (var i = 0; i < list.length; i ++) {
			var item = list[i];
			var li = $("<li>");
			li.attr("data-type", item.id).append($("<a>").attr("href", "javascript:void(0)").append(item.typeName));
			li.unbind().bind("click", {item : item}, function(event) {
				var item = event.data.item;
				Page.goByType('itemTypes', item.typeId, function() {
					Active.load(item);
				});
				
				return false;
			});
			menuBarBox.append(li);
		}
	};
	
	return {
		getDomain : function() {
			return domain;
		},
		load : start,
		nav : nav
	}
})();

var ScrollPage = (function() {
	var myScroll = null;
	var size = 50;
	var isOutTop = false;
	var isOutBottom = false;
	var refresh = null;
	var next = null;
	var scrollCallback = null;
	
	function myScrollEnd(e) {
		console.log(e);
	};
	
	function downRefresh() {
		isOutTop = false;
		console.log("下拉刷新");
		if (refresh) {
			refresh();
		}
	};
	
	function goNext() {
		isOutBottom = false;
		console.log("上拉翻页");
		if (next) {
			next();
		}
	};
	
	function scrollEnd() {
	    if (myScroll.directionY == 1 && isOutBottom) {
	    	goNext();
	    } else if(myScroll.directionY == -1 && isOutTop) {
	    	downRefresh();
	    }
	};
	
	function scroll() {
		if(myScroll.y > size) {
			isOutTop = true;
		} else if (myScroll.y < 0) {
			isOutTop = false;
		}
		var sub = -myScroll.y + myScroll.maxScrollY;
		if (sub > 20) {
			isOutBottom = true;
		} else if (sub < 0) {
			isOutBottom = false;
		}
		if (scrollCallback) {
			scrollCallback(myScroll);
		}
	};
	
	function init(o) {
		myScroll = o;
		myScroll.on("scroll", scroll);
		myScroll.on("scrollEnd", scrollEnd);
	};
	
	return {
		load : init,
		clear : function() {
			refresh = null;
			next = null;
//			scrollCallback = null;
		},
		refresh : function(cb) {
			refresh = cb;
		},
		next : function(cb) {
			next = cb;
		},
		scroll : function(cb) {
			scrollCallback = cb;
		}
	};
})();
var Page = (function() {
	var mainContentId = "container";
	var footerMenuBoxId = "footerMenuBox";
	var initPages = {};
	var cookiePage = {};
	var conditionParam = {};
	var myScroll = null;
	
	function init() {
		initPages['home'] = {menu : Config.type.ZERO, page : "/pages/home.html", fid : "home"};
		initPages['main'] = {menu : Config.type.ZERO, page : "/pages/container/main.html", fid : "main"};
		initPages['itemTypes'] = {menu : Config.type.ZERO, page : "/pages/container/itemActives.html", fid : "itemTypes"};
		initPages['latestActive'] = {menu : Config.type.ONE, page : "/pages/container/latestActive.html", fid : "latestActive"};
		initPages['404'] = {menu : 404, page : "/pages/error/404.html", fid : "home"};
	};
	
	function switchMenuFooter(index) {
		var box = $("#" + footerMenuBoxId + " li:eq(" + index + ")");
		if (box.length > 0) {
			$("#" + footerMenuBoxId + " li.active").removeClass();
			box.addClass("active");
		}
		if (index > 0) {
			$("#" + mainContentId).addClass("no-header");
			$('.home-header').hide();
		} else {
			$("#" + mainContentId).removeClass("no-header");
			$('.home-header').show();
		}
	};
	
	function go(key, type, cb) {
		var item = initPages[key];
		var containerBox = $("#" + mainContentId);
		containerBox.hide();
		containerBox.children().hide();
		if (type != null) {
			if (initPages[key + type] == null) {
				initPages[key + type] = {menu : item.menu, page : item.page, fid : item.fid + type};
			}
			item = initPages[key + type];
		}
		$("#" + item.fid).css("transform", "translate(0px, 0px)").show();
		ScrollPage.clear();
		if (typeof(MainActives) != 'undefined') {
			MainActives.reload();
		}
		switchMenuFooter(item.menu);
		if (cookiePage[item.fid]) {
			containerBox.show();
			if (cb) {
				cb();
			}
			Page.resizePage();
			return;
		}
		MyAjax.post("/getPage.do", {page : item.page}, function(json, error) {
			if (error) {
				if (error.code == 110) {
					Page.go('404');
				}
				return
			}
			var containerBox = $("#" + mainContentId);
			var box = $(json.data);
			box.attr("id", item.fid);
			containerBox.append(box);
			containerBox.fadeIn(150);
			cookiePage[item.fid] = item.fid;
			if (cb) {
				cb();
			}
			Page.resizePage();
		});
	};
	
	return {
		load : function() {
			init();
		},
		go : function(key, condition) {
			if (key == null || key == '') {
				go('home');
			} else if (key == 'home') {
				window.location.href = domain + initPages[key].page;
			} else {
				conditionParam = condition || {};
				go(key);
			}
		},
		goByType : function(key, type, cb) {
			if (key != 'itemTypes') {
				Page.go(key, type);
				return;
			}
			go(key, type, cb);
		},
		getParam : function(key) {
			return conditionParam[key] || null;
		},
		resizePage : function() {
			if (myScroll != null) {
				myScroll.destroy();
			}
			myScroll = new IScroll('#container', {probeType: 3});
			ScrollPage.load(myScroll);
		},
		refreshScroll: function() {
			myScroll.refresh();
		},
		getMyScroll : function() {
			return myScroll;
		}
	};
})();
var Adept = (function() {
	var isPc = false;
	function isPC() {
	    var userAgentInfo = navigator.userAgent;
	    var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
	    var flag = true;
	    for (var v = 0; v < Agents.length; v++) {
	        if (userAgentInfo.indexOf(Agents[v]) > 0) {
	            flag = false;
	            break;
	        }
	    }
	    isPc = flag;
	    return flag;
	};

	function init() {
		var x = 125 * $(window).width() / 320;
		var isPc =  isPC();
		if (isPC && x > 200) {
			x = 200;
		}
		$("html").attr("style", "font-size:" + x + "%;");
		if (isPC) {
			var w = $(window).width();
			var _w = $(".outer-content:first").width();
			var l = (w - _w)/2;
			$(".outer-content:first").css("left", l + "px");
			$(".outer-content:first").css("box-shadow", "1px 1px 5px RGBA(0,0,0,.5)");
		}
	};
	return {
		load : init,
		isPC : function() {
			return isPc;
		}
	};
})();
Adept.load();
Base.load();
$(window).resize(function() {
	Adept.load();
});
var Loop = (function() {
	var timeLong = 10;
	
	Utils.cache.globalTimer = "";
	
	function start(options) {
		Utils.cache.timer_index = 0;
		run();
	};
	
	function run() {
		clearInterval(Utils.cache.globalTimer);
		Utils.cache.globalTimer = setInterval(init, timeLong);
	};
	
	function init() {
		Utils.cache.timer_index ++;
		Utils.cache.nowTime = Utils.cache.nowTime + timeLong;
		if (Utils.cache.timer_cbs == null) {
			return;
		}
		for (var i = 0; i < Utils.cache.timer_cbs.length; i ++) {
			if (Utils.cache.timer_cbs[i]) {
				Utils.cache.timer_cbs[i](i, Utils.cache.timer_index, Utils.cache.nowTime);
			}
		}
	};
	
	function add(cb) {
		if (Utils.cache.timer_cbs == null) {
			Utils.cache.timer_cbs = [];
		}
		if (cb) {
			Utils.cache.timer_cbs.push(cb);
		}
	};
	
	function remove(id) {
		if (Utils.cache.timer_cbs[id]) {
			Utils.cache.timer_cbs[id] = null;
		}
	};
	
	return {
		run : start,
		add : add,
		remove : remove
	};
})();
Loop.run();