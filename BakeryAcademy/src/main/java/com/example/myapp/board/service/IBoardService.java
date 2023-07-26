package com.example.myapp.board.service;



import java.util.List;


import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;
import com.example.myapp.board.model.BoardPrep;
import com.example.myapp.board.model.BoardReply;


public interface IBoardService {

	Board selectArticle(int boardId);
	List<BoardImage> selectArticleImage(int boardId);
	List<BoardPrep> selectArticlePrep(int boardId);

	BoardImage getFile(int boardImageId);

	int insertArticle(Board board);
	int insertArticle(Board board, List<BoardImage> fileList);
	
//	int updateArticle(Board board, BoardImage file);
//	int updateArticle(Board board);
	
	Board selectDeleteBoard(int boardId);
	String getMemberId(String memberId);
	
	
	
	//게시물 삭제
	void deleteArticle(int sessionBoardId);
	//게시물 수정
	void updateBoardArticle(int sessionBoardId);
	
	List<Board> selectAllBoardList(int page);
	List<Board> selectKeywordBoardList(String keyword,int page);
	int countBoard();
	int countKeywordBoard(String keyword);
	
	void deleteBoard(int boardId);
	
	List<BoardReply> selectBoardReplyList(int boardId);
	void insertBoardReply(BoardReply boardreply);
//	void updateBoardArticle(Integer sessionBoardId);
//	void updateArticle(int sessionBoardId);
	


	


	
}