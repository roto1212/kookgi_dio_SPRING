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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.koreait.springMVCBoard_DBCP.Constant;
import com.koreait.vo.MvcboardVO;

public class MvcboardDAO {
	
//	DBCP ����� ����� �� �ʿ��� ��ü�̹Ƿ� DBCP Template���� �ڵ尡 ������ ��ȯ�ǰ� �� �� �ּ����� ó���Ѵ�.
//	private DataSource dataSource;	//Connection
	
//	DAO Ŭ�������� DBCP Template�� ����ϱ� ���ؼ� JdbcTemplate Ŭ���� Ÿ���� ��ü�� �����Ѵ�.
	private JdbcTemplate jdbcTemplate;

	public MvcboardDAO() {
//		DAO Ŭ������ ��ü(bean)�� �����Ǵ� ���� servlet-context.xml���� �����Ǽ� ��Ʈ�ѷ��� ���� �޾� Constant Ŭ������ JdbcTemplate Ŭ���� Ÿ���� ��ü��  ����� bean���� �ʱ�ȭ ��Ų��.
		jdbcTemplate = Constant.jdbcTemplate;
//		������� 
//		try {
//			Context context = new InitialContext();
//			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
//			System.out.println("���Ἲ��!!!");
//		} catch (NamingException e) {
//			e.printStackTrace();
//			System.out.println("�������!!!");
//		}
//		������� DBCP��Ŀ� ����� ��ü�� �ʱ�ȭ ��Ű�� �κ��̹Ƿ�  DBCP Template���� �ڵ尡 ������ ��ȯ�ǰ� �� �� �ּ����� ó���Ѵ�.
	}
//	InsertService Ŭ�������� ȣ��Ǵ� ���̺� ������ ���α� �����Ͱ� ����� ��ü�� �Ѱܹް� insert sql ����� �����ϴ� �޼ҵ�
//	insert, delete, update sql ����� �����ϴ� �޼ҵ��� �μ��� �Ѿ���� �����ʹ� �߰��� ���� ����Ǹ� �ȵǱ� ������ DBCP Template������ �޼ҵ��� �μ��� ���� �� �� �ݵ�� final�� �ٿ��� �Ű������� �Ѿ�� �����͸� ������ �� ������ �����ؾ� �Ѵ�.
	public void insert(final MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO�� insert() �޼ҵ� ����");
//		System.out.println(mvcboardVO);
		/*
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
		*/
		String sql = "insert into mvcboard (idx, name, subject, content, ref, lev, seq) "
				+ "values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
		
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
//			PreparedStatementSetter �������̽� �͸� ���� ��ü�� �����ϰ� �ڵ����� Override �Ǵ� setValues() �޼ҵ�� "?"�� ä���.
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, mvcboardVO.getName());
				ps.setString(2, mvcboardVO.getSubject());
				ps.setString(3, mvcboardVO.getcontent());
				
			}
		});
		
	}
//	SelectService Ŭ�������� ȣ��, ���̺� ����� ��ü���� ������ ������ select sql ����� �����ϴ� �޼ҵ�
	public int selectCount() {
		System.out.println("MvcboardDAO�� selectCount() �޼ҵ� ����");
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;					
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) from mvcboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
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
		*/
		String sql = "select count(*) from mvcboard";
		
//		.update(): ���̺� ������ ���ŵǴ� sql ����� �����Ѵ�. => insert, update, delete
//		.queryForInt(): ���̺��� ������ ���ŵ��� �ʴ� sql ����� �����Ѵ�. => �������� 1���̰� ������ select 
//		.queryForObject(): ���̺��� ������ ���ŵ��� �ʴ� sql ����� �����Ѵ�. => �������� 1���� select
//		.query():  ���̺��� ������ ���ŵ��� �ʴ� sql ����� �����Ѵ�. => �������� �������� select
		return jdbcTemplate.queryForInt(sql);
	}
//	SelectService Ŭ�������� ȣ��, ������ ȭ�鿡 ǥ���� 1������ �з��� ���� ���� �ε����� �� �ε����� ����� HashMap ��ü�� �Ѱܹް� ���̺��� 1������ �з��� ���� ������ select sql ����� �����ϴ� �޼ҵ�
	public ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap) {
		System.out.println("MvcboardDAO�� selectList() �޼ҵ� ����");
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MvcboardVO> list = null;		
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
			list = new ArrayList<MvcboardVO>();
			while (rs.next()) {		
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
		*/
//		DBCP Template�� ����ϴ� ��� select sql ��ɿ��� "?"�� ����� �� ����.
//		=> "?" �ڸ��� �����Ͱ� ����� ������ ����ؾ� �Ѵ�.
		String sql = "select * from ("
				   + "	select rownum rnum, AA.* from ("
				   + "		select * from mvcboard order by ref desc, seq asc"
				   + "	) AA where rownum <= " + hmap.get("endNo")
				   + ") where rnum >= " + hmap.get("startNo");
//		select sql ��� ���� ����� BeanPropertyRowMapper Ŭ���� �������� �μ��� MvcboardVO Ŭ���� ��ü�� �Ѱܼ� sql ��� �������� ������� �����Ѵ�. => ListŸ������ ����� ���ϵǱ� ������ �޼ҵ��� ����Ÿ������ ����ȯ�� �ʿ��ϴ�.
//		���̺��� �ʵ� �̸��� MvcboardVO Ŭ������ ��������̸��� �ݵ�� ���ƾ� ���������� ����ȴ�.
		return (ArrayList<MvcboardVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(MvcboardVO.class));
		
	}
//	IncrementService Ŭ�������� ��ȸ���� ������ų �۹�ȣ�� �Ѱܹް� ��ȸ���� ������Ű�� update sql ����� �����ϴ� �޼ҵ�
	public void increment(final int idx) {
		System.out.println("MvcboardDAO�� increment() �޼ҵ� ����");
		/*
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
		}*/
//		String sql = "update mvcboard set hit = hit + 1 where idx = ?";
//		jdbcTemplate.update(sql, new PreparedStatementSetter() {
//			
//			@Override
//			public void setValues(PreparedStatement ps) throws SQLException {
//				ps.setInt(1, idx);
//				
//			}
//		});
		String sql = "update mvcboard set hit = hit + 1 where idx = " + idx;
		jdbcTemplate.update(sql);
		
		
	}
//	ContentViewService Ŭ�������� ȣ��, ��ȸ���� ������Ų �۹�ȣ�� �Ѱܹް� ��ȸ���� ������Ų �� 1���� ������ select sql ����� �����ϴ� �޼ҵ�
	public MvcboardVO selectByIdx(int idx) {
		System.out.println("MvcboardDAO�� selectByIdx() �޼ҵ� ����");
		/*
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
		*/
		String sql = "select * from mvcboard where idx = " + idx;
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(MvcboardVO.class));
		
	}
//	DeleteService Ŭ�������� ȣ��Ǵ� ������ �۹�ȣ�� �Ѱܹް� �� 1���� �����ϴ� delete sql ����� �����ϴ� �޼ҵ� 	
	public void delete(int idx) {
		System.out.println("MvcboardDAO�� delete() �޼ҵ� ����");
		
		/*Connection conn = null;
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
		*/
		String sql = "delete from mvcboard where idx = " + idx;
		jdbcTemplate.update(sql);
		
	}
//	UpdateService Ŭ�������� ȣ��Ǵ� ������ �۹�ȣ�� �����͸� �Ѱܹް� �� 1���� �����ϴ� update sql ����� �����ϴ� �޼ҵ� - 1
	public void update(final int idx, final String subject, final String content) {
		System.out.println("MvcboardDAO�� update()-1 �޼ҵ� ����");
		/*
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
		*/
		/*
		String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, subject);
				ps.setString(2, content);
				ps.setInt(3, idx);
			}
		});
		*/
		String sql = "update mvcboard set subject = '" + subject
				+ "', content = '" + content
				+ "' where idx = " + idx;
		jdbcTemplate.update(sql); 
	
	}
//	UpdateService Ŭ�������� ȣ��Ǵ� ������ �۹�ȣ�� �����͸� �Ѱܹް� �� 1���� �����ϴ� update sql ����� �����ϴ� �޼ҵ� - 2
	public void update(final MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO�� update()-2 �޼ҵ� ���� ");
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
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
		*/
		/*
		String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, mvcboardVO.getSubject());
				ps.setString(2, mvcboardVO.getcontent());
				ps.setInt(3, mvcboardVO.getIdx());
				
			}
		});
		*/
		String sql = "update mvcboard set subject = '" + mvcboardVO.getSubject()
				+ "', content = '" + mvcboardVO.getcontent()
				+ "' where idx = '" + mvcboardVO.getIdx() + "'";
		jdbcTemplate.update(sql); 
	}
//	ReplyService Ŭ�������� ȣ��, �۱׷�� ���� ��µǴ� ������ ����� HashMap ��ü�� �Ѱܹް� ���ǿ� �����ϴ� seq�� 1�� ������Ű�� update sql ����� �����ϴ� �޼ҵ�
	public void replyIncrement(final HashMap<String, Integer> hmap) {
		System.out.println("MvcboardDAO�� replyIncrement() �޼ҵ� ����");
		/*
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
		*/
		/*String sql = "update mvcboard set seq = seq + 1 where ref = ? and seq >= ?";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, hmap.get("ref"));
				ps.setInt(2, hmap.get("seq"));
				
			}
		});*/
		String sql = "update mvcboard set seq = seq + 1 where ref = " + hmap.get("ref")
				+ "and seq >= " + hmap.get("seq");
		jdbcTemplate.update(sql);
	}
//	ReplyService Ŭ�������� ȣ��, ����� ����� ��ü�� �Ѱܹް� ����� ���̺� �����ϴ� insert sql ����� �����ϴ� �޼ҵ�
	public void replyInsert(final MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO�� replyInsert() �޼ҵ� ����");
		/*
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
		*/
		String sql = "insert into mvcboard (idx, name, subject, content, ref, lev, seq)"
				+ "values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, mvcboardVO.getName());
				pstmt.setString(2, mvcboardVO.getSubject());
				pstmt.setString(3, mvcboardVO.getcontent());
				pstmt.setInt(4, mvcboardVO.getRef());
				pstmt.setInt(5, mvcboardVO.getLev());
				pstmt.setInt(6, mvcboardVO.getSeq());
				
			}
		});
	}
	
	
}
