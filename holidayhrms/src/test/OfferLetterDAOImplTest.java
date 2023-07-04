package test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO.OfferLetterDAOImpl;
import models.Candidate;
import models.Employee;
import models.EmploymentOfferDocument;
import models.HrmsEmploymentOffer;
import models.InductionDocumentTypes;

public class OfferLetterDAOImplTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private ApplicationContext context;

	@InjectMocks
	private OfferLetterDAOImpl offerLetterDAO;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		offerLetterDAO = new OfferLetterDAOImpl();
		offerLetterDAO.setEntityManager(entityManager);
		offerLetterDAO.setApplicationContext(context);
	}

	@Test
	public void testFindAllProvidedCandidates() {
		// Mock TypedQuery and expected result
		TypedQuery<Candidate> query = Mockito.mock(TypedQuery.class);
		List<Candidate> expectedResult = new ArrayList<>();
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Candidate.class))).thenReturn(query);
		Mockito.when(query.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expectedResult);

		// Invoke the method under test
		List<Candidate> result = offerLetterDAO.findAllProvidedCandidates();

		// Verify the method calls and assert the result
		Mockito.verify(entityManager).createQuery(
				Mockito.eq("SELECT cd FROM Candidate cd WHERE cd.candStatus = :status"), Mockito.eq(Candidate.class));
		Mockito.verify(query).setParameter("status", "AC");
		Mockito.verify(query).getResultList();
		Assert.assertEquals(result, expectedResult);
	}

	@Test
	public void testFindAllIssuedCandidates() {
		// Mock TypedQuery and expected result
		TypedQuery<Candidate> query = Mockito.mock(TypedQuery.class);
		List<Candidate> expectedResult = new ArrayList<>();
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Candidate.class))).thenReturn(query);
		Mockito.when(query.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expectedResult);

		// Invoke the method under test
		List<Candidate> result = offerLetterDAO.findAllIssuedCandidates();

		// Verify the method calls and assert the result
		Mockito.verify(entityManager).createQuery(Mockito.eq("SELECT c FROM Candidate c WHERE c.candStatus = :status"),
				Mockito.eq(Candidate.class));
		Mockito.verify(query).setParameter("status", "NA");
		Mockito.verify(query).getResultList();
		Assert.assertEquals(result, expectedResult);
	}

	@Test
	public void testGetHrById() {
		// Mock EntityManager and expected result
		Employee expectedEmployee = new Employee();
		Mockito.when(entityManager.find(Mockito.eq(Employee.class), Mockito.anyInt())).thenReturn(expectedEmployee);

		// Invoke the method under test
		Employee result = offerLetterDAO.getHrById(1);

		// Verify the method calls and assert the result
		Mockito.verify(entityManager).find(Mockito.eq(Employee.class), Mockito.anyInt());
		Assert.assertEquals(result, expectedEmployee);
	}

	@Test
	public void testGetCandidateById() {
		// Mock EntityManager and expected result
		Candidate expectedCandidate = new Candidate();
		Mockito.when(entityManager.find(Mockito.eq(Candidate.class), Mockito.anyInt())).thenReturn(expectedCandidate);

		// Invoke the method under test
		Candidate result = offerLetterDAO.getCandidateById(1);

		// Verify the method calls and assert the result
		Mockito.verify(entityManager).find(Mockito.eq(Candidate.class), Mockito.anyInt());
		Assert.assertEquals(result, expectedCandidate);
	}

	@Test
	public void testUpdateCandidateStatus() {
		// Mock Candidate and EntityManager
		Candidate candidate = new Candidate();
		candidate.setCandStatus("NA");

		// Capture the argument passed to entityManager.merge
		ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);

		// Invoke the method under test
		offerLetterDAO.updateCandidateStatus("cand_status", "AC");

		// Verify the method calls and assert the updated status
		Mockito.verify(entityManager).merge(candidateCaptor.capture());
		Candidate updatedCandidate = candidateCaptor.getValue();
		Assert.assertEquals(updatedCandidate.getCandStatus(), "AC");
	}

	@Test
	public void testInsertEofrInto() {
		// Mock HrmsEmploymentOffer
		HrmsEmploymentOffer employmentOffer = new HrmsEmploymentOffer();

		// Invoke the method under test
		offerLetterDAO.insertEofrInto(employmentOffer);

		// Verify the method call
		Mockito.verify(entityManager).persist(Mockito.eq(employmentOffer));
	}

	@Test
	public void testGetLatestEofrIdFromDatabase() {
		// Mock TypedQuery and expected result
		TypedQuery<Integer> query = Mockito.mock(TypedQuery.class);
		Integer expectedId = 1;
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(query);
		Mockito.when(query.getSingleResult()).thenReturn(expectedId);

		// Invoke the method under test
		int result = offerLetterDAO.getLatestEofrIdFromDatabase();

		// Verify the method calls and assert the result
		Mockito.verify(entityManager).createQuery(Mockito.anyString(), Mockito.eq(Integer.class));
		Mockito.verify(query).getSingleResult();
		Assert.assertEquals(result, expectedId.intValue());
	}

	@Test
	public void testGetAllDocuments() {
		// Mock TypedQuery and expected result
		TypedQuery<String> query = Mockito.mock(TypedQuery.class);
		List<String> expectedDocuments = new ArrayList<>();
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expectedDocuments);

		// Invoke the method under test
		List<String> result = offerLetterDAO.getAllDocuments();

		// Verify the method calls and assert the result
		Mockito.verify(entityManager).createQuery(Mockito.anyString(), Mockito.eq(String.class));
		Mockito.verify(query).getResultList();
		Assert.assertEquals(result, expectedDocuments);
	}

	@Test
	public void testGetInductionDocuments() {
		// Prepare mock data
		List<InductionDocumentTypes> mockDocuments = new ArrayList<>();
		mockDocuments.add(new InductionDocumentTypes());
		mockDocuments.add(new InductionDocumentTypes());
		mockDocuments.add(new InductionDocumentTypes());

		// Mock the query and result
		TypedQuery<InductionDocumentTypes> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(entityManager.createQuery("SELECT d FROM InductionDocumentTypes d", InductionDocumentTypes.class))
				.thenReturn(mockQuery);
		Mockito.when(mockQuery.getResultList()).thenReturn(mockDocuments);

		// Call the method
		List<InductionDocumentTypes> documents = offerLetterDAO.getInductionDocuments();

		// Verify the result
		Assert.assertEquals(mockDocuments, documents);
	}

	@Test
	public void testFindIdtyIdByTitle() {
		// Prepare mock data
		List<InductionDocumentTypes> inductionDocuments = new ArrayList<>();
		inductionDocuments.add(new InductionDocumentTypes(1, "DOC1", "Document1", "Documents"));
		inductionDocuments.add(new InductionDocumentTypes(2, "DOC2", "Document2", "Documents"));
		inductionDocuments.add(new InductionDocumentTypes(3, "DOC3", "Document3", "Documents"));

		String titleToFind = "Document2";

		// Call the method
		int idtyId = offerLetterDAO.findIdtyIdByTitle(inductionDocuments, titleToFind);

		// Verify the result
		Assert.assertEquals(2, idtyId);
	}

	@Test
	public void testUpdateEmploymentOfferDocuments() {
		// mock EmploymentOfferDocument
		EmploymentOfferDocument mockDocument = new EmploymentOfferDocument();
		// Set properties of the mock document

		// Call the method to be tested
		offerLetterDAO.updateEmploymentOfferDocuments(mockDocument);

		// Verify that the persist method was called on the entity manager with the mock document
		Mockito.verify(entityManager).persist(mockDocument);
	}
}
