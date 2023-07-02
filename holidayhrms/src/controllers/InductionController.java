
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import DAO_Interfaces.InductionDAO;
import models.EmploymentInductionDocument;
import models.Induction;
import models.input.output.EmploymentInductionDocumentViewModel;
import models.input.output.SaveInductioninput;
import models.input.output.addinductionDOC;
import service_interfaces.EmploymentInductionDocumentServiceInterface;
import service_interfaces.EmploymentInductionServiceInterface;

@Controller
public class InductionController {
	
	private EmploymentInductionDocumentServiceInterface docServ; // injecting service class object
	private EmploymentInductionServiceInterface indServ; // injecting service class object
	private EmploymentInductionDocument document; // injecting Document Entity Model class object
	private InductionDAO idao;// injecting DAO class object
	private Induction induction;// injecting induction class object

	@Autowired
	public InductionController(EmploymentInductionDocumentServiceInterface docServ,
			EmploymentInductionServiceInterface indServ, EmploymentInductionDocument document, InductionDAO idao,
			Induction induction) {
		this.docServ = docServ;
		this.indServ = indServ;
		this.document = document;
		this.idao = idao;
		this.induction = induction;
	}

	
	private final Logger logger = LoggerFactory.getLogger(InductionController.class);

	@RequestMapping("/inductionlist") // view the list of inductions conducted
	public String showEmployees(Model model) {
		logger.info("Showing Employees.");
		List<Integer> inductions = idao.getAllInductions();
		model.addAttribute("inductions", inductions);
		logger.info("Moved to the INduction jsp page.");
		return "inductions"; // opens the inductions.jsp page
	}

	@RequestMapping("/get-induction-details") // shows the data regarding selected induction
	public String getEmployeeDetails(@RequestParam("id") int indid, Model model) {
		logger.info("Getting induction details for ID");
		logger.debug("Retrieving induction details for ID from the InductionDAO.");
		List<Induction> i = idao.getInductionById(indid);
		model.addAttribute("indid", i);
		model.addAttribute("ID", indid);
		logger.debug("Retrieved details for induction with ID");
		logger.info("Redirecting to the inductiondetails.jsp page.");
		return "inductiondetails"; // opens the inductiondetails.jsp page
	}

	@RequestMapping(value = "/inductioninsert", method = RequestMethod.GET) // to insert into induction
	public String createInduction(Model model) {
		logger.info("VIewing the induction form");
		logger.debug("Retrieving all employment offers from the EmploymentInductionService.");
		List<Integer> hd = idao.getAllEmploymentOffers();
		model.addAttribute("employmentOffers", hd);
		logger.info("Opening the Form to Create induction");
		return "createInduction"; // opens the createInduction.jsp page
	}

	@RequestMapping(value = "/inductionsave", method = RequestMethod.POST) // for saving the induction
	public String saveInduction(@ModelAttribute SaveInductioninput request, Model model) {
		// Map the properties from the input model to the entity model
		logger.info("Saving induction");
		List<Induction> inductions = new ArrayList<>(); // Create the Induction objects
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (Integer indcEmofId : request.getIndcEmofId()) {
			if (request.getIndcId().equals("same")) {
				induction.setIndcId(indServ.getid());
			} else {
				induction.setIndcId(indServ.getidNext());
			}
			induction.setIndcEmofId(indcEmofId);
			induction.setIndcProcessedAusrId(request.getIndcProcessedAusrId());
			induction.setIndcStatus(request.getIndcStatus());
			try {
				Date date = dateFormat.parse(request.getIndcDate());
				induction.setIndcDate(new java.sql.Date(date.getTime()));

			} catch (ParseException e) {
				System.out.println("Causing Error");
			}
			inductions.add(induction);
			idao.insertEmployee(induction);//
			idao.updateEmploymentOfferStatus(indcEmofId, "INDC");
		}
		List<Integer> induc = idao.getAllInductions();
		model.addAttribute("inductions", induc);
		logger.info("Successfully saved induction.");
		return "inductions";
	}

	@GetMapping("/getform") // previews the form to fill and upload document
	public String getform(Model model) {
		List<EmploymentInductionDocumentViewModel> doc = docServ.getAllDocuments();
		System.out.println(doc);
		model.addAttribute("doc", doc);
		logger.info("Getting induction Document Upload form");
		return "InductionDocument";
	}

	@GetMapping("/add") // to save the induction documents
	public String addDocument(@ModelAttribute addinductionDOC input) {
		document.setEmplid(input.getEmploymentOfferId());// employee offer id
		System.out.println(input.getEmploymentOfferId());
		document.setEmplidty(input.getDocumentTypeId());// employee offer document type setting
		document.setIndcProcessedAusrId(input.getProcessedUserId());
		document.setVerified(input.getVerified());
		String path = input.getDocumentData().getAbsolutePath();
		System.out.println("-----------------------" + path);
		document.setDocumentData(path);
		logger.info("Moving to EmploymentInductionDocumentService to Add induction document");
		// moves to the EmploymentInductionDocumentService class to insert document
		docServ.addEmploymentInductionDocument(document);
		logger.info("Added induction document so returning Success");
		return "success";
	}
}
