package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.View1Campaign;

public class View1CampaignMapper implements RowMapper<View1Campaign>{
	 public View1Campaign mapRow(ResultSet rs, int rowNum) throws SQLException {
		 	
		 	View1Campaign campaign = new View1Campaign();
		 	
		 	//Lấy giá trị cột của bảng: Trong câu truy vấn của CampaignJDBCTemplate đến CSDL. Và truyền, gán vào đối tượng View1Campaign để sử dụng
		 	campaign.setId_campaign(rs.getInt("id_campaign"));
		 	campaign.setId_campaign_code(rs.getInt("id_campaign_code"));
		 	campaign.setTarget_money(rs.getLong("target_money"));
		 	campaign.setRemaining_date(rs.getInt("remaining_date"));
		 	campaign.setId_account_write(rs.getInt("id_account_write"));
		 	campaign.setId_account_content_end(rs.getInt("id_account_content_end"));
		 	campaign.setId_account_last_edit(rs.getInt("id_account_last_edit"));
		 	campaign.setRemaining_date(rs.getInt("remaining_date"));		 	
		 	campaign.setStatus(rs.getInt("status"));
		 	
		 	campaign.setSum_donate_money(rs.getLong("sum_donate_money"));
		 	
		 	campaign.setName_vn(rs.getString("name_vn"));
		 	campaign.setImage_cover_link(rs.getString("image_cover_link"));
		 	campaign.setTitle(rs.getString("title"));
		 	campaign.setProvince_code(rs.getString("province_code"));
		 	campaign.setProvince_name(rs.getString("province_name"));		 	
		 	campaign.setContent_begin(rs.getString("content_begin"));
		 	campaign.setContent_end(rs.getString("content_end"));
		 	campaign.setEmail_write(rs.getString("email_write"));
		 	campaign.setEmail_content_end(rs.getString("email_content_end"));
		 	campaign.setEmail_last_edit(rs.getString("email_last_edit"));
		 	campaign.setStatus_name(rs.getString("status_name"));
		 	
		 	campaign.setCampaign_begin_date(rs.getDate("campaign_begin_date"));
		 	campaign.setCampaign_end_date(rs.getDate("campaign_end_date"));
		 	campaign.setContent_begin_date(rs.getDate("content_begin_date"));
		 	campaign.setContent_end_date(rs.getDate("content_end_date"));
		 	campaign.setAccount_last_edit_date(rs.getDate("account_last_edit_date"));
		 	
		 	//Xử lý riêng date để hiển thị theo nhu cầu (dùng format), mục đích hiển thị theo String, không dùng để tính cộng trừ date
//		 	Date campaign_begin_date = rs.getDate("campaign_begin_date");
//		 	Date campaign_end_date = rs.getDate("campaign_end_date");
//		 	Date content_begin_date = rs.getDate("content_begin_date");
//		 	Date content_end_date = rs.getDate("content_end_date");
//		 	Date account_last_edit_date = rs.getDate("account_last_edit_date");
//		 	
//		 	SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-YYYY");
//		 	campaign.setCampaign_begin_date(myFormat.format(campaign_begin_date));
			
			return campaign;
	   }
}
