package controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import DAO_Interfaces.EmployeeDAO;
import DAO_Interfaces.EmployeeOptedLeavesDAO;
import DAO_Interfaces.HolidayDAO;
import DAO_Interfaces.JobGradeHolidaysDAO;
import exceptions.EmployeeNotFoundException;
import models.Employee;
import models.EmployeeOptedLeaves;
import models.EmployeeOptedLeavesId;
import models.GradeHoliday;
import models.Holiday;
import models.HrmsJobGrade;
import models.JobGradeHolidays;
import models.JobGradeModel;

@Controller
public class HolidayController {
	private final EmployeeDAO emp;
	private final HolidayDAO hd;
	private final JobGradeHolidaysDAO jobGradeHolidaysDAO;
	private EmployeeOptedLeavesDAO employeeOptedLeavesDAO;
	private HrmsJobGrade jobGrade;
	private final Logger logger = LoggerFactory.getLogger(HolidayController.class);
	@Autowired
	private ApplicationContext context;

	@Autowired
	public HolidayController(HolidayDAO holidayDAO, EmployeeDAO ed, JobGradeHolidaysDAO jobGradeHolidaysDAO,
			EmployeeOptedLeavesDAO employeeOptedLeavesDAO, HrmsJobGrade jobGrade) {
		this.hd = holidayDAO;
		this.emp = ed;
		this.jobGradeHolidaysDAO = jobGradeHolidaysDAO;
		this.employeeOptedLeavesDAO = employeeOptedLeavesDAO;
		this.jobGrade = jobGrade;
	}

	// to get list of holidays
	@RequestMapping("/holidaysupd")
	public String showHolidays(Model model) {
		logger.info("Request received for holidays");
		List<Holiday> holidays = hd.findAllHolidays();
		model.addAttribute("holidays", holidays);
		return "holidays";
	}

	@RequestMapping(value = "/submitOptionalHolidays", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> submitSelectedHolidays(
			@RequestParam("selectedHolidays") List<String> selectedHolidays, @RequestParam("emplId") int emplId) {
		logger.info("Request received for submitting optional holidays");
		// Process the selected holidays and save to the database
		System.out.println("hello this is emplId " + emplId);
		List<String> years = new ArrayList<>();
		List<String> dates = new ArrayList<>();

		for (String holiday : selectedHolidays) {
			String[] parts = holiday.split("\\|");
			// years.add(parts[0]);
			// dates.add(parts[1]);

			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

			try {
				LocalDate localDate = LocalDate.parse(parts[1].trim(), inputFormatter);
				Date date = Date.valueOf(localDate);
				System.out.println(date);

				EmployeeOptedLeavesId employeeoptedleavesId = context.getBean(EmployeeOptedLeavesId.class);

				employeeoptedleavesId.setEmployeeId(emplId);
				employeeoptedleavesId.setHolidayDate(date);

				EmployeeOptedLeaves employeeoptedleaves = context.getBean(EmployeeOptedLeaves.class);

				employeeoptedleaves.setOptedleavesId(employeeoptedleavesId);
				employeeoptedleaves.setYear_id(Integer.parseInt(parts[0].trim()));

				employeeOptedLeavesDAO.saveEmployeeOptedLeaves(employeeoptedleaves);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ResponseEntity.ok("Successfully Applied");
	}

	@RequestMapping(value = "/optionalHoliday", method = RequestMethod.GET)
	public String displayEmployeeInformation(Model model, HttpSession session) {
		int emplId = (int) session.getAttribute("employeeId");
		logger.info("Request received for selecting optional holidays");
		Employee employee = emp.getEmployeeById(emplId);
		int currentYear = LocalDate.now().getYear();

		System.out.println("working");
		long count = hd.getEmployeeoptionalholidaysCount(emplId, currentYear);
		System.out.println("the count is:" + count);
		if (employee != null) {
			JobGradeHolidays jobGradeHolidays = jobGradeHolidaysDAO
					.getJobGradeHolidaysByGrade(employee.getEmplJbgrId());
			List<Holiday> holidays = hd.findAlloptedHolidays();
			int mandholidays = hd.countMandHolidays();

			// Calculate remaining holidays

			model.addAttribute("employee", employee);
			model.addAttribute("jobGradeHolidays", jobGradeHolidays);

			model.addAttribute("holidays", holidays);
			model.addAttribute("mandholidays", mandholidays);

			model.addAttribute("count", count);

			return "employee-information";
		} else {
			// Handle case when employee is not found
			throw new EmployeeNotFoundException("Employee not found with ID: " + emplId);
		}
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public String handleEmployeeNotFoundException(EmployeeNotFoundException ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "error-page";
	}

	// to get list of grade wise holidays
	@RequestMapping("/getgradewiseholidays")
	public String getgradewiseHolidays(Model model) {
		logger.info("Request received for displaying grade wise holidays");
		List<GradeHoliday> gradeholidays = hd.findAllGradeHolidays();
		List<HrmsJobGrade> existingJobGrades = hd.getAllJobGradesInfo();
		model.addAttribute("jobgradeinfo", existingJobGrades);
		model.addAttribute("gradeholidays", gradeholidays);
		return "gradeholidays";
	}

	@RequestMapping(value = "/getJobGradeList", method = RequestMethod.GET)
	public String getJobGradesList(Model model) {
		logger.info("Request received to  display job grade list");

		List<HrmsJobGrade> info = hd.getAllJobGradesInfo();
		model.addAttribute("gradeInfo", info);
		return "JobGrades";
	}

	@RequestMapping(value = "/addGrades", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> addGrades(@ModelAttribute JobGradeModel jobgrade) {
		try {

			logger.info("Request received for adding new grade in job grade");
			jobGrade.setId(jobgrade.getJbgrId());
			jobGrade.setName(jobgrade.getJbgrName());
			jobGrade.setDescription(jobgrade.getJbgrDescription());
			hd.saveJobGrade(jobGrade);

			return ResponseEntity.ok("success");
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

	@RequestMapping(value = "/addjobgradeholidays", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> addJobGradeHolidays(@ModelAttribute GradeHoliday gradeHoliday) {
		logger.info("Request received for adding holidays for new job grade");
		hd.saveJobGradeHoliday(gradeHoliday);
		return ResponseEntity.ok("successfully added");
	}

	@RequestMapping(value = "/editjobgradeholidays", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<String> updateJobGradeHolidays(@ModelAttribute GradeHoliday gradeHoliday) {
		logger.info("Request received for changing job grade holidays");

		hd.updateJobGradeHoliday(gradeHoliday);
		return ResponseEntity.ok("successfully updated");
	}

}
