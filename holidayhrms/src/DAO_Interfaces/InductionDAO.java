package DAO_Interfaces;

import java.util.List;

import models.Induction;

public interface InductionDAO {
	/**
	 * Retrieves a list of all induction IDs.
	 *
	 * @return the list of all induction IDs
	 */
	List<Integer> getAllInductions();
	
	/**
	 * Retrieves a list of induction objects based on the specified ID.
	 *
	 * @param id the ID of the induction
	 * @return the list of induction objects matching the ID
	 */
	public List<Induction> getInductionById(int id);
	
	/**
	 * Inserts a new employee induction into the database.
	 *
	 * @param induction the induction object to be inserted
	 */
	public void insertEmployee(Induction induction);
	
	/**
	 * Retrieves a list of all employment offer IDs.
	 *
	 * @return the list of all employment offer IDs
	 */
	public List<Integer> getAllEmploymentOffers();
	/**
	 * Updates the status of an employment offer with the specified ID.
	 *
	 * @param offerId the ID of the employment offer
	 * @param status  the new status of the employment offer
	 */
	void updateEmploymentOfferStatus(int offerId, String status);
	/**
	 * Retrieves the next available index for an induction.
	 *
	 * @return the next available index for an induction
	 */
	int getIndex();

}
