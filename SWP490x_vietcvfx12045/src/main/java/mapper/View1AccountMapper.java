package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import model.View1Account;

public class View1AccountMapper implements RowMapper<View1Account>{
	 public View1Account mapRow(ResultSet rs, int rowNum) throws SQLException {
	 	 View1Account account = new View1Account();
	 	 
	 	 //Lấy giá trị trong các cột của bảng Account trong CSDL
	 	 account.setId_account(rs.getInt("id_account"));
	 	 account.setId_account_code(rs.getInt("id_account_code"));
	 	 account.setStatus(rs.getInt("status"));
	 	 account.setGender(rs.getInt("gender"));
	 	 
		 account.setAccount_level(rs.getString("account_level"));
		 account.setEmail(rs.getString("email"));
		 account.setPassword(rs.getString("password"));
		 account.setProvince_code(rs.getString("province_code"));
		 account.setAccount_name(rs.getString("account_name"));
		 account.setPhone(rs.getString("phone"));
		 account.setProvince_name(rs.getString("province_name"));
		 account.setStatus_name(rs.getString("status_name"));
		 account.setGender_name(rs.getString("gender_name"));
		 
		 account.setRegister_date(rs.getDate("register_date"));
		 account.setLast_login_date(rs.getDate("last_login_date"));
		 account.setSum_donate_account(rs.getLong("sum_donate_account"));
		 
		 return account;
   }

}
