package com.mk.eap.portal.vo;

import javax.persistence.Id;
import javax.persistence.Table;
import com.mk.eap.base.VO;

@Table(name = "eap_operation")
public class OperationVo extends VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5777561920654148891L;
	
	@Id
	Long id;
	String name;
	Long parentId;
	Long dependentId;
    Boolean isEndNode;

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
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Long getDependentId() {
		return dependentId;
	}
	public void setDependentId(Long dependentId) {
		this.dependentId = dependentId;
	}
	
	public Boolean getIsEndNode() {
		return isEndNode;
	}
	public void setIsEndNode(Boolean isEndNode) {
		this.isEndNode = isEndNode;
	}
}