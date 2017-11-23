package com.mk.eap.portal.vo;

import javax.persistence.Id;
import javax.persistence.Table;

import com.mk.eap.base.VO;

@Table(name = "eap_menu")
public class MenuVo extends VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -687125827392677434L;
	
	@Id
	Long id;
	String name;
	String code;
	String appName;
	String appProps;
	Long parentId; 
	Long userId;
	Long showOrder;
	
	public String getAppProps() {
		return appProps;
	}
	public void setAppProps(String appProps) {
		this.appProps = appProps;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
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
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Long showOrder) {
		this.showOrder = showOrder;
	}
}
