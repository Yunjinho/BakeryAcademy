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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;
import com.example.myapp.board.service.IBoardService;
import jakarta.servlet.http.HttpSession;






@Controller
public class BoardController {
static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	IBoardService boardService;
	


    
    
	
//	//게시글 리스트 조회
//	@RequestMapping("/board/cat")
//	public String getListByCategory(HttpSession session, Model model) {
//		return getListByCategory(session, model);
//	}
	

	
	
	
	
	
	//게시글 상세조회
	@RequestMapping("/board/{boardId}")
	public String getBoardDetails(@PathVariable int boardId, Model model) {
		Board board = boardService.selectArticle(boardId);
		
//		  String fileName = board.getFileName(); if(fileName!=null) { int fileLength =
//		  fileName.length(); String fileType = fileName.substring(fileLength-4,
//		  fileLength).toUpperCase(); model.addAttribute("fileType", fileType); }

		model.addAttribute("board", board); 
//		logger.info("getBoardDetails" + board.toString()); 
		
		return "board/view";
	}
	
//	@RequestMapping("/board/{boardId}")
//	public String getBoardDetails(@PathVariable int boardId, Model model) {
//		return getBoardDetails(boardId, model);
//	}

	
	//게시글 입력
	@RequestMapping(value="/board/write", method=RequestMethod.GET)
	public String writeArticle(Model model,HttpSession session) {
			
		session.setAttribute("memberId", "my");
		
		
		
//		Board board = boardService.selectArticle(board);
//		model.addAttribute("board", board);
		
//		model.addAttribute("boardId", boardId);
		return "board/write";
	}
	
	
	
	 @RequestMapping(value="/board/write", method=RequestMethod.POST) 
	 public String writeArticle(Board board, BindingResult result, RedirectAttributes redirectAttrs, HttpSession session) {
		 logger.info("/board/write : " + board.toString()); 	 
		 
		 board.setMemberId((String) session.getAttribute("memberId"));
		 try {
			 board.setBoardContent(board.getBoardContent().replace("\r\n", "<br>"));
			 board.setBoardTitle(Jsoup.clean(board.getBoardTitle(), Safelist.basic()));
			 board.setBoardContent(Jsoup.clean(board.getBoardContent(),Safelist.basic())); 
			 MultipartFile mfile = board.getFile(); 
			 if(mfile!=null && !mfile.isEmpty()) { 
				 BoardImage file = new BoardImage();
				 
				 file.setBoardImageSize(mfile.getSize());
				 file.setBoardImageType(mfile.getContentType());
				 file.setBoardImage(mfile.getBytes());
				 
				 boardService.insertArticle(board,file);
			} else {
				boardService.insertArticle(board);
			}
			 }catch(Exception e) {
				 e.printStackTrace(); 
				 redirectAttrs.addFlashAttribute("message", e.getMessage()); 
				 } 
		 return "redirect:/board/" +  board.getBoardId();

	 }

	 
	 @RequestMapping("/file/{boardImageId}")
		public ResponseEntity<byte[]> getFile(@PathVariable int boardImageId){
			BoardImage file = boardService.getFile(boardImageId);
			System.out.println(file);
			logger.info("getFile" + file.toString());
			final HttpHeaders headers = new HttpHeaders();
			String[] mtypes = file.getBoardImageType().split("/");
			headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
			headers.setContentLength(file.getBoardImageSize());
			try {
				String encodedFileName = URLEncoder.encode(file.getBoardImageName(), "UTF-8");
				headers.setContentDispositionFormData("attachment", encodedFileName);
			} catch(UnsupportedEncodingException e){
				throw new RuntimeException(e);
			}
			return new ResponseEntity<byte[]>(file.getBoardImage(), headers, HttpStatus.OK);
		}
}
	
	
	


