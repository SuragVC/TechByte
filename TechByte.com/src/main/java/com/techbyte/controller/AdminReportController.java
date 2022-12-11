package com.techbyte.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techbyte.entity.CurrentSessionAdmin;
import com.techbyte.entity.Order;
import com.techbyte.entity.SalesReportDates;
import com.techbyte.entity.User;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.OrderException;
import com.techbyte.exception.PaymentException;
import com.techbyte.exception.ProductException;
import com.techbyte.exception.UserNotFoundException;
import com.techbyte.repository.AdminSessionDAO;
import com.techbyte.services.AdminLoginService;
import com.techbyte.services.OrderServices;
import com.techbyte.services.UserService;

@RestController
@RequestMapping("/report")
public class AdminReportController {
	
	private OrderServices orderService;
	private UserService userServices;

	@Autowired
	public void setOrderService(OrderServices orderService) {
		this.orderService = orderService;
	}
	@Autowired
	public void setUserServices(UserService userServices) {
		this.userServices = userServices;
	}
	
	@GetMapping("/canceled/{key}")
	public ResponseEntity<List<Order>>getAllCanceledOrders(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.getAllCanceledOrders(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/ordered/{key}")
	public ResponseEntity<List<Order>>getAllOrderdOrders(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.getAllOrderedOrders(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/order/all/{key}")
	public ResponseEntity<List<Order>>getAllOrders(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.getAllOrders(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/shipped/{key}")
	public ResponseEntity<List<Order>>getShippedOrders(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.getShippedOrders(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/outfordelivery/{key}")
	public ResponseEntity<List<Order>>getOutForDeliveryOrders(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.getOutForDeliveryOrders(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/delivered/{key}")
	public ResponseEntity<List<Order>>getAllDeliveredOrders(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.getAllDeliveredOrders(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/refunded/{key}")
	public ResponseEntity<List<Order>>getAllRefundedOrders(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.getAllRefundedOrders(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/all/user/{adminKey}/{userId}")
	public ResponseEntity<List<Order>>getAllOrderDonebyCustomer(@RequestParam String adminkey,@RequestParam Integer userId) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.allOrderOfCustomerForAdmin(adminkey, userId);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/all/yearly/{adminKey}")
	public ResponseEntity<List<Order>>getYearlyOrderFrom_Jan_1_to_Dec_31(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException, ParseException{
		List<Order>list = orderService.allOrdersFromJan1ToDec31(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@PostMapping("/all/yearly/{adminKey}")
	public ResponseEntity<List<Order>>getOrdersFrom_StartDate_to_EndDate(@RequestParam String adminkey,@RequestBody SalesReportDates dates) throws ProductException, LoginException, PaymentException, OrderException, ParseException{
		List<Order>list = orderService.allOrdersFromStartToEndDate(adminkey,dates);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
	@GetMapping("/top/{key}")
	public ResponseEntity<List<Order>>getTopFiveOrdersByTotalOrderAmount(@RequestParam String adminkey) throws ProductException, LoginException, PaymentException, OrderException{
		List<Order>list = orderService.getTopFiveOrdersByTotalOrderAmount(adminkey);
		return new ResponseEntity<List<Order>>(list,HttpStatus.OK);
	}
}
