package models.input.output;

public class OfferDiffModel {
	private Integer offerId;
	private String status;

	public OfferDiffModel(Integer offerId, String status) {
		this.offerId = offerId;
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