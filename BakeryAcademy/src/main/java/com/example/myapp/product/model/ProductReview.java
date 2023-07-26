package com.example.myapp.product.model;

import lombok.Data;

@Data
public class ProductReview {
	private int productReviewId;
	private int productId;
	private String memberId;
	private String productReviewContent;
	private int rating;
	private int orderId;
}
