package DAO_Interfaces;

import java.util.List;

import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;

public interface EmploymentInductionDocumentDAO {

	/**
	 * Adds an employment induction document to the database.
	 *
	 * @param document the employment induction document to be added
	 */
	void addEmploymentInductionDocument(EmploymentInductionDocument document);

	/**
	 * Retrieves a list of all employment induction documents.
	 *
	 * @return the list of all employment induction documents
	 */
	List<EmploymentInductionDocumentViewModel> getAllDocuments();

	/**
	 * Retrieves a list of all employment induction documents types to be submitted by the employee.
	 * 
	 * @param employmentOfferId
	 *
	 * @return the list of all employment induction document types
	 */
	List<Integer> getDocTypeList(int employmentOfferId);

}
