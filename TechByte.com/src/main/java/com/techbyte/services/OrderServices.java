package com.techbyte.services;

import java.text.ParseException;
import java.util.List;

import com.techbyte.entity.Order;
import com.techbyte.entity.OrderStatus;
import com.techbyte.entity.SalesReportDates;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.OrderException;
import com.techbyte.exception.PaymentException;
import com.techbyte.exception.ProductException;

public interface OrderServices {
	public Order createAnewOrder(String key,Order order)throws ProductException,LoginException,PaymentException;
	public String cancelAnOrder(String key,Integer orderId)throws ProductException,LoginException,PaymentException,OrderException;
	public List<Order>allOrderOfCustomer(String key)throws LoginException,OrderException;
	public List<Order>getAllCanceledOrders(String adminkey)throws LoginException,OrderException;
	public Order updateOrderStatus(String adminKey,Integer orderId,OrderStatus status)throws LoginException,OrderException;
	public List<Order>getAllOrderedOrders(String adminkey)throws LoginException,OrderException;
	public List<Order>getAllOrders(String adminkey)throws LoginException,OrderException;
	public List<Order>getShippedOrders(String adminkey)throws LoginException,OrderException;
	public List<Order>getOutForDeliveryOrders(String adminkey)throws LoginException,OrderException;
	public List<Order>getAllDeliveredOrders(String adminkey)throws LoginException,OrderException;
	public List<Order>getAllRefundedOrders(String adminkey)throws LoginException,OrderException;
	public List<Order>allOrderOfCustomerForAdmin(String adminKey,Integer userId)throws LoginException,OrderException;
	public List<Order>allOrdersFromJan1ToDec31(String adminKey)throws LoginException,OrderException,ParseException ;
	public List<Order>allOrdersFromStartToEndDate(String adminKey,SalesReportDates dates)throws LoginException,OrderException,ParseException ;
	public List<Order>getTopFiveOrdersByTotalOrderAmount(String adminKey)throws LoginException,OrderException;
}
