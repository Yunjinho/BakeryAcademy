package com.example.myapp.board.model;


import java.util.Date;


import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class Board {
	private int boardId;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private Date boardWriteDate;
	private int visitCount;
	
	private String memberNickname;
	private int boardImageId; 
	private String boardImageName;
	private long boardImageSize;
	private String boardImageType;
	
	
	private MultipartFile file;
	private byte[] boardImage;
	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", memberId=" + memberId + ", boardTitle=" + boardTitle + ", visitCount="
				+ visitCount + ", memberNickname=" + memberNickname + ", boardImageId=" + boardImageId
				+ ", boardImageName=" + boardImageName + ", boardImageSize=" + boardImageSize + ", boardImageType="
				+ boardImageType + "]";
	}

	
	

	
	

	
	
	
	
	
	
}
