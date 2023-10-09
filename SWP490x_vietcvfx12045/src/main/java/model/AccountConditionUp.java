package model;

public class AccountConditionUp {
	private int id_account, id_account_code, status;
	private String account_code_name;
	private Long condition_up, sum_donate_money;
	
	public AccountConditionUp () {}

	public AccountConditionUp(int id_account, int id_account_code, int status, String account_code_name,
			Long condition_up, Long sum_donate_money) {
		super();
		this.id_account = id_account;
		this.id_account_code = id_account_code;
		this.status = status;
		this.account_code_name = account_code_name;
		this.condition_up = condition_up;
		this.sum_donate_money = sum_donate_money;
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

	public String getAccount_code_name() {
		return account_code_name;
	}

	public void setAccount_code_name(String account_code_name) {
		this.account_code_name = account_code_name;
	}

	public Long getCondition_up() {
		return condition_up;
	}

	public void setCondition_up(Long condition_up) {
		this.condition_up = condition_up;
	}

	public Long getSum_donate_money() {
		return sum_donate_money;
	}

	public void setSum_donate_money(Long sum_donate_money) {
		this.sum_donate_money = sum_donate_money;
	}

}
