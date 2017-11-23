package com.mk.eap.portal.vo;

import javax.persistence.Id;
import javax.persistence.Table;

import com.mk.eap.base.VO;

@Table(name = "eap_menu_operation")
public class MenuOperationVo extends VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7363478083578477804L;
	
	@Id
	Long id;
	Long menuId;
	Long operationId;
	Long roleId;

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
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
