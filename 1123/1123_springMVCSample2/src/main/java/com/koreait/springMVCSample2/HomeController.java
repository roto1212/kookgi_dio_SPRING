package com.koreait.springMVCSample2;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//	@Controller�� ���� Ŭ������ ��Ʈ�ѷ��� ���ȴ�. 
//	@Controller�� �پ� �ִ� �ϴ��� ��� Ŭ������ ��Ʈ�ѷ��� ���Ǵ� ���� �ƴϰ� servlet-context.xml ���Ͽ� ������Ʈ�� ������ �� �Է��� �̸����� ������ base-package�� ������ ��Ű���� Ŭ���� �� @Controller�� �ٿ��� �͸� ��Ʈ�ѷ��� ����� �� �ִ�.
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	@RequestMapping�� method �Ӽ��� RequestMethod.GET�� �����ϸ� get����� ��û�� ó���ϰ� .POST�� ���� post����� ��û�� ó���Ѵ�.
//	=> ������ ó����İ� �ٸ� ��û�� ������ 405 error�� �߻��Ѵ�.
//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	method �Ӽ��� �����ϸ� get ��İ� post ����� �������� �ʰ� ��� ó�� �����ϴ�.
//	@RequestMapping(value = "/")
	
//	value �Ӽ����� �����ϰ� �Ӽ��� ���ܵ� ���� ���� �۵��Ѵ�.
	@RequestMapping("/")
//	home�޼ҵ�� �ּ� â�� context�̸�(base-package="A.B.C" ���·� ������� �� C�� �ش�Ǵ� ����) ������ "/"�� ��û�Ǵ� ��� �ڵ����� ����Ǵ� �޼ҵ�� �ʿ��� �۾��� �ϰ� view ������ �̸��� ���Ͻ�Ų��.
//	view ������ �̸��� ���ڿ��̹Ƿ� ����Ÿ���� String���� �ؾ��ϰ� view �������� ��, �ڿ� �ڵ����� �ٿ��ִ� ������ servlet-context.xml ���Ͽ��� InternalResourceViewResolverŬ������ bean���� ó���Ѵ�.
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	@RequestMapping("/hello")
	public String hello(Locale locale, Model model) {
		return "hello";
	
	}
}
