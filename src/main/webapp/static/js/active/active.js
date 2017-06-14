var Active = (function() {
	var itemActiveBoxId = "itemActiveBox";
	var outerBoxId = "itemTypes";
	
	var page = 1;
	var pageSize = 10;
	var typeId = null;
	var cache = {};
	
	function createBox(item) {
		var box = $("<div>");
		var itemLogo = $("<div>");
		var itemMiddel = $("<div>");
		var itemRight = $("<div>");
		var img = $("<img>");
		img.attr("src", item.itemPicUrl);
		itemLogo.addClass("item-logo").append(img);
		
		itemMiddel.addClass("item-middel");
		itemMiddel.append($("<div>").addClass("item-title").append(item.activeName));
		var process = $("<div>");
		var bar = $("<div>");
		bar.addClass("bar");
		var p = item.totalNum = 0 ? 0 : Math.floor(item.currentNum * 100 / item.totalNum);
		bar.css("width", p + "%");
		process.addClass("sui-process");
		process.append(bar);
		itemMiddel.append(process);
		var itemInfo = $("<div>");
		itemInfo.addClass("item-info");
		itemInfo.append($("<span>").addClass("left").append("已参与").append($("<span>").append(item.currentNum)));
		itemInfo.append($("<span>").addClass("right").append("剩余").append($("<span>").append(item.surplusNum)));
		itemMiddel.append(itemInfo);
		
		itemRight.addClass("item-right");
		var btn = $("<a>");
		btn.addClass("sui-btn").append("加入清单");
		itemRight.append(btn);
		
		box.addClass("item-box");
		box.append(itemLogo);
		box.append(itemMiddel);
		box.append(itemRight);
		return box;
	};
	
	function show(json, error) {
		var box = $("#" + outerBoxId + ">#" + itemActiveBoxId);
		if (error) {
			box.empty().append($("<div>").addClass("item-active-error").arrpend(error.message));
			return;
		}
		if (json.data.list == null || json.data.list.length == 0) {
			box.empty().append(Utils.noData("暂无记录！"));
			return;
		}
		initParam();
		var paging = json.data;
		cache[typeId].pages = paging.pages;
		var list = paging.list;
		var len = list.length;
		for (var i = 0; i < len; i ++) {
			var item = list[i];
			box.append(createBox(item));
		}
		if (page == paging.pages) {
			box.find(".loading-next").remove();
			box.append(Utils.endBox("已到底部"));
		}
		Page.refreshScroll();
	};
	
	function initParam() {
		cache[typeId] = cache[typeId] || {
			page : page,
			typeId : typeId,
			pages : 0
		};
	};
	
	function search() {
		var data = {};
		data.typeId = typeId;
		data.page = page;
		data.pageSize = pageSize;
		if (cache[typeId] && cache[typeId].page >= page) {
			return;
		}
		MyAjax.post("/active/getItemsActives.do", data, show);
	};
	
	function addEvent() {
		
	};
	
	function goNext() {
		if (page >= cache[typeId].pages) {
			return;
		}
		page ++;
		search();
	};
	
	function init(itemType) {
		typeId = itemType.typeId;
		outerBoxId = "itemTypes" + typeId;
		page = cache[typeId] ? cache[typeId].page : 1;
		ScrollPage.next(goNext);
		if (cache[typeId]) {
			return;
		}
		$("#" + outerBoxId + ">#" + itemActiveBoxId).append(Utils.loadingNext());
		addEvent();
		search();
	};
	
	return {
		load : init
	};
})();