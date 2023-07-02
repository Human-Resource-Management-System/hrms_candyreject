
package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import DAO_Interfaces.EmploymentInductionDocumentDAO;
import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;

@Component
public class EmploymentInductionDocDAOImpl implements EmploymentInductionDocumentDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private final Logger logger = LoggerFactory.getLogger(EmploymentInductionDocDAOImpl.class);

    // Adds an employment induction document to the database.
	@Transactional
	public void addEmploymentInductionDocument(EmploymentInductionDocument document) {
		logger.info("Adding employment induction document");
		entityManager.persist(document);
	}

	// Retrieves an employment induction document based on the specified document index.
	@Transactional
	public EmploymentInductionDocument getEmploymentInductionDocument(int documentIndex) {
		logger.info("Getting employment induction document for index");
		return entityManager.find(EmploymentInductionDocument.class, documentIndex);
	}

	// Retrieves a list of all employment induction documents.
	@Override
	public List<EmploymentInductionDocumentViewModel> getAllDocuments() {
		logger.info("Getting all employment induction documents");
		String queryString = "SELECT e.emplid, e.emplidty, e.documentData, e.verified FROM EmploymentInductionDocument e WHERE e.emplid IS NOT NULL AND e.emplidty IS NOT NULL AND e.documentData IS NOT NULL AND e.verified IS NOT NULL";
		Query query = entityManager.createQuery(queryString);
		// return (List<EmploymentInductionDocumentViewModel>) query.getResultList();

		List<Object[]> results = query.getResultList();
		List<EmploymentInductionDocumentViewModel> documents = new ArrayList<>();

		for (Object[] result : results) {
			int emplid = (int) result[0];
			int emid_idty_id = (int) result[1];
			String documentData = (String) result[2];
			String verified = (String) result[3];

			EmploymentInductionDocumentViewModel document = new EmploymentInductionDocumentViewModel(emplid,
					emid_idty_id, documentData, verified);
			documents.add(document);
		}

		return documents;
	}

}
