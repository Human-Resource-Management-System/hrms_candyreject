package test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO_Interfaces.InductionDAO;
import models.input.output.OfferDiffModel;
import service.EmploymentInductionService;
import service_interfaces.EmploymentInductionServiceInterface;

public class TestEmploymentInductionService {

	@Mock
	private InductionDAO inductionDAO;

	@InjectMocks
	public EmploymentInductionServiceInterface employmentInductionService = new EmploymentInductionService(inductionDAO,
			new OfferDiffModel());

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetId() {
		// Mock the getIndex method of InductionDAO
		when(inductionDAO.getIndex()).thenReturn(10);

		// Call the method being tested
		Integer result = employmentInductionService.getid();

		// Verify the expected interactions and assertions
		verify(inductionDAO).getIndex();
		assert result == 10;
	}

	@Test
	public void testGetAllEmploymentOffers() {
		// Create mock data
		List<Integer> hd = Arrays.asList(1, 2);
		Map<Integer, Integer> cntOfferTypeDocMap = new HashMap<>();
		cntOfferTypeDocMap.put(1, 5);
		cntOfferTypeDocMap.put(2, 3);
		Map<Integer, Integer> cntInductionDocTypeMap = new HashMap<>();
		cntInductionDocTypeMap.put(1, 2);
		cntInductionDocTypeMap.put(2, 1);

		// Mock the methods of InductionDAO
		when(inductionDAO.getAllEmploymentOffers()).thenReturn(hd);
		when(inductionDAO.getEmployeeOfferedIdMaxMap(hd)).thenReturn(cntOfferTypeDocMap);
		when(inductionDAO.getEmploymentInductionDocCountMap(hd)).thenReturn(cntInductionDocTypeMap);
		when(inductionDAO.getEmployeeOfferName(1)).thenReturn("John Doe");
		when(inductionDAO.getEmployeeOfferName(2)).thenReturn("Jane Smith");

		// Call the method being tested
		List<OfferDiffModel> result = employmentInductionService.getAllEmploymentOffers();

		// Verify the expected interactions and assertions
		verify(inductionDAO).getAllEmploymentOffers();
		verify(inductionDAO).getEmployeeOfferedIdMaxMap(hd);
		verify(inductionDAO).getEmploymentInductionDocCountMap(hd);
		verify(inductionDAO).getEmployeeOfferName(1);
		verify(inductionDAO).getEmployeeOfferName(2);

		assert result.size() == 2;
		assert result.get(0).getOfferId() == 1;
		assert result.get(0).getName().equals("John Doe");
		assert result.get(0).getStatus().equals("3 documents pending");
		assert result.get(1).getOfferId() == 2;
		assert result.get(1).getName().equals("Jane Smith");
		assert result.get(1).getStatus().equals("2 documents pending");
	}

	@Test
	public void testGetStatusById() {
		// Mock the getCountOfOfferIdentity and getEmploymentInductionDocCount methods of InductionDAO
		when(inductionDAO.getCountOfOfferIdentity(1)).thenReturn(5);
		when(inductionDAO.getEmploymentInductionDocCount(1)).thenReturn(2);

		// Call the method being tested
		String result = employmentInductionService.getStatusById(1);

		// Verify the expected interactions and assertions
		verify(inductionDAO).getCountOfOfferIdentity(1);
		verify(inductionDAO).getEmploymentInductionDocCount(1);
		assert result.equals("3 documents pending");
	}
}
