package com.koreait.springMVController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//	��Ʈ�ѷ� Ŭ������ @RequestMapping�� �ٿ��ָ� ��Ʈ�ѷ��� �ٿ��� @RequestMapping�� ��û�� ���� @RequestMapping�� ��û�� ���ļ� ��û�ؾ� ���������� ����� ã�ư� �� �ִ�.
//	/model/model12 �� ��û�ؾ� ���������� ã�ư� �� �ִ�.
@RequestMapping("/model")
public class NewCtrler {
	@RequestMapping("/model12")
	public String model12(Model model) {
		System.out.println("NewCtrler�� model12 �޼ҵ� ����");
		model.addAttribute("id", "dior");
		model.addAttribute("pw", "987456");
		model.addAttribute("nm", "����");
		
		return "model/model12";
	}
	
}
