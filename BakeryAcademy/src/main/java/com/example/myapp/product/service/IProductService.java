package com.example.myapp.product.service;

import java.util.List;

import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;

public interface IProductService {
	public List<Product> selectProductListByCategory(int categoryId, int page);

	public int selectTotalProductCountByCategory(int categoryId);

	public ProductImage getProductThumbnail(int productId);

	public List<ProductImage> getProductImageList(int productId);
}