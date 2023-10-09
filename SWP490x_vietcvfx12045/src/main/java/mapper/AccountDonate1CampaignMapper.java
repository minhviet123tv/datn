package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.AccountDonate1Campaign;

public class AccountDonate1CampaignMapper implements RowMapper <AccountDonate1Campaign>{
	public AccountDonate1Campaign mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccountDonate1Campaign accountDonate = new AccountDonate1Campaign();
	 	 //Lấy giá trị trong các cột của bảng Account trong CSDL (đầy đủ dữ liệu của một Account) và truyền, gán vào đối tượng Account để sử dụng (lưu truyền dữ liệu)
		 	 
			accountDonate.setEmail(rs.getString("email"));
			accountDonate.setAccount_code_name(rs.getString("account_code_name"));
			accountDonate.setProvince_name(rs.getString("province_name"));
		
			accountDonate.setDonate_money(rs.getLong("donate_money"));
			accountDonate.setDonate_date(rs.getDate("donate_date"));
		 
		 return accountDonate;
  }

}
