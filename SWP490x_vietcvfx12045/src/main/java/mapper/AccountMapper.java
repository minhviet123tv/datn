package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.*;

/*
 * Hàm Mapper của model Account, sử dụng để dùng JdbcTemplate trong AccountJDBCTemplate
 * Mục đích class: Trả về một đối tượng Account của lệnh truy vấn đến bảng CSDL
 */
public class AccountMapper implements RowMapper<Account> {
	
	 public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		 	 Account account = new Account();
		 	 //Lấy giá trị trong các cột của bảng Account trong CSDL (đầy đủ dữ liệu của một Account) và truyền, gán vào đối tượng Account để sử dụng (lưu truyền dữ liệu)
		 	 account.setId_account(rs.getInt("id_account"));
		 	 account.setId_account_code(rs.getInt("id_account_code"));
		 	 account.setGender(rs.getInt("gender"));
		 	 account.setStatus(rs.getInt("status"));
		 	 
			 account.setEmail(rs.getString("email"));
			 account.setPassword(rs.getString("password"));
			 
			 account.setName(rs.getString("name"));
			 account.setPhone(rs.getString("phone"));
			 account.setProvince_code(rs.getString("province_code"));
			 account.setRandom_code(rs.getString("random_code"));
			 
			 account.setRegister_date(rs.getDate("register_date"));
			 account.setLast_login_date(rs.getDate("last_login_date"));
			 
			 return account;
	   }
}
