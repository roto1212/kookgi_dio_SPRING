package com.koreait.springMVCBoard_DBCP;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
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

import com.koreait.dao.MvcboardDAO;
import com.koreait.dao.MybatisDAO;
import com.koreait.service.ContentViewService;
import com.koreait.service.DeleteService;
import com.koreait.service.IncrementService;
import com.koreait.service.InsertService;
import com.koreait.service.MvcboardService;
import com.koreait.service.ReplyService;
import com.koreait.service.SelectService;
import com.koreait.service.UpdateService;
import com.koreait.vo.MvcboardList;
import com.koreait.vo.MvcboardVO;

@Controller
public class HomeController {
//	servlet-context.xml 파일에서 생성한 mybatis bean을 사용하기 위해 SqlSession 인터페이스 타입의 객체를 선언한다.
//	servlet-context.xml 파일에서 생성한 mybatis bean을 자동으로 읽어와서 SqlSession 인터페이스 타입의 객체 넣기 위해 @Autowired를 붙여준다.
	@Autowired
	public SqlSession sqlSession;
//	↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑1126_springMVCBoard_mybatis에서 추가↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
//	===================================================================================================
//	↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓1126_springMVCBoard_DBCP_template에서 추가↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
//	mybatis로 변환이 완료되면 DBCP Template에 사용한 설정 코드는 주석으로 처리한다.
//	private JdbcTemplate jdbcTemplate;
//	public JdbcTemplate getJdbcTemplate() {
//		return jdbcTemplate;
//	}
//	@Autowired		
//	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//		Constant.jdbcTemplate = jdbcTemplate;
//	}
//	↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑1126_springMVCBoard_DBCP_template에서 추가↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	@RequestMapping("/")
	public String home(Locale locale, Model model) {
		System.out.println("home(): 프로젝트가 실행될 때 최초 요청을 받는 메소드");
		return "redirect:list";
	}
	
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 insert() 메소드 실행");
		return "insert";
	}
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 insertOK() 메소드 실행 - Model 클래스 객체 사용");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(model);
		*/
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//		HttpServletRequest 인터페이스 객체로 넘어온 insert.jsp에서 넘겨준 데이터를 받는다.
		/*
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
//		sql 명령을 실행한다.
//		xml 파일에서 데이터를 받을 때 parma1, parma2, ... 형태로 받아야 한다. 
		mapper.insert(name, subject, content);
		*/
//		MvcboardVO 클래스의 bean을 얻어온다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);

//		MvcboardVO 클래스의 bean에 insert.jsp에서 request 객체로 넘어온 데이터를 저장한다.
		mvcboardVO.setName(request.getParameter("name"));
		mvcboardVO.setSubject(request.getParameter("subject"));
		mvcboardVO.setcontent(request.getParameter("content"));
//		System.out.println("vo: " + mvcboardVO);

//		메인글을 테이블에 저장하는 메소드를 실행한다.
		mapper.insert(mvcboardVO);

		return "redirect:list";
	}
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 list() 메소드 실행");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("select", SelectService.class);
		service.execute(model);
		*/
		
//		mapper를 얻어온다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
//	브라우저 화면에 출력할 글의 개수를 정한다.
		int pageSize = 10;
//	HttpServletRequest 인터페이스 객체로 넘어온 화면에 표시할 페이지 번호를 받는다.
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {}
//	테이블에 저장된 전체 글의 개수를 얻어온다.
		int totalCount = mapper.selectCount();
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//	System.out.println("전체 글의 개수: " + totalCount);
		MvcboardList mvcboardList = ctx.getBean("mvcboardList", MvcboardList.class);	
		
//	JSP로 작업할 때처럼 생성자를 사용하는 초기화가 불가능하다. 그래서  MvcboardList클래스의 bean을 얻어온 후 8개의 변수를 초기화하는 메소드를 실행한다.
		mvcboardList.initMvcboardList(pageSize, totalCount, currentPage);
//		System.out.println(mvcboardList);
		
//	MvcboardList 클래스의 1페이지 분량의 글을 기억하는 배열목록에 1페이지 분량의 글을 테이블에서 얻어와서 넣어준다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", mvcboardList.getStartNo());
		hmap.put("endNo", mvcboardList.getEndNo());
		mvcboardList.setList(mapper.selectList(hmap));
		
//		list.jsp로 넘겨줄 데이터를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("mvcboardList", mvcboardList);

		return "list";

	}
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 increment() 메소드 실행 - Model 클래스 객체 사용");
		
//		model.addAttribute("request", request);
		
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//		MvcboardService service = ctx.getBean("increment", IncrementService.class);
//		service.execute(model);
		
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//	HttpServletRequest 인터페이스 객체로 넘어온 조회수를 증가시킬 글번호를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
//	조횟수를 증가시키는 메소드를 실행한다.	
		mapper.increment(idx);

		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		model.addAttribute("idx", idx);
		model.addAttribute("currentPage", currentPage);

		return "redirect:contentView";
	}
	
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 contentView() 메소드 실행 - Model 클래스 객체 사용");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		*/
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//		request 객체로 넘어온 조회수를 증가시킨 글번호를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//		조회수를 증가시킨 글 1건을 얻어와서 MvcboardVO 클래스 객체에 저장한다.
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		mvcboardVO = mapper.selectByIdx(idx);

//		System.out.println(mvcboardVO);
		
//		브라우저에 출력할 글, 작업 후 돌아갈 페이지 번호, 줄바꿈 구현에 사용할 \r\n을 Model 인터페이스 객체에 저장한다.
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		model.addAttribute("enter", "\r\n");

		return "contentView";
	}
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 delete() 메소드 실행 - Model 클래스 객체 사용");
/*		
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("delete", DeleteService.class);
		service.execute(model);
*/	
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
//		request 객체로 넘어온 삭제할 글의 글번호를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
//		글 1건을 삭제하는 메소드를 실행한다.
		mapper.delete(idx);
		
//		글 삭제 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));

		return "redirect:list";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		System.out.println("HomeController의 update() 메소드 실행 - Model 클래스 객체 사용");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("update", UpdateService.class);
		service.execute(model);
		*/
		/*
//	1. request 객체로 넘어온 수정할 글의 글번호와 데이터를 받는다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		mvcboardVO.setIdx(Integer.parseInt(request.getParameter("idx")));
		mvcboardVO.setSubject(request.getParameter("subject"));
		mvcboardVO.setcontent(request.getParameter("content"));
//	글 1건을 수정하는 메소들 실행한다.
		mapper.update(mvcboardVO);
		*/
//	2. 커맨드 객체로 데이터를 받아서 글 한 건을 수정하는 메소드를 실행한다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		mapper.update(mvcboardVO);
		
//	글 수정 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		
		return "redirect:list";
	}
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("HomeController의 reply() 메소드 실행 - Model 클래스 객체 사용");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		*/
//	답글달기 버튼을 눌렀을 때 원글을 보여준다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//	request 객체로 넘어온 답글을 이볅할 메인글의 글번호를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//	답글을 입력할 글 1건을 얻어와서 MvcboardVO 클래스 객체에 저장한다.
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		mvcboardVO = mapper.selectByIdx(idx);

//		System.out.println(mvcboardVO);
		
//	브라우저에 출력할 글, 작업 후 돌아갈 페이지 번호, 줄바꿈 구현에 사용할 \r\n을 Model 인터페이스 객체에 저장한다.
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		model.addAttribute("enter", "\r\n");

		return "reply";
	}
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		System.out.println("HomeController의 replyInsert() 메소드 실행 - Model 클래스 객체 사용");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("reply", ReplyService.class);
		service.execute(model);
		*/
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//	커맨드  객체를 사용해서 답글 데이터를 받고 lev와 seq는 1씩 증가시킨다.
//	답글은 질문글 바로 아래에 위치해야 하므로 lev와 seq는 1씩 증가시켜 저장한다.
		
		mvcboardVO.setLev(mvcboardVO.getLev() + 1);
		mvcboardVO.setSeq(mvcboardVO.getSeq() + 1);
//	답글이 삽입될 위치를 정하기 위해 조건에 만족하는 seq를 1씩 증가시크는 메소드를 실행한다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("ref", mvcboardVO.getRef());
		hmap.put("seq", mvcboardVO.getSeq());
		
		mapper.replyIncrement(hmap);
		
//	답글을 저장하는 메소드를 실행한다.
		mapper.replyInsert(mvcboardVO);
		
//	답글 입력 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));

		
		return "redirect:list";
	}
	
}
