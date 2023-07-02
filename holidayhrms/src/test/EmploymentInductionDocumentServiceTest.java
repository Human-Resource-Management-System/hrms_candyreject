package test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;
import DAO_Interfaces.EmploymentInductionDocumentDAO;
import service.EmploymentInductionDocumentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class EmploymentInductionDocumentServiceTest {

    @Mock
    private EmploymentInductionDocumentDAO docDAOMock;

    @InjectMocks
    private EmploymentInductionDocumentService service;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddEmploymentInductionDocument() {
        EmploymentInductionDocument document = new EmploymentInductionDocument();
        
        // Mocking the behavior of the DAO
        doNothing().when(docDAOMock).addEmploymentInductionDocument(document);

        // Calling the method under test
        service.addEmploymentInductionDocument(document);

        // Verifying that the DAO method was called
        verify(docDAOMock, times(1)).addEmploymentInductionDocument(document);
    }

    @Test
    public void testGetEmploymentInductionDocumentFile() {
        int documentIndex = 1;
        String fileData = "Test file data";

        // Mocking the behavior of the DAO
        when(docDAOMock.getEmploymentInductionDocument(documentIndex)).thenReturn(new EmploymentInductionDocument());
        
        // Calling the method under test
        String result = service.getEmploymentInductionDocumentFile(documentIndex);

        // Verifying that the DAO method was called
        verify(docDAOMock, times(1)).getEmploymentInductionDocument(documentIndex);

    }

    @Test
    public void testGetAllDocuments() {
        List<EmploymentInductionDocumentViewModel> documents = new ArrayList<>();

        // Mocking the behavior of the DAO
        when(docDAOMock.getAllDocuments()).thenReturn(documents);

        // Calling the method under test
        List<EmploymentInductionDocumentViewModel> result = service.getAllDocuments();

        // Verifying that the DAO method was called
        verify(docDAOMock, times(1)).getAllDocuments();

    }
}
