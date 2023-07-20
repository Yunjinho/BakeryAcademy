package com.example.myapp.product;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myapp.product.dao.IProductRepository;
import com.example.myapp.product.model.Product;

@SpringBootTest
public class ProductTest {
	@Autowired
	IProductRepository productRepository;
	
	@Test
	void contextLoads() {
		List<Integer> a=new ArrayList<Integer>();
		a.add(3);
		List<Product> p=productRepository.selectProductListInBoard(a);
		//List<Product> p=productRepository.selectKeywordProductList("test");
		//List<Product> p=productRepository.selectProductListAtModal(0, 10);
		System.out.println(p);
	}
}
