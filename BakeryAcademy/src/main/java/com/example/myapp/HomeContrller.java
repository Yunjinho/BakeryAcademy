package com.example.myapp;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.member.service.ICartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeContrller {
	
	@Autowired
	ICartService cartService;
	
	@RequestMapping("/")
	public String home(HttpSession session) {
		return "index";
	}

	//여기
	@RequestMapping("/cart-count")
	@ResponseBody
	public JSONObject cartCount(HttpSession session) {
		JSONObject jobj = new JSONObject();
		jobj.put("cartCount", cartService.countCart((String)session.getAttribute("memberId")));
		return jobj;
	}
}
