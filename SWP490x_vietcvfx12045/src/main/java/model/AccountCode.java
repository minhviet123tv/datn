package model;

public class AccountCode {
	private int id_account_code;
	private String name_en, name_vn, condition_up, status, notes;
	
	public AccountCode () {}

	public AccountCode(int id_account_code, String name_en, String name_vn, String condition_up, String status,
			String notes) {
		super();
		this.id_account_code = id_account_code;
		this.name_en = name_en;
		this.name_vn = name_vn;
		this.condition_up = condition_up;
		this.status = status;
		this.notes = notes;
	}

	public int getId_account_code() {
		return id_account_code;
	}

	public void setId_account_code(int id_account_code) {
		this.id_account_code = id_account_code;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_vn() {
		return name_vn;
	}

	public void setName_vn(String name_vn) {
		this.name_vn = name_vn;
	}

	public String getCondition_up() {
		return condition_up;
	}

	public void setCondition_up(String condition_up) {
		this.condition_up = condition_up;
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
