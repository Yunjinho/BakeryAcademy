package com.example.myapp.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductImage;

@Repository
@Mapper
public interface IProductRepository {
	Product selectProduct(int productId);
	void insertProduct(Product product);
	
	List<Product> getProductListByCategory(@Param("categoryId") int categoryId, @Param("start") int start, @Param("end") int end);
	int selectTotalProductCountByCategoryId(int categoryId);
	ProductImage getProductImageMinId(int productId);
	List<ProductImage> getProductImageList(int productId);//아직 mapper에 미구현
}