package com.example.myapp.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myapp.board.dao.IBoardRepository;
import com.example.myapp.board.model.Board;
import com.example.myapp.product.dao.ICategoryRepository;
import com.example.myapp.product.model.Category;

@SpringBootTest
public class BoardTest {
	
	@Autowired
	IBoardRepository boardRepository;
	
	@Test
	void contextLoads() {
		selectKeywordBaord();
	}
	
	public void selectKeywordBaord(){
		//List<Board> list=boardRepository.selectKeywordBaord("my");
//		for(Board b:list) {
//			System.out.println(b.getBoardContent());
//			
//		}
	}
}
