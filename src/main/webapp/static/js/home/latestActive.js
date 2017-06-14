var LatestActive = (function() {
	var outerBoxId = "latestActive";
	var boxId = "latestActiveBox";
	var cache = {};
	
	var page = 1;
	var pageSize = 10;
	
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
		var infoIco = $("<div>");
		var img = $("<img>");
		
		logo.addClass("item-logo");
		title.addClass("item-title");
		time.addClass("item-time");
		infoIco.addClass("item-info-ico");
		
		img.attr("src", item.itemPicUrl);
		logo.append(img);
		title.append(item.activeName);
		time.append("04:59:59");
		infoIco.append($("<i>").addClass("sui-icon icon-tb-time"));
		infoIco.append("即将揭晓");
		
		box.addClass("publish-box");
		box.append(logo);
		box.append(title);
		box.append(infoIco);
		box.append(time);
		box.data("item", item);
		return box;
	};
	
	function show(json, error) {
		var box = $("#" + boxId);
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
		var len = list.length;
		for (var i = 0; i < len; i ++) {
			var item = list[i];
			box.append(createBox(item));
		}
		cache.pages = paging.pages;
		Page.refreshScroll();
		var outerBox = $("#" + outerBoxId);
		if (paging.pages > 0 && outerBox.find(".loading-next").length == 0) {
			outerBox.append(Utils.loadingNext());
		}
		if (page == paging.pages) {
			outerBox.find(".loading-next").remove();
			outerBox.append(Utils.endBox("已到底部"));
		}
	};
	
	function search(p) {
		p = p || 1;
		var data = {};
		data.page = page;
		data.pageSize = pageSize;
		data.status = 5;
		data.sort = "publish_time";
		data.desc = false;
		MyAjax.post("/active/getItemsActives.do", data, show);
	};
	
	function addEvent() {
		
	};
	
	function goNext() {
		if (page >= cache.pages) {
			return;
		}
		page ++;
		search(page);
	};
	
	function init() {
		$("#" + boxId).empty();
		addEvent();
		search();
		Loop.add(timerLoop);
		ScrollPage.next(goNext);
	};
	
	return {
		load : init
	};
})();
LatestActive.load();