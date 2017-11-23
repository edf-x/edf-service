package com.mk.eap.component.metadata.print;

import com.mk.eap.component.pdfboxtable.table.TableColumn;
import com.mk.eap.component.pdfboxtable.table.TableHeadInfo;
import com.mk.eap.component.pdfboxtable.table.TextAlignment;
import com.mk.eap.component.pdfboxtable.table.Table;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelMetadataHelper {
	private static final DecimalFormat myFormatter1 = new DecimalFormat(",##0.00");
	{
		fileDataInfos = new ArrayList<>();		
		tableColumns = new ArrayList<>();
		tableHeadInfos = new ArrayList<>();
		tableBodyInfos = new ArrayList<>();
		tableFooterInfos = new ArrayList<>();
		parentInfos = new ArrayList<>();
	}
	public static ExcelMetadataHelper createInstance(){
		return new ExcelMetadataHelper();
	}

	private List<FileDataInfo> fileDataInfos;
	private List<ParentInfo> parentInfos;
	public List<ParentInfo> getParentInfos() {
		return parentInfos;
	}

	public void setParentInfos(List<ParentInfo> parentInfos) {
		this.parentInfos = parentInfos;
	}
	class ChildInfo{
		private int firstCol;
		private int lastCol;
		private int firstRow;
		private int lastRow;
		private String name;
		private int rowIndex;
		private int colIndex;
		private float colWidth;
		private CellStyle cellStyle;
		private ParentInfo parentInfo;
		public int getFirstCol() {
			return firstCol;
		}
		public void setFirstCol(int firstCol) {
			this.firstCol = firstCol;
		}
		public int getLastCol() {
			return lastCol;
		}
		public void setLastCol(int lastCol) {
			this.lastCol = lastCol;
		}
		public int getFirstRow() {
			return firstRow;
		}
		public void setFirstRow(int firstRow) {
			this.firstRow = firstRow;
		}
		public int getLastRow() {
			return lastRow;
		}
		public void setLastRow(int lastRow) {
			this.lastRow = lastRow;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public ParentInfo getParentInfo() {
			return parentInfo;
		}
		public void setParentInfo(ParentInfo parentInfo) {
			this.parentInfo = parentInfo;
		}
		public CellStyle getCellStyle() {
			return cellStyle;
		}
		public void setCellStyle(CellStyle cellStyle) {
			this.cellStyle = cellStyle;
		}
		public int getRowIndex() {
			return rowIndex;
		}
		public void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}
		public int getColIndex() {
			return colIndex;
		}
		public void setColIndex(int colIndex) {
			this.colIndex = colIndex;
		}
		public float getColWidth() {
			return colWidth;
		}
		public void setColWidth(float colWidth) {
			this.colWidth = colWidth;
		}	
	}
	class ParentInfo{
		private int firstCol;
		private int lastCol;
		private int firstRow;
		private int lastRow;
		private String name;
		private int rowIndex;
		private int colIndex;
		private float colWidth;
		private CellStyle cellStyle;
		private List<ChildInfo> childInfos;
		
		public int getFirstCol() {
			return firstCol;
		}

		public void setFirstCol(int firstCol) {
			this.firstCol = firstCol;
		}

		public int getLastCol() {
			return lastCol;
		}

		public void setLastCol(int lastCol) {
			this.lastCol = lastCol;
		}

		public int getFirstRow() {
			return firstRow;
		}

		public void setFirstRow(int firstRow) {
			this.firstRow = firstRow;
		}

		public int getLastRow() {
			return lastRow;
		}

		public void setLastRow(int lastRow) {
			this.lastRow = lastRow;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<ChildInfo> getChildInfos() {
			return childInfos;
		}

		public void setChildInfos(List<ChildInfo> childInfos) {
			this.childInfos = childInfos;
		}

		public CellStyle getCellStyle() {
			return cellStyle;
		}

		public void setCellStyle(CellStyle cellStyle) {
			this.cellStyle = cellStyle;
		}

		public int getRowIndex() {
			return rowIndex;
		}

		public void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}

		public int getColIndex() {
			return colIndex;
		}

		public void setColIndex(int colIndex) {
			this.colIndex = colIndex;
		}

		public float getColWidth() {
			return colWidth;
		}

		public void setColWidth(float colWidth) {
			this.colWidth = colWidth;
		}	
	}
	// 数据字段配置信息
	class FileDataInfo {
		public FileDataInfo() {
			// TODO Auto-generated constructor stub
		}
		
		public FileDataInfo(int cellIndex, String fieldName) {
			this.cellIndex = cellIndex;
			this.fieldName = fieldName;
		}

		public FileDataInfo(int rowIndex, int cellIndex, String fieldName) {
			this.rowIndex = rowIndex;
			this.cellIndex = cellIndex;
			this.fieldName = fieldName;
		}
		

		public FileDataInfo( int cellIndex, String fieldName, CellStyle cellStyle) {
			this.cellIndex = cellIndex;
			this.fieldName = fieldName;
			this.cellStyle = cellStyle;
		}


		/** 模板中对应的行位置 */
		int rowIndex;
		/** 模板中对应的列位置 */
		int cellIndex;
		/** 模板中设置的字段名称-对应DTO属性 */
		String fieldName;
		/** DTO对应属性类型 */
		String fieldType;
		/** 在DTO找get属性是否异常 */
		boolean isErrorOrNoSuchMethod;
		/** 数据单元格格式 */
		CellStyle cellStyle;
		
		
		public int getRowIndex() {
			return rowIndex;
		}

		public void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}

		public int getCellIndex() {
			return cellIndex;
		}

		public void setCellIndex(int cellIndex) {
			this.cellIndex = cellIndex;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldType() {
			return fieldType;
		}

		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}

		public boolean isErrorOrNoSuchMethod() {
			return isErrorOrNoSuchMethod;
		}

		public void setErrorOrNoSuchMethod(boolean isErrorOrNoSuchMethod) {
			this.isErrorOrNoSuchMethod = isErrorOrNoSuchMethod;
		}

		public CellStyle getCellStyle() {
			return cellStyle;
		}

		public void setCellStyle(CellStyle cellStyle) {
			this.cellStyle = cellStyle;
		}
		
		
	}
	
	/**  **/
	private Workbook workbook;
	
	private Sheet sheet;
	/**标题开始的索引 从0开始 **/
	private int headRowIndex;	
	/**输入流**/
	private InputStream fileIS ;
	/**全名包含路径**/
	private String fieldFullName;
	/**模板名称不包括扩展命**/
	private String  templateName;
	/**工作簿索引值从0开始**/
	private int sheetIndex;
	/**标题名称**/
	private String Title;
	/**表头内容信息**/
	private List<FileDataInfo> tableHeadInfos;
	/**表体内容信息**/
	private List<FileDataInfo> tableBodyInfos;
	/**表尾内容信息**/
	private List<FileDataInfo> tableFooterInfos;
	/**实际动态列的数目**/
	private int headRealUnFixColCount;
	/**表头信息（不包含题目及其table的表头）最大索引值**/
	private int headConfigMaxRowIndex;
	/**列信息**/
	private List<TableColumn> tableColumns;
	/**表头信息**/
	private TableHeadInfo tableHeadInfo;
	
	private int nextCellIndex;
	private int nextRowIndex;
	private int maxLevel;
	
	private  FormulaEvaluator evaluator;
	/**
	 * 是否横向打印
	 */
	private boolean isLandscape = true;
	
	// 头信息行数
	protected int headRowCount;
	// 数据总列数
	protected int colCount;
	// 表头变动数据数
	protected int headConfiCount;
	// 配置信息行数
	protected final int configureRowCount = 3;
	// 表头动态列数量
	protected int headUnFixColCount;
	// 表头动态列行号
	protected int unFixColRowNo;

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getNextCellIndex() {
		return nextCellIndex;
	}

	public void setNextCellIndex(int nextCellIndex) {
		this.nextCellIndex = nextCellIndex;
	}

	public int getNextRowIndex() {
		return nextRowIndex;
	}

	public void setNextRowIndex(int nextRowIndex) {
		this.nextRowIndex = nextRowIndex;
	}

	public TableHeadInfo getTableHeadInfo() {
		return tableHeadInfo;
	}

	public void setTableHeadInfo(TableHeadInfo tableHeadInfo) {
		this.tableHeadInfo = tableHeadInfo;
	}

	public List<FileDataInfo> getFileDataInfos() {
		return fileDataInfos;
	}

	public void setFileDataInfos(List<FileDataInfo> fileDataInfos) {
		this.fileDataInfos = fileDataInfos;
	}

	public int getHeadRowCount() {
		return headRowCount;
	}

	public void setHeadRowCount(int headRowCount) {
		this.headRowCount = headRowCount;
	}

	public int getColCount() {
		return colCount;
	}

	public void setColCount(int colCount) {
		this.colCount = colCount;
	}

	public int getHeadConfiCount() {
		return headConfiCount;
	}

	public void setHeadConfiCount(int headConfiCount) {
		this.headConfiCount = headConfiCount;
	}

	public int getHeadUnFixColCount() {
		return headUnFixColCount;
	}

	public void setHeadUnFixColCount(int headUnFixColCount) {
		this.headUnFixColCount = headUnFixColCount;
	}

	public int getUnFixColRowNo() {
		return unFixColRowNo;
	}

	public void setUnFixColRowNo(int unFixColRowNo) {
		this.unFixColRowNo = unFixColRowNo;
	}

	public int getConfigureRowCount() {
		return configureRowCount;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public List<?> getTableHeadInfos() {
		return tableHeadInfos;
	}

	public void setTableHeadInfos(List<FileDataInfo> tableHeadInfos) {
		this.tableHeadInfos = tableHeadInfos;
	}

	public List<FileDataInfo> getTableBodyInfos() {
		return tableBodyInfos;
	}

	public void setTableBodyInfos(List<FileDataInfo> tableBodyInfos) {
		this.tableBodyInfos = tableBodyInfos;
	}

	public List<FileDataInfo> getTableFooterInfos() {
		return tableFooterInfos;
	}

	public void setTableFooterInfos(List<FileDataInfo> tableFooterInfos) {
		this.tableFooterInfos = tableFooterInfos;
	}
	
	public InputStream getFileIS() {
		return fileIS;
	}

	public void setFileIS(InputStream fileIS) {
		this.fileIS = fileIS;
	}

	public String getFieldFullName() {
		return fieldFullName;
	}

	public void setFieldFullName(String fieldFullName) {
		this.fieldFullName = fieldFullName;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public int getHeadRowIndex() {
		return headRowIndex;
	}

	public void setHeadRowIndex(int headRowIndex) {
		this.headRowIndex = headRowIndex;
	}
	public int getHeadRealUnFixColCount() {
		return headRealUnFixColCount;
	}

	public void setHeadRealUnFixColCount(int headRealUnFixColCount) {
		this.headRealUnFixColCount = headRealUnFixColCount;
	}
	
	
	public List<TableColumn> getTableColumns() {
		return tableColumns;
	}

	public void setTableColumns(List<TableColumn> tableColumns) {
		this.tableColumns = tableColumns;
	}
	


	public int getHeadConfigMaxRowIndex() {
		return headConfigMaxRowIndex;
	}

	public void setHeadConfigMaxRowIndex(int headConfigMaxRowIndex) {
		this.headConfigMaxRowIndex = headConfigMaxRowIndex;
	}

	/**
	 * 通过导入模板元数据的方式得到打印字节数组
	 * @param headMap：表头信息
	 * @param dataList:表体填充信息
	 * @param templateName：导出模板名称(不包含扩展名)
	 * @param sheetIndex：模板对应工作簿索引，从0开始
	 * @param unFixCols:变动列 如果存在：把属性字段构造，如果不存在：null,List集合中的String是有顺序的，决定了前后
	 * @param 可变参数,现在只支持一个参数，是否横向打印
	 * @throws Exception 
	 * @return：返回打印的字节数组 byte[]
	 */
	public Map<TableHeadInfo,Table> printFromMetadata(Map<String, String> headMap, List<?> dataList, String templateName, int sheetIndex,
													  List<String> unFixCols, boolean ... param) throws Exception {
		if (templateName == null) {
			throw new Exception("报表文件名称不能为空！");
		}
		if (sheetIndex < 0) {
			sheetIndex = 0;
		}
		initData(sheetIndex, unFixCols,templateName,param);
		commitFileDataInfos();
		updateHead(headMap, fileDataInfos);
		buildHeadInfos();
		setMaxLevel(getHeadRowCount()+configureRowCount-1-getHeadConfigMaxRowIndex());
		createRelation();
		Map<TableHeadInfo,Table> map = new HashMap<>();
		map.put(getTableHeadInfo(), createTableColumns(dataList));
		return map;

	}
	public List<Table> printFromMetadata(List<Map<String, String>> headMaps,List<?> dataLists, String templateName, int sheetIndex,
			List<String> unFixCols,boolean isSampPageContinuePrint,boolean ... param) throws Exception {
		if (templateName == null) {
			throw new Exception("报表文件名称不能为空！");
		}
		if (sheetIndex < 0) {
			sheetIndex = 0;
		}
		int length = headMaps.size();
		initData(sheetIndex, unFixCols,templateName,param);
		commitFileDataInfos();
		List<Table> tables = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			if (i < length - 1) {
				workbook.cloneSheet(i);
				workbook.setSheetOrder(
						workbook.getSheetName(workbook.getNumberOfSheets()-1), i + 1);
			}
			this.sheet = workbook.getSheetAt(i);
			updateHead(headMaps.get(i), fileDataInfos);
			buildHeadInfos();
			setMaxLevel(getHeadRowCount()+configureRowCount-1-getHeadConfigMaxRowIndex());
			createRelation();
			tables.add(createTableColumns((List<?>)dataLists.get(i),getTableHeadInfo()));
		}

		return tables;

	}

	/**
	 * 创建主子关系
	 */
	private void createRelation() {
		int firstRowIndex = getHeadConfigMaxRowIndex() + 1;
		int lastRowIndex = getHeadRowCount()+configureRowCount-1;
		Row row = this.sheet.getRow(firstRowIndex);
		parentInfos.clear();
		for (int colIndex = 0; colIndex < this.getColCount(); colIndex++) {
			ParentInfo pInfo = new ParentInfo();
			if (getMaxLevel() > 1) {
				int sheetMergeCount = sheet.getNumMergedRegions();
				for (int i = 0; i < sheetMergeCount; i++) {
					CellRangeAddress ca = sheet.getMergedRegion(i);
					int firstColumn = ca.getFirstColumn();
					int lastColumn = ca.getLastColumn();
					int firstRow = ca.getFirstRow();
					int lastRow = ca.getLastRow();
					if (firstRowIndex >= firstRow && firstRowIndex <= lastRow && !this.sheet.isColumnHidden(colIndex)) {
						if (colIndex >= firstColumn && colIndex <= lastColumn) {
							pInfo.childInfos = new ArrayList<>();
							pInfo.firstCol = firstColumn;
							pInfo.firstRow = firstRow;
							pInfo.lastCol = lastColumn;
							pInfo.lastRow = lastRow;
							pInfo.cellStyle = row.getCell(colIndex).getCellStyle();
							pInfo.colIndex = colIndex;
							pInfo.rowIndex = firstRowIndex;
							for (int j = firstColumn; j <= lastColumn; j++) {
								pInfo.colWidth += (this.sheet.getColumnWidth(j) / 256 * 6.5);
							}

							pInfo.name = getCellValue(row.getCell(colIndex));
							parentInfos.add(pInfo);
							colIndex = (pInfo.lastCol);
							break;

						}
					}
				}
			} else if(!this.sheet.isColumnHidden(colIndex)) {
				
				pInfo.childInfos = new ArrayList<>();
				pInfo.firstCol = colIndex;
				pInfo.firstRow = firstRowIndex;
				pInfo.lastCol = colIndex;
				pInfo.lastRow = firstRowIndex;
				pInfo.cellStyle = row.getCell(colIndex).getCellStyle();
				pInfo.colIndex = colIndex;
				pInfo.rowIndex = firstRowIndex;
				pInfo.colWidth = (float) (this.sheet.getColumnWidth(colIndex) / 256 * 6.5);
				pInfo.name = getCellValue(row.getCell(colIndex));
				parentInfos.add(pInfo);
				colIndex = (pInfo.lastCol);
			}
		}
		if (getMaxLevel() > 1) {
			createChildRelation();
		}

	}

	private void createChildRelation() {
		int firstRowIndex = getHeadConfigMaxRowIndex() + 2;
		int lastRowIndex = getHeadRowCount()+configureRowCount-1;
		Row row = this.sheet.getRow(firstRowIndex);
		for (FileDataInfo fileDataInfo : this.getTableBodyInfos()) {
			for (ParentInfo parentInfo : parentInfos) {
				int cellIndex = fileDataInfo.cellIndex;
				if(cellIndex>=parentInfo.firstCol&&cellIndex<=parentInfo.lastCol&&StringUtils.isNotEmpty(row.getCell(cellIndex).getStringCellValue())){
					ChildInfo cInfo = new ChildInfo();
					cInfo.setParentInfo(parentInfo);
					cInfo.cellStyle = fileDataInfo.getCellStyle();
					cInfo.colIndex = cellIndex;
					cInfo.rowIndex = firstRowIndex;
					cInfo.colWidth = (float) (this.sheet.getColumnWidth(cellIndex)/256*6.5);
					cInfo.name = getCellValue(row.getCell(cellIndex));
					parentInfo.childInfos.add(cInfo);
					break;
				}
			}
		}

	}

	private Table  createTableColumns(List<?> dataList) {
		String[][] content = buildTableContent(dataList);
		Table table = new Table(tableColumns, content, isLandscape());
		return table;
	}
	private Table  createTableColumns(List<?> dataList,TableHeadInfo tableHeadInfo) {
		String[][] content = buildTableContent(dataList);
		List<TableHeadInfo> tableHeadInfos = new ArrayList<>();
		tableHeadInfos.add(tableHeadInfo);
		Table table = new Table(tableColumns, content,getTitle() ,isLandscape(), getMaxLevel(),tableHeadInfos, null);
		return table;
	}
	/**
	 * 构造table内容
	 * @param dataList
	 * @return
	 */
	private String[][] buildTableContent(List<?> dataList) {
		tableColumns.clear();
		for (ParentInfo parentInfo : parentInfos) {
			TextAlignment textAlignment = getTextAlignment(parentInfo.colIndex);
			TableColumn parentColumn =new TableColumn(parentInfo.getName(),parentInfo.getColWidth(), textAlignment);
			if(parentInfo.childInfos != null && parentInfo.getChildInfos().size()>0){
				List<TableColumn> childColumns = new ArrayList<>();
				for (ChildInfo child : parentInfo.getChildInfos()) {
					TableColumn childColumn = new TableColumn(child.getName(),child.getColWidth(), textAlignment);
					childColumns.add(childColumn);
				}
				parentColumn.setChildColumns(childColumns);
			}
			tableColumns.add(parentColumn);
		}
		String[][] content = new String[dataList.size()][];
		int rowIndex=0;
		for (Object object : dataList) {
			int colIndex =0;
			content[rowIndex] = new String[this.getTableBodyInfos().size()];
			for (FileDataInfo fileDataInfo : this.getTableBodyInfos()) {
				//跨进程调用就会转换成Map
				if(object instanceof Map){
					Object value = ((Map<?, ?>)object).get(fileDataInfo.fieldName);
					content[rowIndex][colIndex++]=(value==null?"":value.toString());
				}else{
					String pre = fileDataInfo.fieldName.substring(0, 1);
					String methodName = "get" + pre.toUpperCase() + fileDataInfo.fieldName.substring(1);
					try {
						Method method = object.getClass().getDeclaredMethod(methodName);
						Object value = method.invoke(object);
						content[rowIndex][colIndex++]=(value==null?"":value.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
			rowIndex++;
		}
		return content;
	}

	private TextAlignment getTextAlignment(int columnIndex) {
		CellStyle  cellStyle=    this.sheet.getRow(1).getCell(columnIndex).getCellStyle();
		 TextAlignment textAlignment =TextAlignment.LEFT;
		 if(cellStyle.getAlignment() == CellStyle.ALIGN_LEFT){
			 textAlignment = TextAlignment.LEFT;
		 }else if (cellStyle.getAlignment() == CellStyle.ALIGN_CENTER){
			 textAlignment = TextAlignment.CENTER;
		 } else if (cellStyle.getAlignment() == CellStyle.ALIGN_RIGHT){
			 textAlignment = TextAlignment.RIGHT;
		 }
		return textAlignment;
	}

	private void initData(int sheetIndex, List<String> unFixCols,String templateName,boolean ... param) throws IOException {
		setFieldFullName(templateName);
//		setFieldFullName("/balanceSumAuxRpt.xls");
		this.setFileIS(this.getClass().getResourceAsStream(getFieldFullName()));
		this.setWorkbook( new HSSFWorkbook(this.fileIS));
		this.setEvaluator(this.getWorkbook().getCreationHelper().createFormulaEvaluator());
		this.fileIS.close();
		this.setSheetIndex(sheetIndex);
		this.setSheet(this.workbook.getSheetAt(this.sheetIndex));
		
		setHeadRowCount((int)this.sheet.getRow(0).getCell(0).getNumericCellValue());
		setColCount((int)this.sheet.getRow(0).getCell(1).getNumericCellValue());
		setHeadConfiCount((int)this.sheet.getRow(0).getCell(2).getNumericCellValue());
		setHeadRowIndex(getConfigureRowCount());
		if(null != unFixCols && unFixCols.size()>0){
			setUnFixColRowNo((int)this.sheet.getRow(0).getCell(3).getNumericCellValue());
			setHeadUnFixColCount((int)this.sheet.getRow(0).getCell(4).getNumericCellValue());
			setHeadRealUnFixColCount(unFixCols.size());
		}else{
			setUnFixColRowNo(0);
			setHeadUnFixColCount(0);
			setHeadRealUnFixColCount(0);
		}
//		for (int i = 0; i < getColCount(); i++) {
//			if(getHeadRealUnFixColCount()>0){
//				for (int unFixCol = 0; unFixCol < getHeadRealUnFixColCount(); unFixCol++) {
//					Cell cell = this.sheet.getRow(1).getCell(i);
//					if(cell.getStringCellValue().equals(unFixCols.get(unFixCol))){
//						FileDataInfo fileDataInfo = new FileDataInfo(cell.getColumnIndex(), cell.getStringCellValue(), cell.getCellStyle());
//						this.tableBodyInfos.add(fileDataInfo);
//						break;
//					}
//				}
//				if(this.tableBodyInfos.size() <getHeadRealUnFixColCount()){
//					continue;
//				}
//				for (int fixCol = getHeadUnFixColCount(); fixCol < getColCount(); fixCol++) {
//					Cell cell = this.sheet.getRow(1).getCell(fixCol);
//					FileDataInfo fileDataInfo = new FileDataInfo(cell.getColumnIndex(), cell.getStringCellValue(), cell.getCellStyle());
//					this.tableBodyInfos.add(fileDataInfo);
//				}
//			}else{
//				Cell cell = this.sheet.getRow(1).getCell(i);
//				FileDataInfo fileDataInfo = new FileDataInfo(cell.getColumnIndex(), cell.getStringCellValue(), cell.getCellStyle());
//				this.tableBodyInfos.add(fileDataInfo);
//			}

		if(getHeadRealUnFixColCount()>0){
			for (int unFixCol = 0; unFixCol < getHeadRealUnFixColCount(); unFixCol++) {
				for (int i = 0; i < getColCount(); i++) {
					Cell cell = this.sheet.getRow(1).getCell(i);
					if(cell.getStringCellValue().equals(unFixCols.get(unFixCol))){
						FileDataInfo fileDataInfo = new FileDataInfo(cell.getColumnIndex(), cell.getStringCellValue(), cell.getCellStyle());
						this.tableBodyInfos.add(fileDataInfo);
						break;
					}
				}
			}
			for (int fixCol = getHeadUnFixColCount(); fixCol < getColCount(); fixCol++) {
				Cell cell = this.sheet.getRow(1).getCell(fixCol);
				FileDataInfo fileDataInfo = new FileDataInfo(cell.getColumnIndex(), cell.getStringCellValue(), cell.getCellStyle());
				this.tableBodyInfos.add(fileDataInfo);
			}
			for (int i = 0; i < getHeadUnFixColCount(); i++) {
				Cell cell = this.sheet.getRow(1).getCell(i);
				if(!unFixCols.contains(cell.getStringCellValue())){
					this.sheet.setColumnHidden(i, true);
				}
			}
			
		}else{
			for (int i = 0; i < getColCount(); i++) {
				Cell cell = this.sheet.getRow(1).getCell(i);
				FileDataInfo fileDataInfo = new FileDataInfo(cell.getColumnIndex(), cell.getStringCellValue(), cell.getCellStyle());
				this.tableBodyInfos.add(fileDataInfo);
			}
		}
		if(param.length >0){
			setLandscape(param[0]);
		}

	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * @param sheet:特定工作簿
	 * @param rowIndex：行下標
	 * @param columnIndex ：列下標
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isMergedRegion(Sheet sheet, int rowIndex, int columnIndex) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (rowIndex >= firstRow && rowIndex <= lastRow) {
				if (columnIndex >= firstColumn && columnIndex <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	

	/**   
	* 获取合并单元格的值   
	* @param sheet   
	* @param row   
	* @param column   
	* @return   
	*/
	public String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					setNextCellIndex(lastColumn);
					setNextRowIndex(lastRow);
					return getCellValue(fCell);
				}
			}
		}
		setNextCellIndex(0);
		setNextRowIndex(0);
		return null;
	}

	/**   
	* 获取单元格的值   
	* @param cell   
	* @return   
	*/
	public String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

			return String.valueOf(cell.getNumericCellValue());
		} 
		return "";
	}  
	 //获取单元格各类型值，返回字符串类型
    private  String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType=cell.getCellType();
        if(cellType==Cell.CELL_TYPE_FORMULA){ //表达式类型
            cellType=evaluator.evaluate(cell).getCellType();
        }
         
        switch (cellType) {
        case Cell.CELL_TYPE_STRING: //字符串类型
            cellValue= cell.getStringCellValue().trim();
            cellValue=StringUtils.isEmpty(cellValue) ? "" : cellValue; 
            break;
        case Cell.CELL_TYPE_BOOLEAN:  //布尔类型
            cellValue = String.valueOf(cell.getBooleanCellValue()); 
            break; 
        case Cell.CELL_TYPE_NUMERIC: //数值类型
             if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                 cellValue =    new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
             } else {  //否
                 cellValue = myFormatter1.format(cell.getNumericCellValue()); 
             } 
            break;
        default: //其它类型，取空串吧
            cellValue = "";
            break;
        }
        return cellValue;
    }

	private void buildHeadInfos() {
		setTitle(this.sheet.getRow(getHeadRowIndex()).getCell(0).getStringCellValue());
		List<TableHeadInfo> headInfos = new ArrayList<>();
		int headStartRowIndex = getHeadRowIndex()+1;
		int headEndRowIndex = getHeadConfigMaxRowIndex();
		boolean isRightFieldName = false;
		for (int i = headStartRowIndex; i <=headEndRowIndex; i++) {
			Row row = this.sheet.getRow(i);
			int colCount = getColCount();
			for (int j = 0; j < colCount; j++) {
				Cell cell = row.getCell(j);
				if(cell == null)continue;
				String fieldName =getMergedRegionValue(this.sheet, row.getRowNum(), cell.getColumnIndex());
					
				if(StringUtils.isNotEmpty(fieldName)){
					j=getNextCellIndex();
				}else{
					fieldName =  cell.getStringCellValue();
				}				  
				if(StringUtils.isNotEmpty(fieldName)){
				  CellStyle  cellStyle =	cell.getCellStyle();
				  if( cellStyle.getAlignment() == CellStyle.ALIGN_LEFT)
				  {
					  TableHeadInfo tableHead = new TableHeadInfo(TextAlignment.LEFT, fieldName);
					  headInfos.add(tableHead);
				  }else if( cellStyle.getAlignment() == CellStyle.ALIGN_CENTER){
					  TableHeadInfo tableHead = new TableHeadInfo(TextAlignment.CENTER, fieldName);
					  headInfos.add(tableHead);
				  }else if( cellStyle.getAlignment() == CellStyle.ALIGN_RIGHT){
					  isRightFieldName = true;
					  TableHeadInfo tableHead = new TableHeadInfo(TextAlignment.RIGHT, fieldName);
					  headInfos.add(tableHead);
				  }else{
					  TableHeadInfo tableHead = new TableHeadInfo(TextAlignment.LEFT, fieldName);
					  headInfos.add(tableHead);
				  }
				} 
			}
		}
			if(!isRightFieldName){
			  TableHeadInfo tableHead = new TableHeadInfo(TextAlignment.RIGHT, "");
			  headInfos.add(tableHead);
			}
		setTableHeadInfo(new TableHeadInfo(getTitle(), headInfos));
	}

	private void commitFileDataInfos() {
		Row headConfigInfo = this.sheet.getRow(2);
		int headMaxIndex =0;
		for (int i = 0; i < headConfiCount; i++) {
			Cell cell = headConfigInfo.getCell(i);
			if (null == cell)
				continue;
			String tmp = cell.getStringCellValue();
			String[] heads = tmp.split(":");
			if (heads.length != 3)
				continue;
			FileDataInfo fileDataInfo = new FileDataInfo(
					Integer.valueOf(heads[0]) - 1, Integer.valueOf(heads[1]) - 1,
					heads[2]);
			fileDataInfos.add(fileDataInfo);
			if((Integer.valueOf(heads[0]) - 1)>headMaxIndex){
				headMaxIndex =Integer.valueOf(heads[0]) - 1;
			}
		}
		setHeadConfigMaxRowIndex(headMaxIndex==0?configureRowCount:headMaxIndex);
	}
	// 构造表头可变信息
	private void updateHead(Map<String, String> headMap, List<FileDataInfo> fileDataInfos) {
		if(headMap == null) return;
		for (FileDataInfo headInfo : fileDataInfos) {
			Row row = this.sheet.getRow(headInfo.rowIndex);
			if (null == row)
				continue;
			Cell cell = row.getCell(headInfo.cellIndex);
			String cellValue = cell.getStringCellValue();
			if (null == cellValue)
				continue;
			String replaceValue = headMap.get(headInfo.fieldName);
			if (null != replaceValue)
				cellValue = cellValue.replace(String.format("{%s}", headInfo.fieldName), replaceValue);
			cell.setCellValue(cellValue);
		}
	}
	public static void main(String[] args) throws Exception {
		ExcelMetadataHelper helper =createInstance();
		List<String > l = new ArrayList<>();
		l.add("accountCode");
		l.add("accountName");
		l.add("departmentName");
		helper.printFromMetadata(null, null, "balanceSumAuxRpt", 0, l);
		
	}

	public boolean isLandscape() {
		return isLandscape;
	}

	public void setLandscape(boolean isLandscape) {
		this.isLandscape = isLandscape;
	}

	public FormulaEvaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(FormulaEvaluator evaluator) {
		this.evaluator = evaluator;
	}




	
	
	
	

}
