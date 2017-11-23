package com.mk.eap.component.pdfboxtable.table;

import java.io.Serializable;
import java.util.List;

public class TableHeadInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1950092503539364917L;

	private TextAlignment alignment = TextAlignment.CENTER;

	private String headTitle;
	
	private String headInfo;
	
	private float fontSize = 10f;
	
	private List<TableColumn> footerColumns;
	private Object[][]	content;
	private boolean isDrawColHead;

	
	
	public List<TableColumn> getFooterColumns() {
		return footerColumns;
	}

	public void setFooterColumns(List<TableColumn> footerColumns) {
		this.footerColumns = footerColumns;
	}

	public Object[][] getContent() {
		return content;
	}

	public void setContent(Object[][] content) {
		this.content = content;
	}

	public boolean isDrawColHead() {
		return isDrawColHead;
	}

	public void setDrawColHead(boolean isDrawColHead) {
		this.isDrawColHead = isDrawColHead;
	}

	public TextAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(TextAlignment alignment) {
		this.alignment = alignment;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public String getHeadInfo() {
		return headInfo;
	}

	public void setHeadInfo(String headInfo) {
		this.headInfo = headInfo;
	}

	public String getHeadTitle() {
		return headTitle;
	}

	public void setHeadTitle(String headTitle) {
		this.headTitle = headTitle;
	}
	
	
	private List<TableHeadInfo> headInfos;



	public List<TableHeadInfo> getHeadInfos() {
		return headInfos;
	}

	public void setHeadInfos(List<TableHeadInfo> headInfos) {
		this.headInfos = headInfos;
	}

	/**
	 * 外层调用 构造表头信息（包含表头名称+表头信息，需要调用方拼写后传过来）
	 * @param headTitle  标题即：表头的大题目，字体大小暂时我来控制
	 * @param headInfos 表头信息，如：单位：河北航信 、期间：2016年8月-2016年9月  ,对齐方式： TextAlignment
	 * 
	 */
	public TableHeadInfo(String headTitle, List<TableHeadInfo> headInfos) {
		super();
		this.headTitle = headTitle;
		this.headInfos = headInfos;
	}
	/**
	 * 内部构造方法，
	 * @param alignment 对齐方式
	 * @param headInfo  表头信息，调用方拼写
	 */
	public TableHeadInfo(TextAlignment alignment, String headInfo) {
		super();
		this.alignment = alignment;
		this.headInfo = headInfo;
	}

	public TableHeadInfo(List<TableColumn> footerColumns, Object[][] content, boolean isDrawColHead) {
		super();
		this.footerColumns = footerColumns;
		this.content = content;
		this.isDrawColHead = isDrawColHead;
	}
	
	
	
	
	
	
	
	



}
