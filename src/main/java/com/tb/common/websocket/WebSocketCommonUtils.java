package com.tb.common.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import net.sf.json.JSONArray;

/**
 * 基础工具类，只与socket通信相关
 * @author tb
 *
 */
public class WebSocketCommonUtils {

	/**
	 * 群发消息，根据用户id
	 * @param userIds 用户ids
	 * @param message 消息内容
	 * @throws IOException
	 */
	public static void sendMessageToAllByUserIds(List<String> userIds,String message) throws IOException{
		
		if(userIds == null) {
			return;
		}
		TextMessage textMessage=new TextMessage(message);
		for(String userId : userIds) {
			
			WebSocketUserInfo userInfo=WebSocketUsersManager.getUserInfo(userId);
			if(userInfo != null) {
				List<WebSocketUserInfo> sonInfos = userInfo.getSonInfos();
				for(WebSocketUserInfo sonInfo : sonInfos) {
					sonInfo.getWsSession().sendMessage(textMessage);
				}
			}
		}
	}
	/**
	 * 群发消息
	 * @param messageInfos
	 * @throws IOException
	 */
	public static void sendMessageToAll(List<WebSocketMessageInfo> messageInfos) throws IOException{
		
		TextMessage textMessage=new TextMessage(JSONArray.fromObject(messageInfos).toString());
		Collection<WebSocketUserInfo> userInfos=WebSocketUsersManager.getAllUserInfosList();
		for(WebSocketUserInfo userInfo : userInfos){
			
			List<WebSocketUserInfo> sonInfos = userInfo.getSonInfos();
			for(WebSocketUserInfo sonInfo : sonInfos) {
				sonInfo.getWsSession().sendMessage(textMessage);
			}
		}
	}
	/**
	 * 群发消息
	 * @param messageInfos
	 * @throws IOException
	 */
	public static void sendMessageToAll(WebSocketSession wsSession, WebSocketMessage<?> wsMessage) throws IOException{
		//消息列表
		List<WebSocketMessageInfo> messageInfos=new ArrayList<WebSocketMessageInfo>();
		String userName=String.valueOf(wsSession.getAttributes().get("userName"));
		//聊天消息
		WebSocketMessageInfo messageInfo=new WebSocketMessageInfo();
		messageInfo.setMessage(wsMessage.getPayload()+"");
		messageInfo.setMessageType(WebSocketMessageInfo.MESSAGETYPE_CHATMESS);
		messageInfo.setSendUserName(userName);
		messageInfos.add(messageInfo);
		Collection<WebSocketUserInfo> userInfos=WebSocketUsersManager.getAllUserInfosList();
		TextMessage textMessage=new TextMessage(JSONArray.fromObject(messageInfos).toString());
		for(WebSocketUserInfo userInfo : userInfos){
			List<WebSocketUserInfo> sonInfos = userInfo.getSonInfos();
			for(WebSocketUserInfo sonInfo : sonInfos) {
				sonInfo.getWsSession().sendMessage(textMessage);
			}
		}
	}
	public static void sendMessageToOne(String userId,String message) throws IOException{
		
		TextMessage textMessage=new TextMessage(message);
		
		WebSocketUserInfo userInfo=WebSocketUsersManager.getUserInfo(userId);
		if(userInfo != null) {
			List<WebSocketUserInfo> sonInfos = userInfo.getSonInfos();
			for(WebSocketUserInfo sonInfo : sonInfos) {
				sonInfo.getWsSession().sendMessage(textMessage);
			}
		}
		
	}
	
}
