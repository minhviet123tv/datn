package model;

import java.util.Date;

public class View1Account {
	private int id_account, id_account_code, status, gender;
	private String account_level, email, password, account_name, phone, province_code, province_name, status_name, gender_name;
	private Date register_date, last_login_date;
	private long sum_donate_account;
	
	public View1Account () {
		
	}

	public View1Account(int id_account, int id_account_code, int status, int gender, String account_level, String email,
			String password, String account_name, String phone, String province_code, String province_name,
			String status_name, String gender_name, Date register_date, Date last_login_date, long sum_donate_account) {
		super();
		this.id_account = id_account;
		this.id_account_code = id_account_code;
		this.status = status;
		this.gender = gender;
		this.account_level = account_level;
		this.email = email;
		this.password = password;
		this.account_name = account_name;
		this.phone = phone;
		this.province_code = province_code;
		this.province_name = province_name;
		this.status_name = status_name;
		this.gender_name = gender_name;
		this.register_date = register_date;
		this.last_login_date = last_login_date;
		this.sum_donate_account = sum_donate_account;
	}

	public int getId_account() {
		return id_account;
	}

	public void setId_account(int id_account) {
		this.id_account = id_account;
	}

	public int getId_account_code() {
		return id_account_code;
	}

	public void setId_account_code(int id_account_code) {
		this.id_account_code = id_account_code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getAccount_level() {
		return account_level;
	}

	public void setAccount_level(String account_level) {
		this.account_level = account_level;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getGender_name() {
		return gender_name;
	}

	public void setGender_name(String gender_name) {
		this.gender_name = gender_name;
	}

	public Date getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}

	public Date getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(Date last_login_date) {
		this.last_login_date = last_login_date;
	}

	public long getSum_donate_account() {
		return sum_donate_account;
	}

	public void setSum_donate_account(long sum_donate_account) {
		this.sum_donate_account = sum_donate_account;
	}

}
