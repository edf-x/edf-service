package com.mk.eap.portal.dto;

import java.util.List;

import com.mk.eap.base.DTO;

public class MenuDto extends DTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2281469258935109618L;

	Long id;
	String name;
	String code;
	String appName;
	String appProps;
	Long parentId;
	Long userId;
	Long showOrder;
	List<MenuOperationDto> operations; 
	
	public String getAppProps() {
		return appProps;
	}
	public void setAppProps(String appProps) {
		this.appProps = appProps;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
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
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Long showOrder) {
		this.showOrder = showOrder;
	}
	public List<MenuOperationDto> getMenuOperationList(){
		return operations;
	}
	public void setMenuOperationList(List<MenuOperationDto> operations) {
		this.operations = operations;
	}
}
