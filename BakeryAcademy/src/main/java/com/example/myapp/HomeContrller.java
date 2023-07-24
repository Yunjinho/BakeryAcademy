package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.board.service.IBoardService;
import com.example.myapp.member.dao.ICartRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeContrller {
	
	@Autowired
	ICartRepository cartRepository;
	
	@RequestMapping("/")
	public String home(HttpSession session) {
		if(session.getAttribute("memberId")!=null) {
			cartRepository.countCart((String)session.getAttribute("memberId"));
		}
		return "index";
	}

	
}
