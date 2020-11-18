package com.koreait.springDI5_xml_namespace;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		String configLoaction = "classpath:applicationCTX.xml";
		String configLoaction2 = "classpath:applicationCTX2.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLoaction, configLoaction2);
		
		Student student = ctx.getBean("student", Student.class);
		
		System.out.println(student); 
		System.out.println("이름: " + student.getName()); 
		System.out.println("취미: " + student.getHobbies()); 
		
		System.out.println("*******************"); 
//		student.studentGetinfo();
		System.out.println("============================"); 
		
		Student student2 = ctx.getBean("student", Student.class);
		
		System.out.println(student2); 
		System.out.println("이름: " + student2.getName()); 
		System.out.println("취미: " + student2.getHobbies()); 
		
		System.out.println("*******************"); 
//		student2.studentGetinfo();
		System.out.println("============================"); 
		
		if (student.equals(student2)) {
			System.out.println("같다.");
		} else {
			
			System.out.println("다르다.");
		}

		System.out.println("============================"); 
		Student student3 = ctx.getBean("student2", Student.class);
		
		System.out.println(student3); 
		System.out.println("이름: " + student3.getName()); 
		System.out.println("취미: " + student3.getHobbies()); 
		System.out.println("*******************"); 
//		student3.studentGetinfo();
		System.out.println("============================"); 
		
		if (student.equals(student3)) {
			System.out.println("같다.");
		} else {
			
			System.out.println("다르다.");
		}
		
		System.out.println("============================"); 
		Student student4 = ctx.getBean("student3", Student.class);
		
		System.out.println(student4); 
		System.out.println("이름: " + student4.getName()); 
		System.out.println("취미: " + student4.getHobbies()); 
		System.out.println("*******************"); 
//		student4.studentGetinfo();
		System.out.println("============================"); 
		
		if (student.equals(student4)) {
			System.out.println("같다.");
		} else {
			
			System.out.println("다르다.");
		}
		
		System.out.println("============================"); 
		StudentInfo studentInfo = ctx.getBean("studentInfo", StudentInfo.class);
		
		studentInfo.getStudentInfo(); 
		System.out.println("============================");
		
		Student student5 = ctx.getBean("student4", Student.class);
		System.out.println(student5); 
		System.out.println("이름: " + student5.getName()); 
		System.out.println("취미: " + student5.getHobbies()); 
		System.out.println("============================"); 
		
		Family family = ctx.getBean("family", Family.class);
		System.out.println(family);
		System.out.println("papa: " + family.getPapaName());
		System.out.println("mama: " + family.getMamiName());
		System.out.println("sister: " + family.getSisterName());
		System.out.println("bro: " + family.getBrotherName());
		System.out.println("============================"); 
	}
}
