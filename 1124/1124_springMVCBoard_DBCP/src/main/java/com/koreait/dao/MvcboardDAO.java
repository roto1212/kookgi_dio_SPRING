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

public class MvcboardDAO {
	DataSource dataSource;	//Connection

	public MvcboardDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			System.out.println("���Ἲ��!!!");
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("�������!!!");
		}
	}
//	InsertService Ŭ�������� ȣ��Ǵ� ���̺� ������ ���α� �����Ͱ� ����� ��ü�� �Ѱܹް� insert sql ����� �����ϴ� �޼ҵ�
	public void insert(MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO�� insert() �޼ҵ� ����");
//		System.out.println(mvcboardVO);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "insert into mvcboard (idx, name, subject, content, ref, lev, seq) "
					+ "values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcboardVO.getName());
			pstmt.setString(2, mvcboardVO.getSubject());
			pstmt.setString(3, mvcboardVO.getcontent());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
	}
//	SelectService Ŭ�������� ȣ��, ���̺� ����� ��ü���� ������ ������ select sql ����� �����ϴ� �޼ҵ�
	public int selectCount() {
		System.out.println("MvcboardDAO�� selectCount() �޼ҵ� ����");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;					// ���̺� ����� ��ü ���� ������ ���� ���� ������ ������ �����Ѵ�.
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) from mvcboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
//			���̺� ����� ��ü ���� ������ null �� ���ø� �����Ƿ� ���� �񱳸� �� �ʿ�� ����.
			rs.next();
			result = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return result;
	}
}
