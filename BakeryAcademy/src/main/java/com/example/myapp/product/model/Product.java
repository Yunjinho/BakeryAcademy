package com.example.myapp.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	private int productId;
	private int categoryId;
	private String productName;
	private int productPrice;
	private String productContent;
	private int productStock;
	private int productStatus;
}
