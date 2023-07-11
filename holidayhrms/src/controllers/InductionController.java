
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
import exceptions.CustomException;
import models.Candidate;
import models.EmploymentInductionDocument;
import models.Induction;
import models.input.output.CandidateDTO;
import models.input.output.EmploymentInductionDocumentViewModel;
import models.input.output.OfferDiffModel;
import models.input.output.SaveInductioninput;
import models.input.output.addinductionDOC;
import service_interfaces.EmploymentInductionDocumentServiceInterface;
import service_interfaces.EmploymentInductionServiceInterface;

@Controller

public class InductionController {

	private final EmploymentInductionDocumentServiceInterface docServ;
	private final EmploymentInductionServiceInterface indServ;
	private final EmploymentInductionDocument documentt;
	private final InductionDAO idao;
	private final OfferDiffModel offerDiff;
	private final Induction induction;
	private final Logger logger = LoggerFactory.getLogger(InductionController.class);

	@Autowired
	public InductionController(EmploymentInductionDocumentServiceInterface docServ,
			EmploymentInductionServiceInterface indServ, EmploymentInductionDocument documentt, InductionDAO idao,
			Induction induction, OfferDiffModel offerDiff) {
		this.docServ = docServ;
		this.indServ = indServ;
		this.documentt = documentt;
		this.idao = idao;
		this.induction = induction;
		this.offerDiff = offerDiff;
	}

	@RequestMapping("/inductionlist") // view the list of inductions conducted
	public String getOfferedCandidates(Model model) {
		logger.info("Showing Employees.");
		// logger.debug("Retrieving all inductions from the InductionDAO.");
		List<Integer> inductions = idao.getAllInductions();
		logger.error("Logging a sample exception", new Exception("Testing"));
		model.addAttribute("inductions", inductions);
		logger.info("Moved to the INduction jsp page.");
		return "inductions"; // opens the inductions.jsp page
	}

	@RequestMapping("/getinductiondetails") // shows the data regarding selected induction
	public String getCandidateDetails(@RequestParam("id") Integer indid, Model model) {
		logger.info("Getting induction details for ID: {}", indid);
		logger.debug("Retrieving induction details for ID: {} from the InductionDAO.", indid);
		List<Induction> i = idao.getInductionById(indid);
		model.addAttribute("indid", i);
		model.addAttribute("ID", indid);
		logger.debug("Retrieved details for induction with ID: {}", indid);
		logger.info("Redirecting to the inductiondetails.jsp page.");
		return "inductiondetails"; // opens the inductiondetails.jsp page
	}

	@RequestMapping(value = "/inductioninsert", method = RequestMethod.GET, produces = "text/html") // to insert into //
																									// induction
	public String createInduction(Model model) {
		logger.info("VIewing the induction form");
		logger.debug("Retrieving all employment offers from the EmploymentInductionService.");
		List<OfferDiffModel> diffmodel = indServ.getAllEmploymentOffers(); // moves to the InductionDAO class
		model.addAttribute("diffmodel", diffmodel);
		logger.info("Opening the Form to Create induction");
		return "createInduction"; // opens the createInduction.jsp page
	}

	@RequestMapping("/rejectedOfferList") // view the list of candidates to be rejected
	public String getRejectedCandidates(Model model) {
		logger.info("Showing  Candidates to be Rejected.");
		// logger.debug("Retrieving all inductions from the InductionDAO.");
		List<CandidateDTO> rejected = idao.getCandidatesForRejected();
		model.addAttribute("rejected", rejected);
		logger.info("Moved to the RejectedCandidates jsp page.");
		return "rejectedCandidates"; // opens the inductions.jsp page
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST) // view the list of inductions conducted
	public String getRejectedList(@RequestParam("candidateId") int candidateId, Model model) {
		logger.info("Showing OfferRejected/Suspended Canidates .");
		List<Candidate> rejectedList = indServ.getRejecetedList(candidateId);
		// for(Candidate c : rejectedList) {
		// System.out.println(c.getCandId());
		// }
		System.out.println(rejectedList.toString());
		model.addAttribute("rejectedList", rejectedList);
		logger.info("Moved to the Reject jsp page.");
		return "reject";
	}

	@RequestMapping(value = "/inductionsave", method = RequestMethod.POST) // for saving the induction
	public String saveInduction(@ModelAttribute SaveInductioninput request, Model model) throws CustomException {
		try {
			logger.info("Saving induction");
			List<Induction> inductions = new ArrayList<>(); // Create the Induction objects
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			induction.setIndcId(indServ.getid());
			logger.debug("Saving induction with ID: {}", induction.getIndcId());
			for (Integer indcEmofId : request.getIndcEmofId()) {
				induction.setIndcEmofId(indcEmofId);
				induction.setIndcProcessedAusrId(request.getIndcProcessedAusrId());
				String status = indServ.getStatusById(indcEmofId);
				String status1 = "Submitted";
				String status2 = "No document submitted";
				if (status1.equals(status)) {
					induction.setIndcStatus("PCMP");
				} else if (status2.equals(status)) {
					throw new CustomException(
							"Candidates who submitted NILL documents are not allowed for the induction");
				} else {
					induction.setIndcStatus("PEND");
				}
				try {
					if (request.getIndcDate() != null) {
						Date date = dateFormat.parse(request.getIndcDate());
						induction.setIndcDate(new java.sql.Date(date.getTime()));
					} else {
						model.addAttribute("errorMessage", "Date is required. Please enter a valid date.");
						return "ErrorInduction"; // Return the ErrorInduction view
					}
				} catch (ParseException e) {// Set an error message to be displayed to the user
					model.addAttribute("errorMessage", "Invalid date format. Please enter a valid date.");
					return "ErrorInduction"; // Return the ErrorInduction view
				}
				inductions.add(induction);
				// moves to the InductionDAO class to insert the candidates participated in the induction
				idao.insertEmployee(induction);
				// moves to the InductionDAO class to update the status in employeeoffer table
				idao.updateEmploymentOfferStatus(indcEmofId, "INDC");
			}
			logger.debug("Saved induction with ID: {}", induction.getIndcId());
			List<Integer> induc = idao.getAllInductions();// goes to the getAllInductions method in DAO class
			model.addAttribute("inductions", induc);
			return "inductions"; // opens the inductions.jsp page
		} catch (CustomException e) {
			logger.error("An error occurred: {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "ErrorInduction";
		}
	}

	@GetMapping("/getform") // previews the form to fill and upload document
	public String getInductionform(Model model) {

		// moves to the EmploymentInductionDocumentService class to get all document
		List<EmploymentInductionDocumentViewModel> doc = docServ.getAllDocuments();
		model.addAttribute("doc", doc);
		logger.info("Getting induction Document Upload form");
		return "InductionDocument"; // opens the InductionDocument jsp page
	}

	@GetMapping("/add") // to save the induction documents
	public String addInductionDocument(@ModelAttribute addinductionDOC input, Model model) {
		// Map the properties from the input model to the entity model
		boolean dt;
		try {
			dt = docServ.getDocType(input.getEmploymentOfferId(), input.getDocumentTypeId());
			if (dt) {
				documentt.setEmplid(input.getEmploymentOfferId());
				documentt.setEmplidty(input.getDocumentTypeId());
				documentt.setIndcProcessedAusrId(input.getProcessedUserId());
				documentt.setVerified(input.getVerified());
				String path = input.getDocumentData().getAbsolutePath();
				documentt.setDocumentData(path);
				logger.info("Moving to EmploymentInductionDocumentService to Add induction document");
				// moves to the EmploymentInductionDocumentService class to insert document
				docServ.addCandidateInductionDocument(documentt);
				logger.info("Added induction document so returning Success");
			}
		} catch (CustomException e) {
			logger.error("An error occurred: {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "ErrorInduction";
		}
		return "success";
	}
}