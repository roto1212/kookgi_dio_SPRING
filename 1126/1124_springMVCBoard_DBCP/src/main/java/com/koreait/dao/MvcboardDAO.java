package com.koreait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

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
			
//			���̺� ����� ��ü ���� ������ null �� ���ø� �����Ƿ�(0�̻�) ���� �񱳸� �� �ʿ�� ����.
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
//	SelectService Ŭ�������� ȣ��, ������ ȭ�鿡 ǥ���� 1������ �з��� ���� ���� �ε����� �� �ε����� ����� HashMap ��ü�� �Ѱܹް� ���̺��� 1������ �з��� ���� ������ select sql ����� �����ϴ� �޼ҵ�
	public ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap) {
		System.out.println("MvcboardDAO�� selectList() �޼ҵ� ����");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MvcboardVO> list = null;		// 1������ �з��� ���� ������ ���Ͻ�ų �迭����� �����Ѵ�.
		try {
			conn = dataSource.getConnection();
			String sql = "select * from ("
					   + "	select rownum rnum, AA.* from ("
					   + "		select * from mvcboard order by ref desc, seq asc"
					   + "	) AA where rownum <= ?"
					   + ") where rnum >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hmap.get("endNo"));
			pstmt.setInt(2, hmap.get("startNo"));
			rs = pstmt.executeQuery();
//			ResultSet ��ü��  ����� 1������ �з��� ������ ���Ͻ�ų �迭��� ��ü�� �����Ѵ�.
			list = new ArrayList<MvcboardVO>();
//			ResultSet ��ü�� ����� 1������ �з��� ���� ���Ͻ�Ű�� ���� �迭��Ͽ� ���� ���� ���� ��ŭ �ݺ��ϸ� �����Ų��.
			while (rs.next()) {		// ����� ����Ʈ �� ������ ������ true, ������ false �����Ѵ�.
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
				MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
				mvcboardVO.setIdx(rs.getInt("idx"));
				mvcboardVO.setName(rs.getString("name"));
				mvcboardVO.setSubject(rs.getString("subject"));
				mvcboardVO.setcontent(rs.getString("content"));
				mvcboardVO.setRef(rs.getInt("ref"));
				mvcboardVO.setLev(rs.getInt("lev"));
				mvcboardVO.setSeq(rs.getInt("seq"));
				mvcboardVO.setHit(rs.getInt("hit"));
				mvcboardVO.setWriteDate(rs.getTimestamp("writeDate"));
				list.add(mvcboardVO);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return list;
	}
//	IncrementService Ŭ�������� ��ȸ���� ������ų �۹�ȣ�� �Ѱܹް� ��ȸ���� ������Ű�� update sql ����� �����ϴ� �޼ҵ�
	public void increment(int idx) {
		System.out.println("MvcboardDAO�� increment() �޼ҵ� ����");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set hit = hit + 1 where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
	}
//	ContentViewService Ŭ�������� ȣ��, ��ȸ���� ������Ų �۹�ȣ�� �Ѱܹް� ��ȸ���� ������Ų �� 1���� ������ select sql ����� �����ϴ� �޼ҵ�
	public MvcboardVO selectByIdx(int idx) {
		System.out.println("MvcboardDAO�� selectByIdx() �޼ҵ� ����");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MvcboardVO mvcboardVO = null;		// �� 1���� ���� ������ �� ���Ͻ�ų ��ü�� �����Ѵ�.
		try {
			conn = dataSource.getConnection();
			String sql = "select * from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
//			ResultSet ��ü�� ����� �� 1���� ������ ���Ͻ�Ű�� ���� MvcboardVO Ŭ���� ��ü�� �����Ѵ�.
			if (rs.next()) {		// ����� ����Ʈ �� ������ ������ true, ������ false �����Ѵ�.
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
				mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
				mvcboardVO.setIdx(rs.getInt("idx"));
				mvcboardVO.setName(rs.getString("name"));
				mvcboardVO.setSubject(rs.getString("subject"));
				mvcboardVO.setcontent(rs.getString("content"));
				mvcboardVO.setRef(rs.getInt("ref"));
				mvcboardVO.setLev(rs.getInt("lev"));
				mvcboardVO.setSeq(rs.getInt("seq"));
				mvcboardVO.setHit(rs.getInt("hit"));
				mvcboardVO.setWriteDate(rs.getTimestamp("writeDate"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return mvcboardVO;
	}
//	DeleteService Ŭ�������� ȣ��Ǵ� ������ �۹�ȣ�� �Ѱܹް� �� 1���� �����ϴ� delete sql ����� �����ϴ� �޼ҵ� 	
	public void delete(int idx) {
		System.out.println("MvcboardDAO�� delete() �޼ҵ� ����");
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "delete from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
	}
//	UpdateService Ŭ�������� ȣ��Ǵ� ������ �۹�ȣ�� �����͸� �Ѱܹް� �� 1���� �����ϴ� update sql ����� �����ϴ� �޼ҵ� - 1
	public void update(int idx, String subject, String content) {
		System.out.println("MvcboardDAO�� update() �޼ҵ� ����");
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard "
					   + "set subject = ?, content = ? "
				       + "where idx = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setInt(3, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
	}
//	UpdateService Ŭ�������� ȣ��Ǵ� ������ �۹�ȣ�� �����͸� �Ѱܹް� �� 1���� �����ϴ� update sql ����� �����ϴ� �޼ҵ� - 2
	public void update(MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO�� update() �޼ҵ� ����");
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard "
					   + "set subject = ?, content = ? "
				       + "where idx = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mvcboardVO.getSubject());
			pstmt.setString(2, mvcboardVO.getcontent());
			pstmt.setInt(3, mvcboardVO.getIdx());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
		
	}
//	ReplyService Ŭ�������� ȣ��, �۱׷�� ���� ��µǴ� ������ ����� HashMap ��ü�� �Ѱܹް� ���ǿ� �����ϴ� seq�� 1�� ������Ű�� update sql ����� �����ϴ� �޼ҵ�
	public void replyIncrement(HashMap<String, Integer> hmap) {
		System.out.println("MvcboardDAO�� replyIncrement() �޼ҵ� ����");
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			// ���� �� �׷쿡 �ְ� ���� seq�� ���ų� ũ�� seq�� 1������Ų��.
			String sql = "update mvcboard set seq = seq + 1 where ref = ? and seq >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hmap.get("ref"));
			pstmt.setInt(2, hmap.get("seq"));
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
	}
//	ReplyService Ŭ�������� ȣ��, ����� ����� ��ü�� �Ѱܹް� ����� ���̺� �����ϴ� insert sql ����� �����ϴ� �޼ҵ�
	public void replyInsert(MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO�� replyInsert() �޼ҵ� ����");
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into mvcboard (idx, name, subject, content, ref, lev, seq)"
					+ "values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcboardVO.getName());
			pstmt.setString(2, mvcboardVO.getSubject());
			pstmt.setString(3, mvcboardVO.getcontent());
			pstmt.setInt(4, mvcboardVO.getRef());
			pstmt.setInt(5, mvcboardVO.getLev());
			pstmt.setInt(6, mvcboardVO.getSeq());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
	}
	
	
}
