package com.example.myapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myapp.product.dao.ICategoryRepository;
import com.example.myapp.product.model.Category;

@SpringBootTest
class BakeryAcademyApplicationTests {
	@Autowired
	ICategoryRepository categoryRepository;
	
	@Test
	void contextLoads() {
		Category category=new Category();
		category.setCategoryName("분말류");
		categoryRepository.insertCategory(category);
	}

}
