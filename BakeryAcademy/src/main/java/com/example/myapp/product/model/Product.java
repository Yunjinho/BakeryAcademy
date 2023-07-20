package com.example.myapp.product.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	private int productId;
	private int categoryId;
	private String productName;
	private int productPrice;
	private String productContent;
	private int productStock;
	private int productStatus;

	// 추가 변수
	private MultipartFile image;
	private MultipartFile image2;
	private MultipartFile image3;
	private int rowNumber;
	
}
