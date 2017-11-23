package com.mk.eap.portal.vo;

import javax.persistence.Id;
import javax.persistence.Table;
import com.mk.eap.base.VO;

@Table(name = "eap_column")
public class ColumnVo extends VO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	Long id;
	String code;
	String name;
	Long isDefault;
	Long userId;

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

	public Long getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Long isDefault) {
		this.isDefault = isDefault;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}