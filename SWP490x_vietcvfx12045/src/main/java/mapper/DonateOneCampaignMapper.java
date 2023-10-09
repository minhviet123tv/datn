package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.DonateOneCampaign;

public class DonateOneCampaignMapper  implements RowMapper <DonateOneCampaign>{
	public DonateOneCampaign mapRow(ResultSet rs, int rowNum) throws SQLException {
			 DonateOneCampaign donate = new DonateOneCampaign();
			
		 	 //Lấy giá trị trong các cột của bảng trong CSDL
			 donate.setId_campaign(rs.getInt("id_campaign"));
			 donate.setStatus(rs.getInt("status"));
			 
			 donate.setSum_donate_campaign(rs.getLong("sum_donate_campaign"));
			 donate.setTarget_money(rs.getLong("target_money"));
			 
			 return donate;
  }

}
