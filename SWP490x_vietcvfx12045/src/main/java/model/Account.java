package model;

import java.util.Date;

//Lớp model cho dữ liệu account lấy được ở CSDL

public class Account {
	private String email, password;
	private int id_account, id_account_code, gender, status;
	private String name, phone, province_code, random_code;
	private Date register_date , last_login_date;

	public Account() {
		
	}

	public Account(String email, String password, int id_account, int id_account_code, int gender, int status,
			String name, String phone, String province_code, String random_code, Date register_date,
			Date last_login_date) {
		super();
		this.email = email;
		this.password = password;
		this.id_account = id_account;
		this.id_account_code = id_account_code;
		this.gender = gender;
		this.status = status;
		this.name = name;
		this.phone = phone;
		this.province_code = province_code;
		this.random_code = random_code;
		this.register_date = register_date;
		this.last_login_date = last_login_date;
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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRandom_code() {
		return random_code;
	}

	public void setRandom_code(String random_code) {
		this.random_code = random_code;
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
	
}
