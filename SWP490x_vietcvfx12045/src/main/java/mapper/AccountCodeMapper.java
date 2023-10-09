package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.AccountCode;

public class AccountCodeMapper implements RowMapper<AccountCode>{
	
	public AccountCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccountCode accountCode = new AccountCode();
	 	 //Lấy giá trị trong các cột của bảng Account trong CSDL (đầy đủ dữ liệu của một Account) và truyền, gán vào đối tượng Account để sử dụng (lưu truyền dữ liệu)
		accountCode.setId_account_code(rs.getInt("id_account_code"));
	 	
		accountCode.setName_en(rs.getString("name_en"));
		accountCode.setName_vn(rs.getString("name_vn"));
		
		accountCode.setCondition_up(rs.getString("condition_up"));
		accountCode.setStatus(rs.getString("status"));
		accountCode.setNotes(rs.getString("notes"));
		 
		 return accountCode;
  }
}
