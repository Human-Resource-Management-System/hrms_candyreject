package DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import DAO_Interfaces.JobGradeDAO;
import models.HrmsJobGrade;


@Repository
public class JobGradeDAOImpl implements JobGradeDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public HrmsJobGrade getJobGradeById(String jbgrId) {
		return entityManager.find(HrmsJobGrade.class, jbgrId);
	}
}
