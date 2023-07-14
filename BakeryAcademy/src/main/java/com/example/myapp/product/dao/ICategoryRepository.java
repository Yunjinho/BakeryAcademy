package com.example.myapp.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.Category;

@Repository
@Mapper
public interface ICategoryRepository {
	void insertCategory(Category category);
}
