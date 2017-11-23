package com.mk.eap.component.export.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.base.BusinessException;
import com.mk.eap.component.export.itf.IExportExcelService;
import com.mk.eap.utils.StringUtil;
import com.mk.eap.base.DTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
@Service
public class ExportExcelService implements IExportExcelService {

	private static final Logger logger = LoggerFactory.getLogger(ExportExcelService.class);


	private final String emptyString = "";
	
	@Override
	public <TDataDto extends DTO> byte[] exportAll(List<Map<String, String>> headMapList, List<String> pageTitleList, List<List<TDataDto>> dataList,
			String fieldName, int sheetIndex, List<String> unFixCollist) throws BusinessException {
		int allCount = dataList.size();
		if (fieldName == null) {
			throw new BusinessException("80564", "报表文件名称不能为空！");
		}
		if (dataList.isEmpty()) {
			throw new BusinessException("80564", "没有要导出的数据！");
		}
		if (dataList.size() != headMapList.size() || dataList.size() != pageTitleList.size()) {
			throw new BusinessException("80564", "传入数据不一致！");
		}
		if (sheetIndex < 0) {
			sheetIndex = 0;
		}

		ExcelBaseInfoDto<TDataDto> excelBaseInfoDto = new ExcelBaseInfoDto<TDataDto>();
		excelBaseInfoDto.dataList = dataList.get(0);

		excelBaseInfoDto.fieldFullName = fieldName;
		try {
			this.init(unFixCollist, sheetIndex, excelBaseInfoDto);
		} catch (Exception ex) {
			throw new BusinessException("80562", "导出Excel失败【初始化表格失败】！", null, ex);
		}
		for (int i = 0; i < allCount; i++) {
			if (i < allCount - 1) {
				excelBaseInfoDto.workbook.cloneSheet(i);
				excelBaseInfoDto.workbook.setSheetOrder(
						excelBaseInfoDto.workbook.getSheetName(excelBaseInfoDto.workbook.getNumberOfSheets()-1), i + 1);
			}
			excelBaseInfoDto.sheet = excelBaseInfoDto.workbook.getSheetAt(i);
			commitHead(headMapList.get(i), excelBaseInfoDto);
			excelBaseInfoDto.dataList = dataList.get(i);
			String pageTitle = pageTitleList.get(i);
			char[] ac = pageTitle.toCharArray();
			String newTitle = "";
			// 特殊字符屏蔽
			for(char c : ac){
        		if(":/\\?*[]：？／＼＊［］／＼".indexOf(c)>=0){
        			continue;
        		}else{
        			newTitle+= c;
        		}
        	}
			excelBaseInfoDto.workbook.setSheetName(i, newTitle);
			try {
				for (int j = 0; j < excelBaseInfoDto.dataList.size(); j++) {
					setRowFromObject(j, excelBaseInfoDto);
				}
			} catch (Exception ex) {
				throw new BusinessException("80562", "导出Excel失败【写入数据失败】！", null, ex);
			}
		}
		byte[] returnByte = null;

		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			excelBaseInfoDto.workbook.write(byteArrayOutputStream);
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
			returnByte = byteArrayOutputStream.toByteArray();
		} catch (Exception ex) {
			throw new BusinessException("80562", "导出Excel失败【转换数据失败】", null, ex);
		}
		return returnByte;
	}

	@Override
	public <TDataDto extends DTO> byte[] export(Map<String, String> headMap, List<TDataDto> dataList, String fieldName, int sheetIndex)
			throws BusinessException {
		return export(headMap, dataList, fieldName, sheetIndex, null);
	}

	@Override
	public <TDataDto extends DTO> byte[] export(Map<String, String> headMap, List<TDataDto> dataList, String fieldName, int sheetIndex,
			List<String> unFixCollist) throws BusinessException {
		if (fieldName == null) {
			throw new BusinessException("80564", "报表文件名称不能为空！");
		}
		if (sheetIndex < 0) {
			sheetIndex = 0;
		}
		ExcelBaseInfoDto<TDataDto> excelBaseInfoDto = new ExcelBaseInfoDto<TDataDto>();
		excelBaseInfoDto.dataList = dataList;
		excelBaseInfoDto.fieldFullName = fieldName;
		byte[] bytes = null;
		try {
			this.init(unFixCollist, sheetIndex, excelBaseInfoDto);
			commitHead(headMap, excelBaseInfoDto);
			for (int i = 0; i < excelBaseInfoDto.dataList.size(); i++) {
				setRowFromObject(i, excelBaseInfoDto);
			}
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			excelBaseInfoDto.workbook.write(byteArrayOutputStream);
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
			bytes = byteArrayOutputStream.toByteArray();
		} catch (Exception ex) {
			throw new BusinessException("80562", "导出Excel失败！", null, ex);
		}
		return bytes;
	}

	// 构造表头可变信息
	private <TDataDto extends DTO> void commitHead(Map<String, String> headMap, ExcelBaseInfoDto<TDataDto> excelBaseInfoDto) {
		for (FileDataInfo headInfo : excelBaseInfoDto.HeadConfigure) {
			Row row = excelBaseInfoDto.sheet.getRow(headInfo.rowIndex);
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

	// 初始化读取Excel模板数据
	private <TDataDto extends DTO> void init(List<String> unFixColList, int sheetIndex, ExcelBaseInfoDto<TDataDto> excelBaseInfoDto) throws Exception {
		// 读取Excel模板数据
		try {
			InputStream inputStream = this.getClass().getResourceAsStream(excelBaseInfoDto.fieldFullName);
			excelBaseInfoDto.workbook = new HSSFWorkbook(inputStream);
			inputStream.close();
		} catch (IOException ex) {
			throw new BusinessException("80562", "读取Excel模板文件异常！", null, ex);
		}
		if(sheetIndex >= excelBaseInfoDto.workbook.getNumberOfSheets()){
			sheetIndex = excelBaseInfoDto.workbook.getNumberOfSheets() -1;
		}
		try {
			for (int i = excelBaseInfoDto.workbook.getNumberOfSheets() - 1; i >= 0; i--) {
				// 删除Sheet
				if (i == sheetIndex)
					continue;
				excelBaseInfoDto.workbook.removeSheetAt(i);
			}
			excelBaseInfoDto.sheet = excelBaseInfoDto.workbook.getSheetAt(0);
		} catch (Exception ex) {
			throw new BusinessException("80562", "解析Excel模板页签异常！", null, ex);
		}
		// 读取模板配置行信息
		excelBaseInfoDto.headRowCount = (int) excelBaseInfoDto.sheet.getRow(0).getCell(0).getNumericCellValue();
		excelBaseInfoDto.colCount = (int) excelBaseInfoDto.sheet.getRow(0).getCell(1).getNumericCellValue();
		excelBaseInfoDto.headConfiCount = (int) excelBaseInfoDto.sheet.getRow(0).getCell(2).getNumericCellValue();
		excelBaseInfoDto.rowHeight = excelBaseInfoDto.sheet.getRow(1).getHeight();
		// 读取动态列头配置数据
		if (null != unFixColList && !unFixColList.isEmpty()) {
			try {
				excelBaseInfoDto.unFixColRowNo = (int) excelBaseInfoDto.sheet.getRow(0).getCell(3)
						.getNumericCellValue();
				excelBaseInfoDto.headUnFixColCount = (int) excelBaseInfoDto.sheet.getRow(0).getCell(4)
						.getNumericCellValue();
			} catch (Exception ex) {
				excelBaseInfoDto.unFixColRowNo = 0;
				excelBaseInfoDto.headUnFixColCount = 0;
			}
		} else {
			excelBaseInfoDto.unFixColRowNo = 0;
			excelBaseInfoDto.headUnFixColCount = 0;
		}
		// 动态列可用，则调整动态列，隐藏多余的动态列
		if (excelBaseInfoDto.unFixColRowNo > 0 && excelBaseInfoDto.headUnFixColCount > 0) {
			int findCount = 0;
			for (int j = 0; j < unFixColList.size() && j < excelBaseInfoDto.headUnFixColCount; j++) {
				String fieldName = unFixColList.get(j);
				boolean isFind = false;
				for (int i = 0; i < excelBaseInfoDto.headUnFixColCount; i++) {
					Cell cell = excelBaseInfoDto.sheet.getRow(1).getCell(i);
					if (fieldName.equals(cell.getStringCellValue())) {
						// 动态列的列头互换
						Cell cellA = excelBaseInfoDto.sheet.getRow(excelBaseInfoDto.unFixColRowNo - 1).getCell(j);
						Cell cellB = excelBaseInfoDto.sheet.getRow(excelBaseInfoDto.unFixColRowNo - 1).getCell(i);
						exchangeCell(cellA, cellB);
						FileDataInfo fileDataInfo = new FileDataInfo(j, fieldName);
						fileDataInfo.cellStyle = cell.getCellStyle();
						excelBaseInfoDto.bodyConfiguer.add(fileDataInfo);
						cellA = excelBaseInfoDto.sheet.getRow(1).getCell(j);
						cellB = excelBaseInfoDto.sheet.getRow(1).getCell(i);
						exchangeCell(cellA, cellB);
						isFind = true;
						findCount++;
						break;
					}
				}
				if (!isFind) {
					logger.error(String.format("动态列【%s】在模板中未找到对应列", fieldName));
				}
			}
			// 隐藏多余的动态列
			for (int j = findCount; j < excelBaseInfoDto.headUnFixColCount; j++) {
				Cell cell = excelBaseInfoDto.sheet.getRow(excelBaseInfoDto.unFixColRowNo - 1).getCell(j);
				cell.setCellValue("");
				excelBaseInfoDto.sheet.setColumnWidth(j, 0);
			}
		} else {
			excelBaseInfoDto.unFixColRowNo = 0;
			excelBaseInfoDto.headUnFixColCount = 0;
		}
		for (int i = excelBaseInfoDto.headUnFixColCount; i < excelBaseInfoDto.colCount; i++) {
			Cell cell = excelBaseInfoDto.sheet.getRow(1).getCell(i);
			FileDataInfo fileDataInfo = new FileDataInfo(i, cell.getStringCellValue());
			fileDataInfo.cellStyle = cell.getCellStyle();
			excelBaseInfoDto.bodyConfiguer.add(fileDataInfo);
		}
		for (int i = 0; i < excelBaseInfoDto.headConfiCount; i++) {
			Cell cell = excelBaseInfoDto.sheet.getRow(2).getCell(i);
			if (null == cell)
				continue;
			String tmp = cell.getStringCellValue();
			String[] heads = tmp.split(":");
			if (heads.length != 3)
				continue;
			FileDataInfo fileDataInfo = new FileDataInfo(
					Integer.valueOf(heads[0]) - 1 - excelBaseInfoDto.configureRowCount, Integer.valueOf(heads[1]) - 1,
					heads[2]);
			excelBaseInfoDto.HeadConfigure.add(fileDataInfo);
		}
		for (int i = excelBaseInfoDto.configureRowCount; i < excelBaseInfoDto.headRowCount
				+ excelBaseInfoDto.configureRowCount; i++) {
			excelBaseInfoDto.headRowHeightList.add(excelBaseInfoDto.sheet.getRow(i).getHeight());
		}
		// 删除配置行
		excelBaseInfoDto.sheet.shiftRows(excelBaseInfoDto.configureRowCount,
				excelBaseInfoDto.sheet.getLastRowNum()+1,
				-excelBaseInfoDto.configureRowCount);
		for (int i = 0; i < excelBaseInfoDto.headRowCount; i++) {
			excelBaseInfoDto.sheet.getRow(i).setHeight(excelBaseInfoDto.headRowHeightList.get(i));
		}
		// 初始化字段类型数据
		initFieldType(excelBaseInfoDto);
	}

	private void exchangeCell(Cell cellA, Cell cellB) {
		String titleA = cellA.getStringCellValue();
		String titleB = cellB.getStringCellValue();
		CellStyle cellStyleA = cellA.getCellStyle();
		CellStyle cellStyleB = cellB.getCellStyle();
		cellA.setCellValue(titleB);
		cellA.setCellStyle(cellStyleB);
		cellB.setCellValue(titleA);
		cellB.setCellStyle(cellStyleA);
	}

	// 初始化字段类型数据
	private <TDataDto extends DTO> void initFieldType(ExcelBaseInfoDto<TDataDto> excelBaseInfoDto) {

		if (excelBaseInfoDto.dataList.isEmpty())
			return;
		Field[] objfields = excelBaseInfoDto.dataList.get(0).getClass().getDeclaredFields();

		boolean isFind = false;
		for (FileDataInfo fileDataInfo : excelBaseInfoDto.bodyConfiguer) {
			isFind = false;
			for (Field f : objfields) {
				if (f.getName().equals(fileDataInfo.fieldName)) {
					fileDataInfo.fieldType = f.getGenericType().toString();
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				logger.warn(String.format("【%s】未找到对应数据源 ", fileDataInfo.fieldName));
				fileDataInfo.fieldType = this.emptyString;
				fileDataInfo.isErrorOrNoSuchMethod = true;
			}
		}
	}

	// 获取sheet的一行，没有则新增
	private <TDataDto extends DTO> Row getRow(int index, ExcelBaseInfoDto<TDataDto> excelBaseInfoDto) {
		if (index <= excelBaseInfoDto.sheet.getLastRowNum()) {
			excelBaseInfoDto.sheet.shiftRows(index, excelBaseInfoDto.sheet.getLastRowNum(), 1, true, false);
        }
        Row row = excelBaseInfoDto.sheet.createRow(index);
		for (int i = 0; i < excelBaseInfoDto.colCount; i++) {
			if (row.getCell(i) == null) {
				row.createCell(i);
			}
		}
		row.setHeight(excelBaseInfoDto.rowHeight);
		return row;
	}

	// 数据填充Excel行
	public <TDataDto extends DTO> void setRowFromObject(int index, ExcelBaseInfoDto<TDataDto> excelBaseInfoDto) throws Exception {
		// 获取一个行
		Row row = getRow(index + excelBaseInfoDto.headRowCount, excelBaseInfoDto);
		// 获取要输出的数据
		DTO obj = excelBaseInfoDto.dataList.get(index);
		for (FileDataInfo fileDataInfo : excelBaseInfoDto.bodyConfiguer) {
			Cell cell = row.getCell(fileDataInfo.cellIndex);
			cell.setCellStyle(fileDataInfo.cellStyle);
			if (!fileDataInfo.isErrorOrNoSuchMethod) {
				String pre = fileDataInfo.fieldName.substring(0, 1);
				String methodName = "get" + pre.toUpperCase() + fileDataInfo.fieldName.substring(1);
				try {
					Method method = obj.getClass().getDeclaredMethod(methodName);
					Object value = method.invoke(obj);
					if (value == null) {
						continue;
					}
					switch (fileDataInfo.fieldType) {
					case "class java.lang.String":
						cell.setCellValue(value.toString());
						break;
					case "class java.util.Date":
						String strValue = StringUtil.getFormatDateS((Date) value);
						cell.setCellValue(strValue);
						break;
					case "class java.lang.Integer":
					case "int":
						cell.setCellValue((Integer) value);
						break;
					case "class java.lang.Boolean":
					case "boolean":
						cell.setCellValue((Boolean) value);
						break;
					case "class java.lang.Double":
					case "double":
						cell.setCellValue((Double) value);
						break;
					case "class java.lang.Long":
					case "long":
						cell.setCellValue(((Long) value).toString());
						break;
					case "class java.lang.Byte":
					case "byte":
						cell.setCellValue((Byte) value);
						break;
					default:
						logger.warn(String.format("【%s】不支持的字段类型:%s ", fileDataInfo.fieldName, fileDataInfo.fieldType));
						fileDataInfo.isErrorOrNoSuchMethod = true;
					}

				} catch (NoSuchMethodException ex) {
					fileDataInfo.isErrorOrNoSuchMethod = true;
					logger.warn(String.format("【%s】未找到对应的【%s】方法 ", fileDataInfo.fieldName, methodName), ex);
				} catch (Exception ex) {
					logger.warn(String.format("【%s】数据读取异常 ", fileDataInfo.fieldName), ex);
				}
			} else {
				cell.setCellValue(emptyString);
			}
		}
	}

	// 数据字段配置信息
	class FileDataInfo {
		public FileDataInfo(int cellIndex, String fieldName) {
			this.cellIndex = cellIndex;
			this.fieldName = fieldName;
		}

		public FileDataInfo(int rowIndex, int cellIndex, String fieldName) {
			this.rowIndex = rowIndex;
			this.cellIndex = cellIndex;
			this.fieldName = fieldName;
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
	}

	class ExcelBaseInfoDto<TDataDto extends DTO> {
		public ExcelBaseInfoDto() {
			dataList = new ArrayList<>();
			bodyConfiguer = new ArrayList<>();
			HeadConfigure = new ArrayList<>();
			headRowHeightList = new ArrayList<>();
		}

		// Excel数据
		protected List<TDataDto> dataList;
		// Excel配置信息
		protected List<FileDataInfo> bodyConfiguer;
		protected List<FileDataInfo> HeadConfigure;
		// 模板文件名称（带路径）
		protected String fieldFullName;
		// Excel模板Book
		protected Workbook workbook;
		// Excel模板Sheet
		protected Sheet sheet;
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

		/** 行高 */
		protected short rowHeight;

		/** 表头行高 */
		protected List<Short> headRowHeightList;
	}

}
