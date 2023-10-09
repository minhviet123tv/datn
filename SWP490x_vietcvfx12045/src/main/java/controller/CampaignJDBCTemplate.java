package controller;

import java.util.*;


import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import mapper.*;
import model.*;

/*
 * Xử lý dữ liệu của Campaign
 */
public class CampaignJDBCTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	/*
	 * 1. Cài đặt DataSource và JdbcTemplate cho lớp này (DataSource đã setup ở Beans.xml)
	 */
	public void setDataSource(DataSource dataSource) {
		 this.dataSource = dataSource;
	     this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	/*
	 * 2. Hàm get một Campaign với đầu vào là ID của Campaign đang muốn lấy, tìm. Nếu không có thì trả về một Campaign rỗng		
	 */
	public Campaign getCampaign (int ID) {
		Campaign campaign = new Campaign();
		
		try {
			String SQL = "select * from Campaign where id_campaign=?";
			campaign = jdbcTemplateObject.queryForObject(SQL, new Object[] {ID}, new CampaignMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL hoặc không có Campaign đang tìm");
		}
		
		return campaign;
	}
	
	/*
	 * 3. Hàm return ArrayList Campaign hiện có của dữ liệu (tại bảng Campaign, không cần đầu vào). Nếu không có Campaign nào thì trả về List rỗng
	 * Chú ý: Có thể cho đầu vào là ID của Account để tra lại lịch sử quyên góp của một Account (Có thể làm một hàm mới khác với ID là đầu vào)
	 */
	public List<Campaign> getListCampaign() {
		
		//Tạo một ArrayList rỗng (chứa các Campaign rỗng)
		List<Campaign> listCampaigns = new ArrayList<Campaign>();
		
		try {
			//Lấy List Campaign từ CSDL
			String SQL = "select * from Campaign";
			listCampaigns = jdbcTemplateObject.query(SQL, new CampaignMapper());
			
		} catch (EmptyResultDataAccessException e) {
			// Trường hợp không kết nối được CSDL, hoặc không có Campaign. Chỉ cần thông báo không cần điền lại listCampaigns. Vì sẽ trả về listCampaigns rỗng phía dưới cùng
			System.out.println("Không kết nối được CSDL, hoặc không có Campaign");
		}
		
		return listCampaigns;
	}
	
	/*
	 * 4.a Hàm lấy về List danh sách view của Campaign trong trang chủ của Admin. Chỉ lấy về những cột chỉ số cần lấy
	 */
	public List<CampaignListViewAdmin> getCampaignListViewAdmins(int pagecurrent, int number1page){
		
		//Điều chỉnh số thứ tự lấy thực tế từ CSDL theo số lượng sản phẩm trong một trang (Như vậy cần nhảy thứ tự là: số trang * số lượng trong một trang)
		pagecurrent = (pagecurrent - 1) * number1page;
		
		List<CampaignListViewAdmin> campaignListViewAdmin = new ArrayList<CampaignListViewAdmin>();
		
		try {
			String SQL = "Select C.id_campaign as id_campaign, C.id_campaign_code as id_campaign_code, CC.name_vn as name_vn, image_cover_link, Substring (title,1,20) as title, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, campaign_end_date, C.province_code as province_code, P.name as province_name, sum_donate_money, C.target_money as target_money, C.status as status,\r\n"
					+ "CASE WHEN C.status = 0 THEN N'Lưu nháp' WHEN C.status = 1 THEN N'Đang quyên góp' WHEN C.status = 2 THEN N'Đã đạt mục tiêu' WHEN C.status = 3 THEN N'Đã trao quà' WHEN C.status = 4 THEN N'Xoá' ELSE N'Chưa đặt tên' END as status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "Order by C.id_campaign DESC \r\n"
					+ "OFFSET ? Rows Fetch next ? rows ONLY";

			campaignListViewAdmin = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new CampaignListViewAdminMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewAdmin");
		}
		
		return campaignListViewAdmin;
	}
	
	/*
	 * 4.b Hàm lấy về List danh sách view Campaign của một account (đang đăng nhập trang Admin). Chỉ lấy về những cột chỉ số cần lấy
	 * Lưu ý không thể dùng Select TOP cho hàm phân trang, vì bản thân chọn theo trang chính là đã chọn số lượng cụ thể
	 */
	public List<CampaignListViewAdmin> getMyCampaignListViewAdmins(int id_account, int pagecurrent, int number1page){
		
		//Điều chỉnh số thứ tự lấy thực tế từ CSDL theo số lượng sản phẩm trong một trang (Như vậy cần nhảy thứ tự là: số trang * số lượng trong một trang)
		pagecurrent = (pagecurrent - 1) * number1page;
		
		List<CampaignListViewAdmin> campaignListViewAdmin = new ArrayList<CampaignListViewAdmin>();
		
		try {
			String SQL = "Select C.id_campaign as id_campaign, C.id_campaign_code as id_campaign_code, CC.name_vn as name_vn, image_cover_link, Substring (title,1,30) as title, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, \r\n"
					+ "campaign_end_date, C.province_code as province_code, P.name as province_name, sum_donate_money, C.target_money as target_money, C.status as status, CS.name_vn as status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join Campaign_status CS on CS.status = C.status\r\n"
					+ "Where C.id_account_write = ? or C.id_account_content_end = ? or C.id_account_last_edit = ?\r\n"
					+ "Order by CAST(C.account_last_edit_date as datetime) DESC \r\n"
					+ "OFFSET ? Rows Fetch next ? rows ONLY";

			campaignListViewAdmin = jdbcTemplateObject.query(SQL, new Object[]{id_account, id_account, id_account, pagecurrent, number1page}, new CampaignListViewAdminMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewAdmin");
		}
		
		return campaignListViewAdmin;
	}
	
	/*
	 * 4.c Hàm lấy về List danh sách view của Campaign trong trang chủ của Admin. Chỉ lấy về những cột chỉ số cần lấy
	 */
	public List<CampaignListViewAdmin> getCampaignListViewAdmins(){
		List<CampaignListViewAdmin> campaignListViewAdmin = new ArrayList<CampaignListViewAdmin>();
		
		try {
			String SQL = "Select C.id_campaign as id_campaign, C.id_campaign_code as id_campaign_code, CC.name_vn as name_vn, image_cover_link, Substring (title,1,150) as title, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, campaign_end_date, C.province_code as province_code, P.name as province_name, sum_donate_money, C.target_money as target_money, C.status as status,\r\n"
					+ "CASE WHEN C.status = 0 THEN N'Lưu nháp' WHEN C.status = 1 THEN N'Đang quyên góp' WHEN C.status = 2 THEN N'Đã đạt mục tiêu' WHEN C.status = 3 THEN N'Đã trao quà' WHEN C.status = 4 THEN N'Xoá' ELSE N'Chưa đặt tên' END as status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "Order by C.id_campaign DESC";

			campaignListViewAdmin = jdbcTemplateObject.query(SQL, new CampaignListViewAdminMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewAdmin");
		}
		
		return campaignListViewAdmin;
	}
	
	/*
	 * 5.a Hàm lấy về view của 1 Campaign trong trang chi tiết của mục Admin (Theo ID truyền vào). Lấy về những cột chỉ số cần lấy
	 * Ghi chú: Xử lý ngày đăng cuối content_end_date. Hiện tại là không dùng
	 */
	public View1Campaign getView1Campaign(int ID){
		View1Campaign view1Campaign = new View1Campaign();
		
		try {
			String SQL = "Select C.id_campaign as id_campaign, C.id_campaign_code as id_campaign_code, CC.name_vn as name_vn, image_cover_link, title, C.target_money as target_money, C.province_code as province_code, P.name as province_name, C.campaign_begin_date as campaign_begin_date, C.campaign_end_date as campaign_end_date, C.content_begin as content_begin, C.content_begin_date as content_begin_date,\r\n"
					+ "C.content_end, C.content_end_date as content_end_date, C.id_account_write as id_account_write, email_write, C.id_account_content_end as id_account_content_end, email_content_end, C.id_account_last_edit as id_account_last_edit, email_last_edit, C.account_last_edit_date as account_last_edit_date,\r\n"
					+ "DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, sum_donate_money, C.status as status,\r\n"
					+ "CASE WHEN C.status = 0 THEN N'Lưu nháp' WHEN C.status = 1 THEN N'Đang quyên góp' WHEN C.status = 2 THEN N'Đã đạt mục tiêu' WHEN C.status = 3 THEN N'Đã trao quà' WHEN C.status = 4 THEN N'Xoá' ELSE N'Chưa đặt tên' END as status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join (select id_account, email as email_write from Account) as A1 on A1.id_account = C.id_account_write\r\n"
					+ "left join (select id_account, email as email_content_end from Account) as A2 on A2.id_account = C.id_account_content_end\r\n"
					+ "left join (select id_account, email as email_last_edit from Account) as A3 on A3.id_account = C.id_account_last_edit\r\n"
					+ "\r\n"
					+ "Where C.id_campaign = ?";
			
			view1Campaign = jdbcTemplateObject.queryForObject(SQL, new Object[] {ID}, new View1CampaignMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có View1Campaign");
		}
		
		return view1Campaign;
	}
	
	/*
	 * 5.b Hàm lấy về view của 1 Campaign trong trang chi tiết của mục Admin (Theo ID truyền vào). Lấy về những cột chỉ số cần lấy
	 * Ghi chú: Xử lý ngày đăng cuối content_end_date. Hiện tại là không dùng
	 */
	public View1CampaignIndex getView1CampaignIndex(int ID){
		View1CampaignIndex view1CampaignIndex = new View1CampaignIndex();
		
		try {
			String SQL = "Select C.id_campaign, C.id_campaign_code, CC.name_vn as campaign_code_name, image_cover_link, title, C.target_money , C.province_code , P.name as province_name, C.campaign_begin_date , C.campaign_end_date, C.content_begin, content_begin_date, email_post_content_begin,\r\n"
					+ "content_end, C.content_end_date, email_post_content_end, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, sum_donate_money, donate_number_total, C.status as status, status_name\r\n"
					+ "from Campaign C\r\n"
					+ "left join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join (Select id_campaign, COUNT (id_campaign) as donate_number_total from Donate Group by id_campaign) as NT on NT.id_campaign = C.id_campaign\r\n"
					+ "left join (Select status, name_vn as status_name from Campaign_status) as CS on CS.status = C.status\r\n"
					+ "left join (Select id_account_write, A.email as email_post_content_begin from Campaign C join Account A on C.id_account_write = A.id_account Where C.id_campaign = ?) as EPCB on EPCB.id_account_write = C.id_account_write\r\n"
					+ "left join (Select id_account_content_end, A.email as email_post_content_end from Campaign C join Account A on C.id_account_content_end = A.id_account Where C.id_campaign = ?) as EPCE on EPCE.id_account_content_end = C.id_account_content_end\r\n"
					+ "Where C.id_campaign = ?";
			
			view1CampaignIndex = jdbcTemplateObject.queryForObject(SQL, new Object[] {ID, ID, ID}, new View1CampaignIndexMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có View1Campaign");
		}
		
		return view1CampaignIndex;
	}
	
	/*
	 * 6. Hàm lấy về List danh sách của CampaignCode để hiển thị trong trang New Campaign.
	 */
	public List<CampaignCode> getListCampaignCode(){
		List<CampaignCode> listCampaignCode = new ArrayList<CampaignCode>();
		
		try {
			String SQL = "Select * from Campaign_code Order by name_characters ASC";

			listCampaignCode = jdbcTemplateObject.query(SQL, new CampaignCodeMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignCode");
		}
		
		return listCampaignCode;
	}
	
	/*
	 * 7.a Hàm lấy về List danh sách của Provinces để hiển thị trong trang New Campaign.
	 */
	public List<Provinces> getListProvinces(){
		List<Provinces> listProvinces = new ArrayList<Provinces>();
		
		try {
			String SQL = "Select * from Provinces Order by code_name ASC";

			listProvinces = jdbcTemplateObject.query(SQL, new ProvincesMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có Provinces");
		}
		
		return listProvinces;
	}
	
	/*
	 * 7.b Hàm lấy về List danh sách của Provinces - Loại trừ mã đang sử dụng của Account
	 */
	public List<Provinces> getListProvinces(String province_code){
		List<Provinces> listProvinces = new ArrayList<Provinces>();
		
		try {
			String SQL = "Select * from Provinces where code != " + province_code+ " Order by code_name ASC";

			listProvinces = jdbcTemplateObject.query(SQL, new ProvincesMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có Provinces");
		}
		
		return listProvinces;
	}
	
	/*
	 * 7.a Hàm lấy về List danh sách của Provinces - Không có mã 00
	 */
	public List<Provinces> getListProvincesClearn(){
		List<Provinces> listProvinces = new ArrayList<Provinces>();
		
		try {
			String SQL = "Select * from Provinces where code != '00' Order by code_name ASC";

			listProvinces = jdbcTemplateObject.query(SQL, new ProvincesMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có Provinces");
		}
		
		return listProvinces;
	}
	
	/*
	 * 8. Thêm một Campaign mới với đầy đủ các thông tin (full cột trong bảng CSDL, trừ id_campaign là tự động))
	 * Tại đây chưa có giá trị cụ thể, trong servlet controller sẽ set giá trị (lấy được từ form) cho từng cột. Riêng cột nào chưa cần điền thông tin sẽ điền giá trị null
	 */
	public void newCampaign (int id_campaign_code, Long target_money, int id_account_write, int status, String image_cover_link,
			String title, String province_code, String content_begin, Date campaign_begin_date, Date campaign_end_date,
			String content_begin_date){
		
		try {
			String SQL = "Insert into Campaign (id_campaign_code, image_cover_link, title, target_money, province_code, campaign_begin_date, campaign_end_date, content_begin, content_begin_date, id_account_write, status) values\r\n"
					+ "(?,?,?,?,?,?,?,?,?,?,?)";

			jdbcTemplateObject.update(SQL, new Object[]{id_campaign_code, image_cover_link, title, target_money, province_code, campaign_begin_date, campaign_end_date, content_begin, content_begin_date, id_account_write, status});
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không thể thêm Campaign mới");
		}
	}
	
	/*
	 * 9. Thêm một Campaign mới với đầy đủ các thông tin (full cột trong bảng CSDL, trừ id_campaign là tự động))
	 * Tại đây chưa có giá trị cụ thể, trong servlet controller sẽ set giá trị (lấy được từ form) cho từng cột. Riêng cột nào chưa cần điền thông tin sẽ điền giá trị null
	 */
	public void updateContentEnd(String content_end, String content_end_date, int id_account_content_end, int status, int id_campaign){
		
		try {
			String SQL = "Update Campaign set content_end=?, content_end_date=?, id_account_content_end=?, status=? where id_campaign=?";

			jdbcTemplateObject.update(SQL, new Object[]{content_end, content_end_date, id_account_content_end, status, id_campaign});
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không thể thêm Campaign mới");
		}
	}
	
	/*
	 * 10. Edit một Campaign
	 */
	public void edit1Campaign (int id_campaign_code, String image_cover_link, String title, Long target_money, String province_code, String campaign_begin_date, String campaign_end_date, String content_begin, String content_end, int id_account_last_edit, String account_last_edit_date, int status, int id_campaign){
		
		try {
			String SQL = "Update Campaign set id_campaign_code = ?, image_cover_link = ?, title = ?, target_money = ?, province_code = ?, campaign_begin_date = ?, campaign_end_date = ?, content_begin = ?, content_end = ?, id_account_last_edit = ?, account_last_edit_date = ?, status = ?\r\n"
					+ "where id_campaign = ?";

			jdbcTemplateObject.update(SQL, new Object[]{id_campaign_code, image_cover_link, title, target_money, province_code, campaign_begin_date, campaign_end_date, content_begin, content_end, id_account_last_edit, account_last_edit_date, status, id_campaign});
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không thể chỉnh sửa Campaign!");
		}
	}

	/*
	 * 11. Xoá một Campaign
	 */
	public void delete1Campaign (int id_account_last_edit, Date account_last_edit_date, int id_campaign){
		
		try {
			String SQL = "Update Campaign set status = 4, id_account_last_edit = ?, account_last_edit_date = ? where id_campaign = ?";

			jdbcTemplateObject.update(SQL, new Object[]{id_account_last_edit, account_last_edit_date, id_campaign});
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc chưa thể chuyển trạng thái sang xoá Campaign!");
		}
	}
	
	/*
	 * 12.a Lọc danh sách Campaign theo một trong các tiêu chí: id_campaign_code, province_code hoặc status
	 */
	public List<CampaignListViewAdmin> searchListCampaign(int id_campaign_code, String province_code, int status, int pagecurrent, int number1page){
		
		//Điều chỉnh số thứ tự lấy thực tế từ CSDL theo số lượng sản phẩm trong một trang (Như vậy cần nhảy thứ tự là: số trang * số lượng trong một trang)
		pagecurrent = (pagecurrent - 1) * number1page;
		
		List<CampaignListViewAdmin> searchListCampaign = new ArrayList<CampaignListViewAdmin>();
		
		try {
			String SQL = "Select C.id_campaign as id_campaign, C.id_campaign_code as id_campaign_code, CC.name_vn as name_vn, image_cover_link, Substring (title,1,50) as title, campaign_begin_date, campaign_end_date, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, C.province_code as province_code, P.name as province_name, sum_donate_money, sum_donate_count, C.target_money as target_money, C.status as status,\r\n"
					+ "CASE WHEN C.status = 0 THEN N'Lưu nháp' WHEN C.status = 1 THEN N'Đang quyên góp' WHEN C.status = 2 THEN N'Đã đạt mục tiêu' WHEN C.status = 3 THEN N'Đã trao quà' WHEN C.status = 4 THEN N'Xoá' ELSE N'Chưa đặt tên' END AS status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join (select  id_campaign, count(id_campaign) as sum_donate_count FROM Donate Group by id_campaign) as Count_Donate on Count_Donate.id_campaign = C.id_campaign\r\n"
					+ "Where C.id_campaign_code = ? or province_code = ? or C.status = ?\r\n"
					+ "Order by remaining_date DESC\r\n"
					+ "OFFSET ? Rows Fetch next ? rows ONLY";

			searchListCampaign = jdbcTemplateObject.query(SQL, new Object[]{id_campaign_code, province_code, status, pagecurrent, number1page}, new CampaignListViewAdminMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có Campaign");
		}
		
		return searchListCampaign;
	}
	
	/*
	 * 12.b Lọc danh sách Campaign theo searchkey trong trang Admin
	 */
	public List<CampaignListViewAdmin> searchListCampaignByKey(String searchkey, int pagecurrent, int number1page){
		
		pagecurrent = (pagecurrent - 1) * number1page;
		
		List<CampaignListViewAdmin> searchListCampaign = new ArrayList<CampaignListViewAdmin>();
		
		try {
			String SQL = "Select C.id_campaign as id_campaign, C.id_campaign_code as id_campaign_code, CC.name_vn as name_vn, image_cover_link, Substring (title,1,30) as title, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, campaign_end_date, C.province_code as province_code, P.name as province_name, sum_donate_money, C.target_money as target_money, C.status as status,\r\n"
					+ "CASE WHEN C.status = 0 THEN N'Lưu nháp' WHEN C.status = 1 THEN N'Đang quyên góp' WHEN C.status = 2 THEN N'Đã đạt mục tiêu' WHEN C.status = 3 THEN N'Đã trao quà' WHEN C.status = 4 THEN N'Xoá' ELSE N'Chưa đặt tên' END AS status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "where C.id_campaign like N'%" + searchkey + "%' or CC.name_vn like N'%" + searchkey + "%' or C.title like N'%" + searchkey + "%' or P.name like N'%" + searchkey + "%' or C.target_money like N'%" + searchkey + "%'\r\n"
					+ "Order by C.id_campaign DESC \r\n"
					+ "OFFSET ? Rows Fetch next ? rows ONLY";

			searchListCampaign = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new CampaignListViewAdminMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có Campaign");
		}
		
		return searchListCampaign;
	}
	
	/*
	 * 13.a.1 Hàm lấy về tổng số lượng Campaign theo tình trạng status (1. đang quyên góp, 2. Đạt mục tiêu, 3. Đã trao quà). Để tạo phân trang index
	 */
	
	@SuppressWarnings("deprecation")
	public int getCampaignListViewIndexTotalNumber (int status) {
		
		int numberCampaign = 0;
		
		try {
			String SQL = "Select count (*) from Campaign where status = ?";
			numberCampaign = jdbcTemplateObject.queryForInt(SQL, new Object[]{status});
			
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndexTotalNumber1");
		}
		
		return numberCampaign;
	}
	
	/*
	 * 13.a.2 Hàm lấy về tổng số lượng Campaign tất cả trạng thái. Để tạo phân trang tại IndexAdmin
	 * Chọn đếm đến TOP 1000 campaign gần nhất, để giảm tải hiển thị
	 * Số lượng trang thực tế còn phụ thuộc số lượng của 1 trang
	 */
	
	@SuppressWarnings("deprecation")
	public int getCampaignListViewAdminTotalNumber () {
		
		int numberCampaign = 0;
		
		try {
			String SQL = "Select count (*) from (Select TOP (1000) id_campaign from Campaign) as TopCampaign";
			numberCampaign = jdbcTemplateObject.queryForInt(SQL);
			
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndexTotalNumber2");
		}
		
		return numberCampaign;
	}
	
	/*
	 * 13.a.3 Hàm lấy về tổng số lượng Campaign theo 3 dữ liệu truyền vào (Khi tìm kiếm). Để tạo phân trang tại IndexAdmin
	 */
	
	@SuppressWarnings("deprecation")
	public int getCampaignListViewAdminTotalNumber (int id_campaign_code, String province_code, int status) {
		
		int numberCampaign = 0;
		
		try {
			
			String SQL = "Select count (*) from Campaign Where id_campaign_code = ? or province_code = ? or status = ?";
			numberCampaign = jdbcTemplateObject.queryForInt(SQL, new Object[]{id_campaign_code, province_code, status});
			
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndexTotalNumber3");
		}
		
		return numberCampaign;
	}
	
	/*
	 * 13.a.4 Hàm lấy về tổng số lượng Campaign theo searchkey. Để tạo phân trang tại IndexAdmin
	 */
	
	@SuppressWarnings("deprecation")
	public int getCampaignListViewAdminTotalNumberSearchKey (String searchkey) {
		
		int numberCampaign = 0;
		
		try {
			String SQL = "Select count (*) from\r\n" 
						 + "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
						 + "left join Provinces P on C.province_code = P.code\r\n" 
						 + "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
						 + "where C.id_campaign like N'%" + searchkey + "%' or CC.name_vn like N'%" + searchkey + "%' or C.title like N'%" + searchkey + "%' or P.name like N'%" + searchkey + "%' or C.target_money like N'%" + searchkey + "%'\r\n" ;
			
			numberCampaign = jdbcTemplateObject.queryForInt(SQL);
		
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndexTotalNumber4");
		}
		
		return numberCampaign;
	}
	
	/*
	 * 13.a.5 Hàm lấy về tổng số lượng Campaign đã từng tham gia của một account (trang Admin). Để tạo phân trang (Số lượng trang tuỳ thuộc số lượng sản phẩm và số lượng hiển thị trong 1 trang)
	 * Riêng hàm này lấy theo TOP (500) (Nhằm giảm số trang hiển thị để sử dụng trong trường hợp quá nhiều, ví dụ như hàng 1000000). Còn mỗi trang view vẫn chỉ lấy và hiện số lượng theo 1 trang
	 * Lưu ý không thể dùng Select TOP cho hàm chọn theo trang
	 */
	
	@SuppressWarnings("deprecation")
	public int getMyCampaignListViewAdminTotalNumber (int id_account) {
		
		int numberCampaign = 0;
		
		try {
			String SQL = "Select count(*) from (Select TOP (500) C.id_campaign from\r\n"
						+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
						+ "left join Provinces P on C.province_code = P.code\r\n"
						+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
						+ "left join Campaign_status CS on CS.status = C.status\r\n"
						+ "Where C.id_account_write = ? or C.id_account_content_end = ? or C.id_account_last_edit = ?) as CountTopTable";
			numberCampaign = jdbcTemplateObject.queryForInt(SQL, new Object[]{id_account, id_account, id_account});
			
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndexTotalNumber5");
		}
		
		return numberCampaign;
	}
	
	/*
	 * 13.a.6 Hàm lấy về tổng số lượng Campaign theo searchkey. Để tạo phân trang tại IndexAdmin
	 */
	
	@SuppressWarnings("deprecation")
	public int getSearchListCampaignIndexByKeyTotalNumber (String searchkey) {
		
		int numberCampaign = 0;
		
		try {
			String SQL = "Select COUNT (*) from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join (select  id_campaign, count(id_campaign) as sum_donate_count FROM Donate Group by id_campaign) as Count_Donate on Count_Donate.id_campaign = C.id_campaign\r\n"
					+ "left join Campaign_status CS on C.status = CS.status\r\n"
					+ "Where CC.name_vn like N'%" + searchkey + "%' or C.title like N'%" + searchkey + "%' or P.name like N'%" + searchkey + "%' or C.target_money like N'%" + searchkey + "%' or CS.name_vn like N'%" + searchkey + "%'\r\n";
			
			numberCampaign = jdbcTemplateObject.queryForInt(SQL);
		
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndexTotalNumber6");
		}
		
		return numberCampaign;
	}
	
	/*
	 * 13.b Hàm lấy về tổng số lượng Campaign theo tình trạng status, id_campaign_code, và province_code (Hàm tìm kiếm chọn lọc). Để tạo phân trang khi tìm kiếm
	 */
	
	@SuppressWarnings("deprecation")
	public int getCampaignListViewIndexTotalNumber (int status, int id_campaign_code, String province_code) {
		
		int numberCampaign = 0;
		
		try {
			String SQL = "Select count (*) from Campaign Where (status = ? and id_campaign_code = ?) or (status = ? and province_code = ?)";
			numberCampaign = jdbcTemplateObject.queryForInt(SQL, new Object[]{status, id_campaign_code, status, province_code});
			
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndexTotalNumber6");
		}
		
		return numberCampaign;
	}
	
	/*
	 * 13.c Hàm lấy về List danh sách view của Campaign (theo status) tại trang người dùng theo dữ liệu đầu vào để phân trang
	 */
	public List<CampaignListViewIndex> getCampaignListViewIndex(int status, int pagecurrent, int number1page){
		
		//Điều chỉnh số thứ tự lấy thực tế từ CSDL theo số lượng sản phẩm trong một trang (Như vậy cần nhảy thứ tự là: số trang * số lượng trong một trang)
		pagecurrent = (pagecurrent - 1) * number1page;
		
		List<CampaignListViewIndex> campaignListViewIndex = new ArrayList<CampaignListViewIndex>();
		
		try {
			String SQL = "Select C.id_campaign, C.id_campaign_code, CC.name_vn, image_cover_link,  Substring (C.title,1,150) as title, campaign_begin_date, campaign_end_date, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, C.province_code, P.name as province_name, sum_donate_money, sum_donate_count, C.target_money, C.status,\r\n"
					+ "CASE WHEN C.status = 0 THEN N'Lưu nháp' WHEN C.status = 1 THEN N'Quyên góp' WHEN C.status = 2 THEN N'Đạt mục tiêu' WHEN C.status = 3 THEN N'Đã trao quà' WHEN C.status = 4 THEN N'Xoá' ELSE N'Chưa đặt tên' END AS status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join (select id_campaign, count(id_campaign) as sum_donate_count FROM Donate Group by id_campaign) as Count_Donate on Count_Donate.id_campaign = C.id_campaign\r\n"
					+ "Where C.status = ? Order by remaining_date ASC, id_campaign ASC\r\n"
					+ "OFFSET ? Rows Fetch next ? rows ONLY";

			campaignListViewIndex = jdbcTemplateObject.query(SQL, new Object[]{status, pagecurrent, number1page}, new CampaignListViewIndexMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndex");
		}
		
		return campaignListViewIndex;
	}
	
	
	/*
	 * 13.d Hàm lấy về List danh sách view của Campaign có cùng hoàn cảnh, trạng thái, ngoại trừ campaign đang xem được gửi lên
	 * Dùng trong danh sách cùng hoàn cảnh khi xem 1 campaign
	 */
	public List<CampaignListViewIndex> getCampaignListViewIndexCode(int status,int id_campaign_code, int id_campaign){
		List<CampaignListViewIndex> campaignListViewIndex = new ArrayList<CampaignListViewIndex>();
		
		try {
			String SQL = "Select TOP 8 C.id_campaign, C.id_campaign_code, CC.name_vn, image_cover_link,  Substring (C.title,1,150) as title, campaign_begin_date, campaign_end_date, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, C.province_code, P.name as province_name, sum_donate_money, sum_donate_count, C.target_money, C.status,\r\n"
					+ "CASE WHEN C.status = 0 THEN N'Lưu nháp' WHEN C.status = 1 THEN N'Quyên góp' WHEN C.status = 2 THEN N'Đạt mục tiêu' WHEN C.status = 3 THEN N'Đã trao quà' WHEN C.status = 4 THEN N'Xoá' ELSE N'Chưa đặt tên' END AS status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join (select id_campaign, count(id_campaign) as sum_donate_count FROM Donate Group by id_campaign) as Count_Donate on Count_Donate.id_campaign = C.id_campaign\r\n"
					+ "Where C.status = ? and C.id_campaign_code = ? and C.id_campaign != ? Order by C.id_campaign DESC, remaining_date DESC ";

			campaignListViewIndex = jdbcTemplateObject.query(SQL, new Object[]{status, id_campaign_code, id_campaign}, new CampaignListViewIndexMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndex");
		}
		
		return campaignListViewIndex;
	}
	
	/*
	 * 14.a Lọc danh sách Campaign theo id_campaign_code (Cho trang chủ người dùng index)
	 */
	public List<CampaignListViewIndex> searchListCampaignIndex(int status, int id_campaign_code, String province_code, int pagecurrent, int number1page){
		
		//Điều chỉnh số thứ tự lấy thực tế từ CSDL theo số lượng sản phẩm trong một trang (Như vậy cần nhảy thứ tự là: số trang * số lượng trong một trang)
		pagecurrent = (pagecurrent - 1) * number1page;
		
		List<CampaignListViewIndex> searchListCampaign = new ArrayList<CampaignListViewIndex>();
		
		try {
			String SQL = "Select C.id_campaign as id_campaign, C.id_campaign_code as id_campaign_code, CC.name_vn as name_vn, image_cover_link, Substring (title,1,150) as title, campaign_begin_date, campaign_end_date, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, C.province_code as province_code, P.name as province_name, sum_donate_money, sum_donate_count, C.target_money as target_money, C.status as status,\r\n"
					+ "CASE WHEN C.status = 0 THEN N'Lưu nháp' WHEN C.status = 1 THEN N'Đang quyên góp' WHEN C.status = 2 THEN N'Đã đạt mục tiêu' WHEN C.status = 3 THEN N'Đã trao quà' WHEN C.status = 4 THEN N'Xoá' ELSE N'Chưa đặt tên' END AS status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join (select  id_campaign, count(id_campaign) as sum_donate_count FROM Donate Group by id_campaign) as Count_Donate on Count_Donate.id_campaign = C.id_campaign\r\n"
					+ "Where (C.status = ? and C.id_campaign_code = ?) or (C.status = ? and province_code = ?) \r\n"
					+ "Order by remaining_date ASC, id_campaign ASC \r\n"
					+ "OFFSET ? Rows Fetch next ? rows ONLY";

			searchListCampaign = jdbcTemplateObject.query(SQL, new Object[]{status, id_campaign_code, status, province_code, pagecurrent, number1page}, new CampaignListViewIndexMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndex");
		}
		
		return searchListCampaign;
	}
	
	/*
	 * 14.b Tìm kiếm Campaign theo key (Cho trang chủ người dùng index)
	 */
	public List<CampaignListViewIndex> searchListCampaignIndexByKey(String searchkey, int pagecurrent, int number1page){
		
		//Điều chỉnh số thứ tự lấy thực tế từ CSDL theo số lượng sản phẩm trong một trang (Như vậy cần nhảy thứ tự là: số trang * số lượng trong một trang)
		pagecurrent = (pagecurrent - 1) * number1page;
		
		List<CampaignListViewIndex> searchListCampaign = new ArrayList<CampaignListViewIndex>();
		
		try {
			String SQL = "Select C.id_campaign as id_campaign, C.id_campaign_code as id_campaign_code, CC.name_vn as name_vn, image_cover_link, Substring (C.title,1,150) as title, campaign_begin_date, campaign_end_date, DATEDIFF (day, getDate(), campaign_end_date) AS remaining_date, \r\n"
					+ "C.province_code as province_code, P.name as province_name, sum_donate_money, sum_donate_count, C.target_money as target_money, C.status as status, CS.name_vn AS status_name\r\n"
					+ "from\r\n"
					+ "Campaign C join Campaign_code CC on C.id_campaign_code = CC.id_campaign_code\r\n"
					+ "left join Provinces P on C.province_code = P.code\r\n"
					+ "left join (select  id_campaign, SUM(donate_money) as sum_donate_money FROM Donate Group by id_campaign) as S on S.id_campaign = C.id_campaign\r\n"
					+ "left join (select  id_campaign, count(id_campaign) as sum_donate_count FROM Donate Group by id_campaign) as Count_Donate on Count_Donate.id_campaign = C.id_campaign\r\n"
					+ "left join Campaign_status CS on C.status = CS.status\r\n"
					+ "Where CC.name_vn like N'%" + searchkey + "%' or C.title like N'%" + searchkey + "%' or P.name like N'%" + searchkey + "%' or C.target_money like N'%" + searchkey + "%' or CS.name_vn like N'%" + searchkey + "%' AND C.status in (1,2,3)\r\n"
					+ "Order by remaining_date ASC \r\n"
					+ "OFFSET ? Rows Fetch next ? rows ONLY";

			searchListCampaign = jdbcTemplateObject.query(SQL, new Object[]{pagecurrent, number1page}, new CampaignListViewIndexMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có CampaignListViewIndex");
		}
		
		return searchListCampaign;
	}
	
	/*
	 * 15. get một province
	 */
	public Provinces get1Provinces (String code) {
		Provinces province = new Provinces();
		
		try {
			String SQL = "Select * from Provinces where code = ?";
			province = jdbcTemplateObject.queryForObject(SQL, new Object[] {code}, new ProvincesMapper());
			
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có Province");
		}
		
		return province;
	}
	
	/*
	 * 16.a Hàm lấy về tổng số lượng Account tất cả trạng thái (Cho cấp Manager id_account_code = 3). Để tạo phân trang tại Admin
	 */
	
	@SuppressWarnings("deprecation")
	public int getHistoryDonateUserListTotalNumber(int id_account) {
		
		int numberAccount = 0;
		
		try {
			String SQL = "Select count (*) from Donate D\r\n"
						+ "join Campaign C on C.id_campaign = D.id_campaign\r\n"
						+ "join Campaign_code CC on CC.id_campaign_code = C.id_campaign_code\r\n"
						+ "join Provinces P on P.code = C.province_code\r\n"
						+ "join Campaign_status CS on CS.status = C.status\r\n"
						+ "Where D.id_account = ?";
			
			numberAccount = jdbcTemplateObject.queryForInt(SQL, new Object[] {id_account});
			
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có getAccountManagerListViewAdminTotalNumber");
		}
		
		return numberAccount;
	}
	

	/*
	 * 16.b Lấy lịch sử (danh sách) những Campaign đã được ủng hộ của một User - Theo id_account
	 */
	public List<HistoryDonateUser> getHistoryDonateUserList(int id_account, int pagecurrent, int number1page){
		
		pagecurrent = (pagecurrent - 1) * number1page;
		
		List<HistoryDonateUser> campaignListViewUser = new ArrayList<HistoryDonateUser>();
		
		try {
			String SQL = "Select D.id_account, D.id_campaign, C.id_campaign_code, D.donate_money, D.donate_date, C.target_money, CC.name_vn as campaign_code_name, C.image_cover_link, C.title, P.name as province_name, C.campaign_end_date, C.status as campaign_status, CS.name_vn as campaign_status_name from Donate D \r\n"
					+ "join Campaign C on C.id_campaign = D.id_campaign\r\n"
					+ "join Campaign_code CC on CC.id_campaign_code = C.id_campaign_code\r\n"
					+ "join Provinces P on P.code = C.province_code\r\n"
					+ "join Campaign_status CS on CS.status = C.status\r\n"
					+ "Where D.id_account = ? ORDER BY D.donate_date DESC "
					+ "OFFSET ? Rows Fetch next ? rows ONLY";

			campaignListViewUser = jdbcTemplateObject.query(SQL, new Object[] {id_account, pagecurrent, number1page}, new HistoryDonateUserMapper());
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không có HistoryDonateUserMapper");
		}
		
		return campaignListViewUser;
	}
	
	/*
	 * 17. Thêm dữ liệu ủng hộ vào bảng Donate mỗi khi có người quyên góp
	 */
	public void DonateFromUser (int id_account, int id_campaign, Long donate_money, String donate_date){
		
		try {
			String SQL = "Insert into Donate values (?, ?, ?, ?, ?)";

			jdbcTemplateObject.update(SQL, new Object[]{id_account, id_campaign, donate_money, donate_date, 1});
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không thể thêm Campaign mới");
		}
	}
	
	/*
	 * 18. Lấy các thông số thông tin về tình trạng, số tiền đã quyên góp / mục tiêu quyên góp, của một Campaign
	 */
	public DonateOneCampaign getDonateOneCampaign (int id_campaign) {
		
		DonateOneCampaign donate = new DonateOneCampaign();
		
		try {
			String SQL = "Select SumDonate.id_campaign, SumDonate.sum_donate_campaign, TargetCampaign.target_money, TargetCampaign.status  from\r\n"
						+ "(Select id_campaign, sum (donate_money) as sum_donate_campaign from Donate Group by id_campaign) as SumDonate\r\n"
						+ "left join (Select id_campaign, target_money, status from Campaign) as TargetCampaign on SumDonate.id_campaign = TargetCampaign.id_campaign\r\n"
						+ "Where SumDonate.id_campaign = ?";
			donate = jdbcTemplateObject.queryForObject(SQL, new Object[] {id_campaign}, new DonateOneCampaignMapper());
			
		} catch (Exception e) {
			System.out.println("Không kết nối được CSDL, hoặc không có DonateOneCampaign");
		}
		
		return donate;
	}
	
	/*
	 * 19. Cập nhật trạng thái của Campaign (Khi vừa đạt mục tiêu sau 1 lần quyên góp)
	 */
	public void updateCampaignStatus (int status, int id_campaign){
		
		try {
			String SQL = "Update Campaign set status = ? where id_campaign = ?";

			jdbcTemplateObject.update(SQL, new Object[]{status, id_campaign});
			
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Không kết nối được CSDL, hoặc không thể Update Campaign");
		}
	}
	
}
