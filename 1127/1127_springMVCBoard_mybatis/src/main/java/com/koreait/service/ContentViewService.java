package com.koreait.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.koreait.dao.MvcboardDAO;
import com.koreait.vo.MvcboardVO;

public class ContentViewService implements MvcboardService {

	@Override
	public void execute(Model model) {
		System.out.println("ContentViewService�� execute() �޼ҵ� ����");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
//		request ��ü�� �Ѿ�� ��ȸ���� ������Ų �۹�ȣ�� �޴´�.
		int idx = Integer.parseInt(request.getParameter("idx"));
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
//		��ȸ���� ������Ų �� 1���� ���ͼ� MvcboardVO Ŭ���� ��ü�� �����Ѵ�.
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		mvcboardVO = mvcboardDAO.selectByIdx(idx);

//		System.out.println(mvcboardVO);
		
//		�������� ����� ��, �۾� �� ���ư� ������ ��ȣ, �ٹٲ� ������ ����� \r\n�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		model.addAttribute("enter", "\r\n");
	}

}
