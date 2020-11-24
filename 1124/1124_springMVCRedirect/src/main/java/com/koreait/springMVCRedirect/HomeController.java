package com.koreait.springMVCRedirect;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	@RequestMapping("/confirm")
	public String confirm(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� confirm() �޼ҵ�");
		String id = request.getParameter("id");
		model.addAttribute("id",id);
		
		if (id.equals("abcd")) {
//			�Ʒ��� ���� ���Ͻ�Ű�� "/WEB-INF/views/" + "idOK" + ".jsp"�� ����Ǽ� idOK.jsp ������ �������� ǥ�õȴ�.
//			return "idOK";		// �������� �̸�
			
//			�Ʒ��� ���� "redirect:"�� �ٿ��� ���Ͻ�Ű�� "/WEB-INF/views/" ������ jsp������ ȣ���ϴ� ���� �ƴϰ� ��Ʈ�ѷ��� @RequestMapping("/confirmOK")�� ���� �޼ҵ带 ȣ���Ѵ�.
			return "redirect:confirmOK";
		} else {
			return "redirect:confirmNG";
		}
	}
	 @RequestMapping("/confirmOK")
	 public String confirmOK(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� confirmOK() �޼ҵ�");
		String id = request.getParameter("id");
		model.addAttribute("id",id);
		return "idOK";
	 }
	 @RequestMapping("/confirmNG")
	 public String confirmNG(HttpServletRequest request, Model model) {
		 System.out.println("HomeController�� confirmNG() �޼ҵ�");
		 String id = request.getParameter("id");
		 model.addAttribute("id",id);
		 return "idNG";
	 }
}
