package com.plateno.booking.internal.util.vo;

/**
 * 基础查询vo
 * @author mogt
 * @date 2016年11月25日
 */
public class BaseSearchVO {

	/**
	 * 一页大小
	 */
	private Integer size = 0;
	/**
	 * 当前页
	 */
	private Integer page = 0;

	public int getStart() {
		int start = (getPage() - 1) * getSize();
		return start;
	}

	public int getSize() {
		if (size == null || size <= 0)
			size = 20;
		
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public int getPage() {
		if(page == null || page <= 0) {
			page = 1;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
