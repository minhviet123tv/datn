package model;

public class AccountStatus {
	private int status_code;
	private String status_name, notes;
	
	public AccountStatus() { }

	public AccountStatus(int status_code, String status_name, String notes) {
		super();
		this.status_code = status_code;
		this.status_name = status_name;
		this.notes = notes;
	}

	public int getStatus_code() {
		return status_code;
	}

	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
