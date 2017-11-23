package com.mk.eap.portal.vo;

import javax.persistence.Id;
import javax.persistence.Table;
import com.mk.eap.base.VO;

@Table(name = "eap_enum_detail")
public class EnumDetailVo extends VO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7674226806334282271L;
	
	@Id
	Long id;
	String name;
	String code;
	Long enumId;
	
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
	public Long getEnumId() {
		return enumId;
	}
	public void setEnumId(Long enumId) {
		this.enumId = enumId;
	}
}