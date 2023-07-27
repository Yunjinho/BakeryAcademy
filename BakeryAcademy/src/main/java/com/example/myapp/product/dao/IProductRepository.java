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

	List<Product> getAllProductList(@Param("start") int start, @Param("end") int end);
	List<Product> getProductListByCategory(@Param("categoryId") int categoryId, @Param("start") int start, @Param("end") int end);

	int selectTotalProductCountByCategoryId(int categoryId);
	int selectTotalProductCount();

	void insertProductImage(ProductImage productImage);

	void updateProductImage(ProductImage productImage);

	void updateProduct(Product product);

	void deleteProductImage(int productId);

	List<Product> selectKeywordProductList(@Param("keyword") String keyword, @Param("start") int start,
			@Param("end") int end);

	List<Product> selectProductListAtModal(@Param("start") int start, @Param("end") int end);

	List<Product> selectProductListInBoard(List<Integer> productList);

	int countProductList();

	int countKeywordProductList(@Param("keyword") String keyword);

	ProductImage getProductImageMinId(int productId);

	List<Integer> getProductImageList(int productId);
	
	ProductImage getProductImageByImageId(int productImageId);
}