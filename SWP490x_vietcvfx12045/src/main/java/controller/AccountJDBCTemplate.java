package controller;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import mapper.*;
import model.*;

/*
 * Class thực hiện các hàm truy vấn đến CSDL
 */
public class AccountJDBCTemplate implements AccountDAO{
		private DataSource dataSource;
		private JdbcTemplate jdbcTemplateObject;
					
		/*
		 * 1. Cài đặt DataSource và JdbcTemplate cho lớp (DataSource đã setup ở Beans.xml)
		 */
		public void setDataSource(DataSource dataSource) {
			 this.dataSource = dataSource;
		     this.jdbcTemplateObject = new JdbcTemplate(dataSource);
		}
		
		/*
		 * 2. Hàm tạo tài khoản mới (chèn dữ liệu) vào bảng Account trong CSDL. Chú ý: Khi sử dụng cần dùng hàm checkAccount trước khi dùng (để kiểm tra sự tồn tại Account đang tạo)
		 */
		public void create(int id_account_code, String email, String password, String name, String phone, String province_code, String register_date, int status, Date last_login_date, int gender, String ramdom_code) {
			
			//Chọn các cột cụ thể chèn vào (Để nhìn rõ, còn bản chất thì không cần chỉ rõ mà chỉ cần trừ cột id_account tự động tăng id, và điền dữ liệu đúng thứ tự của bảng trong SQL là được)
			String SQL = "insert into Account (id_account_code, email, password, name, phone, province_code, register_date, status, last_login_date, gender, random_code) values "
					+ "(?, ?, EncryptByPassPhrase('makhoa', '" + password + "'), ?, ?, ?, ?, ?, ?, ?, ?)";
			
		     jdbcTemplateObject.update( SQL, id_account_code, email, name, phone, province_code, register_date, status, last_login_date, gender, ramdom_code);
		     System.out.println("Đã tạo: user = " + email + " password = " + password);
		}
		
		/*
		 * 3.1 Lấy về một Account từ CSDL, thông qua email truyền vào (phục vụ cho việc đăng nhập)
		 * Lấy tất cả dữ liệu Account theo email của CSDL. Sau đó trả về một Account (Thông qua AccountMapper()) và trả về return là một Account
		 * Sử dụng try catch để xử lý trường hợp tài khoản không có trong CSDL. Cũng có thể sử dụng hàm checkAccount
		 */
		public Account getAccount(String email) {
			
			//Tạo một Account rỗng (Có vỏ nhưng chưa có ruột bằng hàm khởi tạo đã tạo của Account) 
			 Account account = new Account();
			
			 //Nếu có tồn tại trong dữ liệu thì gán trả về account
			 try {
				 String SQL = "Select id_account, id_account_code, email, CONVERT (varchar(255), DECRYPTBYPASSPHRASE ('makhoa', password)) as password, name, phone, province_code, register_date, status, last_login_date, gender, random_code \r\n"
				 			+ "from Account where email = ?";
				 
				 account = jdbcTemplateObject.queryForObject(SQL, new Object[]{email}, new AccountMapper());
			     
			 } catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không tồn tại Account đang tra");
				//Nếu không có trong dữ liệu thì có thể trả về null. Hoặc chỉ đơn giản là trả về account rỗng (không null) Có vỏ nhưng không có ruột. Còn null là không có vỏ cũng không có cả ruột
				//account = null;
			 }
			 
			 return account;
		}
		
		/*
		 * 3.2 Lấy về một Account từ CSDL, thông qua id_account (phục vụ cho việc xem thông tin chi tiết của một Account)
		 */
		public Account getAccount(int id_account) {
			
			//Tạo một Account rỗng (Có vỏ nhưng chưa có ruột bằng hàm khởi tạo đã tạo của Account) 
			 Account account = new Account();
			
			 //Nếu có tồn tại trong dữ liệu thì gán trả về account
			 try {
				 String SQL = "Select id_account, id_account_code, email, CONVERT (varchar(255), DECRYPTBYPASSPHRASE ('makhoa', password)) as password, name, phone, province_code, register_date, status, last_login_date, gender, random_code \r\n"
				 		+ "from Account where id_account = ?";
				 account = jdbcTemplateObject.queryForObject(SQL, new Object[]{id_account}, new AccountMapper());
			     
			 } catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không tồn tại Account đang tra");
			 }
			 
			 return account;
		}
		
		/*
		 * 4. Hàm kiểm tra sự tồn tại của Account trong CSDL (tra theo email)
		 */
		public boolean checkAccount (String email) {
			/* Account account = new Account(); */
			
			try {
				String SQL = "select * from Account where email=?";
				Account account = jdbcTemplateObject.queryForObject(SQL, new Object[]{email}, new AccountMapper());
				
				if (account != null) {
					return true;
				}
			
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL hoặc email không tồn tại trong CSDL nên không đăng nhập được");
				return false;
			}
			
			return false;			
		}
		

		/*
		 * 5. Hàm lấy về List danh sách view AccountManager trong trang chủ của Admin cho cấp id_account_code = 4 (Lấy toàn bộ tài khoản)
		 * Ứng dụng: Có thể thêm câu lệnh where id_account_code < ? (Cấp trên sẽ quản lí cấp dưới, hoặc ngược lại > ? tuỳ cách sắp xếp cấp độ quản lý)
		 * 
		 */
		public List<AccountManager> getAccountManagerListViewAdmins (int pagecurrent, int number1page){
			//Điều chỉnh số thứ tự lấy thực tế từ CSDL theo số lượng sản phẩm trong một trang (Như vậy cần nhảy thứ tự là: số trang * số lượng trong một trang)
			pagecurrent = (pagecurrent - 1) * number1page;
			
			List<AccountManager> accountListViewAdmin = new ArrayList<AccountManager>();
			
			try {
				String SQL = "Select A.id_account, A.id_account_code, AC.name_en as account_level, A.email, A.name as account_name, A.province_code, P.name as province_name, A.last_login_date, A.status, AST.status_name, S.sum_donate_account\r\n"
						+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
						+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
						+ "left join Provinces P on P.code = A.province_code\r\n"
						+ "left join Account_status AST on AST.status_code = A.status\r\n"
						+ "Order by A.last_login_date DESC \r\n"
						+ "OFFSET ? Rows Fetch next ? rows ONLY";

				accountListViewAdmin = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new AccountManagerMapper());
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không có danh sách AccountManager");
			}
			
			return accountListViewAdmin;
		}
		
		/*
		 * 6. Hàm lấy về List danh sách view AccountManager trong trang chủ của Admin cho cấp id_account_code = 3
		 * 
		 */
		public List<AccountManager> getAccountManagerListViewManager(int pagecurrent, int number1page) {
			pagecurrent = (pagecurrent - 1) * number1page;
			
			List<AccountManager> accountListViewAdmin = new ArrayList<AccountManager>();
			
			try {
				String SQL = "Select A.id_account, A.id_account_code, AC.name_en as account_level, A.email, A.name as account_name, A.province_code, P.name as province_name, A.last_login_date, A.status, AST.status_name, S.sum_donate_account\r\n"
						+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
						+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
						+ "left join Provinces P on P.code = A.province_code\r\n"
						+ "left join Account_status AST on AST.status_code = A.status\r\n"
						+ "where A.id_account_code in (1,2,5)"
						+ "Order by A.last_login_date DESC\r\n"
						+ "OFFSET ? Rows Fetch next ? rows ONLY";

				accountListViewAdmin = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new AccountManagerMapper());
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không có danh sách AccountManager");
			}
			
			return accountListViewAdmin;
		}
		
		/*
		 * 7. Set lại ngày đăng nhập cuối cùng của một Account
		 * Được sử dụng khi đăng nhập và đã xác nhận Account thực sự tồn tại
		 */
		public void setLastLoginDateAccount(Date ngayHomNay, int id_account) {
			
			 try {
				 String SQL = "Update Account set last_login_date = ? where id_account = ?";
				 jdbcTemplateObject.update(SQL, new Object[]{ngayHomNay, id_account});
			     
			 } catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không tồn tại Account đang tra");
			 }
		}
		
		/*
		 * 8. Lấy về thông tin của một Account từ CSDL, thông qua id_account
		 */
		public View1Account getView1Account(int id_account) {
			
			View1Account view1Account = new View1Account();
			
			 //Nếu có tồn tại trong dữ liệu thì gán trả về account
			 try {
				 String SQL = "Select A.id_account, A.id_account_code, AC.name_en as account_level, A.email, CONVERT (varchar(255), DECRYPTBYPASSPHRASE ('makhoa', password)) as password, A.name as account_name, A.phone, A.province_code, P.name as province_name, A.register_date, A.status, ST.status_name , A.last_login_date, A.gender, G.gender_vn as gender_name, S.sum_donate_account\r\n"
				 		+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
				 		+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
				 		+ "left join Provinces P on P.code = A.province_code\r\n"
				 		+ "left join (Select id_gender, gender_vn from Gender) as G on G.id_gender = A.gender\r\n"
				 		+ "left join (Select status_code, status_name from Account_status) as ST on ST.status_code = A.status\r\n"
				 		+ "where A.id_account = ?";
				 
				 view1Account = jdbcTemplateObject.queryForObject(SQL, new Object[]{id_account}, new View1AccountMapper());
			     
			 } catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không tồn tại Account đang tra");
			 }
			 
			 return view1Account;
		}
		
		/*
		 * 9.a Edit một Account
		 */
		public void edit1Account (int id_account_code, String password, String name, String phone, String province_code, int status, int gender, int id_account){
			//String pw = "EncryptByPassPhrase('makhoa', '"+ password + "')"; -- Lưu lại cấu trúc mã hoá khi chèn/sửa mật khẩu
			
			try {
				String SQL = "Update Account set id_account_code=?, password=" + "EncryptByPassPhrase('makhoa', '"+ password + "')" + ", name=? , phone=? ,province_code=? , status=?, gender=? \r\n"
						+ "Where id_account=?";

				jdbcTemplateObject.update(SQL, new Object[]{id_account_code, name, phone, province_code, status, gender, id_account});
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không thể chỉnh sửa Account!");
			}
		}
		
		/*
		 * 9.b Edit một User (Người dùng quyên góp)
		 */
		public void edit1User (String password, String name, String phone, String province_code, int gender, int id_account){
			//String pw = "EncryptByPassPhrase('makhoa', '"+ password + "')"; -- Lưu lại cấu trúc mã hoá mật khẩu
			
			try {
				String SQL = "Update Account set password=" + "EncryptByPassPhrase('makhoa', '"+ password + "')" + ", name=? , phone=? , province_code=? , gender=? \r\n"
							 + "Where id_account = ?";

				jdbcTemplateObject.update(SQL, new Object[]{name, phone, province_code, gender, id_account});
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không thể chỉnh sửa User!");
			}
		}
		
		/*
		 * 9.c Thay đổi mật khẩu một Account bằng mã RandomCode (sau khi xác nhận đúng email và số điện thoại)
		 */
		public void changePassword (String password, String email, String phone){
			//String pw = "EncryptByPassPhrase('makhoa', '"+ password + "')"; -- Lưu lại cấu trúc mã hoá khi chèn/sửa mật khẩu
			
			try {
				String SQL = "Update Account set password=" + "EncryptByPassPhrase('makhoa', '"+ password + "')\r\n"
						+ "Where email=? and phone=? ";

				jdbcTemplateObject.update(SQL, new Object[]{email, phone});
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không thể chỉnh sửa Account!");
			}
		}
		
		/*
		 * 10.a Lấy danh sách Gender
		 */
		
		public List<Gender> getListGenders (){
			List<Gender> listGenders = new ArrayList<Gender>();
			
			try {
				String SQL = "Select * from Gender";
				listGenders = jdbcTemplateObject.query(SQL, new GenderMapper());
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có Gender");
			} 
			
			return listGenders;
		}

		/*
		 * 10.b Lấy danh sách Gender - Ngoại trừ mã id_gender (đã chọn khi đăng ký, hoặc khi edit Account) 
		 */
		
		public List<Gender> getListGenders (int id_gender){
			List<Gender> listGenders = new ArrayList<Gender>();
			
			try {
				String SQL = "Select * from Gender where id_gender !=" + id_gender;
				listGenders = jdbcTemplateObject.query(SQL, new GenderMapper());
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có Gender");
			} 
			
			return listGenders;
		}
		
		/*
		 * 11. Lấy một Gender theo mã id_gender
		 */
		
		public Gender get1Gender (int id_gender) {
			Gender gender = new Gender();
			
			try {
				String SQL = "Select * from Gender where id_gender = ?";
				gender = jdbcTemplateObject.queryForObject(SQL, new Object[] {id_gender}, new GenderMapper());
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có Gender");
			}
			
			return gender;
		}
		
		/*
		 * 12.a Lấy danh sách AccountCode (toàn bộ) cho cấp Admin
		 */
		
		public List<AccountCode> getListAccountCodesAdmin (){
			List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
			
			try {
				String SQL = "Select * from Account_code";
				listAccountCodes = jdbcTemplateObject.query(SQL, new AccountCodeMapper());
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có Gender");
			} 
			
			return listAccountCodes;
		}
		
		/*
		 * 12.b Lấy danh sách AccountCode (trừ mã được truyền vào) cho cấp Admin
		 */
		
		public List<AccountCode> getListAccountCodesAdmin (int id_account_code){
			List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
			
			try {
				String SQL = "Select * from Account_code where id_account_code != " + id_account_code;
				listAccountCodes = jdbcTemplateObject.query(SQL, new AccountCodeMapper());
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có Gender");
			} 
			
			return listAccountCodes;
		}
		
		/*
		 * 12.c Lấy danh sách AccountCode (Ngoại trừ mã đang sử dụng được truyền vào) cho cấp Manager
		 */
		
		public List<AccountCode> getListAccountCodesManager (int id_account_code){
			
			List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
			
			try {
				String SQL = "Select * from Account_code where id_account_code in (1,2,3,5) and id_account_code != " + id_account_code;
				listAccountCodes = jdbcTemplateObject.query(SQL, new AccountCodeMapper());
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có Gender");
			} 
			
			return listAccountCodes;
		}
		
		/*
		 * 12.d Lấy danh sách AccountCode - Gồm các cấp để hiển thị tìm kiếm
		 */
		
		public List<AccountCode> getListAccountCodesManager (){
			
			List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
			
			try {
				String SQL = "Select * from Account_code where id_account_code in (1,2,5)";
				listAccountCodes = jdbcTemplateObject.query(SQL, new AccountCodeMapper());
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có Gender");
			} 
			
			return listAccountCodes;
		}
		
		/*
		 * 13.a Lấy danh sách Account_status
		 */
		
		public List<AccountStatus> getListAccountStatus (){
			List<AccountStatus> listAccountStatus = new ArrayList<AccountStatus>();
			
			try {
				String SQL = "Select * from Account_status";
				listAccountStatus = jdbcTemplateObject.query(SQL, new AccountStatusMapper());
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có AccountStatus");
			} 
			
			return listAccountStatus;
		}
		
		/*
		 * 13.b Lấy danh sách Account_status
		 */
		
		public List<AccountStatus> getListAccountStatus (int status_code){
			List<AccountStatus> listAccountStatus = new ArrayList<AccountStatus>();
			
			try {
				String SQL = "Select * from Account_status where status_code !=" + status_code;
				listAccountStatus = jdbcTemplateObject.query(SQL, new AccountStatusMapper());
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có AccountStatus");
			} 
			
			return listAccountStatus;
		}
		
		/*
		 * 14. Xoá một Account
		 */
		public void delete1Account (int id_account){
			
			try {
				String SQL = "Update Account set status = 3 where id_account = ?";

				jdbcTemplateObject.update(SQL, new Object[]{id_account});
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc chưa thể cập nhật Account!");
			}
		}
		
		/*
		 * 15.a Hàm tìm kiếm cho cấp Admin: lấy về List danh sách view AccountManager trong trang chủ của Admin khi chọn một hoặc nhiều yếu tố chọn lọc (filter)
		 * Dành cho cấp Admin (Có thể gộp chung khi truy vấn sử dụng where id_account_code <= id_account_code được truyền vào nếu sắp xếp dữ liệu tốt )
		 * 
		 */
		public List<AccountManager> getAccountManagerSearchListViewAdmin(Integer id_account_code, String province_code, int status, int pagecurrent, int number1page) {
			pagecurrent = (pagecurrent - 1) * number1page;
			
			List<AccountManager> accountListViewAdmin = new ArrayList<AccountManager>();
			
			try {
				String SQL = "Select A.id_account, A.id_account_code, AC.name_en as account_level, A.email, A.name as account_name, A.province_code, P.name as province_name, A.last_login_date, A.status, AST.status_name, S.sum_donate_account\r\n"
						+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
						+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
						+ "left join Provinces P on P.code = A.province_code\r\n"
						+ "left join Account_status AST on AST.status_code = A.status\r\n"
						+ "where A.id_account_code = " + id_account_code + " AND A.province_code = " + province_code + " AND A.status = " + status
						+ " Order by A.id_account ASC OFFSET ? Rows Fetch next ? rows ONLY";

				accountListViewAdmin = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new AccountManagerMapper());
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không có danh sách AccountManager");
			}
			
			return accountListViewAdmin;
		}
		
		/*
		 * 15.b Hàm tìm kiếm cho cấp Manager: lấy về List danh sách view AccountManager trong trang chủ của Admin khi chọn một hoặc nhiều yếu tố chọn lọc (filter)
		 * 
		 */
		public List<AccountManager> getAccountManagerSearchListViewManager(Integer id_account_code, String province_code, int status, int pagecurrent, int number1page) {
			pagecurrent = (pagecurrent - 1) * number1page;
			
			List<AccountManager> accountListViewAdmin = new ArrayList<AccountManager>();
			
			try {
				String SQL = "Select A.id_account, A.id_account_code, AC.name_en as account_level, A.email, A.name as account_name, A.province_code, P.name as province_name, A.last_login_date, A.status, AST.status_name, S.sum_donate_account\r\n"
						+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
						+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
						+ "left join Provinces P on P.code = A.province_code\r\n"
						+ "left join Account_status AST on AST.status_code = A.status\r\n"
						+ "where (A.id_account_code in (1,2,5)) AND A.id_account_code = " + id_account_code + " AND A.province_code = " + province_code + " AND A.status = " + status
						+ " Order by A.id_account ASC OFFSET ? Rows Fetch next ? rows ONLY";

				accountListViewAdmin = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new AccountManagerMapper());
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không có danh sách AccountManager");
			}
			
			return accountListViewAdmin;
		}
		
		/*
		 * 16.a Hàm lấy về List danh sách view AccountManager trong trang chủ của Admin cho cấp id_account_code = 4 (Lấy toàn bộ tài khoản) với searchkey
		 * Ứng dụng: Có thể thêm câu lệnh where id_account_code < ? (Cấp trên sẽ quản lí cấp dưới, hoặc ngược lại > ? tuỳ cách sắp xếp cấp độ quản lý tại CSDL)
		 * 
		 */
		public List<AccountManager> getAccountManagerSearchKeyListViewAdmins(String searchkey, int pagecurrent, int number1page){
			pagecurrent = (pagecurrent - 1) * number1page;
			
			List<AccountManager> accountListView = new ArrayList<AccountManager>();
			
			try {
				String SQL = "Select A.id_account, A.id_account_code, AC.name_en as account_level, A.email, A.name as account_name, A.province_code, P.name as province_name, A.last_login_date, A.status, AST.status_name, S.sum_donate_account\r\n"
						+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
						+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
						+ "left join Provinces P on P.code = A.province_code\r\n"
						+ "left join Account_status AST on AST.status_code = A.status\r\n"
						+ "where AC.name_en like N'%" + searchkey + "%' or  A.email like N'%" + searchkey + "%' or A.name like N'%" + searchkey + "%' or P.name like N'%" + searchkey + "%' or AST.status_name like N'%" + searchkey + "%' or S.sum_donate_account like N'%" + searchkey + "%'"
						+ " Order by A.id_account ASC OFFSET ? Rows Fetch next ? rows ONLY";

				accountListView = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new AccountManagerMapper());
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không có danh sách AccountManager");
			}
			
			return accountListView;
		}
		
		/*
		 * 16.b Hàm tìm kiếm: Lấy về List danh sách view AccountManager trong trang chủ của Admin cho cấp id_account_code = 3, với searchkey
		 * 
		 */
		public List<AccountManager> getAccountManagerSearchKeyListViewManager(String searchkey, int pagecurrent, int number1page) {
			
			pagecurrent = (pagecurrent - 1) * number1page;
			
			List<AccountManager> accountListView = new ArrayList<AccountManager>();
			
			try {
				String SQL = "Select A.id_account, A.id_account_code, AC.name_en as account_level, A.email, A.name as account_name, A.province_code, P.name as province_name, A.last_login_date, A.status, AST.status_name, S.sum_donate_account\r\n"
						+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
						+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
						+ "left join Provinces P on P.code = A.province_code\r\n"
						+ "left join Account_status AST on AST.status_code = A.status\r\n"
						+ "where A.id_account_code in (1,2,5) and ( AC.name_en like N'%" + searchkey + "%' or  A.email like N'%" + searchkey + "%' or A.name like N'%" + searchkey + "%' or P.name like N'%" + searchkey + "%' or AST.status_name like N'%" + searchkey + "%' or S.sum_donate_account like N'%" + searchkey + "%')"
						+ " Order by A.id_account ASC OFFSET ? Rows Fetch next ? rows ONLY";

				accountListView = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new AccountManagerMapper());
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không có danh sách AccountManager");
			}
			
			return accountListView;
		}
		
		/*
		 * 17.a Lấy danh sách người ủng hộ một Campaign
		 * 
		 */
		public List<AccountDonate1Campaign> getListAccountDonate1Campaign(int id_campaign, int pagecurrent,int number1page) {
			pagecurrent = (pagecurrent - 1) * number1page;
			
			List<AccountDonate1Campaign> accountDonateList = new ArrayList<AccountDonate1Campaign>();
			
			try {
				String SQL = "Select donate_money, donate_date, email, AC.name_en as account_code_name, P.name as province_name from Donate D \r\n"
							+ "left join Account A on D.id_account = A.id_account\r\n"
							+ "left join Account_code AC on AC.id_account_code = A.id_account_code\r\n"
							+ "left join Provinces P on P.code = A.province_code\r\n"
							+ "Where D.id_campaign = ? Order by donate_date DESC\r\n"
							+ "OFFSET ? Rows Fetch next ? rows ONLY";

				accountDonateList = jdbcTemplateObject.query(SQL, new Object[]{id_campaign, pagecurrent, number1page}, new AccountDonate1CampaignMapper());
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không có danh sách AccountDonate1Campaign");
			}
			
			return accountDonateList;
		}
		
		/*
		 * 17.a Lấy danh sách người ủng hộ một Campaign
		 * 
		 */
		public List<AccountDonate1Campaign> getListAccountDonate1Campaign(int id_campaign) {
			
			List<AccountDonate1Campaign> accountDonateList = new ArrayList<AccountDonate1Campaign>();
			
			try {
				String SQL = "Select donate_money, donate_date, email, AC.name_en as account_code_name, P.name as province_name from Donate D \r\n"
							+ "left join Account A on D.id_account = A.id_account\r\n"
							+ "left join Account_code AC on AC.id_account_code = A.id_account_code\r\n"
							+ "left join Provinces P on P.code = A.province_code\r\n"
							+ "Where D.id_campaign = ? Order by donate_date DESC";

				accountDonateList = jdbcTemplateObject.query(SQL, new Object[]{id_campaign}, new AccountDonate1CampaignMapper());
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không có danh sách AccountDonate1Campaign");
			}
			
			return accountDonateList;
		}
		
		/*
		 * 17.c Lấy số lượng tài khoản người ủng hộ một Campaign
		 */
		
		@SuppressWarnings("deprecation")
		public int getListAccountDonate1CampaignTotalNumber (int id_campaign) {
			
			int number = 0;
			
			try {
				String SQL = "Select count (*) from Donate D \r\n"
						+ "left join Account A on D.id_account = A.id_account\r\n"
						+ "left join Account_code AC on AC.id_account_code = A.id_account_code\r\n"
						+ "left join Provinces P on P.code = A.province_code\r\n"
						+ "Where D.id_campaign = ?";
				
				number = jdbcTemplateObject.queryForInt(SQL, new Object[]{id_campaign});
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc chưa có tài khoản ủng hộ");
			}
			
			return number;
		}
		
		/*
		 * 18. Kiểm tra điều kiện nâng cấp một tài khoản
		 */
		
		public AccountConditionUp getCheckAccountConditionUp (int id_account) {
			AccountConditionUp account = new AccountConditionUp();
			
			try {
				String SQL = "Select A.id_account, A.id_account_code, AC.name_en as account_code_name, A.status, AC.condition_up, SDA.sum_donate_money from\r\n"
							+ "Account A join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
							+ "left join (Select id_account, SUM (donate_money) as sum_donate_money from Donate Group by id_account) as SDA on SDA.id_account = A.id_account\r\n"
							+ "Where A.id_account = ?";
				account = jdbcTemplateObject.queryForObject(SQL, new Object[] {id_account}, new AccountConditionUpMapper());
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có AccountConditionUp");
			}
			
			return account;
		}
		
		/*
		 * 19. Thực hiện nâng cấp tài khoản
		 */
		public void setAccountCode(int id_account_code, int id_account) {
			
			 try {
				 String SQL = "Update Account set id_account_code = ? where id_account = ?";
				 jdbcTemplateObject.update(SQL, new Object[]{id_account_code, id_account});
			     
			 } catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc không tồn tại Account đang tra");
			 }
		}
		
		/*
		 * 20.a Hàm lấy về tổng số lượng Account tất cả trạng thái (Cho cấp Admin id_account_code = 4). Để tạo phân trang tại Admin
		 */
		
		@SuppressWarnings("deprecation")
		public int getAccountManagerListViewAdminTotalNumber () {
			
			int numberAccount = 0;
			
			try {
				String SQL = "Select count (*) from Account";
				numberAccount = jdbcTemplateObject.queryForInt(SQL);
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có getAccountManagerListViewAdminTotalNumber1");
			}
			
			return numberAccount;
		}
		
		/*
		 * 20.b Hàm lấy về tổng số lượng Account theo 3 dữ liệu tìm kiếm (Sử dụng cho cấp Admin id_account_code = 4). Để tạo phân trang tại Admin
		 */
		
		@SuppressWarnings("deprecation")
		public int getAccountManagerListViewAdminTotalNumber (int id_account_code, String province_code, int status) {
			
			int numberAccount = 0;
			
			try {
				String SQL = "Select count (*) from Account Where id_account_code = ? and province_code = ? and status = ?";
				numberAccount = jdbcTemplateObject.queryForInt(SQL, new Object[]{id_account_code, province_code, status});
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có getAccountManagerListViewAdminTotalNumber2");
			}
			
			return numberAccount;
		}
		
		/*
		 * 20.c Hàm lấy về tổng số lượng Account theo 3 dữ liệu tìm kiếm (Sử dụng cho cấp Manager id_account_code = 3). Để tạo phân trang tại Admin
		 */
		
		@SuppressWarnings("deprecation")
		public int getAccountManagerListViewManagerTotalNumber (int id_account_code, String province_code, int status) {
			
			int numberAccount = 0;
			
			try {
				String SQL = "Select count (*) from Account Where id_account_code = ? and province_code = ? and status = ? and id_account_code in (1,2,5)";
				numberAccount = jdbcTemplateObject.queryForInt(SQL, new Object[]{id_account_code, province_code, status});
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có getAccountManagerListViewManagerTotalNumber3");
			}
			
			return numberAccount;
		}
		
		/*
		 * 20.d Hàm lấy về tổng số lượng Account theo searchkey (Sử dụng cho cấp Admin id_account_code = 4). Để tạo phân trang tại Admin
		 */
		
		@SuppressWarnings("deprecation")
		public int getAccountManagerListViewAdminTotalNumber (String searchkey) {
			
			int numberAccount = 0;
			
			try {
				String SQL = "Select count (*) \r\n"
							+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
							+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
							+ "left join Provinces P on P.code = A.province_code\r\n"
							+ "left join Account_status AST on AST.status_code = A.status\r\n"
							+ "where AC.name_en like N'%" + searchkey + "%' or  A.email like N'%" + searchkey + "%' or A.name like N'%" + searchkey + "%' or P.name like N'%" + searchkey + "%' or AST.status_name like N'%" + searchkey + "%' or S.sum_donate_account like N'%" + searchkey + "%'";
				
				numberAccount = jdbcTemplateObject.queryForInt(SQL);
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có getAccountManagerListViewAdminTotalNumber");
			}
			
			return numberAccount;
		}
		
		/*
		 * 20.e Hàm lấy về tổng số lượng Account theo searchkey (Sử dụng cho cấp Manager id_account_code = 3). Để tạo phân trang tại Admin
		 */
		
		@SuppressWarnings("deprecation")
		public int getAccountManagerListViewManagerTotalNumber (String searchkey) {
			
			int numberAccount = 0;
			
			try {
				String SQL = "Select count (*) \r\n"
						+ "from Account A left join (Select id_account, sum (donate_money) as sum_donate_account from Donate group by id_account) as S on S.id_account = A.id_account\r\n"
						+ "left join Account_code AC on A.id_account_code = AC.id_account_code\r\n"
						+ "left join Provinces P on P.code = A.province_code\r\n"
						+ "left join Account_status AST on AST.status_code = A.status\r\n"
						+ "where A.id_account_code in (1,2,5) and ( AC.name_en like N'%" + searchkey + "%' or  A.email like N'%" + searchkey + "%' or A.name like N'%" + searchkey + "%' or P.name like N'%" + searchkey + "%' or AST.status_name like N'%" + searchkey + "%' or S.sum_donate_account like N'%" + searchkey + "%')";
				
				numberAccount = jdbcTemplateObject.queryForInt(SQL);
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có getAccountManagerListViewManagerTotalNumber");
			}
			
			return numberAccount;
		}
		
		/*
		 * 21 Hàm lấy về tổng số lượng Account tất cả trạng thái (Cho cấp Manager id_account_code = 3). Để tạo phân trang tại Admin
		 */
		
		@SuppressWarnings("deprecation")
		public int getAccountManagerListViewManagerTotalNumber () {
			
			int numberAccount = 0;
			
			try {
				String SQL = "Select count (*) from Account Where id_account_code in (1,2,5)";
				numberAccount = jdbcTemplateObject.queryForInt(SQL);
				
			} catch (Exception e) {
				System.out.println("Không kết nối được CSDL, hoặc không có getAccountManagerListViewAdminTotalNumber");
			}
			
			return numberAccount;
		}
		
		/*
		 * 22. Thực hiện cập nhật trạng thái tài khoản thành kích hoạt
		 */
		public void confirmAccount (String email){
					
			try {
				String SQL = "Update Account set status = 2 where email = ?";

				jdbcTemplateObject.update(SQL, new Object[]{email});
				
			} catch (EmptyResultDataAccessException e) {
				System.out.println("Không kết nối được CSDL, hoặc chưa thể kích hoạt Account!");
				e.printStackTrace();
			}
		}
}
	