package test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.ApplyPermissionDAOImpl;
import models.ApplyPermissions;
import models.Employee;

public class ApplyPermissionDAOImplTest {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private ApplyPermissionDAOImpl applyPermissionDAO;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testPersist() {
		// Create a mock ApplyPermissions object
		ApplyPermissions applyPermissions = mock(ApplyPermissions.class);

		// Call the method under test
		applyPermissionDAO.persist(applyPermissions);

		// Verify that the EntityManager's persist method is called with the ApplyPermissions object
		verify(entityManager, times(1)).persist(applyPermissions);
	}

	@Test
	public void testGetNextPermissionIndex() {
		int employeeId = 123;
		int expectedIndex = 2;

		// Mock the behavior of the Query and EntityManager
		Query query = mock(Query.class);
		when(entityManager.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(eq("empl_id"), eq(employeeId))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(expectedIndex);

		// Call the method under test
		int result = applyPermissionDAO.getNextPermissionIndex(employeeId);

		// Verify the result
		Assert.assertEquals(result, expectedIndex);
	}

	@Test
	public void testGetPermissionByIdAndIndex() {
		int employeeId = 123;
		int index = 2;
		ApplyPermissions expectedPermission = mock(ApplyPermissions.class);

		// Mock the behavior of the TypedQuery and EntityManager
		TypedQuery<ApplyPermissions> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(ApplyPermissions.class))).thenReturn(query);
		when(query.setParameter(eq("empl_id"), eq(employeeId))).thenReturn(query);
		when(query.setParameter(eq("ep_index"), eq(index))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(expectedPermission);

		// Call the method under test
		ApplyPermissions result = applyPermissionDAO.getPermissionByIdAndIndex(employeeId, index);

		// Verify the result
		Assert.assertEquals(result, expectedPermission);
	}

	@Test
	public void testGetPermissionByIdAndIndex_NoResult() {
		int employeeId = 123;
		int index = 2;

		// Mock the behavior of the TypedQuery and EntityManager to throw a NoResultException
		TypedQuery<ApplyPermissions> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(ApplyPermissions.class))).thenReturn(query);
		when(query.setParameter(eq("empl_id"), eq(employeeId))).thenReturn(query);
		when(query.setParameter(eq("ep_index"), eq(index))).thenReturn(query);
		when(query.getSingleResult()).thenThrow(NoResultException.class);

		// Call the method under test
		ApplyPermissions result = applyPermissionDAO.getPermissionByIdAndIndex(employeeId, index);

		// Verify the result
		Assert.assertNull(result);
	}

	@Test
	public void testGetEmployeesByHRAndManager() {
		int employeeId = 123;
		List<Employee> expectedEmployees = new ArrayList<>();
		expectedEmployees.add(mock(Employee.class));
		expectedEmployees.add(mock(Employee.class));

		// Mock the behavior of the TypedQuery and EntityManager
		TypedQuery<Employee> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Employee.class))).thenReturn(query);
		when(query.setParameter(eq("employeeId"), eq(employeeId))).thenReturn(query);
		when(query.getResultList()).thenReturn(expectedEmployees);

		// Call the method under test
		List<Employee> result = applyPermissionDAO.getEmployeesByHRAndManager(employeeId);

		// Verify the result
		Assert.assertEquals(result, expectedEmployees);
	}

	@Test
	public void testGetEmployeeAndPermissionRequestData() {
		int id = 123;
		Date current = mock(Date.class);
		ApplyPermissions expectedPermission = mock(ApplyPermissions.class);

		// Mock the behavior of the TypedQuery and EntityManager
		TypedQuery<ApplyPermissions> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(ApplyPermissions.class))).thenReturn(query);
		when(query.setParameter(eq("employeeIds"), eq(id))).thenReturn(query);
		when(query.setParameter(eq("currentdate"), eq(current))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(expectedPermission);

		// Call the method under test
		ApplyPermissions result = applyPermissionDAO.getEmployeeAndPermissionRequestData(id, current);

		// Verify the result
		Assert.assertEquals(result, expectedPermission);
	}

	@Test
	public void testGetEmployeeAndPermissionRequestData_NoResult() {
		int id = 123;
		Date current = mock(Date.class);

		// Mock the behavior of the TypedQuery and EntityManager to throw a NoResultException
		TypedQuery<ApplyPermissions> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(ApplyPermissions.class))).thenReturn(query);
		when(query.setParameter(eq("employeeIds"), eq(id))).thenReturn(query);
		when(query.setParameter(eq("currentdate"), eq(current))).thenReturn(query);
		when(query.getSingleResult()).thenThrow(NoResultException.class);

		// Call the method under test
		ApplyPermissions result = applyPermissionDAO.getEmployeeAndPermissionRequestData(id, current);

		// Verify the result
		Assert.assertNull(result);
	}

	@Test
	public void testGetEmployeeAndPermissionRequestDataCountPerMonth() {
		int id = 123;
		int month = 6;
		int year = 2023;
		long expectedCount = 10;

		// Mock the behavior of the TypedQuery and EntityManager
		TypedQuery<Long> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
		when(query.setParameter(eq("employeeIds"), eq(id))).thenReturn(query);
		when(query.setParameter(eq("month"), eq(month))).thenReturn(query);
		when(query.setParameter(eq("year"), eq(year))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(expectedCount);

		// Call the method under test
		long result = applyPermissionDAO.getEmployeeAndPermissionRequestDataCountPerMonth(id, month, year);

		// Verify the result
		Assert.assertEquals(result, expectedCount);
	}

	@Test
	public void testGetEmployeeApprovedPermissionsCount() {
		int id = 123;
		int year = 2023;
		long expectedCount = 7;

		// Mock the behavior of the TypedQuery and EntityManager
		TypedQuery<Long> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
		when(query.setParameter(eq("employeeIds"), eq(id))).thenReturn(query);
		when(query.setParameter(eq("year"), eq(year))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(expectedCount);

		// Call the method under test
		long result = applyPermissionDAO.getEmployeeApprovedPermissionsCount(id, year);

		// Verify the result
		Assert.assertEquals(result, expectedCount);
	}

	@Test
	public void testAppliedPermissions() {
		// Mock the necessary objects
		Query query = mock(Query.class);
		List<ApplyPermissions> expectedResult = new ArrayList<>();
		expectedResult.add(new ApplyPermissions());
		int empId = 123;

		// Set up mock behaviors
		when(entityManager.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(eq("empId"), eq(empId))).thenReturn(query);
		when(query.getResultList()).thenReturn(expectedResult);

		// Call the method to be tested
		List<ApplyPermissions> result = applyPermissionDAO.appliedPermissions(empId);

		// Verify the interactions and assertions
		verify(entityManager).createQuery(anyString());
		verify(query).setParameter(eq("empId"), eq(empId));
		verify(query).getResultList();

		verify(entityManager, times(1)).createQuery(anyString());
		verify(query, times(1)).setParameter(eq("empId"), eq(empId));
		verify(query, times(1)).getResultList();
		assertSame(result, expectedResult);
	}
}