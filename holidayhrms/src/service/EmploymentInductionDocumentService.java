package service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DAO_Interfaces.EmploymentInductionDocumentDAO;
import exceptions.CustomException;
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

	@Override
	public void addCandidateInductionDocument(EmploymentInductionDocument documentt) {
		logger.info("--------Adding Candidate induction document------------");
		docDAO.addEmploymentInductionDocument(documentt);// moves to the DAO class to add the documents
		logger.info("--------Employment Candidate document added successfully--------------");
	}

	@Override
	public List<EmploymentInductionDocumentViewModel> getAllDocuments() {
		// Retrieves all employment induction documents.
		logger.info("--------Retrieving all employment induction documents---------");
		return docDAO.getAllDocuments();// to get the document list
	}

	@Override
	public boolean getDocType(int employmentOfferId, int documentTypeId) throws CustomException {
		int flag = 0;
		List<Integer> listDocType = new ArrayList<>();
		listDocType = docDAO.getDocTypeList(employmentOfferId);

		for (int i : listDocType) {
			if (i == documentTypeId) {
				flag = 1;
				break;
			} else {
				flag = 0;
			}
		}
		System.out.println(flag + "-----------the value of boolean----------------------------");
		if (flag == 1) {
			return true;
		} else {
			throw new CustomException("Document ID " + documentTypeId
					+ " is not required to submit. Please make sure you have submitted the necessary documents: "
					+ listDocType.toString());
		}
	}
}