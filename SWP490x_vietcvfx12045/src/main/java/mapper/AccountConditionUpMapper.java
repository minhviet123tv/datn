package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import model.AccountConditionUp;


public class AccountConditionUpMapper implements RowMapper <AccountConditionUp> {
	
	public AccountConditionUp mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		 AccountConditionUp account = new AccountConditionUp();
	 	 //Lấy giá trị trong các cột của bảng Account trong CSDL (đầy đủ dữ liệu của một Account) và truyền, gán vào đối tượng Account để sử dụng (lưu truyền dữ liệu)
	 	 account.setId_account(rs.getInt("id_account"));
	 	 account.setId_account_code(rs.getInt("id_account_code"));
	 	 account.setStatus(rs.getInt("status"));
	 	 
	 	 account.setAccount_code_name(rs.getString("account_code_name"));
	 	 
	 	 account.setCondition_up(rs.getLong("condition_up"));
	 	 account.setSum_donate_money(rs.getLong("sum_donate_money"));
		 
		 return account;
  }

}
