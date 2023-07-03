
package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import DAO_Interfaces.CandidateDAO;
import models.Candidate;

@Repository
public class CandidateDAOImpl implements CandidateDAO {

	@PersistenceContext
	private EntityManager entityManager;
	int eofrId;
	Candidate cann;

	@Override
	@Transactional
	public void saveCandidate(Candidate candidate) {
		entityManager.persist(candidate);
	}

	@Override
	@Transactional
	public Candidate getCandidateById(int candidateId) {
		return entityManager.find(Candidate.class, candidateId);
	}

	@Override
	@Transactional
	public List<Candidate> getAllCandidates() {
		String query = "SELECT c FROM Candidate c";
		return entityManager.createQuery(query, Candidate.class).getResultList();
	}

}
