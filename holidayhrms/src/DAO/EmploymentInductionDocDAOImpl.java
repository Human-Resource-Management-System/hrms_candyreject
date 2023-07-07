
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
import controllers.InductionController;
import models.EmploymentInductionDocument;
import models.input.output.EmploymentInductionDocumentViewModel;

@Component
public class EmploymentInductionDocDAOImpl implements EmploymentInductionDocumentDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(InductionController.class);

	@Override
	@Transactional
	public void addEmploymentInductionDocument(EmploymentInductionDocument documentt) {
		// to add Documents submitted at induction into the EmploymentInductionDocument table
		logger.info("Adding induction document");
		entityManager.persist(documentt);
		logger.info("Induction document added successfully");
	}

	@Override
	public List<EmploymentInductionDocumentViewModel> getAllDocuments() {
		// to get all the Documents submitted at induction from the EmploymentInductionDocument table
		String queryString = "SELECT e.emplid, e.emplidty, e.documentData, e.verified, i.idtyTitle FROM EmploymentInductionDocument e JOIN InductionDocumentTypes i ON e.emplidty = i.idtyId WHERE e.emplid IS NOT NULL AND e.emplidty IS NOT NULL AND e.documentData IS NOT NULL AND e.verified IS NOT NULL ORDER BY e.emplid, e.emplidty";

		Query query = entityManager.createQuery(queryString);
		// return (List<EmploymentInductionDocumentViewModel>) query.getResultList();

		List<Object[]> results = query.getResultList();
		List<EmploymentInductionDocumentViewModel> documents = new ArrayList<>();

		for (Object[] result : results) {
			int emplid = (int) result[0];
			int emid_idty_id = (int) result[1];
			String documentData = (String) result[2];
			String verified = (String) result[3];
			String Title = (String) result[4];
			EmploymentInductionDocumentViewModel document = new EmploymentInductionDocumentViewModel(emplid,
					emid_idty_id, documentData, verified, Title);
			documents.add(document);
		}
		logger.info("Retrieved {} induction documents", documents.size());
		return documents;
	}

	@Override
	public List<Integer> getDocTypeList(int employmentOfferId) {
		String query = "SELECT e.offeridentity FROM EmploymentOfferDocument e WHERE e.empoff.offerid = :employmentOfferId";
		return entityManager.createQuery(query, Integer.class).setParameter("employmentOfferId", employmentOfferId)
				.getResultList();
	}

}
