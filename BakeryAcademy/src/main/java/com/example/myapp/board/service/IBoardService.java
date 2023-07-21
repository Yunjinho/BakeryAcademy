package com.example.myapp.board.service;



import java.util.List;

import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;

public interface IBoardService {
	

	
	
	Board selectArticle(int boardId);


	BoardImage getFile(int boardImageId);

	int insertArticle(Board board);
	int insertArticle(Board board, BoardImage file);

	/*
	 * List<Board> selectArticleListByCategory(int boardId, int page); int
	 * selectTotalArticleCountByCategoryId(int boardId);
	 */
	

	
	List<Board> selectAllBoardList(int page);
	List<Board> selectKeywordBoardList(String keyword,int page);
	int countBoard();
	int countKeywordBoard(String keyword);
	
	void deleteBoard(int boardId);
}