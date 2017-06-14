package com.tool.bean;
/**
 * 分页
 * @author wyj
 */
import java.io.Serializable;
import java.util.List;

public class Paging<T> implements Serializable {

	private static final long  serialVersionUID = -7090602930442343362L;
	
	private List<T> list;
	private int page = 1;//当前页
	private int pageSize = 10;//每页数据量
	private long total = 0L;//总数据量
	
	public List<T> getList() {
		return list;
	}
	public int getListSize() {
		if (null == getList()) {
			return 0;
		}
		return list.size();
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPages() {
		if (total == 0 || getPageSize() == 0) {
			return 0;
		}
		int p = (int) Math.floor(total / pageSize);
		if (total % pageSize == 0) {
			return p;
		} else {
			return p + 1;
		}
	}
	public long getTotal() {
		return total;
	}
	public void setTota(long total) {
		this.total = total;
	}
	public boolean isHasPre() {//是否有上一页
		if (getPages() <= 1) {
			return false;
		}
		return true;
	}
	public boolean isHasNext() {//是否有下一页
		if (getPage() >= getPages()) {
			return false;
		}
		return true;
	}
	public Integer getStart() {
		return (getPage() - 1) * getPageSize();
	}
	
	
}
