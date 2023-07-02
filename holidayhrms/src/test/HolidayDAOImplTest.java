package test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.HolidayDAOImpl;
import models.GradeHoliday;
import models.Holiday;

public class HolidayDAOImplTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private CriteriaQuery<Holiday> holidayCriteriaQuery;

	@Mock
	private Root<Holiday> holidayRoot;

	@Mock
	private TypedQuery<Holiday> holidayQuery;

	@Mock
	private TypedQuery<GradeHoliday> gradeholidayQuery;

	@InjectMocks
	private HolidayDAOImpl holidayDAO;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAllHolidays() {
		// Create a list of holidays
		List<Holiday> holidays = new ArrayList<>();
		holidays.add(new Holiday());
		holidays.add(new Holiday());

		Mockito.when(entityManager.createQuery("SELECT h FROM Holiday h ORDER BY h.hday_date ASC", Holiday.class))
				.thenReturn(holidayQuery);
		Mockito.when(holidayQuery.getResultList()).thenReturn(holidays);

		// Call the method under test
		List<Holiday> result = holidayDAO.findAllHolidays();

		// Verify the result
		Assert.assertEquals(result.size(), 2);
		// You can add more assertions based on the specific behavior you expect
	}

	@Test
	public void testFindHolidayById() {
		// Create a GradeHoliday object
		GradeHoliday gradeHoliday = new GradeHoliday();

		// Mock the behavior of the EntityManager
		Mockito.when(entityManager.find(GradeHoliday.class, "1")).thenReturn(gradeHoliday);

		// Call the method under test
		GradeHoliday result = holidayDAO.findHolidayById("1");

		// Verify the result
		Assert.assertEquals(result, gradeHoliday);
	}

	@Test
	public void testFindAllGradeHolidays() {
		// Create a list of GradeHolidays
		List<GradeHoliday> gradeHolidays = new ArrayList<>();
		gradeHolidays.add(new GradeHoliday());
		gradeHolidays.add(new GradeHoliday());

		// Mock the behavior of the EntityManager and TypedQuery
		Mockito.when(entityManager.createQuery("SELECT gh FROM GradeHoliday gh", GradeHoliday.class))
				.thenReturn(gradeholidayQuery);
		Mockito.when(gradeholidayQuery.getResultList()).thenReturn(gradeHolidays);

		// Call the method under test
		List<GradeHoliday> result = holidayDAO.findAllGradeHolidays();

		// Verify the result
		Assert.assertEquals(result.size(), 2);
	}
}
