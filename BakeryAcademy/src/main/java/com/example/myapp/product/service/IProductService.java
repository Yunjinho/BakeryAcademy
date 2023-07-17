package com.example.myapp.product.service;

import java.util.List;

import com.example.myapp.product.model.Product;

public interface IProductService {
	public List<Product> selectProductListByCategory(int categoryId, int page);
	public int selectTotalProductCountByCategory(int categoryId);
}
