package com.example.myapp.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardPrep {
	private int boardPrepId;
	private int boardId;
	private int productId;
}