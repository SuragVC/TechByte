package com.techbyte.util;

import java.time.LocalTime;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techbyte.entity.OneTimePassword;
import com.techbyte.entity.RandomIdGenerator;
import com.techbyte.entity.User;
import com.techbyte.entity.UserStatus;
import com.techbyte.exception.OTPValidataionException;
import com.techbyte.repository.OTPDAO;
@Component
public class GmailServiceProvider {
	public OTPDAO otpDao;

	@Autowired
	public GmailServiceProvider(OTPDAO otpDao) {
		this.otpDao = otpDao;
	}

	public User gmailValidator(User user) throws OTPValidataionException{
		String to = user.getEmail();
		String from = "techbyteoficial@gmail.com";
		final String username = "techbyteoficial@gmail.com";
		final String password = "hamcxgjsgyjcmnnf";
		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			Integer OTP = RandomIdGenerator.getOneTimePassword();
			OneTimePassword otpBean = new OneTimePassword();
			otpBean.setOtpId(RandomIdGenerator.getRandomInteger());
			otpBean.setOTP(OTP);
			otpBean.setOpenTime(LocalTime.now());
			otpBean.setStatus("OPEN");
			user.setOtpId(otpBean.getOtpId());
			message.setSubject("OTP Verification");
			message.setText("Your One TIme Password : " + OTP);
			message.setDescription("This OTP will be valid for 10 minutes only!");
			Transport.send(message);
			otpDao.save(otpBean);
			user.setStatus(UserStatus.NOT_ACTIVE);
			return user;

		} catch (MessagingException e) {
			throw new OTPValidataionException("Error while fetching details");
		}
	}

}
