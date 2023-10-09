package controller;

import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

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
public class CampaignController {
	//Tạo các biến chung thường xuyên sử dụng
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("controller/Beans.xml"); //(địa chỉ của Beans.xml)
	private CampaignJDBCTemplate campaignJDBCTemplate = (CampaignJDBCTemplate) context.getBean("CampaignJDBCTemplate"); //(tên bean đã tạo ở Beans.xml)
	private AccountJDBCTemplate accountJDBCTemplate = (AccountJDBCTemplate) context.getBean("AccountJDBCTemplate");
	private Calendar calendar = Calendar.getInstance();
	private SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
	private int number1page = 15;
	
	/*
	 * 1. Xử lý trả về trang chủ của phần Admin (trang index)
	 */
	@RequestMapping (value = "/indexAdmin.html", method = RequestMethod.GET)
	
	public String indexAdmin (HttpServletRequest request, Model model) {
		//a.Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//b. Lấy tổng số campaign đếm được để phân trang tại indexAdmin
		int count = campaignJDBCTemplate.getCampaignListViewAdminTotalNumber();
		
		//Xác định số lượng trang sẽ có với tổng số lượng đó
		int endPageCampaign = count / number1page;
			
			//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
			if ((count % number1page) > 0) {
				endPageCampaign ++;
			}
			
		//c. Lấy số thứ tự trang đang chọn (để xem) được gửi lên từ người dùng
		String indexpage = request.getParameter("indexpage");
		
			//Nếu là null thì set về trang đầu tiên (Trường hợp mới vào trang index hoặc được chuyển tiếp sang index hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Chuyển trang đang được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
	
		//d. Lấy danh sách campaign để hiển thị tại trang indexAdmin theo từng trang. Truyền dữ liệu
		List<CampaignListViewAdmin> listCampaignAdmin = campaignJDBCTemplate.getCampaignListViewAdmins(indexcurrent, number1page);
		
		model.addAttribute("listCampaignAdmin", listCampaignAdmin);
		model.addAttribute("endPageCampaign", endPageCampaign);
		model.addAttribute("tagcurrent", indexcurrent);
		
		//e. Lấy danh sách của CampaignCode và Provinces trong CSDL. (Thực tế có thể lấy thêm danh sách Status nếu làm thêm bảng trong CSDL)
		List<CampaignCode> listCampaignCode = campaignJDBCTemplate.getListCampaignCode();
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
		
		//f. Truyền danh sách vào ModelMap (hoặc session, request) để truyền sang trang
		session.setAttribute("listCampaignCodeModel", listCampaignCode);
		session.setAttribute("listProvincesModel", listProvinces);
		return "admin/indexAdmin";
	}
	
	/*
	 * 2. Xử lý truy cập view một Campaign theo ID (Đã được gửi lên đây thông qua URL (Hoặc form))
	 */
	@RequestMapping (value = "/view1campaign.html", method = RequestMethod.GET)
	
	public String view1Campaign(ModelMap model, HttpServletRequest request) {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//a. Lấy id của dữ liệu được gửi lên
		Integer id_campaign = Integer.parseInt(request.getParameter("id_campaign"));
		String indexpage = request.getParameter("indexpage");
		
		//b. Lấy dữ liệu 1 Campaign thông qua id
		View1Campaign campaign = campaignJDBCTemplate.getView1Campaign(id_campaign);
		View1CampaignIndex oneCampaignIndex = campaignJDBCTemplate.getView1CampaignIndex(id_campaign);
		
		//c. Lấy danh sách người ủng hộ một Campaign để tạo phân trang
		//c.1 Xác định trang đang xem của danh sách ủng hộ. Nếu trang được chọn là null thì set về trang đầu tiên (Trường hợp mới vào trang hoặc chưa xác định)
		if (indexpage == null) {
			indexpage = "1";
		}
		//Đặt lại chính xác trang đang được chọn
		int indexcurrent = Integer.parseInt(indexpage);
		
		//c.2 Lấy tổng số người quyên góp đếm được của campaign
		int count = accountJDBCTemplate.getListAccountDonate1CampaignTotalNumber(id_campaign);
		
		//c.3 Xác định số lượng trong 1 trang và số trang sẽ có với tổng số lượng đó
		number1page = 10;
		int endPageDonate = count / number1page;
			
		//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang
		if ((count % number1page) > 0) {
			endPageDonate ++;
		}
		//c.4 Lấy danh sách người ủng hộ theo trang hiện tại
		List<AccountDonate1Campaign> accountDonateList = accountJDBCTemplate.getListAccountDonate1Campaign(id_campaign, indexcurrent, number1page);
		
		//d. Truyền về trang
		model.addAttribute("oneCampaign", campaign);
		model.addAttribute("oneCampaignIndex", oneCampaignIndex);
		model.addAttribute("accountDonateList", accountDonateList);
		
		model.addAttribute("endPageDonate", endPageDonate);
		model.addAttribute("tagcurrent", indexcurrent);
		
		return "admin/view1campaign";
	}
	
	/*
	 * 3.Xử lý truy cập vào trang tạo một Campaign mới
	 */
	@RequestMapping (value = "/newCampaign.html", method = RequestMethod.GET)

	public String newCampaign (ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//3.1 Lấy danh sách của CampaignCode và Provinces trong CSDL
		List<CampaignCode> listCampaignCode = campaignJDBCTemplate.getListCampaignCode();
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
		
		//3.2 Truyền danh sách vào ModelMap (hoặc session, request) để truyền sang trang New Campaign
		model.addAttribute("listCampaignCodeModel", listCampaignCode);
		model.addAttribute("listProvincesModel", listProvinces);
		
		return "admin/newcampaign";
	}
	
	/*
	 * 4.Xử lý thêm một Campaign mới
	 */
	@RequestMapping (value = "/addNewCampaign.html", method = RequestMethod.POST)
	
	public String addNewCampaign (ModelMap model, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//a. Lấy các giá trị gửi từ form lên theo đúng kiểu dữ liệu
		int id_campaign_code = Integer.parseInt(request.getParameter("idcampaigncode"));
		Long target_money = Long.parseLong(request.getParameter("targetmoney"));
		int id_account_write = id_account;
		int status = Integer.parseInt(request.getParameter("status"));
		
		String image_cover_link = request.getParameter("imagecoverlink");
		String title = request.getParameter("title");
		String province_code = request.getParameter("provincecode");
		String content_begin = request.getParameter("contentbegin");
		
		//b.1 Lấy ngày tháng Date theo cách chuyển đổi String sang Date
		Date campaign_begin_date = myFormat.parse(request.getParameter("campaignbegindate"));
		Date campaign_end_date = myFormat.parse(request.getParameter("campaignenddate"));
		
		//b.2 Lấy thời gian chính xác tại đây để làm thời gian bắt đầu đăng content_begin_date
		LocalDateTime myDateTime = LocalDateTime.now();
		DateTimeFormatter myFormat2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String timeNow = myDateTime.format(myFormat2);
		String content_begin_date = timeNow;
		
		//d.Gán các giá trị lấy được cho Campaign mới, nếu những giá trị cần điền (phía user) khác null
		if (id_campaign_code == 0 || target_money == 0 || image_cover_link == null || title == null || province_code == null || content_begin == null || campaign_begin_date == null || campaign_end_date == null) {
			model.addAttribute("message", "Chú ý, thêm Campaign mới KHÔNG thành công! Nguyên nhân do viết thiếu dữ liệu!");
			return "admin/message";
			
		} else if (id_campaign_code != 0 && target_money != 0 && image_cover_link != null && title != null && province_code != null && content_begin != null && campaign_begin_date != null && campaign_end_date != null) {
			
			//d.1 Điền các giá trị đã lấy vào hàm thêm Campaign mới (chỉ lấy đầu vào là các dữ liệu của Campaign mới, các thông số về sau sẽ thêm ở các bước khác)
			campaignJDBCTemplate.newCampaign (id_campaign_code, target_money, id_account_write, status, image_cover_link, title, province_code, content_begin, campaign_begin_date, campaign_end_date, content_begin_date);
			
			//d.2 Gán thông báo và chuyển đến trang thông báo
			model.addAttribute("message", "Thêm Campaign mới thành công! Để kiểm tra lại vui lòng xem danh sách ở trang chủ Admin!");
			return "admin/message";
			
		//e. Trường hợp còn lại, trả về thông báo thêm không thành công và chưa rõ nguyên nhân
		} else {
			model.addAttribute("message", "Thêm Campaign mới KHÔNG thành công! Chưa rõ nguyên nhân!");
			return "admin/message";
		}
	}
	
	/*
	 * 5. Truy cập trang cập nhật nội dung trao quà
	 */
	@RequestMapping (value = "/updatecontentendpage.html", method = RequestMethod.GET)
		
	public String updatecontentendpage (ModelMap model, HttpServletRequest request) {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//a. Lấy id của Campaign được gửi lên
		Integer id_campaign = Integer.parseInt(request.getParameter("id_campaign"));
		
		//b. Lấy dữ liệu 1 Campaign thông qua id
		View1Campaign campaign = campaignJDBCTemplate.getView1Campaign(id_campaign);
		View1CampaignIndex oneCampaignIndex = campaignJDBCTemplate.getView1CampaignIndex(id_campaign);
		
		//c. Truyền Campaign về trang updatecontentendpage
		model.addAttribute("oneCampaign", campaign);
		model.addAttribute("oneCampaignIndex", oneCampaignIndex);
		
		
		//d. Lấy danh sách người ủng hộ một Campaign để tạo phân trang
		//d.1 Xác định trang đang xem của danh sách ủng hộ. Nếu trang được chọn là null thì set về trang đầu tiên (Trường hợp mới vào trang hoặc chưa xác định)
		String indexpage = request.getParameter("indexpage");
			if (indexpage == null) {
				indexpage = "1";
			}
		//Đặt lại chính xác trang đang được chọn
		int indexcurrent = Integer.parseInt(indexpage);
		
		//d.2 Lấy tổng số người quyên góp đếm được của campaign
		int count = accountJDBCTemplate.getListAccountDonate1CampaignTotalNumber(id_campaign);
		
		//d.3 Xác định số lượng trong 1 trang và số trang sẽ có với tổng số lượng đó
		number1page = 10;
		int endPageDonate = count / number1page;
			
		//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang
		if ((count % number1page) > 0) {
			endPageDonate ++;
		}
		//d.4 Lấy danh sách người ủng hộ theo trang hiện tại
		List<AccountDonate1Campaign> accountDonateList = accountJDBCTemplate.getListAccountDonate1Campaign(id_campaign, indexcurrent, number1page);
		
		//d.5 Truyền dữ liệu phân trang
		model.addAttribute("accountDonateList", accountDonateList);
		model.addAttribute("endPageDonate", endPageDonate);
		model.addAttribute("tagcurrent", indexcurrent);
		
		return "admin/updatecontentendpage";
	}
	
	/*
	 * 6. Xử lý cập nhật nội dung trao quà
	 * Phải dùng hàm POST nếu không khi nội dung viết dài truyền lên URL có thể sẽ quá dài và không xử lý được
	 */
	@RequestMapping (value = "updatecontentend.html", method = RequestMethod.POST)
		
	public String updatenoidungtraoqua (ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//Định dạng font chữ khi dùng hàm post
		request.setCharacterEncoding("UTF-8");
		
		//a. Lấy các thông số cần sử dụng
		Integer id_account_content_end = id_account;				
		
		Integer id_campaign = Integer.parseInt(request.getParameter("id_campaign"));
		String content_end = request.getParameter("contentend");
		
		//Lấy ngày tháng hiện tại để làm content_end_date (ngày viết nội dung trao quà). Đặt tại đây mới có thể lấy chính xác để làm thời gian bắt đầu đăng (Thực hiện hàm này)
		LocalDateTime myDateTime = LocalDateTime.now();
		DateTimeFormatter myFormat2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String timeNow = myDateTime.format(myFormat2);
		
		//Đặt trạng thái chiến dịch chuyển sang "Đã trao quà"
		int status = 3;
		
		//b. Xử lý thêm (Update) nội dung trao quà của Campaign (với id đang xử lý)
		campaignJDBCTemplate.updateContentEnd(content_end, timeNow, id_account_content_end, status, id_campaign);
		
		//c. Chuyển về trang thông báo			
		model.addAttribute("message", "Đã cập nhật xong nội dung trao quà Campaign " + id_campaign);
		
		return "admin/message";
	}
	
	/*
	 * 7.Truy cập trang edit một Campaign
	 */
	@RequestMapping (value = "/edit1campaignpage.html", method = RequestMethod.GET)

	public String edit1Campaign (ModelMap model, HttpServletRequest request) {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//a. Lấy id của campaign được chọn
		Integer id_campaign = Integer.parseInt(request.getParameter("id_campaign"));
		
		//b. Lấy thông tin Campaign được chọn
		View1Campaign campaign = campaignJDBCTemplate.getView1Campaign(id_campaign);
		
		//d. Lấy danh sách của CampaignCode và Provinces trong CSDL
		List<CampaignCode> listCampaignCode = campaignJDBCTemplate.getListCampaignCode();
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
		
		//e. Truyền dữ liệu về trang edit1campaign
		model.addAttribute("oneCampaign", campaign);
		model.addAttribute("listCampaignCodeModel", listCampaignCode);
		model.addAttribute("listProvincesModel", listProvinces);
		
		return "admin/edit1campaign";
	}
	
	/*
	 * 8.Xử lý edit một Campaign
	 */
	@RequestMapping (value = "/edit1campaign.html", method = RequestMethod.POST)

	public String edit1campaign (ModelMap model, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		request.setCharacterEncoding("UTF-8");
		
		//a. Nhận các dữ liệu chỉnh sửa từ trang gửi lên
		int id_campaign_code = Integer.parseInt(request.getParameter("idcampaigncode"));
		String image_cover_link = request.getParameter("imagecoverlink");
		String title = request.getParameter("title");
		Long target_money = Long.parseLong(request.getParameter("targetmoney"));
		String province_code = request.getParameter("provincecode");
		String campaign_begin_date = request.getParameter("campaignbegindate"); //Date campaign_begin_date = myFormat.parse(request.getParameter("campaignbegindate"));
		String campaign_end_date = request.getParameter("campaignenddate"); //Date campaign_end_date = myFormat.parse(request.getParameter("campaignenddate"));
		String content_begin = request.getParameter("contentbegin");
		String content_end = request.getParameter("contentend");
		
		//Lấy id_account đang đăng nhập làm id_account_last_edit. (id_account này đã lưu trong session khi đăng nhập)
		Integer id_account_last_edit = (Integer) session.getAttribute("id_account");
		
		//Lấy ngày tháng hiện tại để làm account_last_edit_date (Chỉ cần định dạng String là phía CSDL sẽ tự get được ngày tháng theo định dạng của CSDL)
		//Cần đặt tạo ngày tháng tại đây mới có thể lấy chính xác thời gian mỗi lần thực hiện hàm này
		LocalDateTime myDateTime = LocalDateTime.now();
		DateTimeFormatter myFormat2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String timeNow = myDateTime.format(myFormat2);
		String account_last_edit_date = timeNow; //calendar.getTime();
		
		int status = Integer.parseInt(request.getParameter("status"));		
		Integer id_campaign = Integer.parseInt(request.getParameter("idcampaign"));
		
		//b. Sử dụng hàm cập nhật dữ liệu
		campaignJDBCTemplate.edit1Campaign(id_campaign_code, image_cover_link, title, target_money, province_code, campaign_begin_date, campaign_end_date, content_begin, content_end, id_account_last_edit, account_last_edit_date, status, id_campaign);
		
		//c. Tạo thông báo và chuyển về trang thông báo
		model.addAttribute("message", "Đã chỉnh sửa xong Campaign " + id_campaign);
		return "admin/message";
	}
	
	/*
	 * 9.Xử lý xoá một Campaign
	 */
	@RequestMapping (value = "/delete1campaign.html", method = RequestMethod.GET)

	public String delete1Campaign (ModelMap model, HttpServletRequest request) throws ParseException {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//a. Lấy id_campaign cần xoá đã gửi lên, lấy id account đang đăng nhập
		Integer id_campaign = Integer.parseInt(request.getParameter("id_campaign"));
		Integer id_account_last_edit = (Integer) session.getAttribute("id_account");
		
		//Lấy ngày tháng hiện tại để làm account_last_edit_date (ngày chỉnh sửa cuối cùng)
		Date account_last_edit_date = calendar.getTime();
		
		//b. Sử dụng xoá một Campaign (Chuyển sang trạng thái xoá nhưng vẫn lưu dữ liệu để quản lý, và những dữ liệu liên quan vẫn được lưu lại như user nào đã ủng hộ campaign đó)
		campaignJDBCTemplate.delete1Campaign(id_account_last_edit, account_last_edit_date, id_campaign);
		
		//c. Tạo thông báo và chuyển về trang thông báo
		model.addAttribute("message", "Đã xoá Campaign " + id_campaign);
		return "admin/message";
	}
	
	/*
	 * 10.a Lọc danh sách Campaign ở trang Admin theo một trong các thông số
	 */
	@RequestMapping (value = "/searchListCampaign.html", method = RequestMethod.GET)

	public String searchListCampaign (ModelMap model, HttpServletRequest request) {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//a. Lấy các thông số được gửi lên từ trang indexAdmin
		int id_campaign_code = Integer.parseInt(request.getParameter("id_campaign_code"));
		String province_code = request.getParameter("province_code");
		int status = Integer.parseInt(request.getParameter("status"));
		String indexpage = request.getParameter("indexpage");

		//b. Lấy tổng số campaign đếm được của danh sách theo 3 dữ liệu
		int count = campaignJDBCTemplate.getCampaignListViewAdminTotalNumber(id_campaign_code, province_code, status);
		
		//c. Xác định số lượng trang sẽ có với tổng số lượng đó
		int endPageCampaign = count / number1page;
			
			//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
			if ((count % number1page) > 0) {
				endPageCampaign ++;
			}
			
			//Nếu indexpage là null thì set về trang đầu tiên (Trường hợp mới vào trang index hoặc được chuyển tiếp sang index hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Chuyển trang đang được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
		
		//d. Lấy danh sách của Campaign tìm kiếm được theo 3 dữ liệu truyền lên và 2 dữ liệu phân trang
		
		List<CampaignListViewAdmin> listCampaignAdmin =  campaignJDBCTemplate.searchListCampaign(id_campaign_code, province_code, status, indexcurrent, number1page);
		
		model.addAttribute("listCampaignAdmin", listCampaignAdmin);
		
		model.addAttribute("id_campaign_code", id_campaign_code);
		model.addAttribute("province_code", province_code);
		model.addAttribute("status", status);
		
		model.addAttribute("endPageCampaign", endPageCampaign);
		model.addAttribute("tagcurrent", indexcurrent);
		
		return "admin/indexAdmin";
	}
	
	/*
	 * 10.b Lọc danh sách Campaign ở trang Admin theo một trong các thông số
	 */
	@RequestMapping (value = "/searchListCampaignByKey.html", method = RequestMethod.GET)

	public String searchListCampaignByKey (ModelMap model, HttpServletRequest request) {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//a. Lấy các tiêu chí tìm kiếm được gửi lên từ trang indexAdmin
		String searchkey = request.getParameter("searchkey");
		String indexpage = request.getParameter("indexpage");
		
		//b. Lấy tổng số campaign đếm được của danh sách theo 3 dữ liệu
		int count = campaignJDBCTemplate.getCampaignListViewAdminTotalNumberSearchKey(searchkey);
		
		//c. Xác định số lượng trang sẽ có với tổng số lượng đó
		int endPageCampaignSearchKey = count / number1page;
			
			//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
			if ((count % number1page) > 0) {
				endPageCampaignSearchKey ++;
			}
			
			//Nếu indexpage là null thì set về trang đầu tiên (Trường hợp mới vào trang index hoặc được chuyển tiếp sang index hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Chuyển trang đang được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
		
		//b. Lấy danh sách của Campaign tìm kiếm được theo searchkey và truyền vào model. Truyền trả lại cả searchkey để sử dụng
		List<CampaignListViewAdmin> listCampaignAdmin =  campaignJDBCTemplate.searchListCampaignByKey(searchkey, indexcurrent, number1page);
		
		model.addAttribute("listCampaignAdmin", listCampaignAdmin);
		model.addAttribute("searchkey", searchkey);
		
		model.addAttribute("endPageCampaignSearchKey", endPageCampaignSearchKey);
		model.addAttribute("indexcurrent", indexcurrent);
		
		//c. Trả về trang
		return "admin/indexAdmin";
	}
	

	/*
	 * 11. Truy cập trang my campaign của Admin
	 */
	@RequestMapping (value = "/mycampaign.html", method = RequestMethod.GET)
	
	public String myCampaign (HttpServletRequest request, Model model) {
		/*
		 * Lấy id_account đang đăng nhập (đã lưu vào session khi xử lý đăng nhập) để xử lý các trường hợp cho phép truy cập các trang Admin hoặc trả lại về trang người dùng, hoặc thông báo nếu muốn
		 */
		int id_account = 0;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id_account") == null) {
			id_account = -1;
		} else {
			id_account = (Integer) session.getAttribute("id_account");
		}
		
		Account account = accountJDBCTemplate.getAccount(id_account);
		
		//Nếu tài khoản đang đăng nhập không có thẩm quyền. Hoặc id_account đang đăng nhập là null thì trả về trang index người dùng user
		if(account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5 || id_account == -1) {
			return "index";
		}
		
		//a. Lấy tổng số campaign đếm được để hiển thị tại trang indexAdmin
		int count = campaignJDBCTemplate.getMyCampaignListViewAdminTotalNumber(id_account);
		
		//Xác định số lượng trang sẽ có với tổng số lượng đó
		int endPageCampaign = count / number1page;
			
			//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
			if ((count % number1page) > 0) {
				endPageCampaign ++;
			}
			
		//b. Lấy số thứ tự trang đang chọn (để xem) được gửi lên từ người dùng
		String indexpage = request.getParameter("indexpage");
		
			//Nếu là null thì set về trang đầu tiên (Trường hợp mới vào trang index hoặc được chuyển tiếp sang index hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Chuyển trang đang được chọn về con số chính xác
		int indexcurrent = Integer.parseInt(indexpage);
	
		//c. Lấy danh sách campaign để hiển thị theo từng trang. Truyền dữ liệu
		List<CampaignListViewAdmin> myListCampaignAdmin = campaignJDBCTemplate.getMyCampaignListViewAdmins(id_account, indexcurrent, number1page);
		
		model.addAttribute("myListCampaignAdmin", myListCampaignAdmin);
		model.addAttribute("endPageCampaign", endPageCampaign);
		model.addAttribute("tagcurrent", indexcurrent);
		
		//d. Lấy danh sách của CampaignCode và Provinces trong CSDL. (Thực tế có thể lấy thêm danh sách Status nếu làm thêm bảng trong CSDL)
		List<CampaignCode> listCampaignCode = campaignJDBCTemplate.getListCampaignCode();
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
		
		//e. Truyền danh sách vào ModelMap (hoặc session, request) để truyền sang trang
		session.setAttribute("listCampaignCodeModel", listCampaignCode);
		session.setAttribute("listProvincesModel", listProvinces);
		return "admin/mycampaign";
	}
	
		
	/*
	 * Phụ lục 1: Điều hướng truy vập vào mục menu trái của Admin (dùng để test)
	 */
	@RequestMapping (value = "/viewAdminLeft.html", method = RequestMethod.GET)

	public String viewAdminLeft (ModelMap model, HttpServletRequest request) {
		
		return "admin/adminleft";
	}
	
	/*
	 * Phụ lục 2: Test nhận giá trị number từ một input
	 */
	@RequestMapping (value = "/testViewDate.html", method = RequestMethod.GET)

	public String testViewDate (HttpServletRequest request, Model model) throws ParseException {
		LocalDateTime myTime = LocalDateTime.now();
		DateTimeFormatter myFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String viewDate1 = myTime.format(myFormatter2);
		
		model.addAttribute("viewDate", viewDate1);
		return  "testviewdate";
	}
	
}
