package com.example.myapp.member.model;

import lombok.Data;

@Data
public class Cart {
	private int cartId;
	private String memberId;
	private int productId;
	private int productCount;
}
