package com.mk.eap.portal.dto;

import java.util.List;

import com.mk.eap.base.DTO;

public class EnumDto extends DTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 904903413165806514L;
	
	Long id;
	String name;
	String code;
	List<EnumDetailDto> enumDetails; 

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<EnumDetailDto> getEnumDetails(){
		return enumDetails;
	}
	public void setEnumDetails(List<EnumDetailDto> enumDetails) {
		this.enumDetails = enumDetails;
	}
}
