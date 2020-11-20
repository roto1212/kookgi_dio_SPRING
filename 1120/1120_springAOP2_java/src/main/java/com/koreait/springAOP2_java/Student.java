package com.koreait.springAOP2_java;

public class Student {
	
	private String name;
	private int age;
	private int gradeNum;
	private int classNum;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(String name, int age, int gradeNum, int classNum) {
		super();
		this.name = name;
		this.age = age;
		this.gradeNum = gradeNum;
		this.classNum = classNum;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", gradeNum=" + gradeNum + ", classNum=" + classNum + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(int gradeNum) {
		this.gradeNum = gradeNum;
	}

	public int getClassNum() {
		return classNum;
	}

	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	
//	student 클래스의 핵심기능
	public void getStudentInfo() {
		System.out.println("Student 클래스의 핵심기능");
		System.out.println("이름: " + name);
		System.out.println("나이: " + age);
		System.out.println("학년: " + gradeNum);
		System.out.println("반: " + classNum );
	}
	
	public void afternoon() {
		System.out.println("밥와그너졸");
		int i = 10 / 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
