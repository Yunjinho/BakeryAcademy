package com.example.myapp.board.model;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardReply {
	private int boardReplyId;
	private int boardId;
	private String memberId;
	private String boardReplyContent;
	private Date boardReplyWriteDate;
}