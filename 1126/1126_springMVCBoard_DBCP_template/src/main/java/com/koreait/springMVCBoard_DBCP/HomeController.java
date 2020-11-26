package com.koreait.springMVCBoard_DBCP;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koreait.service.ContentViewService;
import com.koreait.service.DeleteService;
import com.koreait.service.IncrementService;
import com.koreait.service.InsertService;
import com.koreait.service.MvcboardService;
import com.koreait.service.ReplyService;
import com.koreait.service.SelectService;
import com.koreait.service.UpdateService;
import com.koreait.vo.MvcboardVO;

@Controller
public class HomeController {
//	JdbcTemplate을 사용하려면 servlet-context.xml에서 프로젝트가 시작될 때 DriverManagerDataSource 클래스의 bean을 이용해 생성한 데이터베이스 연결정보(dataSource)을 참조해서 생성한 JdbcTemplate 클래스의 bean template을 JdbcTemplate 타입의 멤버 변수로 선언하고 넣어줘야 한다.
	private JdbcTemplate jdbcTemplate;
//	JdbcTemplate 클래스 타입의 객체를 선언한 후 getter와 setter를 만든다. => 사실 getter는 안만들어도 상관없다.
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
//	프로젝트가 실행될 때 자동으로 JdbcTemplate 클래스 객체의 setter 메소드가 실행되게 하기 위해서 @Autowired을 붙여준다.
//	@Autowired이 붙은 메소드의 인수로 스프링이 servlet-context.xml에서 생성한 JdbcTemplate 클래스의 bean을 자동으로 받아서 setter 메소드의 인수로 전달하고 멤버로 선언된 JdbcTemplate 클래스 객체를 초기화 시킨다.
	
	@Autowired		// @Autowired을 붙여준 메소드는 프로젝트가 시작될 때 자동으로 실행된다.
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
//		System.out.println("setJdbcTemplate() 메소드 실행");
//		System.out.println(jdbcTemplate);
//		여기까지 실행되면 컨트롤러에서 DBCP Template을 사용할 수 있다.
//		데이터베이스와 연결은 주로 DAO 클래스에서 사용하므로 컨트롤러 이외의 클래스에서 DBCP Template을 사용할 수 있게 하기 위해서 적당한 이름의 패키지에 적당한 이름으로 클래스를 만들고 선언한 정적변수 servlet-context.xml에서 생성된 JdbcTemplate 클래스의 bean을 넣어준다.
		Constant.jdbcTemplate = jdbcTemplate;
		
	}
//	↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑1126_springMVCBoard_DBCP_template에서 추가↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	@RequestMapping("/")
	public String home(Locale locale, Model model) {
		System.out.println("HomeController의 home() 메소드 실행");
		return "redirect:list";
	}
//	글 입력폼(insert.jsp)을 호출하는 메소드
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 insert() 메소드 실행");
		return "insert";
	}
//	입력 폼에 입력된 데이터를 테이블에 저장하고 브라우저에 출력할 1페이지 분량의 글을 얻어오는 컨트롤러의 메소드를 호출한다.
//		=> request로 받아서 MvcboardVO 클래스 객체에 저장한다. 
	//text_MvcboardVO
	/*
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 insertOK() 메소드 실행 - HttpServletRequest 인터페이스 객체 사용");
		
//		insert.jsp에서 넘어오는 데이터를 받는다.
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
//		MvcboardVO 클래스의 bean을 얻어온다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		
//		MvcboardVO 클래스의 bean에 insert.jsp에서 request 객체로 넘어온 데이터를 저장한다.
		mvcboardVO.setName(name);
		mvcboardVO.setSubject(subject);
		mvcboardVO.setcontent(content);
//		System.out.println("vo: " + mvcboardVO);
		
//		테이블에 메인글을 저장하는 메소드를 호출한다.
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(mvcboardVO);
		return "redirect:list";
	}
	*/
//	입력 폼에 입력된 데이터를 테이블에 저장하고 브라우저에 출력할 1페이지 분량의 글을 얻어오는 컨트롤러의 메소드를 호출한다.
//	=> 커맨드 객체에 사용한다. 
	/*
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		System.out.println("HomeController의 insertOK() 메소드 실행 - 커맨드 객체 사용");
//		System.out.println("vo: " + mvcboardVO);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(mvcboardVO);
		return "redirect:list";
	}
	*/
//	입력 폼에 입력된 데이터를 테이블에 저장하고 브라우저에 출력할 1페이지 분량의 글을 얻어오는 컨트롤러의 메소드를 호출한다.
//	=> request로 받아서 Model 클래스 객체에 저장한다. 
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 insertOK() 메소드 실행 - Model 클래스 객체 사용");
		
//		insert.jsp에서 입력한 데이터가 저장된 request 객체를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(model);
		
//		메인글을 저장했으므로 1페이지 분량의 글을 얻어오는 요청을 하기 위해 redirect 기능을 사용해 @RequestMapping("/list")이 붙은 컨트롤러 내부의 메소드를 호출한다.
		
		return "redirect:list";
	}
//	브라우저에 출력할 1페이지 분량의 글을 얻어오고 1페이지 분량의 글을 브라우저에 출력하는 페이지를 호출하는 메소드
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 list() 메소드 실행 - Model 클래스 객체 사용");
		
//		컨트롤러에게 list로 요청하는 페이지에서 넘어오는 브라우저에 표시할 페이지 번호가 저장된 request객체를 model 객체에 저장한다.
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("select", SelectService.class);
		service.execute(model);
		
		return "list";
	}
//	글을 클릭하면 조회수를 증가시키는 메소드
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 increment() 메소드 실행 - Model 클래스 객체 사용");
		
//		조회수를 증가시킬 글번호가 저장된 request 객체를 model 객체에 저장한다.
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("increment", IncrementService.class);
		service.execute(model);
		
//		조회수를 증가시킨 글(브라우저에 표시할 글) 번호와 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 저장한다.
		int idx = Integer.parseInt(request.getParameter("idx"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		model.addAttribute("idx", idx);
		model.addAttribute("currentPage", currentPage);

		return "redirect:contentView";
	}
	
//	조회수를 증가시킨 글 한건을 브라우저에 출력하기 위해 테이블에서 얻어오는 메소드
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 contentView() 메소드 실행 - Model 클래스 객체 사용");
//		조회수를 증가시킨 글번호와 작업후 돌아갈 페이지 번호가 저장된 request 객체를 model 객체에 저장한다.
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		
		return "contentView";
	}
//	글 1건을 삭제하는 메소드
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 delete() 메소드 실행 - Model 클래스 객체 사용");
//		삭제할 글번호와 작업후 돌아갈 페이지 번호가 저장된 request 객체를 model 객체에 저장한다.
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("delete", DeleteService.class);
		service.execute(model);
		
		return "redirect:list";
	}
//	글 1건을 수정하는 메소드
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 update() 메소드 실행 - Model 클래스 객체 사용");
//		수정할 글번호와 데이터, 수정 후 돌아갈 페이지 번호가 저장된 request 객체를 model 객체에 저장한다.
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("update", UpdateService.class);
		service.execute(model);
		
		return "redirect:list";
	}
//	답글을 입력하기 위해 브라우저 화면에 출력할 메인글을 얻어오고 답글을 입력하는 페이지를 호출하는 메소드
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 reply() 메소드 실행 - Model 클래스 객체 사용");
//		답변을 입력할 원본글의 글번호 작업 후 돌아갈 페이지 번호가 저장된 request 객체를 model 객체에 저장한다.
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//		답변달기 버튼을 눌렀을 때 보여줄 질문글 1건을 불러오기 위해 contentView를 불러와 저장한다.
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		
		return "reply";
	}
//	답글을 위치에 맞게 저장하는 메소드	
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 replyInsert() 메소드 실행 - Model 클래스 객체 사용");
//		답변할 원본 글번호, 글그룹(ref), 글레벨(lev), 같은 글 그룹(ref)에서 글 출력순서(seq), 답글 작성자 이름, 답글 제목, 답글 내용, 답글을 저장하고 돌아갈 페이지 번호가 저장된 request객체를 model 객체에 저장한다.
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("reply", ReplyService.class);
		service.execute(model);
		
		return "redirect:list";
	}
	
}
