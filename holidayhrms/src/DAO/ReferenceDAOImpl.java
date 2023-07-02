package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import DAO_Interfaces.ReferenceDAOInterface;
import models.EmployeeRefDocuments;

@Repository
public class ReferenceDAOImpl implements ReferenceDAOInterface {

	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(ReferenceDAOImpl.class);

	// 	Retrieves a reference document by its ID.
	@Override
	@Transactional
	public EmployeeRefDocuments findById(String id) {
		logger.info("Finding reference document by ID.");
		return entityManager.find(EmployeeRefDocuments.class, id);
	}

	// 	Saves a reference document in the data source.
	@Override
	@Transactional
	public void save(EmployeeRefDocuments document) {
		logger.info("Saving reference document.");
		entityManager.persist(document);
		logger.info("Reference document saved successfully");
	}

	// 	Deletes a reference document from the data source by its ID.
	@Override
	@Transactional
	public void deleteById(int id) {
		logger.info("Deleting reference document by ID.");
		EmployeeRefDocuments document = entityManager.find(EmployeeRefDocuments.class, id);
		if (document != null) {
			logger.info("Reference document deleted successfully");
			entityManager.remove(document);
		}else {
			logger.warn("No reference document found with ID");
		}
	}

	// 	Retrieves all reference documents from the data source.
	@Override
	@Transactional
	public List<EmployeeRefDocuments> getAllDocs() {
		logger.info("Fetching all reference documents");
		String query = "SELECT doc FROM EmployeeRefDocuments doc";
		return entityManager.createQuery(query).getResultList();
	}

	// Retrieves the index of the next reference document.
	@Override
	public int getIndex() {
		logger.info("Getting the index of the next reference document");
		String query = "SELECT MAX(doc.id) FROM EmployeeRefDocuments doc";
		Integer maxId = entityManager.createQuery(query, Integer.class).getSingleResult();
		return maxId != null ? maxId + 1 : 1;
	}

}
