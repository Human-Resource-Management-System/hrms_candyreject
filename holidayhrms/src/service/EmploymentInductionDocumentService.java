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

	private EmploymentInductionDocumentDAO docDAO;
	private final Logger logger = LoggerFactory.getLogger(EmploymentInductionDocumentService.class);

	@Autowired
	EmploymentInductionDocumentService(EmploymentInductionDocumentDAO docDAO) {
		this.docDAO = docDAO;
	}

	public void addCandidateInductionDocument(EmploymentInductionDocument documentt) {
		logger.info("--------Adding Candidate induction document------------");
		docDAO.addEmploymentInductionDocument(documentt);// moves to the DAO class to add the documents
		logger.info("--------Employment Candidate document added successfully--------------");
	}

	public List<EmploymentInductionDocumentViewModel> getAllDocuments() {
		// Retrieves all employment induction documents.
		logger.info("Retrieving all employment induction documents");
		return docDAO.getAllDocuments();// to get the document list
	}

}
