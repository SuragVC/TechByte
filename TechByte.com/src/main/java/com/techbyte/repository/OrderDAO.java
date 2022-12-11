package com.techbyte.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techbyte.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Integer>{
	@Query(value="select * from customer_order where order_status=4",nativeQuery = true)
	List<Order>getAllCanceledOrders();
	@Query(value="select * from customer_order where order_status=0",nativeQuery = true)
	List<Order>getAllOrderedOrders();
	@Query(value="select * from customer_order where order_status=1",nativeQuery = true)
	List<Order>getAllShippedOrders();
	@Query(value="select * from customer_order where order_status=2",nativeQuery = true)
	List<Order>getAllOutForDeliveryOrders();
	@Query(value="select * from customer_order where order_status=3",nativeQuery = true)
	List<Order>getAllDeliveredOrders();
	@Query(value="select * from customer_order where order_status=5",nativeQuery = true)
	List<Order>getAllRefundedOrders();
	@Query(value="select * from customer_order where date>=?1 and date<=?2 order by date",nativeQuery = true)
	List<Order>getAllOrdersFromStartToEnd(LocalDate startDate,LocalDate endDate);
	@Query(value="select * from customer_order order by total_order_amount desc limit 5;",nativeQuery = true)
	List<Order>getTopFiveOrdersByTotalOrderAmount();
}
