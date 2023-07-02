
package controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import models.EmployeeRefDocuments;
import models.input.output.DocumentInputModel;
import service_interfaces.ReferenceServiceInterface;

@Controller
public class ReferenceDocumentController {

	private ReferenceServiceInterface rs;// service interface
	private EmployeeRefDocuments doc;// Entity model class
	
	@Autowired
	public ReferenceDocumentController(ReferenceServiceInterface rs, EmployeeRefDocuments doc) {
		this.rs = rs;
		this.doc = doc;
	}
	
	private final Logger logger = LoggerFactory.getLogger(ReferenceDocumentController.class);


	@RequestMapping(value = "/viewDocuments", method = RequestMethod.GET) // To view the list of all documents at admin
																			// side
	public String viewDocuments(Model model) {
		logger.info("Request received to view all documents");
		List<EmployeeRefDocuments> document = rs.getAllDocuments();
		model.addAttribute("document", document);
		return "documentlist";
	}

	@RequestMapping(value = "/emprefDocuments", method = RequestMethod.GET) // to view documents employside which are
																			// uploaded by admin
	public String viewReferenceDocuments(Model model) {
		logger.info("Request received to view reference documents");
		List<EmployeeRefDocuments> document = rs.getAllDocuments();
		model.addAttribute("document", document);
		return "emprefdoclist";
	}

	@RequestMapping(value = "/addReferenceDocument", method = RequestMethod.GET) // to upload the document by admin
	public String addReferenceDocument(Model model) {
		logger.info("Request received to add a reference document");
		return "UploadReferenceDocument";
	}

	@RequestMapping(value = "/DocumentSave", method = RequestMethod.POST) // to save the doc
	public String saveDocument(@ModelAttribute DocumentInputModel dim, Model model, HttpServletRequest req,HttpSession session) {
		logger.info("Request received to save a document");
		EmployeeRefDocuments document = new EmployeeRefDocuments();
		MultipartFile documentData = dim.getDocumentData();
		String Docname = documentData.getOriginalFilename();// Extract the file name from the MultipartFile
		document.setDocName(Docname);
		document.setCategory(dim.getCategory());

		System.out.println("start");

		if (documentData != null && !documentData.isEmpty()) {
			try {

				System.out.println("setting path");

				byte[] documentBytes = documentData.getBytes();// Save the document to the specified file path
				String path = req.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "views"
						+ File.separator + "Files" + File.separator + dim.getDocumentData().getOriginalFilename();

				System.out.println(path);

				document.setDescription(path);// Set the description as the file path
				FileOutputStream fos = new FileOutputStream(path);
				fos.write(documentBytes);
				fos.close();

				int id = (int) session.getAttribute("adminId");

				rs.addReferenceDocument(document,id);// Add the document to the database

				model.addAttribute("message", "Document uploaded successfully!");// Display success message
			} catch (IOException e) {
				model.addAttribute("error", "Failed to upload the document!");// Handle the exception
			}
		} else {
			model.addAttribute("error", "No document found to upload!");// Handle the case when no document is uploaded
		}
		System.out.println("success");
		return "success";
	}

	@RequestMapping(value = "/OpenDocument", method = RequestMethod.GET) // to open the selected document from the list
	public void openDocument(@RequestParam("docname") String docname, HttpServletResponse response,
			HttpServletRequest request) {
		logger.info("Request received to open a document");
		// Construct the file path based on the selected docname
		String filePath = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "views"
				+ File.separator + "Files" + File.separator + docname;

		System.out.println(filePath);

		try {
			File file = new File(filePath);
			System.out.println("In here");

			if (file.exists()) {
				String contentType = request.getServletContext().getMimeType(docname);
				response.setContentType(contentType);
				response.setHeader("Content-Disposition", "inline; filename=\"" + docname + "\"");

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				OutputStream outputStream = response.getOutputStream();

				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outputStream.flush();
				outputStream.close();

			} else {
				logger.warn("File does not exist");
			}
		} catch (IOException e) {
			// Handle the exception
	        logger.error("Error opening document: {}", e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteReferenceDocument", method = RequestMethod.GET) // to delete the document by admin
	public String deleteReferenceDocument(@RequestParam("docname") String docname, Model model,
			HttpServletRequest request) {
		logger.info("Request received to delete a reference document");
		rs.deleteReferenceDocument(docname);
		return "documentList";
	}
}