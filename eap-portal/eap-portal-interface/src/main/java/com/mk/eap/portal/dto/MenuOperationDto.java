package com.mk.eap.portal.dto;

import com.mk.eap.base.DTO;

public class MenuOperationDto extends DTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1221010611853734472L;
	
	Long id;
	Long menuId;
	Long operationId;
	String operationName; 
	Long roleId;

	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public Long getOperationId() {
		return operationId;
	}
	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
}
