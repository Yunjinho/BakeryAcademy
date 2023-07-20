package com.example.myapp.board.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoardImage {
	private int boardImageId; //파일 아이디, 1씩증가
	private int boardId; //첨부파일이 있는 게시글의 아이디
	private byte[] boardImage; //파일데이터
	private String boardImageName; //파일이름
	private long boardImageSize; //파일크기
	private String boardImageType; //파일타입
	
	
	@Override
	public String toString() {
		return "BoardImage [boardImageId=" + boardImageId + ", boardId=" + boardId + ", boardImageName="
				+ boardImageName + ", boardImageSize=" + boardImageSize + ", boardImageType=" + boardImageType + "]";
	}
	
	

	

	

	
	


}
