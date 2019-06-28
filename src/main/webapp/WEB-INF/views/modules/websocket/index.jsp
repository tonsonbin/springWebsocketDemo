<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>主页</title>
<script src="/static/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/websocket/socketjs.min.1.0.1.js" type="text/javascript"></script>
<script src="/static/websocket/tbwebsocketbase.js?v=1.3" type="text/javascript"></script>
<script src="/static/websocket/judgeUA.js" type="text/javascript"></script>
<script type="text/javascript">
var webSocketCal;
var userInfo=null;
var fiConnectNum = 100;
var limitConnectNum = fiConnectNum;//重新连接指定次数
function initWebSocket(){
	/* if(UA()=="IE"){
		alert("IE 不支持，使用chrome浏览器");
		return;
	} */
	webSocketCal=new tbwebsocket();
	webSocketCal.webSocketUrl="ws://${pageContext.request.serverName}:${pageContext.request.serverPort }/websocket/webSocketServer";
	webSocketCal.sockjsUrl="http://${pageContext.request.serverName}:${pageContext.request.serverPort }/websocket/sockjs/webSocketServer";
	webSocketCal.setOnerrorEvnt(function(){
		//console.log("error");
	});
	webSocketCal.setOncloseEvnt(function(){

		console.log("close");
		limitConnectNum = limitConnectNum - 1;
		if(limitConnectNum >= 0){

			layConntect(limitConnectNum);
			
		}
	});
	webSocketCal.setMessageEvnt(function(jsonData){
		limitConnectNum = fiConnectNum;//链接成功，重置可链接次数
		//alert(JSON.stringify(jsonData));
		for(var messageIndex in jsonData){
			var message=jsonData[messageIndex];
			var messageType=message.messageType;
			if(messageType=="6"){//提示有新的订单
    			//alert("have a new order");
				//openDialog({"title":"订单提醒","content":"有新的订单哦"});
				playNewOrder();
			}else if(messageType=="7"){//普通的弹窗提示
				//弹窗提醒
				normalDialog(message.message);
			
			}else if(messageType=="5"&&userInfo==null){//携带的个人信息
				userInfo=message.message;
				console.log(userInfo);
				$("#message").append("个人信息："+userInfo+"</br>");
			}else if(messageType=="3"){//系统消息
				console.log(message.message);

				$("#message").append("系统消息："+message.message+"</br>");
			}else if(messageType=="1"){//普通聊天信息

				$("#message").append("聊天消息："+message.message+"</br>");
			};
			/* if(messageType=="3"){//系统消息
				sysMess(message.message);
			}else if(messageType=="2"){//用户信息列表
				document.getElementById("left_box").innerHTML="";
				var jsonUserInfos=eval('('+message.message+')');
				for(var key in jsonUserInfos){
					user=jsonUserInfos[key];
					disUserInfo(user.userName);
				}
			}else if(messageType=="1"){//普通聊天信息
				if(message.sendUserName==userInfo.userName){//用户自己的消息
					myselfMess(message.sendUserName,message.message);
				}else
					chatMess(message.sendUserName,message.message);
			}else if(messageType=="5"&&userInfo==null){//携带的个人信息
				userInfo=message.message;
				userInfo=eval('('+userInfo+')');
			};  */
		}
		//alert("callBack"+JSON.stringify(jsonData));
		/* var scrollTop=$("#cb_disMessage").scrollTop();
		var scrollHeight=$("#cb_disMessage")[0].scrollHeight;
		var height=$("#cb_disMessage").height();
		if(scrollHeight-scrollTop-height<135){
			$("#cb_disMessage").scrollTop($("#cb_disMessage")[0].scrollHeight);
		} */
	});
		webSocketCal.init();
	};
function layConntect(num){
	console.log("正在重连，剩余重连次数："+num);
	setTimeout(initWebSocket,5000);
	
}
$(function(){
	
	initWebSocket();
	
});
function send(){
    if (webSocketCal.webSocket != null) {
        var message = $("#sendMessage").val();
        if(message != ""){
            $("#sendMessage").val("");
            webSocketCal.webSocket.send(message);
        }
    } else {
        alert("未与服务器链接.");
    };
};	
</script>
</head>
<script>
	var playNewOrder = function(){
		var audioObj = $("#videoNewOrder")[0];
		audioObj.play();
	}
</script>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow: hidden">
<audio style="display:none" src="${ctxStatic }/source/audio/newOrder.mp3" id="videoNewOrder"></audio>
</body>
<div id="message"></div>

<div><input id="sendMessage"/><button onclick="send()">发送</button></div>
</html>
