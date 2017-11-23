package com.mk.eap.portal.dto;

import java.util.List;

import com.mk.eap.base.DTO;

public class RoleInitDto extends DTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2426949433619500884L;

	List<RoleDto> roles;
	List<MenuDto> menus;
	List<OperationDto> operations;
	List<MenuOperationDto> menuOperations;
	public List<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}
	public List<MenuDto> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuDto> menus) {
		this.menus = menus;
	}
	public List<OperationDto> getOperations() {
		return operations;
	}
	public void setOperations(List<OperationDto> operations) {
		this.operations = operations;
	}
	public List<MenuOperationDto> getMenuOperations() {
		return menuOperations;
	}
	public void setMenuOperations(List<MenuOperationDto> menuOperations) {
		this.menuOperations = menuOperations;
	}
	 
	
}
