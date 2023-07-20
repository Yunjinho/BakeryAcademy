package com.example.myapp.member.model;

import lombok.Data;

@Data
public class Member {
	private String memberId;
	private String memberPassword;
	private String memberName;
	private String memberAddress;
	private String memberPhoneNumber;
	private String memberEmail;
	private String memberNickname;
	private int isAdmin;
}