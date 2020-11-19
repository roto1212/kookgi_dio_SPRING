package com.koreait.springProperties3_java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		AdminConnVO adminConnVO = ctx.getBean("adminConnVO", AdminConnVO.class);
		
		System.out.println("admin.id: " + adminConnVO.getAdminId());
		System.out.println("admin.pw: " + adminConnVO.getAdminPw());
		System.out.println("sub_admin.pw: " + adminConnVO.getSub_adminId());
		System.out.println("sub_admin.pw: " + adminConnVO.getSub_adminPw());
	}

}
