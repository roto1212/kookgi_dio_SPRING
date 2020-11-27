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
//	servlet-context.xml ���Ͽ��� ������ mybatis bean�� ����ϱ� ���� SqlSession �������̽� Ÿ���� ��ü�� �����Ѵ�.
//	servlet-context.xml ���Ͽ��� ������ mybatis bean�� �ڵ����� �о�ͼ� SqlSession �������̽� Ÿ���� ��ü �ֱ� ���� @Autowired�� �ٿ��ش�.
	@Autowired
	public SqlSession sqlSession;
//	����������������1126_springMVCBoard_mybatis���� �߰�������������������������
//	===================================================================================================
//	������������������������1126_springMVCBoard_DBCP_template���� �߰�������������������������
//	mybatis�� ��ȯ�� �Ϸ�Ǹ� DBCP Template�� ����� ���� �ڵ�� �ּ����� ó���Ѵ�.
//	private JdbcTemplate jdbcTemplate;
//	public JdbcTemplate getJdbcTemplate() {
//		return jdbcTemplate;
//	}
//	@Autowired		
//	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//		Constant.jdbcTemplate = jdbcTemplate;
//	}
//	����������������1126_springMVCBoard_DBCP_template���� �߰�������������������������
	
	@RequestMapping("/")
	public String home(Locale locale, Model model) {
		System.out.println("home(): ������Ʈ�� ����� �� ���� ��û�� �޴� �޼ҵ�");
		return "redirect:list";
	}
	
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� insert() �޼ҵ� ����");
		return "insert";
	}
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� insertOK() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(model);
		*/
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//		HttpServletRequest �������̽� ��ü�� �Ѿ�� insert.jsp���� �Ѱ��� �����͸� �޴´�.
		/*
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
//		sql ����� �����Ѵ�.
//		xml ���Ͽ��� �����͸� ���� �� parma1, parma2, ... ���·� �޾ƾ� �Ѵ�. 
		mapper.insert(name, subject, content);
		*/
//		MvcboardVO Ŭ������ bean�� ���´�.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);

//		MvcboardVO Ŭ������ bean�� insert.jsp���� request ��ü�� �Ѿ�� �����͸� �����Ѵ�.
		mvcboardVO.setName(request.getParameter("name"));
		mvcboardVO.setSubject(request.getParameter("subject"));
		mvcboardVO.setcontent(request.getParameter("content"));
//		System.out.println("vo: " + mvcboardVO);

//		���α��� ���̺� �����ϴ� �޼ҵ带 �����Ѵ�.
		mapper.insert(mvcboardVO);

		return "redirect:list";
	}
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� list() �޼ҵ� ����");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("select", SelectService.class);
		service.execute(model);
		*/
		
//		mapper�� ���´�.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
//	������ ȭ�鿡 ����� ���� ������ ���Ѵ�.
		int pageSize = 10;
//	HttpServletRequest �������̽� ��ü�� �Ѿ�� ȭ�鿡 ǥ���� ������ ��ȣ�� �޴´�.
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {}
//	���̺� ����� ��ü ���� ������ ���´�.
		int totalCount = mapper.selectCount();
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//	System.out.println("��ü ���� ����: " + totalCount);
		MvcboardList mvcboardList = ctx.getBean("mvcboardList", MvcboardList.class);	
		
//	JSP�� �۾��� ��ó�� �����ڸ� ����ϴ� �ʱ�ȭ�� �Ұ����ϴ�. �׷���  MvcboardListŬ������ bean�� ���� �� 8���� ������ �ʱ�ȭ�ϴ� �޼ҵ带 �����Ѵ�.
		mvcboardList.initMvcboardList(pageSize, totalCount, currentPage);
//		System.out.println(mvcboardList);
		
//	MvcboardList Ŭ������ 1������ �з��� ���� ����ϴ� �迭��Ͽ� 1������ �з��� ���� ���̺��� ���ͼ� �־��ش�.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", mvcboardList.getStartNo());
		hmap.put("endNo", mvcboardList.getEndNo());
		mvcboardList.setList(mapper.selectList(hmap));
		
//		list.jsp�� �Ѱ��� �����͸� Model �������̽� ��ü�� �־��ش�.
		model.addAttribute("mvcboardList", mvcboardList);

		return "list";

	}
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� increment() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		
//		model.addAttribute("request", request);
		
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//		MvcboardService service = ctx.getBean("increment", IncrementService.class);
//		service.execute(model);
		
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//	HttpServletRequest �������̽� ��ü�� �Ѿ�� ��ȸ���� ������ų �۹�ȣ�� �޴´�.
		int idx = Integer.parseInt(request.getParameter("idx"));
//	��Ƚ���� ������Ű�� �޼ҵ带 �����Ѵ�.	
		mapper.increment(idx);

		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		model.addAttribute("idx", idx);
		model.addAttribute("currentPage", currentPage);

		return "redirect:contentView";
	}
	
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� contentView() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		*/
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//		request ��ü�� �Ѿ�� ��ȸ���� ������Ų �۹�ȣ�� �޴´�.
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//		��ȸ���� ������Ų �� 1���� ���ͼ� MvcboardVO Ŭ���� ��ü�� �����Ѵ�.
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		mvcboardVO = mapper.selectByIdx(idx);

//		System.out.println(mvcboardVO);
		
//		�������� ����� ��, �۾� �� ���ư� ������ ��ȣ, �ٹٲ� ������ ����� \r\n�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		model.addAttribute("enter", "\r\n");

		return "contentView";
	}
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� delete() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
/*		
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("delete", DeleteService.class);
		service.execute(model);
*/	
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
//		request ��ü�� �Ѿ�� ������ ���� �۹�ȣ�� �޴´�.
		int idx = Integer.parseInt(request.getParameter("idx"));
//		�� 1���� �����ϴ� �޼ҵ带 �����Ѵ�.
		mapper.delete(idx);
		
//		�� ���� �۾� �� ���ư� ������ ��ȣ�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));

		return "redirect:list";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		System.out.println("HomeController�� update() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("update", UpdateService.class);
		service.execute(model);
		*/
		/*
//	1. request ��ü�� �Ѿ�� ������ ���� �۹�ȣ�� �����͸� �޴´�.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		mvcboardVO.setIdx(Integer.parseInt(request.getParameter("idx")));
		mvcboardVO.setSubject(request.getParameter("subject"));
		mvcboardVO.setcontent(request.getParameter("content"));
//	�� 1���� �����ϴ� �޼ҵ� �����Ѵ�.
		mapper.update(mvcboardVO);
		*/
//	2. Ŀ�ǵ� ��ü�� �����͸� �޾Ƽ� �� �� ���� �����ϴ� �޼ҵ带 �����Ѵ�.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		mapper.update(mvcboardVO);
		
//	�� ���� �۾� �� ���ư� ������ ��ȣ�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		
		return "redirect:list";
	}
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� reply() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		*/
//	��۴ޱ� ��ư�� ������ �� ������ �����ش�.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//	request ��ü�� �Ѿ�� ����� �̓��� ���α��� �۹�ȣ�� �޴´�.
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//	����� �Է��� �� 1���� ���ͼ� MvcboardVO Ŭ���� ��ü�� �����Ѵ�.
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		mvcboardVO = mapper.selectByIdx(idx);

//		System.out.println(mvcboardVO);
		
//	�������� ����� ��, �۾� �� ���ư� ������ ��ȣ, �ٹٲ� ������ ����� \r\n�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		model.addAttribute("enter", "\r\n");

		return "reply";
	}
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		System.out.println("HomeController�� replyInsert() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		/*
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("reply", ReplyService.class);
		service.execute(model);
		*/
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//	Ŀ�ǵ�  ��ü�� ����ؼ� ��� �����͸� �ް� lev�� seq�� 1�� ������Ų��.
//	����� ������ �ٷ� �Ʒ��� ��ġ�ؾ� �ϹǷ� lev�� seq�� 1�� �������� �����Ѵ�.
		
		mvcboardVO.setLev(mvcboardVO.getLev() + 1);
		mvcboardVO.setSeq(mvcboardVO.getSeq() + 1);
//	����� ���Ե� ��ġ�� ���ϱ� ���� ���ǿ� �����ϴ� seq�� 1�� ������ũ�� �޼ҵ带 �����Ѵ�.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("ref", mvcboardVO.getRef());
		hmap.put("seq", mvcboardVO.getSeq());
		
		mapper.replyIncrement(hmap);
		
//	����� �����ϴ� �޼ҵ带 �����Ѵ�.
		mapper.replyInsert(mvcboardVO);
		
//	��� �Է� �۾� �� ���ư� ������ ��ȣ�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));

		
		return "redirect:list";
	}
	
}
