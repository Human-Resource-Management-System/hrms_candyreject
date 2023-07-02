package test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO_Interfaces.ReferenceDAOInterface;
import models.EmployeeRefDocuments;
import service.ReferenceServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ReferenceServiceImplTest {

    @Mock
    private EmployeeRefDocuments employeeRefDocumentsMock;

    @Mock
    private ReferenceDAOInterface referenceDAOMock;

    @InjectMocks
    private ReferenceServiceImpl referenceService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetReferenceDocumentById() {
        String documentId = "123";
        when(referenceDAOMock.findById(documentId)).thenReturn(employeeRefDocumentsMock);

        EmployeeRefDocuments result = referenceService.getReferenceDocumentById(documentId);

        verify(referenceDAOMock, times(1)).findById(documentId);
       
    }

    @Test
    public void testAddReferenceDocument() {
        EmployeeRefDocuments document = new EmployeeRefDocuments();
        int adminId = 1;
        LocalDate currentDate = LocalDate.now();

        when(referenceDAOMock.getIndex()).thenReturn(1);
        doNothing().when(referenceDAOMock).save(document);

        referenceService.addReferenceDocument(document, adminId);

        verify(referenceDAOMock, times(1)).getIndex();
        verify(referenceDAOMock, times(1)).save(document);
        
    }

    @Test
    public void testDeleteReferenceDocument() {
        String docname = "document.pdf";

        when(referenceDAOMock.getIndex()).thenReturn(1);
        doNothing().when(referenceDAOMock).deleteById(1);

        referenceService.deleteReferenceDocument(docname);

        verify(referenceDAOMock, times(1)).getIndex();
        verify(referenceDAOMock, times(1)).deleteById(1);
        
    }

    @Test
    public void testGetAllDocuments() {
        List<EmployeeRefDocuments> documents = new ArrayList<>();
        when(referenceDAOMock.getAllDocs()).thenReturn(documents);

        List<EmployeeRefDocuments> result = referenceService.getAllDocuments();

        verify(referenceDAOMock, times(1)).getAllDocs();
        
    }
}
