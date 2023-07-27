package com.example.myapp.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.myapp.member.model.Cart;
@Repository
@Mapper
public interface ICartRepository {
	List<Cart> selectCartList(@Param("memberId")String memberId);
	int totalProductPrice(@Param("memberId")String memberId);
	void updateCartList(@Param("cartId")int cartId,@Param("amount")int amount);
	void deleteCart(@Param("memberId")String memberId);
	void deleteCartProduct(int cartId);
	int countCart(@Param("memberId")String memberId);
	void insertCart(Cart cart);
}