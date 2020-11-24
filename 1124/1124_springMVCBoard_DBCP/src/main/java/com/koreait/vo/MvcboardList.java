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

	public MvcboardList(ArrayList<MvcboardVO> list, int pageSize, int totalCount, int totalPage, int currentPage,
			int startNo, int endNo, int startPage, int endPage) {
		super();
		this.list = list;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.startNo = startNo;
		this.endNo = endNo;
		this.startPage = startPage;
		this.endPage = endPage;
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
