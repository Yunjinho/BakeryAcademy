package com.example.myapp.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.myapp.member.model.Order;
@Repository
@Mapper
public interface IOrderRepository {
	//주문 목록
	List<Order> selectDeliveryList(@Param("memberId") String memberId,@Param("status")String status);
	void updateOrder(@Param("status")String stauts,@Param("orderId")int orderId);
	void insertOrder(Order order);
	int selectOrderNumber();
}