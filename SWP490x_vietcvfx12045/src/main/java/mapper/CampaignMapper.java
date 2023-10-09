package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;
import org.springframework.jdbc.core.RowMapper;

public class CampaignMapper implements RowMapper<Campaign> {
	 public Campaign mapRow(ResultSet rs, int rowNum) throws SQLException {
	 	Campaign campaign = new Campaign();
	 	
	 	//Lấy giá trị các cột của các bảng: Trong câu truy vấn của CampaignJDBCTemplate đến CSDL. Và truyền, gán vào đối tượng Campaign để sử dụng
	 	campaign.setId_campaign(rs.getInt("id_campaign"));
	 	campaign.setId_campaign_code(rs.getInt("id_campaign_code"));
	 	campaign.setTarget_money(rs.getLong("target_money"));
	 	campaign.setId_account_write(rs.getInt("id_account_write"));
	 	campaign.setId_account_content_end(rs.getInt("id_account_content_end"));
	 	campaign.setId_account_last_edit(rs.getInt("id_account_last_edit"));
	 	campaign.setStatus(rs.getInt("status"));
	 	
	 	campaign.setImage_cover_link(rs.getString("image_cover_link"));
	 	campaign.setTitle(rs.getString("title"));
	 	campaign.setProvince_code(rs.getString("province_code"));
	 	campaign.setContent_begin(rs.getString("content_begin"));
	 	campaign.setContent_end(rs.getString("content_end"));
	 	
	 	//Tạm thời để getString thay vì getDate
	 	campaign.setCampaign_begin_date(rs.getString("campaign_begin_date"));
	 	campaign.setCampaign_end_date(rs.getString("campaign_end_date"));
	 	campaign.setContent_begin_date(rs.getString("content_begin_date"));
	 	campaign.setContent_end_date(rs.getString("content_end_date"));
	 	campaign.setAccount_last_edit_date(rs.getString("account_last_edit_date"));
		 
		return campaign;
   }
}
