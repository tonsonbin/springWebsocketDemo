package com.tb.common.websocket;

import java.util.Calendar;

public class WebSocketMessageInfo {

	public static final String MESSAGETYPE_CHATMESS="1";//聊天消息
	public static final String MESSAGETYPE_USERINFO="2";//用户信息
	public static final String MESSAGETYPE_SYSMESS="3";//系统消息
	public static final String MESSAGETYPE_MYSELFMESS="4";//我自己的消息
	public static final String MESSAGETYPE_MYSELFINFO="5";//我自己的信息
	
	public static final String MESSAGETYPE_NEWORDER="6";//新的订单
	
	
	private String message;
	private String sendTime;
	private String messageType;//1、聊天框的消息，2、用户信息消息 3、系统消息 4、自己发送的消息 5、用户信息
	private String sendUserName;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSendTime() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_YEAR)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	
	
}
