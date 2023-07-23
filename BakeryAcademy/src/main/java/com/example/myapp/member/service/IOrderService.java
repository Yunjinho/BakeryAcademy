package com.example.myapp.member.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.myapp.member.model.Order;

@Service
public interface IOrderService {
	//주문 목록
	List<Order> selectDeliveryList(String memberId,String status);
	void updateOrder(String stauts,int orderId);
}
