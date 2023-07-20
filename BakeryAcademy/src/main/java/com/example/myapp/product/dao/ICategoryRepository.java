package com.example.myapp.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.Category;

@Repository
@Mapper
public interface ICategoryRepository {
	void insertCategory(Category category);
	void deleteCategory(int categortId);
	void updateCategory(Category category);
	Category selectCategory(int categoryId);
	List<Category> selectAllCategory();
}
