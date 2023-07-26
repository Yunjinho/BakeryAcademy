package com.example.myapp.product.service;

import java.util.List;

import com.example.myapp.product.model.ProductReview;

public interface IProductReviewService {
	void insertProductReview(ProductReview productReview);
	int selectProductReviewCount(int productId);
	List<ProductReview> selectAllReviewByProductId(int productId);
}
