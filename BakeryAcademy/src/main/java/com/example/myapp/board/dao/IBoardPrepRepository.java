package com.example.myapp.board.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.board.model.BoardPrep;

@Repository 
@Mapper
public interface IBoardPrepRepository {
	//게시물에 상품 입력
	void insertBoardPrep(BoardPrep boardPrep);
	
	//해당 게시물에 등록한 상품 리스트
	List<BoardPrep> selectBoardPrepList(int boardPrepId);
	
	//상품 수정
	void updateBoardPrep(BoardPrep boardPrep);
	
	//상품 한개만 삭제
	void deleteBoardPrep(int boardPrepId);

	//게시글에 있는 상품 전부 삭제
	void deleteAllBoardPrep(int boardId);
	
	
}