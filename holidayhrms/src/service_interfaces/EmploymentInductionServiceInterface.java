package service_interfaces;

import java.util.List;

import models.Candidate;
import models.input.output.OfferDiffModel;

public interface EmploymentInductionServiceInterface {

	Integer getid();

	List<OfferDiffModel> getAllEmploymentOffers();

	String getStatusById(Integer indcEmofId);

	List<Candidate> getRejecetedList(int candidateId);

}
