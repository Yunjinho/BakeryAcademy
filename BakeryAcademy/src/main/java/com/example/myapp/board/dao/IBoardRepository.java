package com.example.myapp.board.dao;


import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
	
	//삭제할 보드 조회
	Board selectDeleteBoard(int boardId);
	
	String getMemberId(String memberId);
	//삭제할 파일 데이터
	void deleteFileData(int boardId);
	//삭제할 게시물 정보
	void deleteArticleInfo(int boardId);
	
	
	
	//게시물에 등록된 이미지 삭제
	void deleteBoardImage(int boardId);
	//게시물 삭제
	void deleteBoard(int boardId);
	/*
	 * List<Board> selectArticleListByCategory(@Param("boardId") int
	 * boardId, @Param("start") int start, @Param("end") int end); void
	 * insertArticle(Board board); int selectTotalArticleCountByCategoryId(int
	 * boardId); void updateReadCount(int boardId); BoardImage getFile(int
	 * boardImageId);
	 */
	//등록된 게시물 전체 조회
	List<Board> selectAllBoardList(@Param("start")int start,@Param("end")int end);
	
	//제목이나 내용으로 게시물 조회
	List<Board> selectKeywordBaord(@Param("keyword")String keyword,@Param("start")int start,@Param("end")int end);
	//등록된 전체 게시판 수
	int countBoard();
	//검색한 게시판의 수
	int countKeywordBoard(@Param("keyword")String keyword);

	
}
