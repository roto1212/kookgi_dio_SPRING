package com.koreait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.koreait.vo.MvcboardVO;

public class MvcboardDAO2 {
	DataSource dataSource;	//spring의 DriverManager(Connection할 때 사용)

	public MvcboardDAO2() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			System.out.println("연결성공!!!");
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("연결실패!!!");
		}
	}
	
	//서비스 클래스에서 호출해 sql 명령어를 실행하는 메소드
	//public 리턴타입 메소드명(매개변수) {
	//	Connection conn = null;			// 데이터베이스와 연결된 세션의 객체
	//  PreparedStatement pstmt = null; // 이미 컴파일된 SQL문을 다루는 인터페이스의 객체
	//	ResultSet rs = null;			// sql실행결과를 저장하는 인터페이스
	//	try {
	//	conn = dataSource.getConnection(); // db와 연결
	//	String sql = "실행하고 싶은 sql문 ?,?..." 
	//	pstmt = conn.preparedStatement(sql) // 매개변수('?')에 값을 넣기 위한 sql 임시실행
	//	pstmt.setString(1, 입력변수); 		// ?순서에 맞춰 입력하고 싶은 값을 지정한다.
	//	...
	//	pstmt.executeQuery(); 				// 테이블에 변경이 없는 경우 실행(select)
	//	pstmt.executeUpdate(); 				// 테이블에 변경이 있는 경우 실행
	//  rs = pstmt.executeQuery();			// sql결과값 저장
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	} fianlly {
	//		if(conn != null) { 			// sql 실행결과가 나오면 연결 종료
	//			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
	//		}
	//	}	
	//	[return result;]
	//}
	
}
