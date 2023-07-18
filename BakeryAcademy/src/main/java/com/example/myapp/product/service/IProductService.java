package com.example.myapp.product.service;

import com.example.myapp.product.model.Product;

public interface IProductService {
	public void insertProduct(Product product);
	public void updateProduct(Product product);
	public Product selectProduct(int productId);
}
