package com.example.myapp.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
	private int categoryId;
	private String categoryName;
	
	private int rowNum;
}
