package com.mk.eap.portal.vo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import com.mk.eap.base.VO;

@Table(name = "eap_role")
public class RoleVo extends VO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4145661937134845107L;
	
	@Id
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