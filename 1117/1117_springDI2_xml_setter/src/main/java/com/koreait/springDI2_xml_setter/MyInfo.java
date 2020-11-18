package com.koreait.springDI2_xml_setter;

import java.util.ArrayList;

public class MyInfo {
	private String name;
	private double height;
	private double weight;
	private ArrayList<String> hobbies;
	private BMICalcu bmiCalcu; 
	
	@Override
	public String toString() {
		return "MyInfo [name=" + name + ", height=" + height + ", weight=" + weight + ", hobbies=" + hobbies
				+ ", bmiCalcu=" + bmiCalcu + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public ArrayList<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(ArrayList<String> hobbies) {
		this.hobbies = hobbies;
	}
	public BMICalcu getBmiCalcu() {
		return bmiCalcu;
	}
	public void setBmiCalcu(BMICalcu bmiCalcu) {
		this.bmiCalcu = bmiCalcu;
		
//	개인정보를 출력하는 메소드
	}
	public void getMyInfo() {
		System.out.println("이름: " + name);
		System.out.println("신장: " + height);
		System.out.println("체중: " + weight);
		System.out.println("취미: " + hobbies);
//	BMI 지수를 계산해 출력하는 메소드를 실행한다.
		bmiCalcu.bmiCalcu(height, weight);
	}
	

	
	
	
	
}	
