package test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO_Interfaces.EmploymentInductionDocumentDAO;
import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;
import service.EmploymentInductionDocumentService;

public class TestEmploymentInductionDocumentService {

	@Mock
	private EmploymentInductionDocumentDAO docDAO;

	@Mock
	private Logger logger;

	@InjectMocks
	private EmploymentInductionDocumentService docService;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddCandidateInductionDocument() {
		// Prepare test data
		EmploymentInductionDocument document = new EmploymentInductionDocument();

		// Call the method under test
		docService.addCandidateInductionDocument(document);

		// Verify the interaction with the mock DAO
		verify(docDAO, times(1)).addEmploymentInductionDocument(document);
	}

	@Test
	public void testGetAllDocuments() {
		// Prepare test data
		List<EmploymentInductionDocumentViewModel> documents = new ArrayList<>();

		// Define the behavior of the mock DAO
		when(docDAO.getAllDocuments()).thenReturn(documents);

		// Call the method under test
		List<EmploymentInductionDocumentViewModel> result = docService.getAllDocuments();

		// Verify the interaction with the mock DAO and the result
		verify(docDAO, times(1)).getAllDocuments();
		assertEquals(result, documents);
	}

}
