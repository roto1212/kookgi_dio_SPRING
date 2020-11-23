package com.koreait.springMVController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//	컨트롤러 클래스에 @RequestMapping을 붙여주면 컨트롤러에 붙여준 @RequestMapping과 요청에 붙은 @RequestMapping의 여청을 합쳐서 요청해야 뷰페이지를 제대로 찾아갈 수 있다.
//	/model/model12 로 요청해야 뷰페이지를 찾아갈 수 있다.
@RequestMapping("/model")
public class NewCtrler {
	@RequestMapping("/model12")
	public String model12(Model model) {
		System.out.println("NewCtrler의 model12 메소드 실행");
		model.addAttribute("id", "dior");
		model.addAttribute("pw", "987456");
		model.addAttribute("nm", "장길산");
		
		return "model/model12";
	}
	
}
