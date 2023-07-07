package service_interfaces;

import java.util.List;

import exceptions.CustomException;
import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;

public interface EmploymentInductionDocumentServiceInterface {
	public void addCandidateInductionDocument(EmploymentInductionDocument document);

	public List<EmploymentInductionDocumentViewModel> getAllDocuments();

	public boolean getDocType(int employmentOfferId, int documentTypeId) throws CustomException;
}
