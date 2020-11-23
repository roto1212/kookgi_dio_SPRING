package com.koreait.springMVCReq;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping("/memberView")
	public String memberView(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� memberView() �޼ҵ�");
//		������������ ��Ʈ�ѷ� �Ѿ�� HttpServletRequest �������̽� ��ü�� ����� �����͸� �޴´�.
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		System.out.println(id + pw);
//		��Ʈ�ѷ����� ���������� �Ѱ��� �����͸� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		return "memberView";
	}
	
//	@RequestParam("������������ ��Ʈ�ѷ��� �Ѿ���� ������") �ڷ��� �Ѿ�� �����͸� ������ ������
//	@RequestParam("id") String id => String id = request.getParameter("id")�� ���� ����� ����ȴ�. 
//	HttpServletRequest �������̽� ��ü�� �� ���������� �Ѿ���� �����͸� ���� ���� �����Ͱ� �Ѿ���� �ʾƵ� ������ ������
//	@RequestParam�� �̿��ؼ� ������������ �Ѿ���� �����͸� ���� ���� �����Ͱ� �Ѿ���� ������ 400 ������ ����.
	@RequestMapping("/memberLogin")
	public String memberLogin(/*HttpServletRequest request,*/ Model model, @RequestParam("id") String id, @RequestParam("pw") String pw) {
		System.out.println("HomeController�� memberLogin() �޼ҵ�");
		System.out.println(id + pw);
		
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);

		return "memberLogin";	
	}
	@RequestMapping("/member")
	public String member(HttpServletRequest request, Model model) {
		
		System.out.println("HomeController�� member() �޼ҵ�");
		return "member";
	}
	/*
	@RequestMapping("/memberInsert")
	public String memberInsert(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� memberInsert() �޼ҵ�");
//		������������ ��Ʈ�ѷ� �Ѿ�� HttpServletRequest �������̽� ��ü�� ����� �����͸� �޴´�.
		String name = request.getParameter("name");   
		String id = request.getParameter("id");   
		String pw = request.getParameter("pw");   
		String email = request.getParameter("email");
		
//		VO Ŭ������ ��ü�� ����� ���� �޼ҵ�� �����͸� �־��ش�.
//		MemberVO vo = new MemberVO();
		
//		VO Ŭ������ bean�� ������ setter �޼ҵ�� �����͸� �־��ش�.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberVO vo = ctx.getBean("vo", MemberVO.class);
		vo.setName(name);
		vo.setId(id);
		vo.setPw(pw);
		vo.setEmail(email);
		System.out.println(vo);
//		memberInsert.jsp�� VO Ŭ���� ��ü�� �Ѱ��ֱ� ���ؼ� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("vo", vo);
		return "memberInsert";
	}
	 */
	/*
	@RequestMapping("/memberInsert")
//	Ŀ�ǵ�(������) ��ü ����ϱ�
//	������������ ��Ʈ�ѷ��� �Ѿ���� �����͸� Ŭ���� ��ü�� �����Ϸ��� ��� Ŀ�ǵ尴ü�� ����ϸ� ���ϴ�.
//	Ŀ�ǵ� ��ü�� ����ϸ�  HttpServletRequest �������̽� ��ü�� @RequestParam���� �Ѱܹ��� �����͸� �޾Ƽ� Ŭ���� ��ü�� setter �޼ҵ�� ������ ���� Model �������̽� ��ü�� �־��ִ� ������ �ϰ������� ó���ȴ�.
//	Ŀ�ǵ� ��ü�� �̸��� �ݵ�� Ŀ�ǵ� ��ü�� ������ Ŭ���� �̸��� �����ϰ� �ۼ��ؾ� �ϰ� ù���ڸ� �ҹ��ڷ� �ٲ� ����ؾ� �Ѵ�.
	public String memberInsert(MemberVO memberVO) {
		System.out.println("HomeController�� memberInsert() �޼ҵ�");
		return "memberInsert";
	}
	 */
//	Ŀ�ǵ� ��ü�� �̸��� �ʹ� �� ����ϱ� �����ϰ��� ���������� �Ѱ��ִ� Ŀ�ǵ� ��ü�� �̸��� ������ �����ؼ� ����Ϸ��� @ModelAttribute�� �̿��� ���������� �Ѱ��ִ� Ŀ�ǵ� ��ü�� �̸��� ������ �� �ִ�.
//	�� ��� ���� Ŀ�ǵ� ��ü�̸��� ����� �� ����  @ModelAttribute���� ������ Ŀ�ǵ� ��ü�̸��� ����ؾ� �Ѵ�.
	@RequestMapping("/memberInsert")
	public String memberInsert(HttpServletRequest request, Model model, @ModelAttribute("vo") MemberVO memberVO) {
		System.out.println("HomeController�� memberInsert() �޼ҵ�");
		return "memberInsert";
	}
}
