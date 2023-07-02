package test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO_Interfaces.InductionDAO;
import service.EmploymentInductionService;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class EmploymentInductionServiceTest {

    @Mock
    private InductionDAO inductionDAOMock;

    @InjectMocks
    private EmploymentInductionService inductionService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetId() {
        int currentIndex = 5;

        // Mocking the behavior of the DAO
        when(inductionDAOMock.getIndex()).thenReturn(currentIndex);

        // Calling the method under test
        int result = inductionService.getid();

        // Verifying that the DAO method was called
        verify(inductionDAOMock, times(1)).getIndex();

        assertEquals(result, currentIndex);
    }

    @Test
    public void testGetNextId() {
        int currentIndex = 5;

        // Mocking the behavior of the DAO
        when(inductionDAOMock.getIndex()).thenReturn(currentIndex);

        // Calling the method under test
        int result = inductionService.getidNext();

        // Verifying that the DAO method was called
        verify(inductionDAOMock, times(1)).getIndex();
        
        assertEquals(result, currentIndex+1);

    }
}

