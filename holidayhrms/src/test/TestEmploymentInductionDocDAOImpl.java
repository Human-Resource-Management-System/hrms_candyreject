package test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO.EmploymentInductionDocDAOImpl;
import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;

public class TestEmploymentInductionDocDAOImpl {

	@Mock
	private EntityManager entityManager;

	@Mock
	private Logger logger;

	@InjectMocks
	private EmploymentInductionDocDAOImpl docDAO;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddEmploymentInductionDocument() {
		// Prepare test data
		EmploymentInductionDocument document = new EmploymentInductionDocument();

		// Call the method under test
		docDAO.addEmploymentInductionDocument(document);

		// Verify the interaction with the mock EntityManager
		verify(entityManager, times(1)).persist(document);
	}

	@Test
	public void testGetAllDocuments() {
		// Prepare test data
		List<Object[]> results = new ArrayList<>();
		Object[] result = { 1, 1, "documentData", "verified" };
		results.add(result);

		// Create a mock Query object using Mockito
		Query query = Mockito.mock(Query.class);
		when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(results);

		// Call the method under test
		List<EmploymentInductionDocumentViewModel> result1 = docDAO.getAllDocuments();

		// Verify the interaction with the mock EntityManager and the result
		verify(entityManager, times(1)).createQuery(Mockito.anyString());
		verify(query, times(1)).getResultList();
		assertEquals(result1.size(), 1);
		assertEquals(result1.get(0).getEmplid(), 1);
		assertEquals(result1.get(0).getEmid_idty_id(), 1);
		assertEquals(result1.get(0).getDocumentData(), "documentData");
		assertEquals(result1.get(0).getVerified(), "verified");
	}

}
