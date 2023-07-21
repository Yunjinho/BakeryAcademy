package com.example.myapp.board.service;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.board.dao.IBoardRepository;
import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;

@Service
public class BoardService implements IBoardService{
	
	@Autowired
	IBoardRepository boardRepository;
	

	@Transactional
	public Board selectArticle(int boardId) {
		/* boardRepository.updateReadCount(boardId); */
		return boardRepository.selectArticle(boardId);
	}


	
	
	@Override
	public BoardImage getFile(int boardImageId) {
		return boardRepository.getFile(boardImageId);
	}

	@Transactional
	public int insertArticle(Board board) {
		return boardRepository.insertArticle(board);
	}
	

	
	@Transactional
	public int insertArticle(Board board, BoardImage file) {
		//board.setBoardId(boardRepository.selectMaxArticleNo()+1);
		boardRepository.insertArticle(board);
        if(file != null && file.getBoardImageName() != null && !file.getBoardImageName().equals("")) {
        	file.setBoardId(board.getBoardId());

        	
        	//file.setFileId(boardRepository.selectMaxFileId()+1);
        	boardRepository.insertFileData(file);
        }
        return board.getBoardId();
	}
	

}
