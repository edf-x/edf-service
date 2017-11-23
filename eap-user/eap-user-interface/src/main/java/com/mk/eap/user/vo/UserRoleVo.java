package com.mk.eap.user.vo;

import javax.persistence.Id;
import javax.persistence.Table;
import com.mk.eap.base.VO;

@Table(name = "eap_user_role")
public class UserRoleVo extends VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6004739094264506275L;
	
	@Id
	Long userid;
	Long roleid;	
	Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userid;
	}
	public void setUserId(Long userid) {
		this.userid = userid;
	}
	
	public Long getRoleId() {
		return roleid;
	}
	public void setRoleId(Long roleid) {
		this.roleid = roleid;
	}
}