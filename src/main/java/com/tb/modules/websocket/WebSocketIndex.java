package com.tb.modules.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tb
 * @time 2018-09-18
 */
@Controller
@RequestMapping("websocket")
public class WebSocketIndex {

	@RequestMapping("index")
	public String index() {
		
		return "modules/websocket/index";
	}
}
