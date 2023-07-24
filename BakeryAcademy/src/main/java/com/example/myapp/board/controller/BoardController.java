package com.example.myapp.board.controller;

import java.io.UnsupportedEncodingException;


import java.net.URLEncoder;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;
import com.example.myapp.board.service.IBoardService;
import com.example.myapp.member.model.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	IBoardService boardService;

	//게시물 전체 조회
	@RequestMapping("/board")
	public String board(@RequestParam int page,Model model,HttpSession session) {
		session.setAttribute("page", page);
		List<Board> list=boardService.selectAllBoardList(page);
		model.addAttribute("boardList", list);
		int bbsCount = boardService.countBoard();
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		int nowPageBlock = (int) (Math.ceil(page / 10.0));
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage=0;
		if(totalPage>nowPageBlock*10) {
			endPage=nowPageBlock*10;
		}else {
			endPage=totalPage;
		}
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "board/board";
	}

	//게시물 전체 조회
	@RequestMapping("/search-board")
	public String searchboard(@RequestParam int page,@RequestParam String keyword,Model model,HttpSession session) {
		session.setAttribute("page", page);
		List<Board> list=boardService.selectKeywordBoardList(keyword,page);
		model.addAttribute("boardList", list);
		int bbsCount = boardService.countKeywordBoard(keyword);
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		int nowPageBlock = (int) (Math.ceil(page / 10.0));
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage=0;
		if(totalPage>nowPageBlock*10) {
			endPage=nowPageBlock*10;
		}else {
			endPage=totalPage;
		}
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "board/search-board";
	}
	
	//게시글 상세조회
	@RequestMapping("/board/{boardId}")
	public String getBoardDetails(@PathVariable int boardId, Model model) {
		Board board = boardService.selectArticle(boardId);
//		  String fileName = board.getFileName(); 
//		  if(fileName!=null) { 
//			  int fileLength = fileName.length(); 
//			  String fileType = fileName.substring(fileLength-4,fileLength).toUpperCase(); 
//			  model.addAttribute("fileType", fileType); 
//			  }

		
		model.addAttribute("board", board);
//		String dbId = boardService.getMemberId(board.getMemberId());
//		System.out.println(dbId);

		
		
		//logger.info("getBoardDetails" + board.toString());
		return "board/view";
	}


	// 게시글 입력
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String writeArticle(Model model, HttpSession session) {
		session.setAttribute("memberId", "me");
		return "board/write";
	}


	
	
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String writeArticle( Board board, BindingResult result, RedirectAttributes redirectAttrs, HttpSession session) {
		logger.info("/board/write : " + board.toString());
		board.setMemberId((String) session.getAttribute("memberId"));
		board.setMemberNickname(board.getMemberNickname());
		
		
		
		
		try {
			board.setBoardContent(board.getBoardContent().replace("\r\n", "<br>"));
			board.setBoardTitle(Jsoup.clean(board.getBoardTitle(), Safelist.basic()));
			board.setBoardContent(Jsoup.clean(board.getBoardContent(), Safelist.basic()));
			MultipartFile mfile = board.getFile();
			if (mfile != null && !mfile.isEmpty()) {
				BoardImage file = new BoardImage();
				file.setBoardImageName(mfile.getOriginalFilename());
				file.setBoardImageSize(mfile.getSize());
				file.setBoardImageType(mfile.getContentType());
				file.setBoardImage(mfile.getBytes());
				boardService.insertArticle(board, file);
			} else {
				boardService.insertArticle(board);
				
			}
			 }catch(Exception e) {
				 e.printStackTrace(); 
				 redirectAttrs.addFlashAttribute("message", e.getMessage()); 
				 } 
		 return "redirect:/board/" +  board.getBoardId();

	 }


	

	@RequestMapping("/boardImageFile/{boardImageId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int boardImageId) {
		BoardImage file = boardService.getFile(boardImageId);
		logger.info("getFile " + file.toString());
		System.out.println(file);
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = file.getBoardImageType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(file.getBoardImageSize());
		try {
			String encodedFileName = URLEncoder.encode(file.getBoardImageName(), "UTF-8");
			headers.setContentDispositionFormData("attachment", encodedFileName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<byte[]>(file.getBoardImage(), headers, HttpStatus.OK);
	}
	

	@RequestMapping(value="/board/delete/{boardId}", method=RequestMethod.GET)
	public String deleteBoard(@PathVariable int boardId, Model model, HttpSession session) {
		Board board = boardService.selectDeleteBoard(boardId);
		session.setAttribute("memberId", "me");
		model.addAttribute("memberId", board.getMemberId());
		
		
//		Board board = boardService.selectDeleteBoard(memberId);
//		board.setMemberId((String) session.getAttribute("memberId"));
//		model.addAttribute("boardId", boardId);

		
		
		
		
		return "board/delete";
	}
	

	@RequestMapping(value="/board/delete", method=RequestMethod.POST)
	public String deleteArticle(Board board, HttpSession session, RedirectAttributes model) {
//		board.setMemberId((String) session.getAttribute("memberId"));
        
		try {
			String authenticatedMemberId = (String) session.getAttribute("memberId");
			String dbId = boardService.getMemberId(board.getMemberId());
			System.out.println(dbId);
			if(dbId.equals(authenticatedMemberId)) {
				
				//이미지 삭제 되었는데 보드는 아직 삭제 안됨 보드도 삭제하는 코드 입력해야함
				boardService.deleteArticle(board.getBoardId());
				
				return "redirect:/board/" + board.getBoardId() + "/" + (Integer)session.getAttribute("page");
			}
				else {
				model.addAttribute("message", "이 게시물의 작성자가 아닙니다.");
				return "redirect:/board/delete/" + board.getBoardId();	
			}
		} 
			catch(Exception e) {
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
			return "error/runtime";
		}
	}
	
}
