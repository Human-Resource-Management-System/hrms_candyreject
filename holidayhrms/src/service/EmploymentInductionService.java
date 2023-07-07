package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import DAO_Interfaces.InductionDAO;
import models.input.output.OfferDiffModel;
import service_interfaces.EmploymentInductionServiceInterface;

public class EmploymentInductionService implements EmploymentInductionServiceInterface {

	private InductionDAO idao;// injecting DAO class object
	private OfferDiffModel offerDiff;
	private static final Logger logger = LoggerFactory.getLogger(EmploymentInductionService.class);

	@Autowired
	EmploymentInductionService(InductionDAO idao, OfferDiffModel offerDiff) {
		this.offerDiff = offerDiff;
		this.idao = idao;
	}

	@Override
	public Integer getid() {
		int i = idao.getIndex();// to get the last recently conducted index of induction id
		logger.info("-------Retrieved the last recently conducted index of induction id: {}-----------", i);
		return i;// for next index
	}

	@Override
	public List<OfferDiffModel> getAllEmploymentOffers() {
		List<Integer> hd = idao.getAllEmploymentOffers();
		logger.info("------------Getting the list count ----------");
		Map<Integer, Integer> cntOfferTypeDocMap = idao.getEmployeeOfferedIdMaxMap(hd);
		Map<Integer, Integer> cntInductionDocTypeMap = idao.getEmploymentInductionDocCountMap(hd);
		List<OfferDiffModel> offerDiffList = new ArrayList<>();
		// Iterate over the hd list and calculate the difference between cntOfferTypeDocMap and cntInductionDocTypeMap
		for (Integer id : hd) {
			logger.info("------------Fetching name of the candidate----------");
			String name = idao.getEmployeeOfferName(id);
			OfferDiffModel offerDiff = new OfferDiffModel();
			int maxCount = cntOfferTypeDocMap.getOrDefault(id, 0);
			int cntCount = cntInductionDocTypeMap.getOrDefault(id, 0);
			int diff = maxCount - cntCount;
			// setting the values to the attributes
			offerDiff.setOfferId(id);
			offerDiff.setName(name);
			if (diff == 0) {
				offerDiff.setStatus("Submitted");
			} else if (diff == maxCount) {
				offerDiff.setStatus("No document submitted");
			} else {
				offerDiff.setStatus(diff + " documents pending");
			}
			logger.debug("----------Calculated offer difference for id {}: {}------------", id, offerDiff.getStatus());
			// setting the list of objects into offerDiffList list to pass the list of objects to the controller
			offerDiffList.add(offerDiff);
		}
		logger.info(
				"------------Retrieved all employment offers with offer Document Submition Status differences----------");
		return offerDiffList;
	}

	@Override
	public String getStatusById(Integer indcEmofId) {
		String Status = "";
		int cntEmpOff = idao.getCountOfOfferIdentity(indcEmofId);
		int cntIndDoc = idao.getEmploymentInductionDocCount(indcEmofId);
		int diff = cntEmpOff - cntIndDoc;
		if (diff == 0) {
			Status = "Submitted";
		} else if (diff == cntEmpOff) {
			Status = "No document submitted";
		} else {
			Status = " documents pending";
		}
		logger.debug("----------Calculated offer difference for id {}: {}------------", indcEmofId, Status);
		return Status;
	}
}
