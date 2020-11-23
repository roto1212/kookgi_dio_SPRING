package com.koreait.springMVCSample;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller �� ���� Ŭ������ ��Ʈ�ѷ� Ŭ������ ���ȴ�.
//@Controller�� �پ��ִ� �ϴ��� ��� ��Ʈ�ѷ� Ŭ������ ���Ǵ� ���� �ƴϰ� servlet-context.xml ���Ͽ��� ������Ʈ�� ������ �� �ۼ��� base-package�� ������ ��Ű�� �� @Controller�� �ٿ��� Ŭ������ ��Ʈ�ѷ��� ����Ѵ�.

@Controller	
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	�ּ�â�� ���� ��û�� ������ servlet-context.xml ������ base-package�� ������ ��Ű���� @Controller�� �ٿ��� Ŭ����(��Ʈ�ѷ�)�� @RequestMapping�� �μ��� ������ Value �Ӽ�(���� ����)�� ������ ����� �ּ�â�� ��û�� ������ ���� @RequestMapping�� �پ��ִ� �޼ҵ尡 ����ȴ�.
//	method �Ӽ����� RequestMethod.GET�� �����Ǹ� get ������� ������ ��û�� ó���� �� �ְ� RequestMethod.POST�� �����ϸ� post ������� ������ ��û�� ó���� �� �ִ�.
//	=> ��û����� ��ġ���� ������ 405 ������ �߻��ȴ�.
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	@RequestMapping("/")�� ���� method �Ӽ��� �����ϸ� get/post ����� �������� �ʰ� ��� ��û�� ó�� �� �� �ִ�. value�Ӽ��� ���� ��� value��� ���� �ʰ� �ش� �Ӽ��� ��� �ȴ�.
	@RequestMapping("/")
//	@RequestMapping�� ���� �޼ҵ�� �ʿ��� �۾��� �����ϰ� view ������(jsp����) �̸��� ���Ͻ�Ų��.
//	view �������� �̸��� ���ڿ��̹Ƿ� �޼ҵ��� ���� Ÿ���� String�� �ǰ� ���ϵǴ� view �������� ��, �ڿ� �ٿ��ִ� ������ servlet-context.xml ���Ͽ��� �����Ѵ�.
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";	//view ������ �̸�
	}
	
}
