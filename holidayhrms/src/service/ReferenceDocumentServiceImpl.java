package service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DAO_Interfaces.ReferenceDocumentDAOInterface;
import models.EmployeeRefDocuments;
import service_interfaces.ReferenceDocumentServiceInterface;

@Service
public class ReferenceDocumentServiceImpl implements ReferenceDocumentServiceInterface {

	@Autowired
	private EmployeeRefDocuments document;// entity model class
	private static final Logger logger = LoggerFactory.getLogger(ReferenceDocumentServiceImpl.class);

	@Autowired
	private ReferenceDocumentDAOInterface rd;// DAO interface

	public EmployeeRefDocuments getReferenceDocumentById(int id) {
		logger.info("Getting reference document by id: {}", id);

		return rd.findById(id);
	}

	public void addReferenceDocument(EmployeeRefDocuments document) {
		logger.info("Adding reference document");
		int id = rd.getIndex();// to know last id in the db
		document.setId(id);

		LocalDate dt = LocalDate.now();// to insert the date of upload/change
		document.setLastUpdatedDate(dt);

		document.setLastUpdatedUser(123);// static representation of the user until the sessions involve

		rd.save(document);// calling to DAO class to persist
		System.out.println("EmployeeRefDocument inserted successfully.");
	}

	public void deleteReferenceDocument(String docname) {
		logger.info("Deleting reference document: {}", docname);
		int id = rd.getDOCIndex(docname);// to know id in the db
		// document.setId(id);
		rd.deleteById(id);
	}

	public List<EmployeeRefDocuments> getAllDocuments() {
		logger.info("Retrieving all documents");
		return rd.getAllDocs();
	}
}