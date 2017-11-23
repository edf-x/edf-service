package com.mk.eap.component.pdfboxtable.pdf;

import com.mk.eap.component.common.pdfbox.template.TemplateTypeEnum;
import com.mk.eap.component.pdfboxtable.table.TableColumn;
import com.mk.eap.component.pdfboxtable.table.TextAlignment;
import com.mk.eap.component.pdfboxtable.table.TextVerticalAlignment;
import com.mk.eap.component.pdfboxtable.table.Table;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * Pdf document implementation of PdfBox Document.
 *
 * @author 
 */
public class PageablePdf extends PDDocument {
    private static final float FONT_FACTOR = 1000f;
    private static final float POINTS_PER_INCH = 72f;
    private static final float MM_PER_INCH = 25.4f;
    private static final float MM_TO_POINTS = POINTS_PER_INCH / MM_PER_INCH; // 2.83
    private static final float DEFAULT_FONT_SIZE = 10f;
    private static final float DEFAULT_CONTENT_SIZE = 9f;
    private static final float DEFAULT_HEADING_FONT_SIZE = 16f;
    private static final float DEFAULT_PADDING = 5f * MM_TO_POINTS;
    private static final float DEFAULT_FOOTER_BOTTOM_PADDING = 5f * MM_TO_POINTS;
    private static final float DEFAULT_FOOTER_FONT_SIZE = 8f;
    private static final PDFont DEFAULT_FOOTER_FONT = PDType1Font.HELVETICA_OBLIQUE;

    private PDFont fontBold = PDType1Font.HELVETICA_BOLD;
    private PDFont fontNormal = PDType1Font.HELVETICA;
    private PDFont currentFont = null;//PDType1Font.HELVETICA;
    private float currentFontSize = DEFAULT_CONTENT_SIZE; // DEFAULT_FONT_SIZE;
    private float headingFontSize = DEFAULT_HEADING_FONT_SIZE;
    private PDFont headingFont = PDType1Font.HELVETICA_BOLD;
    private float headingBottomPadding = DEFAULT_PADDING;
    private float headingTopPadding = 5f;//DEFAULT_PADDING;
    private float paragraphPadding = DEFAULT_PADDING;
    private float footerBottomPadding = DEFAULT_FOOTER_BOTTOM_PADDING;
    private float footerFontSize = DEFAULT_FOOTER_FONT_SIZE;
    private PDFont footerFont =DEFAULT_FOOTER_FONT;

    private boolean includePageNumber;

    private float currentPositionX;
    private float currentPositionY;
    
    private float nextPositionY;

    private PDPage currentPage;
    private PDPageContentStream currentPageContentStream;
    private float contentTopPadding=5f;
    private float contentRightPadding;
    private float contentBottomPadding=40f;
    private float contentLeftPadding=20f;
    
    /**是否在同页上连续打印*/
    private boolean isSampPageContinuePrint = true;


    private float pageHeight;
    private float pageWidth;
    
    private boolean isLandscape ;

    private List<String> footerLines;
    
    private float rowHeight=0f;//行高度
    
    private Integer maxLineNum=5;
    
    //是否需要承前过次页
    private boolean isNeedForwardAndOverPage = false;
    
    private static final String FORWORDPAGE="承前页";
    
    private static final String  OVERPAGE ="过次页";
    
    private Map<String, String> balanceMap =new HashMap<>();

    public Map<String, String> getBalanceMap() {
		return balanceMap;
	}

	public void setBalanceMap(Map<String, String> balanceMap) {
		this.balanceMap = balanceMap;
	}
	

	public boolean isNeedForwardAndOverPage() {
		return isNeedForwardAndOverPage;
	}

	public void setNeedForwardAndOverPage(boolean isNeedForwardAndOverPage) {
		this.isNeedForwardAndOverPage = isNeedForwardAndOverPage;
	}
	



	/**
     *  Create a new default document
     *  
     */
    public PageablePdf() {
		this(PDRectangle.A4.getWidth(),PDRectangle.A4.getHeight());
	}

    /**
     * Create a new document specifying page size.
     *
     * @param pageWidth page with in points
     * @param pageHeight page height in points
     */
    public PageablePdf(float pageWidth, float pageHeight) {
        this.pageWidth = pageWidth;
        this.pageHeight = pageHeight;
    }

    /**
     * Create a new document from a COSDocument
     */
    public PageablePdf(COSDocument document) {
        super(document);

        // set page with and height from the first page
        if (getDocumentCatalog().getPages().getCount()>0) {
            PDPage firstPage = (PDPage) getDocumentCatalog().getPages().iterator().next();
            pageHeight = firstPage.getMediaBox().getHeight();
            pageWidth = firstPage.getMediaBox().getWidth();
        }
    }
    private PDFont getFont() throws IOException{
//    	return PDType0Font.load(this, new File("C:\\Windows\\Fonts\\simkai.ttf"));//STSONG.TTF
    	if(getCurrentFont() == null){
    		setCurrentFont(PDType0Font.load(this,Thread.currentThread().getClass().getResourceAsStream("/fonts/msyh.ttf"), true));
//    		setCurrentFont(PDType0Font.load(this,Thread.currentThread().getClass().getResourceAsStream("/.fonts/msyh.ttf"), true));
    	}
    	return getCurrentFont();
    }
    /**
     * Check if there is enough space for a new line, is not, than go the the next page.
     *
     * @param newLineHeight the height of next line
     * @throws IOException If there is an error writing to the page contents.
     */
    public Boolean changePageIfNeeded(float newLineHeight) throws IOException {
        if (currentPageContentStream == null) {
            return false;
        }

        if (getCurrentPositionY() - newLineHeight < contentBottomPadding) {
            currentPageContentStream.close();
            addPage(getCurrentPage());
            currentPage = new PDPage(new PDRectangle(getPageWidth(), getPageHeight()));
            currentPage.setRotation(isLandscape()? 90:0);
            currentPageContentStream = new PDPageContentStream(this, currentPage);
            if(isLandscape()){
            currentPageContentStream.transform(new Matrix(0, 1, -1, 0, getPageWidth(), 0));
            }
            
            setCurrentPosition(getCurrentPositionX(), (this.isLandscape()?pageWidth: pageHeight) - contentTopPadding-newLineHeight);
            return true;
        }
        return false;

    }
    public Boolean isChangePage(float newLineHeight) throws IOException {
        if (currentPageContentStream == null) {
            return false;
        }

        if (getCurrentPositionY() - newLineHeight < contentBottomPadding) {   
            setCurrentPosition(getCurrentPositionX(), (this.isLandscape()?pageWidth: pageHeight) - contentTopPadding - newLineHeight);
            return true;
        }
        return false;

    }
	public void changePageIfNeeded(int index, Table table, float ... startY) throws IOException {
		if (currentPageContentStream == null) {
			return;
		}
		if(index >0){
            currentPageContentStream.close();
            addPage(getCurrentPage());
            currentPage = new PDPage(new PDRectangle(getPageWidth(), getPageHeight()));
            currentPage.setRotation(isLandscape()? 90:0);
            currentPageContentStream = new PDPageContentStream(this, currentPage);
            if(isLandscape()){
            currentPageContentStream.transform(new Matrix(0, 1, -1, 0, getPageWidth(), 0));
            }
            float lineHeight = calculateHeight(getFont(),getCurrentFontSize());
            float rowHeight = ( lineHeight) + (2 * table.getCellInsidePadding());
            if(startY.length>0){
            	setCurrentPosition(getCurrentPositionX(), (this.isLandscape()?pageWidth-contentTopPadding: pageHeight-contentTopPadding));
            }else{
            	setCurrentPosition(getCurrentPositionX(), (this.isLandscape()?pageWidth: pageHeight) - contentTopPadding-rowHeight);
            }
		}

	}

    public void cropCurrentPage() {
        // cropping
        PDRectangle rectangle = new PDRectangle();
        rectangle.setUpperRightY(getCurrentPage().getCropBox().getUpperRightY());
        // add one more line
        rectangle.setLowerLeftY(getCurrentPositionY() - (getCurrentFontSize() * MM_TO_POINTS));
        rectangle.setUpperRightX(getCurrentPage().getCropBox().getUpperRightX());
        rectangle.setLowerLeftX(getCurrentPage().getCropBox().getLowerLeftX());
        getCurrentPage().setCropBox(rectangle);
    }

    /**
     * Convert document on byte array.
     */
    public byte[] toByteArray() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            this.save(out);
        } catch (Exception e ) {
            throw new RuntimeException("Exception while converting pdf to byte array: " + e.getMessage(), e);
        }
        return out.toByteArray();
    }

    /**
     * Set the current position at top left corner.
     *
     * By default the current position is (x,y) = (0,0) which is bottom left corner.
     */
	public void setCurrentPositionAtStartOfThePage() {
		if (this.isLandscape) {
			setCurrentPositionY(pageWidth - contentTopPadding);
		} else {
			setCurrentPositionY(pageHeight - contentTopPadding);
		}
		setCurrentPositionX(contentLeftPadding);

	}

    /**
     * Set current coordinates and move the text position in the current page content stream.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void setCurrentPosition(float x, float y) {
        setCurrentPositionX(x);
        setCurrentPositionY(y);
    }

    /*
     * Close the current page content stream. This should be call when we finish writing to the document.
     *
     * @throws IOException  If there is an error closing {@link PDPageContentStream}
     */
    private void closeCurrentPageContentStream() throws IOException {
        if (currentPageContentStream != null) {
            currentPageContentStream.close();
            addPage(getCurrentPage());
        }
    }

    /**
     * @return the currentPage
     */
    public PDPage getCurrentPage() {
        if (currentPage == null) {
            currentPage = new PDPage(new PDRectangle(getPageWidth(), getPageHeight()));
            currentPage.setRotation(isLandscape()? 90:0);
        }
        return currentPage;
    }
    /**
     * @return the currentPageContentStream
     * @throws IOException If there is an error writing to the page contents.
     */
    private PDPageContentStream getCurrentPageContentStream() throws IOException {
        if (currentPageContentStream == null) {
            currentPageContentStream = new PDPageContentStream(this, getCurrentPage());
            if(isLandscape()){
            currentPageContentStream.transform(new Matrix(0, 1, -1, 0, getPageWidth(), 0));
            }
        }

        return currentPageContentStream;
    }

    /**
     * Draw heading in pdf document
     *
     * @param heading heading
     * @throws IOException If there is an error writing to the stream.
     */
    public void drawHeading(String heading) throws IOException {

        float headingHeight = calculateHeight(getHeadingFont(), getHeadingFontSize());
        changePageIfNeeded(headingHeight);

        setCurrentPosition(getContentLeftPadding(),
                getCurrentPositionY() - getHeadingTopPadding());

        getCurrentPageContentStream().beginText();
        setCurrentPosition(getContentLeftPadding(),
                getCurrentPositionY() - headingHeight);
        getCurrentPageContentStream().newLineAtOffset(getCurrentPositionX(),
                getCurrentPositionY());
        getCurrentPageContentStream().setFont(getHeadingFont(),
                getHeadingFontSize());

        getCurrentPageContentStream().showText(heading);
        getCurrentPageContentStream().endText();

        setCurrentPosition(getContentLeftPadding(), getCurrentPositionY()
                - getHeadingBottomPadding() - getHeadingTopPadding());

    }
    public void drawHeadingLine(String heading,TextAlignment textAlignment) throws IOException{
    	setHeadingFont(getFont());
        float headingHeight = calculateHeight(getHeadingFont(), getCurrentFontSize());
        changePageIfNeeded(headingHeight);
        
        getCurrentPageContentStream().beginText();
        if(textAlignment ==TextAlignment.LEFT){
        setCurrentPosition(getContentLeftPadding(),
                getCurrentPositionY() - headingHeight);
        }
      float currentPositionX =   calculateHeaderStartX(textAlignment,calculateWidth(heading, getHeadingFont(), getCurrentFontSize()),this.isLandscape?getPageHeight():  getPageWidth(), getContentLeftPadding());
        getCurrentPageContentStream().newLineAtOffset(currentPositionX,
                getCurrentPositionY());
        getCurrentPageContentStream().setFont(getHeadingFont(),
        		getCurrentFontSize());

        getCurrentPageContentStream().showText(heading);
        getCurrentPageContentStream().endText();
        if(textAlignment ==TextAlignment.RIGHT)
        setCurrentPosition(getContentLeftPadding(),
                getCurrentPositionY() - getHeadingTopPadding());
    }

	public void drawHeadForCertificate(String heading, TextAlignment textAlignment, boolean isFirst,
			TemplateTypeEnum type) throws IOException {
		setHeadingFont(getFont());
		float headingHeight = 0;
		headingHeight = calculateHeight(getHeadingFont(), getHeadingFontSize());
		if (type.equals(TemplateTypeEnum.A4B2)) {
			
			changePageIfNeeded(headingHeight);
			if (isFirst) {
				setCurrentPositionY((this.isLandscape() ? getPageWidth() : getPageHeight()) - getContentTopPadding()
						- headingHeight);
			}
			setCurrentPosition(getContentLeftPadding(), getCurrentPositionY() - getHeadingTopPadding());

			getCurrentPageContentStream().beginText();
			setCurrentPosition(getContentLeftPadding(), getCurrentPositionY() - headingHeight);
		} else if (type.equals(TemplateTypeEnum.A4B3)) {
			changePageIfNeeded(headingHeight);
			if (isFirst) {
				setCurrentPositionY((this.isLandscape() ? getPageWidth() : getPageHeight()) - getContentTopPadding()
						- headingHeight);
			}
			 setCurrentPosition(getContentLeftPadding(),
			 getCurrentPositionY() - getHeadingTopPadding());

			getCurrentPageContentStream().beginText();
			setCurrentPosition(getContentLeftPadding(), getCurrentPositionY() - getHeadingTopPadding());

		}
		float currentPositionX = calculateHeaderStartX(textAlignment,
				calculateWidth(heading, getHeadingFont(), getHeadingFontSize()),
				this.isLandscape ? getPageHeight() : getPageWidth(), getContentLeftPadding());
		getCurrentPageContentStream().newLineAtOffset(currentPositionX, getCurrentPositionY());
		getCurrentPageContentStream().setFont(getHeadingFont(), getHeadingFontSize());

		getCurrentPageContentStream().showText(heading);
		getCurrentPageContentStream().endText();

//		setCurrentPosition(getContentLeftPadding(), getCurrentPositionY() - headingHeight);

	}
	public float getPageHeight(Table table) throws IOException {
		float headingHeight = calculateHeight(getHeadingFont(), getHeadingFontSize());
		float currentPostionY = 2* headingHeight+getHeadingTopPadding()+contentBottomPadding; 
		headingHeight = calculateHeight(getHeadingFont(), getCurrentFontSize());
		currentPostionY = currentPostionY + headingHeight + getHeadingTopPadding();
		int level = table.getLevel();
		PDFont rowFont = getFont();
		float fontSize = getCurrentFontSize();
		float lineHeight = calculateHeight(rowFont, getCurrentFontSize());
		float rowHeight = lineHeight + (2 * table.getCellInsidePadding());
		currentPostionY = currentPostionY + level * rowHeight;
		float resultvalue = 0;
		if (table.getContent() != null) {
			for (int rowNumber = 0; rowNumber < table.getContent().length; rowNumber++) {
				String[] row = table.getContent()[rowNumber];
				if (row == null)
					row = new String[table.getSubColumns().size()];
				int length = Math.min(table.getSubColumns().size(), row.length);
				String[][] cellsContentLines = new String[length][];
				int necessaryLines = 1;
				for (int cellNumber = 0; cellNumber < length; cellNumber++) {
					String[] lines = generateRowContentLines(table, rowFont, fontSize, row, cellNumber);
					cellsContentLines[cellNumber] = lines;
					if (lines.length > necessaryLines) {
						necessaryLines = lines.length;
					}
				}
				rowHeight = (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());
				currentPostionY = currentPostionY + rowHeight;
				
			}
		}
		return currentPostionY;
	}
	public float isCreateNextPage(Table table) throws IOException {
		float headingHeight = calculateHeight(getHeadingFont(), getHeadingFontSize());
		float currentPostionY = getCurrentPositionY(); 
		currentPostionY =currentPostionY- getHeadingTopPadding() - headingHeight - headingHeight;
		headingHeight = calculateHeight(getHeadingFont(), getCurrentFontSize());
		currentPostionY = currentPostionY - headingHeight - getHeadingTopPadding();
		int level = table.getLevel();
		PDFont rowFont = getFont();
		float fontSize = getCurrentFontSize();
		float lineHeight = calculateHeight(rowFont, getCurrentFontSize());
		float rowHeight = lineHeight + (2 * table.getCellInsidePadding());
		currentPostionY = currentPostionY - level * rowHeight;
		float resultvalue = 0;
		if (table.getContent() != null) {
			for (int rowNumber = 0; rowNumber < table.getContent().length; rowNumber++) {
				String[] row = table.getContent()[rowNumber];
				if (row == null)
					row = new String[table.getSubColumns().size()];
				int length = Math.min(table.getSubColumns().size(), row.length);
				String[][] cellsContentLines = new String[length][];
				int necessaryLines = 1;
				for (int cellNumber = 0; cellNumber < length; cellNumber++) {
					String[] lines = generateRowContentLines(table, rowFont, fontSize, row, cellNumber);
					cellsContentLines[cellNumber] = lines;
					if (lines.length > necessaryLines) {
						necessaryLines = lines.length;
					}
				}
				rowHeight = (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());
				currentPostionY = currentPostionY - rowHeight;
				
			}
		}
		if(currentPostionY>contentBottomPadding){
			resultvalue=currentPostionY;
		}else{
			resultvalue=currentPostionY-contentBottomPadding-headingTopPadding;
		}
		return resultvalue;
	}
    public void drawHeading(String heading,TextAlignment textAlignment,Boolean isAddPadding,boolean ... isFirst) throws IOException {
    	setHeadingFont(getFont());
        float headingHeight = calculateHeight(getHeadingFont(), getHeadingFontSize());
        changePageIfNeeded(headingHeight);
        if(isFirst.length>0&& isFirst[0]){
        	 setCurrentPositionY((this.isLandscape()?getPageWidth(): getPageHeight()) - getContentTopPadding() - headingHeight);
        }
        setCurrentPosition(getContentLeftPadding(),
                getCurrentPositionY() - getHeadingTopPadding());

        getCurrentPageContentStream().beginText();
        setCurrentPosition(getContentLeftPadding(),
                getCurrentPositionY() - headingHeight);
      float currentPositionX =   calculateHeaderStartX(textAlignment,calculateWidth(heading, getHeadingFont(), getHeadingFontSize()),this.isLandscape?getPageHeight():  getPageWidth(), getContentLeftPadding());
        getCurrentPageContentStream().newLineAtOffset(currentPositionX,
                getCurrentPositionY());
        getCurrentPageContentStream().setFont(getHeadingFont(),
                getHeadingFontSize());

        getCurrentPageContentStream().showText(heading);
        getCurrentPageContentStream().endText();

        if(isAddPadding)
//        setCurrentPosition(getContentLeftPadding(), getCurrentPositionY()
//                - getHeadingBottomPadding() - getHeadingTopPadding());
            setCurrentPosition(getContentLeftPadding(), getCurrentPositionY()
                    - headingHeight);

    }
    private void drawLine(String heading,float contentY) throws IOException{

        getCurrentPageContentStream().beginText();
        setCurrentPosition(getContentLeftPadding(),
        		contentY);
        getCurrentPageContentStream().newLineAtOffset(getContentLeftPadding(),
                getCurrentPositionY());
        getCurrentPageContentStream().setFont(getHeadingFont(),
                getHeadingFontSize());
        getCurrentPageContentStream().showText(heading);
        getCurrentPageContentStream().endText();
    }

    /**
     * Draw table at current position.
     *
     * @param table pdf table
     * @throws IOException If there is an error while drawing on the screen.
     */
    public void drawTable(Table table,boolean ...isSrcCertificate) throws IOException {
        drawTable(table, getCurrentPositionX(), getCurrentPositionY(),isSrcCertificate);
    }
    public void drawFooter(boolean isDrawSplit,Table table,int ...drawCondition) throws IOException{
    	PDFont rowFont = getFont();
		for (Object[] row : table.getBodyContent()) {
	        if(row == null)row = new Object[table.getSubColumns().size()];
	        int length= Math.min(table.getSubColumns().size(), row.length);
	        Object[][] cellsContentLines = new Object[length][];
	        int necessaryLines = 1;
	       
	        for (int cellNumber = 0; cellNumber < length; cellNumber++) {
	            String[] lines = generateRowContentLines(table, rowFont, getCurrentFontSize(), row, cellNumber);
	            cellsContentLines[cellNumber] = lines;
	            if (lines.length > necessaryLines) {
	                necessaryLines = lines.length;
	            }
	        }
	        float lineHeight = calculateHeight(rowFont, getCurrentFontSize());
	        float rowHeight = (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());
	        if(drawCondition.length>0 && drawCondition[1]==3){
	        	lineHeight = 2*getHeadingTopPadding();
	        	rowHeight = 2*getHeadingTopPadding();
	        }
	        float topLeftCornerX = getCurrentPositionX();
	        float topLeftCornerY = getCurrentPositionY();
	    	drawRowContent(table, rowFont, cellsContentLines, lineHeight, rowHeight, true, false);
	        // go on the next line
	        setCurrentPosition(topLeftCornerX, topLeftCornerY - rowHeight);
		}
		if(drawCondition.length>0 && (drawCondition[0]%drawCondition[1])!=0  && isDrawSplit){
	    	int split =2;
	    	float contentY = this.isLandscape?getPageWidth()/2:getPageHeight()/2;
	    	if(drawCondition.length>0){
	    		split = drawCondition[1];
	    		if(split ==3){
	    			if(drawCondition[0]%drawCondition[1]==1){
	    				contentY=(this.isLandscape?getPageWidth()/3:getPageHeight()/3)*2;
	    			} else if(drawCondition[0]%drawCondition[1]==2){
	    				contentY=this.isLandscape?getPageWidth()/3:getPageHeight()/3;
	    			}
	    		}
	    	}
//			drawLine("--------------------------------------------------------------------------------",contentY);
	    	drawLine("---------------------------------------------------------------------------",contentY);
	        // go on the next line
//	        setCurrentPosition(getContentLeftPadding(), contentY-getHeadingTopPadding());
			float headingHeight = calculateHeight(getHeadingFont(), getHeadingFontSize());
			setCurrentPosition(getContentLeftPadding(), contentY-getContentTopPadding()-headingHeight);
		}else{
	        setCurrentPosition(getCurrentPositionX(), getContentBottomPadding());
		}


    }

    /**
     * Draw table at a specific position.
     * @param table pdf table
     * @param topLeftCornerX position x
     * @param topLeftCornerY position y
     * @throws IOException If there is an error while drawing on the screen.
     */
    public void drawTable(Table table, float topLeftCornerX, float topLeftCornerY,boolean ... isSrcCertificate)
            throws IOException {
    	    List<Table> tables =    splitTable2Tables(table);
    	    int index =0;
    	    for (Table splitTable : tables) {
    	    	splitTable.modiLevel();
    	    	changePageIfNeeded(index++,splitTable,getContentTopPadding());
    	    	splitTable.setDrawHeaders(table.isDrawHeaders());
    	    	splitTable.setCellInsidePadding(table.getCellInsidePadding());
    	    	splitTable.setDrawGrid(table.isDrawGrid());
    	    	
    	        setCurrentPosition(topLeftCornerX, topLeftCornerY);
    	        if (splitTable.isDrawHeaders()) {
    	        	drawNestingTableHeaders(splitTable);
    	        }
    			if (splitTable.getContent() != null) {
    				for (int rowNumber = 0; rowNumber < splitTable.getContent().length; rowNumber++) {
    					drawRow(splitTable, rowNumber);
    				}
    			}else if(splitTable.getBodyContent() != null){
    				if(table.getMaxLineNumber() != null)
    				setMaxLineNum(table.getMaxLineNumber());
    				for (int rowNumber = 0; rowNumber < splitTable.getBodyContent().length; rowNumber++) {
    					drawRow(splitTable, rowNumber,isSrcCertificate);
    				}
    			}
			}
//    	        setCurrentPosition(topLeftCornerX, topLeftCornerY);
//    	        if (table.isDrawHeaders()) {
////    	            drawTableHeaders(table);
//    	        	drawNestingTableHeaders(table);
//    	        }
//    			if (table.getContent() != null) {
//    				for (int rowNumber = 0; rowNumber < table.getContent().length; rowNumber++) {
//    					drawRow(table, rowNumber);
//    				}
//    			}
    }
	private  List<Table> splitTable2Tables(Table table) {
		List<Table> tables = new ArrayList<>();
		boolean isSplit =  (int) Math.ceil((double)(table.getWidth()+contentLeftPadding*2)/( isLandscape?pageHeight:pageWidth))>1;
		if(!isSplit){
			return  Arrays.asList(table);
		}
		String[][] contents = table.getContent();
		List<List<TableColumn>> multiTableColumn = splitColumn2MutiLineColumn(table, isLandscape?pageHeight:pageWidth,isSplit);
		int index = 0;
		for (List<TableColumn> list : multiTableColumn) {
			if (contents != null) {
				String[][] perTableConten = new String[contents.length][];
				int rowIndex = 0;
				int size = 0;
				for (TableColumn tableCol : list) {
					if (tableCol.getChildColumns() != null) {
						size += tableCol.getChildColumns().size();
					} else {
						++size;
					}
				}
				for (String[] rowContent : contents) {
					perTableConten[rowIndex] = new String[size];
					System.arraycopy(rowContent, index, perTableConten[rowIndex++], 0, size);
				}
				tables.add(new Table(list, perTableConten, table.isLandscape(), table.getLevel()));
				index += size;
			}else{
				tables.add(new Table(list, contents, table.isLandscape(), table.getLevel()));
			}

		}
		return tables;
	}
	private  List<List<TableColumn>> splitColumn2MutiLineColumn(Table table,float maxWidth,boolean isSplit) {
        if(!isSplit){
        	return Arrays.asList(table.getColumns());
        }
		float lineWidth = contentLeftPadding;
        List<List<TableColumn>> allLineList = new ArrayList<>();//所有行集合

        List<TableColumn> perLines = new ArrayList<>();//每行的集合
        for (TableColumn column : table.getColumns()) {
			float perColumnWidth = column.getWidth();//每列的宽度
			if(lineWidth+perColumnWidth <=maxWidth){
				perLines.add(column);
				lineWidth+=perColumnWidth;			
			}else{
				if(lineWidth == contentLeftPadding){
					perLines.add(column);
					allLineList.add(perLines);
					perLines = new ArrayList<>();
				}else{
					allLineList.add(perLines);
					perLines = new ArrayList<>();
					perLines.add(column);
					lineWidth = perColumnWidth;
				}
			}
		}
        if(perLines.size() >0){
        	allLineList.add(perLines);
        }
		return allLineList;
	}

	
    @SuppressWarnings("unused")
	private void drawTableHeaders(Table table) throws IOException {


        PDFont headerFont = getFont();//getFontBold();
        float fontSize = getCurrentFontSize();
        String[][] cellsContent = new String[table.getColumns().size()][];
        int necessaryLines = 1;
        for (int cellNumber = 0; cellNumber < table.getColumns().size(); cellNumber++) {
            TableColumn column = table.getColumns().get(cellNumber);
            float contentWidth = column.getWidth() - (2 * table.getCellInsidePadding());
            List<String> lines = splitTextInLines(column.getHeader(), contentWidth, headerFont, fontSize);
            cellsContent[cellNumber] = lines.toArray(new String[0]);
            if (lines.size() > necessaryLines) {
                necessaryLines = lines.size();
            }
        }

        float lineHeight = calculateHeight(headerFont, fontSize);
        float rowHeight = (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());

       boolean isNeedNextPage =    changePageIfNeeded(rowHeight);
        if(isNeedNextPage){
 			if (table.isDrawHeaders()) {

 				drawTableHeaders(table);
 			}
        }
        float topLeftCornerY = getCurrentPositionY();
        float topLeftCornerX = getCurrentPositionX();

        // draw header background
        getCurrentPageContentStream().setNonStrokingColor(table.getHeaderBackgroundColor());
        getCurrentPageContentStream().addRect(topLeftCornerX, topLeftCornerY - rowHeight, table.getWidth(),
                rowHeight);
        getCurrentPageContentStream().fill();
        getCurrentPageContentStream().setNonStrokingColor(Color.BLACK);

        // column background override header background
        drawColumnBackground(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);

        if (table.isDrawGrid()) {
//            drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);
        	 drawNestingRowGrid(table,table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);
        }
        
        drawRowContent(table, headerFont, cellsContent, lineHeight, rowHeight, false,true);
        
       

        // go on the next line
        setCurrentPosition(topLeftCornerX, topLeftCornerY - rowHeight);

    }
    //绘制表头
    private void drawNestingTableHeaders(Table table) throws IOException {

    	int level = table.getLevel();
        PDFont headerFont = getFont();//getFontBold();
        float fontSize = getCurrentFontSize();
        String[][] cellsContent = new String[table.getColumns().size()][];
        int necessaryLines = 1;
        for (int cellNumber = 0; cellNumber < table.getColumns().size(); cellNumber++) {
            TableColumn column = table.getColumns().get(cellNumber);
            List<TableColumn> childColumns = column.getChildColumns();
            if(childColumns != null){
            	cellsContent[cellNumber] = new String[childColumns.size()+1];
            }
            float contentWidth = column.getWidth() - (2 * table.getCellInsidePadding());
//            List<String> lines = splitTextInLines(column.getHeader(), contentWidth, headerFont, fontSize);
//            cellsContent[cellNumber] = lines.toArray(new String[0]);
            if(childColumns != null){
            cellsContent[cellNumber][0] = column.getHeader();
            } else{
            	cellsContent[cellNumber] = Collections.singletonList(column.getHeader()).toArray(new String[0]) ;
            }
//            if (lines.size() > necessaryLines) {
//                necessaryLines = lines.size();
//            }
//            List<TableColumn> childColumns = column.getChildColumns();
            if(childColumns !=null){
            	int index =1;
            	for (TableColumn child : childColumns) {
            		contentWidth = child.getWidth() - (2 * table.getCellInsidePadding());
                     
                    cellsContent[cellNumber][index++] = child.getHeader();
				}
            }
        }

        float lineHeight = calculateHeight(headerFont, fontSize);
        float rowHeight = getRowHeight() >0 ?getRowHeight(): (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());

       boolean isNeedNextPage =    changePageIfNeeded(rowHeight);
        if(isNeedNextPage){
 			if (table.isDrawHeaders()) {

 				drawNestingTableHeaders(table);
 			}
        }
        float topLeftCornerY = getCurrentPositionY();
        float topLeftCornerX = getCurrentPositionX();

        // draw header background
        getCurrentPageContentStream().setNonStrokingColor(table.getHeaderBackgroundColor());
        getCurrentPageContentStream().addRect(topLeftCornerX, topLeftCornerY -level* rowHeight, table.getWidth(),
               level* rowHeight);
        getCurrentPageContentStream().fill();
        getCurrentPageContentStream().setNonStrokingColor(Color.BLACK);

        // column background override header background
        drawColumnBackground(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);

        if (table.isDrawGrid()) {
//            drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);
        	 drawNestingRowGrid(table,table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);
        }
        
        drawHeadRowContent(table, headerFont, cellsContent, lineHeight, rowHeight, false,true);
        // go on the next line
        setCurrentPosition(topLeftCornerX, topLeftCornerY -level* rowHeight);

    }
    private boolean  getNextRowInfo(Table table,int nextRowNum,PDFont rowFont,float fontSize,float currentRowHeight) throws IOException{
        if (table.getContent() != null) {
			String[] row = table.getContent()[nextRowNum];
			if(row == null)row = new String[table.getSubColumns().size()];
			int length= Math.min(table.getSubColumns().size(), row.length);
			
			String[][] cellsContentLines = new String[length][];
			int necessaryLines = 1;

			for (int cellNumber = 0; cellNumber < length; cellNumber++) {
				String[] lines = generateRowContentLines(table, rowFont, fontSize, row, cellNumber);
				cellsContentLines[cellNumber] = lines;
				if (lines.length > necessaryLines) {
					necessaryLines = lines.length;
				}
			}
			float lineHeight = calculateHeight(rowFont, fontSize);
			float rowHeight =getRowHeight()>0?getRowHeight(): (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());
			float tempCurrentPositionY = getCurrentPositionY();
			Boolean isNeedNextPage = isChangePage(rowHeight+currentRowHeight);
			setNextPositionY(getCurrentPositionY());
			setCurrentPositionY(tempCurrentPositionY);  
			return isNeedNextPage;
        }
        return false;
    }
    private void drawRow(Table table, int rowNumber,boolean ... isSrcCertificate) throws IOException {

        PDFont rowFont = getFont();//getCurrentFont();
        setCurrentFontSize(DEFAULT_CONTENT_SIZE);
        float fontSize = getCurrentFontSize();
        
        if (table.getContent() != null) {
			String[] row = table.getContent()[rowNumber];
			if(row == null)row = new String[table.getSubColumns().size()];
			int length= Math.min(table.getSubColumns().size(), row.length);
			
			String[][] cellsContentLines = new String[length][];
			int necessaryLines = 1;

			for (int cellNumber = 0; cellNumber < length; cellNumber++) {
				String[] lines = generateRowContentLines(table, rowFont, fontSize, row, cellNumber);
				cellsContentLines[cellNumber] = lines;
				if (lines.length > necessaryLines) {
					necessaryLines = lines.length;
				}
			}
			float lineHeight = calculateHeight(rowFont, fontSize);
			float rowHeight =getRowHeight()>0?getRowHeight(): (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());
			Boolean isNeedNextPage = changePageIfNeeded(rowHeight);
			if (isNeedNextPage) {
				if (table.isDrawHeaders()) {
					drawNestingTableHeaders(table);
				}
			}
			float topLeftCornerX = getCurrentPositionX();
			float topLeftCornerY = getCurrentPositionY();
			drawColumnBackground(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);
			if (table.isDrawGrid()) {
				if(isSrcCertificate.length >0){
					drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight,rowNumber);
				}else{
					if(isNeedNextPage && isNeedForwardAndOverPage){//对明细账增加承前过次页页的功能
						rowHeight =lineHeight + (2 * table.getCellInsidePadding());
						drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY,rowHeight);
					}else{

						drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);
					}
				}
			}
			if(isNeedForwardAndOverPage){
				//承前页
				if(isNeedNextPage){
					String[] rowForWard = table.getContent()[rowNumber-1];
					balanceMap.put(FORWORDPAGE, rowForWard[rowForWard.length-1]);
					drawRowContent(table, rowFont, buildForwardAndOverRow(length, 2,6,FORWORDPAGE), lineHeight, rowHeight, true);
					setCurrentPosition(topLeftCornerX, topLeftCornerY - rowHeight);
					topLeftCornerY = getCurrentPositionY();
					if (table.isDrawGrid()) {
						rowHeight =getRowHeight()>0?getRowHeight(): (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());
						drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);	
					}
				} 
				//过次页
				if(topLeftCornerY-rowHeight-(3*lineHeight + 2 * table.getCellInsidePadding())<contentBottomPadding){
					int rowCount = (int)Math.floor((topLeftCornerY - rowHeight-contentBottomPadding)/(lineHeight + (2 * table.getCellInsidePadding())));
					if(table.getContent().length>(rowNumber+1) && getNextRowInfo(table, rowNumber+1, rowFont, fontSize,rowHeight)){
						if(rowCount>=1){
							// draw row content
							drawRowContent(table, rowFont, cellsContentLines, lineHeight, rowHeight, true);					
							// go on the next line
							setCurrentPosition(topLeftCornerX, topLeftCornerY - rowHeight);
							topLeftCornerY = getCurrentPositionY();
							if (table.isDrawGrid()) {
								rowHeight = lineHeight + (2 * table.getCellInsidePadding());
								drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);	
							}
						}

						topLeftCornerY = getCurrentPositionY();
						String[] rowForWard = table.getContent()[rowCount>=1 ?rowNumber:(rowNumber<1?0:rowNumber-1)];
						balanceMap.put(OVERPAGE, rowForWard[rowForWard.length-1]);
						drawRowContent(table, rowFont, buildForwardAndOverRow(length, 2,6,OVERPAGE), lineHeight, rowHeight, true);
						setCurrentPosition(topLeftCornerX, topLeftCornerY - rowHeight);
						if(rowCount<1){
							drawRow(table, rowNumber);
						}
						return;
					}
				}
			}
			// draw row content
				drawRowContent(table, rowFont, cellsContentLines, lineHeight, rowHeight, true);
			
			// go on the next line
			setCurrentPosition(topLeftCornerX, topLeftCornerY - rowHeight);

		}else if(table.getBodyContent() != null){
	        Object[] row = table.getBodyContent()[rowNumber];
	        if(row == null)row = new Object[table.getSubColumns().size()];
	        int length= Math.min(table.getSubColumns().size(), row.length);
	        Object[][] cellsContentLines = new Object[length][];
	        int necessaryLines = 1;
	       
	        for (int cellNumber = 0; cellNumber < length; cellNumber++) {

	            String[] lines = null;//generateRowContentLines(table, rowFont, fontSize, row, cellNumber);
	        	if(isSrcCertificate.length>0 && rowNumber==getMaxLineNum()){
	        		lines = new String[1];
	        		cellsContentLines[cellNumber] = Arrays.asList( row[cellNumber]).toArray();
	        	}else{
	        		lines = generateRowContentLines(table, rowFont, fontSize, row, cellNumber);
	        		
	        		cellsContentLines[cellNumber] = lines.length>2 && isSrcCertificate.length>0? Arrays.copyOf(lines, 3):lines;//lines;
	        	}
	            
	            if (cellsContentLines[cellNumber].length > necessaryLines) {
	                necessaryLines = cellsContentLines[cellNumber].length;
	            }
	        }
	        float lineHeight = calculateHeight(rowFont, fontSize);
	        float rowHeight =getRowHeight()>0?getRowHeight(): (necessaryLines * lineHeight) + (2 * table.getCellInsidePadding());
	        if(isSrcCertificate.length>0){
	        	rowHeight = rowNumber != getMaxLineNum()?40f:20f;
	        	if(isSrcCertificate[1]){
	        		rowHeight = rowNumber != getMaxLineNum()?34f:15f; //32:17
	        	}
	        }
	       Boolean isNeedNextPage =  changePageIfNeeded(rowHeight);
	       if(isNeedNextPage){
				if (table.isDrawHeaders()) {
					drawNestingTableHeaders(table);
				}
	       }	        
	        float topLeftCornerX = getCurrentPositionX();
	        float topLeftCornerY = getCurrentPositionY();
	        drawColumnBackground(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);
	        if (table.isDrawGrid()) {
				if(isSrcCertificate.length >0){
					drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight,rowNumber);
				}else{
					drawRowGrid(table.getColumns(), topLeftCornerX, topLeftCornerY, rowHeight);
				}
	        }
	        // draw row content
	        drawRowContent(table, rowFont, cellsContentLines, lineHeight, rowHeight, true);
	        // go on the next line
	        setCurrentPosition(topLeftCornerX, topLeftCornerY - rowHeight);
		}

    }
    /**
     * 构造承前页行信息
     * @param length 
     * @param fieldIndex 承前页写到那个索引字段上
     * @return
     */
    private  String[][] buildForwardAndOverRow(int length,int fieldIndex,int balanceIndex,String paramType){
    	String[][] cellsContentLines = new String[length][];
    	for (int i = 0; i < length; i++) {
    		if(paramType == FORWORDPAGE){
				if(fieldIndex == i){
					cellsContentLines[i] = new String[1];
					cellsContentLines[i][0] = FORWORDPAGE;
				}else if(balanceIndex==i){
					cellsContentLines[i] = new String[1];
					cellsContentLines[i][0] = balanceMap.get(FORWORDPAGE);
				}else{
					cellsContentLines[i] = new String[1];
					cellsContentLines[i][0] = "";
				}
			}else if(paramType == OVERPAGE){
				if(fieldIndex == i){
					cellsContentLines[i] = new String[1];
					cellsContentLines[i][0] = OVERPAGE;
				}else if(balanceIndex==i){
					cellsContentLines[i] = new String[1];
					cellsContentLines[i][0] = balanceMap.get(OVERPAGE);
				}else{
					cellsContentLines[i] = new String[1];
					cellsContentLines[i][0] = "";
				}
			}
		}
    	return cellsContentLines;
    }


    private String[] generateRowContentLines(Table table, PDFont rowFont, float fontSize, Object[] row,
			int cellNumber) throws IOException {
        if (row[cellNumber] == null) {
            String[] emptyLine = {""};
            return emptyLine;
        }
        List<TableColumn> subColumns = new ArrayList<>();
        for (TableColumn tableColumn : table.getColumns()) {
			if(tableColumn.getChildColumns() == null){
				subColumns.add(tableColumn);
			}else{
				for (TableColumn subColumn : tableColumn.getChildColumns()) {
					subColumns.add(subColumn);
				}
			}
		}
//        TableColumn column = table.getColumns().get(cellNumber);
        TableColumn column = subColumns.get(cellNumber);
        float contentWidth = column.getWidth() - (2 * table.getCellInsidePadding());
        String rowContent = row[cellNumber].toString();

        if (!column.isOverlapNextColumn() || isLastColumn(table, cellNumber)) {
            return splitTextInLines(rowContent, contentWidth, rowFont, fontSize).toArray(new String[0]);
        }

//        TableColumn nextColumn = table.getColumns().get(cellNumber + 1);
        TableColumn nextColumn = subColumns.get(cellNumber + 1);

        // NOT SUPPORTED YET: other alignment
        if (nextColumn.getVerticalAlignment() != TextVerticalAlignment.BOTTOM || nextColumn.getAlignment() != TextAlignment.RIGHT) {
            return splitTextInLines(rowContent, contentWidth, rowFont, fontSize).toArray(new String[0]);
        }

        float nextColumnContentAvailableWidth = nextColumn.getWidth() - (2 * table.getCellInsidePadding());
        String nextColumnContent = row[cellNumber + 1].toString();
        List<String> nextColumnLines = splitTextInLines(nextColumnContent, nextColumnContentAvailableWidth, rowFont, fontSize);

        // NOT SUPPORTED YET: next column require more than one line
        if (nextColumnLines.size() > 1) {
            return splitTextInLines(rowContent, contentWidth, rowFont, fontSize).toArray(new String[0]);
        }

        float withOverlapContentWidth = contentWidth + nextColumn.getWidth();
        List<String> lines = splitTextInLines(rowContent, withOverlapContentWidth, rowFont, fontSize);

        float lastLineWidth = calculateWidth(lines.get(lines.size() - 1), rowFont, fontSize);
        float nextColumnContentWidth = calculateWidth(nextColumnContent, rowFont, fontSize);
        if (withOverlapContentWidth < (lastLineWidth + nextColumnContentWidth)) {
            // add a new line in order to avoid the overlap
            lines.add("");
        }

        return lines.toArray(new String[0]);
	}

	/**
     * This is splitting the row content based on available space on different lines.
     *
     * There are some limitation on next column overlap which should be addressed:
     *  - treat the case when next column vertical alignment is not BOTTOM
     *  - treat the case when column content will not fit in on line
     *  - do better space management, if next column alignment is center or right.
     *  - (optional) make to overlap more than one column
     *  In all these cases we don't do the overlap
     *
     * TODO fix all the above limitations
     */
    private String[] generateRowContentLines(Table table, PDFont rowFont, float fontSize, String[] row, int cellNumber) throws IOException {
        if (row[cellNumber] == null) {
            String[] emptyLine = {""};
            return emptyLine;
        }
        List<TableColumn> subColumns = new ArrayList<>();
        for (TableColumn tableColumn : table.getColumns()) {
			if(tableColumn.getChildColumns() == null){
				subColumns.add(tableColumn);
			}else{
				for (TableColumn subColumn : tableColumn.getChildColumns()) {
					subColumns.add(subColumn);
				}
			}
		}
//        TableColumn column = table.getColumns().get(cellNumber);
        TableColumn column = subColumns.get(cellNumber);
        float contentWidth = column.getWidth() - (2 * table.getCellInsidePadding());
        String rowContent = row[cellNumber];

        if (!column.isOverlapNextColumn() || isLastColumn(table, cellNumber)) {
            return splitTextInLines(rowContent, contentWidth, rowFont, fontSize).toArray(new String[0]);
        }

//        TableColumn nextColumn = table.getColumns().get(cellNumber + 1);
        TableColumn nextColumn = subColumns.get(cellNumber + 1);

        // NOT SUPPORTED YET: other alignment
        if (nextColumn.getVerticalAlignment() != TextVerticalAlignment.BOTTOM || nextColumn.getAlignment() != TextAlignment.RIGHT) {
            return splitTextInLines(rowContent, contentWidth, rowFont, fontSize).toArray(new String[0]);
        }

        float nextColumnContentAvailableWidth = nextColumn.getWidth() - (2 * table.getCellInsidePadding());
        String nextColumnContent = row[cellNumber + 1];
        List<String> nextColumnLines = splitTextInLines(nextColumnContent, nextColumnContentAvailableWidth, rowFont, fontSize);

        // NOT SUPPORTED YET: next column require more than one line
        if (nextColumnLines.size() > 1) {
            return splitTextInLines(rowContent, contentWidth, rowFont, fontSize).toArray(new String[0]);
        }

        float withOverlapContentWidth = contentWidth + nextColumn.getWidth();
        List<String> lines = splitTextInLines(rowContent, withOverlapContentWidth, rowFont, fontSize);

        float lastLineWidth = calculateWidth(lines.get(lines.size() - 1), rowFont, fontSize);
        float nextColumnContentWidth = calculateWidth(nextColumnContent, rowFont, fontSize);
        if (withOverlapContentWidth < (lastLineWidth + nextColumnContentWidth)) {
            // add a new line in order to avoid the overlap
            lines.add("");
        }

        return lines.toArray(new String[0]);
    }

    private boolean isLastColumn(Table table, int cellNumber) {
//        return table.getColumns().size() == cellNumber + 1;
    	 List<TableColumn> subColumns = new ArrayList<>();
        for (TableColumn tableColumn : table.getColumns()) {
			if(tableColumn.getChildColumns() == null){
				subColumns.add(tableColumn);
			}else{
				for (TableColumn subColumn : tableColumn.getChildColumns()) {
					subColumns.add(subColumn);
				}
			}
		}
        return subColumns.size() ==cellNumber + 1;
    }

	private void drawHeadRowContent(Table table, PDFont rowFont, String[][] cellsContent, float lineHeight,
			float rowHeight, boolean checkColumnAlignment, Boolean... isSrcHeader) throws IOException {
		float rowY = getCurrentPositionY();
		int level = table.getLevel();
		for (int cellNumber = 0; cellNumber < cellsContent.length; cellNumber++) {
			TableColumn currentColumn = table.getColumns().get(cellNumber);
			// column font have priority
			PDFont cellFont = table.getColumns().get(cellNumber).getFont() != null
					? table.getColumns().get(cellNumber).getFont() : rowFont;

			int numberOfLinesInCell = cellsContent[cellNumber].length;
//			float contentStartY = calculateRowContentStartY(rowY, currentColumn, table.getCellInsidePadding(),
//					numberOfLinesInCell, lineHeight, rowHeight, checkColumnAlignment);
			//rowHeight - cellPadding - (lineHeight * numberOfLinesInCell) / 2;
			float contentStartY =0;
			if (currentColumn.getChildColumns() == null) {
				contentStartY = rowY - ((level * rowHeight - lineHeight) / 2 - table.getCellInsidePadding());
			} else {
				contentStartY = rowY - ((rowHeight - lineHeight) / 2 - table.getCellInsidePadding());
			}
			setCurrentPosition(getCurrentPositionX(), contentStartY);
			float preWidth  =0;
			for (int cellLineNumber = 0; cellLineNumber < numberOfLinesInCell; cellLineNumber++) {
				String line = cellsContent[cellNumber][cellLineNumber];
				setCurrentPosition(getCurrentPositionX(), contentStartY);
				float lineWidth = calculateWidth(line, cellFont, getCurrentFontSize());
				float contentStartX =0;
				if(cellLineNumber==0){
					setCurrentPosition(getCurrentPositionX(), getCurrentPositionY() - lineHeight);
					contentStartX = calculateRowContentStartX(getCurrentPositionX(), currentColumn, lineWidth,
							table.getCellInsidePadding(), checkColumnAlignment, isSrcHeader);
				} else{
					//子列
					float currentWidth = currentColumn.getChildColumns().get(--cellLineNumber).getWidth();
					setCurrentPosition(getCurrentPositionX(), getCurrentPositionY() - rowHeight-lineHeight);

					contentStartX=getCurrentPositionX()+preWidth + (currentWidth - lineWidth) / 2;
					preWidth+=currentWidth;
					cellLineNumber++; 

				}

				// draw the line
				getCurrentPageContentStream().beginText();
				getCurrentPageContentStream().newLineAtOffset(contentStartX, getCurrentPositionY());
				getCurrentPageContentStream().setFont(cellFont, getCurrentFontSize());
				getCurrentPageContentStream().showText(line);
				getCurrentPageContentStream().endText();
				

			}
			// got the next cell
			float xIncrease = table.getColumns().get(cellNumber).getWidth();
			setCurrentPosition(getCurrentPositionX() + xIncrease, rowY);
		}
	}  

    /**
     * Draw content for all the cells in the row
     *
     * @param cellsContent matrix of lines for the table row. The first dimension is the column number and the second
     *                     dimension is the line number in the row cell
     */
    private void drawRowContent(Table table, PDFont rowFont, String[][] cellsContent,
                                float lineHeight, float rowHeight, boolean checkColumnAlignment,Boolean ...isSrcHeader) throws IOException {
        float rowY = getCurrentPositionY();
        for (int cellNumber = 0; cellNumber < cellsContent.length; cellNumber++) {
//            TableColumn currentColumn = table.getColumns().get(cellNumber);
        	TableColumn currentColumn = table.getSubColumns().get(cellNumber);
            // column font have priority
//            PDFont cellFont = table.getColumns().get(cellNumber).getFont() != null
//                    ? table.getColumns().get(cellNumber).getFont() : rowFont;
        	  PDFont cellFont = table.getSubColumns().get(cellNumber).getFont() != null
                      ? table.getSubColumns().get(cellNumber).getFont() : rowFont;

            int numberOfLinesInCell = cellsContent[cellNumber].length;
            float contentStartY = calculateRowContentStartY(rowY, currentColumn, table.getCellInsidePadding(),
                    numberOfLinesInCell, lineHeight, rowHeight, checkColumnAlignment);
            setCurrentPosition(getCurrentPositionX(), contentStartY);

            for (int cellLineNumber = 0; cellLineNumber < numberOfLinesInCell; cellLineNumber++) {
                String line = cellsContent[cellNumber][cellLineNumber];
                float lineWidth = calculateWidth(line, cellFont, getCurrentFontSize());
                setCurrentPosition(getCurrentPositionX(),
                        getCurrentPositionY() - lineHeight);
                float contentStartX = calculateRowContentStartX(getCurrentPositionX(), currentColumn,
                        lineWidth, table.getCellInsidePadding(), checkColumnAlignment,isSrcHeader);

                // draw the line
                getCurrentPageContentStream().beginText();
                getCurrentPageContentStream().newLineAtOffset(contentStartX,
                        getCurrentPositionY());
                getCurrentPageContentStream().setFont(cellFont, getCurrentFontSize());
                getCurrentPageContentStream().showText(line);
                getCurrentPageContentStream().endText();

            }
            // got the next cell
//            float xIncrease = table.getColumns().get(cellNumber).getWidth();
            float xIncrease = table.getSubColumns().get(cellNumber).getWidth();
            setCurrentPosition(getCurrentPositionX() + xIncrease, rowY);
        }
    }
    
	private void drawRowContent(Table table, PDFont rowFont, Object[][] cellsContent, float lineHeight, float rowHeight,
			boolean checkColumnAlignment, Boolean... isSrcHeader) throws IOException {
		float rowY = getCurrentPositionY();
		for (int cellNumber = 0; cellNumber < cellsContent.length; cellNumber++) {
			TableColumn currentColumn = table.getSubColumns().get(cellNumber);
			PDFont cellFont = table.getSubColumns().get(cellNumber).getFont() != null
					? table.getSubColumns().get(cellNumber).getFont() : rowFont;

			int numberOfLinesInCell = cellsContent[cellNumber].length;
			float contentStartY = calculateRowContentStartY(rowY, currentColumn, table.getCellInsidePadding(),
					numberOfLinesInCell, lineHeight, rowHeight, checkColumnAlignment);
			setCurrentPosition(getCurrentPositionX(), contentStartY);

			for (int cellLineNumber = 0; cellLineNumber < numberOfLinesInCell; cellLineNumber++) {
				String line = cellsContent[cellNumber][cellLineNumber].toString();
				float lineWidth = calculateWidth(line, cellFont, getCurrentFontSize());
				setCurrentPosition(getCurrentPositionX(), getCurrentPositionY() - lineHeight);
				float contentStartX = calculateRowContentStartX(getCurrentPositionX(), currentColumn, lineWidth,
						table.getCellInsidePadding(), checkColumnAlignment, isSrcHeader);

				// draw the line
				getCurrentPageContentStream().beginText();
				getCurrentPageContentStream().newLineAtOffset(contentStartX, getCurrentPositionY());
				getCurrentPageContentStream().setFont(cellFont, getCurrentFontSize());
				getCurrentPageContentStream().showText(line);
				getCurrentPageContentStream().endText();

			}
			// got the next cell
			// float xIncrease = table.getColumns().get(cellNumber).getWidth();
			float xIncrease = table.getSubColumns().get(cellNumber).getWidth();
			setCurrentPosition(getCurrentPositionX() + xIncrease, rowY);
		}
	}

    /**
     * Calculate from where we should start writing first line in the cell based on text vertical alignment.
     * @param rowHeight is the sum of 2 x cellPadding and lineHeight X maxim number of lines in the row
     */
    private float calculateRowContentStartY(float cellTopY, TableColumn column, float cellPadding,
                                            int numberOfLinesInCell, float lineHeight, float rowHeight, boolean checkColumnAlignment) {
        if (!checkColumnAlignment || column.getVerticalAlignment() == TextVerticalAlignment.TOP) {
            return cellTopY - cellPadding;
        }

        float yDecrease; // moving down on the page
        switch (column.getVerticalAlignment()) {
            case TOP:
                yDecrease = cellPadding;
                break;
            case MIDDLE:
                yDecrease = (rowHeight-numberOfLinesInCell*lineHeight)/2-cellPadding;//rowHeight - cellPadding - (lineHeight * numberOfLinesInCell) / 2;
                break;
            case BOTTOM:
                yDecrease = rowHeight - cellPadding - (lineHeight * numberOfLinesInCell);
                break;
            default:
                yDecrease = 0;
                break;
        }

        return cellTopY - yDecrease;
    }

    /**
     * Calculate from where we should start writing the text in the cell based on text alignment.
     */
    private float calculateRowContentStartX(float cellLeftX, TableColumn column, float textWidth,
                                            float cellPadding, boolean checkColumnAlignment,Boolean ...isSrcHeader) {
        if(isSrcHeader.length>0 && isSrcHeader[0]){
        	return cellLeftX + (column.getWidth() - textWidth) / 2;
        }
    	if (!checkColumnAlignment) {
            return cellLeftX + cellPadding;
        }

        float xIncrease;
        switch (column.getAlignment()) {
            case LEFT:
                xIncrease = cellPadding;
                break;
            case CENTER:
                xIncrease = (column.getWidth() - textWidth) / 2;
                break;
            case RIGHT:
                xIncrease = column.getWidth() - textWidth - cellPadding;
                break;
            default:
                xIncrease = 0;
                break;
        }
        return cellLeftX + xIncrease;
    }
    
    private float calculateHeaderStartX(TextAlignment textAlignment, float textWidth,
            float pageWidth,float leftPadding) {
		
		float xIncrease;
		switch (textAlignment) {
			case LEFT:
			xIncrease = leftPadding;
			break;
			case CENTER:
			xIncrease = (pageWidth - textWidth) / 2;
			break;
			case RIGHT:
			xIncrease = pageWidth - textWidth-getContentLeftPadding() ;
			break;
			default:
			xIncrease = 0;
			break;
		}
		return  xIncrease;
    }
    

    /**
     * Split the text at space (" ") on a list of lines base on maxWidth.
     *
     * TODO support other word delimiter beside space (e.g. tab)
     *
     * @throws IOException If there is an error getting the width information.
     */
    private List<String> splitTextInLines(String text, float maxWidth, PDFont font, float fontSize)
            throws IOException {
        if (text == null) {
            return Collections.singletonList("");
        }

        float stringWidth = calculateWidth(text, font, fontSize);

        if (stringWidth <= maxWidth) {
            return new ArrayList<>(Arrays.asList(text));
        }

        List<String> lines = new ArrayList<>();
        
        StringBuilder lineBuilder =new StringBuilder();

        int tempStart = 0;
        for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			float charWidth =calculateWidth(String.valueOf(ch), font, fontSize);;
			float wordWidth = calculateWidth(lineBuilder.toString(), font, fontSize);
			
			if(wordWidth+charWidth >maxWidth){
				lines.add(lineBuilder.toString());
				 tempStart = i;
				 lineBuilder = new StringBuilder();
				 lineBuilder.append(ch);
				 
			}else{
				lineBuilder.append(ch);
			}
            if(i ==  text.length()-1){//最后一行  
            	lines.add( text.substring(tempStart));  
            }
			
		}
//        String[] words = text.split(" ");
//        float spaceWidth = calculateWidth(" ", font, fontSize);
//        float lineWidth = 0;
//        StringBuilder lineBuilder = new StringBuilder();
//        for (String word : words) {
//            float wordWidth = calculateWidth(word, font, fontSize);
//            if (lineWidth + wordWidth <= maxWidth) {
//                lineBuilder.append(" ");
//                lineBuilder.append(word);
//                lineWidth += spaceWidth + wordWidth;
//            } else {
//                // add one row
//                if (lineWidth == 0) {
//                    // this is the first word on the line
//                    lines.add(word);
//                } else {
//                    lines.add(lineBuilder.toString().trim());
//                    lineBuilder = new StringBuilder();
//                    lineBuilder.append(word);
//                    lineWidth = wordWidth;
//                }
//
//            }
//        }
//
//        if (lineBuilder.length() > 0) {
//            lines.add(lineBuilder.toString().trim());
//        }

        return lines;
    }

    /**
     * Draw column background for one row or headers.
     */
    private void drawColumnBackground(List<TableColumn> columns, float rowTopLeftX,
                                      float rowTopLefY, float rowHeight) throws IOException {
        // draw bg color
        float cellTopLeftX = rowTopLeftX;
        for (TableColumn column : columns) {
            if (column.getBackgroundColor() != null) {

                getCurrentPageContentStream().setNonStrokingColor(column.getBackgroundColor());
                getCurrentPageContentStream().addRect(cellTopLeftX, rowTopLefY - rowHeight,
                        column.getWidth(), rowHeight);
                getCurrentPageContentStream().fill();
                getCurrentPageContentStream().setNonStrokingColor(Color.BLACK);

            }
            cellTopLeftX += column.getWidth();
        }
    }
    private void drawNestingRowGrid(Table table,List<TableColumn> columns, float topLeftCornerX,
            float topLeftCornerY, float rowHeight) throws IOException {
        float rowTopLeftCornerX = topLeftCornerX;
        boolean previousColumnDrawGrid = false;
        int level = table.getLevel();
        for (TableColumn column : columns) {
        	List<TableColumn> childColumns = column.getChildColumns();
        	int rowSpan =0;
        	if(childColumns == null || childColumns.size() ==0)
        		rowSpan=level;
        	else {
        		rowSpan = 1;
        	}
            boolean currentDrawGrid = !column.isHideGrid();
            if (currentDrawGrid || previousColumnDrawGrid) {
                // left line

                getCurrentPageContentStream().moveTo(rowTopLeftCornerX, topLeftCornerY);
                getCurrentPageContentStream().lineTo(rowTopLeftCornerX, topLeftCornerY-rowSpan*rowHeight);
                getCurrentPageContentStream().stroke();
            }
            if (currentDrawGrid) {
                // top line

                getCurrentPageContentStream().moveTo(rowTopLeftCornerX, topLeftCornerY);
                getCurrentPageContentStream().lineTo(rowTopLeftCornerX + column.getWidth(),topLeftCornerY);
                getCurrentPageContentStream().stroke();
                // bottom line

                getCurrentPageContentStream().moveTo(rowTopLeftCornerX, topLeftCornerY -rowSpan* rowHeight);
                getCurrentPageContentStream().lineTo(rowTopLeftCornerX + column.getWidth(), topLeftCornerY -rowSpan* rowHeight);
                getCurrentPageContentStream().stroke();
            }
              float childRowTopLeftCornerX=rowTopLeftCornerX;
            if (childColumns != null) {
				for (TableColumn child : childColumns) {
					if (currentDrawGrid || previousColumnDrawGrid) {
						// left line

						getCurrentPageContentStream().moveTo(childRowTopLeftCornerX, topLeftCornerY - rowHeight);
						getCurrentPageContentStream().lineTo(childRowTopLeftCornerX, topLeftCornerY - level * rowHeight);
						getCurrentPageContentStream().stroke();
					}
					if (currentDrawGrid) {
						// top line

						getCurrentPageContentStream().moveTo(childRowTopLeftCornerX, topLeftCornerY - rowHeight);
						getCurrentPageContentStream().lineTo(childRowTopLeftCornerX + child.getWidth(), topLeftCornerY- rowHeight);
						getCurrentPageContentStream().stroke();
						// bottom line

						getCurrentPageContentStream().moveTo(childRowTopLeftCornerX, topLeftCornerY - level * rowHeight);
						getCurrentPageContentStream().lineTo(childRowTopLeftCornerX + child.getWidth(),
								topLeftCornerY - level * rowHeight);
						getCurrentPageContentStream().stroke();
					}
					
					childRowTopLeftCornerX += child.getWidth();
				} 
			}
			// next
            rowTopLeftCornerX += column.getWidth();
            previousColumnDrawGrid = currentDrawGrid;
        }
        if (previousColumnDrawGrid) {
            // last right line
            getCurrentPageContentStream().moveTo(rowTopLeftCornerX, topLeftCornerY);
            getCurrentPageContentStream().lineTo(rowTopLeftCornerX, topLeftCornerY -level* rowHeight);
            getCurrentPageContentStream().stroke();
        }

    }
    /**
     *
     * Draw grid for a row table.
     */
    private void drawRowGrid(List<TableColumn> columns, float topLeftCornerX,
                             float topLeftCornerY, float rowHeight,int ... rowNumber) throws IOException {
        float rowTopLeftCornerX = topLeftCornerX;
        boolean previousColumnDrawGrid = false;
        List<TableColumn> subColumns = new ArrayList<>();
        for (TableColumn tableColumn : columns) {
        	if(tableColumn.getChildColumns() == null){
        		subColumns.add(tableColumn);
        	}
        	else{
        		for (TableColumn subColumn : tableColumn.getChildColumns()) {
        			subColumns.add(subColumn);
				}
        	}
        		
		}
        for (TableColumn column : subColumns) {
            boolean currentDrawGrid = !column.isHideGrid();
            boolean isColSpan =( rowNumber.length >0 && rowNumber[0] == getMaxLineNum()&& column.getHeader()=="科目");
            if ((currentDrawGrid || previousColumnDrawGrid)&& !isColSpan) {
                // left line

                getCurrentPageContentStream().moveTo(rowTopLeftCornerX, topLeftCornerY);
                getCurrentPageContentStream().lineTo(rowTopLeftCornerX, topLeftCornerY-rowHeight);
                getCurrentPageContentStream().stroke();
            }
            if (currentDrawGrid) {
                // top line

                getCurrentPageContentStream().moveTo(rowTopLeftCornerX, topLeftCornerY);
                getCurrentPageContentStream().lineTo(rowTopLeftCornerX + column.getWidth(),topLeftCornerY);
                getCurrentPageContentStream().stroke();
                // bottom line

                getCurrentPageContentStream().moveTo(rowTopLeftCornerX, topLeftCornerY - rowHeight);
                getCurrentPageContentStream().lineTo(rowTopLeftCornerX + column.getWidth(), topLeftCornerY - rowHeight);
                getCurrentPageContentStream().stroke();
            }
            // next
            rowTopLeftCornerX += column.getWidth();
            previousColumnDrawGrid = currentDrawGrid;
        }
        if (previousColumnDrawGrid) {
            // last right line

            getCurrentPageContentStream().moveTo(rowTopLeftCornerX, topLeftCornerY);
            getCurrentPageContentStream().lineTo(rowTopLeftCornerX, topLeftCornerY - rowHeight);
            getCurrentPageContentStream().stroke();
        }

    }

    /**
     * Calculate height for font and size.
     *
     * @param font font use to draw the line, If null use the current one
     * @param aFontSize font size
     * @return height of the string
     * @throws IOException If there is an error getting the width information.
     */
    public float calculateHeight(PDFont font, float aFontSize) {
        return font.getFontDescriptor().getFontBoundingBox().getHeight() / FONT_FACTOR * aFontSize;
    }

    /**
     * Calculate necessary with for the text using specified font and size.
     *
     * @param string the string for which we want to calculate width
     * @param font font use to write the string
     * @param aFontSize font size
     * @return the width of the string
     * @throws IOException If there is an error getting the width information.
     */
    public float calculateWidth(String string, PDFont font, float aFontSize) throws IOException {
        return font.getStringWidth(string) / FONT_FACTOR * aFontSize;
    }

    /**
     * This will load a document from an input stream.
     *
     * @param input The stream that contains the document.
     * @return The document that was loaded.
     * @throws IOException If there is an error reading from the stream.
     */
    public static PageablePdf load(InputStream input) throws IOException {
        PDDocument pdDocument = PDDocument.load(input);
        return new PageablePdf(pdDocument.getDocument());
    }

    /**
     * This method should be call before saving the pdf. It is responsible for closing the current page content stream.
     * adding the current page to the document and drawing the header and footer.
     */
    public void closeDocument() throws IOException {
        closeCurrentPageContentStream();
        drawHeaderAndFooter();
    }
    
    
    public void initTableHeader() throws IOException{
    	
    }

    /**
     * Draw header and footer on each page. This should be called before closing saving the document.
     *
     * @throws IOException If the underlying stream has a problem being written to.
     */
    protected void drawHeaderAndFooter() throws IOException {
        for (int i = 0; i < getDocumentCatalog().getPages().getCount(); i++) {
            PDPage page = (PDPage) getDocumentCatalog().getPages().get(i);
            try (PDPageContentStream contentStream = new PDPageContentStream(this, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                if(isLandscape()){
                	contentStream.transform(new Matrix(0, 1, -1, 0, getPageWidth(), 0));
                    }
                drawPageHeader(contentStream);
                drawPageFooter(contentStream, i + 1);
            }
        }
    }

    /**
     * By default we don't add any header to the document, but if we want a header you have to override it.
     * If we override we have to set the top padding big enough to accommodate it.
     *
     * TODO add a setter for headers so we can add a header without extend this class.
     */
    protected void drawPageHeader(PDPageContentStream contentStream) {
        // do nothing
    }

    protected void drawPageFooter(PDPageContentStream pageContentStream, int pageNumber)
            throws IOException {

        float lineHeight = calculateHeight(footerFont, footerFontSize);
        float lineY = lineHeight * getFooterLines().size() + footerBottomPadding;

        if (includePageNumber) {
            lineY += lineHeight;
        }

        for (String line : getFooterLines()) {
            float lineWidth = calculateWidth(line, footerFont, footerFontSize);
            pageContentStream.beginText();
            pageContentStream.setFont(footerFont, footerFontSize);
            pageContentStream.newLineAtOffset((this.isLandscape ? getPageHeight():getPageWidth() - lineWidth) / 2, lineY);
            pageContentStream.showText(line);
            pageContentStream.endText();

            lineY = lineY - lineHeight;
        }

        if (includePageNumber) {
            drawPageNumber(pageContentStream, pageNumber, lineY);
        }
        if(pageContentStream != null){
        	pageContentStream.close();
        }
    }

    protected void drawPageNumber(PDPageContentStream pageContentStream, int pageNumber, float lineY) throws IOException {
        String pageNumberMessage =
                Integer.toString(pageNumber) + " / " + this.getDocumentCatalog().getPages().getCount();
        float lineWidth = calculateWidth(pageNumberMessage, footerFont, footerFontSize);
        pageContentStream.beginText();
        pageContentStream.setFont(footerFont, footerFontSize);
        pageContentStream.newLineAtOffset((this.isLandscape?getPageHeight(): getPageWidth() - lineWidth) / 2, lineY);
        pageContentStream.showText(pageNumberMessage);
        pageContentStream.endText();
    }

    public void setFooterLines(List<String> footerLines) {
        this.footerLines = footerLines;
    }

    @SuppressWarnings("unchecked")
	protected List<String> getFooterLines() {
        return (List<String>) (footerLines != null ? footerLines : Collections.emptyList());
    }

    /**
     * @return the currentPositionX
     */
    public float getCurrentPositionX() {
        return currentPositionX;
    }

    /**
     * @param currentPositionX the currentPositionX to set
     */
    public void setCurrentPositionX(float currentPositionX) {
        this.currentPositionX = currentPositionX;
    }

    /**
     * @return the currentPositionY
     */
    public float getCurrentPositionY() {
        return currentPositionY;
    }

    /**
     * @param currentPositionY the currentPositionY to set
     */
    public void setCurrentPositionY(float currentPositionY) {
        this.currentPositionY = currentPositionY;
    }


    public PDFont getFontBold() {
        return fontBold;
    }

    public void setFontBold(PDFont fontBold) {
        this.fontBold = fontBold;
    }

    public PDFont getCurrentFont() {
        return currentFont;
    }

    public void setCurrentFont(PDFont currentFont) {
        this.currentFont = currentFont;
    }

    public float getCurrentFontSize() {
        return currentFontSize;
    }

    public void setCurrentFontSize(float currentFontSize) {
        this.currentFontSize = currentFontSize;
    }

    public float getPageHeight() {
        return pageHeight;
    }

    public float getPageWidth() {
        return pageWidth;
    }

    public float getContentTopPadding() {
        return contentTopPadding;
    }

    public void setContentTopPadding(float contentTopPadding) {
        this.contentTopPadding = contentTopPadding;
    }

    public float getContentRightPadding() {
        return contentRightPadding;
    }

    public void setContentRightPadding(float contentRightPadding) {
        this.contentRightPadding = contentRightPadding;
    }

    public float getContentBottomPadding() {
        return contentBottomPadding;
    }

    public void setContentBottomPadding(float contentBottomPadding) {
        this.contentBottomPadding = contentBottomPadding;
    }

    public float getContentLeftPadding() {
        return contentLeftPadding;
    }

    public void setContentLeftPadding(float contentLeftPadding) {
        this.contentLeftPadding = contentLeftPadding;
    }

    public float getHeadingFontSize() {
        return headingFontSize;
    }

    public void setHeadingFontSize(float headingFontSize) {
        this.headingFontSize = headingFontSize;
    }

    public PDFont getHeadingFont() {
        return headingFont;
    }

    public void setHeadingFont(PDFont headingFont) {
        this.headingFont = headingFont;
    }

    public float getHeadingBottomPadding() {
        return headingBottomPadding;
    }

    public void setHeadingBottomPadding(float headingBottomPadding) {
        this.headingBottomPadding = headingBottomPadding;
    }

    public float getHeadingTopPadding() {
        return headingTopPadding;
    }

    public void setHeadingTopPadding(float headingTopPadding) {
        this.headingTopPadding = headingTopPadding;
    }

    public float getParagraphPadding() {
        return paragraphPadding;
    }

    public void setParagraphPadding(float paragraphPadding) {
        this.paragraphPadding = paragraphPadding;
    }

    public PDFont getFontNormal() {
        return fontNormal;
    }

    public void setFontNormal(PDFont fontNormal) {
        this.fontNormal = fontNormal;
    }

    public float getFooterBottomPadding() {
        return footerBottomPadding;
    }

    public void setFooterBottomPadding(float footerBottomPadding) {
        this.footerBottomPadding = footerBottomPadding;
    }

    public float getFooterFontSize() {
        return footerFontSize;
    }

    public void setFooterFontSize(float footerFontSize) {
        this.footerFontSize = footerFontSize;
    }

    public PDFont getFooterFont() {
        return footerFont;
    }

    public void setFooterFont(PDFont footerFont) {
        this.footerFont = footerFont;
    }

    public boolean isIncludePageNumber() {
        return includePageNumber;
    }

    public void setIncludePageNumber(boolean includePageNumber) {
        this.includePageNumber = includePageNumber;
    }

	public boolean isLandscape() {
		return isLandscape;
	}

	public void setLandscape(boolean isLandscape) {
		this.isLandscape = isLandscape;
	}

	public float getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(float rowHeight) {
		this.rowHeight = rowHeight;
	}

	public Integer getMaxLineNum() {
		return maxLineNum;
	}

	public void setMaxLineNum(Integer maxLineNum) {
		this.maxLineNum = maxLineNum;
	}

	public boolean isSampPageContinuePrint() {
		return isSampPageContinuePrint;
	}

	public void setSampPageContinuePrint(boolean isSampPageContinuePrint) {
		this.isSampPageContinuePrint = isSampPageContinuePrint;
	}

	public float getNextPositionY() {
		return nextPositionY;
	}

	public void setNextPositionY(float nextPositionY) {
		this.nextPositionY = nextPositionY;
	}
    
}
