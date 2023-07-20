package com.example.myapp.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.myapp.member.model.Member;
import com.example.myapp.member.service.IMemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private IMemberService memberService;

	@Autowired
	private Validator memberValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(memberValidator);
	}

	@RequestMapping(value = "/member/signup", method = RequestMethod.GET)
	public String insertMember(Model model) {
		model.addAttribute("member", new Member());
		return "member/signup";
	}

	@RequestMapping(value = "/member/signup", method = RequestMethod.POST)
	public String memberInsert(Member member, HttpSession session, BindingResult result, Model model) {
		System.out.println(member);
		memberService.insertMember(member);
		session.invalidate();
		return "member/login";
	}

	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public String login() {
		return "member/login";
	}

	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
	public String login(String memberId, String memberPassword, HttpSession session, Model model) {
		Member member = memberService.selectMember(memberId);
		if (member != null) {
			String dbPassword = member.getMemberPassword();
			if (dbPassword == null) { // 아이디가 없는 경우
				model.addAttribute("message", "NOT_VALID_USER");
			} else { // 아이디가 있는 경우
				if (dbPassword.equals(memberPassword)) {
					// 비밀번호 일치
					session.setAttribute("memberId", memberId);
					session.setAttribute("memberName", member.getMemberName());
					session.setAttribute("memberEmail", member.getMemberEmail());
					return "redirect:/member/memberinfo"; // memberinfo 페이지로 리다이렉션
				} else { // 아이디는 있지만 비밀번호가 다른 경우
					model.addAttribute("message", "WRONG_PASSWORD");
				}
			}
		} else {
			model.addAttribute("message", "USER_NOT_FOUND");
		}
		session.invalidate();
		return "member/login";
	}

	@RequestMapping(value = "/member/memberinfo", method = RequestMethod.GET)
	public String memberinfo() {
		return "member/memberinfo";
	}

	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request) {
		session.invalidate(); // 로그아웃
		return "home";
	}

	@RequestMapping(value = "/member/update", method = RequestMethod.GET)
	public String updateMember(HttpSession session, Model model) {
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null && !memberId.equals("")) {
			Member member = memberService.selectMember(memberId);
			model.addAttribute("member", member);
			model.addAttribute("message", "UPDATE_USER_INFO");
			return "member/update";
		} else {
			// memberid가 세션에 없을 때 (로그인하지 않았을 때)
			model.addAttribute("message", "NOT_LOGIN_USER");
			return "member/login";

		}
	}

	@RequestMapping(value = "/member/update", method = RequestMethod.POST)
	public String updateMember(@Validated Member member, BindingResult result, HttpSession session, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("member", member);
			return "member/update";
		}
		try {
			memberService.updateMember(member);
			model.addAttribute("message", "회원정보가 수정되었습니다.");
			model.addAttribute("member", member);
			session.setAttribute("memberemail", member.getMemberEmail());
			return "member/login";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
			return "member/error";
		}
	}

	@RequestMapping(value = "/member/delete", method = RequestMethod.GET)
	public String deleteMember(HttpSession session, Model model) {
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null && !memberId.equals("")) {
			Member member = memberService.selectMember(memberId);
			model.addAttribute("member", member);
			model.addAttribute("message", "MEMBER_PW_RE");
			return "member/delete";
		} else {
			// userid가 세션에 없을 때(로그인 하지 않았을 때)
			model.addAttribute("message", "NOT_LOGIN_UESR");
			return "member/login";
		}
	}

	@RequestMapping(value = "/member/delete", method = RequestMethod.POST)
	public String deleteMember(String memberpassword, HttpSession session, Model model) {
		try {
			Member member = new Member();
			member.setMemberId((String) session.getAttribute("memberId"));
			String dbpw = memberService.getPassword(member.getMemberId());
			if (memberpassword != null && memberpassword.equals(dbpw)) {
				member.setMemberPassword(memberpassword);
				memberService.deleteMember(member);
				session.invalidate(); // 삭제되었으면 로그아웃 처리
				return "member/login";
			} else {
				model.addAttribute("message", "WRONG_PASSWORD");
				return "member/delete";
			}
		} catch (Exception e) {
			model.addAttribute("message", "DELETE_FAIL");
			e.printStackTrace();
			return "member/delete";
		}
	}
}
	
