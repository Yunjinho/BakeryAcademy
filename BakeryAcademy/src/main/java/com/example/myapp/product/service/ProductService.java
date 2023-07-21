package com.example.myapp.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.product.dao.IProductRepository;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;

@Service
public class ProductService implements IProductService {
	@Autowired
	IProductRepository productRepository;

	@Override
	public List<Product> selectProductListByCategory(int categoryId, int page) {
		int start = (page - 1) * 10 + 1;
		return productRepository.getProductListByCategory(categoryId, start, start + 9);
	}

	@Override
	public int selectTotalProductCountByCategory(int categoryId) {
		return productRepository.selectTotalProductCountByCategoryId(categoryId);
	}

	@Override
	public ProductImage getProductThumbnail(int productId) {
		return productRepository.getProductImageMinId(productId);
	}

	@Override
	public List<ProductImage> getProductImageList(int productId) {
		return productRepository.getProductImageList(productId);
	}
}
