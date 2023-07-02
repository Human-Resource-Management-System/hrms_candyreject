package service_interfaces;

import java.util.List;

import models.EmployeeRefDocuments;

public interface ReferenceServiceInterface {

	/**
	 * Retrieves a reference document by its ID.
	 *
	 * @param id the ID of the reference document
	 * @return the reference document with the specified ID, or null if not found
	 */
	public EmployeeRefDocuments getReferenceDocumentById(String id);

	/**
	 * Adds a new reference document.
	 *
	 * @param document the reference document to be added
	 * @param id       the ID to assign to the new document
	 */
	public void addReferenceDocument(EmployeeRefDocuments document,int id);

	/**
	 * Deletes a reference document by its name.
	 *
	 * @param docname the name of the reference document to be deleted
	 */
	public void deleteReferenceDocument(String docname);

	/**
	 * Retrieves all reference documents.
	 *
	 * @return a list of all reference documents
	 */
	public List<EmployeeRefDocuments> getAllDocuments();
}
