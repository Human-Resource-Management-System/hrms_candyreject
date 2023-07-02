package service_interfaces;

public interface EmploymentInductionServiceInterface {

	/**
	 * Retrieves the next induction ID.
	 *
	 * @return the next induction ID
	 */
	int getidNext();

	/**
	 * Retrieves the current induction ID.
	 *
	 * @return the current induction ID
	 */
	int getid();

}
