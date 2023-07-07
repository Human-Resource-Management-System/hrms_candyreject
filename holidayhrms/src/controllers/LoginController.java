package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DAO.ForgotPasswordDAOImpl;
import models.Employee;
import models.EntityForgotPassword;
import models.input.output.MailOtpModel;
import service.EmployeeLoginService;
import service.MailService;

@Controller
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private MailService mailService;
	private ForgotPasswordDAOImpl forgotPassword;

	private EntityForgotPassword entityforgot;

	EmployeeLoginService empservice;

	@Autowired
	public LoginController(EmployeeLoginService empservice, Employee empauto, MailService mailService,
			ForgotPasswordDAOImpl forgotPassword, EntityForgotPassword entityforgot) {
		this.empservice = empservice;
		this.mailService = mailService;
		this.forgotPassword = forgotPassword;
		this.entityforgot = entityforgot;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String employeeLogin(Model model) {

		logger.info("Login Page Requested");
		logger.warn("testing...");
		logger.error("heloo");
		return "login";
	}

	@PostMapping("/employee")
	public ResponseEntity<String> authenticateUser(@RequestParam("empl_email") String email,
			@RequestParam("empl_password") String password, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();

		if (empservice.authenticateUser(email, password)) {
			Employee empdetails = empservice.getByEmail(email);
			int employeeId = empdetails.getEmplId();
			HttpSession session = request.getSession(true);
			session.setAttribute("employeeId", employeeId);

			response.put("success", true);
			response.put("message", "User authenticated successfully");
			System.out.println("User authenticated successfully");
		} else {
			response.put("success", false);
			response.put("message", "Invalid email or password");
		}

		System.out.println("res " + response);
		// Convert map to JSON using Gson
		Gson gson = new GsonBuilder().create();
		String jsonResponse = gson.toJson(response);
		System.out.println("jsonResponse " + jsonResponse);
		if (response.get("success").equals(true)) {
			return ResponseEntity.ok(jsonResponse);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse);
		}
	}

	@RequestMapping(value = "index2", method = RequestMethod.GET)
	public String getIndex2Page() {

		System.out.println("this is emportant for index2");
		return "index2";
	}

	@PostMapping("/admin")
	public ResponseEntity<String> enterIntoMenu_admin(@RequestParam("admin_email") String email,
			@RequestParam("admin_password") String password, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();

		if (empservice.authenticateUser_admin(email, password)) {
			// Set admin ID in session
			Employee empdetails = empservice.getByEmail(email);
			int adminId = empdetails.getEmplId();
			HttpSession session = request.getSession(true);
			session.setAttribute("adminId", adminId);

			response.put("success", true);
			response.put("message", "User authenticated successfully");
			System.out.println("User authenticated successfully");
		} else {
			response.put("success", false);
			response.put("message", "Invalid email or password");
		}

		System.out.println("res " + response);
		// Convert map to JSON using Gson
		Gson gson = new GsonBuilder().create();
		String jsonResponse = gson.toJson(response);
		System.out.println("jsonResponse " + jsonResponse);
		if (response.get("success").equals(true)) {
			return ResponseEntity.ok(jsonResponse);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse);
		}
	}

	@GetMapping("Index_admin")
	public String getIndex2Page1() {
		System.out.println("this is important for index2");
		return "Index_admin";
	}

	/*
	 * @RequestMapping(value = "/admin", method = RequestMethod.POST) public String
	 * enterIntoMenu_admin(@RequestParam("admin_email") String email,
	 * 
	 * @RequestParam("admin_password") String password, HttpServletRequest request) {
	 * System.out.println("thi9s isv admin side ");
	 * 
	 * HttpSession session = request.getSession(true); if (empservice.authenticateUser_admin(email, password)) {
	 * 
	 * // Set employee ID in session Employee empdetails = empservice.getByEmail(email); int adminId =
	 * empdetails.getEmplId(); System.out.println(adminId);
	 * 
	 * session.setAttribute("adminId", adminId);
	 * 
	 * return "Index_admin"; // Redirect to the dashboard page } else { return "login"; } }
	 */

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		System.out.println("logout");

		return ResponseEntity.ok("success");
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String myControllerMethod() {
		// Controller logic
		logger.info("Forgot Password Page Requested");

		return "forgot";
	}

	@RequestMapping(value = "/sendOtpmail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> handleEmailAjaxRequest(Model model, MailOtpModel mail) {
		System.out.println("entered into handle email ajax methiod ");
		logger.info("Send OTP Mail Requested");
		logger.debug("Email: {}", mail.getEmail().trim());
		System.out.println(mail.getEmail().trim());
		// System.out.println(mail);

		String email = mail.getEmail().trim();

		System.out.println(email);
		// Check whether the email exists
		boolean emailExists = forgotPassword.checkEmailExists(email);

		if (!emailExists) { // Email does not exist, return an error response return
			logger.warn("Email does not exist");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email does not exist.");
		}

		// Email exists, continue with generating OTP and sending the email
		int otp = (int) (Math.random() * 9000) + 1000;
		mail.setOtp(String.valueOf(otp));
		System.out.println(otp);
		System.out.println(mail + "this isn mail");
		entityforgot.setMail(mail.getEmail().trim());
		entityforgot.setOtp(mail.getOtp());

		// Update or create the OTP entity
		empservice.saveOrUpdate(entityforgot);

		boolean flag = mailService.sendOtpMail(email, String.valueOf(otp));

		if (flag) {
			logger.info("OTP Email sent successfully");

			return ResponseEntity.ok("Email Successfully sent!");
		}
		logger.error("Error sending OTP email");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");

	}

	@RequestMapping(value = "/otpvalidate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public ResponseEntity<String> handleOTPAjaxRequest(Model mod, MailOtpModel mail) {
		logger.info("OTP Validation Requested");
		logger.debug("OTP: {}", mail.getOtp());
		System.out.println(mail.getOtp() + "at otp coming from form ");
		String email = mail.getEmail().trim();

		String flagotpvalid = forgotPassword.validateOtp(email);
		System.out.println(flagotpvalid + "otp coming from data base ");
		if (flagotpvalid.equals(mail.getOtp())) {
			logger.info("OTP verification successful");

			return ResponseEntity.ok("otp verification ");
		}
		logger.warn("Invalid OTP");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("otp is not correct.");
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public ResponseEntity<String> changePasswordAjaxRequest(Model mod, Employee emp) {
		System.out.println(emp.getPassword() + "came in controller ");
		System.out.println(emp.getEmplOffemail());
		System.out.println(empservice.hashPassword(emp.getPassword()));

		logger.info("Change Password Requested");
		logger.debug("New Password: {}", emp.getPassword());
		logger.debug("Employee Office Email: {}", emp.getEmplOffemail());

		emp.setPassword(empservice.hashPassword(emp.getPassword()));
		System.out.println("In change password haha ");
		forgotPassword.updatePassword(emp);
		logger.info("Password changed successfully");

		return ResponseEntity.ok("Password change");

	}

}
