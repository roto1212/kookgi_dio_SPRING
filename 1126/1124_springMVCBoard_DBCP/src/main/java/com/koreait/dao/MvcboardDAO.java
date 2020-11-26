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
			System.out.println("연결성공!!!");
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("연결실패!!!");
		}
	}
//	InsertService 클래스에서 호출되는 테이블에 저장할 메인글 데이터가 저장된 객체를 넘겨받고 insert sql 명령을 실행하는 메소드
	public void insert(MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO의 insert() 메소드 실행");
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
//	SelectService 클래스에서 호출, 테이블에 저장된 전체글의 개수를 얻어오는 select sql 명령을 실행하는 메소드
	public int selectCount() {
		System.out.println("MvcboardDAO의 selectCount() 메소드 실행");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;					// 테이블 저장된 전체 글의 개수를 저장 시켜 리턴할 변수를 선언한다.
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) from mvcboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
//			테이블에 저장된 전체 글의 개수는 null 이 나올리 없으므로(0이상) 조건 비교를 할 필요는 없다.
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
//	SelectService 클래스에서 호출, 브라우저 화면에 표시할 1페이지 분량의 글의 시작 인덱스와 끝 인덱스가 저장된 HashMap 객체를 넘겨받고 테이블에서 1페이지 분량의 글을 얻어오는 select sql 명령을 실행하는 메소드
	public ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap) {
		System.out.println("MvcboardDAO의 selectList() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MvcboardVO> list = null;		// 1페이지 분량의 글을 저장해 리턴시킬 배열목록을 선언한다.
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
//			ResultSet 객체에  저장된 1페이지 분량을 저장해 리턴시킬 배열목록 객체를 생성한다.
			list = new ArrayList<MvcboardVO>();
//			ResultSet 객체에 저장된 1페이지 분량의 글을 리턴시키기 위해 배열목록에 얻어온 글의 개수 만큼 반복하며 저장시킨다.
			while (rs.next()) {		// 결과값 리스트 중 다음이 있으면 true, 없으면 false 리턴한다.
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
//	IncrementService 클래스에서 조회수를 증가시킬 글번호를 넘겨받고 조회수를 증가시키는 update sql 명령을 실행하는 메소드
	public void increment(int idx) {
		System.out.println("MvcboardDAO의 increment() 메소드 실행");
		
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
//	ContentViewService 클래스에서 호출, 조회수를 증가시킨 글번호를 넘겨받고 조회수를 증가시킨 글 1건을 얻어오는 select sql 명령을 실행하는 메소드
	public MvcboardVO selectByIdx(int idx) {
		System.out.println("MvcboardDAO의 selectByIdx() 메소드 실행");
		
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
	}
//	DeleteService 클래스에서 호출되는 삭제할 글번호를 넘겨받고 글 1건을 삭제하는 delete sql 명령을 실행하는 메소드 	
	public void delete(int idx) {
		System.out.println("MvcboardDAO의 delete() 메소드 실행");
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
//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을 실행하는 메소드 - 1
	public void update(int idx, String subject, String content) {
		System.out.println("MvcboardDAO의 update() 메소드 실행");
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
//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을 실행하는 메소드 - 2
	public void update(MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO의 update() 메소드 실행");
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
//	ReplyService 클래스에서 호출, 글그룹과 글이 출력되는 순서가 저장된 HashMap 객체를 넘겨받고 조건에 만족하는 seq를 1씩 증가시키는 update sql 명령을 실행하는 메소드
	public void replyIncrement(HashMap<String, Integer> hmap) {
		System.out.println("MvcboardDAO의 replyIncrement() 메소드 실행");
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
		
	}
//	ReplyService 클래스에서 호출, 답글이 저장된 객체를 넘겨받고 답글을 테이블에 저장하는 insert sql 명령을 실행하는 메소드
	public void replyInsert(MvcboardVO mvcboardVO) {
		System.out.println("MvcboardDAO의 replyInsert() 메소드 실행");
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
