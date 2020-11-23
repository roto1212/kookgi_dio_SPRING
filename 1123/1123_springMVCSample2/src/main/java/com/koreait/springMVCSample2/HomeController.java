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

//	@Controller가 붙은 클래스가 컨트롤러로 사용된다. 
//	@Controller가 붙어 있다 하더라도 모든 클래스가 컨트롤러로 사용되는 것이 아니고 servlet-context.xml 파일에 프로젝트를 생성할 때 입력한 이름으로 생성된 base-package로 지정된 패키지의 클래스 중 @Controller가 붙여진 것만 컨트롤러로 사용할 수 있다.
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	@RequestMapping의 method 속성에 RequestMethod.GET을 지정하면 get방식의 요청을 처리하고 .POST를 쓰면 post방식의 요청을 처리한다.
//	=> 지정한 처리방식과 다른 요청이 들어오면 405 error가 발생한다.
//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	method 속성을 생략하면 get 방식과 post 방식을 구분하지 않고 모두 처리 가능하다.
//	@RequestMapping(value = "/")
	
//	value 속성명은 생략하고 속성만 남겨도 위와 같이 작동한다.
	@RequestMapping("/")
//	home메소드는 주소 창에 context이름(base-package="A.B.C" 형태로 만들어질 때 C에 해당되는 내용) 다음에 "/"가 요청되는 경우 자동으로 실행되는 메소드로 필요한 작업을 하고 view 페이지 이름을 리턴시킨다.
//	view 페이지 이름은 문자열이므로 리턴타입은 String으로 해야하고 view 페이지의 앞, 뒤에 자동으로 붙여주는 내용은 servlet-context.xml 파일에서 InternalResourceViewResolver클래스의 bean에서 처리한다.
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
