package com.mk.eap.component.pdfboxtable.pdf;

import com.mk.eap.component.pdfboxtable.table.TableColumn;
import com.mk.eap.component.pdfboxtable.table.Table;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *  pdf生成器类
 * @author thinkpad
 *
 */
public class PageablePdfGenerator {
	private float leftMargion;
	private float topMargion;
	private float bottomMargion;
	private Boolean isIncludePageNumber;
	private String srcFile;
	private List<TableColumn> tableColumns;
	private String[][] tableContent;
	private float fontSize=10f;
	private PDRectangle pdRectangle =PDRectangle.A4;
	private String headinfo;
	
	private Map<String, String[][]> dataMap;
	
	private PageablePdf pageablePdf;
	
	
	
	
	
	
	

	public PageablePdf getPageablePdf() {
		return pageablePdf;
	}


	public void setPageablePdf(PageablePdf pageablePdf) {
		this.pageablePdf = pageablePdf;
	}


	public Map<String, String[][]> getDataMap() {
		return dataMap;
	}


	public void setDataMap(Map<String, String[][]> dataMap) {
		this.dataMap = dataMap;
	}


	public PDRectangle getPdRectangle() {
		return pdRectangle;
	}


	public void setPdRectangle(PDRectangle pdRectangle) {
		this.pdRectangle = pdRectangle;
	}
	

	public String getHeadinfo() {
		return headinfo;
	}


	public void setHeadinfo(String headinfo) {
		this.headinfo = headinfo;
	}


	public float getLeftMargion() {
		return leftMargion;
	}


	public void setLeftMargion(float leftMargion) {
		this.leftMargion = leftMargion;
	}


	public float getTopMargion() {
		return topMargion;
	}


	public void setTopMargion(float topMargion) {
		this.topMargion = topMargion;
	}


	public float getBottomMargion() {
		return bottomMargion;
	}


	public void setBottomMargion(float bottomMargion) {
		this.bottomMargion = bottomMargion;
	}





	public Boolean getIsIncludePageNumber() {
		return isIncludePageNumber;
	}


	public void setIsIncludePageNumber(Boolean isIncludePageNumber) {
		this.isIncludePageNumber = isIncludePageNumber;
	}


	public String getsrcFile() {
		return srcFile;
	}


	public void setsrcFile(String srcFile) {
		this.srcFile = srcFile;
	}


	public List<TableColumn> getTableColumns() {
		return tableColumns;
	}


	public void setTableColumns(List<TableColumn> tableColumns) {
		this.tableColumns = tableColumns;
	}


	public String[][] getTableContent() {
		return tableContent;
	}


	public void setTableContent(String[][] tableContent) {
		this.tableContent = tableContent;
	}


	public float getFontSize() {
		return fontSize;
	}


	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}


	/**
	 * 创建打印类对象（8个参数）
	 * @param leftMargion 左边距
	 * @param topMargion 上边距
	 * @param bottomMargion 下边距
	 * @param isIncludePageNumber 是否包含页码
	 * @param srcFile 来源名称(包含扩展名.pdf)
	 * @param tableColumns 表头的列集合
	 * @param tableContent 表的行信息 二维数组
	 * @param fontSize 字体大小
	 * @throws IOException 
	 */
	public PageablePdfGenerator(float leftMargion, float topMargion, float bottomMargion, Boolean isIncludePageNumber,
			String srcFile, List<TableColumn> tableColumns, String[][] tableContent, float fontSize) throws IOException {
		this.leftMargion = leftMargion;
		this.topMargion = topMargion;
		this.bottomMargion = bottomMargion;
		this.isIncludePageNumber = isIncludePageNumber;
		this.srcFile = srcFile;
		this.tableColumns = tableColumns;
		this.tableContent = tableContent;
		this.fontSize = fontSize;
		
        drawTable(tableColumns, tableContent,invokePageablePdf(this.getPdRectangle()));
        

	}
	
	



	public PageablePdfGenerator(float leftMargion, float topMargion, float bottomMargion, Boolean isIncludePageNumber,
			String srcFile, List<TableColumn> tableColumns, String headinfo,float fontSize, Map<String, String[][]> dataMap,
			PageablePdf pageablePdf) throws IOException {
		this.leftMargion = leftMargion;
		this.topMargion = topMargion;
		this.bottomMargion = bottomMargion;
		this.isIncludePageNumber = isIncludePageNumber;
		this.srcFile = srcFile;
		this.tableColumns = tableColumns;
		this.headinfo = headinfo;
		this.dataMap = dataMap;
		this.pageablePdf = pageablePdf;
		this.fontSize = fontSize;
		setPdfBaseInfo(pageablePdf,headinfo);
//		drawTable(tableColumns,dataMap,pageablePdf);
	}


	/**
	 * 创建打印类对象 (10个参数)
	 * @param leftMargion 左边距
	 * @param topMargion 上边距
	 * @param bottomMargion 下边距
	 * @param isIncludePageNumber 是否包含页码
	 * @param 来源名称(包含扩展名.pdf)
	 * @param tableColumns 表头的列集合
	 * @param tableContent 表的行信息 二维数组
	 * @param fontSize 字体大小
	 * @param pdRectangle 纸张类别，例如：PDRectangle.A4
	 * @param headinfo 非table的头信息
	 * @throws IOException 
	 */

	public PageablePdfGenerator(float leftMargion, float topMargion, float bottomMargion, Boolean isIncludePageNumber,
			String srcFile, List<TableColumn> tableColumns, String[][] tableContent, float fontSize,
			PDRectangle pdRectangle, String headinfo) throws IOException {
		super();
		this.leftMargion = leftMargion;
		this.topMargion = topMargion;
		this.bottomMargion = bottomMargion;
		this.isIncludePageNumber = isIncludePageNumber;
		this.srcFile = srcFile;
		this.tableColumns = tableColumns;
		this.tableContent = tableContent;
		this.fontSize = fontSize;
		this.pdRectangle = pdRectangle;
		this.headinfo = headinfo;
		
		drawTable(tableColumns, tableContent,invokePageablePdf(this.getPdRectangle(),getHeadinfo()));
	}


	private Table drawTable(List<TableColumn> tableColumns, String[][] tableContent,PageablePdf pageablePdf) throws IOException {
		Table table = new Table(tableColumns, tableContent);
        table.setDrawHeaders(true);
        table.setCellInsidePadding(3f);
        table.setDrawGrid(true);
        pageablePdf.drawTable(table);
        pageablePdf.closeDocument();
        pageablePdf.save(srcFile);
		return table;
	}
	private void drawTable(List<TableColumn> tableColumns, Map<String, String[][]> dataMap,PageablePdf pageablePdf) throws IOException {
		for (Entry<String, String[][]> keyAndValue : dataMap.entrySet()) {
			Table table = new Table(tableColumns, keyAndValue.getValue());
			table.setDrawHeaders(true);
			table.setCellInsidePadding(3f);
			table.setDrawGrid(true);
			pageablePdf.drawTable(table);
			pageablePdf.closeDocument();
			pageablePdf.save(srcFile);
		}
		
	}
	public void drawTable() throws IOException {
		for (Entry<String, String[][]> keyAndValue : dataMap.entrySet()) {
			Table table = new Table(tableColumns, keyAndValue.getValue());
			table.setDrawHeaders(true);
			table.setCellInsidePadding(3f);
			table.setDrawGrid(true);
			pageablePdf.drawTable(table);
//			pageablePdf.closeDocument();
//			pageablePdf.save(srcFile);
		}
		
	}
	


	private PageablePdf invokePageablePdf(PDRectangle pdRectangle,String... headInfos) throws IOException {
		PageablePdf pdf = new PageablePdf(pdRectangle.getWidth(), pdRectangle.getHeight());
        setPdfBaseInfo(pdf, headInfos);
        return pdf;
	}


	public void setPdfBaseInfo(PageablePdf pdf, String... headInfos) throws IOException {
		pdf.setContentLeftPadding(this.getLeftMargion());
        pdf.setContentTopPadding(this.getTopMargion());
        pdf.setContentBottomPadding(this.getBottomMargion()); // This include the footer too
        pdf.setIncludePageNumber(this.getIsIncludePageNumber());
        pdf.setCurrentFontSize(this.getFontSize());
        pdf.setCurrentPositionAtStartOfThePage();
        if(headInfos.length <0 || headInfos[0] == null) return;
        for (String headInfo : headInfos) {
			pdf.drawHeading(headInfo);
		}
	}

	/**
	 * 创建打印类对象 (9个参数)
	 * @param leftMargion 左边距
	 * @param topMargion 上边距
	 * @param bottomMargion 下边距
	 * @param isIncludePageNumber 是否包含页码
	 * @param srcFile 来源名称(包含扩展名.pdf)
	 * @param tableColumns 表头的列集合
	 * @param tableContent 表的行信息 二维数组
	 * @param fontSize 字体大小
	 * @param pdRectangle 纸张类别，例如：PDRectangle.A4
	 * @throws IOException 
	 */
	public PageablePdfGenerator(float leftMargion, float topMargion, float bottomMargion, Boolean isIncludePageNumber,
			String srcFile, List<TableColumn> tableColumns, String[][] tableContent, float fontSize,
			PDRectangle pdRectangle) throws IOException {
		this.leftMargion = leftMargion;
		this.topMargion = topMargion;
		this.bottomMargion = bottomMargion;
		this.isIncludePageNumber = isIncludePageNumber;
		this.srcFile = srcFile;
		this.tableColumns = tableColumns;
		this.tableContent = tableContent;
		this.fontSize = fontSize;
		this.pdRectangle = pdRectangle;
		 drawTable(tableColumns, tableContent,invokePageablePdf(this.getPdRectangle()));
	}
	


}
