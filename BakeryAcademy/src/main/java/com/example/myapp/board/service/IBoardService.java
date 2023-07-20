package com.example.myapp.board.service;


import java.util.List;

import com.example.myapp.board.model.Board;

import com.example.myapp.board.model.BoardImage;

public interface IBoardService {
	
	/*
	 * List<Board> selectArticleListByCategory(int boardId, int page); int
	 * selectTotalArticleCountByCategoryId(int boardId);
	 */
	
	
	
	Board selectArticle(int boardId);

	BoardImage getFile(int boardImageId);

	void insertArticle(Board board);
	void insertArticle(Board board, BoardImage file);



	

}