package com.example.myapp.board.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardReply {
	private int boardReplyId;
	private int boardId;
	private String memberId;
	private String boardReplyContent;
	private Date boardReplyWriteDate;

}