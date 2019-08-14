package com.hxs.retrofittednetwork.Bean;

import java.util.List;

public class MainBean {

	private String curPage;
	private int offset;
	private boolean over;
	private int pageCount;
	private List<MainListBean> datas;
	private int size;
	private int total;

	public String getCurPage() {
		return curPage;
	}

	public int getOffset() {
		return offset;
	}

	public boolean isOver() {
		return over;
	}

	public int getPageCount() {
		return pageCount;
	}

	public List<MainListBean> getDatas() {
		return datas;
	}

	public int getSize() {
		return size;
	}

	public int getTotal() {
		return total;
	}
}
