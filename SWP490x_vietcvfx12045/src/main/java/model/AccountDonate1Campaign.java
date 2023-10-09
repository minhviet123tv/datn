package model;

import java.util.Date;

public class AccountDonate1Campaign {
	private String email, account_code_name, province_name;
	private Long donate_money;
	private Date donate_date;
	
	public AccountDonate1Campaign () {}

	public AccountDonate1Campaign(String email, String account_code_name, String province_name, Long donate_money,
			Date donate_date) {
		super();
		this.email = email;
		this.account_code_name = account_code_name;
		this.province_name = province_name;
		this.donate_money = donate_money;
		this.donate_date = donate_date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccount_code_name() {
		return account_code_name;
	}

	public void setAccount_code_name(String account_code_name) {
		this.account_code_name = account_code_name;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public Long getDonate_money() {
		return donate_money;
	}

	public void setDonate_money(Long donate_money) {
		this.donate_money = donate_money;
	}

	public Date getDonate_date() {
		return donate_date;
	}

	public void setDonate_date(Date donate_date) {
		this.donate_date = donate_date;
	}

}
