/*      						
 * Copyright 2016 Beijing RRTIMES, Inc. All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    			|  		Who  			|  		What  
 * 2016-05-03		| 	    lil 			| 	create the file                       
 */
package com.mk.eap.user.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title:       SysOrgUser.java
 * @Package:     com.rrtimes.user.model
 * @Description: 类文件概述
 * 
 * <p>
 * 	类文件详细描述
 * </p> 
 * 
 * @author lil
 * 
 */
public class OrgUser implements Serializable{

	private static final long serialVersionUID = -1847021528387557657L;
	
	private Long userId;                        //用户ID
	
	private Long orgId;                         //企业机构ID
	
	private Byte status;
	
	private Integer isDefaultOrg; //设置默认企业
	
	private Date inviteDate;    //邀请时间
	
	private Boolean isOtherUser;    //是否是外部人员
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date lastLoginTime;    //最后登录时间
	
	private String lastLoginIP;    //最后登录的IP
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Integer getIsDefaultOrg() {
		return isDefaultOrg;
	}
	public void setIsDefaultOrg(Integer isDefaultOrg) {
		this.isDefaultOrg = isDefaultOrg;
	}
	public Date getInviteDate() {
		return inviteDate;
	}
	public void setInviteDate(Date inviteDate) {
		this.inviteDate = inviteDate;
	}
	public Boolean getIsOtherUser() {
		return isOtherUser;
	}
	public void setIsOtherUser(Boolean isOtherUser) {
		this.isOtherUser = isOtherUser;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	
	
    
}
