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
	DataSource dataSource;	//spring�� DriverManager(Connection�� �� ���)

	public MvcboardDAO2() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			System.out.println("���Ἲ��!!!");
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("�������!!!");
		}
	}
	
	//���� Ŭ�������� ȣ���� sql ��ɾ �����ϴ� �޼ҵ�
	//public ����Ÿ�� �޼ҵ��(�Ű�����) {
	//	Connection conn = null;			// �����ͺ��̽��� ����� ������ ��ü
	//  PreparedStatement pstmt = null; // �̹� �����ϵ� SQL���� �ٷ�� �������̽��� ��ü
	//	ResultSet rs = null;			// sql�������� �����ϴ� �������̽�
	//	try {
	//	conn = dataSource.getConnection(); // db�� ����
	//	String sql = "�����ϰ� ���� sql�� ?,?..." 
	//	pstmt = conn.preparedStatement(sql) // �Ű�����('?')�� ���� �ֱ� ���� sql �ӽý���
	//	pstmt.setString(1, �Էº���); 		// ?������ ���� �Է��ϰ� ���� ���� �����Ѵ�.
	//	...
	//	pstmt.executeQuery(); 				// ���̺� ������ ���� ��� ����(select)
	//	pstmt.executeUpdate(); 				// ���̺� ������ �ִ� ��� ����
	//  rs = pstmt.executeQuery();			// sql����� ����
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	} fianlly {
	//		if(conn != null) { 			// sql �������� ������ ���� ����
	//			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
	//		}
	//	}	
	//	[return result;]
	//}
	
}
