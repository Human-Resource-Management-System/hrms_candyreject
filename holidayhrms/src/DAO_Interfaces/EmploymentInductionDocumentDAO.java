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
	 * Retrieves an employment induction document based on the specified document index.
	 *
	 * @param documentIndex the index of the employment induction document
	 * @return the employment induction document matching the document index
	 */
	EmploymentInductionDocument getEmploymentInductionDocument(int documentIndex);
	
	/**
	 * Retrieves a list of all employment induction documents.
	 *
	 * @return the list of all employment induction documents
	 */
	List<EmploymentInductionDocumentViewModel> getAllDocuments();

	// Other methods for updating and deleting EmploymentInductionDocuments can be added here
}
