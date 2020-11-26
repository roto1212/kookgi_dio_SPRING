package com.koreait.service;

import java.awt.image.VolatileImage;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.koreait.dao.MvcboardDAO;
import com.koreait.vo.MvcboardVO;

public class ReplyService implements MvcboardService {

	@Override
	public void execute(Model model) {
		System.out.println("ReplyService�� execute() �޼ҵ� ����");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
//		request ��ü���� �Ѿ�� �亯�� ���� �۹�ȣ, �۱׷�(ref), �۷���(lev), ���� �� �׷�(ref)���� �� ��¼���(seq), ��� �ۼ��� �̸�, ��� ����, ��� ����, ����� �����ϰ� ���ư� ������ ��ȣ�� �޴´�.
		int idx = Integer.parseInt(request.getParameter("idx"));
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		int ref = Integer.parseInt(request.getParameter("ref"));
		int lev = Integer.parseInt(request.getParameter("lev"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
//		��� �����͸�  MvcboardVO Ŭ������ �����Ѵ�.
		MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
//		����� ������ �ٷ� �Ʒ��� ��ġ�ؾ� �ϹǷ� lev�� seq�� 1�� �������� �����Ѵ�.
		mvcboardVO.setIdx(idx);
		mvcboardVO.setName(name);
		mvcboardVO.setSubject(subject);
		mvcboardVO.setcontent(content);
		mvcboardVO.setRef(ref);
		mvcboardVO.setLev(lev + 1);
		mvcboardVO.setSeq(seq + 1);
		
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
//		����� ���Ե� ��ġ�� ���ϱ� ���� ���ǿ� �����ϴ� seq�� 1�� ������ũ�� �޼ҵ带 �����Ѵ�.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("ref", mvcboardVO.getRef());
		hmap.put("seq", mvcboardVO.getSeq());
		
//		�̰� ���ϳ�?
		mvcboardDAO.replyIncrement(hmap);
		
//		����� �����ϴ� �޼ҵ带 �����Ѵ�.
		mvcboardDAO.replyInsert(mvcboardVO);
		
//		��� �Է� �۾� �� ���ư� ������ ��ȣ�� Model �������̽� ��ü�� �����Ѵ�.
		model.addAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		
		

	}

}
