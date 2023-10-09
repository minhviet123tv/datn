package model;


public class CampaignListViewAdmin {
	private int id_campaign, id_campaign_code, remaining_date, status;
	private String name_vn, image_cover_link, title, province_code, province_name, status_name, campaign_end_date;
	private Long target_money, sum_donate_money;
	
	public CampaignListViewAdmin () {
		
	}

	public CampaignListViewAdmin(int id_campaign, int id_campaign_code, int remaining_date, int status, String name_vn,
			String image_cover_link, String title, String province_code, String province_name, String status_name,
			String campaign_end_date, Long target_money, Long sum_donate_money) {
		super();
		this.id_campaign = id_campaign;
		this.id_campaign_code = id_campaign_code;
		this.remaining_date = remaining_date;
		this.status = status;
		this.name_vn = name_vn;
		this.image_cover_link = image_cover_link;
		this.title = title;
		this.province_code = province_code;
		this.province_name = province_name;
		this.status_name = status_name;
		this.campaign_end_date = campaign_end_date;
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

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getCampaign_end_date() {
		return campaign_end_date;
	}

	public void setCampaign_end_date(String campaign_end_date) {
		this.campaign_end_date = campaign_end_date;
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
