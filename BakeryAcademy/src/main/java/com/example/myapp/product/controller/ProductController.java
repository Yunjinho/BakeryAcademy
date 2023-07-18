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
import com.example.myapp.product.model.Category;
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
	
	
	//상품 등록페이지로 이동
	@RequestMapping(value="/admin/insert-product",method=RequestMethod.GET)
	public String productInsert(Model model) {
		model.addAttribute("category", categoryService.selectAllCategory());
		return "admin/product-register";
	}
	// 상품 등록 처리
	@RequestMapping(value="/admin/insert-product",method=RequestMethod.POST)
	public String productInsert(@Validated Product product,Model model) {
		productService.insertProduct(product);
		return "redirect:/";
	}
	//상품 업데이트 페이지로 이동
	@RequestMapping(value="/admin/update-product",method=RequestMethod.GET)
	public String productUpdate(@RequestParam int productId, Model model) {
		Product product=productService.selectProduct(productId);
		model.addAttribute("category", categoryService.selectAllCategory());
		model.addAttribute("product", product);
		return "admin/product-update";
	}
	//상품 업데이트 처리
	@RequestMapping(value="/admin/update-product",method=RequestMethod.POST)
	public String productUpdate(@Validated Product product, Model model) {
		productService.updateProduct(product);
		return "redirect:/";
	}
	//카테고리 목록 리스트 페이지로 이동
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String categoryView(Model model) {
		model.addAttribute("categoryList", categoryService.selectAllCategory());
		return "admin/category-view";
	}
	//카테고리 등록 페이지로 이동
	@RequestMapping(value="/admin/insert-category", method=RequestMethod.GET)
	public String categoryInsert() {
		return "admin/category-register";
	}
	//카테고리 등록
	@RequestMapping(value="/admin/insert-category",method=RequestMethod.POST)
	public String categoryInsert(@Validated Category category,Model model) {
		categoryService.insertCategory(category);
		model.addAttribute("categoryList", categoryService.selectAllCategory());
		return "redirect:/admin/category";
	}
	//카테고리 업데이트 페이지로 이동
	@RequestMapping(value="/admin/update-category", method=RequestMethod.GET)
	public String categoryUpdate(@RequestParam int categoryId,Model model) {
		Category category=new Category();
		category=categoryService.selectCategory(categoryId);
		model.addAttribute("category", category);
		return "admin/category-update";
	}
	//카테고리 업데이트
	@RequestMapping(value="/admin/update-category",method=RequestMethod.POST)
	public String categoryUpdate(@Validated Category category,Model model) {
		categoryService.updateCategory(category);
		model.addAttribute("categoryList", categoryService.selectAllCategory());
		return "redirect:/admin/category";
	}
	
	//카테고리 삭제
	@RequestMapping(value="/admin/delete-category",method=RequestMethod.GET)
	public String categoryDelete(@RequestParam int categoryId,Model model) {
		categoryService.deleteCategory(categoryId);
		model.addAttribute("categoryList", categoryService.selectAllCategory());
		return "redirect:/admin/category";
	}
	
}
