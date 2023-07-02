package service;

import org.springframework.beans.factory.annotation.Autowired;

import DAO_Interfaces.InductionDAO;
import service_interfaces.EmploymentInductionServiceInterface;

public class EmploymentInductionService implements EmploymentInductionServiceInterface {

	@Autowired
	private InductionDAO idao;// injecting DAO class object

	// Retrieves the current induction ID.
	public int getid() {
		return idao.getIndex();
	}

	// Retrieves the next induction ID.
	public int getidNext() {

		int i = idao.getIndex();
		return i + 1;
	}

}
