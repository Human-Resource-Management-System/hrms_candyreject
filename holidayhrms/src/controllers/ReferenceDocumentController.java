package controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import service_interfaces.ReferenceDocumentServiceInterface;

@Controller
public class ReferenceDocumentController {

	private static final Logger logger = LoggerFactory.getLogger(ReferenceDocumentController.class);
	private ReferenceDocumentServiceInterface rs;
	private EmployeeRefDocuments document;

	@Autowired
	public ReferenceDocumentController(ReferenceDocumentServiceInterface rs, EmployeeRefDocuments document) {
		this.rs = rs;
		this.document = document;
	}

	@RequestMapping(value = "/emprefDocuments", method = RequestMethod.GET)
	public String empViewDocuments(Model model) {
		logger.info("Viewing all documents");
		List<EmployeeRefDocuments> refdocs = rs.getAllDocuments();
		model.addAttribute("refdocs", refdocs);
		return "emprefdoclist";
	}

	@RequestMapping(value = "/viewDocuments", method = RequestMethod.GET)
	public String viewDocuments(Model model) {
		logger.info("Viewing all documents");
		List<EmployeeRefDocuments> refdocs = rs.getAllDocuments();
		model.addAttribute("refdocs", refdocs);
		return "documentlist";
	}

	@RequestMapping(value = "/addReferenceDocument", method = RequestMethod.GET)
	public String addReferenceDocument(Model model) {
		logger.info("Adding a new reference document");
		return "UploadReferenceDocument";
	}

	@RequestMapping(value = "/DocumentSave", method = RequestMethod.POST)
	public String saveDocument(@ModelAttribute DocumentInputModel dim, Model model, HttpServletRequest req) {
		logger.info("Saving a new document");
		EmployeeRefDocuments document = new EmployeeRefDocuments();
		MultipartFile documentData = dim.getDocumentData();
		String Docname = documentData.getOriginalFilename();// Extract the file name from the MultipartFile
		document.setDocName(Docname);
		document.setCategory(dim.getCategory());

		System.out.println("start");

		if (documentData != null && !documentData.isEmpty()) {
			try {

				System.out.println("setting path");
				ServletContext servletContext = req.getServletContext();

				byte[] documentBytes = documentData.getBytes();// Save the document to the specified file path
				String path = req.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "views"
						+ File.separator + "Files" + File.separator + dim.getDocumentData().getOriginalFilename();

				logger.info("File path : {}", path);

				document.setDescription(path);// Set the description as the file path
				FileOutputStream fos = new FileOutputStream(path);
				fos.write(documentBytes);
				fos.close();
				logger.info("File uploaded successfully");

				rs.addReferenceDocument(document);// Add the document to the database

				model.addAttribute("message", "Document uploaded successfully!");// Display success message
			} catch (IOException e) {
				model.addAttribute("error", "Failed to upload the document!");// Handle the exception
			}
		} else {
			model.addAttribute("error", "No document found to upload!");// Handle the case when no document is uploaded
		}
		logger.info("Success");
		logger.info("Viewing all documents");
		List<EmployeeRefDocuments> refdocs = rs.getAllDocuments();
		model.addAttribute("refdocs", refdocs);
		return "documentlist";
	}

	@RequestMapping(value = "/OpenDocument", method = RequestMethod.GET)
	public void openDocument(@RequestParam("docname") String docname, HttpServletResponse response,
			HttpServletRequest request) {
		logger.info("Opening document: {}", docname);
		// Construct the file path based on the selected docname
		String filePath = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "views"
				+ File.separator + "Files" + File.separator + docname;
		logger.info("File path : {}", filePath);

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
				System.out.println("No File exists");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteReferenceDocument", method = RequestMethod.GET)
	public String deleteReferenceDocument(@RequestParam("docname") String docname, Model model,
			HttpServletRequest request) {
		logger.info("Deleting reference document: {}", docname);
		rs.deleteReferenceDocument(docname);
		logger.info("Viewing all documents");
		List<EmployeeRefDocuments> refdocs = rs.getAllDocuments();
		model.addAttribute("refdocs", refdocs);
		return "documentlist";

	}
}
