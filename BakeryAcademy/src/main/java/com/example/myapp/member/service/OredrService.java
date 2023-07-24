package com.example.myapp.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.member.dao.ICartRepository;
import com.example.myapp.member.dao.IOrderRepository;
import com.example.myapp.member.model.Order;

import jakarta.transaction.Transactional;

@Service
public class OredrService implements IOrderService {
	@Autowired
	IOrderRepository orderRepository;
	@Autowired
	ICartRepository cartRepository;
	
	@Override
	public List<Order> selectDeliveryList(String memberId, String status) {
		return orderRepository.selectDeliveryList(memberId, status);
	}

	@Override
	public void updateOrder(String stauts,int orderId) {
		orderRepository.updateOrder(stauts,orderId);
	}

	@Override
	@Transactional
	public void insertOrder(List<Integer> productId, List<Integer> amount, String name, String address,String addressDetail,String memberId) {
		int orderNumber=orderRepository.selectOrderNumber();
		Order order=new Order(0, 0, memberId, orderNumber, address, memberId, null, 0, address, addressDetail);
		for(int i=0;i<productId.size();i++) {
			order.setProductId(productId.get(i));
			order.setOrderCount(amount.get(i));
			orderRepository.insertOrder(order);
		}
		cartRepository.deleteCart(memberId);
	}

	@Override
	public List<Order> selectAdminDeliveryList(String status) {
		return orderRepository.selectAdminDeliveryList(status);
	}

	@Override
	public int countOrder(String stauts) {
		return orderRepository.countOrder(stauts);
	}

	@Override
	public Order selectOrderDetail(int orderId) {
		return orderRepository.selectOrderDetail(orderId);
	}

	@Override
	public void updateOrderStatus(Order order) {
		if(order.getOrderStatus().equals("환불 완료")) {
			orderRepository.deleteOrder(order.getOrderId());
		}else {
			orderRepository.updateOrderStatus(order);
		}
	}

}
