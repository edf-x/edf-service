package com.mk.eap.portal.dto;

import java.util.List;

import com.mk.eap.base.DTO;
import com.mk.eap.base.PageObject;

public class EnumInitDto extends DTO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	List<EnumDto> tree;
	List<EnumDetailDto> list;
	PageObject pagination;

	public List<EnumDto> getTree() {
		return tree;
	}
	public void setTree(List<EnumDto> tree) {
		this.tree = tree;
	}
	
	public List<EnumDetailDto> getList() {
		return list;
	}
	public void setList(List<EnumDetailDto> list) {
		this.list = list;
	}
	
	public PageObject getPagination() {
		return pagination;
	}
	public void setPagination(PageObject pagination) {
		this.pagination = pagination;
	}
}
