package com.koreait.springAOP1_xml;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.ejb.criteria.expression.function.CurrentTimeFunction;
import org.hibernate.ejb.criteria.expression.function.CurrentTimestampFunction;
import org.springframework.context.annotation.Bean;



//	공통기능 메소드를 모아놓은 클래스
public class LogAOP {
	
//	before 
	public void before() {
		System.out.println("LogAOP 클래스의 before() 메소드가 실행됨");
	}
//	after-returning
	public void afterreturning() {
		System.out.println("LogAOP 클래스의 afterreturning() 메소드가 실행됨");
	}
//	after-throwing
	public void afterthrowing() {
		System.out.println("LogAOP 클래스의 afterthrowing() 메소드가 실행됨");
	}
//	after
	public void after() {
		System.out.println("LogAOP 클래스의 after() 메소드가 실행됨");
	}
	
//	around (* pom.xml의 <!-- AOP 드라이버 설정 -->에서 scope가 있다면 지워줘야 ProceedingJoinPoint 클래스를 사용할 수 있다.)
//	1. around AOP 메소드는 핵심기능이 실행되고 난 후 리턴되는 데이터 타입을 예측할 수 없으므로 반드시 리턴타입을 모든 데이터타입을 포함하는 자바의 최상위 클래스인 Object로 지정해야 한다.
//	2. around AOP 메소드의 매개변수로 실행할 핵심기능(메소드)이 넘어온다. => 반드시 매개변수로  ProceedingJoinPoint 인터페이스 타입의 객체를 사용한다.
//	3. around AOP 메소드는 try ~ finally 형태로 실행되며 catch 블록은 throws Throwable로 대체 한다.
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable   {
		System.out.println("LogAOP 클래스의 around() 메소드가 실행됨");
		long startTime = System.currentTimeMillis();
		
//		핵심기능이 실행되기 전 실행할 내용을 코딩한다.
		System.out.println("around() 메소드가 실행됨  - 핵심기능 실행전 ");
		try {
//			try 블록에서 핵심기능을 실행한다.
			System.out.println("ProceedingJoinPoint 인터페이스 객체 joinPoint로 넘어온 핵심기능을 실행한다.");
//			ProceedingJoinPoint 인터페이스 객체로 넘어온 핵심기능을 실행한다.
			Object object = joinPoint.proceed();
//			핵심기능을 실행한 결과를 리턴한다.
			return object;
			
		} finally {
			long endTime = System.currentTimeMillis();
//			핵샘기능이 실행되고 난 후 내용을 코딩한다.
			System.out.println("around() 메소드가 실행됨  - 핵심기능 실행 후 ");
			System.out.println("핵심기능이 실행되는데 걸린 시간 : " + (endTime - startTime) / 1000. + "초");
		}
		
		
	}
}
