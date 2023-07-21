package com.example.myapp.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.member.dao.IOrderRepository;
import com.example.myapp.member.model.Order;

@Service
public class OredrService implements IOrderService {
	@Autowired
	IOrderRepository orderRepository;
	
	@Override
	public List<Order> selectDeliveryList(String memberId, String status) {
		return orderRepository.selectDeliveryList(memberId, status);
	}

	@Override
	public void updateOrder(String stauts,int orderId) {
		orderRepository.updateOrder(stauts,orderId);
	}

}
