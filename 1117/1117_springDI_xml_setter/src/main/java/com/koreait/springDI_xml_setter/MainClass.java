package com.koreait.springDI_xml_setter;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {
	public static void main(String[] args) {
		
		/*
		MyCalcurator mycalc = new MyCalcurator();
		mycalc.setFirstNum(8);
		mycalc.setSecondNum(5);
		
//		Calcurator calcu = new Calcurator();
//		mycalc.setCalcu(calcu);
		mycalc.setCalcu(new calcu());
		*/
		
//		xml 파일에서 설정한 bean 설정정보를 읽어들인다.
//		class의 bean을 설정하는 xml파일 만들기 => src/main/resources에서 우클릭 - new - Spring Bean Configuration File - 원하는 이름으로 xml파일 생성
//		classpath:파일명.xml : xml 파일 위치 경로 src/main/resources/applicationCTX.xml를 의미한다.
		String configLocation = "classpath:applicationCTX.xml";
		
//		GenericXmlApplicationContext 클래스 생성자의 인수로 bean을 설정하는 xml파일의 경로와 이름을 넘겨주면 xml 파일의 내용을 읽어서(parsing) java 객체로 변환시킨 후 부모클래스인 AbstractApplicationContext 클래스의 객체에 저장한다.
//		xml 파일의 bean 설정 정보를 읽어서 ctx에 저장한다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
//		getBean() 메소드로 AbstractApplicationContext 클래스 타입의 객체 ctx에 저장된 bean을 얻어온다.
//		getBean("얻어올 bean의 id", bean을 생성한 클래스 이름.class)
		MyCalcurator mycalc = ctx.getBean("mycalc", MyCalcurator.class);		// == MyCalcurator mycalc = new MyCalcurator();을 spring으로 만든것
		System.out.println(mycalc);
		
		
		mycalc.add(); 
		mycalc.sub();
		mycalc.mul();
		mycalc.div();
	}
}
