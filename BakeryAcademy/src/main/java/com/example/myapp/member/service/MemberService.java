package com.example.myapp.member.service;

import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.myapp.member.dao.IMemberRepository;
import com.example.myapp.member.model.Member;

@Service
public class MemberService implements IMemberService {

	@Autowired
	IMemberRepository memberRepository;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	private String authNum;
	
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

	@Override
	public Member duplicateMember(Member member) {
		return memberRepository.duplicateMember(member);
	}

	public String findMemberId(Member member) {
		return memberRepository.findMemberId(member);
	}

	@Override
	public void makeRandomNumber() {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
				Random random = new Random();
		        StringBuffer key = new StringBuffer();

		        for(int i=0; i<8; i++) {
		            int idx = random.nextInt(4);

		            switch (idx) {
		                case 0 :
		                    key.append((char) ((int)random.nextInt(26) + 97));
		                    break;
		                case 1:
		                    key.append((char) ((int)random.nextInt(26) + 65));
		                    break;
		                case 2:
		                    key.append(random.nextInt(9));
		                    break;
		                case 3:
		                	key.append((char) ((int)random.nextInt(15) + 33));
		                	break;
		            }
		        }
		        authNum = key.toString();
	}

	@Override
	public String joinEmail(String email) {
		makeRandomNumber();
		String setFrom = "dbswlsgh1238@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력 
		String toMail = email;
		String title = "비밀번호 찾기 인증 이메일 입니다."; // 이메일 제목 
		String content = 
                "<br><br>" + 
			    "임시 비밀 번호는 " + authNum + "입니다." + 
			    "<br>" + 
			    "임시 비밀번호를 이용해 로그인 후 비밀번호를 변경해주세요."; //이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		return authNum;
	}

	@Override
	public void mailSend(String setFrom, String toMail, String title, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(content,true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}