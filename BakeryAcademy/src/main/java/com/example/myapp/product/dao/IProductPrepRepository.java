package com.example.myapp.product.dao;

import org.apache.ibatis.annotations.Mapper;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.myapp.board.model.BoardPrep;

@Repository
@Mapper
public interface IProductPrepRepository {
	void insertProductPrep(BoardPrep productPrep);
}
