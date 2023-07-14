package com.example.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeContrller {
	@RequestMapping("/")
	public String home() {
		return "index";
	}
}
