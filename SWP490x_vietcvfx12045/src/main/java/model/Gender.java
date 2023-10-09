package model;

public class Gender {
	private int id_gender, status;
	private String gender_vn, gender_en, notes;
	
	public Gender() {
		
	}

	public Gender(int id_gender, int status, String gender_vn, String gender_en, String notes) {
		super();
		this.id_gender = id_gender;
		this.status = status;
		this.gender_vn = gender_vn;
		this.gender_en = gender_en;
		this.notes = notes;
	}

	public int getId_gender() {
		return id_gender;
	}

	public void setId_gender(int id_gender) {
		this.id_gender = id_gender;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getGender_vn() {
		return gender_vn;
	}

	public void setGender_vn(String gender_vn) {
		this.gender_vn = gender_vn;
	}

	public String getGender_en() {
		return gender_en;
	}

	public void setGender_en(String gender_en) {
		this.gender_en = gender_en;
	}

	public String getNote() {
		return notes;
	}

	public void setNote(String note) {
		this.notes = note;
	}

}
