package com.koreait.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.koreait.dao.MvcboardDAO;
import com.koreait.vo.MvcboardList;

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
//		System.out.println("��ü ���� ����: " + totalCount);
		
//		1������ �з��� �۰� ������ �۾��� ����� 8���� ������ ����ϴ� MvcboardListŬ������ bean�� ���´�.
		MvcboardList mvcboardList = ctx.getBean("mvcboardList", MvcboardList.class);		//�⺻ �����ڷ� ������ bean

//		JSP�� �۾��� ��ó�� �����ڸ� ����ϴ� �ʱ�ȭ�� �Ұ����ϴ�. �׷���  MvcboardListŬ������ bean�� ���� �� 8���� ������ �ʱ�ȭ�ϴ� �޼ҵ带 �����Ѵ�.
		mvcboardList.initMvcboardList(pageSize, totalCount, currentPage);
//		System.out.println(mvcboardList);
		
//		MvcboardList Ŭ������ 1������ �з��� ���� ����ϴ� �迭��Ͽ� 1������ �з��� ���� ���̺��� ���ͼ� �־��ش�.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", mvcboardList.getStartNo());
		hmap.put("endNo", mvcboardList.getEndNo());
		
		mvcboardList.setList(mvcboardDAO.selectList(hmap));
//		System.out.println(mvcboardList);
		
//		list.jsp�� �Ѱ��� �����͸� Model �������̽� ��ü�� �־��ش�.
		model.addAttribute("mvcboardList", mvcboardList);
		
	}

}
