package com.mk.eap.component.pdfboxtable.table;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Table content for a pdf document.
 *
 * @author 
 */
public class Table implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6962030284160775903L;

	private static final Color DEFAULT_HEADER_BACKGROUND_COLOR = new Color(224, 224, 224);

    private List<TableColumn> columns;
    private String[][] content;
    private Object[][] bodyContent;
    private Float width;
    private boolean drawGrid = true;
    private boolean drawHeaders=true;
    private float cellInsidePadding=3f;
    private Color headerBackgroundColor = DEFAULT_HEADER_BACKGROUND_COLOR;
    private boolean isLandscape;//Rotation mark use landscape orientation or portrait
    private int level=1;
    private String head;

    private TableHeadInfo tableHeadInfo;
    
    private List<TableHeadInfo> tableHeadInfos;
    
    private TableFooterInfo tableFooterInfo ;
    
    private Integer maxLineNumber=5;
    
    

    /**
     * @param columns table columns
     * @param content table content
     */
    public Table(List<TableColumn> columns, String[][] content) {
        super();
        this.columns = columns;
        this.content = content;
    }
    public Table(List<TableColumn> columns, Object[][] content) {
        super();
        this.columns = columns;
        this.bodyContent = content;
    }
    
    

    public Table(List<TableColumn> columns, String[][] content, boolean isLandscape) {
		super();
		this.columns = columns;
		this.content = content;
		this.isLandscape = isLandscape;
	}
    


    public Table(List<TableColumn> columns, String[][] content, String head, List<TableHeadInfo> tableHeadInfos,
			TableFooterInfo tableFooterInfo) {
		super();
		this.columns = columns;
		this.content = content;
		this.head = head;
		this.tableHeadInfos = tableHeadInfos;
		this.tableFooterInfo = tableFooterInfo;
	}
	public Table(List<TableColumn> columns, String[][] content, List<TableHeadInfo> tableHeadInfos,
			TableFooterInfo tableFooterInfo) {
		super();
		this.columns = columns;
		this.content = content;
		this.tableHeadInfos = tableHeadInfos;
		this.tableFooterInfo = tableFooterInfo;
	}
    
	public Table(List<TableColumn> columns, String[][] content, boolean isLandscape, int level,
			List<TableHeadInfo> tableHeadInfos, TableFooterInfo tableFooterInfo) {
		super();
		this.columns = columns;
		this.content = content;
		this.isLandscape = isLandscape;
		this.level = level;
		this.tableHeadInfos = tableHeadInfos;
		this.tableFooterInfo = tableFooterInfo;
	}
	
	public Table(List<TableColumn> columns, String[][] content,String head, boolean isLandscape, int level,
			List<TableHeadInfo> tableHeadInfos, TableFooterInfo tableFooterInfo) {
		super();
		this.columns = columns;
		this.content = content;
		this.isLandscape = isLandscape;
		this.head = head;
		this.level = level;
		this.tableHeadInfos = tableHeadInfos;
		this.tableFooterInfo = tableFooterInfo;
	}
	
    public Table(List<TableColumn> columns, Object[][] bodyContent, String head, List<TableHeadInfo> tableHeadInfos,
			TableFooterInfo tableFooterInfo,Integer ...maxLineNum) {
		super();
		this.columns = columns;
		this.bodyContent = bodyContent;
		this.head = head;
		this.tableHeadInfos = tableHeadInfos;
		this.tableFooterInfo = tableFooterInfo;
		if(maxLineNum != null && maxLineNum.length>0){
			this.setMaxLineNumber(maxLineNum[0]);
		}
	}
	public Table(List<TableColumn> columns, Object[][] bodyContent, List<TableHeadInfo> tableHeadInfos,
			TableFooterInfo tableFooterInfo) {
		super();
		this.columns = columns;
		this.bodyContent = bodyContent;
		this.tableHeadInfos = tableHeadInfos;
		this.tableFooterInfo = tableFooterInfo;
	}
    
	public Table(List<TableColumn> columns, Object[][] bodyContent, boolean isLandscape, int level,
			List<TableHeadInfo> tableHeadInfos, TableFooterInfo tableFooterInfo) {
		super();
		this.columns = columns;
		this.bodyContent = bodyContent;
		this.isLandscape = isLandscape;
		this.level = level;
		this.tableHeadInfos = tableHeadInfos;
		this.tableFooterInfo = tableFooterInfo;
	}
	/**
     * 构造table
     * @param columns 表头信息
     * @param content 数据内容
     * @param isLandscape 是否横向
     * @param level 表头是几级表头，最多支持二级表头
     */
	public Table(List<TableColumn> columns, String[][] content, boolean isLandscape, int level) {
		super();
		this.columns = columns;
		this.content = content;
		this.isLandscape = isLandscape;
		this.level = level;
	}
	
	

/**
 * 多参数构造方法（可以绘制没有表格的内容）
 * @param columns 列集合
 * @param content 二维数组填充数据
 * @param drawGrid 是否绘制Table的边框
 * @param drawHeaders 是否绘制表头
 * @param isLandscape 是否横向打印
 * @param level 表头级数
 */
	public Table(List<TableColumn> columns, String[][] content, boolean drawGrid, boolean drawHeaders,
			boolean isLandscape, int level) {
		super();
		this.columns = columns;
		this.content = content;
		this.drawGrid = drawGrid;
		this.drawHeaders = drawHeaders;
		this.isLandscape = isLandscape;
		this.level = level;
	}
	/**
	 * 多参数构造方法（可以绘制没有表格的内容）
	 * @param columns 列集合
	 * @param content 二维数组填充数据
	 * @param drawGrid 是否绘制Table的边框
	 * @param drawHeaders 是否绘制表头
	 * @param isLandscape 是否横向打印
	 */
		public Table(List<TableColumn> columns, String[][] content, boolean drawGrid, boolean drawHeaders,
				boolean isLandscape) {
			super();
			this.columns = columns;
			this.content = content;
			this.drawGrid = drawGrid;
			this.drawHeaders = drawHeaders;
			this.isLandscape = isLandscape;
		}
		/**
		 * 多参数构造方法（可以绘制没有表格的内容）
		 * @param columns 列集合
		 * @param content 二维数组填充数据
		 * @param drawGrid 是否绘制Table的边框
		 * @param drawHeaders 是否绘制表头
		 * @param isLandscape 是否横向打印
		 * @param level 表头级数
		 * @param tableHeadInfo 头信息：包含标题、头信息
		 */
			public Table(List<TableColumn> columns, String[][] content, boolean drawGrid, boolean drawHeaders,
					boolean isLandscape, int level,TableHeadInfo tableHeadInfo) {
				super();
				this.columns = columns;
				this.content = content;
				this.drawGrid = drawGrid;
				this.drawHeaders = drawHeaders;
				this.isLandscape = isLandscape;
				this.level = level;
				this.tableHeadInfo = tableHeadInfo;
			}



	/**
	 * 构造打印信息的方法，适用于集合类型List<Table>
	 * @param columns 列信息
	 * @param content 传入的数据
	 * @param isLandscape 是否横向
	 * @param level 列的表头等级，最多支持2级
	 * @param tableHeadInfo  表头信息：包含标题、表头信息
	 */
	public Table(List<TableColumn> columns, String[][] content, boolean isLandscape, int level,
			TableHeadInfo tableHeadInfo) {
		super();
		this.columns = columns;
		this.content = content;
		this.isLandscape = isLandscape;
		this.level = level;
		this.tableHeadInfo = tableHeadInfo;
	}



	/**
     * @param column table column
     * @param content table content
     * constructor for building individual rows
     */
    public Table(TableColumn column, String content) {
        super();
        this.columns = Arrays.asList(column);
        this.content = new String[][] {{content}};
    }

    /**
     * @param column table column
     * @param rows table content (only one column)
     */
    public Table(TableColumn column, List<String> rows) {
        super();
        this.columns = Arrays.asList(column);
        this.content = new String[rows.size()][];

        for (int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
            String[] row = new String[1];
            row[0] = rows.get(rowNumber);
            content[rowNumber] = row;
        }

    }
   

    public List<TableColumn> getMaxUnity(){
    	  List<TableColumn> returnMaxColumns = new ArrayList<>() ;
      	for (TableColumn tableColumn : this.getColumns()) {
  			if(tableColumn.getChildColumns().size()>returnMaxColumns.size()){
  				returnMaxColumns=tableColumn.getChildColumns();
  			}
  		}
      	return returnMaxColumns;
    }

    /**
     * @return table width
     */
    public float calculateWidth() {
        float columnsWidth = 0;
        for (TableColumn column : columns) {
            columnsWidth += column.getWidth();
        }
        return columnsWidth;
    }

    /**
     * @return the width
     */
    public Float getWidth() {
        if (width == null) {
            width = calculateWidth();
        }
        return width;
    }

    /**
     * @return the columns
     */
    public List<TableColumn> getColumns() {
        return columns;
    }

    public List<TableColumn> getSubColumns() {
//        return columns;
        List<TableColumn> subColumns = new ArrayList<>();
        for (TableColumn tableColumn : getColumns()) {
			if(tableColumn.getChildColumns() == null){
				subColumns.add(tableColumn);
				tableColumn.setParentColumn(null);
			}else{
				for (TableColumn subColumn : tableColumn.getChildColumns()) {
					subColumns.add(subColumn);
					subColumn.setParentColumn(tableColumn);
				}
			}
		}
        return subColumns;
    } 
    public void modiLevel(){
		for (TableColumn tableColumn : getColumns()) {
			if (tableColumn.getChildColumns() != null) {
				setLevel(2);
				break;
			}
		}		
    	
    }
    

    public void setColumns(List<TableColumn> columns) {
		this.columns = columns;
	}

	/**
     * @return the content
     */
    public String[][] getContent() {
        return content;
    }

    /**
     * @return the drawGrid
     */
    public boolean isDrawGrid() {
        return drawGrid;
    }

    /**
     * @param drawGrid the drawGrid to set
     */
    public void setDrawGrid(boolean drawGrid) {
        this.drawGrid = drawGrid;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Float width) {
        this.width = width;
    }

    /**
     * @return the drawHeaders
     */
    public boolean isDrawHeaders() {
        return drawHeaders;
    }

    /**
     * @param drawHeaders the drawHeaders to set
     */
    public void setDrawHeaders(boolean drawHeaders) {
        this.drawHeaders = drawHeaders;
    }

    /**
     * @param cellInsidePadding the cellInsidePadding to set
     */
    public void setCellInsidePadding(float cellInsidePadding) {
        this.cellInsidePadding = cellInsidePadding;
    }

    /**
     * @return the cellInsidePadding
     */
    public float getCellInsidePadding() {
        return cellInsidePadding;
    }

    /**
     * @param cells list of row cells
     * @return row array
     */
    public static String[] generateRow(String... cells) {
        return cells;
    }

    /**
     * @return the headerBackgroundColor
     */
    public Color getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    /**
     * @param headerBackgroundColor the headerBackgroundColor to set
     */
    public void setHeaderBackgroundColor(Color headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

	public boolean isLandscape() {
		return isLandscape;
	}

	public void setLandscape(boolean isLandscape) {
		this.isLandscape = isLandscape;
	}



	public String getHead() {
		return head;
	}


	/**
	 * 设置表头题目
	 * @param head
	 */
	public void setHead(String head) {
		this.head = head;
	}



	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public TableHeadInfo getTableHeadInfo() {
		return tableHeadInfo;
	}



	public void setTableHeadInfo(TableHeadInfo tableHeadInfo) {
		this.tableHeadInfo = tableHeadInfo;
	}



	public Object[][] getBodyContent() {
		return bodyContent;
	}



	public void setBodyContent(Object[][] bodyContent) {
		this.bodyContent = bodyContent;
	}
	public List<TableHeadInfo> getTableHeadInfos() {
		return tableHeadInfos;
	}
	public void setTableHeadInfos(List<TableHeadInfo> tableHeadInfos) {
		this.tableHeadInfos = tableHeadInfos;
	}
	public TableFooterInfo getTableFooterInfo() {
		return tableFooterInfo;
	}
	public void setTableFooterInfo(TableFooterInfo tableFooterInfo) {
		this.tableFooterInfo = tableFooterInfo;
	}
	public Integer getMaxLineNumber() {
		return maxLineNumber;
	}
	public void setMaxLineNumber(Integer maxLineNumber) {
		this.maxLineNumber = maxLineNumber;
	}
	
	
	
	
    
    

}
