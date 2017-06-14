(function(jq) {
	jq.fn.FocusMap = function(options) {
		options = options || {};
		var defalutInit = {
			dir: 'left',
			callback : null
		};
		var opts = jq.extend(defalutInit, options);
		window.FocusMapcache = window.FocusMapcache || {};
		window.FocusMapcache.index = window.FocusMapcache.index || 0;
		
		var run = function(self) {
			var ul = self.find("ul");
			var li = ul.children();
			var opts = self.data("opts");
			var liMove = $(li[0]);
			if (opts.dir == 'left') {
				liMove = $(li[0]);
				liMove.addClass("go-left");
			}
			clearTimeout(self.data("timer"));
			var timer = setTimeout(function() {
				liMove.removeClass("go-left").removeClass("go-right")
				ul.append(liMove);
				run(self);
			}, 3500);
			self.data("timer", timer);
		};
		var enterEvent = "ontouchstart";
		$(this).bind("mouse");
		return this.each(function(index, obj) {
			var self = $(this);
			self.data("opts", opts);
			var self = $(this);
			var ul = self.find("ul");
			var li = ul.children();
			if (li.length == 1) {
				ul.append(li.clone());
				return;
			}
			if (li.length == 2) {
				ul.append($(li[0]).clone());
			}
            if (!Adept.isPC()) {
            	$(this).bind("touchstart", FocusMap.touchstart)
    			.bind("touchmove", FocusMap.touchmove)
    			.bind("touchend", FocusMap.touchend)
    			.bind("dragstart", FocusMap.touchstart)
    			.bind("drag", FocusMap.touchmove)
    			.bind("dragend", FocusMap.touchend);
            } else {
            	$(this).bind("mousedown", FocusMap.touchstart)
    			.bind("mousemove", FocusMap.touchmove)
    			.bind("mouseup", FocusMap.touchend)
            }
            run(self);
		});
	};
	var FocusMap = {
		touchstart : function(event) {
			console.log("==>1");
		}, touchmove : function(event) {
			console.log("==>2");
		}, touchend : function(event) {
			console.log("==>3");
		}
	};
})(jQuery);