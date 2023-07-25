package com.example.myapp.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.board.dao.IBoardPrepRepository;
import com.example.myapp.board.dao.IBoardReplyRepository;
import com.example.myapp.board.dao.IBoardRepository;
import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;
import com.example.myapp.board.model.BoardPrep;

@Service
public class BoardService implements IBoardService{
	
	@Autowired
	IBoardRepository boardRepository;
	
	@Autowired
	IBoardPrepRepository boardPrepRepository;
	
	@Autowired
	IBoardReplyRepository boardReplyRepository;

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
		boardRepository.insertArticle(board);
		for(Integer list:board.getProductId()) {
			BoardPrep bp=new BoardPrep();
			bp.setBoardId(board.getBoardId());
			bp.setProductId(list);
			boardPrepRepository.insertBoardPrep(bp);
		}
		return 0;
	}
	

	
	@Transactional
	public int insertArticle(Board board, List<BoardImage> fileList) {
		//board.setBoardId(boardRepository.selectMaxArticleNo()+1);
		boardRepository.insertArticle(board);
		for(BoardImage file : fileList) {
			if(file != null && file.getBoardImageName() != null && !file.getBoardImageName().equals("")) {
				file.setBoardId(board.getBoardId());
				//file.setFileId(boardRepository.selectMaxFileId()+1);
				boardRepository.insertFileData(file);
			}
		}
		for(Integer list:board.getProductId()) {
			BoardPrep bp=new BoardPrep();
			bp.setBoardId(board.getBoardId());
			bp.setProductId(list);
			boardPrepRepository.insertBoardPrep(bp);
		}
        return board.getBoardId();
	}
	
	//삭제할 게시물 조회
	@Transactional
	public Board selectDeleteBoard(int boardId) {
		return boardRepository.selectDeleteBoard(boardId);
	}
	
	
	
	//제목 내용 작성자 키워드로 게시물 찾기
	@Override
	public List<Board> selectKeywordBoardList(String keyword, int page) {
		int start=(page-1)*10+1;
		return boardRepository.selectKeywordBaord(keyword,start,start+9);
	}
	
	//게시물 삭제
	@Override
	@Transactional
	public void deleteBoard(int boardId) {
		//게시물 상품 삭제
		boardPrepRepository.deleteAllBoardPrep(boardId);
		//게시물 댓글 삭제
		boardReplyRepository.deleteAllBoardReply(boardId);
		//게시물 이미지 삭제
		boardRepository.deleteBoardImage(boardId);
		//게시물 삭제
		boardRepository.deleteBoard(boardId);
	}

	@Override
	public List<Board> selectAllBoardList(int page) {
		int start=(page-1)*10+1;
		return boardRepository.selectAllBoardList(start,start+9);
	}
	@Override
	public int countBoard() {
		return boardRepository.countBoard();
	}

	@Override
	public int countKeywordBoard(String keyword) {
		return boardRepository.countKeywordBoard(keyword);
	}




	@Override
	public String getMemberId(String memberId) {
		return boardRepository.getMemberId(memberId);
	}
	


	@Transactional
	public void deleteArticle(int boardId) {
//		if(replyNumber>0) {
//			boardRepository.deleteReplyFileData(boardId);
//			boardRepository.deleteArticleByBoardId(boardId);
//		} else if(replyNumber == 0){
//			boardRepository.deleteFileData(boardId);
//			boardRepository.deleteArticleByMasterId(boardId);
//		} else {
//			throw new RuntimeException("WRONG_REPLYNUMBER");
//		}
//	}
		boardRepository.deleteFileData(boardId);
		boardRepository.deleteArticleInfo(boardId);
		
	}





// 	@Override
// 	public int updateArticle(Board board, BoardImage file) {
// 		boardRepository.updateArticle(board);
//         if(file != null && file.getBoardImageName() != null && !file.getBoardImageName().equals("")) {
//         	file.setBoardId(board.getBoardId());

        	
//         	//file.setFileId(boardRepository.selectMaxFileId()+1);
//         	boardRepository.insertFileData(file);
//         }
//         return board.getBoardId();
// 	}




// 	@Override
// 	public int updateArticle(Board board) {
// 		return boardRepository.updateArticle(board);
// 	}


}
