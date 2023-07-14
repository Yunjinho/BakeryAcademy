package com.example.myapp.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.Product;

@Repository
@Mapper
public interface IProductRepository {
	Product selectProduct(int productId);
	void insertProduct(Product product);
}
