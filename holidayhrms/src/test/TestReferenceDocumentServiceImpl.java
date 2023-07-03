package test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO_Interfaces.ReferenceDocumentDAOInterface;
import models.EmployeeRefDocuments;
import service.ReferenceDocumentServiceImpl;

class TestReferenceDocumentServiceImpl {
	@Mock
	private EmployeeRefDocuments document;

	@Mock
	private ReferenceDocumentDAOInterface rd;

	@InjectMocks
	private ReferenceDocumentServiceImpl referenceDocumentService;

	@BeforeClass
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetReferenceDocumentById() {
		int id = 1;
		EmployeeRefDocuments expectedDocument = new EmployeeRefDocuments();
		when(rd.findById(id)).thenReturn(expectedDocument);

		EmployeeRefDocuments result = referenceDocumentService.getReferenceDocumentById(id);

		assertEquals(expectedDocument, result);

		verify(rd).findById(id);
	}

	@Test
	void testAddReferenceDocument() {
		EmployeeRefDocuments document = new EmployeeRefDocuments();
		int id = 1;
		LocalDate currentDate = LocalDate.now();
		document.setId(id);
		document.setLastUpdatedDate(currentDate);
		document.setLastUpdatedUser(123);
		when(rd.getIndex()).thenReturn(id);

		referenceDocumentService.addReferenceDocument(document);

		verify(rd).getIndex();
		verify(rd).save(document);
		assertEquals(currentDate, document.getLastUpdatedDate());
		assertEquals(123, document.getLastUpdatedUser());
	}

	@Test
	void testDeleteReferenceDocument() {
		String docname = "example.pdf";
		int id = 1;
		when(rd.getDOCIndex(docname)).thenReturn(id);

		referenceDocumentService.deleteReferenceDocument(docname);

		verify(rd).getDOCIndex(docname);
		verify(rd).deleteById(id);
	}

	@Test
	void testGetAllDocuments() {
		// Prepare test data
		List<EmployeeRefDocuments> expectedDocuments = new ArrayList<>();
		expectedDocuments.add(new EmployeeRefDocuments());

		// Define the behavior of the ReferenceDocumentDAOInterface mock
		when(rd.getAllDocs()).thenReturn(expectedDocuments);

		// Call the method under test
		List<EmployeeRefDocuments> result = referenceDocumentService.getAllDocuments();

		// Verify the result
		assertEquals(expectedDocuments, result);

		// Verify the interaction with the ReferenceDocumentDAOInterface mock
		verify(rd).getAllDocs();
	}
}
