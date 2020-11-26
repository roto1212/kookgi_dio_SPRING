package com.koreait.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.koreait.dao.MvcboardDAO;
import com.koreait.vo.MvcboardVO;

public class UpdateService implements MvcboardService {

	@Override
	public void execute(Model model) {
		System.out.println("UpdateService�� execute() �޼ҵ� ����");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

//		request ��ü�� �Ѿ�� ������ ���� �۹�ȣ�� �����͸� �޴´�.
		int idx = Integer.parseInt(request.getParameter("idx"));
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
		mvcboardDAO.update(idx, subject, content);
		
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		mvcboardVO.setIdx(idx);
		mvcboardVO.setSubject(subject);
		mvcboardVO.setcontent(content);
		mvcboardDAO.update(mvcboardVO);
		
//		�� ���� �۾� �� ���ư� ������ ��ȣ�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));


	}

}
