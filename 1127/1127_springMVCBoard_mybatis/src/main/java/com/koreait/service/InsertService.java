package com.koreait.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.koreait.dao.MvcboardDAO;
import com.koreait.vo.MvcboardVO;

public class InsertService implements MvcboardService {
	
	//text_MvcboardVO
	/*
	@Override
	public void execute(MvcboardVO mvcboardVO) {
		System.out.println("InsertService�� execute() �޼ҵ� ���� - VO ���");
//		System.out.println("vo: " + mvcboardVO);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
//		���α��� ���̺� �����ϴ� �޼ҵ带 �����Ѵ�.
		mvcboardDAO.insert(mvcboardVO);
	}
	*/
	@Override
	public void execute(Model model) {
		System.out.println("InsertService�� execute() �޼ҵ� ���� - VO ���");
		
//		��Ʈ�ѷ����� Model�������̽� ��ü�� ������ �Ѱ��� HttpServletRequest  �������̽� ��ü���� insert.jsp���� �Է��� �����͸� �о��.
//		Model �������̽� ��ü�� key�� value�� ������ ������ ������ �����Ƿ� asMap()�޼ҵ�� Map<String, Object> Ÿ������ ��ȯ���� �����Ѵ�.
		Map<String, Object> map = model.asMap();
		
//		Model �������̽� ��ü�� Map<String, Object> Ÿ������ ��ȯ�Ǽ� ����� ��ü���� key�� "request"�� value(insert.jsp���� �Ѿ�� ������)�� ���´�.
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
//		Model �������̽� ��ü���� ����Ǽ� �Ѿ�� HttpServletRequest �������̽� ��ü���� insert.jsp���� �Ѿ�� �����͸� �޴´�.
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

//		���α��� ���̺� �����ϴ� �޼ҵ带 �����Ѵ�.
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
		mvcboardDAO.insert(mvcboardVO);


	}
	
}
