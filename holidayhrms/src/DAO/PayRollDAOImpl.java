package DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DAO_Interfaces.PayRollDAO;
import models.EmployeePayslip;

public class PayRollDAOImpl implements PayRollDAO {

	private final Logger logger = LoggerFactory.getLogger(ApplyPermissionDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void insertEmployeePayslip(EmployeePayslip payslip) {
		logger.info("Employee payRoll have been Inserted ");

		entityManager.persist(payslip);
	}

	@Override
	@Transactional
	public EmployeePayslip getEmployeePayslipsByEmployeeId(int employeeId) {
		logger.info("Employee fetches payslip  ");

		String jpql = "SELECT p FROM EmployeePayslip p WHERE p.employeeId = :employeeId";
		TypedQuery<EmployeePayslip> query = entityManager.createQuery(jpql, EmployeePayslip.class);
		query.setParameter("employeeId", employeeId);
		return query.getSingleResult();
	}

	@Override
	@Transactional
	public EmployeePayslip getEmployeePayslipsByEmployeeIdAndMonthYear(int employeeId, String monthYear) {
		logger.info("Fetching paySlip based on month at employee side");

		String jpql = "SELECT p FROM EmployeePayslip p WHERE p.employeeId = :employeeId AND p.monthYear = :monthYear";
		TypedQuery<EmployeePayslip> query = entityManager.createQuery(jpql, EmployeePayslip.class);
		query.setParameter("employeeId", employeeId);
		query.setParameter("monthYear", monthYear);
		return query.getSingleResult();
	}

}