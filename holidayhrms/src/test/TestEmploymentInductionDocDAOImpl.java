package test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.EmploymentInductionDocDAOImpl;
import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;

public class TestEmploymentInductionDocDAOImpl {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private EmploymentInductionDocDAOImpl employmentInductionDocDAO;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddEmploymentInductionDocument() {
		// Create a mock instance of EmploymentInductionDocument
		EmploymentInductionDocument document = mock(EmploymentInductionDocument.class);

		// Call the method being tested
		employmentInductionDocDAO.addEmploymentInductionDocument(document);

		// Verify the expected interactions
		verify(entityManager).persist(document);
	}

	@Test
	public void testGetAllDocuments() {
		// Create a mock instance of Query
		Query query = mock(Query.class);

		// Mock the createQuery method of EntityManager
		when(entityManager.createQuery(anyString())).thenReturn(query);

		// Create a mock list of Object[] for query results
		List<Object[]> results = new ArrayList<>();
		Object[] result1 = { 1, 1, "document1", "verified1", "Title1" };
		Object[] result2 = { 2, 2, "document2", "verified2", "Title2" };
		results.add(result1);
		results.add(result2);

		// Mock the getResultList method of Query
		when(query.getResultList()).thenReturn(results);

		// Call the method being tested
		List<EmploymentInductionDocumentViewModel> documents = employmentInductionDocDAO.getAllDocuments();

		// Verify the expected interactions and assertions
		verify(entityManager).createQuery(anyString());
		Assert.assertEquals(documents.size(), 2);
	}

	@Test
	public void testGetDocTypeList() {
		// Mock the createQuery method of EntityManager
		TypedQuery<Integer> query = mock(TypedQuery.class);
		when(entityManager.createQuery(anyString(), eq(Integer.class))).thenReturn(query);

		// Create a mock list of integers for query results
		List<Integer> docTypeList = Arrays.asList(1, 2, 3);

		// Mock the getResultList method of Query
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(docTypeList);

		// Call the method being tested
		List<Integer> result = employmentInductionDocDAO.getDocTypeList(1);

		// Verify the expected interactions and assertions
		verify(entityManager).createQuery(anyString(), eq(Integer.class));
		verify(query).setParameter(anyString(), any());
		Assert.assertEquals(result, docTypeList);
	}

}
