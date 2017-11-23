package com.mk.eap.portal.vo;

import javax.persistence.Id;
import javax.persistence.Table;
import com.mk.eap.base.VO;

@Table(name = "eap_column_detail")
public class ColumnDetailVo extends VO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	Long id;
	Long columnId; //栏目方案id
	String fieldName; //字段名称
	String caption; //字段标题
	Long idFieldType;  //字段类型
	Long width;  //宽度
	Long defPrecision; //默认精度
	Long idAlignType; //对齐方式
	Long colIndex; //显示顺序
	Long idOrderMode; //排序方式
	Long isFixed; //是否固定列
	Long isVisible; //是否显示
	Long isMustSelect; //是否必选
	Long isSystem; //是否系统字段
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public Long getIdFieldType() {
		return idFieldType;
	}
	public void setIdFieldType(Long idFieldType) {
		this.idFieldType = idFieldType;
	}
	
	public Long getWidth() {
		return width;
	}
	public void setWidth(Long width) {
		this.width = width;
	}
	
	public Long getDefPrecision() {
		return defPrecision;
	}
	public void setDefPrecision(Long defPrecision) {
		this.defPrecision = defPrecision;
	}
	
	public Long getIdAlignType() {
		return idAlignType;
	}
	public void setIdAlignType(Long idAlignType) {
		this.idAlignType = idAlignType;
	}
	
	public Long getColIndex() {
		return colIndex;
	}
	public void setColIndex(Long colIndex) {
		this.colIndex = colIndex;
	}
	
	public Long getIdOrderMode() {
		return idOrderMode;
	}
	public void setIdOrderMode(Long idOrderMode) {
		this.idOrderMode = idOrderMode;
	}
	
	
	public Long getIsFixed() {
		return isFixed;
	}
	public void setIsFixed(Long isFixed) {
		this.isFixed = isFixed;
	}
	
	public Long getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Long isVisible) {
		this.isVisible = isVisible;
	}
	
	public Long getIsMustSelect() {
		return isMustSelect;
	}
	public void setIsMustSelect(Long isMustSelect) {
		this.isMustSelect = isMustSelect;
	}
	
	public Long getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Long isSystem) {
		this.isSystem = isSystem;
	}

}




