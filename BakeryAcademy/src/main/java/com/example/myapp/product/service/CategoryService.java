package com.example.myapp.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.product.dao.ICategoryRepository;
import com.example.myapp.product.model.Category;

@Service
public class CategoryService implements ICategoryService{
	@Autowired
	ICategoryRepository categoryRepository;

	@Override
	public List<Category> selectAllCategory() {
		return categoryRepository.selectAllCategory();
	}
	
}
