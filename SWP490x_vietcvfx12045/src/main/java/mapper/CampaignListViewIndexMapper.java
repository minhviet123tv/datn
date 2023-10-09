package mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.springframework.jdbc.core.RowMapper;
import model.CampaignListViewIndex;

public class CampaignListViewIndexMapper implements RowMapper <CampaignListViewIndex>{
	
	public CampaignListViewIndex mapRow(ResultSet rs, int rowNum) throws SQLException {
		
	 	CampaignListViewIndex campaign = new CampaignListViewIndex(); //remaining_date, campaign_end_date
	 	
	 	//Lấy giá trị các cột của các bảng: Trong câu truy vấn của CampaignJDBCTemplate đến CSDL. Và truyền, gán vào đối tượng CampaignListViewIndex để sử dụng
	 	campaign.setId_campaign(rs.getInt("id_campaign"));
	 	campaign.setId_campaign_code(rs.getInt("id_campaign_code"));
	 	campaign.setTarget_money(rs.getLong("target_money"));
	 	campaign.setRemaining_date(rs.getInt("remaining_date"));
	 	campaign.setStatus(rs.getInt("status"));
	 	campaign.setSum_donate_count(rs.getInt("sum_donate_count"));
	 	campaign.setSum_donate_money(rs.getLong("sum_donate_money"));
	 	
	 	campaign.setName_vn(rs.getString("name_vn"));
	 	campaign.setImage_cover_link(rs.getString("image_cover_link"));
	 	campaign.setTitle(rs.getString("title"));
	 	campaign.setProvince_code(rs.getString("province_code"));
	 	campaign.setProvince_name(rs.getString("province_name"));
	 	campaign.setStatus_name(rs.getString("status_name"));
	 	
	 	//Xử lý riêng date để hiển thị theo nhu cầu (dùng format), mục đích hiển thị theo String, không dùng để tính cộng trừ date
	 	SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-YYYY");
	 	
	 	Date campaign_begin_date = rs.getDate("campaign_begin_date");
	 	Date campaign_end_date = rs.getDate("campaign_end_date");
	 	
	 	String string_campaign_begin_date = myFormat.format(campaign_begin_date);
		String string_campaign_end_date = myFormat.format(campaign_end_date);
		
		campaign.setCampaign_begin_date(string_campaign_begin_date);
	 	campaign.setCampaign_end_date(string_campaign_end_date);
	 	
		return campaign;
   }

}
