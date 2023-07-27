package com.example.myapp.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.member.model.Member;
@Repository
@Mapper
public interface IMemberRepository {
	void insertMember(Member member);
	Member selectMember(String memberId);
	List<Member> selectAllMembers();
	void updateMember(Member member);
	void deleteMember(Member member);
	String getPassword(String memberId);
	Member duplicateMember(Member member);
	String findMemberId(Member member);
}