package com.example.myapp.board;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myapp.board.dao.IBoardReplyRepository;
import com.example.myapp.board.model.BoardReply;

@SpringBootTest
public class BoardReplyTest {
	
	@Autowired
	IBoardReplyRepository repository;
	
	private int boardReplyId;
	private int boardId;
	private String memberId;
	private String baordReplyContent;
	private Date boardReplyWriteDate;
	
	@Test
	void contextLoads() {
		delete();
	}
	
	public void select(){
		System.out.println(repository.selectBoardReplyList(1));
	}
	public void delete(){
		repository.deleteBoardReply(5);
	}
	public void update(){
		//BoardReply board=new BoardReply(4,1,"my","난 진짜 불가능",new Date(0));
		//repository.updateBoardReply(board);
	}
	public void insert(){
		//BoardReply board=new BoardReply(4,1,"my","난 진짜 불가능",new Date(0));
		//repository.insertBoardReply(board);
	}
}
