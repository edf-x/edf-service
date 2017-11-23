package com.mk.eap.user.dto;

import com.mk.eap.base.DTO;
/*
	用户和角色的关联表
 */
public class UserRoleDto extends DTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2437060793073181446L;
	
	Long id;
	Long userId;
	Long roleId;	 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}