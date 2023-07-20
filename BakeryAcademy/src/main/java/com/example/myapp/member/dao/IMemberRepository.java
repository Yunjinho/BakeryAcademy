package com.example.myapp.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.member.model.Member;

@Repository
@Mapper
public interface IMemberRepository {
	void insertMember(Member member);
}