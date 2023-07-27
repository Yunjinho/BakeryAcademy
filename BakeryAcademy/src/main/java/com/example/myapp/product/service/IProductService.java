package com.example.myapp.product.service;

import java.util.List;

import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;

public interface IProductService {
	public void insertProduct(Product product);

	public void updateProduct(Product product);

	public Product selectProduct(int productId);

	public List<Product> selectSearchKeywordProduct(String keyword, int start, int end);

	public List<Product> selectProductAtModal(int start, int end);

	public List<Product> selectProductListInBaord(List<Integer> list);

	public int countProductList();

	public int countKeyWordProductList(String keyword);

	public List<Product> selectProductListByCategory(int categoryId, int page);

	public int selectTotalProductCountByCategory(int categoryId);

	public ProductImage getProductThumbnail(int productId);

	public List<Integer> getProductImageList(int productId);

	public ProductImage getProductImageByImageId(int productImageId);
	
	public List<Product> getAllProductList(int page);
	public List<Product> getStopProductList(int page);
	
	public int selectTotalProductCount();
}