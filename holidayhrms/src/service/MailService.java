package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.itextpdf.html2pdf.HtmlConverter;

import models.input.output.EmployeeLeaveInputModel;
import service_interfaces.MailServiceInterface;

public class MailService implements MailServiceInterface {

	private static String from = "sambangichandrasekhar340@gmail.com";
	private static String host = "smtp.gmail.com";
	private static String apppass = "edgg kndd kwgs vqcb";

	private Session preProcess() {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, apppass);
			}
		});
		return session;
	}

	private String generateHtml(String viewname, String attribute, Object object, HttpServletRequest request,
			HttpServletResponse response) {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		String viewName = viewname;
		request.setAttribute(attribute, object);
		// Render JSP
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp");
		StringWriter stringWriter = new StringWriter();
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
			@Override
			public PrintWriter getWriter() throws IOException {
				return new PrintWriter(stringWriter);
			}
		};
		try {
			requestDispatcher.include(request, responseWrapper);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String renderedHtml = stringWriter.toString();
		return renderedHtml;

	}

	@Override
	public boolean sendOtpMail(String to, String otp) {
		try {

			Session session = preProcess();
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			msg.setSubject("your one time password");
			msg.setText("here is the OTP : " + otp);
			System.out.println("Sending Message.........");
			Transport.send(msg);
			System.out.println("sent successfully.........");

			return true;

		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	@Override
	public void sendOfferEmail(HttpServletRequest request, HttpServletResponse response, models.OfferModel offerModel,
			String to) throws Exception {

		String renderedHtml = generateHtml("sendingofferemail", "offerModel", offerModel, request, response);
		OutputStream file = new FileOutputStream(new File("offerletter.pdf"));
		HtmlConverter.convertToPdf(renderedHtml, file);
		file.close();

		Session session = preProcess();

		try {
			MimeMessage mm = new MimeMessage(session);
			mm.setFrom(new InternetAddress(from));
			System.out.println(to);
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mm.setSubject("Offer Letter Details");
			// mm.setContent(renderedHtml, "text/html");
			mm.setContent("This is Your Offer ...........\n", "text/html");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Congratulations You are Selected");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String filename = "offerletter.pdf";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			mm.setContent(multipart);

			Transport.send(mm);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendLeaveRequestMail(HttpServletRequest request, HttpServletResponse response,
			EmployeeLeaveInputModel leave, String managerEmail, String hrEmail) throws Exception {

		String renderedHtml = generateHtml("employeeLeaveRequestMail", "leaverequest", leave, request, response);

		Session session = preProcess();

		try {
			MimeMessage mm = new MimeMessage(session);
			mm.setFrom(new InternetAddress(from));

			// Send email to manager and add HR as CC
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(managerEmail));
			mm.setSubject("Leave Request");
			mm.setRecipients(Message.RecipientType.CC, InternetAddress.parse(hrEmail));
			mm.setContent(renderedHtml, "text/html");
			Transport.send(mm);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendPaySlipMail(HttpServletRequest request, HttpServletResponse response,
			models.input.output.EmployeePayRollOutputModel payRollOutput, String email) throws Exception {

		String renderedHtml = generateHtml("payslip", "pay", payRollOutput, request, response);
		OutputStream file = new FileOutputStream(new File("Payslip.pdf"));

		HtmlConverter.convertToPdf(renderedHtml, file);
		file.close();

		Session session = preProcess();

		try {
			MimeMessage mm = new MimeMessage(session);
			mm.setFrom(new InternetAddress(from));
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mm.setSubject("Payslip Details");
			// mm.setContent(renderedHtml, "text/html");
			mm.setContent("This is payslip email...........\n", "text/html");
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText("This is your payslip.");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String filename = "Payslip.pdf";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			mm.setContent(multipart);

			Transport.send(mm);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send the email with the rendered HTML

	}

}