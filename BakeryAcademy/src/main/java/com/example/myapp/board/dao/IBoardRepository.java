package com.example.myapp.board.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;

@Repository 
@Mapper
public interface IBoardRepository {
	int selectMaxArticleNo();
	
	//boardId를 기반으로 단일 게시물을 가져옵니다.
	Board selectArticle(int boardId);

	BoardImage getFile(int boardImageId); 
	
	void insertArticle(Board board);
	void insertArticle(Board board, BoardImage file);

	int selectMaxBoardImageId();

	void insertBoardImage(BoardImage file);
	void insertFileData(BoardImage file);
	/*
	 * List<Board> selectArticleListByCategory(@Param("boardId") int
	 * boardId, @Param("start") int start, @Param("end") int end); void
	 * insertArticle(Board board); int selectTotalArticleCountByCategoryId(int
	 * boardId); void updateReadCount(int boardId); BoardImage getFile(int
	 * boardImageId);
	 */
}
