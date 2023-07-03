package service_interfaces;

import java.util.List;

import models.EmployeeRefDocuments;

public interface ReferenceDocumentServiceInterface {

	public EmployeeRefDocuments getReferenceDocumentById(int id);

	public void addReferenceDocument(EmployeeRefDocuments document);

	public void deleteReferenceDocument(String docname);

	public List<EmployeeRefDocuments> getAllDocuments();
}