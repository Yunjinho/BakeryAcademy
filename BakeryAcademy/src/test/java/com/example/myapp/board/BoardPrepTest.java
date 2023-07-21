package com.example.myapp.board;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myapp.board.dao.IBoardPrepRepository;
import com.example.myapp.board.model.BoardPrep;
import com.example.myapp.board.model.BoardReply;

@SpringBootTest
public class BoardPrepTest {
	
	@Autowired
	IBoardPrepRepository repository;
	
	private int boardPrepId;
	private int boardId;
	private int productId;
	
	@Test
	void contextLoads() {
		update();
	}
	
	public void select(){
		System.out.println(repository.selectBoardPrepList(1));
	}
	public void delete(){
		repository.deleteBoardPrep(boardPrepId);
	}
	public void update(){
		BoardPrep boardPrep=new BoardPrep(2,1,50);
		repository.updateBoardPrep(boardPrep);
	}
	public void insert(){
		BoardPrep boardPrep=new BoardPrep(0,1,71);
		repository.insertBoardPrep(boardPrep);
	}
}
