package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.View1CampaignIndex;

public class View1CampaignIndexMapper implements RowMapper<View1CampaignIndex> {
	public View1CampaignIndex mapRow (ResultSet rs, int rowNum) throws SQLException {
	 	
		View1CampaignIndex campaign = new View1CampaignIndex();
	 	
	 	//Lấy giá trị cột của bảng: Trong câu truy vấn của CampaignJDBCTemplate đến CSDL. Và truyền, gán vào đối tượng View1Campaign để sử dụng
	 	campaign.setId_campaign(rs.getInt("id_campaign"));
	 	campaign.setId_campaign_code(rs.getInt("id_campaign_code"));
	 	campaign.setRemaining_date(rs.getInt("remaining_date"));
	 	campaign.setDonate_number_total(rs.getInt("donate_number_total"));		 	
	 	campaign.setStatus(rs.getInt("status"));
	 	
	 	campaign.setCampaign_code_name(rs.getString("campaign_code_name"));
	 	campaign.setImage_cover_link(rs.getString("image_cover_link"));
	 	campaign.setTitle(rs.getString("title"));
	 	campaign.setProvince_code(rs.getString("province_code"));
	 	campaign.setProvince_name(rs.getString("province_name"));		 	
	 	campaign.setContent_begin(rs.getString("content_begin"));
	 	campaign.setEmail_post_content_begin(rs.getString("email_post_content_begin"));
	 	campaign.setContent_end(rs.getString("content_end"));
	 	campaign.setEmail_post_content_end(rs.getString("email_post_content_end"));
	 	campaign.setStatus_name(rs.getString("status_name"));
	 	
	 	campaign.setCampaign_begin_date(rs.getDate("campaign_begin_date"));
	 	campaign.setCampaign_end_date(rs.getDate("campaign_end_date"));
	 	campaign.setContent_begin_date(rs.getDate("content_begin_date"));
	 	campaign.setContent_end_date(rs.getDate("content_end_date"));
	 	
	 	campaign.setTarget_money(rs.getLong("target_money"));
	 	campaign.setSum_donate_money(rs.getLong("sum_donate_money"));
	 	
	 	/* Xử lý riêng date để hiển thị theo nhu cầu (dùng format), mục đích hiển thị theo String, không dùng để tính cộng trừ date	*/
//	 	Date campaign_begin_date = rs.getDate("campaign_begin_date");
//	 	SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-YYYY");
//	 	campaign.setCampaign_begin_date(myFormat.format(campaign_begin_date));
		
		return campaign;
   }

}
