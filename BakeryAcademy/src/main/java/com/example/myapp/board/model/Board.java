package com.example.myapp.board.model;

import java.sql.Date;

import lombok.Data;
@Data
public class Board {
	private int boardId;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private Date boardWriteDate;
	private int visitCount;
}
