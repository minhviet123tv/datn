package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import model.Gender;


public class GenderMapper implements RowMapper<Gender>{
	 public Gender mapRow(ResultSet rs, int rowNum) throws SQLException {
		 	Gender gender = new Gender();
		 	
		 	//Lấy giá trị các cột của các bảng: Trong câu truy vấn của CampaignJDBCTemplate đến CSDL. Và truyền, gán vào đối tượng Campaign để sử dụng
		 	gender.setId_gender(rs.getInt("id_gender"));
		 	gender.setStatus(rs.getInt("status"));
		 	
		 	gender.setGender_vn(rs.getString("gender_vn"));
		 	gender.setGender_en(rs.getString("gender_en"));
		 	gender.setNote(rs.getString("notes"));
			 
			return gender;
	   }
}
