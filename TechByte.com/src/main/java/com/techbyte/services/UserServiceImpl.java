package com.techbyte.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.Cart;
import com.techbyte.entity.CurrentSessionAdmin;
import com.techbyte.entity.OneTimePassword;
import com.techbyte.entity.Product;
import com.techbyte.entity.RandomIdGenerator;
import com.techbyte.entity.User;
import com.techbyte.entity.UserStatus;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.OTPValidataionException;
import com.techbyte.exception.UserNotFoundException;
import com.techbyte.repository.AdminSessionDAO;
import com.techbyte.repository.CartDAO;
import com.techbyte.repository.OTPDAO;
import com.techbyte.repository.UserDAO;
import com.techbyte.util.GmailServiceProvider;



@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO signUpDAO;
	@Autowired
	private CartDAO cartDao;
	@Autowired
	private AdminSessionDAO adminDao;
	@Autowired
	private CurrentUserSessionService getCurrentLoginUserSession;
	@Autowired
	private GmailServiceProvider gmailProvider;
	@Autowired
	private OTPDAO otpDao;
	@Override
	public User createNewSignUp(User newSignUp) throws LoginException {
		newSignUp.setUserId(RandomIdGenerator.getRandomInteger());
		newSignUp.setEmail(newSignUp.getEmail().toLowerCase());
		Optional<User> opt = signUpDAO.findByMobileNo(newSignUp.getMobileNo());
		if(opt.isPresent())
		{
			throw new LoginException("User Already Exist!");
		}
		Optional<User> emailOpt = signUpDAO.findByEmail(newSignUp.getEmail());
		if(emailOpt.isPresent())
		{
			throw new LoginException("User Already Registered with same Mail Id");
		}
		Cart cart= new Cart();
		List<Product>list=new ArrayList<>();
		cart.setProducts(list);
		cart.setCartId(RandomIdGenerator.getHighLegthID());
		cart.setUserName(newSignUp.getUserName());
		cartDao.save(cart);
		newSignUp.setCart(cart);
		signUpDAO.save(newSignUp);
		newSignUp.setOtpId(1);
		newSignUp.setStatus(UserStatus.ACTIVE);
		return newSignUp;
	}
	@Override
	public User updateSignUpDetails(User signUp, String key) throws LoginException {
		User signUpDetails = getCurrentLoginUserSession.getSignUpDetails(key);
		
		if(signUpDetails == null)
		{
			throw new LoginException("UnAuthorized!!! No User Found....Try To login first!");
		}
		
		if(signUpDetails.getUserId() == signUp.getUserId())
			{
			signUpDAO.save(signUp);
			return signUp;
			}
		else
			throw new LoginException("Can't change UserId!!");
	}
	@Override
	public User createNewSignUpWithGmailValidation(User newSignUp) throws LoginException, OTPValidataionException {
		newSignUp.setUserId(RandomIdGenerator.getRandomInteger());
		newSignUp.setEmail(newSignUp.getEmail().toLowerCase());
		Optional<User> opt = signUpDAO.findByMobileNo(newSignUp.getMobileNo());
		if(opt.isPresent())
		{
			throw new LoginException("User Already Exist!");
		}
		Optional<User> emailOpt = signUpDAO.findByEmail(newSignUp.getEmail());
		if(emailOpt.isPresent())
		{
			throw new LoginException("User Already Registered with same Mail Id");
		}
		Cart cart= new Cart();
		List<Product>list=new ArrayList<>();
		cart.setProducts(list);
		cart.setCartId(RandomIdGenerator.getHighLegthID());
		cart.setUserName(newSignUp.getUserName());
		User user=gmailProvider.gmailValidator(newSignUp);
		cartDao.save(cart);
		newSignUp.setCart(cart);
		return signUpDAO.save(newSignUp);
	}
	@Override
	public String otpValidation(String gmail, Integer OTP) throws OTPValidataionException, LoginException {
		gmail=gmail.toLowerCase();
		Optional<User> userOpt = signUpDAO.findByEmail(gmail);
		if(userOpt.isEmpty())
		{
			throw new LoginException("User Not found with gmail id "+gmail);
		}
		
		Optional<OneTimePassword> otpOpt= otpDao.findById(userOpt.get().getOtpId());
		int sendOtp = (int)otpOpt.get().getOTP();
		if(sendOtp!=(int)OTP){
			throw new  OTPValidataionException("Incorect OTP!");
		}
		otpOpt.get().setStatus("VALIDATED");
		otpDao.save(otpOpt.get());
		userOpt.get().setStatus(UserStatus.ACTIVE);
		signUpDAO.save(userOpt.get());
		return "Successfully Signup";
		
	}

}
