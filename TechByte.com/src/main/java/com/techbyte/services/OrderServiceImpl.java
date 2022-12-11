package com.techbyte.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.Address;
import com.techbyte.entity.Cart;
import com.techbyte.entity.CurrentSessionAdmin;
import com.techbyte.entity.CurrentSessionUser;
import com.techbyte.entity.Order;
import com.techbyte.entity.OrderStatus;
import com.techbyte.entity.Payment;
import com.techbyte.entity.Product;
import com.techbyte.entity.RandomIdGenerator;
import com.techbyte.entity.SalesReportDates;
import com.techbyte.entity.User;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.OrderException;
import com.techbyte.exception.PaymentException;
import com.techbyte.exception.ProductException;
import com.techbyte.repository.AddressDAO;
import com.techbyte.repository.AdminSessionDAO;
import com.techbyte.repository.OrderDAO;
import com.techbyte.repository.PaymentDAO;
import com.techbyte.repository.ProductDAO;
import com.techbyte.repository.SessionDAO;
import com.techbyte.repository.UserDAO;

@Service
public class OrderServiceImpl implements OrderServices {
	private OrderDAO orderDao;
	private PaymentDAO paymentDao;
	private AddressDAO addressDao;
	private UserDAO userDao;
	private ProductDAO productDao;
	private SessionDAO sessionDao;
	private AdminSessionDAO adminDao;
	@Autowired
	public void setAdminDao(AdminSessionDAO adminDao) {
		this.adminDao = adminDao;
	}

	@Autowired
	public void setSessionDao(SessionDAO sessionDao) {
		this.sessionDao = sessionDao;
	}

	@Autowired
	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	@Autowired
	public void setPaymentDao(PaymentDAO paymentDao) {
		this.paymentDao = paymentDao;
	}

	@Autowired
	public void setAddressDao(AddressDAO addressDao) {
		this.addressDao = addressDao;
	}

	@Autowired
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

	@Override
	public Order createAnewOrder(String key, Order order) throws ProductException, LoginException, PaymentException {
		order.setOrderId(RandomIdGenerator.getHighLegthID());
		order.setDate(LocalDate.now());
		order.setTime(LocalTime.now());
		Optional<CurrentSessionUser> currentUser = sessionDao.findByUuid(key);
		if(currentUser.isEmpty()){
			throw new LoginException("User Needs to login");
		}
		Integer userID = currentUser.get().getUserId();
		Optional<User> user = userDao.findById(userID);
		List<Order>orderList=user.get().getOrder();
		Integer product_id_1=order.getProduct_Id_1();
		Integer product_id_2=order.getProduct_Id_2();
		Integer product_id_3=order.getProduct_id_3();
		Integer product_1_qty=order.getProduct_1_qty();
		Integer product_2_qty=order.getProduct_2_qty();
		Integer product_3_qty=order.getProduct_3_qty();
		Product p1=null;Product p2=null;Product p3=null;
		if(product_id_1 == 0.0 && product_id_2==0.0&& product_id_3==0.0) {
			throw new ProductException("Minimum one product id requered!");
		}else {
			if(product_id_1!=0.0) {
				if(product_1_qty==0.0) {
					throw new ProductException("Product 1 Quantity is not provided!");
				}
				Optional<Product>pr1Opt=productDao.findById(product_id_1);
				if(pr1Opt.isEmpty()) {
					throw new ProductException("Product 1 id is wrong");
				}else {
					
					p1=pr1Opt.get();
					System.out.println(p1.toString());
					
				}
			}
			if(product_id_2!=0.0) {
				if(product_2_qty==0.0) {
					throw new ProductException("Product 2 Quantity is not provided!");
				}
				Optional<Product>pr2Opt=productDao.findById(product_id_2);
				if(pr2Opt.isEmpty()) {
					throw new ProductException("Product 3 id is wrong");
				}else {
					p2=pr2Opt.get();
					System.out.println(p2.toString());
				}
			}
			if(product_id_3!=0.0) {
				if(product_3_qty==0.0) {
					throw new ProductException("Product 3 Quantity is not provided!");
				}
				Optional<Product>pr3Opt=productDao.findById(product_id_3);
				if(pr3Opt.isEmpty()) {
					throw new ProductException("Product 3 id is wrong");
				}else {
					p3=pr3Opt.get();
					
				}
			}
		}
		Payment payment=order.getPayment();
		payment.setPaymentId(RandomIdGenerator.getHighLegthID());
		Double total =payment.getPaymentPrice();

		Double totalOrderAmount=0.0;
		if(p1!=null) {
			totalOrderAmount=totalOrderAmount+(p1.getPrice()*product_1_qty);
			
			System.out.println(totalOrderAmount);
		}
		if(p2!=null) {
			
			totalOrderAmount=totalOrderAmount+(p2.getPrice()*product_2_qty);
			System.out.println(totalOrderAmount);
		}
		if(p3!=null) {
		
			totalOrderAmount=totalOrderAmount+(p3.getPrice()*product_3_qty);
		}
		if(totalOrderAmount>total) {
			throw new PaymentException("Total Cost for product is "+totalOrderAmount);
		}

		order.setTotalOrderAmount(totalOrderAmount);
		
		payment.setTotalCostForProducts(totalOrderAmount);
		if(totalOrderAmount<total) {
			payment.setReturnToCustomer(total-totalOrderAmount);
		}else {
			payment.setReturnToCustomer(0.0);
		}
		Address addressOfCustomer = order.getAddress();
		addressOfCustomer.setAddressId(RandomIdGenerator.getHighLegthID());
		addressDao.save(addressOfCustomer);
		paymentDao.save(payment);
		orderDao.save(order);
		orderList.add(order);
		userDao.save(user.get());
		return order;
	}

	@Override
	public String cancelAnOrder(String key, Integer orderId) throws ProductException, LoginException, PaymentException,OrderException {
		Optional<CurrentSessionUser> currentUser = sessionDao.findByUuid(key);
		if(currentUser.isEmpty()){
			throw new LoginException("User Needs to login");
		}
		Optional<Order> orderOpt=orderDao.findById(orderId);
		if(orderOpt.isEmpty()) {
			throw new OrderException("Order not found with id "+orderId);
		}
		Order order=orderOpt.get();
		if(order.getOrderStatus().equals(OrderStatus.Delivered)) {
			throw new OrderException("Order already delivered");
		}
		if(order.getOrderStatus().equals(OrderStatus.CancelAndApplyforRefund)) {
			throw new OrderException("Order already applyed for refund");
		}
		if(order.getOrderStatus().equals(OrderStatus.CanceledAndRefunded)) {
			throw new OrderException("Order already canceled and refunded");
		}
		order.setOrderStatus(OrderStatus.CancelAndApplyforRefund);
		orderDao.save(order);
		return "Order canceled and applyed for refund";
	}

	@Override
	public List<Order> allOrderOfCustomer(String key) throws LoginException, OrderException {
		Optional<CurrentSessionUser> currentUser = sessionDao.findByUuid(key);
		if(currentUser.isEmpty()){
			throw new LoginException("User Needs to login");
		}
		Integer userID = currentUser.get().getUserId();
		Optional<User> user = userDao.findById(userID);
		List<Order>list = user.get().getOrder();
		if(list.size()==0) {
			throw new OrderException("There is no order done by customer");
		}
		return list;
	}

	@Override
	public List<Order> getAllCanceledOrders(String adminkey) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminkey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		List<Order>list = orderDao.getAllCanceledOrders();
		if(list.size()==0) {
			throw new OrderException("No order cancelations");
		}
		
		return list;
	}

	@Override
	public Order updateOrderStatus(String adminKey,Integer orderId,OrderStatus status) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminKey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		Optional<Order> orderOpt=orderDao.findById(orderId);
		if(orderOpt.isEmpty()) {
			throw new OrderException("Order not found with id "+orderId);
		}
		if(orderOpt.get().getOrderStatus().equals(status)) {
			throw new OrderException("Order already in the same status");
		}
		orderOpt.get().setOrderStatus(status);
		System.out.println(orderOpt.get().getOrderStatus());
		return orderDao.save(orderOpt.get());
	}

	@Override
	public List<Order> getAllOrderedOrders(String adminkey) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminkey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		List<Order>list = orderDao.getAllOrderedOrders();
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

	@Override
	public List<Order> getAllOrders(String adminkey) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminkey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		List<Order>list = orderDao.findAll();
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

	@Override
	public List<Order> getShippedOrders(String adminkey) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminkey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		List<Order>list = orderDao.getAllShippedOrders();
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

	@Override
	public List<Order> getOutForDeliveryOrders(String adminkey) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminkey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		List<Order>list = orderDao.getAllOutForDeliveryOrders();
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

	@Override
	public List<Order> getAllDeliveredOrders(String adminkey) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminkey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		List<Order>list = orderDao.getAllDeliveredOrders();
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

	@Override
	public List<Order> getAllRefundedOrders(String adminkey) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminkey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		List<Order>list = orderDao.getAllRefundedOrders();
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

	@Override
	public List<Order> allOrderOfCustomerForAdmin(String adminKey, Integer userId) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminKey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		Optional<User> user = userDao.findById(userId);
		List<Order>list = user.get().getOrder();
		if(list.size()==0) {
			throw new OrderException("There is no order done by customer");
		}
		return list;
	}

	@Override
	public List<Order> allOrdersFromJan1ToDec31(String adminKey) throws LoginException, OrderException, ParseException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminKey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		int year=LocalDate.now().getYear();
		String date=year+"-01-01";
		LocalDate startDate = LocalDate.parse(date);

		LocalDate endDate=LocalDate.now();
		
		List<Order>list = orderDao.getAllOrdersFromStartToEnd(startDate, endDate);
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

	@Override
	public List<Order> allOrdersFromStartToEndDate(String adminKey, SalesReportDates dates)
			throws LoginException, OrderException, ParseException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminKey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}

		List<Order>list = orderDao.getAllOrdersFromStartToEnd(dates.getStartDate(), dates.getEndDate());
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

	@Override
	public List<Order> getTopFiveOrdersByTotalOrderAmount(String adminKey) throws LoginException, OrderException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminKey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login");
		}
		List<Order>list = orderDao.getTopFiveOrdersByTotalOrderAmount();
		if(list.size()==0) {
			throw new OrderException("No orders are placed");
		}
		
		return list;
	}

}
