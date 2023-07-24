package com.example.myapp.member.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.myapp.member.model.Cart;
import com.example.myapp.member.model.Order;

@Service
public interface ICartService {
	List<Cart> selectCartList(String memberId);
	int totalProductPrice(String memberId);
	void updateCartList(List<Integer>cartId,List<Integer>amount);
	int countCart(String memberId);
}
