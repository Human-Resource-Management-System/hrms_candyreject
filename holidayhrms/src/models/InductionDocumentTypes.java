
package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hrms_inductiondocumenttypes")
public class InductionDocumentTypes {

	@Id
	@Column(name = "idty_id")
	private int idtyId;

	@Column(name = "idty_code")
	private String idtyCode;

	@Column(name = "idty_title")
	private String idtyTitle;

	@Column(name = "idty_desc")
	private String idtyDescription;

	// Constructors, getters, and setters

	public InductionDocumentTypes() {
	}

	public InductionDocumentTypes(int idtyId, String idtyCode, String idtyTitle, String idtyDescription) {
		this.idtyId = idtyId;
		this.idtyCode = idtyCode;
		this.idtyTitle = idtyTitle;
		this.idtyDescription = idtyDescription;
	}

	// Getters and setters

	public int getIdtyId() {
		return idtyId;
	}

	public void setIdtyId(int idtyId) {
		this.idtyId = idtyId;
	}

	public String getIdtyCode() {
		return idtyCode;
	}

	public void setIdtyCode(String idtyCode) {
		this.idtyCode = idtyCode;
	}

	public String getIdtyTitle() {
		return idtyTitle;
	}

	public void setIdtyTitle(String idtyTitle) {
		this.idtyTitle = idtyTitle;
	}

	public String getIdtyDescription() {
		return idtyDescription;
	}

	public void setIdtyDescription(String idtyDescription) {
		this.idtyDescription = idtyDescription;
	}
}
