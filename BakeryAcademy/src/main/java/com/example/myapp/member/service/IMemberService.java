package com.example.myapp.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.myapp.member.model.Member;

public interface IMemberService {
	void insertMember(Member member);
	Member selectMember(String memberId);
	List<Member> selectAllMembers();
	void updateMember(Member member);
	void deleteMember(Member member);
	String getPassword(String memberId);
	Member duplicateMember(Member member);
	String findMemberId(Member member);
	void makeRandomNumber();
	String joinEmail(String email);
	void mailSend(String setFrom, String toMail, String title, String content);
}