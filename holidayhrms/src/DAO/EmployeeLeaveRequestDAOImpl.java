package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import DAO_Interfaces.EmployeeLeaveRequestDAO;
import models.EmployeeLeaveRequest;
import models.EmployeeLeaveRequestId;
import models.JobGradeWiseLeaves;
import models.input.output.JobGradeLeavesOutModel;

@Repository
public class EmployeeLeaveRequestDAOImpl implements EmployeeLeaveRequestDAO {

	private static Logger logger = LoggerFactory.getLogger(EmployeeLeaveRequestDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	// Saves the employee leave request to the database.
	@Override
	public void saveEmployeeLeaveRequest(EmployeeLeaveRequest leaveRequest) {
		entityManager.persist(leaveRequest);
		logger.info("Employee leave request saved successfully.");
	}

	// Retrieves the next leave request index for the given employee ID.
	@Override
	public int getNextLeaveRequestIndex(int employeeId){
		logger.info("Getting the next index for the leave request");
		String queryString = "SELECT COALESCE(MAX(lr.leaveRequestId.leaveRequestIndex), 0) + 1 "
				+ "FROM EmployeeLeaveRequest lr " + "WHERE lr.leaveRequestId.employeeId = :employeeId";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("employeeId", employeeId);
		return (Integer) query.getSingleResult();
	}

	// Retrieves the employee and leave request data for the given employee ID.
	@Override
	public List<EmployeeLeaveRequest> getEmployeeAndLeaveRequestData(int id) {
		logger.info("Getting the employee leaves which are not approved till now.");
		String jpqlQuery = "SELECT elrq FROM EmployeeLeaveRequest elrq "
				+ "WHERE elrq.leaveRequestId.employeeId = :employeeIds " + "AND elrq.approvedBy = 0";
		TypedQuery<EmployeeLeaveRequest> query = entityManager.createQuery(jpqlQuery, EmployeeLeaveRequest.class);
		query.setParameter("employeeIds", id);
		List<EmployeeLeaveRequest> result = query.getResultList();
		return result;
	}

	// Retrieves the approved and pending employee leave requests for the given employee ID and year.
	@Override
	public List<EmployeeLeaveRequest> getApprovedAndPendingEmployeeAndLeaveRequestData(int id, int year) {
		logger.info("Getting the employee leaves which are approved and pending.");
		String jpqlQuery = "SELECT elrq FROM EmployeeLeaveRequest elrq"
				+ " WHERE elrq.leaveRequestId.employeeId = :employeeIds" + " AND elrq.approvedBy != -1"
				+ " AND EXTRACT(YEAR FROM elrq.requestDateTime) = :year";
		TypedQuery<EmployeeLeaveRequest> query = entityManager.createQuery(jpqlQuery, EmployeeLeaveRequest.class);
		query.setParameter("employeeIds", id);
		query.setParameter("year", year);
		List<EmployeeLeaveRequest> result = query.getResultList();
		return result;
	}

	// Retrieves the approved employee leave requests for the given employee ID.
	@Override
	public List<EmployeeLeaveRequest> getApprovedEmployeeAndLeaveRequestData(int id) {
		logger.info("Getting Leave Requests which are approved by an employee.");
		String jpqlQuery = "SELECT elrq FROM EmployeeLeaveRequest elrq " + "WHERE "
				+ " elrq.approvedBy = :employeeIds ";
		TypedQuery<EmployeeLeaveRequest> query = entityManager.createQuery(jpqlQuery, EmployeeLeaveRequest.class);
		query.setParameter("employeeIds", id);
		List<EmployeeLeaveRequest> result = query.getResultList();
		return result;
	}

	// Retrieves the employee leave request for the given leave request ID.
	@Override
	public EmployeeLeaveRequest getEmployeeLeaveRequest(EmployeeLeaveRequestId key) {
		logger.info("getting the employee leave request for an id");
		return entityManager.find(EmployeeLeaveRequest.class, key);
	}

	// Retrieves the job grade wise leaves provided statistics for the given job grade ID.
	@Override
	public JobGradeWiseLeaves getJobGradeWiseLeaves(String jobGradeId) {
		logger.info("getting the job grade wise leaves for an employee");
		return entityManager.find(JobGradeWiseLeaves.class, jobGradeId);
	}

	// Retrieves the leave request history for the given employee ID.
	@Override
	public List<EmployeeLeaveRequest> getLeaveRequestHistory(int id) {
		logger.info("getting the leave Requests History.");
		String jpqlQuery = "SELECT elrq FROM EmployeeLeaveRequest elrq " + "WHERE "
				+ " elrq.leaveRequestId.employeeId = :employeeIds ";
		TypedQuery<EmployeeLeaveRequest> query = entityManager.createQuery(jpqlQuery, EmployeeLeaveRequest.class);
		query.setParameter("employeeIds", id);
		List<EmployeeLeaveRequest> result = query.getResultList();
		logger.info("successfully got the leave requests history");
		return result;
	}

	// Retrieves the job grade wise leaves provided statistics for all job grades.
	@Override
	public List<JobGradeWiseLeaves> getJobGradeWiseLeaves() {
		
		logger.info("getting the job grade wise leaves.");
		String jpqlQuery = "SELECT jgwl FROM JobGradeWiseLeaves jgwl ";
		TypedQuery<JobGradeWiseLeaves> query = entityManager.createQuery(jpqlQuery, JobGradeWiseLeaves.class);
		List<JobGradeWiseLeaves> result = query.getResultList();
		logger.info("successfully got the job grade wise leaves.");
		return result;
	}

	// Retrieves the approved leave requests for the given employee ID and year.
	@Override
	public List<EmployeeLeaveRequest> getApprovedLeaveRequests(int id, int year) {
		
		logger.info("getting the approved leave requests");

		String jpqlQuery = "SELECT elrq FROM EmployeeLeaveRequest elrq "
				+ "WHERE elrq.leaveRequestId.employeeId = :employeeId" + " AND elrq.approvedBy > 0"
				+ " AND EXTRACT(YEAR FROM elrq.requestDateTime) = :year";

		TypedQuery<EmployeeLeaveRequest> query = entityManager.createQuery(jpqlQuery, EmployeeLeaveRequest.class);
		query.setParameter("employeeId", id);
		query.setParameter("year", year);
		List<EmployeeLeaveRequest> result = query.getResultList();
		logger.info("successfully got the approved leave requests.");
		return result;
	}

	// adds the job grade wise leaves data.
	@Override
	@Transactional
	public void saveJobGradeLeaveRequest(JobGradeWiseLeaves request) {
		entityManager.persist(request);
		logger.info("Job Grade Leave request is added successfully.");
	}

	@Override
	@Transactional
	public void updateJobGradeLeaveRequest(JobGradeLeavesOutModel jobGradeLeavesmodel) {
		JobGradeWiseLeaves jobGradeWiseLeaves = entityManager.find(JobGradeWiseLeaves.class,
				jobGradeLeavesmodel.getJobGradeId());
		jobGradeWiseLeaves.setCasualLeavesPerYear(jobGradeLeavesmodel.getCasualLeaves());
		jobGradeWiseLeaves.setTotalLeavesPerYear(jobGradeLeavesmodel.getTotalLeaves());
		jobGradeWiseLeaves.setSickLeavesPerYear(jobGradeLeavesmodel.getSickLeaves());
		jobGradeWiseLeaves.setOtherLeavesPerYear(jobGradeLeavesmodel.getOtherLeaves());

		entityManager.merge(jobGradeWiseLeaves);
		
		logger.info("successfully updated the job grade leave");

	}

}