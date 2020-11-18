package com.koreait.springDI4_xml_interface;

public class PencilHBEraser implements Pencil{

	@Override
	public void use() {
		System.out.println("지우개 달린 HB연필로 그림을 그립니다.");
		
	}

}
