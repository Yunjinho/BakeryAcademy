package com.example.myapp.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {
	private int cartId;
	private String memberId;
	private int productId;
	private int productCount;
	
	private String productName;
	private int productPrice;
}