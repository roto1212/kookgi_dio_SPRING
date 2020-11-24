package com.koreait.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.koreait.dao.MvcboardDAO;

public class SelectService implements MvcboardService{

	@Override
	public void execute(Model model) {
		System.out.println("SelectService�� execute() �޼ҵ� ����");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
//		DAO Ŭ������ bean�� ���´�.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
//		������ ȭ�鿡 ����� ���� ������ ���Ѵ�.
		int pageSize = 10;
//		HttpServletRequest �������̽� ��ü�� ����Ǽ� �Ѿ���� ȭ�鿡 ǥ���� ������ ��ȣ�� �޴´�.
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {}
		
//		���̺� ����� ��ü ���� ������ ���´�.
		int totalCount = mvcboardDAO.selectCount();
		System.out.println("��ü ���� ����: " + totalCount);
		
		
	}

}
