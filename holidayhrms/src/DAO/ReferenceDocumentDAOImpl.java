
package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import DAO_Interfaces.ReferenceDocumentDAOInterface;
import models.EmployeeRefDocuments;

@Repository
public class ReferenceDocumentDAOImpl implements ReferenceDocumentDAOInterface {

	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger logger = LoggerFactory.getLogger(ReferenceDocumentDAOImpl.class);

	@Override
	@Transactional
	public EmployeeRefDocuments findById(int id) {
		logger.info("Finding document by id: {}", id);
		return entityManager.find(EmployeeRefDocuments.class, id);
	}

	@Override
	@Transactional
	public void save(EmployeeRefDocuments document) {
		logger.info("Saving document: {}", document.getId());
		entityManager.persist(document);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		logger.info("Deleting document by id: {}", id);
		EmployeeRefDocuments document = entityManager.find(EmployeeRefDocuments.class, id);
		entityManager.remove(document);
	}

	@Override
	@Transactional
	public List<EmployeeRefDocuments> getAllDocs() {
		logger.info("Retrieving all documents");
		String query = "SELECT doc FROM EmployeeRefDocuments doc";
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public int getIndex() {
		logger.info("Getting maximum document ID");
		String query = "SELECT MAX(doc.id) FROM EmployeeRefDocuments doc";
		Integer maxId = entityManager.createQuery(query, Integer.class).getSingleResult();
		return maxId != null ? maxId + 1 : 1;
	}

	@Override
	public int getDOCIndex(String docname) {
		String queryString = "SELECT d.id FROM models.EmployeeRefDocuments d WHERE d.docName = :docname ORDER BY d.id";
		TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
		query.setParameter("docname", docname);
		query.setMaxResults(1);
		Integer result = query.getSingleResult();
		int res = result.intValue();
		return res;
	}

}
