package com.koreait.vo;

import java.util.ArrayList;

public class MvcboardList {
	private ArrayList<MvcboardVO> list = new ArrayList<MvcboardVO>();  
	// ����¡ �۾��� �ʿ��� ������
	private int pageSize = 10;		// �� ������ ���� �� ����	
	private int totalCount = 0; 	// ��ü �� ����
	private int totalPage = 0; 		// ��ü ������ ��
	private int currentPage = 1; 	
	private int startNo = 0; 		// �� ������ ���� ���� �۹�ȣ
	private int endNo = 0;			
	private int startPage = 0;		// �� ȭ�鿡�� ������ ������ ù ��° ��ȣ
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
