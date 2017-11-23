package com.mk.eap.component.common.pdfbox.template;

import com.mk.eap.component.pdf.adapter.RealDataAdapter;
import com.mk.eap.component.pdf.adapter.Target;
import com.mk.eap.component.pdf.convert.NumberToChineseBig;
import com.mk.eap.component.pdf.strategy.CertificateTemplateFor2;
import com.mk.eap.component.pdf.strategy.CertificateTemplateFor3;
import com.mk.eap.component.pdf.strategy.TemplateStrategy;
import com.mk.eap.component.pdfboxtable.table.Table;
import com.mk.eap.component.pdfboxtable.table.TemplateHeadInfo;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.*;

import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 此类是填充pdf表单数据的接口
 * @author thinkpad
 * @param <T>
 *
 */
public class FillPdfTemplateUtil {
	
	
//	
//	private final static String A4B2="A4(2)";
//	
//	private final static String A4B3="A4(3)";
	
	private  String fontSizeInfo="/Helv %f Tf 0 g";
	/**
	 * 传入内容：适用于凭证这类模板
	 * 
	 */
	private List<List<Map<String, Object>>> srcObject;
	/**
	 * 传入内容方式为key和value，即key:位置【行】【列】,value:填入的值
	 */
	private Map<String, String> fieldAndValue;
	
	private Map<String, Object[][]> dataMap;
	
//	private String templateType="A4(2)";
	/**
	 * 目标模板名称
	 */
	private String descFile;
	/**
	 * 来源模板
	 */
	private String srcFileName;
	/**
	 * 字体大小
	 */
	private float fontSize=10f;
	/**
	 * 是否来源于凭证
	 */
	private  Boolean isSrcGL = false;
	/**
	 * 凭证模板类型例如：a4两版=A4(2)、三版=A4(3)暂时就这两种，枚举应该是合理的，暂时用数字标识
	 */
//	private String templateType="A4(2)";
	
	
	private TemplateTypeEnum templateType;
	
	private TemplateHeadInfo templateHeadInfo;
	
	/**
	 * 传入内容方式为二维数组：即【行坐标】【列列坐标】对应的值，把坐标作为Filed的位置
	 */
	private String[][] tableContents;
	/**
	 * 传入内容方式为集合：集合中数组即为每列的值
	 */
	private List<String[]> listContent;
	
	private String returnPdfPath;
	
	private byte[] returnBytyArray;
	
	private   PDFont currentFont;
	
	private PDAcroForm pdAcroForm;
	
	private List<Table> returnTables;
	

	
	public FillPdfTemplateUtil() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 用于模板打印，例如：凭证
	 * @param srcObject
	 * @param srcFileName
	 * @param templateType
	 * @throws IOException
	 * @throws PrinterException
	 */
	public FillPdfTemplateUtil(List<List<Map<String, Object>>> srcObject, String srcFileName, TemplateTypeEnum templateType,Integer ... maxLineNum) throws IOException, PrinterException {
		this.srcObject = srcObject;
		this.srcFileName = srcFileName;
		this.templateType = templateType;
		buildTemp(srcObject, srcFileName, templateType,maxLineNum);
	}
	

	/**
	 * 
	 * 用于总账、明细账、余额表及其对应辅助帐的打印使用
	 * @param srcFileName 报表名称
	 * @param dataMap Map<String, Object[][]> 类型：key为主键：科目+日期 ；value为内容值二维数组的方式
	 * @param templateType TemplateTypeEnum
	 * @throws IOException
	 * @throws PrinterException
	 */
	public FillPdfTemplateUtil(String srcFileName,Map<String, Object[][]> dataMap,TemplateTypeEnum templateType) throws IOException, PrinterException {
		this.dataMap = dataMap;
		this.srcFileName = srcFileName;
		buildTemp(dataMap,srcFileName,templateType);
		
	}

	private void buildTemp(Map<String, Object[][]> dataMap, String srcFileName, TemplateTypeEnum templateType) throws IOException, PrinterException {
		TemplateStrategy strategy = null;
		
		switch (templateType) {
//		case GL:
//			strategy = new GeneralLedgerRpt();
//			strategy.batchOperate(dataMap, srcFileName,templateType);
//			setReturnPdfPath( strategy.getPdfPath());
//			break;
//		case SGL:
//			strategy = new SubsidiaryGeneralLedgerRpt();
//			strategy.batchOperate(dataMap, srcFileName,templateType);
//			setReturnPdfPath( strategy.getPdfPath());
//			break;
//		case SL:
//			strategy = new SubsidiaryDetailAccountRpt();
//			strategy.batchOperate(dataMap, srcFileName,templateType);
//			setReturnPdfPath( strategy.getPdfPath());
//			break;
//		case DA:
//			strategy = new DetailAccountRpt();
//			strategy.batchOperate(dataMap, srcFileName,templateType);
//			setReturnPdfPath( strategy.getPdfPath());
//			break;
//		case BS:
//			strategy = new BalancesSheetRpt();
//			strategy.batchOperate(dataMap, srcFileName,templateType);
//			setReturnPdfPath( strategy.getPdfPath());
//			break;
//		case ASBS:
//			strategy = new AccountSubsidiaryBalancesSheetRpt();
//			strategy.batchOperate(dataMap, srcFileName,templateType);
//			setReturnPdfPath( strategy.getPdfPath());
//			break;
//		case SABS:
//			strategy = new SubsidiaryAcountBalancesSheetRpt();
//			strategy.batchOperate(dataMap, srcFileName,templateType);
//			setReturnPdfPath( strategy.getPdfPath());
//			break;

		default:
			break;
		}
	}
	private void buildTemp(List<List<Map<String, Object>>> srcObject, String srcFileName, TemplateTypeEnum templateType,Integer ... maxLineNum) throws IOException, PrinterException {
		TemplateStrategy strategy = null;
		switch (templateType) {
		case A4B2:
			strategy = new CertificateTemplateFor2();
			strategy.batchOperate(getTargetDataMap(srcObject), srcFileName,templateType,maxLineNum);
//			setReturnPdfPath( strategy.getPdfPath());
//			
//			setReturnBytyArray(strategy.getByteArray());
			setReturnTables(((CertificateTemplateFor2)strategy).getReturnTables());
			
			break;
			
		case A4B3:
			strategy = new CertificateTemplateFor3();
			strategy.batchOperate(getTargetDataMap(srcObject), srcFileName,templateType);
//			setReturnPdfPath( strategy.getPdfPath());
//			setReturnBytyArray(strategy.getByteArray());
			setReturnTables(((CertificateTemplateFor3)strategy).getReturnTables());
			break;

		default:
			break;
		}
	}


	/**
	 * 模板模板构造函数，适用于凭证模板的打印的实现,批量+单张打印
	 * @param srcObject：多单单据，一单对应多张明细的参数
	 * @param srcFile
	 * @throws IOException 
	 * @throws PrinterException 
	 */
//	public FillPdfTemplateUtil(List<List<Map<String, Object>>> srcObject, String srcFileName) throws IOException, PrinterException {
//
//		this.srcObject = srcObject;
//		this.srcFileName = srcFileName;
//		
//		buildTemp(srcObject, srcFileName, templateType);
//	}


	private LinkedHashMap<String, Object[][]> getTargetDataMap(List<List<Map<String, Object>>> srcObject) {
		//convert from srcObject To descObject
		Target target = new RealDataAdapter(srcObject);
		return  target.dataTransfer();
	}


	/**
	 * 构造方法2个参数
	 * @param fieldAndValue 要填充pdf表单的key及其value
	 * @param srcFile 模板全名（路径+名称）
	 * @throws IOException
	 */
	
//	public FillPdfTemplateUtil(Map<String, String> fieldAndValue, String srcFileName) throws IOException {
//	
//		this.fieldAndValue = fieldAndValue;
//		this.srcFileName = srcFileName;
//		createPDDocument(fieldAndValue, srcFileName);
//	}


	/**
	 * 不使用此构造方法，请用模板类型或者绘制的类型构造方法
	 * 构造方法3个参数
	 * @param fieldAndValue 要填充pdf表单的key及其value
	 * @param srcFile 模板全名（路径+名称）
	 * @param fontSize 字体大小
	 * @throws IOException
	 */
//	@Deprecated
//	public FillPdfTemplateUtil(Map<String, String> fieldAndValue, String srcFileName, float fontSize) throws IOException {
//	
//		this(fieldAndValue,srcFileName);
//		this.fontSize = fontSize;
//		createPDDocument(fieldAndValue, srcFileName);
//	}

	/**
	 * 适用于资产负债
	 * @param srcFileName
	 * @param tableContents
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */

//	public FillPdfTemplateUtil(TemplateTypeEnum  templateType,String srcFileName, String[][] tableContents) throws IOException {
//		this.templateType = templateType;
//		this.srcFileName = srcFileName;
//		this.tableContents = tableContents;
//		createPDDocument(tableContents, srcFileName);
//	}
	public FillPdfTemplateUtil(TemplateTypeEnum  templateType,TemplateHeadInfo templateHeadInfo, String[][] tableContents) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.templateType = templateType;
		this.templateHeadInfo = templateHeadInfo;
		this.tableContents = tableContents;
		createPDDocument(tableContents, templateHeadInfo);
	}
	

//	@Deprecated
//	public FillPdfTemplateUtil(String srcFileName, List<String[]> listContent) throws IOException {
//
//		this.srcFileName = srcFileName;
//		this.listContent = listContent;
//		createPDDocument(listContent, srcFileName);
//	}


	private void createPDDocument(String[][] tableContents,TemplateHeadInfo templateHeadInfo) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		PDDocument pdfDocument = PDDocument.load(this.getClass().getResourceAsStream(FilePathUtil.getRealPath(this)));
		setField(pdfDocument, tableContents,templateHeadInfo);
		ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
		pdfDocument.save(outPutStream);
		setReturnBytyArray(outPutStream.toByteArray());
		pdfDocument.close();
	}
//	private void createPDDocument(String[][] tableContents, String srcFileName) throws IOException {
//		PDDocument pdfDocument = PDDocument.load(new File(FilePathUtil.getRealPath(this)));
//		setField(pdfDocument, tableContents);
//		String fileName=FilePathUtil.SAVEFILE+"/"+FilePathUtil.getOnlyKey()+".pdf";
//		pdfDocument.save(fileName);
//		pdfDocument.close();
//		setReturnPdfPath(fileName);
//	}
//	private void createPDDocument(List<String[]> listContent, String srcFileName) throws IOException {
//		PDDocument pdfDocument = PDDocument.load(new File(FilePathUtil.getRealPath(this)));
//		setField(pdfDocument, listContent);
//		pdfDocument.close();
//	}



	public Map<String, String> getFieldAndValue() {
		return fieldAndValue;
	}


	public void setFieldAndValue(Map<String, String> fieldAndValue) {
		this.fieldAndValue = fieldAndValue;
	}


	public String[][] getTableContents() {
		return tableContents;
	}


	public void setTableContents(String[][] tableContents) {
		this.tableContents = tableContents;
	}


	public List<String[]> getListContent() {
		return listContent;
	}


	public void setListContent(List<String[]> listContent) {
		this.listContent = listContent;
	}


	public float getFontSize() {
		return fontSize;
	}


	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	
	public TemplateTypeEnum getTemplateType() {
		return templateType;
	}


	public void setTemplateType(TemplateTypeEnum templateType) {
		this.templateType = templateType;
	}


	public String getDescFile() {
		return descFile;
	}


	public void setDescFile(String descFile) {
		this.descFile = descFile;
	}


	public Boolean getIsSrcGL() {
		return isSrcGL;
	}


	public void setIsSrcGL(Boolean isSrcGL) {
		this.isSrcGL = isSrcGL;
	}


	public String getSrcFile() {
		return srcFileName;
	}


	public void setSrcFile(String srcFileName) {
		this.srcFileName = srcFileName;
	}
	


	public List<List<Map<String, Object>>> getSrcObject() {
		return srcObject;
	}


	public void setSrcObject(List<List<Map<String, Object>>> srcObject) {
		this.srcObject = srcObject;
	}
	


	public Map<String, Object[][]> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object[][]> dataMap) {
		this.dataMap = dataMap;
	}
	public  void setField(PDDocument pdfDocument,Object[][] tableContents,TemplateHeadInfo templateHeadInfo) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		PDAcroForm acroForm = interactiveConstructor(pdfDocument);
//		PDAcroForm acroForm = null;//interactiveConstructor(pdfDocument);
//		if(getPdAcroForm() == null){
//			setPdAcroForm(interactiveConstructor(pdfDocument));
//		}
//		acroForm = getPdAcroForm();
		 if(acroForm == null)return;
		 
		 if(templateHeadInfo != null){
			 Field[] fields = templateHeadInfo.getClass().getDeclaredFields();
			 if(fields != null && fields.length >0){
				 for (Field field : fields) {
						String fieldName = field.getName();
						if(fieldName.equals("serialVersionUID")) continue;
						 String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1); 
						 Method m = this.getTemplateHeadInfo().getClass().getMethod(methodName);
						 String fieldValue = (String) m.invoke(templateHeadInfo);
						 if(fieldValue != null && !fieldValue.isEmpty()){
							 setField(acroForm, fieldName, fieldValue);
						 }
				}
			 }
		 }
		
		 for (int row = 0; row < tableContents.length; row++) {
			Object[] rows = tableContents[row];
			if(rows == null || rows.length <0) continue;
			for (int column = 0; column < rows.length; column++) {
				String fieldValue = rows[column]== null ? null:rows[column].toString();
				 if(fieldValue != null && !fieldValue.isEmpty()){
					 String fieldName = String.valueOf(row).concat(String.valueOf(column));
					 setField(acroForm, fieldName, fieldValue);
				 }	
			}
			
		}
		
		
	}
	public  void setField(PDDocument pdfDocument,Object[][] tableContents,boolean ... isSrcCertificate) throws IOException{
		PDAcroForm acroForm = interactiveConstructor(pdfDocument);
//		PDAcroForm acroForm = null;//interactiveConstructor(pdfDocument);
//		if(getPdAcroForm() == null){
//			setPdAcroForm(interactiveConstructor(pdfDocument));
//		}
//		acroForm = getPdAcroForm();
		 if(acroForm == null)return;
		 
		 boolean isCertificate = false;
		 if(isSrcCertificate.length>0 && isSrcCertificate[0]){
			 isCertificate = true;
		 }
		
		 for (int row = 0; row < tableContents.length; row++) {
			Object[] rows = tableContents[row];
			if(rows == null || rows.length <0) continue;
			int columncounts = rows.length;
			if(isCertificate){
				columncounts=4;
			}
			for (int column = 0; column < columncounts; column++) {			
				String fieldValue = null;
				if(isCertificate){
					if(column==2 ||column ==3){
						fieldValue = rows[column]== null ? null:String.format("%.2f",Double.valueOf(rows[column].toString()).doubleValue()) ;
					}else{
						fieldValue = rows[column]== null ? null:rows[column].toString();
					}
					
				}else
				{
				 fieldValue = rows[column]== null ? null:rows[column].toString();
				}
				if(fieldValue != null && !fieldValue.isEmpty()){
					String fieldName = String.valueOf(row).concat(String.valueOf(column));
					setField(acroForm, fieldName, fieldValue);
				}

				
			}
			
		}
		
		
	}
	public void setSum(PDDocument pdfDocument,String sumDr,String sumCr,int firstFew,Object[] columnsData,String ...tablePercentTableAll) throws IOException{
		PDAcroForm acroForm = interactiveConstructor(pdfDocument);
//		PDAcroForm acroForm = null;//interactiveConstructor(pdfDocument);
//		if(getPdAcroForm() == null){
//			setPdAcroForm(interactiveConstructor(pdfDocument));
//		}
//		acroForm = getPdAcroForm();
		 if(acroForm == null)return;
		String borrowName = "borrowsum"+firstFew;
		String  loanName = "loansum"+firstFew;
	
		setField(acroForm, borrowName, sumDr);
		setField(acroForm, loanName, sumCr);
		if(columnsData != null){
			setTitleField(acroForm,columnsData,firstFew,tablePercentTableAll);
		}

	}

	private void setTitleField(PDAcroForm acroForm, Object[] columnsData,int firstFew,String ...tablePercentTableAll) throws IOException {
		
		String accountingUnitName = "accountingUnit"+firstFew;
		String voucherDateName = "voucherDate"+firstFew;
		String codeName = "code"+firstFew;
		String attachedVoucherNumName = "attachedVoucherNum"+firstFew;
		String makerName = "maker"+firstFew;
		String bookkeeperName = "bookkeeper"+firstFew;
		String auditorName = "auditor"+firstFew;
		String cashierName = "cashier"+firstFew;
		setField(acroForm, accountingUnitName, columnsData[8].toString());
		setField(acroForm, voucherDateName, columnsData[6].toString());
		if(tablePercentTableAll.length>0 && !tablePercentTableAll[0].isEmpty()){
			setField(acroForm, codeName,columnsData[4].toString()+"-"+ columnsData[5].toString()+"-"+tablePercentTableAll[0]);
		}else {
			setField(acroForm, codeName,columnsData[4].toString()+"-"+ columnsData[5].toString());
		}
		setField(acroForm, attachedVoucherNumName, columnsData[7].toString());
		setField(acroForm, makerName, columnsData[9].toString());
		setField(acroForm, accountingUnitName, columnsData[8].toString());
		if(columnsData[10] != null){
			setField(acroForm, auditorName, columnsData[10].toString());
		}
	}

	public void setChineseSum(PDDocument pdfDocument, String sum, int firstFew) throws IOException {
		PDAcroForm acroForm = interactiveConstructor(pdfDocument);
//		PDAcroForm acroForm = null;//interactiveConstructor(pdfDocument);
//		if(getPdAcroForm() == null){
//			setPdAcroForm(interactiveConstructor(pdfDocument));
//		}
//		acroForm = getPdAcroForm();
		if (acroForm == null)
			return;

		String sumName = "sum" + firstFew;

		BigDecimal money = new BigDecimal(sum);
		String sumChinese = NumberToChineseBig.number2CNMontrayUnit(money);

		setField(acroForm, sumName, sumChinese);

	}
	public void setField(PDDocument pdfDocument,List<String[]> listContent) throws IOException{
		PDAcroForm acroForm = interactiveConstructor(pdfDocument);
//		PDAcroForm acroForm = null;//interactiveConstructor(pdfDocument);
//		if(getPdAcroForm() == null){
//			setPdAcroForm(interactiveConstructor(pdfDocument));
//		}
//		acroForm = getPdAcroForm();
		 if(acroForm == null)return;
		 for (int row = 0; row < listContent.size(); row++) {
			String[] rows = listContent.get(row);
			for (int column = 0; column < rows.length; column++) {
				String fieldName = String.valueOf(row).concat(String.valueOf(column));
				String fieldValue = rows[column];
				setField(acroForm, fieldName, fieldValue);
				
			}
			
		}

		 
	}

	/**
	 * 为模板中对应的字段key赋值
	 * @param pdfDocument pdf文档对象
	 * @param fieldAndValue key和value
	 * @throws IOException
	 */
	public void setField(PDDocument pdfDocument,Map<String, String> fieldAndValue) throws IOException
    {
        PDAcroForm acroForm = interactiveConstructor(pdfDocument);
//		PDAcroForm acroForm = null;//interactiveConstructor(pdfDocument);
//		if(getPdAcroForm() == null){
//			setPdAcroForm(interactiveConstructor(pdfDocument));
//		}
//		acroForm = getPdAcroForm();
        if(acroForm == null)return;
        for ( Entry<String, String> field2value : fieldAndValue.entrySet()) {
        	
        	String fieldName = field2value.getKey();
        	String fieldValue = field2value.getValue();
 	        setField(acroForm, fieldName, fieldValue);
        }

    }


	public  void setField(PDAcroForm acroForm, String fieldName, String fieldValue) throws IOException {
		PDField field = acroForm.getField(fieldName);
		if (field != null){
	
		    if (field instanceof PDTextField){
		        //set font size
		    	String fontInfo = String.format(fontSizeInfo, getFontSize());
		        ((PDTextField)field).setDefaultAppearance(fontInfo);
		        ((PDTextField)field).setValue(fieldValue);
//		        ((PDTextField)field).setPartialName(fieldName+"end");//设置完后把名称修改，要不然多页的话name相同就会覆盖

		    } 
		    else if (field instanceof PDCheckBox){
		         ((PDCheckBox)field).setValue(fieldValue);

		    }
		    else if (field instanceof PDComboBox){
		        ((PDComboBox)field).setValue(fieldValue);

		    }
		    else if (field instanceof PDListBox){
		        ((PDListBox)field).setValue(fieldValue);
		        
		    }
		    else if (field instanceof PDRadioButton){
		        ((PDRadioButton)field).setValue(fieldValue);
		        
		    }

		}
	}


	public  PDAcroForm interactiveConstructor(PDDocument pdfDocument) throws IOException {
		PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog(); 
        //暂时写死，本项目文件夹Fonts下取字体simkai.ttf  STSONG.TTF  msyh.ttf
//        PDFont font = PDType0Font.load(pdfDocument, new File("C:/Windows/Fonts/simkai.ttf"));
		if(currentFont == null){
			setCurrentFont(PDType0Font.load(pdfDocument,Thread.currentThread().getClass().getResourceAsStream("/fonts/simkai.ttf"),true));
		}
		PDFont font = getCurrentFont();// PDType0Font.load(pdfDocument,Thread.currentThread().getClass().getResourceAsStream("/fonts/simkai.ttf"),true);
        PDResources resource = new PDResources();
        
        resource.put(COSName.getPDFName("Helv"), font);
        
        PDAcroForm acroForm = docCatalog.getAcroForm();

        acroForm.setDefaultResources(resource);
		return acroForm;
	}
	private String getFontPath() throws URISyntaxException{
		//set fontdir
		String systemName = System.getProperty("os.name");
		if(systemName.startsWith("Linux")){
			 File file= new File(Thread.currentThread().getClass().getResource("/.fonts").toURI());
			 if(file.exists()){
				 String userHomePath = file.getParent();
				 System.setProperty("user.home", userHomePath);
			 }
		}		
		return "/.fonts/simkai.ttf";
	}
	
	public String getReturnPdfPath() {
		return returnPdfPath;
	}
	public void setReturnPdfPath(String returnPdfPath) {
		this.returnPdfPath = returnPdfPath;
	}
	public byte[] getReturnBytyArray() {
		return returnBytyArray;
	}
	public void setReturnBytyArray(byte[] returnBytyArray) {
		this.returnBytyArray = returnBytyArray;
	}
	public TemplateHeadInfo getTemplateHeadInfo() {
		return templateHeadInfo;
	}
	public void setTemplateHeadInfo(TemplateHeadInfo templateHeadInfo) {
		this.templateHeadInfo = templateHeadInfo;
	}
	public  PDFont getCurrentFont() {
		return currentFont;
	}
	public  void setCurrentFont(PDFont currentFont) {
		this.currentFont = currentFont;
	}
	public PDAcroForm getPdAcroForm() {
		return pdAcroForm;
	}
	public void setPdAcroForm(PDAcroForm pdAcroForm) {
		this.pdAcroForm = pdAcroForm;
	}
	public List<Table> getReturnTables() {
		return returnTables;
	}
	public void setReturnTables(List<Table> returnTables) {
		this.returnTables = returnTables;
	}
	

	
	
	

}
