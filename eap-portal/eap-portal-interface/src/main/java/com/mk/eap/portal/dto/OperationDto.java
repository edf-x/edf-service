package com.mk.eap.portal.dto;

import java.util.List;

import com.mk.eap.base.DTO;

public class OperationDto extends DTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8236837002390392725L;
	
	Long id;
	String name;
	Long parentId;
	Long dependentId;
    Boolean isEndNode;
    List<MenuOperationDto> operations;

	public List<MenuOperationDto> getOperations() {
		return operations;
	}
	public void setOperations(List<MenuOperationDto> operations) {
		this.operations = operations;
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
