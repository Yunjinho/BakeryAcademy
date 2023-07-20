package com.example.myapp.product.service;

import java.util.List;

import com.example.myapp.product.model.Product;

public interface IProductService {
	public void insertProduct(Product product);
	public void updateProduct(Product product);
	public Product selectProduct(int productId);
	
	public List<Product> selectSearchKeywordProduct(String keyword,int start,int end);
	public List<Product> selectProductAtModal(int start,int end);
	List<Product> selectProductListInBaord(List<Integer> list);
	
	int countProductList();
	int countKeyWordProductList(String keyword);
}
