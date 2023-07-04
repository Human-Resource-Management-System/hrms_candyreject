package test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO.InductionDAOImpl;
import exceptions.CustomException;
import models.Induction;

public class TestInductionDAOImpl {

	@Mock
	private EntityManager entityManager;

	@Mock
	private Logger logger;

	@InjectMocks
	private InductionDAOImpl inductionDAO;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllInductions() {
		// Prepare test data
		List<Integer> expectedResult = new ArrayList<>();
		expectedResult.add(1);
		expectedResult.add(2);

		// Create a mock TypedQuery object using Mockito
		TypedQuery<Integer> query = Mockito.mock(TypedQuery.class);

		// Define the behavior of the mock EntityManager
		when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(expectedResult);

		// Call the method under test
		List<Integer> result = inductionDAO.getAllInductions();

		// Verify the interaction with the mock EntityManager and the result
		verify(entityManager, times(1)).createQuery(Mockito.anyString(), Mockito.eq(Integer.class));
		verify(query, times(1)).getResultList();
		assertEquals(result, expectedResult);
	}

	@Test
	public void testGetInductionById() {
		// Prepare test data
		Integer id = 1;
		List<Induction> expectedResult = new ArrayList<>();
		Induction induction = new Induction();
		expectedResult.add(induction);

		// Create a mock TypedQuery object using Mockito
		TypedQuery<Induction> query = Mockito.mock(TypedQuery.class);

		// Define the behavior of the mock EntityManager
		when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Induction.class))).thenReturn(query);
		when(query.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(query);
		when(query.getResultList()).thenReturn(expectedResult);

		// Call the method under test
		List<Induction> result = inductionDAO.getInductionById(id);

		// Verify the interaction with the mock EntityManager and the result
		verify(entityManager, times(1)).createQuery(Mockito.anyString(), Mockito.eq(Induction.class));
		verify(query, times(1)).setParameter(Mockito.anyString(), Mockito.any());
		verify(query, times(1)).getResultList();
		assertEquals(result, expectedResult);
	}

	@Test(expectedExceptions = CustomException.class)
	public void testInsertEmployeeException() throws CustomException {
		// Prepare test data
		Induction induction = null;

		// Call the method under test
		inductionDAO.insertEmployee(induction);
	}

	@Test
	public void testInsertEmployee() throws CustomException {
		// Prepare test data
		Induction induction = new Induction();

		// Call the method under test
		inductionDAO.insertEmployee(induction);

		// Verify the interaction with the mock EntityManager
		verify(entityManager, times(1)).persist(induction);
	}

	// Add test methods for other DAO methods similarly
	// ...

}
