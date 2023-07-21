package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.board.service.IBoardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeContrller {
	
	@Autowired
	IBoardService boardService;
	
	@RequestMapping("/")
	public String home(HttpSession session) {
		session.setAttribute("memberId", "my");
		session.setAttribute("isAdmin", 0);
		if(session.getAttribute("memberId")==null) {
			session.setAttribute("memberId", null);
		}
		return "index";
	}

	
}
