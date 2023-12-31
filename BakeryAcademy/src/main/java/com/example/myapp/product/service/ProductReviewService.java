package com.example.myapp.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.product.dao.IProductReviewRepository;
import com.example.myapp.product.model.ProductReview;

@Service
public class ProductReviewService implements IProductReviewService{
	@Autowired
	IProductReviewRepository productReviewRepository;
	
	@Override
	public void insertProductReview(ProductReview productReview) {
		productReviewRepository.insertProductReview(productReview);
	}

	@Override
	public int selectProductReviewCount(int productId) {
		return productReviewRepository.selectProductReviewCount(productId);
	}

	@Override
	public List<ProductReview> selectAllReviewByProductId(int productId) {
		System.out.println("엄");
		List<ProductReview> list =productReviewRepository.selectAllReviewByProductId(productId);
		System.out.println("어엄");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		System.out.println("어어엄");
		return list;
	}

}
