package service;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DAO_Interfaces.ReferenceDAOInterface;
import models.EmployeeRefDocuments;
import service_interfaces.ReferenceServiceInterface;

@Service
public class ReferenceServiceImpl implements ReferenceServiceInterface {


	private EmployeeRefDocuments doc;// entity model class
	private ReferenceDAOInterface rd;// DAO interface
	
	@Autowired
	public ReferenceServiceImpl(EmployeeRefDocuments doc, ReferenceDAOInterface rd) {
		this.doc = doc;
		this.rd = rd;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(ReferenceServiceImpl.class);

	//  Retrieves a reference document by its ID.
	public EmployeeRefDocuments getReferenceDocumentById(String id) {
		logger.info("retrieving the reference document by id.");
		return rd.findById(id);
	}
	
    //  Adds a new reference document.
	@Override
	public void addReferenceDocument(EmployeeRefDocuments document,int adminid) {
		
		logger.info("Request received to add a reference document.");


		int id = rd.getIndex();// to know last id in the db
		document.setId(id);

		LocalDate dt = LocalDate.now();// to insert the date of upload/change
		document.setLastUpdatedDate(dt);
		document.setLastUpdatedUser(adminid);

		rd.save(document);// calling to DAO class to persist
		logger.info("Reference document inserted successfully.");
	}

	// 	Deletes a reference document by its name.
	@Override
	public void deleteReferenceDocument(String docname) {
		logger.info("Request received to delete a reference document.");
		int id = rd.getIndex();// to know last id in the db
		doc.setId(id);
		rd.deleteById(id);
	}
    
	// Retrieves all reference documents.
	@Override
	public List<EmployeeRefDocuments> getAllDocuments() {
		logger.info("Request received to retrieve all reference documents.");
		return rd.getAllDocs();
	}

}
