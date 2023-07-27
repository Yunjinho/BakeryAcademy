package com.example.myapp.board.model;




import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
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
	
	
	private List<MultipartFile> file;
	private List<Integer> productId;
	private byte[] boardImage;
	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", memberId=" + memberId + ", boardTitle=" + boardTitle + ", boardContent="
				+ boardContent + ", boardWriteDate=" + boardWriteDate + ", visitCount=" + visitCount
				+ ", memberNickname=" + memberNickname + ", boardImageId=" + boardImageId + ", boardImageName="
				+ boardImageName + ", boardImageSize=" + boardImageSize + ", boardImageType=" + boardImageType
				+ ", productId=" + productId + ", boardImage=" + Arrays.toString(boardImage) + "]";
	}
	

	
	

	
	

	
	
	
	
	
	
}
