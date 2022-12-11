package com.techbyte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.techbyte.entity.Order;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.OrderException;
import com.techbyte.exception.PaymentException;
import com.techbyte.exception.ProductException;
import com.techbyte.services.OrderServices;
@RestController
@RequestMapping("/order")
public class OrderController {
	private OrderServices orderService;
	@Autowired
	public void setOrderService(OrderServices orderService) {
		this.orderService = orderService;
	}
	@PostMapping("/create/{key}")
	public ResponseEntity<Order>createAorder(@RequestParam String key,@RequestBody Order order) throws ProductException, LoginException, PaymentException{
		Order savedOrder = orderService.createAnewOrder(key, order);
		return new ResponseEntity<Order>(savedOrder,HttpStatus.ACCEPTED);
	}
	@GetMapping("/create/{key}/{orderId}")
	public ResponseEntity<String>cancelAnOrderAndApplyForRefund(@RequestParam String key,@RequestParam Integer orderId) throws ProductException, LoginException, PaymentException, OrderException{
		String message = orderService.cancelAnOrder(key, orderId);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	@GetMapping("/all/{key}")
	public ResponseEntity<List<Order>>getAllOrderDonebyCustomer(@RequestParam String key) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.allOrderOfCustomer(key);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	
}
