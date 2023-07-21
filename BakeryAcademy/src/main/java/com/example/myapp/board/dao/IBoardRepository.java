package com.example.myapp.board.dao;





import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;

@Repository 
@Mapper
public interface IBoardRepository {
	int selectMaxArticleNo();
	
	//boardId를 기반으로 단일 게시물을 가져오기
	Board selectArticle(int boardId);

	BoardImage getFile(int boardImageId); 
	
	
	
	
	int insertArticle(Board board);
	int insertArticle(Board board, BoardImage file);

	
	
	
	
	
	int selectMaxBoardImageId();

	void insertBoardImage(BoardImage file);
	void insertFileData(BoardImage file);



	
	
}
