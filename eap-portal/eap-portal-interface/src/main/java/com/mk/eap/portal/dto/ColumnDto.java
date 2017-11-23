package com.mk.eap.portal.dto;

import java.util.List;

import com.mk.eap.base.DTO;

public class ColumnDto extends DTO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long id;
	String code;
	String name;
	Long isDefault;
	Long userId;
	List<ColumnDetailDto> columnDetails; 

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Long isDefault) {
		this.isDefault = isDefault;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<ColumnDetailDto> getColumnDetails(){
		return columnDetails;
	}
	public void setColumnDetails(List<ColumnDetailDto> columnDetails) {
		this.columnDetails = columnDetails;
	}
}
