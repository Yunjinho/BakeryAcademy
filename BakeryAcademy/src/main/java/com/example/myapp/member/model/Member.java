package com.example.myapp.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
	private String memberId;
	private String memberPassword;
	private String memberPasswordConfirm;
	private String memberName;
	private String memberAddress;
	private String memberPhoneNumber;
	private String memberEmail;
	private String memberNickName;
	private int isAdmin;
	
	private String memberAddressDetail;
}