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
	Member duplicateMember(String memberId);
}