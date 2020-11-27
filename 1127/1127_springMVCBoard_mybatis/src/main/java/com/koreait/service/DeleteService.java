package com.koreait.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.koreait.dao.MvcboardDAO;

public class DeleteService implements MvcboardService {

	@Override
	public void execute(Model model) {
		System.out.println("DeleteService�� execute() �޼ҵ� ����");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

//		request ��ü�� �Ѿ�� ������ ���� �۹�ȣ�� �޴´�.
		int idx = Integer.parseInt(request.getParameter("idx"));
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
		mvcboardDAO.delete(idx);
		
//		�� ���� �۾� �� ���ư� ������ ��ȣ�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));

	}

}
