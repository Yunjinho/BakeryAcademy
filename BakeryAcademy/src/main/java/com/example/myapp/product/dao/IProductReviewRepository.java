package com.example.myapp.product.dao;

import org.apache.ibatis.annotations.Mapper;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.ProductReview;

@Repository
@Mapper
public interface IProductReviewRepository {
	void insertProductReview(ProductReview productReview);
}
