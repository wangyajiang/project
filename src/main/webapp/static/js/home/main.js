//============>模块：焦点图
var FocusMaps = (function() {
	var focusMapBoxId = "focusMapBox";
	var cache = {};
	
	function show(json, error) {
		var box = $("#" + focusMapBoxId).empty();
		if (error) {
			return;
		}
		cache.focusMaps = json;
		var arr = [];
		var list = json.data;
		for (var i = 0; i < list.length; i ++) {
			var item = list[i];
			var img = $("<img>");
			img.attr("src", item.url);
			if (item.name) {
				img.attr("alt", item.name);
			}
			 var dom = document.createElement('img');
             dom.src = item.url;
             dom.alt = item.name;
			arr[i] = {content : dom};
		}
	    var slider = new iSlider({
	        dom: document.getElementById(focusMapBoxId),
	        data: arr,
	        isAutoplay: 1,
	        duration: 3000,
	        isLooping: 1,
	        isOverspread: 1,
	        animateTime: 800,
	        plugins: [['dot', {locate:document.getElementById('focusMapDotBox')}]]
	    });
	};
	
	function search() {
		if (cache.focusMaps) {
			show(cache.focusMaps);
			return;
		}
		MyAjax.post("/home/focusMaps.do", null, show);
	};
	
	function init() {
		search();
	};
	
	return {
		load : init
	};
})();

//============>模块：最新揭晓
var LatestAnnouncement = (function() {
	var moreId = "newPublishMore";
	var boxId = "newPublishItemsBox";
	var cache = {};
	
	var page = 1;
	var pageSize = 3;
	var isPc = false;
	
	function formatTime(countDown) {
		if (countDown <= 0) {
			return "已公布";
		}
		var str = "";
        var oDay = parseInt(countDown / 86400000);
        var oHours = parseInt((countDown - oDay * 86400000) / (3600000));
        var oMinutes = parseInt((countDown -  oDay * 86400000 - oHours * 3600000) / 60000);
        var oSeconds = parseInt((countDown -  oDay * 86400000 - oHours * 3600000 - oMinutes * 60000) / 1000);
        var mill = parseInt((countDown -  oDay * 86400000 - oHours * 3600000 - oMinutes * 60000 - oSeconds * 1000) / 10);
        oHours = oHours < 10 ? "0" + oHours : oHours;
        oMinutes = oMinutes < 10 ? "0" + oMinutes : oMinutes;
        oSeconds = oSeconds < 10 ? "0" + oSeconds : oSeconds;
        mill = mill < 10 ? "0" + mill : mill;
        if (countDown > 86400000) {//天
        	str = oDay + "天 " + oHours + ":" + oMinutes;
        } else if (countDown > 3600000) {//时
        	 str = oHours + ":" + oMinutes + ":" + oSeconds;
        } else {
        	 str = oMinutes + ":" + oSeconds + ":" + mill;
        }
		return str;
	};
	
	function timerLoop(n, index, nowTime) {
		var boxs = $("#" + boxId + ">.publish-box")
		if (boxs.length == 0) {
			return;
		}
		boxs.each(function() {
			var box = $(this);
			var item = box.data("item");
			if (item) {
				var publishTime = item.publishTime;
				var subTime = item.publishTime - nowTime;
				var rt = null;
				if (subTime <= 0) {
					box.removeData("item");
					rt = "已公布";
				} else {
					rt = formatTime(subTime);
				}
				box.find(".item-time").empty().append(rt);
			}
		});
	};
	
	function createBox(item) {
		var box = $("<div>");
		var logo = $("<div>");
		var title = $("<div>");
		var time = $("<div>");
		var img = $("<img>");
		
		logo.addClass("item-logo");
		title.addClass("item-title");
		time.addClass("item-time");
		
		img.attr("src", item.itemPicUrl);
		logo.append(img);
		title.append(item.activeName);
		time.append("04:59:59");
		
		box.addClass("publish-box");
		box.append(logo);
		box.append(title);
		box.append(time);
		box.data("item", item);
		return box;
	};
	
	function show(json, error) {
		var box = $("#" + boxId).empty();
		if (error) {
			box.append($("<div>").addClass("item-active-error").arrpend(error.message));
			return;
		}
		if (json.data.list == null || json.data.list.length == 0) {
			box.append(Utils.noData("暂无记录！"));
			return;
		}
		cache.json = json.data;
		var paging = json.data;
		var list = paging.list;
		var len = list.length;
		for (var i = 0; i < len; i ++) {
			var item = list[i];
			box.append(createBox(item));
		}
	};
	
	function search() {
		if (cache.json) {
			show(cache.json);
			return;
		}
		var data = {};
		data.page = page;
		data.pageSize = pageSize;
		data.status = 5;
		data.sort = "publish_time";
		data.desc = false;
		MyAjax.post("/active/getItemsActives.do", data, show);
	};
	
	function addEvent() {
		var eventClick = "touchstart";
		if (isPc) {
			eventClick = "click";
		}
		console.log(eventClick);
		$("#" + moreId).unbind().bind(eventClick, function() {
			Page.go('latestActive');
			return false;
		});
	};
	
	function init() {
		search();
		Loop.add(timerLoop);
		setTimeout(function() {
			isPc = Adept.isPC();
			addEvent();
		}, 100);
	};
	
	return {
		load : init
	};
})();

//============>模块：公告
var Notice = (function() {
	var noticeBoxId = "noticeBox";
	
	function init() {
		$("#" + noticeBoxId).goSlide();
	};
	
	return {
		load : init
	};
})();
//============>模块：活动
var MainActives = (function() {
	var boxId = "itemActiveBoxs";
	var itemActiveMenuId = "itemActiveMenu";
	var popularityBtnId = "popularityBtn";//人气
	var surplusBtnId = "surplusBtn";//剩余
	var newestBtnId = "newestBtn";//最新
	var totalRequiredBtnId = "totalRequiredBtn";//总需
	var header = 0;
	var cache = {};
	
	var page = 1;
	var pageSize = 10;
	var sort = "current_num";
	var desc = true;
	var isPc = false;
	
	function createBox(item) {
		var box = $("<div>");
		var logo = $("<div>");
		var title = $("<div>");
		var openBx = $("<div>");
		var btn = $("<a>");
		var img = $("<img>");
		
		box.addClass("item-box");
		
		logo.addClass("item-logo");
		title.addClass("item-title");
		openBx.addClass("open-box");
		btn.addClass("sui-btn small");
		
		img.attr("src", item.itemPicUrl);
		logo.append(img);
		title.append(item.activeName);
		
		var p = parseInt(item.currentNum * 100 / item.totalNum);
		openBx.append($("<div>").addClass("name").append("开奖进度").append($("<span>").append(p).append("%")));
		openBx.append($("<div>").addClass("sui-process striped active").append($("<div>").addClass("bar").css("width", p + "%")));
		var eventClick = "touchstart";
		if (isPc) {
			eventClick = "click";
		}
		btn.append("加入清单").unbind().bind(eventClick, function() {
			alert(1);
		});
		
		box.append(logo);
		box.append(title);
		box.append(openBx);
		box.append(btn);
		box.data("item", item);
		return box;
	};
	
	function show(json, error) {
		var box = $("#" + boxId).empty();
		if (error) {
			box.append($("<div>").addClass("item-active-error").arrpend(error.message));
			return;
		}
		if (json.data.list == null || json.data.list.length == 0) {
			box.append(Utils.noData("暂无记录！"));
			return;
		}
		var paging = json.data;
		var list = paging.list;
		var len = list == null ? 0 : list.length;
		if (cache[sort]) {
			cache[sort].page = page;
			cache[sort].pages = paging.pages;
			cache[sort].json.data.list = cache[sort].json.data.list || [];
			for (var i = 0; i < len; i ++) {
				var item = list[i];
				cache[sort].json.data.list.push(item);
			}
			list = cache[sort].json.data.list;
			len = list.length;
		} else {
			cache[sort] = {page : page, pages : paging.pages, json : json};
		}
		for (var i = 0; i < len; i ++) {
			var item = list[i];
			box.append(createBox(item));
		}
		if (page == paging.pages) {
			box.append(Utils.endBox("已到底部"));
		} else {
			box.append(Utils.loadingNext());
		}
		Page.refreshScroll();
		Page.refreshScroll();
	};
	
	function addEvent() {
		var eventClick = "touchstart";
		if (isPc) {
			eventClick = "click";
		}
		$("#" + popularityBtnId).unbind().bind(eventClick, function() {
			$(this).parent().find(".active").removeClass("active");
			$(this).addClass("active");
			sort = "current_num";
			desc = true;
			search();
		});
		$("#" + surplusBtnId).unbind().bind(eventClick, function() {
			$(this).parent().find(".active").removeClass("active");
			$(this).addClass("active");
			sort = "surplus_num";
			desc = false;
			search();	
		});
		$("#" + newestBtnId).unbind().bind(eventClick, function() {
			$(this).parent().find(".active").removeClass("active");
			$(this).addClass("active");
			sort = "start_time";
			desc = true;
			search();
		});
		$("#" + totalRequiredBtnId).unbind().bind(eventClick, function() {
			$(this).parent().find(".active").removeClass("active");
			$(this).addClass("active");
			sort = "total_num";
			desc = false;
			search();
		});
	};
	
	function scroll(myScroll) {
		if (!$("#main").is(":visible")) {
			return;
		}
		var box = $("#" + itemActiveMenuId);
		var commBox = $("#commonBox");
		var top = $("#" + itemActiveMenuId + "Box").offset().top;
		if (top < header) {
			if (commBox.hasClass("menu-sec-head")) {
				return;
			}
			commBox.addClass("menu-sec-head").empty().append(box);
		} else {
			if ($("#" + itemActiveMenuId + "Box").children().length > 0) {
				return;
			}
			$("#" + itemActiveMenuId + "Box").empty().append(box);
			commBox.removeClass("menu-sec-head");
		}
	};
	
	function reload() {
		var box = $("#" + itemActiveMenuId);
		if (box.is(":visible")) {
			$("#commonBox").removeClass("menu-sec-head");
			$("#" + itemActiveMenuId + "Box").empty().append(box);
		}
	};
	
	function search(p) {
		page = p || 1;
		var data = {};
		data.page = page;
		data.pageSize = pageSize;
		data.status = 4;
		data.sort = sort;
		data.desc = desc;
		if (cache[sort] && cache[sort].page >= page) {
			page = cache[sort].page;
			show(cache[sort].json);
			return;
		}
		MyAjax.post("/active/getItemsActives.do", data, show);
	};
	
	function goNext() {
		if (cache[sort]) {
			if (cache[sort] && page >= cache[sort].pages) {
				return;
			}
			page = cache[sort].page;
		}
		page ++;
		search(page);
	};
	
	function init() {
		header = $(".main-content").offset().top;
		setTimeout(function() {
			isPc = Adept.isPC();
			addEvent();
			search();
			ScrollPage.scroll(scroll);
			ScrollPage.next(goNext);
		}, 80);
	};
	
	return {
		load : init,
		reload : reload
	};
})();
Notice.load();
FocusMaps.load();
LatestAnnouncement.load();
MainActives.load();