package com.example.myapp.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.member.dao.ICartRepository;
import com.example.myapp.member.dao.IMemberRepository;
import com.example.myapp.member.model.Cart;
import com.example.myapp.member.model.Member;
import com.example.myapp.member.model.Order;

import jakarta.transaction.Transactional;

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
			if(amount.get(i)==0) {
				cartRepository.deleteCartProduct(cartId.get(i));
			}else {
				cartRepository.updateCartList(cartId.get(i), amount.get(i));
			}
		}
	}

	@Override
	public int countCart(String memberId) {
		return cartRepository.countCart(memberId);
	}

	@Transactional
	@Override
	public void insertCart(String memberId, List<Integer> productId, List<Integer> amount) {
		Cart cart=new Cart();
		cart.setMemberId(memberId);
		for(int i=0;i<productId.size();i++) {
			cart.setProductId(productId.get(i));
			cart.setProductCount(amount.get(i));
			cartRepository.insertCart(cart);
		}
	}
}
