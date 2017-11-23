package com.mk.eap.portal.dto;

import java.util.Date;
import com.mk.eap.base.DTO;

public class RoleDto extends DTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1038297801546839452L;
	
	Long id;
	String code;	  
	String name;
	String memo;
	Date createTime;
	Date updateTime;
	
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
	
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}