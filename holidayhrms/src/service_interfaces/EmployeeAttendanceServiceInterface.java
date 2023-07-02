package service_interfaces;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.multipart.MultipartFile;

import models.AttendanceEvent;
import models.EmployeeAttendance;
import models.EmployeeRequestResult;

public interface EmployeeAttendanceServiceInterface {

	/**
	 * Inserts the employee attendance data into the system.
	 * 
	 * @param attendance The employee attendance to be inserted.
	 */
	void insertEmployeeAttendance(EmployeeAttendance attendance);

	/**
	 * Retrieves the punch data for yesterday for the given employee ID.
	 * 
	 * @param employeeId The ID of the employee.
	 * @return A list of attendance events for yesterday.
	 */
	List<AttendanceEvent> getYesterdayPunchData(int employeeId);

	/**
	 * Calculates the attendance result based on the provided punch data.
	 * 
	 * @param punchData The punch data.
	 * @return The attendance result.
	 */
	EmployeeRequestResult calculateAttendance(List<Object[]> punchData);

	/**
	 * Converts the given cell value to a LocalDateTime object.
	 * 
	 * @param cell The cell value.
	 * @return The LocalDateTime object.
	 */
	LocalDateTime convertToDateTime(Cell cell);

	/**
	 * Retrieves a list of years based on the employee's join date.
	 * 
	 * @param joinDate The employee's join date.
	 * @return A list of years.
	 */
	List<Integer> getYears(Date joinDate);

	/**
	 * Retrieves the average punch in and punch out times for the given employee ID.
	 * 
	 * @param id The ID of the employee.
	 * @return A list containing the average punch in and punch out times.
	 */
	List<Long> getAvgPunchInAndOut(int id);

	/**
	 * 
	 * Processes an Excel file.
	 * 
	 * @param file the Excel file to be processed
	 * @throws IOException if an I/O error occurs while processing the file
	 */
	void processExcelFile(MultipartFile file) throws IOException;

}
