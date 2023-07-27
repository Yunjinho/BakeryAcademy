package com.example.myapp.product.model;

import java.util.List;

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
	private List<MultipartFile> image;
	private int rowNumber;
	
}
