package com.tb.common.websocket;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.collect.Lists;

/**
 * 用户管理类
 * @author tb
 * @time 2016-10-31
 */
public class WebSocketUsersManager {

	private static int maxUsers=10000;
	private static Map<String,WebSocketUserInfo> userInfos=new HashMap<String,WebSocketUserInfo>();
	private static Map<String,Map<String,Object>> userBaseInfos=new HashMap<String,Map<String,Object>>();
	
	/**
	 * 添加用户
	 * @param userName
	 * @param wsSession
	 * @return
	 * TODO 做已存在用户判断
	 */
	public static Map<String, Object> addUser(WebSocketUserInfo userInfo,WebSocketSession wsSession){
		
		Map<String,Object> baseUserInfo=new HashMap<String,Object>();
		String userId = userInfo.getUserId();
		String userName = userInfo.getUserName();
		
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(userName)) {
			System.out.println("websocket addUser -->用户id和用户名称不能为空");
			throw new RuntimeException("websocket addUser -->用户id和用户名称不能为空");
		}
		
		if(!userInfos.containsKey(userId)){//不存在用户组，添加该用户会话
			
			WebSocketUserInfo sonInfo = userInfo.getSonInfo();
			sonInfo.setWsSession(wsSession);
			
			List<WebSocketUserInfo> sonInfos = Lists.newArrayList();
			sonInfos.add(sonInfo);
			userInfo.setSonInfos(sonInfos);
			
			userInfos.put(userId, userInfo);
			
			
			baseUserInfo.put("userName", userName);
			baseUserInfo.put("sendMessageTime", userInfo.getSendMessageTime());
			baseUserInfo.put("isOnLine", userInfo.isOnLine());
			baseUserInfo.put("lastOnLineTime", userInfo.getLastOnLineTime());
			userBaseInfos.put(userName, baseUserInfo);
		}else {//存在用户组
			//取已经存在的子会话信息
			WebSocketUserInfo userInfoIn = userInfos.get(userId);
			List<WebSocketUserInfo> sonInfos = userInfoIn.getSonInfos();
			//取新的子会话
			WebSocketUserInfo sonInfo = userInfo.getSonInfo();
			sonInfo.setWsSession(wsSession);
			sonInfos.add(sonInfo);
			//重置子会话列表
			userInfoIn.setSonInfos(sonInfos);
			userInfos.put(userId, userInfoIn);
			
		}
		return baseUserInfo;
	}
	
	/**
	 * 获取所有用户信息
	 * @return
	 */
	public static Map<String,WebSocketUserInfo> getAllUserInfos(){
		return userInfos;
	}
	/**
	 * 获取所有用户基础信息
	 * @return
	 */
	public static Map<String,Map<String,Object>> getAllUserInfosCom(){
		return userBaseInfos;
	}
	public static  Collection<WebSocketUserInfo> getAllUserInfosList(){
		return userInfos.values();
	}
	
	
	/**
	 * 获取一个用户的信息
	 * @param userName
	 * @return
	 */
	public static WebSocketUserInfo getUserInfo(String userId){
		if(userInfos.containsKey(userId)){
			return userInfos.get(userId);
		}else return null;
	}
	
	/**
	 * 修改用户信息
	 * @param userName
	 * @param userInfo
	 */
	public static void setUserInfo(String userName,WebSocketUserInfo userInfo){
		userInfos.put(userName, userInfo);
		Map<String,Object> baseUserInfo=new HashMap<String,Object>();
		baseUserInfo.put("userName", userName);
		baseUserInfo.put("sendMessageTime", userInfo.getSendMessageTime());
		baseUserInfo.put("isOnLine", userInfo.isOnLine());
		baseUserInfo.put("lastOnLineTime", userInfo.getLastOnLineTime());
		userBaseInfos.put(userName, baseUserInfo);
	}
	
	/**
	 * 移除用户
	 * @param userName
	 */
	public static void removeUser(WebSocketUserInfo userInfo){
		
		String userId = userInfo.getUserId();
		String userName = userInfo.getUserName();
		String sonId = userInfo.getSonInfo().getUserId();
		
		if(userInfos.containsKey(userId)){
			
			WebSocketUserInfo userInfoIn = userInfos.get(userId);
			List<WebSocketUserInfo> sonInfos = userInfoIn.getSonInfos();
			List<WebSocketUserInfo> newSonInfos = Lists.newArrayList();
			for(WebSocketUserInfo sonInfo : sonInfos) {
				if(!sonId.equals(sonInfo.getUserId())) {//移除子对话
					newSonInfos.add(sonInfo);
				}
			}
			
			userInfoIn.setSonInfos(newSonInfos);
			
			userInfos.put(userId, userInfoIn);//重置会话
			
			if(sonInfos.size()<=0) {//已经没有对话了
				userInfos.remove(userId);
			}
		}
	}
}
