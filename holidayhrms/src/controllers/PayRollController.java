package controllers;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import DAO_Interfaces.EmployeeDAO;
import DAO_Interfaces.PayRollDAO;
import models.Employee;
import models.EmployeePayslip;
import models.input.output.EmployeePayRollInputModel;
import models.input.output.EmployeePayRollOutputModel;
import service.PaySlipMail;
import service_interfaces.PayRollService;

@Controller
public class PayRollController {

	private PayRollService payRollservice;
	private EmployeeDAO ed;
	private EmployeePayRollOutputModel payRollOutput;
	private EmployeePayRollInputModel payRollInput;
	private EmployeePayslip empPaySlip;
	private PayRollDAO payrollDAO;
	private static final Logger logger = LoggerFactory.getLogger(PayRollController.class);

	@Autowired
	PayRollController(PayRollService payRollservice, EmployeeDAO ed, EmployeePayRollOutputModel payRollOutput,
			EmployeePayRollInputModel payRollInput, EmployeePayslip empPaySlip, PayRollDAO payrollDAO) {
		this.payRollservice = payRollservice;
		this.ed = ed;
		this.payRollOutput = payRollOutput;
		this.payRollInput = payRollInput;
		this.empPaySlip = empPaySlip;
		this.payrollDAO = payrollDAO;
	}

	// get admin side pay roll form to select employee id and month
	@RequestMapping(value = "/getemppayroll", method = RequestMethod.GET)
	public String getPayRoll(Model model) {
		logger.info("Entered into payroll at admin side");
		return "payrollemp";

	}

	// Display the generated payslip at admin side for that particular employee in that month
	@RequestMapping(value = "/getpayslip", method = RequestMethod.POST)
	public String getAdminSidePaySlip(@RequestParam("empl_id") int id, @RequestParam("month") int month, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		System.out.println(month);
		logger.info("Generating payslip for an employee based on month");
		int Adminid = (int) session.getAttribute("adminId");

		Employee employee = ed.getEmployeeById(id);
		String firstName = employee.getEmplFirstname();
		String lastName = employee.getEmplLastname();
		String designation = employee.getEmplDesignation();
		double ctc = employee.getEmpl_ctc();
		double basicsal = employee.getEmpl_basicsal();
		double fixedsal = employee.getEmpl_fixedsal();
		double variablesal = employee.getEmpl_variablesal();
		String email = employee.getEmplOffemail();

		// for setting values into input model

		int year = LocalDate.now().getYear(); // to get current year
		logger.info("Setting values into input model");
		payRollInput.setId(id);
		payRollInput.setmonthYear(month + "-" + year);
		payRollInput.setBasicPay(payRollservice.calculateBasicPay(basicsal / 12));
		payRollInput.setFixedPay(fixedsal / 12);
		payRollInput.setVariablePay(variablesal / 12);
		payRollInput.setGratuity(payRollservice.calculateGratuity((ctc * 0.1) - 200));
		payRollInput.setPf(payRollservice.calculatePF((ctc * 0.1) - 200));
		payRollInput.setHra(payRollservice.calculateHRA(fixedsal / 12));
		payRollInput.setDa(payRollservice.calculateDA(fixedsal / 12));
		payRollInput.setTa(payRollservice.calculateTA(fixedsal / 12));
		payRollInput.setAdditions(payRollservice.calculateAdditions(variablesal / 12));
		payRollInput.setDeductions(payRollservice.calculateDeductions());
		payRollInput.setPtax(200);
		payRollInput.setEsi(payRollservice.calculateESI((ctc * 0.1) - 200));
		payRollInput.setTds(payRollservice.calculateTax(ctc));
		logger.info("fetching data from input model");

		// for setting into entity model
		empPaySlip.setEmployeeId(payRollInput.getId());
		empPaySlip.setMonthYear(payRollInput.getmonthYear());
		empPaySlip.setBasicSalary(payRollInput.getBasicPay());
		empPaySlip.setDa(payRollInput.getDa());
		empPaySlip.setAdditions(payRollInput.getAdditions());
		empPaySlip.setDeductions(payRollInput.getDeductions());
		empPaySlip.setEsi(payRollInput.getEsi());
		empPaySlip.setGratuity(payRollInput.getGratuity());
		empPaySlip.setHra(payRollInput.getHra());
		empPaySlip.setPf(payRollInput.getPf());
		empPaySlip.setPtax(payRollInput.getPtax());
		empPaySlip.setTa(payRollInput.getTa());
		empPaySlip.setTds(payRollInput.getTds());
		empPaySlip.setLastUpdatedUser(Adminid);
		empPaySlip.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));

		payrollDAO.insertEmployeePayslip(empPaySlip);
		logger.info("Payslip data inserted successfully!!");

		// for setting into output model
		payRollOutput.setId(id);
		payRollOutput.setMonthYear(empPaySlip.getMonthYear());
		payRollOutput.setFirstName(firstName);
		payRollOutput.setLastName(lastName);
		payRollOutput.setDesignation(designation);
		payRollOutput.setBasicPay(empPaySlip.getBasicSalary());
		payRollOutput.setDa(empPaySlip.getDa());
		payRollOutput.setAdditions(empPaySlip.getAdditions());
		payRollOutput.setDeduction(empPaySlip.getDeductions());
		payRollOutput.setEsi(empPaySlip.getEsi());
		payRollOutput.setGratuity(empPaySlip.getGratuity());
		payRollOutput.setHra(empPaySlip.getHra());
		payRollOutput.setPf(empPaySlip.getPf());
		payRollOutput.setPtax(empPaySlip.getPtax());
		payRollOutput.setTa(empPaySlip.getTa());
		payRollOutput.setTds(empPaySlip.getTds());

		payRollOutput.setTotal(payRollservice.calculateTotalPay());
		payRollOutput.setNetpay(payRollservice.calculateNetPay());

		logger.info("Payslip generated successfully!!");

		model.addAttribute("pay", payRollOutput);

		try {
			PaySlipMail.sendEmail(request, response, payRollOutput, email);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "payslip";
	}

	// employe sie pay slip form
	@RequestMapping(value = "/getemppayslip", method = RequestMethod.GET)
	public String getMonthWisePaySlip(Model model) {
		logger.info("get employee pay slip");

		return "payslipEmpSide";
	}

	// Display payslip for an employee based on the selected month
	@RequestMapping(value = "/EmployeeSidePaySlip", method = RequestMethod.POST)
	public String getEmployeePayslip(@RequestParam("month") int month, Model model, HttpSession session) {

		int id = (int) session.getAttribute("employeeId");
		Employee employee = ed.getEmployeeById(id);
		int year = LocalDate.now().getYear();

		try {
			logger.info("get the pay slip based on the month");

			EmployeePayslip eps = payrollDAO.getEmployeePayslipsByEmployeeIdAndMonthYear(id, month + "-" + year);

			String firstName = employee.getEmplFirstname();
			String lastName = employee.getEmplLastname();
			String designation = employee.getEmplDesignation();
			payRollOutput.setId(id);
			payRollOutput.setMonthYear(eps.getMonthYear());
			payRollOutput.setFirstName(firstName);
			payRollOutput.setLastName(lastName);
			payRollOutput.setDesignation(designation);
			payRollOutput.setBasicPay(eps.getBasicSalary());
			payRollOutput.setDa(eps.getDa());
			payRollOutput.setAdditions(eps.getAdditions());
			payRollOutput.setDeduction(eps.getDeductions());
			payRollOutput.setEsi(eps.getEsi());
			payRollOutput.setGratuity(eps.getGratuity());
			payRollOutput.setHra(eps.getHra());
			payRollOutput.setPf(eps.getPf());
			payRollOutput.setPtax(eps.getPtax());
			payRollOutput.setTa(eps.getTa());
			payRollOutput.setTds(eps.getTds());

			payRollOutput
					.setTotal(eps.getBasicSalary() + eps.getHra() + eps.getDa() + eps.getTa() + eps.getAdditions());
			payRollOutput
					.setNetpay((eps.getBasicSalary() + eps.getHra() + eps.getDa() + eps.getTa() + eps.getAdditions())
							- eps.getDeductions());

			logger.info("Employee Payslip retrieved successfully!!");

			model.addAttribute("output", payRollOutput);
		} catch (Exception e) {
			logger.info("if user selects the month for which pay slip is not generated");

			String errorMessage = "Please select a valid month."; // incase employee selects the month for which pay
																	// slip haven't been generated
			model.addAttribute("errorMessage", errorMessage);
			return "errorPageforpayslip";
		}

		return "EmployeeSidePaySlip";

	}

}
