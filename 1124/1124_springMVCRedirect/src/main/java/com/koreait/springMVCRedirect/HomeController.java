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
		System.out.println("HomeController의 confirm() 메소드");
		String id = request.getParameter("id");
		model.addAttribute("id",id);
		
		if (id.equals("abcd")) {
//			아래와 같이 리턴시키면 "/WEB-INF/views/" + "idOK" + ".jsp"이 연결되서 idOK.jsp 파일이 브라우저에 표시된다.
//			return "idOK";		// 뷰페이지 이름
			
//			아래와 같이 "redirect:"를 붙여서 리턴시키면 "/WEB-INF/views/" 폴더의 jsp파일을 호출하는 것이 아니고 컨트롤러의 @RequestMapping("/confirmOK")이 붙은 메소드를 호출한다.
			return "redirect:confirmOK";
		} else {
			return "redirect:confirmNG";
		}
	}
	 @RequestMapping("/confirmOK")
	 public String confirmOK(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 confirmOK() 메소드");
		String id = request.getParameter("id");
		model.addAttribute("id",id);
		return "idOK";
	 }
	 @RequestMapping("/confirmNG")
	 public String confirmNG(HttpServletRequest request, Model model) {
		 System.out.println("HomeController의 confirmNG() 메소드");
		 String id = request.getParameter("id");
		 model.addAttribute("id",id);
		 return "idNG";
	 }
}
