package com.mk.eap.portal.dto;

import java.util.List;

import com.mk.eap.base.DTO;
import com.mk.eap.base.PageObject;

public class ColumnInitDto extends DTO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<ColumnDto> tree;
	List<ColumnDetailDto> list;
	PageObject pagination;

	public List<ColumnDto> getTree() {
		return tree;
	}
	public void setTree(List<ColumnDto> tree) {
		this.tree = tree;
	}
	
	public List<ColumnDetailDto> getList() {
		return list;
	}
	public void setList(List<ColumnDetailDto> list) {
		this.list = list;
	}
	
	public PageObject getPagination() {
		return pagination;
	}
	public void setPagination(PageObject pagination) {
		this.pagination = pagination;
	}
}
