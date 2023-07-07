package models.input.output;

public class EmployeeLeaveInputModel {
	private int employeeId;
	private String name;
	private int leaveRequestIndex;
	private String leaveStartDate;
	private String leaveEndDate;
	private String leaveType;
	private String reason;
	private String remarks;

	
	public EmployeeLeaveInputModel(int employeeId, String name, int leaveRequestIndex, String leaveStartDate,
			String leaveEndDate, String leaveType, String reason, String remarks) {
		this.employeeId = employeeId;
		this.name = name;
		this.leaveRequestIndex = leaveRequestIndex;
		this.leaveStartDate = leaveStartDate;
		this.leaveEndDate = leaveEndDate;
		this.leaveType = leaveType;
		this.reason = reason;
		this.remarks = remarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeaveRequestIndex() {
		return leaveRequestIndex;
	}

	public void setLeaveRequestIndex(int leaveRequestIndex) {
		this.leaveRequestIndex = leaveRequestIndex;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public EmployeeLeaveInputModel() {}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public String getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(String leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
