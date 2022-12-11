package com.techbyte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techbyte.entity.Order;
import com.techbyte.entity.OrderStatus;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.OrderException;
import com.techbyte.exception.PaymentException;
import com.techbyte.exception.ProductException;
import com.techbyte.services.OrderServices;

@RestController
@RequestMapping("/main")
public class AdminController {
	private OrderServices orderService;
	@Autowired
	public void setOrderService(OrderServices orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping("/updateOrder/{key}/{orderId}")
	public ResponseEntity<Order>updateOrderStatus(@RequestParam String key,@RequestParam Integer orderId,@RequestBody OrderStatus status) throws  LoginException ,OrderException{
		Order savedOrder = orderService.updateOrderStatus(key, orderId, status);
		System.out.println(savedOrder.toString());
		return new ResponseEntity<Order>(savedOrder,HttpStatus.ACCEPTED);
	}
}
