package com.koreait.springDI7_xml_to_java;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration				
public class ApplicationConfig {
	@Bean
	public Student student2() {
		/*
		Student student = new Student();
		student.setName("홍길동");
		student.setAge(20);;
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("등산");
		hobbies.add("바둑");
		hobbies.add("낚시");
		student.setHobbies(hobbies);
		*/
		
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("java");
		hobbies.add("jsp");
		hobbies.add("spring");
		
		Student student = new Student("임꺽정", 20, hobbies);
		
		student.setHobbies(hobbies);
		student.setHeight(165);
		student.setWeight(110);
		
		return student;
	}
	
	
}
