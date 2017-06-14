var jq = jQuery;
Date.prototype.format = function(format) { 
	var o = { 
		"M+" : this.getMonth()+1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth()+3)/3),
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)); 
	} 
	for(var k in o) { 
		if (new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)); 
		} 
	} 
	return format; 
};
String.prototype.toJson = function() {
	return eval('(' + this + ')');
};
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
};
String.prototype.toDate = function() {
	return new Date(Date.parse(this.replace(/-/g, "/")));
};
String.prototype.toZeroDate = function() {
	var d = this.toDate();
	var rt = new Date(d.getFullYear(), d.getMonth(), d.getDate(), 0, 0, 0);
	return rt;
};
Date.prototype.toZeroDate = function() {
	var d = this;
	return new Date(d.getFullYear(), d.getMonth(), d.getDate(), 0, 0, 0);
};
String.prototype.getFullMonthDates = function() {
	var d = this.toDate();
	return new Date(d.getFullYear(), d.getMonth() + 1, 0, 0, 0, 0).getDate();
};
Date.prototype.getFullMonthDates = function() {
	var d = this;
	return new Date(d.getFullYear(), d.getMonth() + 1, 0, 0, 0, 0).getDate();
};
Date.prototype.getBeforeMonthFirst = function() {
	return new Date(this.getFullYear(), this.getMonth() - 1, 1, 0, 0, 0);
};
String.prototype.getBeforeMonthFirst = function() {
	return this.toDate().getBeforeMonthFirst();
};
Date.prototype.getBeforeMonthLast = function() {
	return new Date(this.getFullYear(), this.getMonth(), 0, 0, 0, 0);
};
String.prototype.getBeforeMonthLast = function() {
	return this.toDate().getBeforeMonthLast();
};
Date.prototype.getMonthFirst = function() {
	return new Date(this.getFullYear(), this.getMonth(), 1, 0, 0, 0);
};
String.prototype.getMonthFirst = function() {
	return this.toDate().getMonthFirst();
};
Date.prototype.getMonthLast = function() {
	return new Date(this.getFullYear(), this.getMonth() + 1, 0, 0, 0, 0);
};
String.prototype.getMonthLast = function() {
	return this.toDate().getMonthLast();
};
Number.prototype.forFixeds =  function(n) {
	return Math.round(this*Math.pow(10,n))/Math.pow(10,n);
};
String.prototype.forFixeds =  function(n) {
	var num = parseFloat(this);
	return num.forFixeds(n);
};
Date.prototype.addDay = function(t) {
	var d = new Date(this.getTime() + (86400 * 1000 * t));
	return new Date(d.getFullYear(), d.getMonth(), d.getDate(), 0, 0, 0);
};
Array.prototype.remove = function(o) {
	for (var i = 0; i < this.length; i ++) {
		if (JSON.stringify(this[i]) == JSON.stringify(o)) {
			this.splice(i, 1);
			break;
		}
	}
};
Array.prototype.contains = function(o) {
	for (var i = 0; i < this.length; i ++) {
		if (JSON.stringify(this[i]) == JSON.stringify(o)) {
			return true;
		}
	}
	return false;
};
String.prototype.isAmt = function() {
	return /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/.test(this);
};
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.isMobile = function() {
	if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(this))) { 
        return false; 
    }
    return true;
};
Config = (function() {
	return {
		status : new function() {
			this.OK = 0;
			this.FAIL = 1;
		},
		type : new function() {
			this.ZERO = 0;
			this.ONE = 1;
			this.TWO = 2;
			this.THREE = 3;
			this.FOUR = 4;
			this.FIVE = 5;
			this.SIX = 6;
			this.SEVEN = 7;
			this.EIGHT = 8;
			this.NINE = 9;
			this.TEN = 10;
		}
	};
})();
var Utils = {
	cache : {
		nowTime : new Date().getTime(),
		getNowDate : function() {
			return new Date(Utils.cache.nowTime);
		}
	},
	getDomain : function() {
		return Base.getDomain();
	},
	trim : function(str) {
		if (str == null) {
			return "";
		}
		if (typeof(str) == "string") {
			return str.trim();
		}
		return str;
	},
	getNowTime : function() {
		return Utils.cache.nowTime;
	},
	forFixed : function(str, n) {
		n = n || 2;
		var num = 0;
		if (str == null) {
			str = 0;
		}
		if (typeof str != 'number') {
			try {
				num = parseFloat(str);
			} catch(e){}
		} else {
			num = str;
		}
		var key = 1;
		for (var i = 0; i < n; i ++) {
			key = key * 10;
		}
		str = Math.round(num * key) / key;
		return str;
	},
	loadJs : function(url, time) {
		time = time ? time : 0;
		setTimeout(function() {
			var head = document.getElementsByTagName('HEAD').item(0); 
		    var script= document.createElement("script"); 
		    script.type = "text/javascript"; 
		    script.src = url;
		    head.appendChild(script);
		}, time);
	},
	loadCss : function(url, time) {
		time = time ? time : 0;
		setTimeout(function() {
			var head = document.getElementsByTagName('HEAD').item(0); 
		    var link = document.createElement("link"); 
		    link.type = "text/css"; 
		    link.href = url;
		    link.rel = "stylesheet";
		    head.appendChild(link);
		}, time);
	},
	isNull : function(str, msg) {
		if (str == null || str == '') {
			if (msg != null) {
				layer.info(msg);
			}
			return true;
		}
		if (typeof(str) == "string" && str.trim() == '') {
			if (msg != null) {
				alert(msg);
			}
			return true;
		}
		return false;
	},
	getQueryStr : function(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) {
			return unescape(r[2]);
		}
		return null;
	},
	noData : function(msg) {
		var box = $("<div>");
		box.addClass("no-record");
		box.append(msg);
		return box;
	},
	endBox : function(msg) {
		var box = $("<div>");
		box.addClass("item-end");
		box.append(msg);
		return box;
	},
	loadingNext : function(msg) {
		msg = msg == null ? "正在加载······" : msg;
		var box = $("<div>");
		var img = $("<img>");
		img.attr("src", Base.getDomain() + "/static/images/loading.gif");
		box.addClass("loading-next");
		box.append(img);
		box.append(msg);
		return box;
	}
};
AjaxSync = (function() {
	var mapLok = {};
	var mapType = {};
	var mapTime = {};
	var last = null;
	var index = 0;
	
	function send(url, data, callback) {
		if (mapType[url] == 1) {
			if (mapLok[url]) {
				return;
			}
			mapLok[url] = true;
		}
		MyAjax.post(url, data, function(json, error) {
			if (json) {
				if (mapType[url] == 1) {
					if (mapTime[url] > 0) {
						setTimeout(function() {
							callback(json);
							mapLok[url] = false;
						}, mapTime[url]);
					} else {
						callback(json);
						mapLok[url] = false;
					}
				} else if (mapType[url] == 2) {
					if (mapTime[url] > 0) {
						setTimeout(function() {
							if (data._randomId == last) {
								callback(json);
							}
						}, mapTime[url]);
					} else {
						if (data._randomId == last) {
							callback(json);
						}
					}
					
				}
			} else {
				if (mapType[url] == 1) {
					callback(null, json);
					mapLok[url] = false;
				} else if (mapType[url] == 2) {
					if (data._randomId == last) {
						callback(null, json);
					}
				}
			}
			
		});
	};
	
	return {
		send : function(url, data, callback) {
			var type = data.syncType || 1;
			mapType[url] = type;
			if (type == 2) {
				last = new Date().getTime() + "-" + parseInt(Math.random(100)*100) + "-" + index;
				data._randomId = last;
				index ++;
			}
			mapTime[url] = data.loadingTime || 0;
			send(url, data, callback);
		}
	};
})();
var MyAjax = (function() {
	function ajax() {
		jq.ajaxSetup({
			cache : true,
			timeout : 30000
		});
		$(document).ajaxStart(function() {
		}).ajaxStop(function() {
		}).ajaxSuccess(function(event, request, settings) {
			if (request.responseText == null || request.responseText == "") {
				return;
			}
			var obj = eval('(' + request.responseText + ')');
		});
	};
	
	function request(type, url, dataType, data, callback) {
		if (url.indexOf("http") == -1) {
			if (url.substr(0, 1) != '/') {
				url = "/" + url;
			}
			url = Base.getDomain() + url;
		}
		jq.ajax({
			type : type,
			url : url,
			dataType : dataType,
			data : data,
			error : function(xmlHttpRequest, error) {
				callback(null, {code : 404, message : "&#x672A;&#x627E;&#x5230;&#x8BE5;&#x9875;&#x9762;"});
			}
		}).done(function(json) {
			if (json.nowTime) {
				Utils.cache.nowTime = json.nowTime;
			}
			if (json.code == 0) {
				callback(json);
			} else {
				callback(null, json);
			}
		});
	};
	
	return {
		load : ajax,
		request : request,
		post : function(url, data, callback) {
			if (data && (data.syncType == 1 || data.syncType == 2)) {
				AjaxSync.send(url, data, callback);
			} else {
				request('POST', url, 'json', data, callback);
			}
		},
		get : function(url, data, callback) {
			request('GET', url, 'json', data, callback);
		}
	};
})();

(function(jq) {
	jq.fn.Select = function(options) {
		var els = jq(this);
		var defalutInit = {
			arr : [],
			name : "name",
			value : "value",
			isFirst : false,
			firstName : "请选择",
			firstVal : "",
			defaultVal : "",
			callback : null
		};
		var opts = jq.extend(defalutInit, options);
		
		if (opts.isFirst) {
			$(this).each(function(index, obj) {
			 	var opt = new Option(opts.firstName, opts.firstVal);
				 obj.options.add(opt);
			});
		}
		if (opts.arr == null || opts.arr.length == 0) {
			return;		
		}
		$(this).each(function(index, obj) {
			for (var i = 0; i < opts.arr.length; i ++) {
				 var item = opts.arr[i];
				 var opt = new Option(item[opts.name], item[opts.value]);
				 if (item[opts.value] == opts.defaultVal && opts.defaultVal != "") {
					 opt.selected = true;
					 if (opts.callback) {
					 	opts.callback(item[opts.value], item[opts.name], opt);
					 }
				 }
				 obj.options.add(opt);
			}
			if (opts.callback) {
				$(obj).unbind().bind("change", {callback : opts.callback}, function(event) {
					event.data.callback(this.value, this.options[obj.options.selectedIndex].text, this);
				});
			}
		});
	};
	jq.fn.goSlide = function(options) {
		options = options || {};
		var defalutInit = {
			dir : 'top',
			time : 5000
		};
		var opts = jq.extend(defalutInit, options);
		$(this).each(function() {
			var ul = $(this);
			if (this.tagName != 'UL') {
				ul = $(this).find("ul");
			}
			var lis = ul.find("li");
			if (lis.length < 2) {
				return;
			}
			ul.addClass("slide-run");
			ul.data("item", opts)
			var li = null;
			if (opts.dir == 'top') {
				li = $(lis[0]);
			} else {
				li = $(lis[lis.length - 1]);
			}
			li.addClass("move-" + opts.dir);
		});
		var timer = $(this).data("timer");
		clearInterval(timer);
		timer = setInterval(function() {
			$("ul.slide-run").each(function() {
				var opts = $(this).data("item");
				var ul = $(this);
				var lis = ul.find("li");
				var li = ul.find(".move-" + opts.dir);
				li.removeClass();
				if (opts.dir == 'top') {
					ul.append(li);
					ul.find("li:first").addClass("move-" + opts.dir);
				} else {
					ul.prepend(li);
					ul.find("li:last").addClass("move-" + opts.dir);
				}
			});
		}, opts.time);
	};
})(jQuery);