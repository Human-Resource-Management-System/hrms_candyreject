package DAO_Interfaces;

import java.sql.Date;
import java.util.List;

import models.ApplyPermissions;
import models.Employee;

/**
 * 
 * The ApplyPermissionDao interface provides methods to interact with the database for permission-related operations.
 */
public interface ApplyPermissionDAO {

	/**
	 * 
	 * Persists the given permission request in the database.
	 * 
	 * @param apppermission The permission request to be persisted.
	 */
	void persist(ApplyPermissions apppermission);

	/**
	 * 
	 * Retrieves the next available permission index for a given employee.
	 * 
	 * @param employeeId The ID of the employee.
	 * @return The next available permission index.
	 */
	public int getNextPermissionIndex(int employeeId);

	/**
	 * 
	 * Retrieves the permission request by employee ID and index.
	 * 
	 * @param employeeId The ID of the employee.
	 * @param index      The index of the permission request.
	 * @return The permission request associated with the employee ID and index.
	 */
	public ApplyPermissions getPermissionByIdAndIndex(int employeeId, int index);

	/**
	 * 
	 * Retrieves the list of employees who have HR and Manager roles.
	 * 
	 * @param employeeId The ID of the employee.
	 * @return The list of employees who have HR and Manager roles.
	 */
	public List<Employee> getEmployeesByHRAndManager(int employeeId);

	/**
	 * 
	 * Retrieves the count of employee and permission request data for a given employee ID, month, and year.
	 * 
	 * @param id    The ID of the employee.
	 * @param month The month.
	 * @param year  The year.
	 * @return The count of employee and permission request data for the specified criteria.
	 */
	public long getEmployeeAndPermissionRequestDataCountPerMonth(int id, int month, int year);

	/**
	 * 
	 * Retrieves the count of employee and permission request data for a given employee ID and current date.
	 * 
	 * @param id      The ID of the employee.
	 * @param current The current date.
	 * @return The count of employee and permission request data for the specified criteria.
	 */
	public Long getEmployeeAndPermissionRequestDataCountPerDay(int id, Date current);

	/**
	 * 
	 * Retrieves the employee and permission request data for a given employee ID and current date.
	 * 
	 * @param id      The ID of the employee.
	 * @param current The current date.
	 * @return The employee and permission request data associated with the employee ID and current date.
	 */
	public ApplyPermissions getEmployeeAndPermissionRequestData(int id, Date current);

	/**
	 * 
	 * Retrieves the count of approved permissions for a given employee ID and year.
	 * 
	 * @param id   The ID of the employee.
	 * @param year The year.
	 * @return The count of approved permissions for the specified criteria.
	 */
	public long getEmployeeApprovedPermissionsCount(int id, int year);

	public List<ApplyPermissions> appliedPermissions(int id);
}