package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import model.AccountManager;

public class AccountManagerMapper implements RowMapper<AccountManager> {
	
	 public AccountManager mapRow(ResultSet rs, int rowNum) throws SQLException {
	 	 AccountManager account = new AccountManager();
	 	 //Lấy giá trị trong các cột của bảng Account trong CSDL (đầy đủ dữ liệu của một Account) và truyền, gán vào đối tượng Account để sử dụng (lưu truyền dữ liệu)
	 	 account.setId_account(rs.getInt("id_account"));
	 	 account.setId_account_code(rs.getInt("id_account_code"));
	 	 account.setProvince_code(rs.getInt("province_code"));
	 	 account.setStatus(rs.getInt("status"));
	 	 
		 account.setAccount_level(rs.getString("account_level"));
		 account.setEmail(rs.getString("email"));
		 account.setAccount_name(rs.getString("account_name"));
		 account.setProvince_name(rs.getString("province_name"));
		 account.setStatus_name(rs.getString("status_name"));
		 
		 account.setLast_login_date(rs.getDate("last_login_date"));
		 account.setSum_donate_account(rs.getLong("sum_donate_account"));
		 
		 return account;
   }

}
