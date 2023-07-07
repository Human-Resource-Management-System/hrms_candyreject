package DAO_Interfaces;

import java.util.List;
import java.util.Map;

import exceptions.CustomException;
import models.Induction;

public interface InductionDAO {
	List<Integer> getAllInductions();

	/**
	 * Retrieves all induction IDs.
	 *
	 * @return A list of all induction IDs.
	 */
	public List<Induction> getInductionById(Integer id);

	/**
	 * Retrieves an induction by its ID.
	 *
	 * @param id The ID of the induction.
	 * @return A list of inductions matching the ID.
	 */
	public void insertEmployee(Induction induction) throws CustomException;

	/**
	 * Inserts an employee induction.
	 *
	 * @param induction The induction to be inserted.
	 * @throws CustomException If an exception occurs during insertion.
	 */
	public List<Integer> getAllEmploymentOffers();

	/**
	 * Retrieves all employment offer IDs.
	 *
	 * @return A list of all employment offer IDs.
	 */
	void updateEmploymentOfferStatus(int offerId, String status);

	/**
	 * Updates the status of an employment offer.
	 *
	 * @param offerId The ID of the employment offer.
	 * @param status  The new status of the employment offer.
	 */
	Integer getIndex();

	/**
	 * Retrieves the current index.
	 *
	 * @return The current index.
	 */
	Map<Integer, Integer> getEmployeeOfferedIdMaxMap(List<Integer> hd);

	/**
	 * Retrieves a mapping of employee offered ID to maximum value from a list of IDs.
	 *
	 * @param hd A list of IDs.
	 * @return A mapping of employee offered ID to maximum value.
	 */
	int getCountOfOfferIdentity(int id);

	/**
	 * Retrieves the count of offer identity for a given ID.
	 *
	 * @param id The ID of the offer.
	 * @return The count of offer identity.
	 */
	Map<Integer, Integer> getEmploymentInductionDocCountMap(List<Integer> hd);

	/**
	 * Retrieves a mapping of employment induction document count from a list of IDs.
	 *
	 * @param hd A list of IDs.
	 * @return A mapping of employment induction document count.
	 */
	int getEmploymentInductionDocCount(int id);

	/**
	 * Retrieves the employment induction document count for a given ID.
	 *
	 * @param id The ID of the employment induction document.
	 * @return The employment induction document count.
	 */

	String getEmployeeOfferName(Integer id);
}
