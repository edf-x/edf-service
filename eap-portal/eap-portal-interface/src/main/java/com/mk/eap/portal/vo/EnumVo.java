package com.mk.eap.portal.vo;

import javax.persistence.Id;
import javax.persistence.Table;
import com.mk.eap.base.VO;

@Table(name = "eap_enum")
public class EnumVo extends VO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6417864171288773949L;
	
	@Id
	Long id;
	String name;
	String code;
	

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
}