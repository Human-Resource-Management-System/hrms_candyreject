package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import DAO_Interfaces.EmployeeDAO;
import models.Employee;
import models.EmployeeLeaveRequest;
import models.JobGradeWiseLeaves;
import models.LeaveValidationModel;
import models.input.output.EmployeeLeaveInputModel;
import service_interfaces.EmployeeLeaveServiceInterface;
import service_interfaces.MailServiceInterface;

public class EmployeeLeaveService implements EmployeeLeaveServiceInterface {

	private static Logger logger = LoggerFactory.getLogger(EmployeeLeaveService.class);
	private EmployeeDAO ed;
	private MailServiceInterface mailService;

	@Autowired
	public EmployeeLeaveService(EmployeeDAO ed, MailServiceInterface mailService) {
		this.ed = ed;
		this.mailService = mailService;
	}

	public EmployeeLeaveService() {
	}

	private LeaveValidationModel leaveValidation;

	public static long calculateLeavesTakenBetwwenDates(LocalDate startDate, LocalDate endDate) {
		// Calculate the number of days between the start and end dates, inclusive of both dates
		if (startDate == null || endDate == null)
			return 0;
		logger.info("calculating the leaves taken between dates");
		return ChronoUnit.DAYS.between(startDate, endDate) + 1;
	}

	@Override
	public LeaveValidationModel calculateLeavesTaken(List<EmployeeLeaveRequest> leaves,
			JobGradeWiseLeaves leavesProvidedStatistics) {

		logger.info("calculating leaves taken for an employee");

		if (leavesProvidedStatistics != null) {

			long totalNoOfLeaves = 0;
			long sickLeaves = 0;
			long casualLeaves = 0;
			long otherLeaves = 0;

			long pendingTotalNoOfLeaves = 0;
			long pendingSickLeaves = 0;
			long pendingCasualLeaves = 0;
			long pendingOtherLeaves = 0;

			leaveValidation = new LeaveValidationModel();

			for (EmployeeLeaveRequest leave : leaves) {

				if (leave.getApprovedLeaveStartDate() == null || leave.getApprovedLeaveEndDate() == null) {
					// Calculate leaves count for pending leave requests
					long leavesCount = EmployeeLeaveService.calculateLeavesTakenBetwwenDates(leave.getLeaveStartDate(),
							leave.getLeaveEndDate());
					pendingTotalNoOfLeaves += leavesCount;
					if (leave.getLeaveType().trim().equals("SICK")) {
						pendingSickLeaves += leavesCount;
					} else if (leave.getLeaveType().trim().equals("CASL")) {
						pendingCasualLeaves += leavesCount;
					} else if (leave.getLeaveType().trim().equals("OTHR")) {
						pendingOtherLeaves += leavesCount;
					}

				} else {
					// Calculate leaves count for approved leave requests
					long leavesCount = EmployeeLeaveService.calculateLeavesTakenBetwwenDates(
							leave.getApprovedLeaveStartDate(), leave.getApprovedLeaveEndDate());
					System.out.println(leavesCount);
					totalNoOfLeaves += leavesCount;
					if (leave.getLeaveType().trim().equals("SICK")) {
						sickLeaves += leavesCount;
					} else if (leave.getLeaveType().trim().equals("CASL")) {
						casualLeaves += leavesCount;
					} else if (leave.getLeaveType().trim().equals("OTHR")) {
						otherLeaves += leavesCount;
					}
				}
			}

			// Set the calculated leaves information in the leave validation model
			leaveValidation.setTakenCasualLeaves(casualLeaves);
			leaveValidation.setTakenOtherLeaves(otherLeaves);
			leaveValidation.setTakenSickLeaves(sickLeaves);
			leaveValidation.setTakenTotalLeaves(totalNoOfLeaves);

			leaveValidation.setPendingCasualLeaves(pendingCasualLeaves);
			leaveValidation.setPendingOtherLeaves(pendingOtherLeaves);
			leaveValidation.setPendingSickLeaves(pendingSickLeaves);
			leaveValidation.setPendingTotalNoOfLeaves(pendingTotalNoOfLeaves);

			leaveValidation.setAllowedCasualLeaves(leavesProvidedStatistics.getCasualLeavesPerYear());
			leaveValidation.setAllowedOtherLeaves(leavesProvidedStatistics.getOtherLeavesPerYear());
			leaveValidation.setAllowedSickLeaves(leavesProvidedStatistics.getSickLeavesPerYear());
			leaveValidation.setAllowedTotalLeaves(leavesProvidedStatistics.getTotalLeavesPerYear());

		}

		logger.info("successfully calculated the leaves taken.");

		return leaveValidation;
	}

	@Override
	public void sendEmail(HttpServletRequest request, HttpServletResponse respone, EmployeeLeaveInputModel leave)
			throws Exception {
		Employee employee = ed.getEmployeeById(leave.getEmployeeId());
		Employee manager = ed.getEmployeeById(employee.getEmplRmanagerEmplId());
		Employee hr = ed.getEmployeeById(employee.getEmplHrEmplId());

		try {
			mailService.sendLeaveRequestMail(request, respone, leave, manager.getEmplOffemail(), hr.getEmplOffemail());
		} catch (Exception e) {
			throw e;
		}
	}

}