package controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.*;

@Controller
public class StaffManagerController {
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("controller/Beans.xml"); //(địa chỉ của Beans.xml)
	private AccountJDBCTemplate accountJDBCTemplate = (AccountJDBCTemplate) context.getBean("AccountJDBCTemplate"); //(tên bean đã tạo ở Beans.xml)
	private CampaignJDBCTemplate campaignJDBCTemplate = (CampaignJDBCTemplate) context.getBean("CampaignJDBCTemplate"); //(tên bean đã tạo ở Beans.xml)
	private int number1page = 15;
	
	/*
	 * 1. Xử lý truy cập danh sách nhân viên theo từng cấp của admin, manager
	 */
	@RequestMapping (value = "/staffmanager.html", method = RequestMethod.GET)
	
	public String staffmanager (HttpServletRequest request, Model model) {
		
		//a. Lấy id_account_code của tài khoản đang đăng nhập (để truyền danh sách AccountManager tương ứng theo quyền của tài khoản)
		HttpSession session = request.getSession();
		int id_account_code = (Integer) session.getAttribute("id_account_code");
		
		//b. Lấy số thứ tự trang sẽ chọn (để xem) được gửi lên từ người dùng
		String indexpage = request.getParameter("indexpage");
		
		//Nếu là null thì set về trang đầu tiên (Trường hợp mới vào trang hoặc được chuyển tiếp sang trang hoặc chưa xác định)
		if (indexpage == null) {
			indexpage = "1";
		}
		
		//Chuyển số trang hiện tại được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
		
		//c. Tạo trước một số thông tin sẽ được phân nhánh theo cấp
		List<AccountManager> listAccount = new ArrayList<AccountManager>();
		List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
		int endPageAccount = 0;
		
		//d. Xử lý trường hợp tài khoản đang đăng nhập là admin
		if (id_account_code == 4) {
			
			//d.1 Lấy tổng số account đếm được
			int count = accountJDBCTemplate.getAccountManagerListViewAdminTotalNumber();
			
			//d.2 Xác định số lượng trang sẽ có với tổng số lượng đó
			endPageAccount = count / number1page;
				
				//Nếu tổng số Account / số lượng một trang mà dư thì tăng thêm một trang
				if ((count % number1page) > 0) {
					endPageAccount ++;
				}
			
			//d.3 Lấy danh sách tài khoản và code tài khoản cho cấp admin, theo phân trang (Nếu đánh thứ tự ở CSDL hợp lý thì có thể dùng 1 hàm cho cả 2 cấp)
			listAccount = accountJDBCTemplate.getAccountManagerListViewAdmins(indexcurrent, number1page);
			listAccountCodes = accountJDBCTemplate.getListAccountCodesAdmin();
			
		//e. Xử lý trường hợp tài khoản đăng nhập là Manager
		} else if (id_account_code == 3) {
			
			//e.1 Lấy tổng số account đếm được
			int count = accountJDBCTemplate.getAccountManagerListViewManagerTotalNumber();
			
			//e.2 Xác định số lượng trang sẽ có với tổng số lượng đó
			endPageAccount = count / number1page;
				
				//Nếu tổng số Account / số lượng một trang mà dư thì tăng thêm một trang
				if ((count % number1page) > 0) {
					endPageAccount ++;
				}
				
			listAccount = accountJDBCTemplate.getAccountManagerListViewManager(indexcurrent, number1page);
			listAccountCodes = accountJDBCTemplate.getListAccountCodesManager();
			
		}
		
		//b. Lấy danh sách code tỉnh thành và trạng thái (không phụ thuộc cấp Account). Truyền các dữ liệu vào session và model
		
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvincesClearn();
		List<AccountStatus> listAccountStatus = accountJDBCTemplate.getListAccountStatus();
		
		session.setAttribute("listAccount", listAccount);
		session.setAttribute("listAccountCodes", listAccountCodes);
		session.setAttribute("listProvinces", listProvinces);
		session.setAttribute("listAccountStatus", listAccountStatus);
		
		model.addAttribute("endPageAccount", endPageAccount);
		model.addAttribute("tagcurrent", indexcurrent);
		
		return "admin/staffmanager";
	}
	
	/*
	 * 2. Xem thông tin chi tiết của một Account tại trang Admin
	 */
	@RequestMapping (value = "/view1account.html", method = RequestMethod.GET)
	
	public String view1account (HttpServletRequest request, Model model) {
		//a. Lấy id_account của tài khoản đang đăng nhập và lấy đủ thông tin
		int id_account = Integer.parseInt(request.getParameter("id_account"));
		View1Account view1Account = accountJDBCTemplate.getView1Account(id_account);
		
		//b. Lấy tổng số lịch sử quyên góp đếm được để hiển thị tại trang
		int count = campaignJDBCTemplate.getHistoryDonateUserListTotalNumber(id_account);
		
		//Xác định số lượng trang sẽ có với tổng số lượng đó. Đặt lại số lượng của 1 trang
		number1page = 10;
		int endPage = count / number1page;
			
			//Nếu tổng số / số lượng một trang mà dư thì tăng thêm một trang
			if ((count % number1page) > 0) {
				endPage ++;
			}
			
		//c. Lấy số thứ tự trang đang chọn (để xem) được gửi lên từ người dùng
		String indexpage = request.getParameter("indexpage");
		
			//Nếu là null thì set về trang đầu tiên (Trường hợp mới vào trang hoặc được chuyển tiếp sang hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Chuyển số trang hiện tại được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
		
		//d. Lấy danh sách các list thông tin từ CSDL
		List<HistoryDonateUser> historyDonateUserList = campaignJDBCTemplate.getHistoryDonateUserList(id_account, indexcurrent, number1page);
		
		//b. Truyền vào model để xem ở trang view1account
		model.addAttribute("oneAccount", view1Account);
		model.addAttribute("historyDonateUserList", historyDonateUserList);
		
		model.addAttribute("tagcurrent", indexcurrent);
		model.addAttribute("endPage", endPage);
		
		return "admin/view1account";
	}
	
	/*
	 * 3. Xem thông tin chi tiết của một Account tại trang Admin
	 */
	@RequestMapping (value = {"/edit1accountpage.html"}, method = RequestMethod.POST)
	
	public String edit1accountpage (HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		//Thêm định dạng cho chữ, nếu không khi đọc xong sẽ bị lỗi (Khi dùng hàm POST)
		request.setCharacterEncoding("UTF-8");
		
		//a. Lấy id_account của user đang cần sửa để lấy đủ thông tin.
		int id_account = Integer.parseInt(request.getParameter("id_account"));
		View1Account oneAccount = accountJDBCTemplate.getView1Account(id_account);
		
		//Lấy id_account_code của tài khoản quản lý đang thực hiện chỉnh sửa cho user, để chọn danh sách phù hợp với cấp account_level
		HttpSession session = request.getSession();
		int id_account_code_current = (Integer) session.getAttribute("id_account_code");
		
		//b. Lấy danh sách các list thông tin để lựa chọn từ CSDL
		List<Gender> listGenders = accountJDBCTemplate.getListGenders(oneAccount.getGender());
		List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces(oneAccount.getProvince_code());
		List<AccountStatus> listAccountStatus = accountJDBCTemplate.getListAccountStatus(oneAccount.getStatus());
		
		//c. Xử lý riêng danh sách của account_code theo cấp tài khoản đang thao tác quản lý
		if (id_account_code_current == 4) {
			listAccountCodes = accountJDBCTemplate.getListAccountCodesAdmin(oneAccount.getId_account_code());
		} else if (id_account_code_current == 3) {
			listAccountCodes = accountJDBCTemplate.getListAccountCodesManager(oneAccount.getId_account_code());
		}
		
		//d. Truyền vào model để xem ở trang view1account
		model.addAttribute("oneAccount", oneAccount);
		model.addAttribute("listGenders", listGenders);
		model.addAttribute("listAccountCodes", listAccountCodes);
		model.addAttribute("listProvinces", listProvinces);
		model.addAttribute("listAccountStatus", listAccountStatus);
		
		return "admin/edit1account";
	}
	
	/*
	 * 4.Xử lý edit một Account tại trang Admin
	 */
	@RequestMapping (value = "/edit1account.html", method = RequestMethod.POST)

	public String edit1account (ModelMap model, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		//a. Nhận các dữ liệu chỉnh sửa từ trang gửi lên
		int id_account = Integer.parseInt(request.getParameter("id_account"));
		int id_account_code = Integer.parseInt(request.getParameter("id_account_code"));
		int gender = Integer.parseInt(request.getParameter("gender"));
		int status = Integer.parseInt(request.getParameter("status"));
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String account_name = request.getParameter("account_name");
		String phone = request.getParameter("phone");
		String province_code = request.getParameter("province_code");
		
		//b. Sử dụng hàm cập nhật dữ liệu
		accountJDBCTemplate.edit1Account(id_account_code, password, account_name, phone, province_code, status, gender, id_account);
		
		//c. Tạo thông báo và chuyển về trang thông báo
		model.addAttribute("message", "Đã chỉnh sửa xong Account: " + email);
		return "admin/message";
	}
	
	/*
	 * 5.Xoá 1 Account (Vẫn lưu dữ liệu nhưng chuyển sang trạng thái xoá)
	 */
	@RequestMapping (value = "/delete1account.html", method = RequestMethod.GET)

	public String delete1account (ModelMap model, HttpServletRequest request) throws ParseException {
		
		//a. Nhận dữ liệu dùng để chỉnh sửa từ trang gửi lên
		int id_account = Integer.parseInt(request.getParameter("id_account"));

		//b. Lấy thông tin để hiện thị và sử dụng hàm cập nhật dữ liệu để chuyển sang trạng thái xoá
		Account account = accountJDBCTemplate.getAccount(id_account);
		accountJDBCTemplate.delete1Account(id_account);
		
		//c. Tạo thông báo và chuyển về trang thông báo
		model.addAttribute("message", "Đã xoá xong Account ID" + id_account + " - " + account.getEmail());
		return "admin/message";
	}
	
	/*
	 * 6.a Xử lý Tìm kiếm danh sách chọn lọc
	 */
	@RequestMapping (value = "/searchListAccount.html", method = RequestMethod.GET)

	public String searchListAccount (ModelMap model, HttpServletRequest request) throws ParseException {
		//a. Lấy id_account_code của tk đang hoạt động để điều hướng. Và lấy các dữ liệu tìm kiếm gửi lên từ form
		HttpSession session = request.getSession();
		int id_account_code = (Integer) session.getAttribute("id_account_code");
		
		int capaccount = Integer.parseInt(request.getParameter("capaccount"));
		String tinhthanh = request.getParameter("tinhthanh");
		int trangthai = Integer.parseInt(request.getParameter("trangthai"));
		
		//b. Lấy số thứ tự trang đang chọn (để xem) được gửi lên từ người dùng
		String indexpage = request.getParameter("indexpage");
		
			//Nếu là null thì set về trang đầu tiên (Trường hợp mới vào trang index hoặc được chuyển tiếp sang index hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Chuyển số trang hiện tại được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
		
		//c. Tạo trước một số mẫu thông tin sẽ được phân nhánh theo cấp
			List<AccountManager> listAccount = new ArrayList<AccountManager>();
			List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
			int endPageAccount = 0;
		
		//d. Lấy danh sách AccountManager với những yếu tố tìm kiếm này (Phân nhánh theo cấp id_account_code)
		if (id_account_code == 3) {
			
			//1. Lấy tổng số Account đếm được để hiển thị tại trang indexAdmin theo 3 dữ liệu
			int count = accountJDBCTemplate.getAccountManagerListViewManagerTotalNumber(capaccount, tinhthanh, trangthai);
			
			//2. Xác định số lượng trang sẽ có với tổng số lượng đó
			endPageAccount = count / number1page;
				
				//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
				if ((count % number1page) > 0) {
					endPageAccount ++;
				}
			
			listAccount = accountJDBCTemplate.getAccountManagerSearchListViewManager(capaccount, tinhthanh, trangthai, indexcurrent, number1page);
			listAccountCodes = accountJDBCTemplate.getListAccountCodesManager();
			
		} else if (id_account_code == 4) {
			
			//1. Lấy tổng số Account đếm được để hiển thị tại trang indexAdmin theo 3 dữ liệu
			int count = accountJDBCTemplate.getAccountManagerListViewAdminTotalNumber(capaccount, tinhthanh, trangthai);
			
			//2. Xác định số lượng trang sẽ có với tổng số lượng đó
			endPageAccount = count / number1page;
				
				//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
				if ((count % number1page) > 0) {
					endPageAccount ++;
				}
				
				listAccount = accountJDBCTemplate.getAccountManagerSearchListViewAdmin(capaccount, tinhthanh, trangthai, indexcurrent, number1page );
				listAccountCodes = accountJDBCTemplate.getListAccountCodesAdmin();
		}
		
		//e. Lấy mẫu option cho các form và truyền các dữ liệu
		
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvincesClearn();
		List<AccountStatus> listAccountStatus = accountJDBCTemplate.getListAccountStatus();
		
		//Truyền dữ liệu
		session.setAttribute("listAccount", listAccount);
		session.setAttribute("listAccountCodes", listAccountCodes);
		
		session.setAttribute("listProvinces", listProvinces);
		session.setAttribute("listAccountStatus", listAccountStatus);
		
		model.addAttribute("endPageAccount", endPageAccount);
		model.addAttribute("tagcurrent", indexcurrent);
		
		model.addAttribute("capaccount", capaccount);
		model.addAttribute("tinhthanh", tinhthanh);
		model.addAttribute("trangthai", trangthai);
		
		
		return "admin/staffmanager";
	}
	
	/*
	 * 6.b Xử lý Tìm kiếm danh sách theo từ khoá được nhập vào (searchkey)
	 */
	@RequestMapping (value = "/searchKeyListAccount.html", method = RequestMethod.GET)

	public String searchKeyListAccount (ModelMap model, HttpServletRequest request) throws ParseException {
		
		//a. Lấy id_account_code của tk đang hoạt động để điều hướng. Lấy search key được gửi lên
			HttpSession session = request.getSession();
			int id_account_code = (Integer) session.getAttribute("id_account_code");
			
			String searchkey = request.getParameter("searchkey");
			
		//b. Lấy số thứ tự trang đang chọn (để xem) được gửi lên từ người dùng
		String indexpage = request.getParameter("indexpage");
		
			//Nếu là null thì set về trang đầu tiên (Trường hợp mới vào trang index hoặc được chuyển tiếp sang index hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Chuyển số trang hiện tại được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
		
		//c. Tạo trước một số mẫu thông tin sẽ được phân nhánh theo cấp
		List<AccountManager> listAccount = new ArrayList<AccountManager>();
		List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
		
		int endPageAccount = 0;
			
		//d. Sử dụng hàm tìm kiếm phân cấp id_account_code và truyền về trang
			if (id_account_code == 4) {
				//1. Lấy tổng số Account đếm được để hiển thị tại trang indexAdmin theo searchkey
				int count = accountJDBCTemplate.getAccountManagerListViewAdminTotalNumber(searchkey);
				
				//2. Xác định số lượng trang sẽ có với tổng số lượng đó
				endPageAccount = count / number1page;
					
					//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
					if ((count % number1page) > 0) {
						endPageAccount ++;
					}
				
				listAccount = accountJDBCTemplate.getAccountManagerSearchKeyListViewAdmins(searchkey, indexcurrent, number1page);
				listAccountCodes = accountJDBCTemplate.getListAccountCodesAdmin();
				
			} else if (id_account_code == 3) {
				
				//1. Lấy tổng số Account đếm được để hiển thị tại trang indexAdmin theo searchkey
				int count = accountJDBCTemplate.getAccountManagerListViewManagerTotalNumber(searchkey);
				
				//2. Xác định số lượng trang sẽ có với tổng số lượng đó
				endPageAccount = count / number1page;
					
					//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
					if ((count % number1page) > 0) {
						endPageAccount ++;
					}
					
				listAccount = accountJDBCTemplate.getAccountManagerSearchKeyListViewManager(searchkey, indexcurrent, number1page);
				listAccountCodes = accountJDBCTemplate.getListAccountCodesManager();
			}
				
		//e. Truyền dữ liệu và Chuyển về trang
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvincesClearn();
		List<AccountStatus> listAccountStatus = accountJDBCTemplate.getListAccountStatus();
		
		model.addAttribute("searchkey", searchkey);
		session.setAttribute("listAccount", listAccount);
		session.setAttribute("listAccountCodes", listAccountCodes);
		
		session.setAttribute("listProvinces", listProvinces);
		session.setAttribute("listAccountStatus", listAccountStatus);
		
		model.addAttribute("endPageAccount", endPageAccount);
		model.addAttribute("tagcurrent", indexcurrent);
		
		return "admin/staffmanager";
	}
	
	/*
	 * 7.Xử lý edit một Account tại trang Admin
	 */
	@RequestMapping (value = "/myaccountpage.html", method = RequestMethod.GET)

	public String myaccountpage (ModelMap model, HttpServletRequest request) throws ParseException {
		
				//a. Lấy id_account, id_account_code của tài khoản quản lý (đang login) đang thực hiện chỉnh sửa cho user, để chọn danh sách phù hợp với cấp account_level
				HttpSession session = request.getSession();
				int id_account = (Integer) session.getAttribute("id_account");
				int id_account_code = (Integer) session.getAttribute("id_account_code");
				
				View1Account oneAccount = accountJDBCTemplate.getView1Account(id_account);
				
				//b. Lấy danh sách các list thông tin để lựa chọn từ CSDL
				List<Gender> listGenders = accountJDBCTemplate.getListGenders(oneAccount.getGender());
				List<AccountCode> listAccountCodes = new ArrayList<AccountCode>();
				List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces(oneAccount.getProvince_code());
				List<AccountStatus> listAccountStatus = accountJDBCTemplate.getListAccountStatus(oneAccount.getStatus());
				
				//c. Xử lý riêng danh sách của account_code theo cấp tài khoản đang thao tác quản lý
				if (id_account_code == 4) {
					listAccountCodes = accountJDBCTemplate.getListAccountCodesAdmin(id_account_code);
				} else if (id_account_code == 3) {
					listAccountCodes = accountJDBCTemplate.getListAccountCodesManager(oneAccount.getId_account_code());
				}
				
				//d. Truyền vào model để xem ở trang myaccountpage
				model.addAttribute("oneAccount", oneAccount);
				model.addAttribute("listGenders", listGenders);
				model.addAttribute("listAccountCodes", listAccountCodes);
				model.addAttribute("listProvinces", listProvinces);
				model.addAttribute("listAccountStatus", listAccountStatus);
		
		return "admin/myaccountpage";
	}
	

	/*
	 * 8. Truy cập trang Edit user tại trang index
	 */
	@RequestMapping (value = {"/edituserpage.html"}, method = RequestMethod.GET)
	
	public String edituserpage (HttpServletRequest request, Model model) {
		//a. Lấy id_account của user đang đăng nhập để lấy đủ thông tin theo View1Account 
		HttpSession session = request.getSession();
		int id_account = (Integer) session.getAttribute("id_account");
		
		View1Account oneUser = accountJDBCTemplate.getView1Account(id_account);
		
		//b. Lấy danh sách các list thông tin để lựa chọn từ CSDL (Trừ mã của user đang đăng nhập)
		List<Gender> listGenders = accountJDBCTemplate.getListGenders(oneUser.getGender());
		List<AccountCode> listAccountCodes = accountJDBCTemplate.getListAccountCodesManager(oneUser.getId_account_code());
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces(oneUser.getProvince_code());
		List<AccountStatus> listAccountStatus = accountJDBCTemplate.getListAccountStatus(oneUser.getStatus());
		
		//d. Truyền vào model để xem ở trang edituser
		model.addAttribute("oneAccount", oneUser);
		model.addAttribute("listGenders", listGenders);
		model.addAttribute("listAccountCodes", listAccountCodes);
		model.addAttribute("listProvinces", listProvinces);
		model.addAttribute("listAccountStatus", listAccountStatus);
		
		return "edituser";
	}
	
	/*
	 * 9. Edit user tại trang người dùng
	 */
	@RequestMapping (value = {"/edituser.html"}, method = RequestMethod.POST)
	
	public String edituser (HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		
		//Thêm định dạng cho chữ, nếu không khi đọc xong sẽ bị lỗi (Khi dùng hàm POST)
		request.setCharacterEncoding("UTF-8");
		
		//a. Lấy các thông tin cần update được gửi lên từ form
		int id_account = Integer.parseInt(request.getParameter("id_account"));
		int gender = Integer.parseInt(request.getParameter("gender"));
		
		String password = request.getParameter("password"); 
		String account_name = request.getParameter("account_name");
		String phone = request.getParameter("phone");
		String province_code = request.getParameter("province_code");
		
		//Thực hiện update dữ liệu của account
		accountJDBCTemplate.edit1User(password, account_name, phone, province_code, gender, id_account);
		
		//Lấy thông tin của Account vừa sửa
		View1Account oneUser = accountJDBCTemplate.getView1Account(id_account);
		
		//b. Lấy danh sách các list thông tin để lựa chọn từ CSDL (Trừ mã của user đang đăng nhập)
		List<Gender> listGenders = accountJDBCTemplate.getListGenders(oneUser.getGender());
		List<AccountCode> listAccountCodes = accountJDBCTemplate.getListAccountCodesManager(oneUser.getId_account_code());
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces(oneUser.getProvince_code());
		List<AccountStatus> listAccountStatus = accountJDBCTemplate.getListAccountStatus(oneUser.getStatus());
		
		//d. Truyền vào model để xem ở trang view1account
		model.addAttribute("oneAccount", oneUser);
		model.addAttribute("listGenders", listGenders);
		model.addAttribute("listAccountCodes", listAccountCodes);
		model.addAttribute("listProvinces", listProvinces);
		model.addAttribute("listAccountStatus", listAccountStatus);
		
		model.addAttribute("message", "Cập nhật tài khoản thành công!");
		
		return "edituser";
	}
	
	/*
	 * 10. Lịch sử quyên góp của một user
	 */
	@RequestMapping (value = {"/myhistory.html"}, method = RequestMethod.GET)
	
	public String myhistory (HttpServletRequest request, Model model) {
		//a. Lấy id_account của user đang đăng nhập để lấy đủ thông tin (theo View1Account)
		HttpSession session = request.getSession();
		int id_account = (Integer) session.getAttribute("id_account");
		View1Account oneUser = accountJDBCTemplate.getView1Account(id_account);
		
		//b. Lấy tổng số đếm được để hiển thị tại trang
		int count = campaignJDBCTemplate.getHistoryDonateUserListTotalNumber(id_account);
		
		//Xác định số lượng trang sẽ có với tổng số lượng đó. Đặt lại số lượng của 1 trang
		number1page = 10;
		int endPage = count / number1page;
			
			//Nếu tổng số / số lượng một trang mà dư thì tăng thêm một trang
			if ((count % number1page) > 0) {
				endPage ++;
			}
			
		//c. Lấy số thứ tự trang đang chọn (để xem) được gửi lên từ người dùng
		String indexpage = request.getParameter("indexpage");
		
			//Nếu là null thì set về trang đầu tiên (Trường hợp mới vào trang hoặc được chuyển tiếp sang hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Chuyển số trang hiện tại được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
		
		//d. Lấy danh sách list thông tin từ CSDL
		List<HistoryDonateUser> historyDonateUserList = campaignJDBCTemplate.getHistoryDonateUserList(id_account, indexcurrent, number1page);
		
		//e. Truyền vào model để xem ở trang
		model.addAttribute("oneAccount", oneUser);
		model.addAttribute("historyDonateUserList", historyDonateUserList);
		
		model.addAttribute("tagcurrent", indexcurrent);
		model.addAttribute("endPage", endPage);
		
		return "myhistory";
	}
	
	/*
	 * 11. Cập nhật số tiền quyên góp và thông tin của một Campaign vừa được quyên góp (Vừa quản lý Account vừa quản lý Campaign)
	 * Nếu chưa liên kết API với ngân hàng, ví điện tử, có thể xác nhận qua thông tin ATM: Họ tên, Số tài khoản ngân hàng, Số tiền ủng hộ
	 */
	@RequestMapping (value = {"/donatefromuser.html"}, method = RequestMethod.POST)
	
	public String donatefromuser (HttpServletRequest request, Model model) throws UnsupportedEncodingException {

		//Thêm định dạng cho chữ, nếu không khi đọc xong sẽ bị lỗi (Khi dùng hàm POST)
		request.setCharacterEncoding("UTF-8");

		//a. Lấy các thông tin được gửi lên từ form
		int id_campaign = Integer.parseInt(request.getParameter("id_campaign"));
		Long donate_money = Long.parseLong(request.getParameter("donate_money"));
		Long check_donate_money = donate_money % 1000;
		
		//Kiểm tra dữ liệu số tiền ủng hộ
		if (check_donate_money > 0) {
			model.addAttribute("message", "Số tiền ủng hộ phải chia hết cho 1000 (VNĐ)");
			return "message";
		}
			
		//Lấy id_account user đang đăng nhập, lấy thông tin tài khoản
		HttpSession session = request.getSession();
		int id_account = (Integer) session.getAttribute("id_account");
		View1Account accountUser = accountJDBCTemplate.getView1Account(id_account);
		
		//Lấy ngày hiện tại (theo format) làm ngày thực hiện ủng hộ. Cần đặt tạo ngày tháng tại đây mới có thể lấy chính xác thời gian mỗi lần thực hiện hàm này
		LocalDateTime myDateTime = LocalDateTime.now();
		DateTimeFormatter myFormat2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String timeNow = myDateTime.format(myFormat2);
		
		//b. Thực hiện update dữ liệu vào bảng Donate
		campaignJDBCTemplate.DonateFromUser(id_account, id_campaign, donate_money, timeNow);
		
		//c. Điều hướng thông báo và Kiểm tra điều kiện nâng cấp (trạng thái) của Campaign vừa được quyên góp
		DonateOneCampaign donateOneCampaign = campaignJDBCTemplate.getDonateOneCampaign(id_campaign);
		
		//Tạo thông báo thứ nhất	
		String message1 = "";
			
			if (donateOneCampaign.getSum_donate_campaign() < donateOneCampaign.getTarget_money()) {
				message1 = "Chúc mừng! Bạn đã quyên góp thành công cho chiến dịch: <b>" + donate_money + "</b> VNĐ<br>"
						+ "Xin cảm ơn sự đóng góp hảo tâm của bạn!";
				
			} else if (donateOneCampaign.getSum_donate_campaign() >= donateOneCampaign.getTarget_money()) {
				int status = 2;
				message1 = "Xin chúc mừng! Bạn đã giúp chiến dịch <b>Đạt mục tiêu</b> với số tiền vừa quyên góp: <b>" + donate_money + "</b> VNĐ"
						+ "<br>Xin cảm ơn sự đóng góp hảo tâm của bạn!";
				campaignJDBCTemplate.updateCampaignStatus(status, id_campaign);
			}
		
		/*
		 * d. Kiểm tra điều kiện nâng cấp tài khoản (id_account_code) của tài khoản vừa thực hiện quyên góp. Thông báo nếu đạt điều kiện
		 * Tạo luôn hàm để lấy tên tài khoản sau khi nâng cấp (Set mới xong get luôn)
		 */
			
		AccountConditionUp account = accountJDBCTemplate.getCheckAccountConditionUp(id_account);
		String message2 = "";
		
		//Nếu thoả mãn điều kiện up level và tài khoản đang là cấp user (Có id_account_code = 1) thì up lên ngay cấp VIP1 (id_account_code = 2)
		if (account.getSum_donate_money() >= account.getCondition_up() && account.getId_account_code() == 1) {
			
				accountJDBCTemplate.setAccountCode(2, id_account);
				account = accountJDBCTemplate.getCheckAccountConditionUp(id_account); // Update lại thông tin account sau khi đã up
				message2 = "<br> Bạn đã được thăng cấp tài khoản lên: <b>" + account.getAccount_code_name() + "</b><br>";
				
				//Nếu vấn tiếp tục thoả mãn điều kiện lên cấp thì up lên VIP2 (id_account_code = 5)
				if (account.getSum_donate_money() >= account.getCondition_up()) {
					accountJDBCTemplate.setAccountCode(5, id_account);
					account = accountJDBCTemplate.getCheckAccountConditionUp(id_account); // Update lại thông tin account sau khi đã up
					message2 = "<br> Bạn đã được thăng cấp tài khoản lên: <b>" + account.getAccount_code_name() + "</b><br>";
				}
				
		//Nếu thoả mãn điều kiện up level và hiện tại đang là cấp VIP1, thì up lên cấp VIP2	(Không xử lý trường hợp đang là VIP2 vì lên VIP2 đang để là max level dành cho user)
		} else	if (account.getSum_donate_money() >= account.getCondition_up() && account.getId_account_code() == 2) {
			
			accountJDBCTemplate.setAccountCode(5, id_account);
			account = accountJDBCTemplate.getCheckAccountConditionUp(id_account); // Update lại thông tin account sau khi đã up
			message2 = "<br> Bạn đã được thăng cấp tài khoản lên: <b>" + account.getAccount_code_name() + "</b><br>";
		}
		
		/*
		 * e.Tạo email thông báo số tiền đã quyên góp và các thông tin cần thiết đến email vừa quyên góp
		 */
		
        //e.1 SMTP server information. Email gốc sẽ gửi thông báo
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "minhviet.dragon@gmail.com";
        String password = "zagshhlktmpikgoy";
 
        //e.2 outgoing message information. Email tài khoản vừa quyên góp
        String mailTo = accountUser.getEmail();
        String subject = "Thông báo tham gia quyên góp từ thiện thành công!";
 
        //e.3 message contains HTML markups. Lấy thông tin phục vụ thông báo và tạo thông báo
        View1CampaignIndex campaignIndex = campaignJDBCTemplate.getView1CampaignIndex(id_campaign);
        
        String message3 = "Xin kính chào người tham gia quyên góp " + accountUser.getAccount_name() +"! <br>";
        message3 += message1 + message2;
        message3 += "<br>";
        message3 += "<br>";
        message3 += "Chiến dịch bạn đã tham gia: <b>" + campaignIndex.getTitle() + "</b><br>";
        message3 += "Tổng quyên góp/mục tiêu: " + campaignIndex.getSum_donate_money() + "/" + campaignIndex.getTarget_money() + "<br>";
        message3 += "Tình trạng chiến dịch: " + campaignIndex.getStatus_name() + "<br>";
        message3 += "<hr>";
        message3 += "Để theo dõi các chiến dịch quyên góp vui lòng truy cập trang web: ";
        message3 += "<i>doantotnghiep.funix.edu.vn </i>";
        
        //e.4 Thực hiện gửi email
        SendEmail mailer = new SendEmail();
 
        try {
            mailer.sendHtmlEmail(host, port, mailFrom, password, mailTo, subject, message3);
            
        } catch (Exception ex) {
            System.out.println("Failed to sent email from StaffManagerController");
            ex.printStackTrace();
        }
		
		//f. Chuyển thông báo đến trang thông báo. Tạo form xem lại đối với thông báo quyên góp
		model.addAttribute("message", message1 + message2);
		
		String xemlaidonate = "<form action='myhistory.html'><input type='submit' value='Xem lại lịch sử'></form>";
		model.addAttribute("viewmyhistory", xemlaidonate);
		
		return "message";
	}

	/*
	 * 12. Xác nhận mã kích hoạt tài khoản
	 */
	@RequestMapping (value = {"/confirmaccount.html"}, method = RequestMethod.POST)
	
	public String confirmaccount (HttpServletRequest request, Model model) {
		
		//a. Lấy mã random_code mà user đã nhập xác thực
		String random_code_from_user = request.getParameter("random_code_from_user");
		
		//b. Lấy emailconfirm của user đang xác thực để lấy đủ thông tin
		HttpSession session = request.getSession();
		String emailconfirm = (String) session.getAttribute("emailconfirm");
		
		//Lấy thông tin Account thông qua email để lấy random_code đã gửi đến email và lưu vào CSDL khi đăng ký
		Account account = accountJDBCTemplate.getAccount(emailconfirm);
		String random_code = account.getRandom_code();
		
		//c. Nếu mã xác thực trùng nhau thì kích hoạt tài khoản.Xoá emailconfirm và chuyển về trang thông báo
		if (random_code.equals(random_code_from_user)) {
			accountJDBCTemplate.confirmAccount(emailconfirm);
			
			session.setAttribute("emailconfirm", "removed");
			model.addAttribute("message","Kích hoạt tài khoản thành công! Vui lòng đăng nhập để sử dụng!");
			
			return "message";
			
		//d. Nếu mã xác thực không trùng nhau thì trả lại trang và thông báo
		} else {
			model.addAttribute("random_code_from_user",random_code_from_user);
			model.addAttribute("message","Mã kích hoạt không chính xác, vui lòng kiểm tra lại!");
			
			return "confirmaccount";
		}
		
	}
	
	/*
	 * 13. Truy cập trang quên mật khẩu
	 */
	@RequestMapping (value = {"/forgetpassword.html"}, method = RequestMethod.GET)
	
	public String forgetpassword (HttpServletRequest request, Model model) {
		return "forgetpassword";
	}
	

	/*
	 * 14. Tiến hành làm lại mật khẩu
	 */
	@RequestMapping (value = {"/doforgetpassword.html"}, method = RequestMethod.POST)
	
	public String doforgetpassword (HttpServletRequest request, Model model) {
		//a. Lấy thông tin gửi lên từ form
		String emailforget = request.getParameter("emailforget");
		String phoneforget = request.getParameter("phoneforget");
		
		//b. Nếu tồn tại Account
		if (accountJDBCTemplate.checkAccount(emailforget)) {
			Account account = accountJDBCTemplate.getAccount(emailforget);
			
			//1. Tiếp tục kiểm tra phone, nếu khớp thì thực hiện reset mật khẩu bằng mã random và gửi mã đó về email
			if (account.getPhone().equals(phoneforget)) {
				//1.1 Tạo mã RandomCode mỗi lần yêu cầu
				String randomCode = new RandomCode().getRandomCodeNow();
				
				//1.2 Tiến hành thay đổi mật khẩu của account (với email đang yêu cầu) bằng mã RandomCode đó
				accountJDBCTemplate.changePassword(randomCode, emailforget, phoneforget);
				
				//1.3 Gửi email thông báo đến email của người dùng
				// SMTP server information. Email gốc sẽ gửi thông báo
		        String host = "smtp.gmail.com";
		        String port = "587";
		        String mailFrom = "minhviet.dragon@gmail.com";
		        String password = "zagshhlktmpikgoy";
		 
		        // outgoing message information. Email tài khoản vừa quyên góp và các thông tin gửi đi
		        String mailTo = account.getEmail();
		        String subject = "Thông báo mật khẩu mới của bạn!";
		        
		        String message = "Xin kính chào " + account.getName() +"! <br>";
		        message += "Mật khẩu mới của bạn là: <b>" + randomCode + "</b><br>";
		        message += "Vui lòng truy cập trang web: <i>doantotnghiep.funix.edu.vn </i><br>";
		        message += "Để thay đổi thông tin và theo dõi các chiến dịch quyên góp!";
		        
		        //Thực hiện gửi email
		        SendEmail mailer = new SendEmail();
		 
		        try {
		            mailer.sendHtmlEmail(host, port, mailFrom, password, mailTo, subject, message);
		            
		        } catch (Exception ex) {
		            System.out.println("Failed to sent email from StaffManagerController.");
		            ex.printStackTrace();
		        }
				
				//1.4 Trả về trang thông báo
				model.addAttribute("message","Tìm lại mật khẩu thành công!<br> Mật khẩu mới đã được gửi về email của bạn, vui lòng kiểm tra email!");
				return "message";
				
			//2. Nếu phone không khớp thì trả lại trang và thông báo
			} else {
				model.addAttribute("emailforget",emailforget);
				model.addAttribute("phoneforget",phoneforget);
				
				model.addAttribute("message","Số điện thoại chưa chính xác, vui lòng kiểm tra lại!");
				return "forgetpassword";
			}
		
		//c. Nếu không tồn tại Account thì trả lại trang và thông báo
		} else {
			model.addAttribute("emailforget",emailforget);
			model.addAttribute("phoneforget",phoneforget);
			
			model.addAttribute("message","Email chưa đăng ký, vui lòng kiểm tra lại!");
			return "forgetpassword";
		}
	}
}
