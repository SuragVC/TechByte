package com.techbyte.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.techbyte.entity.OneTimePassword;
import com.techbyte.entity.Order;
import com.techbyte.entity.RandomIdGenerator;
import com.techbyte.entity.User;
import com.techbyte.entity.UserStatus;
import com.techbyte.exception.OTPValidataionException;
import com.techbyte.exception.PDFGenerationException;
import com.techbyte.repository.OTPDAO;

@Component
public class GmailServiceProvider {
	public OTPDAO otpDao;

	@Autowired
	public GmailServiceProvider(OTPDAO otpDao) {
		this.otpDao = otpDao;
	}

	public User gmailValidator(User user) throws OTPValidataionException {
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

	public synchronized void OrderGmailSender(User user, Order order)
			throws OTPValidataionException, PDFGenerationException, DocumentException, IOException {
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
		while (generatePDFforOrder(user, order)) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                throw new PDFGenerationException(
						"Something wrong in internal functioning! Sorry for the inconvenience");
            }
        }
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			 
			message.setSubject("Order Confirmation");
			message.setText("Hi, " + user.getUserName());
			message.setDescription("Your order is placed. You can check the details on the attached PDF.");
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			String filename = ".\\OrderPDFFiles\\Details.pdf";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);

		} catch (MessagingException e) {
			throw new OTPValidataionException("Error while fetching details");
		}
	}

	public boolean generatePDFforOrder(User user, Order order)
			throws PDFGenerationException, DocumentException, IOException {
		FileOutputStream fos = new FileOutputStream(".\\OrderPDFFiles\\Details.pdf");
		Document doc = new Document();
		PdfWriter writer = PdfWriter.getInstance(doc, fos);

		// opens the PDF
		doc.open();
		// adding paragraphs to the PDF
		doc.add(new Paragraph("                         TechByte"));
		doc.add(new Paragraph("                                       "));
		doc.add(new Paragraph(" Hi, " + user.getUserName()));
		doc.add(new Paragraph("                                       "));
		doc.add(new Paragraph("                        Order Details"));
		doc.add(new Paragraph("                                       "));
		doc.add(new Paragraph(" Total Order Amount : " + order.getTotalOrderAmount()));
		doc.add(new Paragraph(" Order Date : " + order.getTime()));
		doc.add(new Paragraph("	Order Status : " + order.getOrderStatus()));
		doc.add(new Paragraph("                                       "));
		doc.add(new Paragraph(" Have a nice day                       "));
		doc.close();
		fos.close();
		return true;
	}
}
