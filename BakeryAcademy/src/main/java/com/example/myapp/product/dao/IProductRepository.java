package com.example.myapp.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;

@Repository
@Mapper
public interface IProductRepository {
	Product selectProduct(int productId);
	void insertProduct(Product product);
	void insertProductImage(ProductImage productImage);
	void updateProductImage(ProductImage productImage);

	void updateProduct(Product product);
	void deleteProductImage(int productId);
}
