package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
