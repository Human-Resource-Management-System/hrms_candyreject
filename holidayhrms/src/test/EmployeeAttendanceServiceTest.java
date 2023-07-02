package test;
import org.apache.poi.ss.usermodel.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import DAO_Interfaces.EmployeeAttendanceDAO;
import service.EmployeeAttendanceService;
import models.AttendanceEvent;
import models.EmployeeAttendance;
import models.EmployeeRequestResult;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.*;
import org.testng.annotations.*;

import java.io.*;
import java.time.*;

import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class EmployeeAttendanceServiceTest {

    @Mock
    private EmployeeAttendanceDAO employeeAttendanceDAO;

    @InjectMocks
    private EmployeeAttendanceService employeeAttendanceService;
    
    @Mock
    private Cell cell;
    
    @Mock
    private MultipartFile mockFile;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertEmployeeAttendance() {
        EmployeeAttendance attendance = new EmployeeAttendance();
        employeeAttendanceService.insertEmployeeAttendance(attendance);
        verify(employeeAttendanceDAO).save(attendance);
    }

    @Test
    public void testGetYesterdayPunchData() {
        int employeeId = 1;
        LocalDateTime punchIn = LocalDateTime.of(2023, 6, 30, 9, 0);
        LocalDateTime punchOut = LocalDateTime.of(2023, 6, 30, 17, 0);
        Object[] punchData = {punchIn, punchOut};
        List<Object[]> results = new ArrayList<>();
        results.add(punchData);

        when(employeeAttendanceDAO.getYesterdayPunchInAndPunchOut(employeeId)).thenReturn(results);

        List<AttendanceEvent> formattedEvents = employeeAttendanceService.getYesterdayPunchData(employeeId);

        assertEquals(formattedEvents.size(), 2);

        AttendanceEvent punchInEvent = formattedEvents.get(0);
        assertEquals(punchInEvent.getEvent(), "Punch In");
        assertEquals(punchInEvent.getTime(), "09:00 am");

        AttendanceEvent punchOutEvent = formattedEvents.get(1);
        assertEquals(punchOutEvent.getEvent(), "Punch Out");
        assertEquals(punchOutEvent.getTime(), "05:00 pm");

        verify(employeeAttendanceDAO).getYesterdayPunchInAndPunchOut(employeeId);
    }

    @Test
    public void testCalculateAttendance() {
        List<Object[]> punchData = new ArrayList<>();
        LocalDateTime punchIn1 = LocalDateTime.of(2023, 6, 30, 9, 0);
        LocalDateTime punchOut1 = LocalDateTime.of(2023, 6, 30, 17, 0);
        Object[] punches1 = {punchIn1, punchOut1};
        punchData.add(punches1);

        LocalDateTime punchIn2 = LocalDateTime.of(2023, 6, 29, 9, 0);
        LocalDateTime punchOut2 = LocalDateTime.of(2023, 6, 29, 17, 0);
        Object[] punches2 = {punchIn2, punchOut2};
        punchData.add(punches2);

        when(employeeAttendanceDAO.getPunchInAndPunchOutDataForYearAndMonthAndEmployee(1, 2023, 6)).thenReturn(punchData);

        EmployeeRequestResult result = employeeAttendanceService.calculateAttendance(
            employeeAttendanceDAO.getPunchInAndPunchOutDataForYearAndMonthAndEmployee(1, 2023, 6)
        );

        assertNotNull(result);
        assertEquals(result.getDayswithminhrs(), 2);
        assertEquals(result.getPercentage(), 100.0);
        assertEquals(result.getTotaldays(), 2);

        verify(employeeAttendanceDAO).getPunchInAndPunchOutDataForYearAndMonthAndEmployee(1, 2023, 6);
    }


    @Test
    public void testConvertToDateTime_WhenCellContainsDateTime_ReturnsDateTimeValue() {
        // Create a sample LocalDateTime value
        LocalDateTime expectedDateTime = LocalDateTime.of(2023, 7, 1, 10, 30);

        // Mock the Cell object
        when(cell.getCellType()).thenReturn(CellType.NUMERIC);
        when(cell.getLocalDateTimeCellValue()).thenReturn(expectedDateTime);

        // Call the method under test
        LocalDateTime actualDateTime = employeeAttendanceService.convertToDateTime(cell);

        // Assert the result
        assertEquals(expectedDateTime, actualDateTime);
    }

    @Test
    public void testConvertToDateTime_WhenCellDoesNotContainDateTime_ReturnsNull() {
        // Mock the Cell object
        when(cell.getCellType()).thenReturn(CellType.STRING);

        // Call the method under test
        LocalDateTime actualDateTime = employeeAttendanceService.convertToDateTime(cell);

        // Assert the result
        assertEquals(null, actualDateTime);
    }

    @Test
    public void testGetYears() throws ParseException {
        // Create a sample join date
        String joinDateStr = "2020-01-01";
        Date joinDate = new SimpleDateFormat("yyyy-MM-dd").parse(joinDateStr);

        // Call the method under test
        List<Integer> yearList = employeeAttendanceService.getYears(joinDate);

        // Assert the result
        assertNotNull(yearList);
        assertEquals(yearList.size(), 4); // Assuming the current year is 2023

        // Verify the expected years in the list
        int expectedYear = 2020;
        for (Integer year : yearList) {
            assertEquals(year, expectedYear);
            expectedYear++;
        }
    }
    
    @Test
    public void testGetAvgPunchInAndOut_WithValidData_ReturnsAveragePunchInAndOutTimes() {
        // Prepare test data
        int employeeId = 1;
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        LocalDateTime punchIn1 = LocalDateTime.of(year, month, 30, 9, 0);
        LocalDateTime punchOut1 = LocalDateTime.of(year, month, 30, 10, 0);
        LocalDateTime punchIn2 = LocalDateTime.of(year, month, 30, 11, 0);
        LocalDateTime punchOut2 = LocalDateTime.of(year, month, 30, 12, 0);

        // Prepare punch data
        List<Object[]> punchData = new ArrayList<>();
        punchData.add(new Object[]{punchIn1, punchOut1});
        punchData.add(new Object[]{punchIn2, punchOut2});

        // Mock the method call in the DAO
        when(employeeAttendanceDAO.getPunchInAndPunchOutDataForYearAndMonthAndEmployee(employeeId, year, month))
                .thenReturn(punchData);

        // Call the method under test
        List<Long> result = employeeAttendanceService.getAvgPunchInAndOut(employeeId);
        
        System.out.println("Punch Data: " + punchData);
        System.out.println("Result: " + result);

        // Assert the result
        assertNotNull(result);
        assertEquals(result.size(), 2);

        long expectedPunchInAvg = Duration.between(punchIn1, punchOut1).toMinutes() +
                Duration.between(punchIn2, punchOut2).toMinutes();
        expectedPunchInAvg /= 1;
        System.out.println(expectedPunchInAvg);
        assertEquals(result.get(0), expectedPunchInAvg);

        long expectedPunchOutAvg = Duration.between(punchOut1, punchIn2).toMinutes();
        System.out.println(expectedPunchOutAvg);
        expectedPunchOutAvg /= 1;
        assertEquals(result.get(1), expectedPunchOutAvg);

        // Verify the method call to the DAO
        verify(employeeAttendanceDAO).getPunchInAndPunchOutDataForYearAndMonthAndEmployee(employeeId, year, month);
    }


    @Test
    public void testGetAvgPunchInAndOut_WithNoPunchData_ReturnsEmptyList() {
        // Prepare test data
        int employeeId = 1;
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        
        // Prepare an empty punch data list
        List<Object[]> punchData = new ArrayList<>();
        
        // Mock the method call in the DAO
        when(employeeAttendanceDAO.getPunchInAndPunchOutDataForYearAndMonthAndEmployee(employeeId, year, month))
                .thenReturn(punchData);
        
        // Call the method under test
        List<Long> result = employeeAttendanceService.getAvgPunchInAndOut(employeeId);
        
        // Assert the result
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        // Verify the method call to the DAO
        verify(employeeAttendanceDAO).getPunchInAndPunchOutDataForYearAndMonthAndEmployee(employeeId, year, month);
    }

}
