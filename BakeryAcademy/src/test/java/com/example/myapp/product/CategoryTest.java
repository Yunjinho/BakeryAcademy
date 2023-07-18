package com.example.myapp.product;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myapp.product.dao.ICategoryRepository;
import com.example.myapp.product.model.Category;

@SpringBootTest
public class CategoryTest {
	@Autowired
	ICategoryRepository categoryRepository;
	
	@Test
	void contextLoads() {
		selectAllcategory();
	}
	void selectAllcategory() {
		List<Category> list=categoryRepository.selectAllCategory();
		for(Category l:list) {
			System.out.println(l);
		}
	}
	void selectCategory() {
		Category category=new Category();
		category=categoryRepository.selectCategory(1);
		System.out.println(category);
		
	}
	void update() {
		Category category=new Category();
		category.setCategoryName("버터/생크림");
		categoryRepository.updateCategory(category);
	}
	void delete() {
		categoryRepository.deleteCategory(0);
	}
	
	void insert() {
		Category category=new Category();
		category.setCategoryName("분말류");
		categoryRepository.insertCategory(category);
	}
}
