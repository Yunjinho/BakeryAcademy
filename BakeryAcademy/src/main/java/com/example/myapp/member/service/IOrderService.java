package com.example.myapp.member.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.myapp.member.model.Order;

public interface IOrderService {
	//주문 목록
	List<Order> selectDeliveryList(String memberId,String status);
	void updateOrder(String stauts,int orderId);
	String insertOrder(List<Integer>productId,List<Integer>amont,String name,String address,String addressDetail,String memberId);
	List<Order> selectAdminDeliveryList(String status);
	int countOrder(String stauts);
	Order selectOrderDetail(int orderId);
	void updateOrderStatus(Order order);
}
