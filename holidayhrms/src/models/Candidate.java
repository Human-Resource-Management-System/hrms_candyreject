package models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "hrms_candidates")
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cand_id")
	private Integer candId;

	@NotBlank(message = "First name is required")
	@Column(name = "cand_firstname")
	private String candFirstName;

	@NotBlank(message = "Middle name is required")
	@Column(name = "cand_middlename")
	private String candMiddleName;

	@NotBlank(message = "Last name is required")
	@Column(name = "cand_lastname")
	private String candLastName;

	@NotNull(message = "Registration date is required")
	@Column(name = "cand_rdate")
	private Date candRDate;

	@NotBlank(message = "Gender is required")
	@Column(name = "cand_gender")
	private String candGender;

	@NotNull(message = "Date of birth is required")
	@Column(name = "cand_dob")
	private Date candDOB;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Pattern(regexp = ".+\\.(com|org|in)$", message = "Invalid email format. Must have a .com, .org, or .in domain")
	@Column(name = "cand_email")
	private String candEmail;

	@NotNull(message = "Mobile number is required")
	@Column(name = "cand_mobile")
	private Long candMobile;

	@NotBlank(message = "Address is required")
	@Column(name = "cand_address")
	private String candAddress;

	@NotNull(message = "Last updated date is required")
	@Column(name = "cand_ludate")
	private Date candLUDate;

	@NotBlank(message = "Status is required")
	@Column(name = "cand_status")
	private String candStatus;

	// Getters and setters

	@Override
	public String toString() {
		return "Candidate [candId=" + candId + ", candFirstName=" + candFirstName + ", candMiddleName=" + candMiddleName
				+ ", candLastName=" + candLastName + ", candRDate=" + candRDate + ", candGender=" + candGender
				+ ", candDOB=" + candDOB + ", candEmail=" + candEmail + ", candMobile=" + candMobile + ", candAddress="
				+ candAddress + ", candLUDate=" + candLUDate + ", candStatus=" + candStatus + "]";
	}

	public Integer getCandId() {
		return candId;
	}

	public void setCandId(Integer candId) {
		this.candId = candId;
	}

	public String getCandFirstName() {
		return candFirstName;
	}

	public void setCandFirstName(String candFirstName) {
		this.candFirstName = candFirstName;
	}

	public String getCandMiddleName() {
		return candMiddleName;
	}

	public void setCandMiddleName(String candMiddleName) {
		this.candMiddleName = candMiddleName;
	}

	public String getCandLastName() {
		return candLastName;
	}

	public void setCandLastName(String candLastName) {
		this.candLastName = candLastName;
	}

	public Date getCandRDate() {
		return candRDate;
	}

	public void setCandRDate(Date candRDate) {
		this.candRDate = candRDate;
	}

	public String getCandGender() {
		return candGender;
	}

	public void setCandGender(String candGender) {
		this.candGender = candGender;
	}

	public Date getCandDOB() {
		return candDOB;
	}

	public void setCandDOB(Date candDOB) {
		this.candDOB = candDOB;
	}

	public String getCandEmail() {
		return candEmail;
	}

	public void setCandEmail(String candEmail) {
		this.candEmail = candEmail;
	}

	public Long getCandMobile() {
		return candMobile;
	}

	public void setCandMobile(Long candMobile) {
		this.candMobile = candMobile;
	}

	public String getCandAddress() {
		return candAddress;
	}

	public void setCandAddress(String candAddress) {
		this.candAddress = candAddress;
	}

	public Date getCandLUDate() {
		return candLUDate;
	}

	public void setCandLUDate(Date candLUDate) {
		this.candLUDate = candLUDate;
	}

	public String getCandStatus() {
		return candStatus;
	}

	public void setCandStatus(String candStatus) {
		this.candStatus = candStatus;
	}
}
