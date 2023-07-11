package models.input.output;

public class CandidateDTO {
	private int candidateId;
	private String candidateFirstName;

	public CandidateDTO(int candidateId, String candidateFirstName) {
		this.candidateId = candidateId;
		this.candidateFirstName = candidateFirstName;
	}

	// Getters and setters

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateFirstName() {
		return candidateFirstName;
	}

	public void setCandidateFirstName(String candidateFirstName) {
		this.candidateFirstName = candidateFirstName;
	}
}
