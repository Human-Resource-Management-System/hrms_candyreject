package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import DAO_Interfaces.InductionDAO;
import models.Induction;

@Repository
public class InductionDAOImpl implements InductionDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final Logger logger = LoggerFactory.getLogger(InductionDAOImpl.class);

	// Retrieves a list of all induction IDs.
	@Override
	@Transactional
	public List<Integer> getAllInductions() {
		 logger.info("Getting all inductions");
		String query = "SELECT DISTINCT i.indcId FROM Induction i ORDER BY i.indcId DESC";
		return entityManager.createQuery(query, Integer.class).getResultList();
	}

	//Retrieves a list of induction objects based on the specified ID.
	@Override
	@Transactional
	public List<Induction> getInductionById(int id) {
		 logger.info("Getting induction by id");
		Query query = entityManager.createQuery("SELECT i FROM Induction i WHERE i.indcId = :id");
		query.setParameter("id", id);

		return (List<Induction>) query.getResultList();
	}

	@Override
	@Transactional
	// Inserts a new employee induction into the database.
	public void insertEmployee(Induction induction) {
		logger.info("Inserting employee induction");
		entityManager.persist(induction);

	}

	// Retrieves a list of all employment offer IDs.
	@Override
	@Transactional
	public List<Integer> getAllEmploymentOffers() {
		  logger.info("Getting all employment offers");
		String query = "SELECT o.candidateId FROM HrmsEmploymentOffer o WHERE o.status='INPR'";
		return entityManager.createQuery(query, Integer.class).getResultList();
	}

	// Updates the status of an employment offer with the specified ID.
	@Override
	@Transactional
	public void updateEmploymentOfferStatus(int offerId, String status) {
        logger.info("Updating employment offer status.");
		String query = "UPDATE HrmsEmploymentOffer SET eofr_status = :status WHERE candidateId = :offerId";
		entityManager.createQuery(query).setParameter("status", status).setParameter("offerId", offerId)
				.executeUpdate();
	}

	 // Retrieves the next available index for an induction.
	@Override
	public int getIndex() {
		logger.info("Getting the maximum index");
		String query = "SELECT MAX(ind.id) FROM Induction ind";
		Integer maxId = entityManager.createQuery(query, Integer.class).getSingleResult();
		return maxId != null ? maxId + 1 : 1;
	}

}
