package controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Account;
import model.CampaignCode;
import model.CampaignListViewAdmin;
import model.CampaignListViewIndex;
import model.Gender;
import model.Provinces;
import model.RandomCode;
import model.SendEmail;
import model.User;

@Controller
public class LoginController {
	
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("controller/Beans.xml"); //(địa chỉ của Beans.xml)
	private CampaignJDBCTemplate campaignJDBCTemplate = (CampaignJDBCTemplate) context.getBean("CampaignJDBCTemplate"); //(tên bean đã tạo ở Beans.xml)
	private AccountJDBCTemplate accountJDBCTemplate = (AccountJDBCTemplate) context.getBean("AccountJDBCTemplate"); //(tên bean đã tạo ở Beans.xml)
	private Calendar calendar = Calendar.getInstance();
	private Date ngayHomNay = calendar.getTime(); 
	private int number1page = 8; //Số lượng trong 1 trang, để mặc định là 8, có thể set lại tuỳ từng trang hay theo từng truy cập
	
	/*
	 * 1. Điều hướng về trang index.jsp để xử lý lấy các danh sách theo lựa chọn (Mọi trang đều được set về đuôi hiển thị trên URL là .html). Có thể set nhiều value trong ngoặc nhọn nếu cần
	 */
		@RequestMapping (value = {"/index.html"}, method = RequestMethod.GET)
		
		public String index (HttpServletRequest request, Model model) {
			
			//a. Lấy trạng thái Campaign được chọn gửi lên
			String status = request.getParameter("status");
				//Xứ lý nếu chưa có status được truyền vào
				if (status == null) {
					status = "1";
				}
				
			//Đặt lại chính xác status đang được chọn	
			int statusCurrent = Integer.parseInt(status);
			//Nếu status là khác phạm vi người dùng thì trả về status cơ bản là Đang quyên góp
			if (statusCurrent != 1 && statusCurrent != 2 && statusCurrent != 3) {
				statusCurrent = 1;
			}
			
			//b. Lấy tổng số campaign đếm được của danh sách ở trạng thái campaign theo statusCurrent
			int count = campaignJDBCTemplate.getCampaignListViewIndexTotalNumber(statusCurrent);
			
			//Xác định số lượng trang sẽ có với tổng số lượng đó
			number1page = 8;
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
			
			//c. Lấy danh sách List Campaign theo dạng CampaignListViewIndex, theo statusCurrent, trang hiện tại và số lượng 1 trang để lấy danh sách tại trang đó
			List<CampaignListViewIndex> listCampaignIndex = campaignJDBCTemplate.getCampaignListViewIndex(statusCurrent, indexcurrent, number1page);
			
			//d. Thực hiện truyền dữ liệu vào session và model
			HttpSession session = request.getSession();
			session.setAttribute("listCampaignIndex", listCampaignIndex);
			model.addAttribute("endPageCampaign", endPageCampaign);
			model.addAttribute("tagcurrent", indexcurrent);
			model.addAttribute("status", statusCurrent);
			
			//e. Lấy danh sách của CampaignCode và Provinces trong CSDL. (Để truyền vào phần tìm kiếm chọn lọc)
			List<CampaignCode> listCampaignCode = campaignJDBCTemplate.getListCampaignCode();
			List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
			
			//f. Truyền danh sách vào ModelMap hoặc session, request để truyền sang trang index
			session.setAttribute("listCampaignCodeModel", listCampaignCode);
			session.setAttribute("listProvincesModel", listProvinces);
			
		    return "index";
		}
	
	/*
	 * 2. Xử lý của @RequestMapping có value = "/login.html", (Đặt .html vì tại web.xml DispatcherServlet đã đặt thành nhận đuôi .html thân thiện người dùng)	
	 * Dùng redirect chuyển trang theo value nếu cần, đích đến là return "login"
	 * Có thể thực hiện nhiều hàm chứ không chỉ 1 hàm trong @RequestMapping (như login() là một hàm)
	 */
		
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	
	//Chuyển vào login.jsp | Theo cài đặt trong login2-servlet.xml thì các đuôi sẽ tự động là .jsp
	
	public String login() {
	      return "login";
	   }
	
	/*
	 * 3. Xử lý của phần @RequestMapping có value = "/dologin.html", dùng hàm POST
	 */
	@RequestMapping(value = "/dologin.html", method = RequestMethod.POST)
	   
	   /*
	    * Thông số từ form login được truyền vào User bởi @ModelAttribute. (Đặt tên @ModelAttribute là SpringWebASM4) (không cần lấy qua request)
	    * Có thể lấy cách khác theo hàm request nếu không sử dụng truyền đối tượng như @ModelAttribute. Ở đây dùng @ModelAttribute là lấy được luôn (tên nhập) user và password truyền vào (Đối tượng) User.
	    * Ở đây sử dụng User để tiện gọn vì chỉ có 2 thông số: email, password, sau đó mới dùng Account
	    */
	public String dologin (@ModelAttribute("SpringWebDATN") User user, ModelMap model, HttpServletRequest request) {
		    
		    //a. Đặt lại thông tin đã truyền vào User để nhìn cho gọn
		    String userLogin = user.getEmail();
		    String passLogin = user.getPassword();
		    
		    //b. Lưu email đăng nhập và ngày hôm nay vào session để sử dụng nhiều lần (gọi session tại các trang)
		    //Tạo session, dùng session cũ nếu có
			HttpSession session = request.getSession(true);
			session.setAttribute("email", userLogin);
			
			//Tạo ngày tháng theo format (ngày - tháng - năm) và truyền vào session
			SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-YYYY");
			String formatted = myFormat.format(calendar.getTime());
			session.setAttribute("ngayHomNay", formatted);
		    
		    //e. Xử lý thông tin đăng nhập từ form gửi lên
			if(userLogin != null && passLogin != null) {
				
				//1. Check email: Nếu userLogin là email chưa đăng ký thì thông báo và trả lại trang đăng nhập
				if (!accountJDBCTemplate.checkAccount(userLogin)) {
					model.addAttribute("message","Thông báo 1: email chưa đăng ký!");
				    return "login";
				    
			    //2. Nếu userLogin là email đã đăng ký thì tiếp tục kiểm tra mật khẩu gửi lên so với mật khẩu trong CSDL 
				} else {
					//a. Lấy dữ liệu Account của email trong CSDL
					Account account = accountJDBCTemplate.getAccount(userLogin);
					
					//b.Nếu khớp mật khẩu. Xử lý các trường hợp tài khoản không phải là đã kích hoạt
					if (passLogin.equals(account.getPassword()) && account.getStatus() != 2) {
						session.setAttribute("keyShowMenu", null);
						
						//Trường hợp tài khoản chưa kích hoạt
						if (account.getStatus() == 1) {
							//Lưu emailconfirm vào session
							session.setAttribute("emailconfirm", userLogin);
							
							return "confirmaccount";
						
						//Trường hợp tài khoản ở trạng thái XOÁ
						} else if (account.getStatus() == 3) {
							model.addAttribute("message","Tài khoản đang trong tình trạng: Xoá!");
							return "message";
							
						//Trường hợp tài khoản KHOÁ
						} else if (account.getStatus() == 4) {
							model.addAttribute("message","Tài khoản đang trong tình trạng: Khoá!");
							return "message";
						}
						
						
					//c. Nếu passLogin khớp với của account trong CSDL và tài khoản ở trạng thái đã kích hoạt thì cho phép đăng nhập
					} else if (passLogin.equals(account.getPassword()) && account.getStatus() == 2) {
						
						//Lưu id_account, id_account_code vào session
						session.setAttribute("id_account", account.getId_account());
						session.setAttribute("id_account_code", account.getId_account_code());
						//Lưu khoá ẩn hiện ở menu
						session.setAttribute("keyShowMenu", "Hide");
						//Đặt ngày đăng nhập cuối của tài khoản thành ngày hôm nay
						accountJDBCTemplate.setLastLoginDateAccount(ngayHomNay, account.getId_account());
						
						//Tạo form đăng xuất
						String logoutString = "<form action='logout.html' method='get'>" + "<input type='submit' value='Logout'>"  +  "</form>";
						session.setAttribute("userLogout", logoutString);
						
						/*
						 * 1. Phân loại tài khoản admin và trang sẽ chuyển đến (theo account.getId_account_code() Mã code theo bảng Account_code)
						 */
						
						if (account.getId_account_code() == 3 || account.getId_account_code() == 4) {
							
							//a. Lấy tổng số campaign đếm được để hiển thị tại trang indexAdmin
							int count = campaignJDBCTemplate.getCampaignListViewAdminTotalNumber();
							
							//Đặt lại số sản phẩm hiện thị trong 1 trang của trang admin
							number1page = 15;
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
						
							//c. Lấy danh sách CampaignListViewAdmin theo trang và truyền dữ liệu
							List<CampaignListViewAdmin> listCampaignAdmin = campaignJDBCTemplate.getCampaignListViewAdmins(indexcurrent, number1page);
							model.addAttribute("listCampaignAdmin", listCampaignAdmin);
							model.addAttribute("endPageCampaign", endPageCampaign);
							model.addAttribute("tagcurrent", indexcurrent);
							
							//d. Lấy danh sách của CampaignCode và Provinces trong CSDL. Truyền dữ liệu (Có thể lấy thêm danh sách Status nếu làm thêm bảng trong CSDL)
							List<CampaignCode> listCampaignCode = campaignJDBCTemplate.getListCampaignCode();
							List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
							
							model.addAttribute("listCampaignCodeModel", listCampaignCode);
							model.addAttribute("listProvincesModel", listProvinces);
							
							//e. Chuyển về trang đích là index (của admin)
							return "admin/indexAdmin";
						
						/*
						 * 2. Phân loại tài khoản user và trang sẽ chuyển đến (theo account.getId_account_code() Mã code theo bảng Account_code)
						 */
						} else if (account.getId_account_code() == 1 || account.getId_account_code() == 2 || account.getId_account_code() == 5) {
							
							return "redirect:index.html?status=1";
							
						/*
						 * 3. Trả về trang login và thông báo nếu tài khoản chưa được phân loại
						 */
						} else {
							model.addAttribute("message","Thông báo 2: Tài khoản chưa được phân loại!");
							return "login";
						}
					
					//c. Trả về trang login và thông báo nếu mật khẩu không đúng
					} else {
						model.addAttribute("message","Thông báo 3: Mật khẩu chưa chính xác!");
						return "login";
					}
				}
			}
			
			//Thông báo và trả lại trang login nếu tên đăng nhập hoặc mật khẩu == null. Hoặc != null nhưng không khớp với tài khoản
			model.addAttribute("message","Thông báo 4: Vui lòng nhập email và password!");
		    return "login";
	   }
	
	/*
	 * 4. Xử lý đăng xuất tài khoản
	 */
	
	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	/*
	 * Model hay ModelMap là cơ chế truyền dữ liệu 1 lần giữa view và controller chứ không lưu lại (Còn session là cơ chế lưu dữ liệu)
	 * Nó cũng có cấu trúc tương tự để đặt tên và truyền. Tên của Model có thể gọi lại, đặt lại như request hay session, nhưng giá trị thì chỉ truyền 1 lần trong mỗi lần truyền
	 */
	public String logout (ModelMap model, HttpServletRequest request) {
		//1. Kết thúc sesion
		HttpSession session = request.getSession(true);
		session.invalidate();
		
		/*
		 * Hoặc có thể đặt lại từng giá trị trong session
		 * session.setAttribute("userLogout", ""); 
		 * session.setAttribute("email", "");
		 * session.setAttribute("id_account", "");
		 * session.setAttribute("id_account_code", "");
		 */
		
		//2. Đặt lại giá trị các model
		model.addAttribute("oneAccount", "");
		model.addAttribute("message","Thông báo: Đăng xuất xong!"); 
		number1page = 8;
		
		//3. Trả lại trang login
		return "login";
	}
	
	/*
	 * 5. Điều hướng về trang đăng ký
	 */
	@RequestMapping(value = "/registerpage.html", method = RequestMethod.GET)
	
	public String registerpage (ModelMap model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		
		//a. Lấy danh sách các dữ liệu để truyền về trang
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
		List<Gender> listGenders = accountJDBCTemplate.getListGenders();
		
		//b. Truyền danh sách vào ModelMap hoặc session, request để truyền sang trang register
		session.setAttribute("listProvincesModel", listProvinces);
		session.setAttribute("listGendersModel", listGenders);
		
		//c. Tạo sẵn một option để mục lựa chọn không bị trống
		Provinces oneProvince = new Provinces();
		oneProvince.setCode("00");
		oneProvince.setName("-- Bấm để chọn --");
		
		Gender oneGender = new Gender();
		oneGender.setId_gender(00);
		oneGender.setGender_vn("-- Bấm để chọn --");
		
		//d. Truyền OnceProvince vào model để chuyển về trang register
		model.addAttribute("oneProvince", oneProvince);
		model.addAttribute("oneGender", oneGender);
		
		return "register";
	}
	
	/*
	 * 6. Thực hiện đăng ký tài khoản mới
	 */
	@RequestMapping(value = "/register.html", method = RequestMethod.POST)
	
	public String register(ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		//a. Lấy các tham số từ form gửi lên
		String emailregister = request.getParameter("emailregister");
		String password = request.getParameter("password");
		String verifypass = request.getParameter("verifypass");
		String name = request.getParameter("name");
		String phonenumber = request.getParameter("phonenumber");
		int id_gender = Integer.parseInt(request.getParameter("id_gender"));
		String provincecode = request.getParameter("provincecode");
		
		//b. Truyền lại về trang register trong trường hợp phải đăng ký lại
		model.addAttribute("emailregister", emailregister);
		model.addAttribute("password", password);
		model.addAttribute("verifypass", verifypass);
		model.addAttribute("name", name);
		model.addAttribute("phonenumber", phonenumber);
		
		//c. Lấy danh sách List các tỉnh thành và một Province, một Gender mà user đã chọn để sử dụng
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
		Provinces oneProvince = campaignJDBCTemplate.get1Provinces(provincecode);
		Gender oneGender = accountJDBCTemplate.get1Gender(id_gender);
		
		//d. Truyền dữ liệu tỉnh vào session và model (Sẽ chuyển tiếp về trang register trong trường hợp phải đăng ký lại)
		HttpSession session = request.getSession();
		session.setAttribute("listProvincesModel", listProvinces);
		model.addAttribute("oneProvince", oneProvince);
		model.addAttribute("oneGender", oneGender);
		
		//e. Nếu mật khẩu không trùng nhau thì trả lại trang (rồi sau đó đến sự tồn tại của tài khoản email đang đăng ký)
		if(!password.equals(verifypass)) {
			model.addAttribute("message", "Đăng ký không thành công! Mật khẩu không trùng nhau!");
			return "register";
		
		//f. Tiếp tục thực hiện các bước tiếp theo sau khi mật khẩu đăng ký đã trùng nhau
		} else {
			
			//Tạo user để sử dụng kiểm tra nhanh riêng cho email và password
			User user = new User(emailregister, password);
			
			//1 Kiểm tra cú pháp user hiện tại (email và password). Nếu không vượt qua kiểm tra của user thì trả lại trang register
			if(!user.check()) {
				model.addAttribute("message", user.getMessage());
				
				return "register";
				
			//2 Khi đã vượt qua kiểm tra của user, tiếp tục dùng kiểm tra của Account, khi vượt qua thì tạo tài khoản lưu vào CSDL
			} else {
				
				//2.1 Nếu tài khoản đã tồn tại thì trả lại về trang register
				if(accountJDBCTemplate.checkAccount(emailregister)) {
					model.addAttribute("message", "Tài khoản này đã tồn tại");
					return "register";
					
				//2.2 Nếu tài khoản chưa tồn tại thì tiến hành đăng ký, thêm vào cơ sở dữ liệu
				} else {
					
					//a.Tạo mã kích hoạt random_code (Mỗi run đến đây sẽ tự động tạo mã mới)
					RandomCode randomCode = new RandomCode();
					String random_code = randomCode.getRandomCodeNow();
				    
				    //b.Thực hiện tạo tài khoản cùng mã random_code
			        int id_account_code = 1; //mã của người dùng đăng ký thông thường cấp user là 1
					int status = 1; //Trạng thái tài khoản chưa kích hoạt là 1
					
					//Lấy thời gian chính xác đến giây (ss) để làm thời gian đăng ký tài khoản (Sẽ được lấy chính xác mỗi lần chạy đến hàm này)
					LocalDateTime myDateTime = LocalDateTime.now();
					DateTimeFormatter myFormat2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					String timeNow = myDateTime.format(myFormat2);
					
					accountJDBCTemplate.create(id_account_code, emailregister, password, name, phonenumber, provincecode, timeNow, status, null, id_gender, random_code);
					
					//c.Gửi mã random_code đến email vừa đăng ký
					
			        // SMTP server information. Email gốc sẽ gửi thông báo
			        String host = "smtp.gmail.com";
			        String port = "587";
			        String mailFrom = "minhviet.dragon@gmail.com";
			        String passwordFrom = "zagshhlktmpikgoy";
			 
			        // outgoing message information. Email tài khoản sẽ được gửi đến và tạo thông báo
			        String mailTo = emailregister;
			        String subject = "Mã kích hoạt tài khoản doantotnghiep.funix.edu.vn!";
			        
			        String message = "Xin kính chào " + name + "! <br>";
			        message += "Mã kích hoạt tài khoản của bạn là: <b>" + random_code + "</b><br>";
			        message += "Vui lòng đăng nhập tại trang web: <i>doantotnghiep.funix.edu.vn </i><br>";
			        message += "Để kích hoạt tài khoản và theo dõi các chiến dịch quyên góp từ thiện!";
			 
			        SendEmail mailer = new SendEmail();
			 
			        try {
			            mailer.sendHtmlEmail(host, port, mailFrom, passwordFrom, mailTo, subject, message);
			            
			        } catch (Exception ex) {
			            System.out.println("Failed to sent email from StaffManagerController.");
			            ex.printStackTrace();
			        }
					
					//d. Sau khi đăng ký thành công thì chuyển về trang thông báo
					model.addAttribute("message", "Đăng ký thành công tài khoản: <b>" + emailregister + "</b>.<br><p>Vui lòng truy cập email để nhận mã kích hoạt tài khoản!</p>");
					return "message";
				}
			}
		}
	}
	
}
