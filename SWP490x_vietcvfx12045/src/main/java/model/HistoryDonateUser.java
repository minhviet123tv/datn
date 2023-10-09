package model;

import java.util.Date;

public class HistoryDonateUser {
	private int id_account, id_campaign, id_campaign_code, campaign_status;
	private String campaign_code_name, image_cover_link, title, province_name, campaign_status_name;
	private Long donate_money, target_money;
	private Date donate_date, campaign_end_date;
	
	public HistoryDonateUser () {
		
	}

	public HistoryDonateUser(int id_account, int id_campaign, int id_campaign_code, int campaign_status,
			String campaign_code_name, String image_cover_link, String title, String province_name,
			String campaign_status_name, Long donate_money, Long target_money, Date donate_date,
			Date campaign_end_date) {
		super();
		this.id_account = id_account;
		this.id_campaign = id_campaign;
		this.id_campaign_code = id_campaign_code;
		this.campaign_status = campaign_status;
		this.campaign_code_name = campaign_code_name;
		this.image_cover_link = image_cover_link;
		this.title = title;
		this.province_name = province_name;
		this.campaign_status_name = campaign_status_name;
		this.donate_money = donate_money;
		this.target_money = target_money;
		this.donate_date = donate_date;
		this.campaign_end_date = campaign_end_date;
	}

	public int getId_account() {
		return id_account;
	}

	public void setId_account(int id_account) {
		this.id_account = id_account;
	}

	public int getId_campaign() {
		return id_campaign;
	}

	public void setId_campaign(int id_campaign) {
		this.id_campaign = id_campaign;
	}

	public int getId_campaign_code() {
		return id_campaign_code;
	}

	public void setId_campaign_code(int id_campaign_code) {
		this.id_campaign_code = id_campaign_code;
	}

	public int getCampaign_status() {
		return campaign_status;
	}

	public void setCampaign_status(int campaign_status) {
		this.campaign_status = campaign_status;
	}

	public String getCampaign_code_name() {
		return campaign_code_name;
	}

	public void setCampaign_code_name(String campaign_code_name) {
		this.campaign_code_name = campaign_code_name;
	}

	public String getImage_cover_link() {
		return image_cover_link;
	}

	public void setImage_cover_link(String image_cover_link) {
		this.image_cover_link = image_cover_link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getCampaign_status_name() {
		return campaign_status_name;
	}

	public void setCampaign_status_name(String campaign_status_name) {
		this.campaign_status_name = campaign_status_name;
	}

	public Long getDonate_money() {
		return donate_money;
	}

	public void setDonate_money(Long donate_money) {
		this.donate_money = donate_money;
	}

	public Long getTarget_money() {
		return target_money;
	}

	public void setTarget_money(Long target_money) {
		this.target_money = target_money;
	}

	public Date getDonate_date() {
		return donate_date;
	}

	public void setDonate_date(Date donate_date) {
		this.donate_date = donate_date;
	}

	public Date getCampaign_end_date() {
		return campaign_end_date;
	}

	public void setCampaign_end_date(Date campaign_end_date) {
		this.campaign_end_date = campaign_end_date;
	}

}
