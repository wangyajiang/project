var AniBase = (function() {
	var Config = {
		clazz: "sui-ani",
		aniNames : "ani-names",//规定 @keyframes 动画的名称，以“ ”分割
		aniTimer : "ani-timer",//动画之间间隔时长（毫秒）
		duration : "ani-duration",//规定动画完成一个周期所花费的秒或毫秒
		timingFunction : "ani-timing-function",//规定动画的速度曲线。
		delay : "ani-delay",//规定动画何时开始。
		iterationCount : "ani-iteration-count",//规定动画被播放的次数。
		direction : "ani-direction",//规定动画是否在下一周期逆向地播放。
		fillMode : "ani-fill-mode"//：规定对象动画时间之外的状态。
	};
	var defaultConfig = {//默认值
		aniNames : null,
		aniTimer : 1000,
		duration : "1s",
		timingFunction : "ease",
		delay : "0s",
		iterationCount : "1",
		direction : "normal",
		fillMode : "backwards"
	};
	
	var cssArr = ["animation-name", "animation-duration", "animation-timing-function", "animation-delay", "animation-iteration-count", "animation-direction", "animation-fill-mode"];
	
	function init(clazz) {
		if (clazz) {
			Config.clazz = clazz;
		}
		start();
	};
	
	function delBlank(arr) {
		arr = arr || [];
		var temp = [];
		for (var i = 0; i < arr.length; i ++) {
			if (arr[i]) {
				temp.push(arr[i]);
			}
		}
		arr = temp;
		return arr;
	};
	
	function retArr(val, defaultVal, index) {
		if (val == null || val == undefined || val == 'undefined' || val == '') {
			return defaultVal;
		}
		var arr = val.trim().split(" ");
		arr = delBlank(arr);
		if (arr.length <= index) {
			return defaultVal;
		}
		return arr[index];
	};
	
	function setClass(self, aniNameArr, i) {
		i = i || 0;
		var len = aniNameArr.length;
		if (i >= len) {
			return;	
		}
		
		var aniName = self.getAttribute(Config.aniNames);
		var aniTimer = self.getAttribute(Config.aniTimer);
		var duration = self.getAttribute(Config.duration);
		var timingFunction = self.getAttribute(Config.timingFunction);
		var delay = self.getAttribute(Config.delay);
		var iterationCount = self.getAttribute(Config.iterationCount);
		var direction = self.getAttribute(Config.direction);
		var fillMode = self.getAttribute(Config.fillMode);
		
		var arr = [];
		arr[0] = aniNameArr[i];
		arr[1] = retArr(duration, defaultConfig.duration, i);
		arr[2] = retArr(timingFunction, defaultConfig.timingFunction, i);
		arr[3] = retArr(delay, defaultConfig.delay, i);
		arr[4] = retArr(iterationCount, defaultConfig.iterationCount, i);
		arr[5] = retArr(direction, defaultConfig.duration, i);
		arr[6] = retArr(fillMode, defaultConfig.fillMode, i);
		
		var browers = ["", "-webkit-", "-moz-", "-o-"];
		var $self = $(self);
		for (var k = 0; k < cssArr.length; k ++) {
			for (var j = 0; j < browers.length; j ++) {
				var cssName = browers[j] + cssArr[k];
				$self.css(cssName, arr[k]);
			}
		}
		if (len > 1 && i < len) {
			var time = retArr(aniTimer, defaultConfig.aniTimer, i);
			setTimeout(function() {
				setClass(self, aniNameArr, i);
			}, time);
		}
		if (len == 1 || i < len - 1) {
			//self.removeAttribute(Config.aniNames);
		}
		i ++;
		if (typeof(Config.clazz) == 'string') {
			$("." + Config.clazz).show();
		} else {
			Config.clazz.show();
		}
		
	};
	
	function start() {
		var aniBox = [];
		if (typeof(Config.clazz) == 'string') {
			aniBox = document.getElementsByClassName(Config.clazz);
		} else {
			aniBox = Config.clazz;
		}
		for (var i = 0; i < aniBox.length; i ++) {
			var self = aniBox[i];
			var aniNames = self.getAttribute(Config.aniNames);
			if (!aniNames) {
				continue;	
			}
			var aniNameArr =  aniNames.split(" ");
			aniNameArr = delBlank(aniNameArr);
			if (aniNameArr.length <= 0) {
				return;	
			}
			setClass(self, aniNameArr);
		}
	};
	
	return {
		addAnis : init
	};
})();
AniBase.addAnis();