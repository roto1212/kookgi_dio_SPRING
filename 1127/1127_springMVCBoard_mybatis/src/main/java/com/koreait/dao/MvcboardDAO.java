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
	
//	DBCP 방식을 사용할 때 필요한 객체이므로 DBCP Template으로 코드가 완전히 변환되고 난 후 주석으로 처리한다.
//	private DataSource dataSource;	//Connection
	
//	DAO 클래스에서 DBCP Template을 사용하기 위해서 JdbcTemplate 클래스 타입의 객체를 선언한다.
	private JdbcTemplate jdbcTemplate;

	public MvcboardDAO() {
//		DAO 클래스의 객체(bean)가 생성되는 순간 servlet-context.xml에서 생성되서 컨트롤러가 전달 받아 Constant 클래스의 JdbcTemplate 클래스 타입의 객체에  저장된 bean으로 초기화 시킨다.
		jdbcTemplate = Constant.jdbcTemplate;
//		여기부터 
//		try {
//			Context context = new InitialContext();
//			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
//			System.out.println("연결성공!!!");
//		} catch (NamingException e) {
//			e.printStackTrace();
//			System.out.println("연결실패!!!");
//		}
//		여기까지 DBCP방식에 사용할 객체를 초기화 시키는 부분이므로  DBCP Template으로 코드가 완전히 변환되고 난 후 주석으로 처리한다.
	}
//	InsertService 클래스에서 호출되는 테이블에 저장할 메인글 데이터가 저장된 객체를 넘겨받고 insert sql 명령을 실행하는 메소드
//	insert, delete, update sql 명령을 실행하는 메소드의 인수로 넘어오는 데이터는 중간에 값이 변경되면 안되기 때문에 DBCP Template에서는 메소드의 인수를 선언 할 때 반드시 final를 붙여서 매개변수로 넘어온 데이터를 수정할 수 없도록 선언해야 한다.
	public void insert(final MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO의 insert() 메소드 실행");
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
//			PreparedStatementSetter 인터페이스 익명 내장 객체를 구현하고 자동으로 Override 되는 setValues() 메소드로 "?"를 채운다.
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, mvcboardVO.getName());
				ps.setString(2, mvcboardVO.getSubject());
				ps.setString(3, mvcboardVO.getcontent());
				
			}
		});
		
	}
//	SelectService 클래스에서 호출, 테이블에 저장된 전체글의 개수를 얻어오는 select sql 명령을 실행하는 메소드
	public int selectCount() {
		System.out.println("MvcboardDAO의 selectCount() 메소드 실행");
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
		
//		.update(): 테이블 내용이 갱신되는 sql 명령을 실행한다. => insert, update, delete
//		.queryForInt(): 테이블의 내용이 갱신되지 않는 sql 명령을 실행한다. => 실행결과가 1건이고 정수인 select 
//		.queryForObject(): 테이블의 내용이 갱신되지 않는 sql 명령을 실행한다. => 실행결과가 1건인 select
//		.query():  테이블의 내용이 갱신되지 않는 sql 명령을 실행한다. => 실행결과가 여러건인 select
		return jdbcTemplate.queryForInt(sql);
	}
//	SelectService 클래스에서 호출, 브라우저 화면에 표시할 1페이지 분량의 글의 시작 인덱스와 끝 인덱스가 저장된 HashMap 객체를 넘겨받고 테이블에서 1페이지 분량의 글을 얻어오는 select sql 명령을 실행하는 메소드
	public ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap) {
		System.out.println("MvcboardDAO의 selectList() 메소드 실행");
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
//		DBCP Template을 사용하는 경우 select sql 명령에만 "?"를 사용할 수 없다.
//		=> "?" 자리에 데이터가 저장된 변수를 사용해야 한다.
		String sql = "select * from ("
				   + "	select rownum rnum, AA.* from ("
				   + "		select * from mvcboard order by ref desc, seq asc"
				   + "	) AA where rownum <= " + hmap.get("endNo")
				   + ") where rnum >= " + hmap.get("startNo");
//		select sql 명령 실행 결과를 BeanPropertyRowMapper 클래스 생성자의 인수로 MvcboardVO 클래스 객체를 넘겨서 sql 명령 실행결과를 저장시켜 리턴한다. => List타입으로 결과가 리턴되기 때문에 메소드의 리턴타입으로 형변환이 필요하다.
//		테이블의 필드 이름과 MvcboardVO 클래스의 멤버변수이름이 반드시 같아야 정상적으로 실행된다.
		return (ArrayList<MvcboardVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(MvcboardVO.class));
		
	}
//	IncrementService 클래스에서 조회수를 증가시킬 글번호를 넘겨받고 조회수를 증가시키는 update sql 명령을 실행하는 메소드
	public void increment(final int idx) {
		System.out.println("MvcboardDAO의 increment() 메소드 실행");
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
//	ContentViewService 클래스에서 호출, 조회수를 증가시킨 글번호를 넘겨받고 조회수를 증가시킨 글 1건을 얻어오는 select sql 명령을 실행하는 메소드
	public MvcboardVO selectByIdx(int idx) {
		System.out.println("MvcboardDAO의 selectByIdx() 메소드 실행");
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MvcboardVO mvcboardVO = null;		// 글 1건을 얻어와 저장한 후 리턴시킬 객체를 선언한다.
		try {
			conn = dataSource.getConnection();
			String sql = "select * from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
//			ResultSet 객체에 저장된 글 1개를 저장해 리턴시키기 위해 MvcboardVO 클래스 객체에 저장한다.
			if (rs.next()) {		// 결과값 리스트 중 다음이 있으면 true, 없으면 false 리턴한다.
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
//	DeleteService 클래스에서 호출되는 삭제할 글번호를 넘겨받고 글 1건을 삭제하는 delete sql 명령을 실행하는 메소드 	
	public void delete(int idx) {
		System.out.println("MvcboardDAO의 delete() 메소드 실행");
		
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
//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을 실행하는 메소드 - 1
	public void update(final int idx, final String subject, final String content) {
		System.out.println("MvcboardDAO의 update()-1 메소드 실행");
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
//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을 실행하는 메소드 - 2
	public void update(final MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO의 update()-2 메소드 실행 ");
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
//	ReplyService 클래스에서 호출, 글그룹과 글이 출력되는 순서가 저장된 HashMap 객체를 넘겨받고 조건에 만족하는 seq를 1씩 증가시키는 update sql 명령을 실행하는 메소드
	public void replyIncrement(final HashMap<String, Integer> hmap) {
		System.out.println("MvcboardDAO의 replyIncrement() 메소드 실행");
		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			// 같은 글 그룹에 있고 이전 seq와 같거나 크면 seq를 1증가시킨다.
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
//	ReplyService 클래스에서 호출, 답글이 저장된 객체를 넘겨받고 답글을 테이블에 저장하는 insert sql 명령을 실행하는 메소드
	public void replyInsert(final MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO의 replyInsert() 메소드 실행");
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
