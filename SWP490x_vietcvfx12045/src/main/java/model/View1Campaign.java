package model;

import java.sql.Date;

public class View1Campaign {
	private int id_campaign, id_campaign_code, id_account_write, id_account_content_end, id_account_last_edit, remaining_date, status;
	private String name_vn, image_cover_link, title, province_code, province_name, content_begin, content_end, email_write, email_content_end, email_last_edit, status_name;
	private Date campaign_begin_date, campaign_end_date, content_begin_date, content_end_date, account_last_edit_date;
	private Long target_money, sum_donate_money;
	
	public View1Campaign () {
		
	}

	public View1Campaign(int id_campaign, int id_campaign_code, int id_account_write, int id_account_content_end,
			int id_account_last_edit, int remaining_date, int status, String name_vn, String image_cover_link,
			String title, String province_code, String province_name, String content_begin, String content_end,
			String email_write, String email_content_end, String email_last_edit, String status_name,
			Date campaign_begin_date, Date campaign_end_date, Date content_begin_date, Date content_end_date,
			Date account_last_edit_date, Long target_money, Long sum_donate_money) {
		super();
		this.id_campaign = id_campaign;
		this.id_campaign_code = id_campaign_code;
		this.id_account_write = id_account_write;
		this.id_account_content_end = id_account_content_end;
		this.id_account_last_edit = id_account_last_edit;
		this.remaining_date = remaining_date;
		this.status = status;
		this.name_vn = name_vn;
		this.image_cover_link = image_cover_link;
		this.title = title;
		this.province_code = province_code;
		this.province_name = province_name;
		this.content_begin = content_begin;
		this.content_end = content_end;
		this.email_write = email_write;
		this.email_content_end = email_content_end;
		this.email_last_edit = email_last_edit;
		this.status_name = status_name;
		this.campaign_begin_date = campaign_begin_date;
		this.campaign_end_date = campaign_end_date;
		this.content_begin_date = content_begin_date;
		this.content_end_date = content_end_date;
		this.account_last_edit_date = account_last_edit_date;
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

	public int getId_account_write() {
		return id_account_write;
	}

	public void setId_account_write(int id_account_write) {
		this.id_account_write = id_account_write;
	}

	public int getId_account_content_end() {
		return id_account_content_end;
	}

	public void setId_account_content_end(int id_account_content_end) {
		this.id_account_content_end = id_account_content_end;
	}

	public int getId_account_last_edit() {
		return id_account_last_edit;
	}

	public void setId_account_last_edit(int id_account_last_edit) {
		this.id_account_last_edit = id_account_last_edit;
	}

	public int getRemaining_date() {
		return remaining_date;
	}

	public void setRemaining_date(int remaining_date) {
		this.remaining_date = remaining_date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName_vn() {
		return name_vn;
	}

	public void setName_vn(String name_vn) {
		this.name_vn = name_vn;
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

	public String getContent_end() {
		return content_end;
	}

	public void setContent_end(String content_end) {
		this.content_end = content_end;
	}

	public String getEmail_write() {
		return email_write;
	}

	public void setEmail_write(String email_write) {
		this.email_write = email_write;
	}

	public String getEmail_content_end() {
		return email_content_end;
	}

	public void setEmail_content_end(String email_content_end) {
		this.email_content_end = email_content_end;
	}

	public String getEmail_last_edit() {
		return email_last_edit;
	}

	public void setEmail_last_edit(String email_last_edit) {
		this.email_last_edit = email_last_edit;
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

	public Date getAccount_last_edit_date() {
		return account_last_edit_date;
	}

	public void setAccount_last_edit_date(Date account_last_edit_date) {
		this.account_last_edit_date = account_last_edit_date;
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
