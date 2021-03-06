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

//@Controller 가 붙은 클래스가 컨트롤러 클래스로 사용된다.
//@Controller가 붙어있다 하더라도 모두 컨트롤러 클래스로 사용되는 것이 아니고 servlet-context.xml 파일에서 프로젝트를 생성할 때 작성한 base-package로 지정한 패키지 중 @Controller가 붙여진 클래스를 컨트롤러로 사용한다.

@Controller	
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	주소창에 서비스 요청이 들어오면 servlet-context.xml 파일의 base-package로 지정된 패키지의 @Controller을 붙여준 클래스(컨트롤러)의 @RequestMapping의 인수로 지정된 Value 속성(생략 가능)에 지정된 내용과 주소창에 요청된 내용이 같은 @RequestMapping가 붙어있는 메소드가 실행된다.
//	method 속성으로 RequestMethod.GET이 지정되면 get 방식으로 들어오는 요청만 처리할 수 있고 RequestMethod.POST를 지정하면 post 방식으로 들어오는 요청만 처리할 수 있다.
//	=> 요청방식이 일치하지 않으면 405 에러가 발생된다.
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	@RequestMapping("/")와 같이 method 속성을 생략하면 get/post 방식을 구분하지 않고 모든 요청을 처리 할 수 있다. value속성만 남은 경우 value라고 적지 않고 해당 속성만 적어도 된다.
	@RequestMapping("/")
//	@RequestMapping이 붙은 메소드는 필요한 작업을 실행하고 view 페이지(jsp파일) 이름을 리턴시킨다.
//	view 페이지의 이름은 문자열이므로 메소드의 리턴 타입은 String이 되고 리턴되는 view 페이지의 앞, 뒤에 붙여주는 내용은 servlet-context.xml 파일에서 설정한다.
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";	//view 페이지 이름
	}
	
}
