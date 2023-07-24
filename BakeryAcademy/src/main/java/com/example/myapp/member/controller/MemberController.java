package com.example.myapp.member.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.board.model.Board;
import com.example.myapp.member.MemberValidator;
import com.example.myapp.member.model.Cart;
import com.example.myapp.member.model.Member;
import com.example.myapp.member.model.Order;
import com.example.myapp.member.service.ICartService;
import com.example.myapp.member.service.IMemberService;
import com.example.myapp.member.service.IOrderService;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.ProductReview;
import com.example.myapp.product.service.IProductReviewService;
import com.example.myapp.product.service.IProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private IMemberService memberService;

	@Autowired
	private MemberValidator memberValidator;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IProductReviewService productReviewService;
	 @InitBinder("Member")
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
					return "redirect:/"; // memberinfo 페이지로 리다이렉션
				} else { // 아이디는 있지만 비밀번호가 다른 경우
					model.addAttribute("message", " 비밀번호를 잘못 입력했습니다.\r\n"
							+ "입력하신 내용을 다시 확인해주세요..");
				}
			}
		} else {
			model.addAttribute("message", "아이디(로그인 전용 아이디)를 잘못 입력했습니다.\r\n"
					+ "입력하신 내용을 다시 확인해주세요.");
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
		return "index";
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
			model.addAttribute("message", "사용자가 아닙니다.");
			return "member/login";

		}
	}

	@RequestMapping(value = "/member/update", method = RequestMethod.POST)
	public String updateMember(Member member, HttpSession session, Model model) {
		System.out.println(member);
		memberService.updateMember(member);
		return "redirect:/";
	}

	@RequestMapping(value = "/member/delete", method = RequestMethod.GET)
	public String deleteMember(HttpSession session, Model model) {
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null && !memberId.equals("")) {
			Member member = memberService.selectMember(memberId);
			model.addAttribute("member", member);
			model.addAttribute("message", "");
			return "member/delete";
		} else {
			// userid가 세션에 없을 때(로그인 하지 않았을 때)
			model.addAttribute("message", "NOT_LOGIN_UESR");
			return "member/login";
		}
	}

	@RequestMapping(value = "/member/delete", method = RequestMethod.POST)
	public String deleteMember(String memberPassword, HttpSession session, Model model) {
		try {
			Member member = new Member();
			member.setMemberId((String) session.getAttribute("memberId"));
			String dbpw = memberService.getPassword(member.getMemberId());
			if (memberPassword != null && memberPassword.equals(dbpw)) {
				member.setMemberPassword(memberPassword);
				memberService.deleteMember(member);
				session.invalidate(); // 삭제되었으면 로그아웃 처리
				return "redirect:/";
			} else {
				model.addAttribute("message", "비밀번호가 틀렸습니다.");
				return "member/delete";
			}
		} catch (Exception e) {
			model.addAttribute("message", "DELETE_FAIL");
			e.printStackTrace();
			return "member/delete";
		}
	}
	
	@RequestMapping(value=("/member/my-orders"),method=RequestMethod.GET)
	public String myOrderList(Model model,HttpSession session) {
		List<Order> orderList=orderService.selectDeliveryList((String)session.getAttribute("memberId"), "상품 준비중");
		model.addAttribute("beforeDeliveryList", orderList);

		orderList=orderService.selectDeliveryList((String)session.getAttribute("memberId"), "배송중");
		model.addAttribute("delivering", orderList);

		orderList=orderService.selectDeliveryList((String)session.getAttribute("memberId"), "배송 완료");
		model.addAttribute("afterDeliveryList", orderList);
		
		orderList=orderService.selectDeliveryList((String)session.getAttribute("memberId"), "환불 요청중");
		model.addAttribute("refunList", orderList);
		return "/member/my-orders";
	}
	
	@RequestMapping("/member/update-order")
	public String updateOrderStatus(@RequestParam String status,@RequestParam int orderId, HttpSession session,Model model) {
		orderService.updateOrder(status, orderId);
		
		List<Order> orderList=orderService.selectDeliveryList((String)session.getAttribute("memberId"), "상품 준비중");
		model.addAttribute("beforeDeliveryList", orderList);
		orderList=orderService.selectDeliveryList((String)session.getAttribute("memberId"), "배송중");
		model.addAttribute("delivering", orderList);
		orderList=orderService.selectDeliveryList((String)session.getAttribute("memberId"), "배송 완료");
		model.addAttribute("afterDeliveryList", orderList);
		orderList=orderService.selectDeliveryList((String)session.getAttribute("memberId"), "환불 요청중");
		model.addAttribute("refunList", orderList);
		
		return "/member/my-orders";
	}
	
	@RequestMapping(value="/member/shoping-cart",method=RequestMethod.GET)
	public String viewMyCart(HttpSession session,Model model) {
		String memberId=(String)session.getAttribute("memberId");
		List<Cart> cartList=cartService.selectCartList(memberId);
		int totalPrice=0;
		if(cartList.size()>0) {
			totalPrice=cartService.totalProductPrice(memberId);
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartList);
		return "/member/shoping-cart";
	}
	
	@RequestMapping(value="/member/update-cart",method=RequestMethod.POST)
	public String updateCart(@RequestParam(value="cartId") List<Integer> cartId,@RequestParam(value="amount") List<Integer> amount,@RequestParam(value="productId") List<Integer> productId) {
		cartService.updateCartList(cartId, amount);
		return "redirect:/member/shoping-cart";
	}
	
	@RequestMapping(value="/member/order",method=RequestMethod.GET)
	public String order(@RequestParam(value="productId") List<Integer> productId,@RequestParam(value="amount") List<Integer> amount,@RequestParam(value="totalPrice")int totalPrice,HttpSession session,Model model) {
		Map<Integer, Integer> productList=new HashMap<Integer, Integer>();
		Member member=new Member();
		for(int i=0;i<productId.size();i++) {
			productList.put(productId.get(i),amount.get(i));
		}
		model.addAttribute("productList", productList);
		member=memberService.selectMember((String)session.getAttribute("memberId"));
		model.addAttribute("member", member);
		model.addAttribute("totalPrice", totalPrice);
		return "/member/order";
	}

	@RequestMapping(value="/member/order",method=RequestMethod.POST)
	public String insertOrder(@RequestParam(value="product") List<Integer> product,@RequestParam(value="amount") List<Integer> amount,
			@RequestParam(value="memberId") String name,@RequestParam(value="orderAddress") String orderAddress,@RequestParam(value="orderAddressDetail") String orderAddressDetail, 
			HttpSession session,Model model) {
		orderService.insertOrder(product, amount, name,orderAddress,orderAddressDetail,(String)session.getAttribute("memberId"));
		return "redirect:/member/my-orders";
	}
	
	
	@RequestMapping(value="/admin/orders",method=RequestMethod.GET)
	public String managerOrders(@RequestParam int beforePage,@RequestParam int ingPage,@RequestParam int afterPage,@RequestParam int refunPage,Model model) {
		
		//
		List<Order> orderList=orderService.selectAdminDeliveryList("상품 준비중");
		model.addAttribute("beforeDeliveryList", orderList);
		int bbsCount = orderService.countOrder("상품 준비중");
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		int nowPageBlock = (int) (Math.ceil(beforePage / 10.0));
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage=0;
		if(totalPage>nowPageBlock*10) {
			endPage=nowPageBlock*10;
		}else {
			endPage=totalPage;
		}
		model.addAttribute("beforeTotalPageCount", totalPage);
		model.addAttribute("beforeNowPage", beforePage);
		model.addAttribute("beforeTotalPageBlock", totalPageBlock);
		model.addAttribute("beforeNowPageBlock", nowPageBlock);
		model.addAttribute("beforeStartPage", startPage);
		model.addAttribute("beforeEndPage", endPage);
		//
		//
		orderList=orderService.selectAdminDeliveryList("배송중");
		model.addAttribute("delivering", orderList);
		bbsCount = orderService.countOrder("배송중");
		totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		nowPageBlock = (int) (Math.ceil(beforePage / 10.0));
		startPage = (nowPageBlock - 1) * 10 + 1;
		endPage=0;
		if(totalPage>nowPageBlock*10) {
			endPage=nowPageBlock*10;
		}else {
			endPage=totalPage;
		}
		model.addAttribute("ingTotalPageCount", totalPage);
		model.addAttribute("ingNowPage", beforePage);
		model.addAttribute("ingTotalPageBlock", totalPageBlock);
		model.addAttribute("ingNowPageBlock", nowPageBlock);
		model.addAttribute("ingStartPage", startPage);
		model.addAttribute("ingEndPage", endPage);
		//
		//
		orderList=orderService.selectAdminDeliveryList("배송 완료");
		model.addAttribute("afterDeliveryList", orderList);
		bbsCount = orderService.countOrder("배송 완료");
		totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		nowPageBlock = (int) (Math.ceil(beforePage / 10.0));
		startPage = (nowPageBlock - 1) * 10 + 1;
		endPage=0;
		if(totalPage>nowPageBlock*10) {
			endPage=nowPageBlock*10;
		}else {
			endPage=totalPage;
		}
		model.addAttribute("afterTotalPageCount", totalPage);
		model.addAttribute("afterNowPage", beforePage);
		model.addAttribute("afterTotalPageBlock", totalPageBlock);
		model.addAttribute("afterNowPageBlock", nowPageBlock);
		model.addAttribute("afterStartPage", startPage);
		model.addAttribute("afterEndPage", endPage);
		//
		//
		orderList=orderService.selectAdminDeliveryList("환불 요청중");
		model.addAttribute("refunList", orderList);
		bbsCount = orderService.countOrder("배송중");
		totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		nowPageBlock = (int) (Math.ceil(beforePage / 10.0));
		startPage = (nowPageBlock - 1) * 10 + 1;
		endPage=0;
		if(totalPage>nowPageBlock*10) {
			endPage=nowPageBlock*10;
		}else {
			endPage=totalPage;
		}
		model.addAttribute("refunTotalPageCount", totalPage);
		model.addAttribute("refunNowPage", beforePage);
		model.addAttribute("refunTotalPageBlock", totalPageBlock);
		model.addAttribute("refunNowPageBlock", nowPageBlock);
		model.addAttribute("refunStartPage", startPage);
		model.addAttribute("refunEndPage", endPage);
		//
		
		return "/admin/orders";
	}
	
	@RequestMapping(value="/admin/order-detail",method=RequestMethod.GET)
	public String viewOrderDetail(@RequestParam int orderId,Model model) {
		Order order=new Order();
		order=orderService.selectOrderDetail(orderId);
		model.addAttribute("order", order);
		return "/admin/orders-detail";
	}

	@RequestMapping(value="/admin/order-detail",method=RequestMethod.POST)
	public String updateOrderDetail(Order order,Model model) {
		orderService.updateOrderStatus(order);
		return "redirect:/admin/orders?beforePage=1&ingPage=1&afterPage=1&refunPage=1";
	}
	
	@RequestMapping(value="member/write-review",method=RequestMethod.GET)
	public String viewReview(@RequestParam int orderId, @RequestParam int productId,Model model) {
		Product product=new Product();
		product=productService.selectProduct(productId);
		model.addAttribute("product", product);
		Order order =new Order();
		order=orderService.selectOrderDetail(orderId);
		model.addAttribute("order", order);
		return "/member/write-review";
	}
	@RequestMapping(value="member/write-review",method=RequestMethod.POST)
	public String writeReview(@RequestParam int orderId,ProductReview productReview, HttpSession session) {
		String memberId=(String)session.getAttribute("memberId");
		productReview.setMemberId(memberId);
		productReview.setOrderId(orderId);
		productReviewService.insertProductReview(productReview);
		return "redirect:/member/my-orders";
	}
	
}
	
