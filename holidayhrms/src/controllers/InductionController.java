
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private EmploymentInductionDocumentServiceInterface docServ; // injecting service class object

	@Autowired
	private EmploymentInductionServiceInterface indServ; // injecting service class object

	@Autowired
	private EmploymentInductionDocument documentt; // injecting Document Entity Model class object

	@Autowired
	private InductionDAO idao;// injecting DAO class object

	@Autowired
	private Induction induction;// injecting induction class object

	@RequestMapping("/inductionlist") // view the list of inductions conducted
	public String showEmployees(Model model) {
		List<Integer> inductions = idao.getAllInductions();
		model.addAttribute("inductions", inductions);
		return "inductions";
	}

	@RequestMapping("/get-induction-details") // shows the data regarding selected induction
	public String getEmployeeDetails(@RequestParam("id") int indid, Model model) {
		System.out.println(indid);
		List<Induction> i = idao.getInductionById(indid);
		model.addAttribute("indid", i);
		model.addAttribute("ID", indid);
		return "inductiondetails";
	}

	@RequestMapping(value = "/inductioninsert", method = RequestMethod.GET) // to insert into induction
	public String createInduction(Model model) {
		List<Integer> hd = idao.getAllEmploymentOffers();
		model.addAttribute("employmentOffers", hd);
		return "createInduction";
	}

	@RequestMapping(value = "/inductionsave", method = RequestMethod.POST) // for saving the induction
	public String saveInduction(@ModelAttribute SaveInductioninput request, Model model) {
		// Map the properties from the input model to the entity model
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
		return "inductions";
	}

	@GetMapping("/getform") // previews the form to fill and upload document
	public String getform(Model model) {
		List<EmploymentInductionDocumentViewModel> doc = docServ.getAllDocuments();
		System.out.println(doc);
		model.addAttribute("doc", doc);
		return "InductionDocument";
	}

	@GetMapping("/add") // to save the induction documents
	public String addDocument(@ModelAttribute addinductionDOC input) {
		documentt.setEmplid(input.getEmploymentOfferId());// employee offer id
		System.out.println(input.getEmploymentOfferId());
		documentt.setEmplidty(input.getDocumentTypeId());// employee offer document type setting
		documentt.setIndcProcessedAusrId(input.getProcessedUserId());
		documentt.setVerified(input.getVerified());
		String path = input.getDocumentData().getAbsolutePath();
		System.out.println("-----------------------" + path);
		documentt.setDocumentData(path);
		docServ.addEmploymentInductionDocument(documentt);
		return "success";
	}
}