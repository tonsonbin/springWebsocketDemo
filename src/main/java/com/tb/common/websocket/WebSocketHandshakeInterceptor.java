package com.tb.common.websocket;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * 进入handler之前的拦截
 * @author tb
 * @time 2016-10-31
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private static Logger logger = Logger.getLogger(WebSocketHandshakeInterceptor.class);
    private int maxUsers=200;
    /**
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object
                > attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null&&WebSocketUsersManager.getAllUserInfos().size()<maxUsers) {
                //使用userName区分WebSocketHandler，以便定向发送消息
                //String userName = (String) session.getAttribute(Constants.SESSION_USERNAME);
                /*attributes.put("userName",Calendar.getInstance().getTimeInMillis());*/
            	
		        String userId = String.valueOf(Calendar.getInstance().getTimeInMillis());
		        
		        WebSocketUserInfo userInfo = new WebSocketUserInfo();
		        userInfo.setUserId(userId);
		        userInfo.setUserName(userId+"name");
		        
		        String sonId = String.valueOf(Calendar.getInstance().getTimeInMillis());
		        WebSocketUserInfo sonInfo = new WebSocketUserInfo();
		        sonInfo.setUserId(sonId);
		        sonInfo.setUserName(userId+"name");
		        sonInfo.setParentId(userInfo.getUserId());
		        
		        userInfo.setSonInfo(sonInfo);
		        
		        attributes.put("userInfo",userInfo);
		        
		        System.out.println("webSocketHandshakeInterceptor userId："+userId+"，sonId："+sonId);
                return true;
            }else return false;
        }else return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
