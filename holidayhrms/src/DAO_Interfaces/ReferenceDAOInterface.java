
package DAO_Interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import models.EmployeeRefDocuments;

@Repository
public interface ReferenceDAOInterface {

	/**
	Retrieves a reference document by its ID.
	@param id The ID of the reference document to retrieve.
	@return The reference document with the specified ID, or null if not found.
	*/
	EmployeeRefDocuments findById(String id);

	/**
	Saves a reference document in the data source.
	@param document The reference document to save.
	*/
	void save(EmployeeRefDocuments document);

	/**
	Retrieves all reference documents from the data source.
	@return A list of all reference documents.
	*/
	List<EmployeeRefDocuments> getAllDocs();

	/**
	Retrieves the index of the next reference document.
	@return The index of the next reference document.
	*/
	int getIndex();

	/**
	Deletes a reference document from the data source by its ID.
	@param id The ID of the reference document to delete.
	*/
	void deleteById(int id);

}