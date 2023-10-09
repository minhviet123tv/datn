package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import model.Provinces;

public class ProvincesMapper implements RowMapper<Provinces>{
	public Provinces mapRow (ResultSet rs, int rowNum) throws SQLException {
	Provinces provinces = new Provinces();
 	
 	//Lấy giá trị các cột của các bảng: Trong câu truy vấn của CampaignJDBCTemplate đến CSDL. Và truyền, gán vào đối tượng Provinces để sử dụng
	provinces.setAdministrative_unit_id(rs.getInt("administrative_unit_id"));
	provinces.setAdministrative_region_id(rs.getInt("administrative_region_id"));
 	
	provinces.setCode(rs.getString("code"));
	provinces.setName(rs.getString("name"));
	provinces.setName_en(rs.getString("name_en"));
	provinces.setFull_name(rs.getString("full_name"));
	provinces.setFull_name_en(rs.getString("full_name_en"));
	provinces.setCode_name(rs.getString("code_name"));
 	
	return provinces;	
	}
}
