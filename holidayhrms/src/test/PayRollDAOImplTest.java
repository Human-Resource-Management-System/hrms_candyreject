package test;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.PayRollDAOImpl;
import models.EmployeePayslip;

public class PayRollDAOImplTest {
	private PayRollDAOImpl payRollDAO;
	private EntityManager entityManager;
	private TypedQuery<EmployeePayslip> query;

	@BeforeMethod
	public void setup() {
		payRollDAO = new PayRollDAOImpl();
		entityManager = mock(EntityManager.class);
		query = mock(TypedQuery.class);
		payRollDAO.setEntityManager(entityManager);
	}

	@Test
	public void testInsertEmployeePayslip() {
		EmployeePayslip payslip = new EmployeePayslip();
		doNothing().when(entityManager).persist(any(EmployeePayslip.class));

		payRollDAO.insertEmployeePayslip(payslip);

		verify(entityManager, times(1)).persist(payslip);
	}

	@Test
	public void testGetEmployeePayslipsByEmployeeId() {
		int employeeId = 1;
		EmployeePayslip payslip = new EmployeePayslip();
		when(entityManager.createQuery(anyString(), eq(EmployeePayslip.class))).thenReturn(query);
		when(query.setParameter("employeeId", employeeId)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(payslip);

		EmployeePayslip result = payRollDAO.getEmployeePayslipsByEmployeeId(employeeId);

		verify(entityManager, times(1)).createQuery(anyString(), eq(EmployeePayslip.class));
		verify(query, times(1)).setParameter("employeeId", employeeId);
		verify(query, times(1)).getSingleResult();
		assertEquals(result, payslip);
	}

	@Test
	public void testGetEmployeePayslipsByEmployeeIdAndMonthYear() {
		int employeeId = 1;
		String monthYear = "2023-07";
		EmployeePayslip payslip = new EmployeePayslip();
		when(entityManager.createQuery(anyString(), eq(EmployeePayslip.class))).thenReturn(query);
		when(query.setParameter("employeeId", employeeId)).thenReturn(query);
		when(query.setParameter("monthYear", monthYear)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(payslip);

		EmployeePayslip result = payRollDAO.getEmployeePayslipsByEmployeeIdAndMonthYear(employeeId, monthYear);

		verify(entityManager, times(1)).createQuery(anyString(), eq(EmployeePayslip.class));
		verify(query, times(1)).setParameter("employeeId", employeeId);
		verify(query, times(1)).setParameter("monthYear", monthYear);
		verify(query, times(1)).getSingleResult();
		assertEquals(result, payslip);
	}
}