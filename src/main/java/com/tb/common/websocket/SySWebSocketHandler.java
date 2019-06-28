package com.tb.common.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import net.sf.json.JSONObject;

/**
 * 消息处理类
 * @author tb
 * @time 2016-10-31
 */
@Component
public class SySWebSocketHandler implements WebSocketHandler {

    private static final Logger logger=Logger.getLogger(SySWebSocketHandler.class);

    /**
     * 断开链接
     * 1、可以记录用户下线时间
     */
	@Override
	public void afterConnectionClosed(WebSocketSession wsSession, CloseStatus closeStatus)
			throws Exception {
		
		WebSocketUserInfo userInfo =(WebSocketUserInfo) wsSession.getAttributes().get("userInfo");
		
		String userName=userInfo.getUserName();
		String userId = userInfo.getUserId();
		String sonId = userInfo.getSonInfo().getUserId();
		
		System.out.println(userName+" isClose ，userId = "+userId+",sonId="+sonId);
		
		WebSocketUsersManager.removeUser(userInfo);
		
		//消息列表
		List<WebSocketMessageInfo> messageInfos=new ArrayList<WebSocketMessageInfo>();
		//发送系统消息提示某某下线了
		WebSocketMessageInfo messageInfo=new WebSocketMessageInfo();
		messageInfo.setMessage(userName+" is offline ");
		messageInfo.setMessageType(WebSocketMessageInfo.MESSAGETYPE_SYSMESS);
		messageInfos.add(messageInfo);
		//发送最新的用户信息消息（在线用户信息）
		messageInfo=new WebSocketMessageInfo();
		Map<String, Map<String, Object>> userInfos=WebSocketUsersManager.getAllUserInfosCom();
		messageInfo.setMessage(JSONObject.fromObject(userInfos).toString());
		messageInfo.setMessageType(WebSocketMessageInfo.MESSAGETYPE_USERINFO);
		messageInfos.add(messageInfo);
		WebSocketCommonUtils.sendMessageToAll(messageInfos);
	}

	/**
	 * 打开链接后
	 * 1、可以立即向该用户推送未读消息
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession wsSession)
			throws Exception {
		
		WebSocketUserInfo userInfo =(WebSocketUserInfo) wsSession.getAttributes().get("userInfo");
		
		String userName=userInfo.getUserName();
		String userId = userInfo.getUserId();
		String sonId = userInfo.getSonInfo().getUserId();
		
		System.out.println(userName+" isLogin ，userId = "+userId+",sonId="+sonId);
		//添加该用户
		Map<String, Object> baseUserInfo=WebSocketUsersManager.addUser(userInfo, wsSession);
		//消息列表
		List<WebSocketMessageInfo> messageInfos=new ArrayList<WebSocketMessageInfo>();
		//发送系统消息提示某某上线了
		WebSocketMessageInfo messageInfo=new WebSocketMessageInfo();
		messageInfo.setMessage(userName+" is login ");
		messageInfo.setMessageType(WebSocketMessageInfo.MESSAGETYPE_SYSMESS);
		messageInfos.add(messageInfo);
		//发送最新的用户信息消息（在线用户信息）
		messageInfo=new WebSocketMessageInfo();
		Map<String, Map<String, Object>> userInfos=WebSocketUsersManager.getAllUserInfosCom();
		messageInfo.setMessage(JSONObject.fromObject(userInfos).toString());
		messageInfo.setMessageType(WebSocketMessageInfo.MESSAGETYPE_USERINFO);
		messageInfos.add(messageInfo);
		//发送自己的用户信息
		messageInfo=new WebSocketMessageInfo();
		messageInfo.setMessage(JSONObject.fromObject(baseUserInfo).toString());
		messageInfo.setMessageType(WebSocketMessageInfo.MESSAGETYPE_MYSELFINFO);
		messageInfos.add(messageInfo);
		//未读消息处理
		//TODO
		WebSocketCommonUtils.sendMessageToAll(messageInfos);
		
	}

	/**
	 * 收到消息后
	 */
	@Override
	public void handleMessage(WebSocketSession wsSession, WebSocketMessage<?> wsMessage)
			throws Exception {
		WebSocketCommonUtils.sendMessageToAll(wsSession, wsMessage);
	}

	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable arg1)
			throws Exception {
		arg1.printStackTrace();
	}

	@Override
	public boolean supportsPartialMessages() {
		System.out.println("xxx");
		return false;
	}
    

    
}
