package com.example.myapp.board.controller;

import java.io.UnsupportedEncodingException;




import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.myapp.board.model.Board;
import com.example.myapp.board.model.BoardImage;
import com.example.myapp.board.model.BoardPrep;
import com.example.myapp.board.service.IBoardService;

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
		System.out.println(endPage);
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
		System.out.println("*****************" + board);
		System.out.println(board.getMemberId());
		
		
		//logger.info("getBoardDetails" + board.toString());
		return "board/view";
	}


	// 게시글 입력
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String writeArticle(Model model, HttpSession session) {
		return "board/write";
	}


	
	
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String writeArticle( Board board,BindingResult result, RedirectAttributes redirectAttrs, HttpSession session) {
		logger.info("/board/write : " + board.toString());
		board.setMemberId((String) session.getAttribute("memberId"));
		try {
			board.setBoardContent(board.getBoardContent().replace("\r\n", "<br>"));
			board.setBoardTitle(Jsoup.clean(board.getBoardTitle(), Safelist.basic()));
			board.setBoardContent(Jsoup.clean(board.getBoardContent(), Safelist.basic()));
			List<BoardImage> fileList =new ArrayList<BoardImage>();
			
			for(MultipartFile m : board.getFile()) {
				MultipartFile mfile=m;
				if(mfile != null && !mfile.isEmpty()) {
					BoardImage file = new BoardImage();
					file.setBoardImageName(mfile.getOriginalFilename());
					file.setBoardImageSize(mfile.getSize());
					file.setBoardImageType(mfile.getContentType());
					file.setBoardImage(mfile.getBytes());
					fileList.add(file);
				}
			}
			if (fileList.size()>0) {
				boardService.insertArticle(board, fileList);
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
	

//	@RequestMapping(value="/board/delete/{boardId}", method=RequestMethod.GET)
//	public String deleteArticle(@PathVariable int boardId, Model model, HttpSession session) {
//		Board board = boardService.selectDeleteBoard(boardId);
////		model.addAttribute("memberId", board.getMemberId());
//		boardService.getMemberId(board.getMemberId());
////		board.setMemberId(board.getMemberId());
//
//		return "board/delete";
//	}
	
	@RequestMapping(value="/board/delete", method=RequestMethod.GET)
	public String deleteArticle(Board board, Model model, HttpSession session) {
		boardService.selectDeleteBoard(board.getBoardId());
//		model.addAttribute("memberId", board.getMemberId());
//		board.setMemberId(board.getMemberId());
//		boardService.getMemberId(board.getMemberId());
		return "board/delete";
	}
	
	

//	@RequestMapping(value="/board/delete", method=RequestMethod.POST)
//	public String deleteArticle(Board board, HttpSession session, RedirectAttributes model) {
////		board.setMemberId((String) session.getAttribute("memberId"));
//		
//		try {
//			String sessionMemberId = (String)session.getAttribute("memberId");
//			String dbId = boardService.getMemberId(board.getMemberId());
//			System.out.println("**************************" + dbId);
//			
//			if(board.getMemberId().equals(sessionMemberId)) {
//				boardService.deleteArticle(board.getBoardId());
////				return "redirect:/board/" + board.getBoardId() + "/" + (Integer)session.getAttribute("page");
////				return "redirect:/board=?page={page}" + (int)session.getAttribute("page");
//				return "redirect:/board/write";
//			}
//				else {
//				model.addAttribute("message", "이 게시물의 작성자가 아니므로 삭제가 불가능합니다.");
////				return "redirect:/board/" + board.getBoardId();	
//				return "redirect:/board/write";
//
//			}
//		} 
//			catch(Exception e) {
//			model.addAttribute("message", e.getMessage());
//			e.printStackTrace();
//			return "error/runtime";
//		}
//	}

//	// 게시물 삭제를 처리하는 핸들러 메서드
    @RequestMapping(value = "/board/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteArticle(String memberId,  HttpSession session) {
        try {
            String sessionMemberId = (String) session.getAttribute("memberId");
            int sessionBoardId = (int) session.getAttribute("boardId");
//            String dbId = boardService.getMemberId(board.getMemberId());
//            System.out.println("dbId" + dbId);

            if (memberId.equals(sessionMemberId)) {
                boardService.deleteArticle(sessionBoardId);
                return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
            } else {
            	return new ResponseEntity<>("이 게시물의 작성자가 아니므로 삭제가 불가능합니다.", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
    }
	

	
	
	//게시물 수정
//	@RequestMapping(value="/board/update/{boardId}", method=RequestMethod.GET)
//	public String updateArticle(@PathVariable int boardId, Model model) {
//
//		Board board = boardService.selectArticle(boardId);
//		model.addAttribute("boardTitle", board.getBoardTitle());
//		board.setBoardContent(board.getBoardContent().replaceAll("<br>", "\r\n"));
//		model.addAttribute("board", board);
//		return "board/update";
//	}
//	
//	@RequestMapping(value="/board/update", method=RequestMethod.POST)
//	public String updateArticle(Board board, RedirectAttributes redirectAttrs) {
//		logger.info("/board/update " + board.toString());
//		String dbPassword = boardService.getPassword(board.getBoardId());
//		
//		//멤버아이디랑 세션의멤버아이디가 일치하지 않을 경우
//		if(!board.getPassword().equals(dbPassword)) {
//			redirectAttrs.addFlashAttribute("passwordError", "게시글 비밀번호가 다릅니다");
//			return "redirect:/board/update/" + board.getBoardId();
//		}
//		try{
//			board.setBoardContent(board.getBoardContent().replace("\r\n", "<br>"));
//			board.setBoardTitle(Jsoup.clean(board.getBoardTitle(), Safelist.basic()));
//			board.setBoardContent(Jsoup.clean(board.getBoardContent(), Safelist.basic()));
//			MultipartFile mfile = board.getFile();
//			if(mfile!=null && !mfile.isEmpty()) {
//				logger.info("/board/update : " + mfile.getOriginalFilename());
//				BoardImage file = new BoardImage();
//				file.setBoardImageName(mfile.getOriginalFilename());
//				file.setBoardImageSize(mfile.getSize());
//				file.setBoardImageType(mfile.getContentType());
//				file.setBoardImage(mfile.getBytes());
//				logger.info("/board/update : " + file.toString());
//				boardService.updateArticle(board, file);
//			}else {
//				boardService.updateArticle(board);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			redirectAttrs.addFlashAttribute("message", e.getMessage());
//		}
//		return "redirect:/board/"+board.getBoardId();
//	}

