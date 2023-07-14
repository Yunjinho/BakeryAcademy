package com.example.myapp.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardImage {
	private int boardImageId;
	private int boardId;
	private byte[] boardImage;
	
	private String fileContentType;

	@Override
	public String toString() {
		return "BoardImage [boardImageId=" + boardImageId + ", boardId=" + boardId + ", fileContentType="
				+ fileContentType + "]";
	}
	
}
