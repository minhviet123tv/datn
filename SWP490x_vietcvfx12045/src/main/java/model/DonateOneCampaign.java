package model;

public class DonateOneCampaign {
	private int id_campaign, status;
	private Long sum_donate_campaign, target_money;
	
	public DonateOneCampaign() {}

	public DonateOneCampaign(int id_campaign, int status, Long sum_donate_campaign, Long target_money) {
		super();
		this.id_campaign = id_campaign;
		this.status = status;
		this.sum_donate_campaign = sum_donate_campaign;
		this.target_money = target_money;
	}

	public int getId_campaign() {
		return id_campaign;
	}

	public void setId_campaign(int id_campaign) {
		this.id_campaign = id_campaign;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getSum_donate_campaign() {
		return sum_donate_campaign;
	}

	public void setSum_donate_campaign(Long sum_donate_campaign) {
		this.sum_donate_campaign = sum_donate_campaign;
	}

	public Long getTarget_money() {
		return target_money;
	}

	public void setTarget_money(Long target_money) {
		this.target_money = target_money;
	}

}
