package model;

import java.sql.Date;

public class View1CampaignIndex {
	private int id_campaign, id_campaign_code, remaining_date, donate_number_total, status;
	private String campaign_code_name, image_cover_link, title, province_code, province_name, content_begin, email_post_content_begin, content_end, email_post_content_end, status_name;
	private Date campaign_begin_date, campaign_end_date, content_begin_date, content_end_date;
	private Long target_money, sum_donate_money;
	
	public View1CampaignIndex () {}

	public View1CampaignIndex(int id_campaign, int id_campaign_code, int remaining_date, int donate_number_total,
			int status, String campaign_code_name, String image_cover_link, String title, String province_code,
			String province_name, String content_begin, String email_post_content_begin, String content_end,
			String email_post_content_end, String status_name, Date campaign_begin_date, Date campaign_end_date,
			Date content_begin_date, Date content_end_date, Long target_money, Long sum_donate_money) {
		super();
		this.id_campaign = id_campaign;
		this.id_campaign_code = id_campaign_code;
		this.remaining_date = remaining_date;
		this.donate_number_total = donate_number_total;
		this.status = status;
		this.campaign_code_name = campaign_code_name;
		this.image_cover_link = image_cover_link;
		this.title = title;
		this.province_code = province_code;
		this.province_name = province_name;
		this.content_begin = content_begin;
		this.email_post_content_begin = email_post_content_begin;
		this.content_end = content_end;
		this.email_post_content_end = email_post_content_end;
		this.status_name = status_name;
		this.campaign_begin_date = campaign_begin_date;
		this.campaign_end_date = campaign_end_date;
		this.content_begin_date = content_begin_date;
		this.content_end_date = content_end_date;
		this.target_money = target_money;
		this.sum_donate_money = sum_donate_money;
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

	public int getRemaining_date() {
		return remaining_date;
	}

	public void setRemaining_date(int remaining_date) {
		this.remaining_date = remaining_date;
	}

	public int getDonate_number_total() {
		return donate_number_total;
	}

	public void setDonate_number_total(int donate_number_total) {
		this.donate_number_total = donate_number_total;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getContent_begin() {
		return content_begin;
	}

	public void setContent_begin(String content_begin) {
		this.content_begin = content_begin;
	}

	public String getEmail_post_content_begin() {
		return email_post_content_begin;
	}

	public void setEmail_post_content_begin(String email_post_content_begin) {
		this.email_post_content_begin = email_post_content_begin;
	}

	public String getContent_end() {
		return content_end;
	}

	public void setContent_end(String content_end) {
		this.content_end = content_end;
	}

	public String getEmail_post_content_end() {
		return email_post_content_end;
	}

	public void setEmail_post_content_end(String email_post_content_end) {
		this.email_post_content_end = email_post_content_end;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public Date getCampaign_begin_date() {
		return campaign_begin_date;
	}

	public void setCampaign_begin_date(Date campaign_begin_date) {
		this.campaign_begin_date = campaign_begin_date;
	}

	public Date getCampaign_end_date() {
		return campaign_end_date;
	}

	public void setCampaign_end_date(Date campaign_end_date) {
		this.campaign_end_date = campaign_end_date;
	}

	public Date getContent_begin_date() {
		return content_begin_date;
	}

	public void setContent_begin_date(Date content_begin_date) {
		this.content_begin_date = content_begin_date;
	}

	public Date getContent_end_date() {
		return content_end_date;
	}

	public void setContent_end_date(Date content_end_date) {
		this.content_end_date = content_end_date;
	}

	public Long getTarget_money() {
		return target_money;
	}

	public void setTarget_money(Long target_money) {
		this.target_money = target_money;
	}

	public Long getSum_donate_money() {
		return sum_donate_money;
	}

	public void setSum_donate_money(Long sum_donate_money) {
		this.sum_donate_money = sum_donate_money;
	}

}
