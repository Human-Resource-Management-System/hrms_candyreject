package test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.InductionDAOImpl;
import exceptions.CustomException;
import models.Induction;

public class TestInductionDAOImpl {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private InductionDAOImpl inductionDAO;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllInductions() {
		// Mocking the TypedQuery and EntityManager
		TypedQuery<Integer> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(Arrays.asList(1, 2, 3));

		// Calling the method under test
		List<Integer> inductions = inductionDAO.getAllInductions();

		// Verify the interactions and assertions
		verify(entityManager).createQuery(anyString(), eq(Integer.class));
		verify(query).getResultList();
		assert inductions != null;
		assert inductions.size() == 3;
		assert inductions.containsAll(Arrays.asList(1, 2, 3));
	}

	@Test
	public void testGetInductionById() {
		// Mocking the TypedQuery and EntityManager
		TypedQuery<Induction> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Induction.class))).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(new ArrayList<>());

		// Calling the method under test
		List<Induction> inductionList = inductionDAO.getInductionById(1);

		// Verify the interactions and assertions
		verify(entityManager).createQuery(anyString(), eq(Induction.class));
		verify(query).setParameter(anyString(), any());
		verify(query).getResultList();
		assert inductionList != null;
		assert inductionList.isEmpty();
	}

	@Test(expectedExceptions = CustomException.class)
	public void testInsertEmployeeWithNullInduction() throws CustomException {
		// Calling the method under test
		inductionDAO.insertEmployee(null);
	}

	@Test
	public void testInsertEmployee() throws CustomException {
		// Mocking the EntityManager
		Induction induction = new Induction();
		doNothing().when(entityManager).persist(induction);

		// Calling the method under test
		inductionDAO.insertEmployee(induction);

		// Verify the interaction
		verify(entityManager).persist(induction);
	}

	@Test
	public void testGetAllEmploymentOffers() {
		// Mocking the TypedQuery and EntityManager
		TypedQuery<Integer> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(Arrays.asList(1, 2, 3));

		// Calling the method under test
		List<Integer> employmentOffers = inductionDAO.getAllEmploymentOffers();

		// Verify the interactions and assertions
		verify(entityManager).createQuery(anyString(), eq(Integer.class));
		verify(query).getResultList();
		assert employmentOffers != null;
		assert employmentOffers.size() == 3;
		assert employmentOffers.containsAll(Arrays.asList(1, 2, 3));
	}

	// @Test
	// public void testUpdateEmploymentOfferStatus() {
	// // Mocking the Query and EntityManager
	// javax.persistence.Query query = mock(javax.persistence.Query.class);
	// when(entityManager.createQuery(anyString())).thenReturn(query);
	//
	// doAnswer(new Answer<Void>() {
	// public Void answer(InvocationOnMock invocation) throws Throwable {
	// String parameterName = invocation.getArgument(0);
	// Object parameterValue = invocation.getArgument(1);
	// if (parameterName.equals("status")) {
	// assertEquals("STATUS", parameterValue);
	// } else if (parameterName.equals("offerId")) {
	// assertEquals(1, parameterValue);
	// } else {
	// fail("Unexpected parameter name: " + parameterName);
	// }
	// return null;
	// }
	// }).when(query).setParameter(anyString(), any());
	//
	// // Calling the method under test
	// inductionDAO.updateEmploymentOfferStatus(1, "STATUS");
	//
	// // Verify the interactions
	// verify(entityManager).createQuery(anyString());
	// verify(query).setParameter(anyString(), any());
	// verify(query).setParameter("status", "STATUS");
	// verify(query).setParameter("offerId", 1);
	// verify(query).executeUpdate();
	// }

	@Test
	public void testGetIndex() {
		// Mocking the TypedQuery and EntityManager
		TypedQuery<Integer> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Integer.class))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(10);

		// Calling the method under test
		Integer index = inductionDAO.getIndex();

		// Verify the interactions and assertions
		verify(entityManager).createQuery(anyString(), eq(Integer.class));
		verify(query).getSingleResult();
		assert index != null;
		assert index.equals(11);
	}

	@Test
	public void testGetEmployeeOfferedIdMaxMap() {
		// Mocking the getCountOfOfferIdentity method
		when(inductionDAO.getCountOfOfferIdentity(1)).thenReturn(3);
		when(inductionDAO.getCountOfOfferIdentity(2)).thenReturn(2);
		when(inductionDAO.getCountOfOfferIdentity(3)).thenReturn(1);

		// Calling the method under test
		Map<Integer, Integer> map = inductionDAO.getEmployeeOfferedIdMaxMap(Arrays.asList(1, 2, 3));

		// Verify the interactions and assertions
		verify(inductionDAO).getCountOfOfferIdentity(1);
		verify(inductionDAO).getCountOfOfferIdentity(2);
		verify(inductionDAO).getCountOfOfferIdentity(3);
		assert map != null;
		assert map.size() == 3;
		assert map.get(1) == 3;
		assert map.get(2) == 2;
		assert map.get(3) == 1;
	}

	@Test
	public void testGetCountOfOfferIdentity() {
		// Mocking the TypedQuery and EntityManager
		TypedQuery<Long> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(5L);

		// Calling the method under test
		int count = inductionDAO.getCountOfOfferIdentity(1);

		// Verify the interactions and assertions
		verify(entityManager).createQuery(anyString(), eq(Long.class));
		verify(query).setParameter(anyString(), any());
		verify(query).getSingleResult();
		assert count == 5;
	}

	@Test
	public void testGetEmploymentInductionDocCountMap() {
		// Mocking the getEmploymentInductionDocCount method
		when(inductionDAO.getEmploymentInductionDocCount(1)).thenReturn(2);
		when(inductionDAO.getEmploymentInductionDocCount(2)).thenReturn(1);
		when(inductionDAO.getEmploymentInductionDocCount(3)).thenReturn(0);

		// Calling the method under test
		Map<Integer, Integer> map = inductionDAO.getEmploymentInductionDocCountMap(Arrays.asList(1, 2, 3));

		// Verify the interactions and assertions
		verify(inductionDAO).getEmploymentInductionDocCount(1);
		verify(inductionDAO).getEmploymentInductionDocCount(2);
		verify(inductionDAO).getEmploymentInductionDocCount(3);
		assert map != null;
		assert map.size() == 3;
		assert map.get(1) == 2;
		assert map.get(2) == 1;
		assert map.get(3) == 0;
	}

	@Test
	public void testGetEmploymentInductionDocCount() {
		// Mocking the TypedQuery and EntityManager
		TypedQuery<Long> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(3L);

		// Calling the method under test
		int count = inductionDAO.getEmploymentInductionDocCount(1);

		// Verify the interactions and assertions
		verify(entityManager).createQuery(anyString(), eq(Long.class));
		verify(query).setParameter(anyString(), any());
		verify(query).getSingleResult();
		assert count == 3;
	}
}
