package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EmployeeLeaveRequests")
public class EmployeeLeaveRequest {

	@EmbeddedId
	private EmployeeLeaveRequestId leaveRequestId;

	@Column(name = "elrq_date")
	private LocalDateTime requestDateTime;

	@Column(name = "elrq_leavetype")
	private String leaveType;

	@Column(name = "elrq_reason")
	private String reason;

	@Column(name = "elrq_leavestdate")
	private LocalDate leaveStartDate;

	@Column(name = "elrq_leaveenddate")
	private LocalDate leaveEndDate;

	@Column(name = "elrq_approvedby")
	private int approvedBy;

	@Column(name = "elrq_approvedremarks")
	private String approvedRemarks;

	@Column(name = "elrq_aprvdleavestdate")
	private LocalDate approvedLeaveStartDate;

	@Column(name = "elrq_aprvdleaveenddate")
	private LocalDate approvedLeaveEndDate;

	// Constructors

	public EmployeeLeaveRequest() {
		// Default constructor
	}

	// Getters and Setters

	public EmployeeLeaveRequestId getLeaveRequestId() {
		return leaveRequestId;
	}

	public void setLeaveRequestId(EmployeeLeaveRequestId leaveRequestId) {
		this.leaveRequestId = leaveRequestId;
	}

	public LocalDateTime getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(LocalDateTime requestDateTime) {
		this.requestDateTime = requestDateTime;
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

	public LocalDate getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(LocalDate leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public LocalDate getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(LocalDate leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public int getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedRemarks() {
		return approvedRemarks;
	}

	public void setApprovedRemarks(String approvedRemarks) {
		this.approvedRemarks = approvedRemarks;
	}

	public LocalDate getApprovedLeaveStartDate() {
		return approvedLeaveStartDate;
	}

	public void setApprovedLeaveStartDate(LocalDate approvedLeaveStartDate) {
		this.approvedLeaveStartDate = approvedLeaveStartDate;
	}

	public LocalDate getApprovedLeaveEndDate() {
		return approvedLeaveEndDate;
	}

	public void setApprovedLeaveEndDate(LocalDate approvedLeaveEndDate) {
		this.approvedLeaveEndDate = approvedLeaveEndDate;
	}

	@Override
	public String toString() {
		return "EmployeeLeaveRequest [leaveRequestId=" + leaveRequestId + ", requestDateTime=" + requestDateTime
				+ ", leaveType=" + leaveType + ", reason=" + reason + ", leaveStartDate=" + leaveStartDate
				+ ", leaveEndDate=" + leaveEndDate + ", approvedBy=" + approvedBy + ", approvedRemarks="
				+ approvedRemarks + ", approvedLeaveStartDate=" + approvedLeaveStartDate + ", approvedLeaveEndDate="
				+ approvedLeaveEndDate + "]";
	}

}