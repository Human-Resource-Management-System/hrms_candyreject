package test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO_Interfaces.InductionDAO;
import models.input.output.OfferDiffModel;
import service.EmploymentInductionService;

public class TestEmploymentInductionService {

	@Mock
	private InductionDAO idao;

	@Mock
	private Logger logger;

	@InjectMocks
	private EmploymentInductionService inductionService;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetId() {
		// Prepare test data
		int index = 5;

		// Define the behavior of the mock object
		when(idao.getIndex()).thenReturn(index);

		// Call the method under test
		Integer result = inductionService.getid();
		verify(idao, times(1)).getIndex();
		// Verify the result and interactions
		assertEquals(Integer.valueOf(index), result);

	}

	@Test
	public void testGetAllEmploymentOffers() {
		// Prepare test data
		List<Integer> hd = Arrays.asList(1, 2, 3);
		Map<Integer, Integer> cntOfferTypeDocMap = new HashMap<>();
		cntOfferTypeDocMap.put(1, 3);
		cntOfferTypeDocMap.put(2, 5);
		Map<Integer, Integer> cntInductionDocTypeMap = new HashMap<>();
		cntInductionDocTypeMap.put(1, 1);
		cntInductionDocTypeMap.put(2, 2);
		cntInductionDocTypeMap.put(3, 0);

		// Define the behavior of the mock object
		when(idao.getAllEmploymentOffers()).thenReturn(hd);
		when(idao.getEmployeeOfferedIdMaxMap(hd)).thenReturn(cntOfferTypeDocMap);
		when(idao.getEmploymentInductionDocCountMap(hd)).thenReturn(cntInductionDocTypeMap);

		// Call the method under test
		List<OfferDiffModel> result = inductionService.getAllEmploymentOffers();
		verify(idao, times(1)).getAllEmploymentOffers();
		verify(idao, times(1)).getEmployeeOfferedIdMaxMap(hd);
		verify(idao, times(1)).getEmploymentInductionDocCountMap(hd);
		// Verify the result and interactions
		assertEquals(3, result.size());

	}

	@Test
	public void testGetStatusById() {
		// Prepare test data
		int indcEmofId = 1;
		int cntEmpOff = 3;
		int cntIndDoc = 2;

		// Define the behavior of the mock object
		when(idao.getCountOfOfferIdentity(indcEmofId)).thenReturn(cntEmpOff);
		when(idao.getEmploymentInductionDocCount(indcEmofId)).thenReturn(cntIndDoc);

		// Call the method under test
		String result = inductionService.getStatusById(indcEmofId);
		verify(idao, times(1)).getCountOfOfferIdentity(indcEmofId);
		verify(idao, times(1)).getEmploymentInductionDocCount(indcEmofId);

		// Verify the result and interactions
		assertEquals(" documents pending", result);

	}
}