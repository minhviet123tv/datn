package model;

public class Campaign {
	private int id_campaign, id_campaign_code, id_account_write,id_account_content_end, id_account_last_edit, status;
	private Long target_money;
	private String image_cover_link, title, province_code, content_begin, content_end; 
	private String campaign_begin_date, campaign_end_date, content_begin_date, content_end_date, account_last_edit_date;
	
	public Campaign () {
		
	}

	public Campaign(int id_campaign, int id_campaign_code, Long target_money, int id_account_write,
			int id_account_content_end, int id_account_last_edit, int status, String image_cover_link, String title,
			String province_code, String content_begin, String content_end, String campaign_begin_date,
			String campaign_end_date, String content_begin_date, String content_end_date,
			String account_last_edit_date) {
		super();
		this.id_campaign = id_campaign;
		this.id_campaign_code = id_campaign_code;
		this.target_money = target_money;
		this.id_account_write = id_account_write;
		this.id_account_content_end = id_account_content_end;
		this.id_account_last_edit = id_account_last_edit;
		this.status = status;
		this.image_cover_link = image_cover_link;
		this.title = title;
		this.province_code = province_code;
		this.content_begin = content_begin;
		this.content_end = content_end;
		this.campaign_begin_date = campaign_begin_date;
		this.campaign_end_date = campaign_end_date;
		this.content_begin_date = content_begin_date;
		this.content_end_date = content_end_date;
		this.account_last_edit_date = account_last_edit_date;
	}

	public Campaign(int id_campaign_code, Long target_money, int id_account_write, int status, String image_cover_link,
			String title, String province_code, String content_begin, String campaign_begin_date,
			String campaign_end_date, String content_begin_date) {
		super();
		this.id_campaign_code = id_campaign_code;
		this.target_money = target_money;
		this.id_account_write = id_account_write;
		this.status = status;
		this.image_cover_link = image_cover_link;
		this.title = title;
		this.province_code = province_code;
		this.content_begin = content_begin;
		this.campaign_begin_date = campaign_begin_date;
		this.campaign_end_date = campaign_end_date;
		this.content_begin_date = content_begin_date;
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

	public Long getTarget_money() {
		return target_money;
	}

	public void setTarget_money(Long target_money) {
		this.target_money = target_money;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getCampaign_begin_date() {
		return campaign_begin_date;
	}

	public void setCampaign_begin_date(String campaign_begin_date) {
		this.campaign_begin_date = campaign_begin_date;
	}

	public String getCampaign_end_date() {
		return campaign_end_date;
	}

	public void setCampaign_end_date(String campaign_end_date) {
		this.campaign_end_date = campaign_end_date;
	}

	public String getContent_begin_date() {
		return content_begin_date;
	}

	public void setContent_begin_date(String content_begin_date) {
		this.content_begin_date = content_begin_date;
	}

	public String getContent_end_date() {
		return content_end_date;
	}

	public void setContent_end_date(String content_end_date) {
		this.content_end_date = content_end_date;
	}

	public String getAccount_last_edit_date() {
		return account_last_edit_date;
	}

	public void setAccount_last_edit_date(String account_last_edit_date) {
		this.account_last_edit_date = account_last_edit_date;
	}
	
	
}
