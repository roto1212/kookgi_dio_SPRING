package com.koreait.springAOP2_java;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.ejb.criteria.expression.function.CurrentTimeFunction;
import org.hibernate.ejb.criteria.expression.function.CurrentTimestampFunction;
import org.springframework.context.annotation.Bean;

//	java파일을 이용해서 AOP 설정을 하려면 AOP를 설정할 클래스에 @Aspect 어노테이션을 붙여준다. 
@Aspect
public class LogAOP {
	
//	1. pointcut으로 사용할 메소드를 이용해 pointcut 지정하기
//	적당한  이름으로 비어있는 메소드를 만든 후 Pointcut 어노테이션을 붙여서 메소드의 인수로 pointcut을 지정한다.
//	pointcut 메소드를 만든 후 AOP 메소드에 지정하려면 AOP 어노테이션의 인수로 pointcut메소드를 적어준다.
//	AOP  어노테이션 => @Before @AfterReturning @AfterThrowing @After @Around
	 
	@Pointcut("within(com.koreait.springAOP2_java.S*)") //<aop:pointcut expression="within(*)" id="pointcutMethod"/>	
	public void pointcutMethod() {
//		비어있는 메소드	 => pointcut 1개를 만들어 여러 AOP 메소드에 pointcut을 지정할 때 사용한다.
	}
	@Before("pointcutMethod()")
	public void before() {
		System.out.println("LogAOP 클래스의 before() 메소드가 실행됨");
	}
	@AfterReturning("pointcutMethod()")
	public void afterreturning() {
		System.out.println("LogAOP 클래스의 afterreturning() 메소드가 실행됨");
	}
	@AfterThrowing("pointcutMethod()")
	public void afterthrowing() {
		System.out.println("LogAOP 클래스의 afterthrowing() 메소드가 실행됨");
	}
	@After("pointcutMethod()")
	public void after() {
		System.out.println("LogAOP 클래스의 after() 메소드가 실행됨");
	}
	
//	2. AOP 어노테이션의 인수로 pointcut을 바로 지정할 수 있다.
	@Around("execution(* com.koreait.springAOP2_java.W*.*())")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable   {
		System.out.println("LogAOP 클래스의 around() 메소드가 실행됨");
		long startTime = System.currentTimeMillis();
		
		System.out.println("around() 메소드가 실행됨  - 핵심기능 실행전 ");
		try {
			System.out.println("ProceedingJoinPoint 인터페이스 객체 joinPoint로 넘어온 핵심기능을 실행한다.");
			Object object = joinPoint.proceed();
			return object;
			
		} finally {
			long endTime = System.currentTimeMillis();
			System.out.println("around() 메소드가 실행됨  - 핵심기능 실행 후 ");
			System.out.println("핵심기능이 실행되는데 걸린 시간 : " + (endTime - startTime) / 1000. + "초");
		}
		
		
	}
}
