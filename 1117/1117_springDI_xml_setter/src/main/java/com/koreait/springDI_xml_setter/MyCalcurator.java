package com.koreait.springDI_xml_setter;

//	사칙연산에 사용할 멤버변수를 선언한고 Calcurator 클래스의 사칙연산 메소드를 호출하는 메소드
public class MyCalcurator {	
	private int firstNum;
	private int secondNum;
	private Calcurator calcu;
	
	
	@Override
	public String toString() {
		return "MyCalcurator [firstNum=" + firstNum + ", secondNum=" + secondNum + ", calcu=" + calcu + "]";
	}
	
	public int getFirstNum() {
		return firstNum;
	}
	public void setFirstNum(int firstNum) {
		this.firstNum = firstNum;
	}
	public int getSecondNum() {
		return secondNum;
	}
	public void setSecondNum(int secondNum) {
		this.secondNum = secondNum;
	}
	public Calcurator getCalcu() {
		return calcu;
	}
	public void setCalcu(Calcurator calcu) {
		this.calcu = calcu;
	}
//	Calcurator 클래스 add(), sub(), mul(), div() 메소드를 실행하는 메소드
	public void add() {
		calcu.add(firstNum, secondNum);
	}
	public void sub() {
		calcu.sub(firstNum, secondNum);
	}
	public void mul() {
		calcu.mul(firstNum, secondNum);
	}
	public void div() {
		calcu.div(firstNum, secondNum);
	}
}
