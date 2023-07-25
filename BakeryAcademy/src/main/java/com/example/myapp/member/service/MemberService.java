package com.example.myapp.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.member.dao.IMemberRepository;
import com.example.myapp.member.model.Member;

@Service
public class MemberService implements IMemberService {

	@Autowired
	IMemberRepository memberRepository;
	
	@Override
	public void insertMember(Member member) {
		memberRepository.insertMember(member);
	}

	@Override
	public Member selectMember(String memberId) {
		return memberRepository.selectMember(memberId);
	}

	@Override
	public List<Member> selectAllMembers() {
		return memberRepository.selectAllMembers();
	}

	@Override
	public void updateMember(Member member) {
		memberRepository.updateMember(member);
	}

	@Override
	public void deleteMember(Member member) {
		memberRepository.deleteMember(member);
	}

	@Override
	public String getPassword(String memberId) {
		return memberRepository.getPassword(memberId);
	}
}