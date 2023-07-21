package com.example.myapp.board.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.board.model.BoardReply;

@Repository 
@Mapper
public interface IBoardReplyRepository {
	//댓글 입력
	void insertBoardReply(BoardReply boardreply);
	
	//해당 게시물에 대한 댓글 리스트
	List<BoardReply> selectBoardReplyList(int boardId);
	
	//댓글 수정
	void updateBoardReply(BoardReply boardreply);
	
	//댓글 삭제
	void deleteBoardReply(int boardReplyId);

	//게시물에 있는 댓글 전부 삭제
	void deleteAllBoardReply(int boardReplyId);
}
