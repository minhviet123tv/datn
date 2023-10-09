package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import model.AccountStatus;

public class AccountStatusMapper implements RowMapper<AccountStatus>{
	
	 public AccountStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		 AccountStatus accountStatus = new AccountStatus();
	 	 //Lấy giá trị trong các cột của bảng Account_status trong CSDL (đầy đủ dữ liệu của một Account_status) và truyền, gán vào đối tượng Account_status để sử dụng (lưu truyền dữ liệu)
		 accountStatus.setStatus_code(rs.getInt("status_code"));
	 	 
		 accountStatus.setStatus_name(rs.getString("status_name"));
		 accountStatus.setNotes(rs.getString("notes"));
		 
		 return accountStatus;
   }

}
