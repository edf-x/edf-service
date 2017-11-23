package com.mk.eap.component.pdfboxtable.table;

import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

/**
 * Display information for a column in {@link Table}.
 *
 * @author 
 */
public class TableColumn implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7884694291408440694L;
	private String header;
    private float width;
    private TextAlignment alignment = TextAlignment.CENTER;
    private TextVerticalAlignment verticalAlignment = TextVerticalAlignment.MIDDLE;
    private Color backgroundColor;
    private PDFont font;
    private boolean hideGrid;
  
    
    private List<TableColumn> childColumns;
    
    private TableColumn parentColumn;

    private ColumnType columnType = ColumnType.STRING; 
    /**
     * This should be used only when next column have an optional value and only if:
     *  - the text on the next column will not require 2 lines
     *  - next column vertical alignment is BOTTOM
     *
     *  TODO - make it usable without the above restrictions
     */
    private boolean overlapNextColumn;

    /**
     * @param header columns header
     * @param width column width
     */
    public TableColumn(String header, float width) {
        super();
        this.header = header;
        this.width = width;
    }

    /**
     * @param header columns header
     * @param width column width
     */
    public TableColumn(String header, float width, TextAlignment alignment) {
        super();
        this.header = header;
        this.width = width;
        this.alignment = alignment;
    }
    public TableColumn(String header, float width, TextAlignment alignment,TextVerticalAlignment verticalAlignment) {
        super();
        this.header = header;
        this.width = width;
        this.alignment = alignment;
        this.verticalAlignment = verticalAlignment;
    }


    public TableColumn(float width, TextAlignment alignment, TextVerticalAlignment verticalAlignment) {
        this.width = width;
        this.alignment = alignment;
        this.verticalAlignment = verticalAlignment;
    }
    

    public TableColumn(float width, TextAlignment alignment, TextVerticalAlignment verticalAlignment,
			Color backgroundColor) {
		super();
		this.width = width;
		this.alignment = alignment;
		this.verticalAlignment = verticalAlignment;
		this.backgroundColor = backgroundColor;
	}

	/**
     * @return the header
     */
    public String getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return the alignment
     */
    public TextAlignment getAlignment() {
        return alignment;
    }

    /**
     * @param newAlignment the alignment to set
     * @return Same object
     */
    public TableColumn setAlignment(TextAlignment newAlignment) {
        this.alignment = newAlignment;
        return this;
    }

    /**
     * @return the backgroundColor
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param newBackgroundColor the backgroundColor to set
     * @return Same object
     */
    public TableColumn setBackgroundColor(Color newBackgroundColor) {
        this.backgroundColor = newBackgroundColor;
        return this;
    }

    /**
     * @return the font
     */
    public PDFont getFont() {
        return font;
    }

    /**
     * @param font the font to set
     * @return Same object
     */
    public TableColumn setFont(PDFont font) {
        this.font = font;
        return this;
    }

    /**
     * @return the hideGrid
     */
    public boolean isHideGrid() {
        return hideGrid;
    }

    /**
     * @param hideGrid the hideGrid to set
     * @return Same object
     */
    public TableColumn setHideGrid(boolean hideGrid) {
        this.hideGrid = hideGrid;
        return this;
    }

    public TextVerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(TextVerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public boolean isOverlapNextColumn() {
        return overlapNextColumn;
    }

    public void setOverlapNextColumn(boolean overlapNextColumn) {
        this.overlapNextColumn = overlapNextColumn;
    }

	public ColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}

	public List<TableColumn> getChildColumns() {
		return childColumns;
	}

	public void setChildColumns(List<TableColumn> childColumns) {
		this.childColumns = childColumns;
	}

	public TableColumn getParentColumn() {
		return parentColumn;
	}

	public void setParentColumn(TableColumn parentColumn) {
		this.parentColumn = parentColumn;
	}


	
	
    
}
