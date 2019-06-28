//判断浏览器
UA=function(){
	var ua = navigator.userAgent;
	var isWX = ua.match(/MicroMessenger\/([\d\.]+)/), isQQ = ua.match(/QQ\/([\d\.]+)/), isQZ = ua.indexOf("Qzone/") !== -1;
	var isOpera = ua.indexOf("Opera") > -1;
	var isFireFox=ua.indexOf("Firefox") > -1;
	var isChrome=ua.indexOf("Chrome") > -1;
	var isSafari=ua.indexOf("Safari") > -1;
	var isIe=ua.indexOf("compatible") > -1 && ua.indexOf("MSIE") > -1 && !isOpera;
    
	if(isWX){//微信
		return "WX";
	}else if(isQQ){//qq
		return "QQ";
	}else if(isQZ){//QQ空间
		return "QZ";
	}else if(isOpera){
		return "OPERA";
	}else if(isFireFox){
		return "FIREFOX";
	}else if(isChrome){
		return "CHROME";
	}else if(isSafari){
		return "SAFARI";
	}else if(isIe){
		return "IE";
	}
};