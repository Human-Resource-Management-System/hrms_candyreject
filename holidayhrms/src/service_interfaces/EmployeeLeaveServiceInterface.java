package service_interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.EmployeeLeaveRequest;
import models.JobGradeWiseLeaves;
import models.LeaveValidationModel;
import models.input.output.EmployeeLeaveInputModel;

public interface EmployeeLeaveServiceInterface {

	/**
	 * Calculates the leaves taken by an employee based on the leave request data and leave statistics.
	 * 
	 * @param leaves                   The list of employee leave requests.
	 * @param leavesProvidedStatistics The job grade-wise leave statistics.
	 * @return The leave validation model containing the calculated leaves taken.
	 */
	LeaveValidationModel calculateLeavesTaken(List<EmployeeLeaveRequest> leaves,
			JobGradeWiseLeaves leavesProvidedStatistics);

	void sendEmail(HttpServletRequest request, HttpServletResponse respone, EmployeeLeaveInputModel leave)
			throws Exception;
}