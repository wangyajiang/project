var jq = jQuery;
/*
 * auth:wangyj
 * web util.js
 * */
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
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.isAmt = function() {
	return /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/.test(this);
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
Array.prototype.remove = function (dx) {  
    if (isNaN(dx) || dx > this.length) {  
        return false;  
    }  
    for (var i = 0, n = 0; i < this.length; i++) {  
        if (this[i] != this[dx]) {  
            this[n++] = this[i];  
        }  
    }  
    this.length -= 1;  
};
function CloneJson(json) {
	if (typeof json == 'object') {
		var result = {};
		for (var key in json) {
	      result[key] = typeof json[key]==='object' ? CloneJson(json[key]) : json[key];
	    } 
	    return result; 
	}
	return json;
};
Config = {
	seArr : ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F']
};
MyMethod = {
	getRandomColor : function() {
		var n = Config.seArr.length - 1;
		var res = "";
	    for (var i = 0; i < 6 ; i ++) {
	         var id = Math.ceil(Math.random() * n);
	         res += Config.seArr[id];
	    }
		return res;
	},
	getArrPersion : function(arr) {
		if (typeof arr != 'object') {
			return;
		}
		var rt = [];
		var _total = 0;
		var _p = 100;
		for (var i = 0; i < arr.length; i ++) {
			_total += arr[i];
		}
		for (var i = 0; i < arr.length; i ++) {
			if (_total == 0) {
				rt[i] = 0;
			} else {
				rt[i] = (arr[i] * 100 / _total).forFixeds(2);
			}
			_p -= rt[i];
		}
		rt[0] += (_p * 100).forFixeds(2) / 100;
		return rt;
	},
	getEChartDefaultColor : function(index) {
		var _echartArr = ['#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed', '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0', '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700','#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0' ]; 
		if (index == null) {
			return;
		}
		if (typeof index != 'number') {
			return;
		}
		if (index >= _echartArr.length) {
			index = index - _echartArr.length;
		}
		return _echartArr[index];
	},
	getTimeSelectCode : function() {
		var rtArr = [];
		rtArr[0] = {name : "今天", value : "day"};
		rtArr[1] = {name : "昨天", value : "yesterday"};
		rtArr[2] = {name : "本月", value : "month"};
		rtArr[3] = {name : "过去7天", value : "week"};
		rtArr[4] = {name : "过去30天", value : "thirty"};
		return rtArr;
	},
	getTimeOption : function(nowTime) {
		nowTime = nowTime || new Date();
		nowTime = nowTime.toZeroDate();
		var yesterday = nowTime.addDay(-1);
		var rtJson = {};
		rtJson.day = {start : nowTime, end : nowTime};
		rtJson.yesterday = {start : yesterday, end : yesterday};
		rtJson.month = {start : nowTime.getMonthFirst(), end : nowTime.getMonthLast()};
		rtJson.week = {start : yesterday.addDay(-6), end : yesterday};
		rtJson.thirty = {start : yesterday.addDay(-30), end : yesterday};
		return rtJson;
	},
	getDivProcessMsg : function(msg) {
		var box = jq("<div>");
		box.attr("style", "width:100%;line-height:50px;color:#aaa;font-size:16px;text-align:center;").empty().append(msg);
		return box;
	},
	getTdProcessMsg : function(msg) {
		var td = jq("<td>");
		td.attr("colspan", "100").attr("style", "width:100%;color:#aaa;font-size:16px;").empty().append(msg);
		return jq("<tr>").append(td);
	},
	copyJson : function(data) {
		if (!data) {
			return data;
		}
		if (typeof data == 'object') {
			var temp = {};
			for (var key in data) {
				temp[key] = data[key];
			}
			return temp;
		}
		return data;
	},
	getGirdToListAndKeys : function(arr) {
		var list = [];
		var keys = [];
		for (var i = 0; i <  arr.length; i ++) {
			var id = arr[i].id;
			var innArr = arr[i].data;
			var data = {id : id};
			var len = innArr.length;
			for (var j = 0; j < len; j ++) {
				if (i == 0) {
					keys[j] = "column_" + j;
				}
				if (j == len - 1 && innArr[j].indexOf("^") >= 0) {
					var detialArr = innArr[j].split("^");
					data["column_" + j] = '<a href="javascript:' + detialArr[1] +'" target="' + detialArr[2] + '">' + detialArr[0] + '</a>';
				} else {
					data["column_" + j] = innArr[j];
				}
				
				
			}
			list[i] = data;
		}
		return {list : list, keys : keys};
	},
	createTable : function(options) {
		return new createTable(options);
	},
	loadIngJs : function(url) {
		setTimeout(function() {
			var head = document.getElementsByTagName('HEAD').item(0); 
		    var script= document.createElement("script"); 
		    script.type = "text/javascript"; 
		    script.src = url; 
		    head.appendChild(script);
		}, 1);
	},
	createAddReduce : function(num, callback, item) {
    	var box = $("<div>");
    	var input = $("<input>");
    	var prifx = $("<span>");
    	var next = $("<span>");
    	prifx.addClass("prifx");
    	next.addClass("next");
    	input.attr("type", "text").val(num);
    	input.data("data", num);
    	input.attr("maxlength", "4");
		input.unbind().bind("keyup", {callback : callback, item : item}, function(event) {
			if (isNaN(this.value) || this.value.indexOf(".") >= 0) {
				this.value = $(this).data("data");
				return false;
			}
			if (this.value == "") {
				this.value = "0";
			}
			$(this).data("data", this.value.trim());
			if (event.data.callback) {
				event.data.callback(Number(this.value.trim()), event.data.item);
			}
		}).bind("blur", function() {
			this.value = Number(this.value.trim());
		});
		next.unbind().bind("click", {input : input, callback : callback, item : item}, function(event) {
			var input = $(event.data.input);
			input.val(Number(input.val()) + 1);
			if (input.val() > 9999) {
				input.val(9999);
			}
			if (event.data.callback) {
				event.data.callback(Number(input.val().trim()), event.data.item);
			}
		});
		prifx.unbind().bind("click", {input : input, callback : callback, item : item}, function(event) {
			var input = $(event.data.input);
			input.val(Number(input.val()) - 1);
			if (input.val() < -999) {
				input.val(-999);
			}
			if (event.data.callback) {
				event.data.callback(Number(input.val().trim()), event.data.item);
			}
		});
		
    	box.addClass("sui-a-s");
    	box.append(prifx);
    	box.append(input);
    	box.append(next);
    	return box;
	}
	
};

/*
 * auth:wyj
 * 实例
 * var box = document.getElementById("box");
	var list = [];
	for (var i = 0; i < 10; i ++) {
		var name = "name";
		if (i >= 3 && i <= 5) {
			name = "name3-5";	
		} else if (i < 2){
			name = "name1";
		} else if (i < 7) {
			name = "name" + i;
		} else {
			name = "name=";
		}
		var data = {id : i + 1, name : name, amnt : 100 + i, rno : 6001 + i};
		list[i] = data;
	}
	var tb = createTable({
		list : list,
		heads : [ "姓名", "金额", "房号"],
		keys : [ "name", "amnt", "rno"],
		sorts : [ false, true, true],
		types : [],
		isTotal : true,
		totalIndex : [1,2],//末行统计信息
		isMerge : true,//合并第一列相邻且相同名称
		callback : function(type, sort, desc, index) {
			return;
			alert("type=" + type + "\nsort=" + sort + "\ndesc=" + desc + "\nindex=" + index);
		}
	});
	box.appendChild(tb);
*/
function createTable(options) {
	this.tbId = "my" + Math.ceil(Math.random()*100) + new Date().getTime();
	var _this = this;
	this.sort = null;
	this.desc = true;
	this.rowsJson = {};
	this.totalIndexJson = {};
	var init = {
		list : null,
		className : 'tb-sui hover center no-wrap',
		sorts : [],
		heads : [],
		keys : [],
		txt : [],
		types : [],
		isTotal : true,
		totalIndex : [],
		keyId : "id",
		noDataMsg : "无查询结果",
		isMerge : false,
		sort : null,
		desc : true,
		callback : null
	};
	
	options = options || init;
	options.className = options.className || init.className;
	options.sorts = options.sorts || init.sorts;
	options.heads = options.heads || init.heads;
	options.keys = options.keys || init.keys;
	options.types = options.types || init.types;
	options.keyId = options.keyId || init.keyId;
	options.noDataMsg = options.noDataMsg || init.noDataMsg;
	options.totalIndex = options.totalIndex || init.totalIndex;
	options.isMerge = options.isMerge || init.isMerge;
	options.callback = options.callback || init.callback;
	if (options.sort) {
		this.sort = options.sort;
		this.desc = options.desc;
	}
	if (options.heads.length > options.sorts.length) {
		var len = options.heads.length - options.sorts.length;
		for (var  i = 0; i < len; i ++) {
			options.sorts.push(false);
		}
	}
	if (options.heads.length > options.types.length) {
		var len = options.heads.length - options.types.length;
		for (var  i = 0; i < len; i ++) {
			options.types.push("txt");
		}
	}
	if (options.isTotal) {
		for (var i = 0; i < options.totalIndex.length; i ++) {
			this.totalIndexJson[options.keys[options.totalIndex[i]]] = {
				name : 	options.heads[options.totalIndex[i]],
				value : 0
			};
		}
		if (options.list && options.list.length > 0) {
			for (var i = 0; i < options.list.length; i ++) {
				var temp = options.list[i];
				for (var key in temp) {
					if (this.totalIndexJson[key]) {
						this.totalIndexJson[key].value +=  Number(temp[key] || 0);
					}
				}
			}
		}
	}
	
	var tab = document.createElement("table");
	tab.className = options.className;
	tab.setAttribute("id", _this.tbId);
	
	this.createTh = function(name, type, isSort, index) {
		var th = document.createElement("th");
		th.setAttribute("data-index", index);
		if (type == "checkbox") {
			var obj = document.createElement("input");
			obj.setAttribute("type", "checkbox");
			obj.value = "";
			obj.onchange = function() {
				var checkboxs = document.getElementById(_this.tbId).getElementsByClassName("my-check-box");
				if (this.checked) {
					for (var k = 0; k < checkboxs.length; k ++) {
						if (!checkboxs[k].checked) {
							checkboxs[k].checked = true;	
						}	
					}
				} else {
					for (var k = 0; k < checkboxs.length; k ++) {
						if (checkboxs[k].checked) {
							checkboxs[k].checked = false;	
						}	
					}
				}
			};
			th.appendChild(obj);
		} else {
			th.innerHTML = name;
		}
		if (isSort) {
			var ico = document.createElement("i");
			if (null != this.sort && options.keys[index] == this.sort) {
				if (this.desc) {
					ico.className = "ico-sort sort-down";
				} else {
					ico.className = "ico-sort sort-up";
				}
			} else {
				ico.className = "ico-sort";
			}
			th.appendChild(ico);
			th.className = "cursor";
			th.onclick = function() {
				var index = this.getAttribute("data-index");
				var ico = this.getElementsByClassName("ico-sort")[0];
				var className = ico.className;
				var icos = document.getElementById(_this.tbId).getElementsByClassName("ico-sort");
				for (var k = 0; k < icos.length; k ++) {
					icos[k].className = "ico-sort";
				}
				if (className.indexOf("sort-down") >= 0) {
					ico.className = "ico-sort sort-up";
					_this.desc = false;
				} else {
					ico.className = "ico-sort sort-down";
					_this.desc = true;
				}
				_this.sort = options.keys[index];
				if (options.callback) {
					options.callback(1, _this.sort, _this.desc, index);	
				}
				_this.search();
			};
		}
		return th;
	};
	
	this.createTd = function(item, key, idKey, type, line, index) {
		var td = document.createElement("td");
		td.setAttribute("data-index", index);
		td.className = key;
		if (type == "checkbox") {
			var obj = document.createElement("input");
			obj.setAttribute("type", "checkbox");
			obj.className = "my-check-box";
			obj.value = item[idKey];
			td.appendChild(obj);
		} else {
			//td.innerHTML = item[key];
			if (typeof item[key] == 'object') {
				td.appendChild(item[key]);
			} else {
				td.innerHTML = item[key];
				td.setAttribute("title", item[key]);
			}
		}
		return td;
	};
	
	this.createThead = function() {
		var thead = document.createElement("thead");
		for (var i = 0; i < options.heads.length; i ++) {
			thead.appendChild(this.createTh(options.heads[i], options.types[i], options.sorts[i], i));
		}
		return thead;
	};
	
	this.createTbody = function() {
		var tbody = document.createElement("tbody");
		for (var i = 0; i < options.list.length; i ++) {
			var tr = document.createElement("tr");
			tr.setAttribute("data-id", options.list[i][options.keyId]);
			tr.setAttribute("data-index", i);
			for (var j = 0; j < options.keys.length; j ++) {
				if (j == 0 && options.isMerge && this.rowsJson[i] && this.rowsJson[i].flag == 3) {
					continue;
				}
				var td = this.createTd(options.list[i], options.keys[j], options.keyId, options.types[j], i, j);
				if (j == 0 && options.isMerge && this.rowsJson[i] && this.rowsJson[i].flag) {
					td.setAttribute("rowspan", this.rowsJson[i].rows);	
				}
				tr.appendChild(td);
			}
			if (options.callback) {
				tr.onclick = function(e) {
					options.callback("TR", this, this.getAttribute("data-id"));
				};
				tr.ondblclick = function(e) {
					options.callback("TR_db", this, this.getAttribute("data-id"));
				};
			}
			
			tbody.appendChild(tr);
		}
		if (options.isTotal) {
			tbody.appendChild(this.createTotalTr());
		}
		return tbody;
	};
	
	this.noDataMsg = function(msg) {
		var tbody = document.createElement("tbody");
		var tr = document.createElement("tr");
		var td = document.createElement("td");
		td.setAttribute("colspan", 100);
		td.setAttribute("style", "text-align:center;color:#aaa;");
		td.innerHTML = msg;
		tr.appendChild(td);
		tbody.appendChild(tr);
		tab.appendChild(tbody);
	};
	
	this.createTotalTr = function() {
		if (options.list == null || options.list.length == 0) {
			return;	
		}
		var tr = document.createElement("tr");
		var td = document.createElement("td");
		td.setAttribute("colspan", 100);
		td.setAttribute("style", "text-align:left;font-size:12px;");
		tr.appendChild(td);
		var totalSpan = document.createElement("span");
		var totalLabel = document.createElement("label");
		var totalI = document.createElement("i");
		totalSpan.className = "lin-span";
		totalLabel.innerHTML = "数据量：";
		totalI.innerHTML = options.list.length;
		totalSpan.appendChild(totalLabel);
		totalSpan.appendChild(totalI);
		td.appendChild(totalSpan);
		for (var key in this.totalIndexJson) {
			var span = document.createElement("span");
			var label = document.createElement("label");
			var i = document.createElement("i");
			span.className = "lin-span";
			label.innerHTML = this.totalIndexJson[key].name + "：" ;
			i.innerHTML = this.totalIndexJson[key].value;
			span.appendChild(label);
			span.appendChild(i);
			td.appendChild(span);
		}
		tr.appendChild(td);
		return tr;
	};
	
	this.initRowsJson = function() {
		this.rowsJson = {};
		if (!options.isMerge) {
			return;	
		}
		var temp = -1;
		var index = 0;
		var json = {};
		if (options.list && options.list.length > 0) {
			for (var i = 0; i < options.list.length; i ++) {
				var item = options.list[i];
				var value = item[options.keys[0]];
				if (temp != value) {
					if (i - index > 1) {
						json[index] = i - index;
					}
					temp = value;
					index = i;
				}
			}
			if (options.list.length - index > 1) {
				if (i - index > 1) {
					json[index] = options.list.length - index;
				}
			}
			index = 0;
			for (var i = 0; i < options.list.length; i ++) {
				if (json[i]) {
					this.rowsJson[i] = {flag : 1, rows : json[i]};// 2
					index = i;
				} else {
					if (this.rowsJson[index] && i < index + this.rowsJson[index].rows) {//i = 2 2 >2
						this.rowsJson[i] = {flag : 3};
					} else {
						this.rowsJson[i] = {flag : 2};	
					}
					
				}
			}
		}
		
	};
	
	this.search = function() {
		if (this.sort && options.list && options.list.length > 0) {
			options.list.sort(function(o1, o2) {
				if (_this.desc) {
					return o2[_this.sort] - o1[_this.sort];
				} else {
					return o1[_this.sort] - o2[_this.sort];
				}
			});
		}
		this.initRowsJson();
		try {tab.innerHTML = "";} catch(e){}
		tab.appendChild(this.createThead());
		if (options.list && options.list.length > 0) {
			tab.appendChild(_this.createTbody());
		} else {
			this.noDataMsg(options.noDataMsg);
		}
	};
	
	this.search();
	return tab;
};


MyAjax = (function() {
	
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
		jq.ajax({
			type : type,
			url : url,
			dataType : dataType,
			data : data,
			error : function(xmlHttpRequest, error) {
				callback(null, {rtState : 404, retmsrg : "请求失败"});
			}
		}).done(function(json) {
			if (json.rtState == 0) {
				callback(json);
			} else {
				callback(null, json);
			}
		});
	};
	
	return {
		init : function() {
			ajax();
		},
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
/* 
* data.loadingTime 需要请求完成之后延迟的时间
* data.syncType = 1:只执行同一种请求的第一个方法
* data.syncType = 2:只执行同一种请求的第最后一个方法
*/
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
		MyAjax.send(url, data, function(json, error) {
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

(function(jq) {
	jq.fn.TabSwitch = function(callback) {
		jq(this).each(function(i, obj) {
			jq(obj).find("li").unbind().bind("click", {callback : callback}, function(event) {
				jq(this).parent().find("li").removeClass("active");
				jq(this).addClass("active");
				var index = jq(this).index();
				if (event.data.callback) {
					event.data.callback(index);
				}
			});
		});
	};
	jq.fn.dates = function() {
		if (jq(this).length == 0) {
			return;
		}
		jq(this).each(function(i, obj) {
			jq(obj).datetimepicker({
		        language:  'zh-CN',
		        //weekStart: 1,
		        format: "yyyy-mm-dd",
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				forceParse: 0,
		        showMeridian: 0,
		        minView: 2
		    });
		});
	};
	jq.fn.initEstate = function(callback, path) {
		if (jq(this).length == 0) {
			return;
		}
		var _this = this;
		var url = contextPath + "/com/easy/i3/room/act/MYI3EstateAct/getI3EstateByUser.act";
		if (path) {
			url = path;
		}
		MyAjax.post(url, null, function(json, error) {
			if (json == null || json.rtData == null || json.rtData.length == 0) {
				return;
			}
			jq(_this).each(function(_i, obj) {
				 for(var i = 0; i < json.rtData.length; i ++) {
					 var item = json.rtData[i];
					 obj.options.add(new Option(item.estateName, item.estateNo));
				 }
			});
			if (callback) {
				callback();
			}
		});
	};
	jq.fn.initPersions = function(callback) {
		if (jq(this).length == 0) {
			return;
		}
		var _this = this;
		var url = contextPath + "/com/easy/tech/person/act/MYPersonAct/getPersonList.act";
		MyAjax.post(url, null, function(json, error) {
			if (json == null || json.rtData == null || json.rtData.length == 0) {
				return;
			}
			jq(_this).each(function(_i, obj) {
				 for(var i = 0; i < json.rtData.length; i ++) {
					 var item = json.rtData[i];
					 obj.options.add(new Option(item.userName, item.userId));
				 }
			});
			if (callback) {
				callback();
			}
		});
	};
	jq.fn.initNationality = function(parentNo, callback) {
		var data = {parentNo : parentNo || ""};
		if (jq(this).length == 0) {
			return;
		}
		var _this = this;
		var url = contextPath + "/com/easy/codeclass/act/MYCodeClassAct/selectChildCode.act";
		MyAjax.post(url, data, function(json, error) {
			if (json == null || json.rtData == null || json.rtData.length == 0) {
				return;
			}
			jq(_this).each(function(_i, obj) {
				 for(var i = 0; i < json.rtData.length; i ++) {
					 var item = json.rtData[i];
					 var opt = new Option(item.classCode + " " + item.classDesc, item.classCode);
//					 if (item.classCode == "CHN") {
//						 opt.selected = true;
//					 }
					 obj.options.add(opt);
				 }
			});
			if (callback) {
				callback();
			}
		});
	};
	jq.fn.preview = function(imgCss){
		var xOffset = 10;
		var yOffset = 20;
		var w = jq(window).width();
		jq(this).each(function() {
			jq(this).unbind().hover(function(e) {
				if (/.png$|.gif$|.jpg$|.bmp$/.test(jq(this).attr("src"))) {
					jq("body").append("<div id='imgPreviewTemp'><div><img src='" + jq(this).attr('src') + "' /><p>" + jq(this).attr('data-title') + "</p></div></div>");
				} else {
					jq("body").append("<div id='imgPreviewTemp'><div><p>" + jq(this).attr('title') + "</p></div></div>");
				}
				jq("#imgPreviewTemp").css({
					position : "absolute",
					padding : "4px",
					border : "1px solid #f3f3f3",
					backgroundColor : "#eeeeee",
					top : (e.pageY - yOffset) + "px",
					zIndex : 1000
				});
				jq("#imgPreviewTemp > div").css({
					padding : "5px",
					backgroundColor : "white",
					border : "1px solid #cccccc"
				});
				jq("#imgPreviewTemp > div > img").css(imgCss);
				jq("#imgPreviewTemp > div > p").css({
					textAlign : "center",
					fontSize : "12px",
					padding : "8px 0 3px",
					margin : "0"
				});
				if(e.pageX < w/2){
					jq("#imgPreviewTemp").css({
						left: e.pageX + xOffset + "px",
						right: "auto"
					}).fadeIn("fast");
				} else {
					jq("#imgPreviewTemp").css("right",(w - e.pageX + yOffset) + "px").css("left", "auto").fadeIn("fast");	
				}
			}, function() {
				jq("#imgPreviewTemp").remove();
			}).mousemove(function(e){
				jq("#imgPreviewTemp").css("top",(e.pageY - xOffset) + "px");
				if(e.pageX < w/2){
					jq("#imgPreviewTemp").css("left", (e.pageX + yOffset) + "px").css("right","auto");
				}else{
					jq("#imgPreviewTemp").css("right", (w - e.pageX + yOffset) + "px").css("left","auto");
				}
			});						  
		});
	};
})(jQuery);
var MyNameSpace = {
	_selectObj : null
};
(function(jq) {
	jq.fn.extend({
		Select : function(options) {
			var initDefault = {
				isDefault : true,
				values : [],
				defaultText : "未选择",
				defaultFristText : "全选",
				defaultVal : "",
				isMore : false,
				selectedVal : "",
				callback : null
			};
			var opt = jQuery.extend(initDefault, options);
			if (opt.isMore) {
				opt.isDefault = true;
			}
			var createBox = function() {
					var box = jq("<div>");
					var headBox = jq("<div>");
					var btnBox = jq("<span>");
					var ulBox = jq("<ul>");
					box.addClass("sui-select");
					box.attr("value", "");
					headBox.addClass("sui-select-btn");
					btnBox.addClass("select-val");
					headBox.unbind().bind("click", function(event) {
						if (jq(this).parent().hasClass("disable")) {
							return false;
						}
						var dialog = jq(this).parent().find("ul");
						if (dialog.is(":visible")) {
							dialog.hide();
						} else {
							dialog.show();
						}
						MyNameSpace._selectObj = jq(this).parent();
						event.stopPropagation();
					});
					
					var defaultText = opt.defaultText;
					if (opt.isDefault) {
						var len = opt.values.length;
						var arr = opt.values;
						if (opt.isMore) {
							defaultText = opt.defaultFristText;
						}
						arr.splice(0, 0, {name : defaultText, value : opt.defaultVal});
						opt.values = arr;
						btnBox.attr("value", opt.defaultVal);
						btnBox.text(opt.defaultText);
					} else {
						if (opt.values.length > 0) {
							btnBox.attr("value", opt.values[0]['value']);
							btnBox.text(opt.values[0]['name']);
							if (opt.selectedVal == null ||opt.selectedVal == "") {
								opt.selectedVal = opt.values[0]['value'];
							}
						}
					}
					headBox.append(btnBox);
					box.append(headBox);
					var len = opt.values.length;
					opt.selectedVal = opt.selectedVal + "";
					console.log(opt.selectedVal);
					var deValues = opt.selectedVal.split(",");
					var deValueJson = {};
					for (var i = 0; i < deValues.length; i ++) {
						deValueJson[deValues[i]] = true;
					}
					for (var i = 0; i < len; i ++) {
						var item = opt.values[i];
						var option = jq("<li>");
						option.attr("value", item['value']);
						option.text(item['name']);
						if (opt.isMore) {
							if (deValueJson[item['value']]) {
								option.addClass("active");
								var name = item['name'];
								if (deValues.length > 1) {
										name = "已选择";	
								}
								if (opt.callback) {
									opt.callback(opt.selectedVal, name);
								}
								if (item['value'] == opt.defaultVal) {
									name = opt.defaultText;
								}
								btnBox.text(name);
								btnBox.attr("value", opt.selectedVal);
							}
						} else {
							if (opt.selectedVal == item['value']) {
									option.addClass("active");
									btnBox.text(item['name']);
									btnBox.attr("value", item['value']);
									if (opt.callback) {
										opt.callback(item['value'], item['name']);
									}
							}
						}
						option.unbind().bind("click", {opt : opt, item : item}, function(event) {
							var name = event.data.item['name'];
							var value = event.data.item['value'];
							var opt = event.data.opt;
							var vals  = "";
							if (opt.isMore) {
								if (jq(this).hasClass("active")) {
									jq(this).removeClass("active");
								} else {
									jq(this).addClass("active");	
								}
								var _name = opt.defaultText;
								var valObj = jq(this).parent().parent().find(".select-val");
								if (opt.isDefault && opt.defaultVal == value) {
									if (jq(this).hasClass("active")) {
										jq(this).parent().find("li").addClass("active");
										_name = "全选";
										valObj.text(_name);
									} else {
										jq(this).parent().find("li").removeClass("active");
										vals  = "";
										_name = opt.defaultText;
										valObj.text(_name);
									}
									jq(this).parent().find("li.active").each(function() {
										var value = jq(this).attr("value");
										if (value != opt.defaultVal) {
											vals += value + ",";
										}
									});
									vals = vals.length == 0 ? "" : vals.substring(0, vals.length - 1);
									valObj.attr("value", vals);
								} else {
									jq(this).parent().find("li:first").removeClass("active");
									var seLi = jq(this).parent().find("li.active");
									if (seLi.length == 0) {
											valObj.text(_name);
									} else {
										seLi.each(function() {
											var value = jq(this).attr("value");
											if (value != opt.defaultVal) {
												vals += value + ",";
											}
										});
										vals = !vals && vals.length == 0 ? "" : vals.substring(0, vals.length - 1);
										if (vals == "") {
											_name = opt.defaultText;
											valObj.text(_name);
										} else {
											_name = "已选择";
											valObj.text(_name);
										}
									}
									valObj.attr("value", vals);
								}
								if (opt.callback) {
									opt.callback(vals, _name);
								}
							} else {
								jq(this).parent().find("li").removeClass("active");
								jq(this).addClass("active");
								var valObj = jq(this).parent().parent().find(".select-val");
								valObj.attr("value", value);
								valObj.text(name);
								jq(this).parent().hide();
								if (opt.callback) {
									opt.callback(value, name);
								}
							}
							event.stopPropagation();
						});
						ulBox.append(option);
					}
					box.append(ulBox);
					return box;
			};
			jq(this).each(function(index, obj) {
				jq(obj).append(createBox());
			});
			$("*").bind("click", function() {
				if ($(".sui-select ul").is(':visible') && !MyNameSpace._selectObj[0].contains(window.event.srcElement)) {
					$(".sui-select ul").hide();
				}
			});
		},
		SearchInput : function() {
			jq(this).bind("focus", function() {
				jq(this).parent().addClass("hover");
			}).bind("blur", function() {
				jq(this).parent().removeClass("hover");
			});
		}
	});
	
})(jQuery);

jq(document).ready(function() {
	jq(".sui-date").dates();
	jq(".sui-search-input input").SearchInput();
});
