package com.example.myapp.product.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.product.dao.ICategoryRepository;
import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.service.IProductService;

import jakarta.servlet.http.HttpSession;

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
	
	
	//상품 인서트 모달
	@RequestMapping("/board/insert-product-register")
	public String insertProductModal(Model model,HttpSession session) {
		return "/board/insert-product-modal";
	}
	
	//모달에서 페이징처리
	@RequestMapping("/board/modal")
	@ResponseBody
	public Object openPopUp(int page) {
		int productCount=5;
		List<Product> list=productService.selectProductAtModal(productCount*(page-1)+1, (productCount*page));
		int bbsCount=productService.countProductList();
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / (double)productCount);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / (double)productCount));
		int nowPageBlock = (int) (Math.ceil(page / (double)productCount));
		int startPage = (nowPageBlock - 1) * 2 + 1;
		int endPage=0;
		if(totalPage>nowPageBlock*productCount) {
			endPage=nowPageBlock*productCount;
		}else {
			endPage=totalPage;
		}
		JSONObject pagingInfo = new JSONObject();
		JSONArray result=new JSONArray();
		for(Product p:list) {
			JSONObject product = new JSONObject();
			product.put("product", p); 
			result.add(product); 
		}
		pagingInfo.put("productCount", productCount);
		pagingInfo.put("totalPageCount", totalPage);
		pagingInfo.put("nowPage", page); 
		pagingInfo.put("totalPageBlock", totalPageBlock);
		pagingInfo.put("nowPageBlock", nowPageBlock); 
		pagingInfo.put("startPage", startPage);
		pagingInfo.put("endPage", endPage); 
		result.add(pagingInfo);
		return result;
	}
	
	//모달에서 페이징처리
	@RequestMapping("/board/modal/search")
	@ResponseBody
	public Object openPopUp(String keyword, int page) {
		int productCount=5;
		List<Product> list=productService.selectSearchKeywordProduct(keyword,productCount*(page-1)+1, (productCount*page));
		int bbsCount=productService.countKeyWordProductList(keyword);
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / (double)productCount);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / (double)productCount));
		int nowPageBlock = (int) (Math.ceil(page / (double)productCount));
		int startPage = (nowPageBlock - 1) * 2 + 1;
		int endPage=0;
		if(totalPage>nowPageBlock*productCount) {
			endPage=nowPageBlock*productCount;
		}else {
			endPage=totalPage;
		}
		
		JSONObject pagingInfo = new JSONObject();
		JSONArray result=new JSONArray();
		for(Product p:list) {
			JSONObject product = new JSONObject();
			product.put("product", p); 
			result.add(product); 
		}
		pagingInfo.put("productCount", productCount);
		pagingInfo.put("totalPageCount", totalPage);
		pagingInfo.put("nowPage", page); 
		pagingInfo.put("totalPageBlock", totalPageBlock);
		pagingInfo.put("nowPageBlock", nowPageBlock); 
		pagingInfo.put("startPage", startPage);
		pagingInfo.put("endPage", endPage); 
		result.add(pagingInfo);
		return result;
	}
	
	@RequestMapping("/board/insert-product")
	@ResponseBody
	public Object boardInsertProduct(@RequestParam(value="list[]") List<Integer> list) {
		List<Product>productList=productService.selectProductListInBaord(list);
		JSONArray result=new JSONArray();
		for(Product p:productList) {
			JSONObject product = new JSONObject();
			product.put("product", p); 
			result.add(product); 
		}
		return result;
	}
}
