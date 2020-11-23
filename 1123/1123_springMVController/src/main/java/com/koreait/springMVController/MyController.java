package com.koreait.springMVController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//	Ŭ���̾�Ʈ�κ��� ���� ��û�� ������ �� ��Ʈ�ѷ��� �����ϰ� �ǰ� ��Ʈ�ѷ��� ��û�� ���� �۾��� ������ �� �� �������� �����͸� �����Ѵ�.
//	��Ʈ�ѷ� ���� ����
//	do {
//	1. base-package�� ������  ��Ű���� ������ �̸����� Ŭ������ ����� @Controller�� �ٿ��� ��Ʈ�ѷ��� ����� Ŭ�������� ���������� �˸���.
//	} while (Ŭ���� �����) {
//	2. @RequestMapping(�μ�)�� �μ��� Ŭ���̾�Ʈ���� �Ѿ�� ó�� ���� ��û�� �����Ѵ�.
//	3. ��û�� ó���� ������ �̸��� �޼ҵ带 �����. => �� ������ �̸��� ���ڿ��̹Ƿ� �޼ҵ��� ����Ÿ���� String���� �����Ѵ�.
//	4. �ۼ��� �޼ҵ忡�� �ʿ��� �۾��� �Ϸ��� �� �� ������ jsp ������ �̸��� ���Ͻ�Ų��.
//	}
@Controller
public class MyController {
	
	@RequestMapping("/view")		// Ŭ���̾�Ʈ�κ��� �Ѿ���� ���񽺿�û
	public String view() {
		System.out.println("MyController�� view() �޼ҵ� ����");
//		prefix + view() return + suffix == /WEB-INF/views/view.jsp
		return "view";		// view ������ �̸�
	}
	
	@RequestMapping("/model")
//	@RequestMapping�� ������ �޼ҵ�� Model �������̽� ��ü�� �μ��� ���� �� �ִ�.
//	Model �������̽� ��ü�� ��Ʈ�ѷ����� ���������� �Ѱ��� �����͸� �����Ѵ�. => jsp�� response�� ���� ������ �Ѵ�.
	public String model(Model model) {
		
		System.out.println("MyController�� model() �޼ҵ� ����");
//		.addAttribute(key, value): Model�������̽� ��ü�� key�� ���� value�� �־��ش�.
		model.addAttribute("id", "dio");
		model.addAttribute("pw", "12345");
		model.addAttribute("nm", "ȫ�浿");
		
//		servlet-context.xml�� prefix + Controller class�� model()�� return + servlet-context.xml�� suffix == /WEB-INF/views/model//model.jsp
		return "model/model"; 
		
		
	}
	
	@RequestMapping("/modelAndView")
//	ModelAndView Ŭ���� ��ü�� ��Ʈ�ѷ����� �� ������ �̸��� ���������� �Ѱ��� �����͸� �����Ѵ�.
//	�������� �̸��� ���������� �Ѱ��� �����͸� ModelAndView Ŭ���� ��ü�� �����ؼ� ���Ͻ��Ѿ��ϹǷ� ���� Ÿ���� ModelAndView�� ����Ѵ�.
	public ModelAndView modelAndView(Model model) {
		System.out.println("MyController�� modelAndView() �޼ҵ� ����");
//		���������� �Ѱ��� �����Ϳ� �������� �̸��� ������ ModelAndView Ŭ���� ��ü�� �����Ѵ�.
		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject(key, value): ModelAndViewŬ���� ��ü�� key�� ���� value�� �־��ش�. => �� �������� �Ѱ��� �����͸� �����Ѵ�.
		modelAndView.addObject("id", "diosun");
		modelAndView.addObject("pw", "67890");
		modelAndView.addObject("nm", "�Ӳ���");
//		setViewName(): ModelAndView Ŭ���� ��ü�� �� ������ �̸��� �־��ش�.
		modelAndView.setViewName("modelAndView/modelAndView");
//		�� �������� �Ѱ��� �����Ϳ� �� ������ �̸��� ����� modelAndView Ŭ���� ��ü�� �����Ѵ�.
		return modelAndView;
	}
	
	
}
