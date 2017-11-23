package com.mk.eap.component.pdfboxtable.table;

import java.io.Serializable;
import java.util.List;

public class TableFooterInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3791563000434717797L;

	private List<TableColumn> footerColumns;
	private Object[][]	content;
	private boolean isDrawColHead;
	private boolean isDrawSplit;

	
	
	public boolean isDrawSplit() {
		return isDrawSplit;
	}

	public void setDrawSplit(boolean isDrawSplit) {
		this.isDrawSplit = isDrawSplit;
	}

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
	/**
	 * 构造表尾信息
	 * @param footerColumns ：列集合
	 * @param content  ：填充内容
	 */
	public TableFooterInfo(List<TableColumn> footerColumns, Object[][] content) {
		super();
		this.footerColumns = footerColumns;
		this.content = content;
	}
	/**
	 * 构造表尾信息
	 * @param footerColumns：列集合
	 * @param isDrawSplit：是否绘制分隔符，默认：false
	 * @param content：填充内容：
	 */
	public TableFooterInfo( boolean isDrawSplit,List<TableColumn> footerColumns, Object[][] content) {
		super();
		this.footerColumns = footerColumns;
		this.content = content;
		this.isDrawSplit = isDrawSplit;
	}
	/**
	 * 构造表尾信息
	 * @param footerColumns：列集合
	 * @param content ：填充内容
	 * @param isDrawColHead：是否绘制列头默认：false
	 * @param isDrawSplit：是否绘制分隔符，默认：false
	 */
	public TableFooterInfo(List<TableColumn> footerColumns, Object[][] content, boolean isDrawColHead,
			boolean isDrawSplit) {
		super();
		this.footerColumns = footerColumns;
		this.content = content;
		this.isDrawColHead = isDrawColHead;
		this.isDrawSplit = isDrawSplit;
	}

	/**
	 * 构造表尾信息
	 * @param footerColumns：列集合
	 * @param content：填充表尾内容
	 * @param isDrawColHead ：是否绘制列头默认：false
	 */
	public TableFooterInfo(List<TableColumn> footerColumns, Object[][] content, boolean isDrawColHead) {
		super();
		this.footerColumns = footerColumns;
		this.content = content;
		this.isDrawColHead = isDrawColHead;
	}
	
	


	
	

}
