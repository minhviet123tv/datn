package model;

public class CampaignCode {
	private int id_campaign_code;
	private String name_characters, name_vn, status, notes;
	
	public CampaignCode () {
		
	}

	public CampaignCode(int id_campaign_code, String name_characters, String name_vn, String status, String notes) {
		super();
		this.id_campaign_code = id_campaign_code;
		this.name_characters = name_characters;
		this.name_vn = name_vn;
		this.status = status;
		this.notes = notes;
	}

	public int getId_campaign_code() {
		return id_campaign_code;
	}

	public void setId_campaign_code(int id_campaign_code) {
		this.id_campaign_code = id_campaign_code;
	}

	public String getName_characters() {
		return name_characters;
	}

	public void setName_characters(String name_characters) {
		this.name_characters = name_characters;
	}

	public String getName_vn() {
		return name_vn;
	}

	public void setName_vn(String name_vn) {
		this.name_vn = name_vn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
