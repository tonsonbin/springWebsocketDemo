package com.tb.common.websocket;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketUserInfo {

	private WebSocketSession wsSession;
	
	private String parentId;
	private String userId;
	private String userName;
	private String sendMessageTime;
	private boolean isOnLine;
	private String lastOnLineTime;
	private List<WebSocketUserInfo> sonInfos;
	private WebSocketUserInfo sonInfo;
	
	
	public WebSocketSession getWsSession() {
		return wsSession;
	}
	public void setWsSession(WebSocketSession wsSession) {
		this.wsSession = wsSession;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSendMessageTime() {
		return sendMessageTime;
	}
	public void setSendMessageTime(String sendMessageTime) {
		this.sendMessageTime = sendMessageTime;
	}
	
	public boolean isOnLine() {
		return isOnLine;
	}
	public void setOnLine(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}
	public String getLastOnLineTime() {
		return lastOnLineTime;
	}
	public void setLastOnLineTime(String lastOnLineTime) {
		this.lastOnLineTime = lastOnLineTime;
	}
	public List<WebSocketUserInfo> getSonInfos() {
		return sonInfos;
	}
	public void setSonInfos(List<WebSocketUserInfo> sonInfos) {
		this.sonInfos = sonInfos;
	}
	public WebSocketUserInfo getSonInfo() {
		return sonInfo;
	}
	public void setSonInfo(WebSocketUserInfo sonInfo) {
		this.sonInfo = sonInfo;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
