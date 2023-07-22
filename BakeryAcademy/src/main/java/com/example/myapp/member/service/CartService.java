package com.example.myapp.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.member.dao.ICartRepository;
import com.example.myapp.member.dao.IMemberRepository;
import com.example.myapp.member.model.Cart;
import com.example.myapp.member.model.Member;
import com.example.myapp.member.model.Order;

@Service
public class CartService implements ICartService {

	@Autowired
	ICartRepository cartRepository;
	
	@Override
	public List<Cart> selectCartList(String memberId) {
		return cartRepository.selectCartList(memberId);
	}

	@Override
	public int totalProductPrice(String memberId) {
		return cartRepository.totalProductPrice(memberId);
	}

	@Override
	public void updateCartList(List<Integer> cartId, List<Integer> amount) {
		for(int i=0;i<cartId.size();i++) {
			cartRepository.updateCartList(cartId.get(i), amount.get(i));
		}
	}
}