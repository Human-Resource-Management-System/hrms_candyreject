package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import DAO_Interfaces.HolidayDAO;
import models.GradeHoliday;
import models.Holiday;
import models.HrmsJobGrade;

@Repository
public class HolidayDAOImpl implements HolidayDAO {

	private static final Logger logger = LoggerFactory.getLogger(HolidayDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<Holiday> findAllHolidays() {
		logger.info("Finding all holidays");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Holiday> cq = cb.createQuery(Holiday.class);
		Root<Holiday> root = cq.from(Holiday.class);
		cq.select(root);
		cq.orderBy(cb.asc(root.get("hday_date")));
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public GradeHoliday findHolidayById(String id) {
		logger.info("Finding holiday by ID: {}", id);
		return entityManager.find(GradeHoliday.class, id);
	}

	@Override
	@Transactional
	public List<GradeHoliday> findAllGradeHolidays() {
		logger.info("Finding all grade holidays");
		TypedQuery<GradeHoliday> query = entityManager.createQuery("SELECT gh FROM GradeHoliday gh",
				GradeHoliday.class);
		return query.getResultList();
	}

	@Override
	@Transactional
	public List<Holiday> findAlloptedHolidays() {
		logger.info("Finding all opted holidays");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Holiday> cq = cb.createQuery(Holiday.class);
		Root<Holiday> root = cq.from(Holiday.class);
		cq.select(root);
		cq.where(cb.equal(root.get("hday_type"), "OPTN"));
		TypedQuery<Holiday> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	@Override
	@Transactional
	public int countMandHolidays() {
		logger.info("Counting mandatory holidays");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Holiday> root = cq.from(Holiday.class);
		cq.select(cb.count(root));
		cq.where(cb.equal(root.get("hday_type"), "MAND"));
		TypedQuery<Long> query = entityManager.createQuery(cq);
		Long count = query.getSingleResult();
		return count.intValue();
	}

	@Override
	public long getEmployeeoptionalholidaysCount(int id, int year) {
		logger.info("Getting employee optional holidays count for employee ID: {} and year: {}", id, year);
		String jpqlQuery = "SELECT COUNT(e) FROM EmployeeOptedLeaves e WHERE e.optedleavesId.employeeId = :employeeId AND  EXTRACT(YEAR FROM e.optedleavesId.holidayDate) = :year";
		TypedQuery<Long> query = entityManager.createQuery(jpqlQuery, Long.class);
		query.setParameter("employeeId", id);
		query.setParameter("year", year);
		Long count = query.getSingleResult();
		logger.info("Employee optional holidays count: {}", count);
		return count;
	}

	@Override
	public List<HrmsJobGrade> getAllJobGradesInfo() {
		logger.info("Getting all job grades information");
		TypedQuery<HrmsJobGrade> query = entityManager.createQuery("SELECT jg FROM HrmsJobGrade jg",
				HrmsJobGrade.class);
		return query.getResultList();
	}

	@Override
	public void saveJobGrade(HrmsJobGrade jobgrade) {
		logger.info("Saving job grade: {}", jobgrade);
		entityManager.persist(jobgrade);
	}

	@Override
	public void saveJobGradeHoliday(GradeHoliday holiday) {
		logger.info("Saving job grade holiday: {}", holiday);
		entityManager.persist(holiday);
	}

	@Override
	public void updateJobGradeHoliday(GradeHoliday holiday) {
		logger.info("Updating job grade holiday: {}", holiday);
		GradeHoliday holidaydata = entityManager.find(GradeHoliday.class, holiday.getJbgr_id());
		holidaydata.setJbgr_totalnoh(holiday.getJbgr_totalnoh());
		entityManager.merge(holidaydata);
	}

}
