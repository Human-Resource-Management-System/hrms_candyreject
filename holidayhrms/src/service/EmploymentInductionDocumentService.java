package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DAO_Interfaces.EmploymentInductionDocumentDAO;
import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;
import service_interfaces.EmploymentInductionDocumentServiceInterface;

@Service
public class EmploymentInductionDocumentService implements EmploymentInductionDocumentServiceInterface {
	@Autowired
	private EmploymentInductionDocumentDAO docDAO;

	private final Logger logger = LoggerFactory.getLogger(EmploymentInductionDocumentService.class);
	
	// Adds an employment induction document.
	public void addEmploymentInductionDocument(EmploymentInductionDocument document) {
		docDAO.addEmploymentInductionDocument(document);
		logger.info("Added employment induction document with index.");
	}

	// Retrieves the file data of an employment induction document based on the document index.
	public String getEmploymentInductionDocumentFile(int documentIndex) {
		EmploymentInductionDocument document = docDAO.getEmploymentInductionDocument(documentIndex);
		logger.info("Retrieved employment induction document with index.");
		String fileData = document.getDocumentData();

		return fileData;
	}

	// Retrieves a list of all employment induction documents.
	public List<EmploymentInductionDocumentViewModel> getAllDocuments() {
		logger.info("Retrieving all employment induction documents.");
		return docDAO.getAllDocuments();
	}

}
