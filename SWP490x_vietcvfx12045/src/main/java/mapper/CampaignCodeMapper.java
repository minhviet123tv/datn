package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.CampaignCode;

public class CampaignCodeMapper implements RowMapper<CampaignCode>{
	public CampaignCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		CampaignCode campaign = new CampaignCode();
	 	
	 	//Lấy giá trị các cột của các bảng: Trong câu truy vấn của CampaignJDBCTemplate đến CSDL. Và truyền, gán vào đối tượng CampaignCode để sử dụng
	 	campaign.setId_campaign_code(rs.getInt("id_campaign_code"));
	 	
	 	campaign.setName_characters(rs.getString("name_characters"));
	 	campaign.setName_vn(rs.getString("name_vn"));
	 	campaign.setStatus(rs.getString("status"));
	 	campaign.setNotes(rs.getString("notes"));
	 	
		return campaign;
   }
}
