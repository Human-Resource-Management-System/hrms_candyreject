package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import DAO_Interfaces.InductionDAO;
import exceptions.CustomException;
import models.Candidate;
import models.Induction;
import models.input.output.CandidateDTO;

@Repository
public class InductionDAOImpl implements InductionDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(InductionDAOImpl.class);

	@Override
	@Transactional
	public List<Integer> getAllInductions() {// to get all the inductions list
		logger.info("Getting all inductions Conducted From DB");
		String query = "SELECT DISTINCT i.indcId FROM Induction i ORDER BY i.indcId DESC";
		logger.info("Retrieved Inductions");
		return entityManager.createQuery(query, Integer.class).getResultList();
	}

	@Override
	@Transactional
	public List<Induction> getInductionById(Integer id) {
		logger.info("Getting induction by ID: {}", id);
		TypedQuery<Induction> parameterQuery = entityManager
				.createQuery("SELECT i FROM Induction i WHERE i.indcId = :id", Induction.class);
		parameterQuery.setParameter("id", id);
		logger.info("Retrieved induction by ID from DB: {}", id);
		return parameterQuery.getResultList();
	}

	@Override
	@Transactional
	public void insertEmployee(Induction induction) throws CustomException {// to insert the selected into the induction
		if (induction == null) {
			throw new CustomException("Custom exception message");
		} else {
			logger.info("Inserting Offered Candidates into induction");
			entityManager.persist(induction);
			logger.info("Offered Candidates are inserted into induction successfully");
		}
	}

	@Override
	@Transactional //
	public List<Integer> getAllEmploymentOffers() {// to get the list of employeeoffer id inorder to select for the
													// inductions
		logger.info("Getting all employment offer IDs");
		String query = "SELECT o.candidateId FROM HrmsEmploymentOffer o WHERE o.status='INPR'";
		logger.info("Retrieved employment offer IDs");
		return entityManager.createQuery(query, Integer.class).getResultList();
	}

	@Override
	@Transactional
	public void updateEmploymentOfferStatus(int offerId, String status) {
		logger.info("Updating employment offer status, Offer ID: {}, Status: {} in the EmploymentOffer table", offerId,
				status);
		String query = "UPDATE HrmsEmploymentOffer SET eofr_status = :status WHERE candidateId = :offerId";
		entityManager.createQuery(query).setParameter("status", status).setParameter("offerId", offerId)
				.executeUpdate();
		logger.info("Employment offer status updated successfully");
	}

	@Override
	@Transactional
	public Integer getIndex() {
		logger.info("Getting maximum induction ID");
		String query = "SELECT MAX(ind.indcId) FROM Induction ind";
		TypedQuery<Integer> typedQuery = entityManager.createQuery(query, Integer.class);
		Integer maxId = typedQuery.getSingleResult();
		Integer newIndex = maxId != null ? maxId + 1 : 1;
		logger.info("Retrieved maximum induction ID: {}", newIndex);
		return newIndex;
	}

	@Override
	public Map<Integer, Integer> getEmployeeOfferedIdMaxMap(List<Integer> hd) {
		logger.info("Getting the count of Documents need to be Submitted by the candidate Specified in Offer Letter");
		Map<Integer, Integer> maxMap = new HashMap<>();
		for (Integer id : hd) {
			int count = getCountOfOfferIdentity(id);
			maxMap.put(id, count);
		}
		return maxMap;
	}

	@Override
	public int getCountOfOfferIdentity(int id) {
		logger.info("Getting count of offer identity for ID: {}", id);
		String query = "SELECT COUNT(e.offeridentity) FROM EmploymentOfferDocument e WHERE e.empoff.offerid = :id";
		TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
		typedQuery.setParameter("id", id);
		Long count = typedQuery.getSingleResult();
		int result = count != null ? count.intValue() : 0;
		logger.info("Retrieved count of offer identity: {}", result);
		return result;
	}

	@Override
	public Map<Integer, Integer> getEmploymentInductionDocCountMap(List<Integer> hd) {
		logger.info("Getting employment induction document count map");
		Map<Integer, Integer> countMap = new HashMap<>();
		for (Integer id : hd) {
			int count = getEmploymentInductionDocCount(id);
			countMap.put(id, count);
		}
		return countMap;
	}

	@Override
	public int getEmploymentInductionDocCount(int id) {
		logger.info("Getting employment induction document count for ID: {}", id);
		String query = "SELECT COUNT(e.emplidty) FROM EmploymentInductionDocument e WHERE e.id = :id";
		TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
		typedQuery.setParameter("id", id);
		Long count = typedQuery.getSingleResult();
		int result = count != null ? count.intValue() : 0;
		logger.info("Retrieved employment induction document count: {}", result);
		return result;
	}

	@Override
	public String getEmployeeOfferName(Integer id) {
		logger.info("Getting name of the candiate of  ID: {}", id);
		String query = "SELECT c.candFirstName FROM Candidate c WHERE c.candId = :id";
		return entityManager.createQuery(query, String.class).setParameter("id", id).getSingleResult();
	}

	@Override
	public List<CandidateDTO> getCandidatesForRejected() {
		logger.info("Getting all the Candidates to be rejected");
		String query = "SELECT NEW models.input.output.CandidateDTO(r.candId, r.candFirstName) FROM Candidate r WHERE r.candId IN (SELECT o.candidateId FROM HrmsEmploymentOffer o WHERE o.status ='INPR' AND o.candidateId = r.candId) ORDER BY r.candId DESC";
		logger.info("Retrieved Candidates for rejection");
		return entityManager.createQuery(query, CandidateDTO.class).getResultList();
	}

	@Override
	@Transactional
	public void updateCandiateStatus(int candidateId) {
		logger.info("Updating the status for candidate table as suspended for the gievn id  ; {}", candidateId);
		String jpql = "UPDATE Candidate c SET c.candStatus = :status WHERE c.candId = :candidateId";
		entityManager.createQuery(jpql).setParameter("status", "SU").setParameter("candidateId", candidateId)
				.executeUpdate();
	}

	@Override
	@Transactional
	public void updateOfferStatus(int candidateId) {
		logger.info("Updating the status for EmploymentOffer table as OfferRejected for the gievn id  ; {}",
				candidateId);
		String jpql = "UPDATE HrmsEmploymentOffer o SET o.status = :status WHERE o.candidateId = :candidateId";
		entityManager.createQuery(jpql).setParameter("status", "OFRJ").setParameter("candidateId", candidateId)
				.executeUpdate();
	}

	@Override
	@Transactional
	public List<Candidate> getRejectList() {
		logger.info("Getting the Rejection List");
		String query = "SELECT c FROM Candidate c WHERE c.candStatus = 'SU' ";
		return entityManager.createQuery(query, Candidate.class).getResultList();
	}

}