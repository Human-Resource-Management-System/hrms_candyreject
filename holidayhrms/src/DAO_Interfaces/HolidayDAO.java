package DAO_Interfaces;

import java.util.List;

import models.GradeHoliday;
import models.Holiday;
import models.HrmsJobGrade;

public interface HolidayDAO {
	List<Holiday> findAllHolidays();

	GradeHoliday findHolidayById(String id);

	List<GradeHoliday> findAllGradeHolidays();

	List<Holiday> findAlloptedHolidays();

	int countMandHolidays();

	long getEmployeeoptionalholidaysCount(int id, int year);

	List<HrmsJobGrade> getAllJobGradesInfo();

	void saveJobGrade(HrmsJobGrade jobgrade);

	void saveJobGradeHoliday(GradeHoliday holiday);

	void updateJobGradeHoliday(GradeHoliday holiday);
}
