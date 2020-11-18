package com.koreait.springDI8_java_to_xml;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//	java 파일에서 xml파일의 bean 설정 정보를 읽어오려면 @ImportResource 어노테이션으로 읽어들일 xml 파일을 java파일에 포함시킨다. 
@ImportResource("classpath:applicationCTX.xml")
//	xml 파일에서는 아래와 같이 구현한다.
//	<beans xmlns:context="http://www.springframework.org/schema/context">
//	<context:annotation-config/>
//	<bean class="com.koreait.springDI7_xml_to_java.ApplicationConfig"/>

@Configuration				
public class ApplicationConfig {
	@Bean
	public Student student() {
				
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("java");
		hobbies.add("jsp");
		hobbies.add("spring");
		
		Student student = new Student("임꺽정", 20, hobbies);
		
		student.setHeight(165);
		student.setWeight(110);
		
		return student;
	}
	
	
}
