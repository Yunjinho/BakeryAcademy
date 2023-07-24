package com.example.myapp.product.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.product.dao.ICategoryRepository;
import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;
import com.example.myapp.product.service.IProductService;
//import com.example.myapp.product.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	@Autowired
	ICategoryRepository categoryService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IProductService productService;
	
	//테스트용 매핑
	@RequestMapping("/test/{categoryId}/{page}")
//	@RequestMapping("/test")
	public String getTestCSS(@PathVariable int categoryId, @PathVariable int page, HttpSession session, Model model) {
//	public String getTestCSS(@RequestParam int categoryId, @RequestParam int page, HttpSession session, Model model) {
		session.setAttribute("page", page);
		model.addAttribute("categoryId", categoryId);
		List<Product> productList = productService.selectProductListByCategory(categoryId, page);
		model.addAttribute("productList", productList);
		int bbsCount = productService.selectTotalProductCountByCategory(categoryId);
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		int nowPageBlock = (int) Math.ceil(page / 10.0);
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage = 0;
		if (totalPage > nowPageBlock * 10) {
			endPage = nowPageBlock * 10;
		} else {
			endPage = totalPage;
		}
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "/product/product-original";
	}

	// 카테고리와 페이지에 따른 상품 목록으로 이동
	@RequestMapping("/product/{categoryId}/{page}")
	public String getProductListByCategory(@PathVariable int categoryId, @PathVariable int page, HttpSession session,
			Model model) {
		session.setAttribute("page", page);
		model.addAttribute("categoryId", categoryId);
		List<Product> productList = productService.selectProductListByCategory(categoryId, page);
		model.addAttribute("productList", productList);
		int bbsCount = productService.selectTotalProductCountByCategory(categoryId);
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		int nowPageBlock = (int) Math.ceil(page / 10.0);
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage = 0;
		if (totalPage > nowPageBlock * 10) {
			endPage = nowPageBlock * 10;
		} else {
			endPage = totalPage;
		}
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "product";
	}

	// 상품 등록페이지로 이동
	@RequestMapping(value = "/admin/insert-product", method = RequestMethod.GET)
	public String productInsert(Model model) {
		model.addAttribute("category", categoryService.selectAllCategory());
		return "admin/product-register";
	}

	// 상품 등록 처리
	@RequestMapping(value = "/admin/insert-product", method = RequestMethod.POST)
	public String productInsert(@Validated Product product, Model model) {
		productService.insertProduct(product);
		return "redirect:/";
	}

	// 상품 업데이트 페이지로 이동
	@RequestMapping(value = "/admin/update-product", method = RequestMethod.GET)
	public String productUpdate(@RequestParam int productId, Model model) {
		Product product = productService.selectProduct(productId);
		model.addAttribute("category", categoryService.selectAllCategory());
		model.addAttribute("product", product);
		return "admin/product-update";
	}

	// 상품 업데이트 처리
	@RequestMapping(value = "/admin/update-product", method = RequestMethod.POST)
	public String productUpdate(@Validated Product product, Model model) {
		productService.updateProduct(product);
		return "redirect:/";
	}

	// 카테고리 목록 리스트 페이지로 이동
	@RequestMapping(value = "/admin/category", method = RequestMethod.GET)
	public String categoryView(Model model) {
		model.addAttribute("categoryList", categoryService.selectAllCategory());
		return "admin/category-view";
	}

	// 카테고리 등록 페이지로 이동
	@RequestMapping(value = "/admin/insert-category", method = RequestMethod.GET)
	public String categoryInsert() {
		return "admin/category-register";
	}

	// 카테고리 등록
	@RequestMapping(value = "/admin/insert-category", method = RequestMethod.POST)
	public String categoryInsert(@Validated Category category, Model model) {
		categoryService.insertCategory(category);
		model.addAttribute("categoryList", categoryService.selectAllCategory());
		return "redirect:/admin/category";
	}

	// 카테고리 업데이트 페이지로 이동
	@RequestMapping(value = "/admin/update-category", method = RequestMethod.GET)
	public String categoryUpdate(@RequestParam int categoryId, Model model) {
		Category category = new Category();
		category = categoryService.selectCategory(categoryId);
		model.addAttribute("category", category);
		return "admin/category-update";
	}

	// 카테고리 업데이트
	@RequestMapping(value = "/admin/update-category", method = RequestMethod.POST)
	public String categoryUpdate(@Validated Category category, Model model) {
		categoryService.updateCategory(category);
		model.addAttribute("categoryList", categoryService.selectAllCategory());
		return "redirect:/admin/category";
	}

	// 카테고리 삭제
	@RequestMapping(value = "/admin/delete-category", method = RequestMethod.GET)
	public String categoryDelete(@RequestParam int categoryId, Model model) {
		categoryService.deleteCategory(categoryId);
		model.addAttribute("categoryList", categoryService.selectAllCategory());
		return "redirect:/admin/category";
	}

	// 상품 인서트 모달
	@RequestMapping("/board/insert-product-register")
	public String insertProductModal(Model model, HttpSession session) {
		return "/board/insert-product-modal";
	}

	// 모달에서 페이징처리
	@RequestMapping("/board/modal")
	@ResponseBody
	public Object openPopUp(int page) {
		int productCount = 5;
		List<Product> list = productService.selectProductAtModal(productCount * (page - 1) + 1, (productCount * page));
		int bbsCount = productService.countProductList();
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / (double) productCount);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / (double) productCount));
		int nowPageBlock = (int) (Math.ceil(page / (double) productCount));
		int startPage = (nowPageBlock - 1) * 2 + 1;
		int endPage = 0;
		if (totalPage > nowPageBlock * productCount) {
			endPage = nowPageBlock * productCount;
		} else {
			endPage = totalPage;
		}
		JSONObject pagingInfo = new JSONObject();
		JSONArray result = new JSONArray();
		for (Product p : list) {
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

	// 모달에서 페이징처리
	@RequestMapping("/board/modal/search")
	@ResponseBody
	public Object openPopUp(String keyword, int page) {
		int productCount = 5;
		List<Product> list = productService.selectSearchKeywordProduct(keyword, productCount * (page - 1) + 1,
				(productCount * page));
		int bbsCount = productService.countKeyWordProductList(keyword);
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / (double) productCount);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / (double) productCount));
		int nowPageBlock = (int) (Math.ceil(page / (double) productCount));
		int startPage = (nowPageBlock - 1) * 2 + 1;
		int endPage = 0;
		if (totalPage > nowPageBlock * productCount) {
			endPage = nowPageBlock * productCount;
		} else {
			endPage = totalPage;
		}

		JSONObject pagingInfo = new JSONObject();
		JSONArray result = new JSONArray();
		for (Product p : list) {
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
	public Object boardInsertProduct(@RequestParam(value = "list[]") List<Integer> list) {
		List<Product> productList = productService.selectProductListInBaord(list);
		JSONArray result = new JSONArray();
		for (Product p : productList) {
			JSONObject product = new JSONObject();
			product.put("product", p);
			result.add(product);
		}
		return result;
	}

	// 상품 목록 1 페이지로 이동
	@RequestMapping("/product/{categoryId}")
	public String getProductListByCategory(@PathVariable int categoryId, HttpSession session, Model model) {
		return "redirect:/product/{categoryId}/1";
	}

	// 카테고리 1의 1페이지로 이동
	@RequestMapping("/product")
	public String getProductListByCategory(HttpSession session, Model model) {
		return "redirect:/product/1/1";
	}

	// 상품 id의 썸네일 반환
	@RequestMapping("/product-thumnail/{productId}")
	public ResponseEntity<byte[]> getProductThumbnail(@PathVariable int productId) {
		ProductImage file = productService.getProductThumbnail(productId);
		if (file == null) {
			productId = 0;
			file = productService.getProductThumbnail(productId);
		}
		logger.info("getFile " + file.toString());
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = file.getProductImageType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(Long.parseLong(file.getProductImageSize()));
		try {
			String encodedFileName = URLEncoder.encode(file.getProductImageName(), "UTF-8");
			headers.setContentDispositionFormData("attachment", encodedFileName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<byte[]>(file.getProductImage(), headers, HttpStatus.OK);
	}

	// 상품 상세 페이지로 이동
	@RequestMapping("/product-detail/{productId}")
	public String getProductDetail(@PathVariable int productId, Model model) {
		model.addAttribute("product", productService.selectProduct(productId));
		List<Integer> imageIdList = productService.getProductImageList(productId);
		if (imageIdList.size() != 0) {
			model.addAttribute("imageIdList", imageIdList);
		}
		return "product-detail";
	}

	// 이미지 번호에 따른 이미지 반환
	@RequestMapping("/product-detail/image/{productImageId}")
	public ResponseEntity<byte[]> getProductImageByImageId(@PathVariable int productImageId) {
		ProductImage file = productService.getProductImageByImageId(productImageId);
		logger.info("getFile " + file.toString());
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = file.getProductImageType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(Long.parseLong(file.getProductImageSize()));
		try {
			String encodedFileName = URLEncoder.encode(file.getProductImageName(), "UTF-8");
			headers.setContentDispositionFormData("attachment", encodedFileName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<byte[]>(file.getProductImage(), headers, HttpStatus.OK);
	}

}