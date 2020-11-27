package com.koreait.vo;

import java.util.ArrayList;

public class MvcboardList {
	private ArrayList<MvcboardVO> list = new ArrayList<MvcboardVO>();  
	// 페이징 작업에 필요한 변수들
	private int pageSize = 10;		// 한 페이지 내의 글 개수	
	private int totalCount = 0; 	// 전체 글 개수
	private int totalPage = 0; 		// 전체 페이지 수
	private int currentPage = 1; 	
	private int startNo = 0; 		// 한 페이지 내의 시작 글번호
	private int endNo = 0;			
	private int startPage = 0;		// 한 화면에서 보여줄 페이지 첫 번째 번호
	private int endPage = 0;
	
	public MvcboardList() {
		// TODO Auto-generated constructor stub
	}

//	pageSize, totalCount, currentPage를 넘겨받아 페이지 작업에 사용할 8개의 변수를 초기화 시키는 메소드
	public void initMvcboardList(int pageSize, int totalCount, int currentPage) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		
		calculator();
	}
	
	private void calculator() {
		totalPage = (totalCount - 1) / pageSize + 1;
		currentPage = currentPage > totalPage ? totalPage : currentPage;
	
		//	oracle은 select sql명령 실행결과 인덱스 값이 1부터 시작되므로 mysql의 계산식에 1을 더해야 한다.
		startNo = (currentPage - 1) * pageSize + 1 ; 
		endNo = startNo + pageSize -1;
		endNo = endNo > totalCount ? totalCount : endNo;
		
		startPage = (currentPage - 1)  / 10 * 10 + 1 ;
		endPage = startPage +  9;
		endPage = endPage > totalPage ?  totalPage : endPage; 
		
	}


	@Override
	public String toString() {
		return "MvcboardList [list=" + list + ", pageSize=" + pageSize + ", totalCount=" + totalCount + ", totalPage="
				+ totalPage + ", currentPage=" + currentPage + ", startNo=" + startNo + ", endNo=" + endNo
				+ ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}

	public ArrayList<MvcboardVO> getList() {
		return list;
	}

	public void setList(ArrayList<MvcboardVO> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
}
