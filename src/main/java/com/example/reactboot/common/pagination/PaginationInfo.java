package com.example.reactboot.common.pagination;

import com.example.reactboot.common.utils.StringUtil;

public class PaginationInfo {
	private int currentPageNo;
	private int recordCountPerPage;
	private int paginationSize;
	private int totalRecordCount;
	private int totalPageCount;
	private int firstRecordIndex;
	private int lastRecordIndex;
	private int paginationStartNo;
	private int paginationEndNo;
	private int pageStartRow;
	private int pageEndRow;

	public PaginationInfo(int currentPageNo, int recordCountPerPage, int paginationSize) {
		this.currentPageNo = (currentPageNo == 0) ? 1 : StringUtil.nvlInt(currentPageNo, 1);
		this.recordCountPerPage = (recordCountPerPage == 0) ? 10 : StringUtil.nvlInt(recordCountPerPage, 10);
		this.paginationSize = (paginationSize == 0) ? 10 : StringUtil.nvlInt(paginationSize, 10);

		getFirstRecordIndex();
		getLastRecordIndex();
	}

	public int getCurrentPageNo() {
		return this.currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getRecordCountPerPage() {
		return this.recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getPaginationSize() {
		return this.paginationSize;
	}

	public void setPaginationSize(int pageSize) {
		this.paginationSize = pageSize;
	}

	public int getTotalRecordCount() {
		return this.totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public int getTotalPageCount() {
		this.totalPageCount = (this.getTotalRecordCount() - 1) / this.getRecordCountPerPage() + 1;
		return this.totalPageCount;
	}

	public int getFirstPageNo() {
		return 1;
	}

	public int getLastPageNo() {
		return this.getTotalPageCount();
	}

	public int getFirstRecordIndex() {
		this.firstRecordIndex = (this.getCurrentPageNo() - 1) * this.getRecordCountPerPage() + 1;
		return this.firstRecordIndex;
	}

	public int getLastRecordIndex() {
		this.lastRecordIndex = this.getCurrentPageNo() * this.getRecordCountPerPage();
		return this.lastRecordIndex;
	}

	public int getPaginationStartNo() {
		this.paginationStartNo = (this.getCurrentPageNo() - 1) / this.getPaginationSize() * this.getPaginationSize() + 1;
		return this.paginationStartNo;
	}

	public int getPaginationEndNo() {
		this.paginationEndNo = 0;
		if (this.getTotalPageCount() > 0) {
			if (this.getTotalPageCount() < this.getPaginationSize()) {
				this.paginationEndNo = this.getTotalPageCount();
			} else if (this.getPaginationStartNo() + this.getPaginationSize() - 1 > this.getTotalPageCount()) {
				this.paginationEndNo = this.getTotalPageCount();
			} else {
				this.paginationEndNo = this.getPaginationStartNo() + this.getPaginationSize() - 1;
			}
		}
		return this.paginationEndNo;
	}

	public int getPageStartRow() {
		if (this.getTotalRecordCount() == 0) {
			this.pageStartRow = 0;
		} else if (this.getCurrentPageNo() == this.getPaginationEndNo()) {
			this.pageStartRow = 1;
		} else {
			this.pageStartRow = this.getPageEndRow() - this.getRecordCountPerPage() + 1;
		}
		return this.pageStartRow;
	}

	public int getPageEndRow() {
		this.pageEndRow = this.getTotalRecordCount() - (this.getCurrentPageNo() - 1) * this.getRecordCountPerPage();
		return this.pageEndRow;
	}

	public boolean hasPrevPageNo() {
		return this.getPrevPageNo() >= this.getFirstPageNo();
	}

	public int getPrevPageNo() {
		return this.getPaginationStartNo() - 1;
	}

	public boolean hasNextPageNo() {
		return this.getNextPageNo() <= this.getLastPageNo();
	}

	public int getNextPageNo() {
		return this.getPaginationEndNo() + 1;
	}
}
