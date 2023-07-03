package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.ReferenceDocumentDAOImpl;
import DAO_Interfaces.ReferenceDocumentDAOInterface;
import models.EmployeeRefDocuments;
import service.ReferenceDocumentServiceImpl;

class TestReferenceDocumentDAOImpl {
	@Mock
	private EntityManager entityManager;

	@Mock
	private ReferenceDocumentDAOInterface rd;

	@Mock
	private TypedQuery<EmployeeRefDocuments> query;

	@Mock
	private ReferenceDocumentServiceImpl referenceDocumentService;

	@InjectMocks
	private ReferenceDocumentDAOImpl referenceDocumentDAO;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testFindById() {
		// Prepare test data
		int id = 123;
		EmployeeRefDocuments expectedDocument = new EmployeeRefDocuments();
		expectedDocument.setId(id);

		// Define the behavior of the EntityManager mock
		Mockito.when(entityManager.find(EmployeeRefDocuments.class, id)).thenReturn(expectedDocument);

		// Call the method under test
		EmployeeRefDocuments actualDocument = referenceDocumentDAO.findById(id);

		// Verify the result
		assertNotNull(actualDocument);
		assertEquals(expectedDocument.getId(), actualDocument.getId());
	}

	@Test
	void testSave() {
		// Prepare test data
		EmployeeRefDocuments document = new EmployeeRefDocuments();

		// Call the method under test
		referenceDocumentDAO.save(document);

		// Verify the interaction with the EntityManager mock
		Mockito.verify(entityManager).persist(document);
	}

	@Test
	void testDeleteById() {
		// Prepare test data
		int id = 123;
		EmployeeRefDocuments document = new EmployeeRefDocuments();

		// Define the behavior of the EntityManager mock
		Mockito.when(entityManager.find(EmployeeRefDocuments.class, id)).thenReturn(document);

		// Call the method under test
		referenceDocumentDAO.deleteById(id);

		// Verify the interaction with the EntityManager mock
		Mockito.verify(entityManager).remove(document);
	}

	@Test
	void testGetIndex() {
		// Prepare test data
		int expectedMaxId = 10;

		// Define the behavior of the EntityManager mock
		TypedQuery<Integer> queryMock = Mockito.mock(TypedQuery.class);
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(queryMock);
		Mockito.when(queryMock.getSingleResult()).thenReturn(expectedMaxId);

		// Call the method under test
		int actualIndex = referenceDocumentDAO.getIndex();

		// Verify the result
		assertEquals(expectedMaxId + 1, actualIndex);
	}

	@Test
	void testGetDOCIndex() {
		// Prepare test data
		String docname = "document1";
		int expectedIndex = 123;

		// Define the behavior of the EntityManager mock
		TypedQuery<Integer> queryMock = Mockito.mock(TypedQuery.class);
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(queryMock);
		Mockito.when(queryMock.setParameter(Mockito.eq("docname"), Mockito.eq(docname))).thenReturn(queryMock);
		Mockito.when(queryMock.setMaxResults(Mockito.anyInt())).thenReturn(queryMock);
		Mockito.when(queryMock.getSingleResult()).thenReturn(expectedIndex);

		// Call the method under test
		int actualIndex = referenceDocumentDAO.getDOCIndex(docname);

		// Verify the result
		assertEquals(expectedIndex, actualIndex);
	}
}
