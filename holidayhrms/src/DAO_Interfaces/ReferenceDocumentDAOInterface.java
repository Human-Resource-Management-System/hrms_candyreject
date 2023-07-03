
package DAO_Interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import models.EmployeeRefDocuments;

@Repository
public interface ReferenceDocumentDAOInterface {

	EmployeeRefDocuments findById(int id);

	void save(EmployeeRefDocuments document);

	List<EmployeeRefDocuments> getAllDocs();

	int getIndex();

	void deleteById(int id);

	int getDOCIndex(String docname);

}