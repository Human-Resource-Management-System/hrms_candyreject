package service_interfaces;

import java.util.List;

import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;

public interface EmploymentInductionDocumentServiceInterface {
	/**
	 * Adds an employment induction document.
	 *
	 * @param document the employment induction document to be added
	 */
	public void addEmploymentInductionDocument(EmploymentInductionDocument document);

	/**
	 * Retrieves the file data of an employment induction document based on the document index.
	 *
	 * @param documentIndex the index of the employment induction document
	 * @return the file data of the employment induction document
	 */
	public String getEmploymentInductionDocumentFile(int documentIndex);
	
	/**
	 * Retrieves a list of all employment induction documents.
	 *
	 * @return the list of all employment induction documents
	 */
	public List<EmploymentInductionDocumentViewModel> getAllDocuments();
}
