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
//	JdbcTemplate�� ����Ϸ��� servlet-context.xml���� ������Ʈ�� ���۵� �� DriverManagerDataSource Ŭ������ bean�� �̿��� ������ �����ͺ��̽� ��������(dataSource)�� �����ؼ� ������ JdbcTemplate Ŭ������ bean template�� JdbcTemplate Ÿ���� ��� ������ �����ϰ� �־���� �Ѵ�.
	private JdbcTemplate jdbcTemplate;
//	JdbcTemplate Ŭ���� Ÿ���� ��ü�� ������ �� getter�� setter�� �����. => ��� getter�� �ȸ��� �������.
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
//	������Ʈ�� ����� �� �ڵ����� JdbcTemplate Ŭ���� ��ü�� setter �޼ҵ尡 ����ǰ� �ϱ� ���ؼ� @Autowired�� �ٿ��ش�.
//	@Autowired�� ���� �޼ҵ��� �μ��� �������� servlet-context.xml���� ������ JdbcTemplate Ŭ������ bean�� �ڵ����� �޾Ƽ� setter �޼ҵ��� �μ��� �����ϰ� ����� ����� JdbcTemplate Ŭ���� ��ü�� �ʱ�ȭ ��Ų��.
	
	@Autowired		// @Autowired�� �ٿ��� �޼ҵ�� ������Ʈ�� ���۵� �� �ڵ����� ����ȴ�.
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
//		System.out.println("setJdbcTemplate() �޼ҵ� ����");
//		System.out.println(jdbcTemplate);
//		������� ����Ǹ� ��Ʈ�ѷ����� DBCP Template�� ����� �� �ִ�.
//		�����ͺ��̽��� ������ �ַ� DAO Ŭ�������� ����ϹǷ� ��Ʈ�ѷ� �̿��� Ŭ�������� DBCP Template�� ����� �� �ְ� �ϱ� ���ؼ� ������ �̸��� ��Ű���� ������ �̸����� Ŭ������ ����� ������ �������� servlet-context.xml���� ������ JdbcTemplate Ŭ������ bean�� �־��ش�.
		Constant.jdbcTemplate = jdbcTemplate;
		
	}
//	����������������1126_springMVCBoard_DBCP_template���� �߰�������������������������
	
	@RequestMapping("/")
	public String home(Locale locale, Model model) {
		System.out.println("HomeController�� home() �޼ҵ� ����");
		return "redirect:list";
	}
//	�� �Է���(insert.jsp)�� ȣ���ϴ� �޼ҵ�
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� insert() �޼ҵ� ����");
		return "insert";
	}
//	�Է� ���� �Էµ� �����͸� ���̺� �����ϰ� �������� ����� 1������ �з��� ���� ������ ��Ʈ�ѷ��� �޼ҵ带 ȣ���Ѵ�.
//		=> request�� �޾Ƽ� MvcboardVO Ŭ���� ��ü�� �����Ѵ�. 
	//text_MvcboardVO
	/*
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� insertOK() �޼ҵ� ���� - HttpServletRequest �������̽� ��ü ���");
		
//		insert.jsp���� �Ѿ���� �����͸� �޴´�.
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
//		MvcboardVO Ŭ������ bean�� ���´�.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		
//		MvcboardVO Ŭ������ bean�� insert.jsp���� request ��ü�� �Ѿ�� �����͸� �����Ѵ�.
		mvcboardVO.setName(name);
		mvcboardVO.setSubject(subject);
		mvcboardVO.setcontent(content);
//		System.out.println("vo: " + mvcboardVO);
		
//		���̺� ���α��� �����ϴ� �޼ҵ带 ȣ���Ѵ�.
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(mvcboardVO);
		return "redirect:list";
	}
	*/
//	�Է� ���� �Էµ� �����͸� ���̺� �����ϰ� �������� ����� 1������ �з��� ���� ������ ��Ʈ�ѷ��� �޼ҵ带 ȣ���Ѵ�.
//	=> Ŀ�ǵ� ��ü�� ����Ѵ�. 
	/*
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		System.out.println("HomeController�� insertOK() �޼ҵ� ���� - Ŀ�ǵ� ��ü ���");
//		System.out.println("vo: " + mvcboardVO);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(mvcboardVO);
		return "redirect:list";
	}
	*/
//	�Է� ���� �Էµ� �����͸� ���̺� �����ϰ� �������� ����� 1������ �з��� ���� ������ ��Ʈ�ѷ��� �޼ҵ带 ȣ���Ѵ�.
//	=> request�� �޾Ƽ� Model Ŭ���� ��ü�� �����Ѵ�. 
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� insertOK() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		
//		insert.jsp���� �Է��� �����Ͱ� ����� request ��ü�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(model);
		
//		���α��� ���������Ƿ� 1������ �з��� ���� ������ ��û�� �ϱ� ���� redirect ����� ����� @RequestMapping("/list")�� ���� ��Ʈ�ѷ� ������ �޼ҵ带 ȣ���Ѵ�.
		
		return "redirect:list";
	}
//	�������� ����� 1������ �з��� ���� ������ 1������ �з��� ���� �������� ����ϴ� �������� ȣ���ϴ� �޼ҵ�
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� list() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		
//		��Ʈ�ѷ����� list�� ��û�ϴ� ���������� �Ѿ���� �������� ǥ���� ������ ��ȣ�� ����� request��ü�� model ��ü�� �����Ѵ�.
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("select", SelectService.class);
		service.execute(model);
		
		return "list";
	}
//	���� Ŭ���ϸ� ��ȸ���� ������Ű�� �޼ҵ�
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� increment() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
		
//		��ȸ���� ������ų �۹�ȣ�� ����� request ��ü�� model ��ü�� �����Ѵ�.
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("increment", IncrementService.class);
		service.execute(model);
		
//		��ȸ���� ������Ų ��(�������� ǥ���� ��) ��ȣ�� �۾� �� ���ư� ������ ��ȣ�� Model �������̽� ��ü�� �����Ѵ�.
		int idx = Integer.parseInt(request.getParameter("idx"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		model.addAttribute("idx", idx);
		model.addAttribute("currentPage", currentPage);

		return "redirect:contentView";
	}
	
//	��ȸ���� ������Ų �� �Ѱ��� �������� ����ϱ� ���� ���̺��� ������ �޼ҵ�
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� contentView() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
//		��ȸ���� ������Ų �۹�ȣ�� �۾��� ���ư� ������ ��ȣ�� ����� request ��ü�� model ��ü�� �����Ѵ�.
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		
		return "contentView";
	}
//	�� 1���� �����ϴ� �޼ҵ�
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� delete() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
//		������ �۹�ȣ�� �۾��� ���ư� ������ ��ȣ�� ����� request ��ü�� model ��ü�� �����Ѵ�.
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("delete", DeleteService.class);
		service.execute(model);
		
		return "redirect:list";
	}
//	�� 1���� �����ϴ� �޼ҵ�
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� update() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
//		������ �۹�ȣ�� ������, ���� �� ���ư� ������ ��ȣ�� ����� request ��ü�� model ��ü�� �����Ѵ�.
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("update", UpdateService.class);
		service.execute(model);
		
		return "redirect:list";
	}
//	����� �Է��ϱ� ���� ������ ȭ�鿡 ����� ���α��� ������ ����� �Է��ϴ� �������� ȣ���ϴ� �޼ҵ�
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� reply() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
//		�亯�� �Է��� �������� �۹�ȣ �۾� �� ���ư� ������ ��ȣ�� ����� request ��ü�� model ��ü�� �����Ѵ�.
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//		�亯�ޱ� ��ư�� ������ �� ������ ������ 1���� �ҷ����� ���� contentView�� �ҷ��� �����Ѵ�.
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		
		return "reply";
	}
//	����� ��ġ�� �°� �����ϴ� �޼ҵ�	
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model) {
		System.out.println("HomeController�� replyInsert() �޼ҵ� ���� - Model Ŭ���� ��ü ���");
//		�亯�� ���� �۹�ȣ, �۱׷�(ref), �۷���(lev), ���� �� �׷�(ref)���� �� ��¼���(seq), ��� �ۼ��� �̸�, ��� ����, ��� ����, ����� �����ϰ� ���ư� ������ ��ȣ�� ����� request��ü�� model ��ü�� �����Ѵ�.
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardService service = ctx.getBean("reply", ReplyService.class);
		service.execute(model);
		
		return "redirect:list";
	}
	
}
