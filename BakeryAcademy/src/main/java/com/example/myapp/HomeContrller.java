package com.example.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeContrller {
	@RequestMapping("/")
	public String home(HttpSession session) {
		session.setAttribute("memberId", "admin");
		session.setAttribute("isAdmin", 1);
		if(session.getAttribute("memberId")==null) {
			session.setAttribute("memberId", null);
		}
		return "index";
	}
}
