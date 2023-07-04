package controllers;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import DAO_Interfaces.CandidateDAO;
import models.Candidate;
import models.input.output.CandidateIO;

@Controller
public class CandidateController {
	private CandidateDAO candidateDAO;
	private final ModelMapper modelMapper;
	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);
	private Candidate cd;

	public CandidateController(CandidateDAO candidateDAO, ModelMapper mp, Candidate candidates) {
		this.candidateDAO = candidateDAO;
		modelMapper = mp;
		cd = candidates;

	}

	@RequestMapping("/viewcandidates")
	public String showCandidateList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		int pageSize = 10; // Number of records to display per page
		List<Candidate> candidates = candidateDAO.getAllCandidates();
		int totalCandidates = candidates.size();
		int totalPages = (int) Math.ceil(totalCandidates / (double) pageSize);

		// Calculate the start and end indexes for the current page
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, totalCandidates);

		List<Candidate> candidatesOnPage = candidates.subList(startIndex, endIndex);
		List<CandidateIO> candidateOutputs = modelMapper.map(candidatesOnPage, new TypeToken<List<CandidateIO>>() {
		}.getType());

		model.addAttribute("candidates", candidateOutputs);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);

		return "candidateview";
	}

	// To display candidate details by id
	@GetMapping("/viewcandidate")
	public String getCandidateDetails(@RequestParam("id") int candidateId, Model model) {
		Candidate candidate = candidateDAO.getCandidateById(candidateId);
		CandidateIO cout = modelMapper.map(candidate, new TypeToken<CandidateIO>() {
		}.getType());
		if (candidate != null) {
			model.addAttribute("candidate", cout);
		} else {
			model.addAttribute("error", "No candidate found with the provided ID.");
		}
		logger.info("Showing Candidate Details By Id!!");
		return "viewcandidate";
	}

	// To insert a new candidate
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String addCandidates(Model model) {
		model.addAttribute("candidate", cd);
		return "candidate";
	}

	@RequestMapping(value = "/candidateadded", method = RequestMethod.POST)
	public String listOfCandidatesAfterInsertion(@Valid @ModelAttribute Candidate cand, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("candidate", cand);
			return "candidate";
		}

		candidateDAO.saveCandidate(cand);
		List<Candidate> candidates = candidateDAO.getAllCandidates();
		List<CandidateIO> candidateOutputs = modelMapper.map(candidates, new TypeToken<List<CandidateIO>>() {
		}.getType());

		model.addAttribute("candidates", candidateOutputs);
		return "update";
	}
}
