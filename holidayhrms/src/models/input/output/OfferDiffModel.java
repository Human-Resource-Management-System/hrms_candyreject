package models.input.output;

public class OfferDiffModel {
	private Integer offerId;
	private String name;
	private String status;

	public OfferDiffModel(Integer offerId, String name, String status) {
		this.offerId = offerId;
		this.name = name;
		this.status = status;
	}

	public OfferDiffModel() {

	}

	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OfferDiffModel [offerId=" + offerId + ", status=" + status + "]";
	}

}