package com.example.myapp.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.Product;

@Repository
@Mapper
public interface IProductRepository {
	Product selectProduct(int productId);
	void insertProduct(Product product);
	
	List<Product> getProductListByCategory(@Param("categoryId") int categoryId, @Param("start") int start, @Param("end") int end);
	int selectTotalProductCountByCategoryId(int categoryId);
}
