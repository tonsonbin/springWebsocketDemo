tbwebsocket=function(){
	this.webSocket=null;
	this.webSocketUrl=null;
	this.sockjsUrl=null;
	//私有function
	var onMessage;
	var onOpen;
	var onError;
	var onClose;
	this.init=function(){
		if(this.webSocketUrl == null || this.webSocketUrl == undefined || this.webSocketUrl == ''){
			alert("webSocketUrl undefied");
		}else if(this.sockjsUrl == null || this.sockjsUrl == undefined || this.sockjsUrl == ''){
			alert("sockjsUrl undefied");
		}
		if ('WebSocket' in window) {
			this.webSocket=new WebSocket(this.webSocketUrl);
		} else if ('MozWebSocket' in window) {
			this.webSocket = new MozWebSocket(this.webSocketUrl);
		} else {
			this.webSocket = new SockJS(this.sockjsUrl);
		}
		this.webSocket.onmessage = function(evnt) {
			var jsonData=eval("("+evnt.data+")");
			onMessage(jsonData);
		};
		this.webSocket.onopen=function(evnt){
			if(onOpen){
				onOpen(evnt);
			}
		};
		this.webSocket.onerror=function(evnt){
			if(onError){
				onError(evnt);
			}
		};
		this.webSocket.onclose=function(evnt){
			if(onClose){
				onClose(evnt);
			}
		};
	};

	this.setMessageEvnt=function(messageEvnt){
		onMessage=messageEvnt;
	};
	this.setOpenEvnt=function(openEvnt){
		onOpen=openEvnt;
	};
	this.setOnerrorEvnt=function(errorEvnt){
		onError=errorEvnt;
	};
	this.setOncloseEvnt=function(closeEvnt){
		onClose=closeEvnt;
	};
};