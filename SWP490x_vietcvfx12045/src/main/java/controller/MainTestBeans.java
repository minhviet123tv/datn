package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.ls.LSOutput;

import model.Account;
import model.Campaign;
import model.CampaignCode;
import model.CampaignListViewAdmin;
import model.Gender;

public class MainTestBeans {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("controller/Beans.xml");
		AccountJDBCTemplate accountJDBCTemplate = (AccountJDBCTemplate) context.getBean("AccountJDBCTemplate");
		
		String email = "jimmii.love25@gmail.com";
		String password = "";
		
		Account account = accountJDBCTemplate.getAccount(email);
		System.out.println(account.getEmail());
		System.out.println(account.getPassword());
		
		
//		List<CampaignCode> listCampaigns = campaignJDBCTemplate.getListCampaignCode();
//		for (int i=0; i<listCampaigns.size(); i++) {
//			System.out.print(listCampaigns.get(i).getId_campaign_code() + ". ");
//			System.out.println(listCampaigns.get(i).getName_vn());
//		}
//		
//		System.out.println("---");
		
	
	}
}
