package com.example.myapp.board.service;



import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;

public interface IBoardService {
	

	
	
	Board selectArticle(int boardId);


	BoardImage getFile(int boardImageId);

	int insertArticle(Board board);
	int insertArticle(Board board, BoardImage file);

	

}