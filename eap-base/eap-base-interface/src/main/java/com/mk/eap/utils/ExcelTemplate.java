package com.mk.eap.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel 导入导出抽象基类定义
 * @author gaox
 *
 */
public abstract class ExcelTemplate {

    public static final String XLS_EXTENSION = ".xls";
    
    public static final String XLSX_EXTENSION = ".xlsx";
    
    /** 工作表索引，从 0 开始 */
    protected int sheetIndex = 0;
    
    /** 数据起始行索引，从 0 开始 */
    protected int dataStartRowIndex = 0;
    
    /** 当前处理行索引 */
    protected int currentRowIndex = 0;

    /** 列模板信息 */
    protected List<ColumnTemplate> columns = new ArrayList<>();
    
    /** 当前处理的 excel */
    protected Workbook workbook;
    
    /** 当前处理的工作簿 */
    protected Sheet currentSheet;
    
    /** 是否已经初始化 */
    protected boolean isInited = false;
    
    /** 合计行判断标识 */
    protected String dataMark = "合计";
    
    /** 合计行标识所在列 */
    protected int dataMarkColIndex = 0;
    
    /** excel版本 */
    protected boolean isXLS = true;
    
    /**
     * 初始化
     */
    protected void init() {
        if (!isInited) {
            initColumns();
        }
        
        isInited = true;
    }
    
    /**
     * 初始化列模板信息
     */
    protected void initColumns() {
        columns = new ArrayList<>();
    }
    
    /**
     * 返回指定单元格对应的合并单元格信息
     * @param cell 
     * @return 合并单元格区域的起始单元格，如果不是合并单元格，返回本身
     */
    protected Cell getMergedRegionCell(Cell cell) {
        Cell result = cell;
        if (result == null) {
            return result;
        }
        int colIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        int mergeCount = currentSheet.getNumMergedRegions();
        for (int index = 0; index < mergeCount; index++) {
            CellRangeAddress mergedRegion = currentSheet.getMergedRegion(index);
            int firstCol = mergedRegion.getFirstColumn();
            int lastCol = mergedRegion.getLastColumn();
            int firstRow = mergedRegion.getFirstRow();
            int lastRow = mergedRegion.getLastRow();
            
            if (rowIndex >= firstRow && rowIndex <= lastRow && colIndex >= firstCol && colIndex <= lastCol) {
                Row row = currentSheet.getRow(firstRow);
                result = row.getCell(firstCol);
            }
        }
        return result;
    }
    
    protected Comment getComment(Cell cell) {
        Drawing patriarch = currentSheet.createDrawingPatriarch();
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setDx1(0);
        anchor.setDy1(0);
        anchor.setDx2(0);
        anchor.setDy2(0);
        anchor.setCol1(cell.getColumnIndex());
        anchor.setRow1(cell.getRowIndex());
        anchor.setCol2(cell.getColumnIndex() + 4);
        anchor.setRow2(cell.getRowIndex() + 3);
        Comment comment = patriarch.createCellComment(anchor);
        return comment;
    }
    
    protected Map<Integer, CellStyle> colErrorStyle = new HashMap<>();
    protected CellStyle getErrorStyle(Cell cell, short backgroundColor) {
        CellStyle style;
        int colIndex = cell.getColumnIndex();
        if (colErrorStyle.containsKey(colIndex)) {
            style = colErrorStyle.get(colIndex);
        } else {
            style = workbook.createCellStyle();
            if (cell.getCellStyle() != null) {
                style.cloneStyleFrom(cell.getCellStyle());
            }
            style.setFillForegroundColor(backgroundColor);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            colErrorStyle.put(colIndex, style);
        }
        return style;
    }
    
    /**
     * excel 导入导出列模板定义
     * @author gaox
     *
     */
    public static class ColumnTemplate {

        /** 标题 */
        protected String title;
        /** 对应 excel 列索引 */
        protected int colIndex;
        /** 对应 vo 中属性名称 */
        protected String fieldName;
        /** 对应 vo 中属性的类型 */
        protected FieldType fieldType = FieldType.String;
        /** 是否允许为空 */
        protected boolean nullable = true;
        /** 是否允许为负数 */
        protected boolean allowNegative = true;
        /** vo 使用的公式 */
        protected String formula;//TODO 表达式解析 逆波兰表达式 "a+b-c"
        /** excel 公式 */
        protected String excelFormula;
        
        public ColumnTemplate() {
            // do nothing
        }
        
        public ColumnTemplate(String title, int colIndex, String fieldName, FieldType fieldType) {
            this();
            this.title = title;
            this.colIndex = colIndex;
            this.fieldName = fieldName;
            this.fieldType = fieldType;
        }
        
        /**
         * @return 标题
         */
        public String getTitle() {
            return title;
        }
        /**
         * @param title 标题
         */
        public void setTitle(String title) {
            this.title = title;
        }
        
        /**
         * @return 对应 excel 列索引
         */
        @JsonIgnore
        public int getColIndex() {
            return colIndex;
        }
        /**
         * @param colIndex 对应 excel 列索引
         */
        public void setColIndex(int colIndex) {
            this.colIndex = colIndex;
        }
        
        /**
         * @return 对应 vo 中属性名称 
         */
        public String getFieldName() {
            return fieldName;
        }
        /**
         * @param fieldName 对应 vo 中属性名称 
         */
        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
        
        /**
         * @return 对应 vo 中属性的类型
         */
        @JsonIgnore
        public FieldType getFieldType() {
            return fieldType;
        }
        /**
         * @param fieldType 对应 vo 中属性的类型
         */
        public void setFieldType(FieldType fieldType) {
            this.fieldType = fieldType;
        }
        
        /**
         * @return 是否允许为空 
         */
        public boolean isNullable() {
            return nullable;
        }
        /**
         * @param nullable 是否允许为空 
         */
        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }
        
        /**
         * @return 是否允许为负数
         */
        public boolean isAllowNegative() {
            return allowNegative;
        }
        /**
         * @param allowNegative 是否允许为负数
         */
        public void setAllowNegative(boolean allowNegative) {
            this.allowNegative = allowNegative;
        }
        
        /**
         * @return vo 使用的公式
         */
        public String getFormula() {
            return formula;
        }
        /**
         * @param formula vo 使用的公式
         */
        public void setFormula(String formula) {
            this.formula = formula;
        }
        
        /**
         * @return excel 公式
         */
        public String getExcelFormula() {
            return excelFormula;
        }
        /**
         * @param excelFormula excel 公式
         */
        public void setExcelFormula(String excelFormula) {
            this.excelFormula = excelFormula;
        }
    }
    
    /**
     * 字段类型
     */
    public enum FieldType {
        
        /** 文本 */
        String,
        
        /** 小数 */
        Double,
        
        /** 日期 */
        Date,
        
        /** 百分比 */
        Percent,
        
        /** 整数 */
        Int,
    }
}
