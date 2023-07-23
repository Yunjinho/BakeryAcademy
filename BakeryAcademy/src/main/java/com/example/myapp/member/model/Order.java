package com.example.myapp.member.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Order {
	private int orderId;
	private int productId;
	private String memberId;
	private int orderNumber;
	private String orderAddress;
	private String orderStatus;
	private Date orderDate;
	private int orderCount;
	
	private String productName;
}