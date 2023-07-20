package com.example.myapp.product.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;
import com.example.myapp.product.service.IProductService;
//import com.example.myapp.product.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IProductService productService;

	@RequestMapping("/product/{categoryId}/{page}")
	public String getProductListByCategory(@PathVariable int categoryId, @PathVariable int page, HttpSession session, Model model) {
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

	@RequestMapping("/product/{categoryId}")
	public String getProductListByCategory(@PathVariable int categoryId, HttpSession session, Model model) {
//		return getProductListByCategory(categoryId, 1, session, model);
		return "redirect:/product/{categoryId}/1";
	}

	@RequestMapping("/product")
	public String getProductListByCategory(HttpSession session, Model model) {
//		return getProductListByCategory(1, 1, session, model);
		return "redirect:/product/1/1";
	}
	
//	@RequestMapping("/files/{productId}")
//	public ResponseEntity<byte[]> getFileList(@PathVariable int productId) {
//		BoardUploadFile file = boardService.getFile(fileId);
//		logger.info("getFile " + file.toString());
//		final HttpHeaders headers = new HttpHeaders();
//		String[] mtypes = file.getFileContentType().split("/");
//		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
//		headers.setContentLength(file.getFileSize());
//		try {
//			String encodedFileName = URLEncoder.encode(file.getFileName(), "UTF-8");
//			headers.setContentDispositionFormData("attachment", encodedFileName);
//		} catch (UnsupportedEncodingException e) {
//			throw new RuntimeException(e);
//		}
//		return new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
//	}
	
	@RequestMapping("/file/{productId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int productId) {
		ProductImage file = productService.getProductThumbnail(productId);
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
//		return new ResponseEntity<byte[]>(file.getProductImage(), HttpStatus.OK);
	}
}
