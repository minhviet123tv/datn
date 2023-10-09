package controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.AccountDonate1Campaign;
import model.CampaignCode;
import model.CampaignListViewIndex;
import model.Provinces;
import model.View1CampaignIndex;

@Controller
public class CampaignControllerIndex {
	
	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("controller/Beans.xml"); //(địa chỉ của Beans.xml)
	private CampaignJDBCTemplate campaignJDBCTemplate = (CampaignJDBCTemplate) context.getBean("CampaignJDBCTemplate"); //(tên bean đã tạo ở Beans.xml)
	private AccountJDBCTemplate accountJDBCTemplate = (AccountJDBCTemplate) context.getBean("AccountJDBCTemplate");
	private int number1page = 8;
	
	/*
	 * 1.Lọc danh sách Campaign ở trang index theo tiêu chí
	 */
	@RequestMapping (value = {"/searchListCampaignIndex.html"}, method = RequestMethod.GET)

	public String searchListCampaignIndex (ModelMap model, HttpServletRequest request) {
		
		//a. Lấy các tham số tìm kiếm được gửi lên từ trang index
		int id_campaign_code = Integer.parseInt(request.getParameter("id_campaign_code"));
		String province_code = request.getParameter("province_code");
		String status = request.getParameter("status");
		String indexpage = request.getParameter("indexpage");
			
			//Nếu trạng thái là null thì trả về trạng thái 1 (Quyên góp)
			if (status == null) {
				status = "1";
			}
			
			//Nếu trang được chọn là null thì set về trang đầu tiên (Trường hợp mới vào trang hoặc chưa xác định)
			if (indexpage == null) {
				indexpage = "1";
			}
			
		//Đặt lại chính xác status hiện tại
		int statusCurrent = Integer.parseInt(status);
		//Đặt lại chính xác trang đang được chọn
		int indexcurrent = Integer.parseInt(indexpage);
		
		//b. Lấy tổng số campaign đếm được của danh sách ở trạng thái campaign theo statusCurrent, id_campaign_code và province_code
		int count = campaignJDBCTemplate.getCampaignListViewIndexTotalNumber(statusCurrent, id_campaign_code, province_code);
		
		//c. Xác định số lượng trang sẽ có với tổng số lượng đó
		int endPageCampaign = count / number1page;
			
			//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
			if ((count % number1page) > 0) {
				endPageCampaign ++;
			}
		
		//d. Lấy danh sách Campaign muốn tìm kiếm theo các thông số truyền vào model
		List<CampaignListViewIndex> listCampaignIndex =  campaignJDBCTemplate.searchListCampaignIndex(statusCurrent, id_campaign_code, province_code, indexcurrent, number1page);
		
		model.addAttribute("listCampaignIndex", listCampaignIndex);
		model.addAttribute("endPageCampaign", endPageCampaign);
		model.addAttribute("tagcurrent", indexcurrent);
		model.addAttribute("status", statusCurrent);
		model.addAttribute("id_campaign_code", id_campaign_code);
		model.addAttribute("province_code", province_code);
		
		//e. Lấy danh sách của CampaignCode và Provinces trong CSDL. (Để truyền vào phần tìm kiếm chọn lọc)
		List<CampaignCode> listCampaignCode = campaignJDBCTemplate.getListCampaignCode();
		List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
		
		//f. Truyền danh sách vào ModelMap hoặc session, request để truyền sang trang index (Có thể không cần thêm vì đã lưu vào session từ lúc vào index)
		HttpSession session = request.getSession();
		session.setAttribute("listCampaignCodeModel", listCampaignCode);
		session.setAttribute("listProvincesModel", listProvinces);
		
		return "index";
	}
	
	/*
	 * 2. Xử lý truy cập view một Campaign theo ID ở trang Index
	 */
		@RequestMapping (value = "/view1campaignindex.html", method = RequestMethod.GET)
		
		public String view1campaignindex(ModelMap model, HttpServletRequest request) {
			//a. Lấy thông tin được gửi lên
			int id_campaign = Integer.parseInt(request.getParameter("id_campaign"));
			int id_campaign_code = Integer.parseInt(request.getParameter("id_campaign_code"));
			int status = Integer.parseInt(request.getParameter("status"));
			String indexpage = request.getParameter("indexpage");
				
			//b. Lấy dữ liệu 1 Campaign thông qua id
			View1CampaignIndex oneCampaign = campaignJDBCTemplate.getView1CampaignIndex(id_campaign);
			
			//c. Lấy danh sách người ủng hộ một Campaign và tạo phân trang
			//c.1 Xác định trang đang xem (của danh sách ủng hộ) và phân trang. Nếu trang được chọn là null thì set về trang đầu tiên (Trường hợp mới vào trang hoặc chưa xác định)
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
			
			//d. Lấy danh sách TOP 8 chương trình quyên góp (khác 1 campaign hiện tại) nhưng cùng hoàn cảnh (cùng id_campaign_code) và cùng trạng thái status
			List<CampaignListViewIndex> listCampaignIndexCode = campaignJDBCTemplate.getCampaignListViewIndexCode(status, id_campaign_code, id_campaign);
			
			//e. Truyền về trang view1campaignindex
			//Dữ liệu campaign đang được truy cập
			model.addAttribute("oneCampaign", oneCampaign);
			model.addAttribute("accountDonateList", accountDonateList);
			model.addAttribute("listCampaignIndexCode", listCampaignIndexCode);
			
			//Thông tin lưu truyền phục vụ phân trang
			model.addAttribute("id_campaign_paging", id_campaign);
			model.addAttribute("id_campaign_code_paging", id_campaign_code);
			model.addAttribute("status_paging", status);
			
			model.addAttribute("endPageDonate", endPageDonate);
			model.addAttribute("tagcurrent", indexcurrent);
			
			return "view1campaignindex";
		}
		
		/*
		 * 3.Lọc danh sách Campaign ở trang index theo tiêu chí
		 */
		@RequestMapping (value = {"/searchListCampaignIndexByKey.html"}, method = RequestMethod.GET)

		public String searchListCampaignIndexByKey (ModelMap model, HttpServletRequest request) {
			
			//a. Lấy các tham số tìm kiếm được gửi lên từ trang index
			String searchkey = request.getParameter("searchkey");
			String indexpage = request.getParameter("indexpage");
				
				//Nếu trang được chọn là null thì set về trang đầu tiên (Trường hợp mới vào trang hoặc chưa xác định)
				if (indexpage == null) {
					indexpage = "1";
				}
				
			//Đặt lại chính xác trang đang được chọn
			int indexcurrent = Integer.parseInt(indexpage);
			
			//b. Lấy tổng số campaign đếm được theo searchkey
			int count = campaignJDBCTemplate.getSearchListCampaignIndexByKeyTotalNumber(searchkey);
			
			//c. Xác định số lượng trang sẽ có với tổng số lượng đó
			int endPageCampaign = count / number1page;
				
				//Nếu tổng số Campaign / số lượng một trang mà dư thì tăng thêm một trang (Trang cuối nếu là số còn dư sẽ được thêm và không đủ số lượng như những trang trước)
				if ((count % number1page) > 0) {
					endPageCampaign ++;
				}
			
			//d. Lấy danh sách Campaign muốn tìm kiếm theo các thông số truyền vào model
			List<CampaignListViewIndex> listCampaignIndex =  campaignJDBCTemplate.searchListCampaignIndexByKey(searchkey, indexcurrent, number1page);
			
			model.addAttribute("listCampaignIndex", listCampaignIndex);
			model.addAttribute("endPageCampaign", endPageCampaign);
			model.addAttribute("tagcurrent", indexcurrent);
			model.addAttribute("searchkey", searchkey); //Trả lại searchkey để hiển thị tại ô tìm kiếm
			
			//e. Lấy danh sách của CampaignCode và Provinces trong CSDL. (Để truyền vào phần tìm kiếm chọn lọc)
			List<CampaignCode> listCampaignCode = campaignJDBCTemplate.getListCampaignCode();
			List<Provinces> listProvinces = campaignJDBCTemplate.getListProvinces();
			
			//f. Truyền danh sách vào ModelMap hoặc session, request để truyền sang trang index (Có thể không cần thêm vì đã lưu vào session từ lúc vào index)
			HttpSession session = request.getSession();
			session.setAttribute("listCampaignCodeModel", listCampaignCode);
			session.setAttribute("listProvincesModel", listProvinces);
			
			return "searchindex";
		}
}
