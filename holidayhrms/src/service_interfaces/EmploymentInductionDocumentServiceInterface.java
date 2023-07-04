package service_interfaces;

import java.util.List;

import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;

public interface EmploymentInductionDocumentServiceInterface {
	public void addCandidateInductionDocument(EmploymentInductionDocument document);

	public List<EmploymentInductionDocumentViewModel> getAllDocuments();
}
