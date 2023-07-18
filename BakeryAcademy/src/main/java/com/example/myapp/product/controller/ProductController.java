package com.example.myapp.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.product.dao.ICategoryRepository;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.service.IProductService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
@Controller
public class ProductController {
	@Autowired
	ICategoryRepository categoryService;
	@Autowired
	IProductService productService;
	
	@RequestMapping(value="/admin/insert-product",method=RequestMethod.GET)
	public String productInsert(Model model) {
		model.addAttribute("category", categoryService.selectAllCategory());
		model.addAttribute("header", "header");
		return "admin/product-register";
	}
	
	@RequestMapping(value="/admin/insert-product",method=RequestMethod.POST)
	public String productInsert(@Validated Product product,Model model) {
		productService.insertProduct(product);
		return "redirect:/";
	}
	
	@RequestMapping(value="/admin/update-product",method=RequestMethod.GET)
	public String productUpdate(@RequestParam int productId, Model model) {
		Product product=productService.selectProduct(productId);
		model.addAttribute("category", categoryService.selectAllCategory());
		model.addAttribute("product", product);
		return "admin/product-update";
	}

	@RequestMapping(value="/admin/update-product",method=RequestMethod.POST)
	public String productUpdate(@Validated Product product, Model model) {
		productService.updateProduct(product);
		return "redirect:/";
	}
}
