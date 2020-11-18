package com.koreait.springDI2_xml_setter;

public class BMICalcu {
//	키와 몸무게를 인수로 넘겨받아 BMI지수를 계산하고 등급을 출력하는 메소드
//	BMI 지수 = 몸무게 kg / (키 m * 키 m)
//	산출된 값이 18.5미만이면 저체중, 18.5이상 ~ 23 미만이면 정상, 23이상 ~ 30 미만이면 비만, 30 이상은 고도비만으로 분류한다.
	public void bmiCalcu(double height, double weight) {
		
		double bmi = weight / Math.pow(height/100, 2);
		if (bmi < 18.5) {
			System.out.println("저체중 입니다.");
		} else if(bmi < 23) {
			System.out.println("정상 입니다.");
		} else if(bmi < 30) {
			System.out.println("비만 입니다.");
		} else {
			System.out.println("고도비만 입니다.");
		}
	}
}
