package com.example.myapp.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.ProductReview;

@Repository
@Mapper
public interface IProductReviewRepository {
	void insertProductReview(ProductReview productReview);
	int selectProductReviewCount(int productId);
	List<ProductReview> selectAllReviewByProductId(int productId);
}
