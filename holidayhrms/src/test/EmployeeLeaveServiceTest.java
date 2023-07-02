package test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import models.LeaveValidationModel;
import service.EmployeeLeaveService;
import models.EmployeeLeaveRequest;
import models.JobGradeWiseLeaves;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class EmployeeLeaveServiceTest {


    private EmployeeLeaveService employeeLeaveService;

    @BeforeMethod
    public void setup() {
    	MockitoAnnotations.initMocks(this);
        employeeLeaveService = new EmployeeLeaveService();
    }

    @Test
    public void testCalculateLeavesTaken_NoLeaves() {
        // Arrange
        List<EmployeeLeaveRequest> leaves = new ArrayList<>();
        JobGradeWiseLeaves leavesProvidedStatistics = new JobGradeWiseLeaves();

        // Act
        LeaveValidationModel result = employeeLeaveService.calculateLeavesTaken(leaves, leavesProvidedStatistics);

        // Assert
        assertNotNull(result);
        assertEquals(result.getTakenCasualLeaves(), 0);
        assertEquals(result.getTakenOtherLeaves(), 0);
        assertEquals(result.getTakenSickLeaves(), 0);
        assertEquals(result.getTakenTotalLeaves(), 0);
        assertEquals(result.getPendingCasualLeaves(), 0);
        assertEquals(result.getPendingOtherLeaves(), 0);
        assertEquals(result.getPendingSickLeaves(), 0);
        assertEquals(result.getPendingTotalNoOfLeaves(), 0);
        assertEquals(result.getAllowedCasualLeaves(), 0);
        assertEquals(result.getAllowedOtherLeaves(), 0);
        assertEquals(result.getAllowedSickLeaves(), 0);
        assertEquals(result.getAllowedTotalLeaves(), 0);
    }

    @Test
    public void testCalculateLeavesTaken_WithLeaves() {
        // Arrange
        List<EmployeeLeaveRequest> leaves = new ArrayList<>();
        JobGradeWiseLeaves leavesProvidedStatistics = new JobGradeWiseLeaves();
        leavesProvidedStatistics.setCasualLeavesPerYear(10);
        leavesProvidedStatistics.setSickLeavesPerYear(8);
        leavesProvidedStatistics.setOtherLeavesPerYear(5);
        leavesProvidedStatistics.setTotalLeavesPerYear(20);

        EmployeeLeaveRequest leave1 = new EmployeeLeaveRequest();
        leave1.setApprovedLeaveStartDate(LocalDate.of(2023, 6, 1));
        leave1.setApprovedLeaveEndDate(LocalDate.of(2023, 6, 3));
        leave1.setLeaveType("CASL");

        EmployeeLeaveRequest leave2 = new EmployeeLeaveRequest();
        leave2.setApprovedLeaveStartDate(LocalDate.of(2023, 7, 1));
        leave2.setApprovedLeaveEndDate(LocalDate.of(2023, 7, 5));
        leave2.setLeaveType("SICK");

        leaves.add(leave1);
        leaves.add(leave2);

        // Act
        LeaveValidationModel result = employeeLeaveService.calculateLeavesTaken(leaves, leavesProvidedStatistics);

        System.out.println(result.getTakenCasualLeaves());
        
        // Assert
        assertNotNull(result);
        assertEquals(result.getTakenCasualLeaves(), 3);
        assertEquals(result.getTakenOtherLeaves(), 0);
        assertEquals(result.getTakenSickLeaves(), 5);
        assertEquals(result.getTakenTotalLeaves(), 8);
        assertEquals(result.getPendingCasualLeaves(), 0);
        assertEquals(result.getPendingOtherLeaves(), 0);
        assertEquals(result.getPendingSickLeaves(), 0);
        assertEquals(result.getPendingTotalNoOfLeaves(), 0);
        assertEquals(result.getAllowedCasualLeaves(), 10);
        assertEquals(result.getAllowedOtherLeaves(), 5);
        assertEquals(result.getAllowedSickLeaves(), 8);
        assertEquals(result.getAllowedTotalLeaves(), 20);
    }

    @Test
    public void testCalculateLeavesTaken_PendingLeaves() {
        // Arrange
        List<EmployeeLeaveRequest> leaves = new ArrayList<>();
        JobGradeWiseLeaves leavesProvidedStatistics = new JobGradeWiseLeaves();
        leavesProvidedStatistics.setCasualLeavesPerYear(10);
        leavesProvidedStatistics.setSickLeavesPerYear(8);
        leavesProvidedStatistics.setOtherLeavesPerYear(5);
        leavesProvidedStatistics.setTotalLeavesPerYear(20);

        EmployeeLeaveRequest leave1 = new EmployeeLeaveRequest();
        leave1.setApprovedLeaveStartDate(LocalDate.of(2023, 6, 1));
        leave1.setApprovedLeaveEndDate(LocalDate.of(2023, 6, 3));
        leave1.setLeaveType("CASL");

        EmployeeLeaveRequest leave2 = new EmployeeLeaveRequest();
        leave2.setLeaveStartDate(LocalDate.of(2023, 7, 1));
        leave2.setLeaveEndDate(LocalDate.of(2023, 7, 5));
        leave2.setLeaveType("SICK");
        leave2.setApprovedLeaveStartDate(null);
        leave2.setApprovedLeaveEndDate(null);

        leaves.add(leave1);
        leaves.add(leave2);

        // Act
        LeaveValidationModel result = employeeLeaveService.calculateLeavesTaken(leaves, leavesProvidedStatistics);

        // Assert
        assertNotNull(result);
        assertEquals(result.getTakenCasualLeaves(), 3);
        assertEquals(result.getTakenOtherLeaves(), 0);
        assertEquals(result.getTakenSickLeaves(), 0);
        assertEquals(result.getTakenTotalLeaves(), 3);
        assertEquals(result.getPendingCasualLeaves(), 0);
        assertEquals(result.getPendingOtherLeaves(), 0);
        assertEquals(result.getPendingSickLeaves(), 5);
        assertEquals(result.getPendingTotalNoOfLeaves(), 5);
        assertEquals(result.getAllowedCasualLeaves(), 10);
        assertEquals(result.getAllowedOtherLeaves(), 5);
        assertEquals(result.getAllowedSickLeaves(), 8);
        assertEquals(result.getAllowedTotalLeaves(), 20);
    }
    

    @Test
    public void testCalculateLeavesTakenBetweenDates() {
        // Mock the start and end dates
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 5);

        // Mock the expected result
        long expectedLeaves = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // Call the method under test
        long actualLeaves = EmployeeLeaveService.calculateLeavesTakenBetwwenDates(startDate, endDate);

        // Assert the result
        Assert.assertEquals(actualLeaves, expectedLeaves);
    }
    
    @Test
    public void testCalculateLeavesTakenBetweenDatesWithNullDates() {
        // Mock the start and end dates as null
        LocalDate startDate = null;
        LocalDate endDate = null;

        // Mock the expected result as zero
        long expectedLeaves = 0;

        // Call the method under test
        long actualLeaves = EmployeeLeaveService.calculateLeavesTakenBetwwenDates(startDate, endDate);

        // Assert the result
        Assert.assertEquals(actualLeaves, expectedLeaves);
    }

    @Test
    public void testCalculateLeavesTakenBetweenDatesWithSameDate() {
        // Mock the start and end dates as the same date
        LocalDate date = LocalDate.of(2023, 1, 1);

        // Mock the expected result for a single day
        long expectedLeaves = 1;

        // Call the method under test
        long actualLeaves = EmployeeLeaveService.calculateLeavesTakenBetwwenDates(date, date);

        // Assert the result
        Assert.assertEquals(actualLeaves, expectedLeaves);
    }

}
