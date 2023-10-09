package mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import model.HistoryDonateUser;

public class HistoryDonateUserMapper implements RowMapper <HistoryDonateUser>{
	
	 public HistoryDonateUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		 	
		 HistoryDonateUser campaign = new HistoryDonateUser();
		 	
		 	//Lấy giá trị cột của bảng: Trong câu truy vấn của CampaignJDBCTemplate đến CSDL. Và truyền, gán vào đối tượng HistoryDonateUser để sử dụng
		 	campaign.setId_account(rs.getInt("id_account"));
		 	campaign.setId_campaign(rs.getInt("id_campaign"));
		 	campaign.setId_campaign_code(rs.getInt("id_campaign_code"));
		 	campaign.setCampaign_status(rs.getInt("campaign_status"));
		 	
		 	campaign.setTarget_money(rs.getLong("target_money"));
		 	campaign.setDonate_money(rs.getLong("donate_money"));
		 	campaign.setTarget_money(rs.getLong("target_money"));
		 	
		 	campaign.setCampaign_code_name(rs.getString("campaign_code_name"));
		 	campaign.setImage_cover_link(rs.getString("image_cover_link"));
		 	campaign.setTitle(rs.getString("title"));
		 	campaign.setProvince_name(rs.getString("province_name"));
		 	campaign.setCampaign_status_name(rs.getString("campaign_status_name"));		 	
		 	
		 	campaign.setDonate_date(rs.getDate("donate_date"));
		 	campaign.setCampaign_end_date(rs.getDate("campaign_end_date"));
			
			return campaign;
	   }

}
