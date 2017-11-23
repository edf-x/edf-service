package com.mk.eap.component.component.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.mk.eap.component.common.pdfbox.template.FillPdfTemplateUtil;
import com.mk.eap.component.common.pdfbox.template.TemplateTypeEnum;
import com.mk.eap.component.component.itf.IPrintService;
import com.mk.eap.component.pdfboxtable.pdf.PageablePdf;
import com.mk.eap.component.metadata.print.ExcelMetadataHelper;
import com.mk.eap.component.pdfboxtable.table.*;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 打印服务类实现
 * @author thinkpad
 *
 */
@Component
@Service
public class PrintService implements IPrintService {

	public static final Logger logger = LoggerFactory.getLogger(PrintService.class);

	private boolean isSrcCertificate = false;
	private TemplateTypeEnum templateType = TemplateTypeEnum.EMPTY;
	private TemplateHeadInfo templateHeadInfo;
	/**是否在同页上连续打印*/
	private boolean isSampPageContinuePrint = true;
	
	private boolean isNeedForwardAndOverPage = false;

	public boolean isSrcCertificate() {
		return isSrcCertificate;
	}

	public void setSrcCertificate(boolean isSrcCertificate) {
		this.isSrcCertificate = isSrcCertificate;
	}

	public TemplateHeadInfo getTemplateHeadInfo() {
		return templateHeadInfo;
	}

	public void setTemplateHeadInfo(TemplateHeadInfo templateHeadInfo) {
		this.templateHeadInfo = templateHeadInfo;
	}
	

	public boolean isSampPageContinuePrint() {
		return isSampPageContinuePrint;
	}

	public void setSampPageContinuePrint(boolean isSampPageContinuePrint) {
		this.isSampPageContinuePrint = isSampPageContinuePrint;
	}
	

	public boolean isNeedForwardAndOverPage() {
		return isNeedForwardAndOverPage;
	}

	public void setNeedForwardAndOverPage(boolean isNeedForwardAndOverPage) {
		this.isNeedForwardAndOverPage = isNeedForwardAndOverPage;
	}

	/**
	 * table 的构造方式
	 * @throws IOException 
	 */
	@Override
	public byte[] getPdfByteArray(Table table, boolean isOpen, String filename) throws IOException {
		PageablePdf pdf = pdfInit(table);
		return invokeToByte(table, pdf);
	}

	private byte[] invokeToByte(Table table, PageablePdf pdf) throws IOException {
		byte[] returnByteArray = null;
		try {
			pdf.drawTable(table);
		} catch (IOException e) {
			logger.error("打印数据异常！", e);
			// throw new BusinessException("500010", "打印数据异常");
			throw e;
		} finally {
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				pdf.closeDocument();// 先关闭，后保存
				pdf.save(byteArrayOutputStream);
				pdf.close();
				returnByteArray = byteArrayOutputStream.toByteArray();
			} catch (IOException e) {
				logger.error("打印数据异常！", e);
				// throw new BusinessException("500010", "打印数据异常");
				throw e;
			}
		}
		return returnByteArray;
	}

	private byte[] invokeToByte(List<Table> tables, PageablePdf pdf) throws IOException {

		for (Table table : tables) {
			buildHeadInfo(table.getTableHeadInfo(), pdf);
			pdf.drawTable(table);
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		pdf.closeDocument();// 先关闭，后保存
		pdf.save(byteArrayOutputStream);
		pdf.close();
		return byteArrayOutputStream.toByteArray();

	}

	private byte[] invokeToByte(PageablePdf pdf, List<Table> tables) throws IOException {
		int index = 0;
		float tempHeigth =0;
		int tableCount = tables.size();
		for (Table table : tables) {
			if(!isSrcCertificate){
				if(!isSampPageContinuePrint){
					pdf.changePageIfNeeded(index, table,pdf.getContentTopPadding());
				}else{				
//					float currentHeigth = pdf.isCreateNextPage(table);
//					if(currentHeigth <=0){
//						pdf.changePageIfNeeded(index, table,pdf.getContentTopPadding());
//					}else if(tempHeigth<0){
//						if(Math.abs(tempHeigth) >currentHeigth)  {
//							pdf.changePageIfNeeded(index, table,pdf.getContentTopPadding());
//						}
//					}
////					if(tempHeigth <=0 ||currentHeigth<=0){
////						pdf.changePageIfNeeded(index, table,pdf.getContentTopPadding());
////					}
//					tempHeigth =currentHeigth;	
					float sumHeight = pdf.getPageHeight(table)+ ((index+1 ==tableCount)?0:pdf.getPageHeight(tables.get(index+1)));
					if(sumHeight > (pdf.isLandscape()?pdf.getPageWidth():pdf.getPageHeight())){
						pdf.changePageIfNeeded(index, table,pdf.getContentTopPadding());
					}else if(index >=1 && pdf.getPageHeight(tables.get(index-1))> (pdf.isLandscape()?pdf.getPageWidth():pdf.getPageHeight())){
						pdf.changePageIfNeeded(index, table,pdf.getContentTopPadding());
					}else if(pdf.getCurrentPositionY()-pdf.getPageHeight(table)<pdf.getContentBottomPadding()){
						pdf.changePageIfNeeded(index, table,pdf.getContentTopPadding());
					}
				}
			}
			index++;
			boolean isFirst = false;
			if (index == 1) {
				isFirst = true;
			}

			// 标题 title
			if (!StringUtils.isEmpty(table.getHead())) {
				if (getTemplateType().equals(TemplateTypeEnum.EMPTY)) {
					pdf.drawHeading(table.getHead(), TextAlignment.CENTER, true);
				} else {
					pdf.drawHeadForCertificate(table.getHead(), TextAlignment.CENTER, isFirst, getTemplateType());
				}
			}
			// 表头 head
			if (table.getTableHeadInfos() != null) {
				for (TableHeadInfo headInfo : table.getTableHeadInfos()) {
					if(StringUtils.isNotEmpty(headInfo.getHeadInfo())){
						pdf.drawHeadingLine(headInfo.getHeadInfo(), headInfo.getAlignment());
					}else if(headInfo.getHeadInfos() !=null){
						for (TableHeadInfo info : headInfo.getHeadInfos()) {
							pdf.drawHeadingLine(info.getHeadInfo(), info.getAlignment());
						}
					}
				}
			}
			boolean isCertifacateThree = false;
			if (getTemplateType().equals(TemplateTypeEnum.A4B3)) {
				isCertifacateThree = true;
			}
			// 表体body
			pdf.drawTable(table, isSrcCertificate(), isCertifacateThree);
			// 表尾 footer
			if (table.getTableFooterInfo() != null) {
				int type = 1;
				if (getTemplateType().equals(TemplateTypeEnum.A4B2)) {
					type = 2;
				} else if (getTemplateType().equals(TemplateTypeEnum.A4B3)) {
					type = 3;
				}
				pdf.drawFooter(true, new Table(table.getTableFooterInfo().getFooterColumns(),
						table.getTableFooterInfo().getContent()), index, type);
			}
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		pdf.closeDocument();// 先关闭，后保存
		pdf.save(byteArrayOutputStream);
		pdf.close();
		return byteArrayOutputStream.toByteArray();

	}

	/**
	 * 初始化
	 * @param table
	 * @return
	 */
	private PageablePdf pdfInit(Table table) {
		// table.setDrawHeaders(true);
		table.setCellInsidePadding(3f);
		// table.setDrawGrid(true);

		PageablePdf pdf = new PageablePdf(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
		pdf.setLandscape(table.isLandscape());
		pdf.setContentLeftPadding(20f);
		pdf.setContentTopPadding(5f);
		pdf.setContentBottomPadding(40f); // This include the footer too
		pdf.setIncludePageNumber(true);
		pdf.setCurrentPositionAtStartOfThePage();
		return pdf;
	}

	private PageablePdf init(boolean isLandscape, float leftPadding, float topPadding, float bottomPadding,
			boolean isIncludePageNum, float... paramContent) {
		PageablePdf pdf = new PageablePdf(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
		pdf.setLandscape(isLandscape);
		pdf.setContentLeftPadding(leftPadding);
		pdf.setContentTopPadding(topPadding);
		pdf.setContentBottomPadding(bottomPadding); // This include the footer
												// too
		pdf.setSampPageContinuePrint(isSampPageContinuePrint());
		pdf.setNeedForwardAndOverPage(isNeedForwardAndOverPage());
		pdf.setIncludePageNumber(isIncludePageNum);
		pdf.setCurrentPositionAtStartOfThePage();
		if (paramContent.length > 0) {
			for (int i = 0; i < paramContent.length; i++) {
				if (i == 0) {
					pdf.setHeadingTopPadding(paramContent[i]);
				} else if (i == 1) {
					pdf.setRowHeight(paramContent[1]);
				}
			}
		}
		return pdf;
	}

	/**
	 * 适用于凭证的打印
	 * @throws PrinterException 
	 * @throws IOException 
	 */
	@Override
	public byte[] getPdfByteArray(List<List<Map<String, Object>>> srcObject, String srcFileName,
			TemplateTypeEnum templateType,Integer ... maxLineNum) throws PrinterException, IOException {

		FillPdfTemplateUtil util;
		try {
			util = new FillPdfTemplateUtil(srcObject, srcFileName, templateType,maxLineNum);
			// return util.getReturnBytyArray();
			setSrcCertificate(true);
			setTemplateType(templateType);
			return getPdfByteArray(util.getReturnTables());
		} catch (PrinterException e) {
			logger.error("打印数据异常！", e);
			// throw new BusinessException("500010", "打印数据异常");
			throw e;
		} catch (IOException e) {
			logger.error("打印数据异常！", e);
			// throw new BusinessException("500010", "打印数据异常");
			throw e;
		}
	}

	@Override
	public byte[] getPdfByteArray(TableHeadInfo tableHeadInfo, Table table, boolean isOpen, String filename)
			throws IOException {
		PageablePdf pdf = pdfInit(table);
		buildHeadInfo(tableHeadInfo, pdf);
		return invokeToByte(table, pdf);
	}

	private void buildHeadInfo(TableHeadInfo tableHeadInfo, PageablePdf pdf) throws IOException {
		if (tableHeadInfo != null) {
			try {
				pdf.drawHeading(tableHeadInfo.getHeadTitle(), TextAlignment.CENTER, true);
			} catch (IOException e) {
				logger.error("打印构造表头标题异常！", e);
				// throw new BusinessException("500011", "打印构造表头标题异常");
				throw e;
			}
			for (TableHeadInfo headInfo : tableHeadInfo.getHeadInfos()) {
				try {
					pdf.setHeadingFontSize(10f);
					pdf.drawHeadingLine(headInfo.getHeadInfo(), headInfo.getAlignment());
				} catch (IOException e) {
					logger.error("打印构造表头信息异常！", e);
					// throw new BusinessException("500012", "打印构造表头信息异常");
					throw e;
				}
			}
		}
	}

	@Override
	public byte[] getPdfByteArray(List<Table> tables) throws IOException {
		if (tables == null || tables.size() == 0)
			return null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		PageablePdf pdf = null;
		if (getTemplateType().equals(TemplateTypeEnum.A4B3)) {
			pdf = init(false, 40f, 0f, 3f, false, 4f, 0f);
		}else if(getTemplateType().equals(TemplateTypeEnum.A4B2)){
			pdf = init(false, 40f, 0f, 1f, false);
		}else {

			pdf = init(false, 20f, 4f, 40f, false);
		}
		byte[] byteArray = invokeToByte(pdf, tables);
		if (byteArray != null && byteArray.length > 0) {
			output.write(byteArray, 0, byteArray.length);
		}
		return output.toByteArray();

	}

	@Override
	public byte[] getPdfByteArray(List<Table> tables, String srcFileName) throws IOException {

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		if (tables == null || tables.size() == 0)
			return null;
		PageablePdf pdf = pdfInit(tables.get(0));
		byte[] byteArray = invokeToByte(tables, pdf);
		if (byteArray != null && byteArray.length > 0) {
			output.write(byteArray, 0, byteArray.length);
		}
		return output.toByteArray();

	}

	@Override
	public byte[] getPdfByteArray(TemplateHeadInfo templateHeadInfo, String[][] content, TemplateTypeEnum templateType)
			throws Exception {

		// FillPdfTemplateUtil util;
		// try {
		// util = new FillPdfTemplateUtil(templateType, templateHeadInfo,
		// content);
		// return util.getReturnBytyArray();
		// } catch (Exception e) {
		// logger.error("打印数据异常！", e);
		//
		//// throw new BusinessException("500010", "打印数据异常");
		// throw e;
		// }
		setTemplateHeadInfo(templateHeadInfo);
		return getPdfByteArray(null, content, templateType, null);

	}

	public TemplateTypeEnum getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateTypeEnum templateType) {
		this.templateType = templateType;
	}

	@Override
	public byte[] getPdfByteArray(TableHeadInfo templateHeadInfo, String[][] content, TemplateTypeEnum templateType,
			List<TableColumn> tableColumns) throws Exception {
		Table table = null;
		if (tableColumns != null && tableColumns.size() > 0) {
			table = new Table(tableColumns, content);
		} else if (templateType.equals(TemplateTypeEnum.AS2007)) {
			tableColumns = new ArrayList<>();
			tableColumns.add(new TableColumn(ASSET, 78f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 27f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(BEGINBALANCE, 83f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(ENDBALANCE, 83f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(LIABIANDCAPT, 90f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 27f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(BEGINBALANCE, 83f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(ENDBALANCE, 83f, TextAlignment.RIGHT));

			templateHeadInfo = createHeadInfo(templateType);

		} else if (templateType.equals(TemplateTypeEnum.AS2013)) {
			tableColumns = new ArrayList<>();
			tableColumns.add(new TableColumn(ASSET, 78f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 27f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(ENDBALANCE, 83f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(BEGINBALANCE, 83f, TextAlignment.RIGHT));

			tableColumns.add(new TableColumn(LIABIANDCAPT, 90f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 27f, TextAlignment.CENTER));

			tableColumns.add(new TableColumn(ENDBALANCE, 83f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(BEGINBALANCE, 83f, TextAlignment.RIGHT));

			templateHeadInfo = createHeadInfo(templateType);

		} else if (templateType.equals(TemplateTypeEnum.CF2007)) {
			tableColumns = new ArrayList<>();
			tableColumns.add(new TableColumn(PROJECT, 305f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 30f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(YEARTOENDPERIODAMOUNT, 110f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(LASTYEARTOENDPERIODAMOUNT, 110f, TextAlignment.RIGHT));

			templateHeadInfo = createHeadInfo(templateType);

		} else if (templateType.equals(TemplateTypeEnum.CF2013)) {
			tableColumns = new ArrayList<>();
			tableColumns.add(new TableColumn(PROJECT, 305f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 30f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(YEARTOTALAMOUNT, 110f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(CURRENTPERIODAMOUNT, 110f, TextAlignment.RIGHT));

			templateHeadInfo = createHeadInfo(templateType);

		} else if (templateType.equals(TemplateTypeEnum.IS2007GX)) {
			tableColumns = new ArrayList<>();
			tableColumns.add(new TableColumn(PROJECT, 305f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 30f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(CURRENTPERIODAMOUNT, 110f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(LASTPERIODAMOUNT, 110f, TextAlignment.RIGHT));

			templateHeadInfo = createHeadInfo(templateType);

		} else if (templateType.equals(TemplateTypeEnum.IS2007ZY)) {
			tableColumns = new ArrayList<>();
			tableColumns.add(new TableColumn(PROJECT, 168f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 27f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(CURRENTPERIODAMOUNT, 90f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(LASTPERIODAMOUNT, 90f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(YEARTOTALAMOUNT, 90f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(LASTYEARTOTALAMOUNT, 90f, TextAlignment.RIGHT));

			templateHeadInfo = createHeadInfo(templateType);

		} else if (templateType.equals(TemplateTypeEnum.IS2013GX)) {
			tableColumns = new ArrayList<>();
			tableColumns.add(new TableColumn(PROJECT, 305f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 30f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(CURRENTPERIODAMOUNT, 110f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(LASTPERIODAMOUNT, 110f, TextAlignment.RIGHT));

			templateHeadInfo = createHeadInfo(templateType);

		} else if (templateType.equals(TemplateTypeEnum.IS2013ZY)) {
			tableColumns = new ArrayList<>();
			tableColumns.add(new TableColumn(PROJECT, 168f, TextAlignment.LEFT));
			tableColumns.add(new TableColumn(LINENUM, 27f, TextAlignment.CENTER));
			tableColumns.add(new TableColumn(CURRENTPERIODAMOUNT, 90f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(LASTPERIODAMOUNT, 90f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(YEARTOTALAMOUNT, 90f, TextAlignment.RIGHT));
			tableColumns.add(new TableColumn(LASTYEARTOTALAMOUNT, 90f, TextAlignment.RIGHT));

			templateHeadInfo = createHeadInfo(templateType);

		}
		table = new Table(tableColumns, content);
		return getPdfByteArray(templateHeadInfo, table, true, "");
	}

	private TableHeadInfo createHeadInfo(TemplateTypeEnum templateTypeEnum) {
		TableHeadInfo templateHeadInfo;
		List<TableHeadInfo> headInfos = new ArrayList<>();
		String category = "";
		String title = "";
		if (templateTypeEnum.equals(TemplateTypeEnum.AS2007)) {
			category = CATEGORY0701;
			title = BALNACESHEET;
		} else if (templateTypeEnum.equals(TemplateTypeEnum.AS2013)) {
			category = CATEGORY1301;
			title = BALNACESHEET;
		} else if (templateTypeEnum.equals(TemplateTypeEnum.CF2007)) {
			category = CATEGORY0703;
			title = CASHFLOWSHEET;
		} else if (templateTypeEnum.equals(TemplateTypeEnum.CF2013)) {
			category = CATEGORY1303;
			title = CASHFLOWSHEET;
		} else if (templateTypeEnum.equals(TemplateTypeEnum.IS2007GX)
				|| templateTypeEnum.equals(TemplateTypeEnum.IS2007ZY)) {
			category = CATEGORY0702;
			title = INCOMESTATEMENTS;
		} else if (templateTypeEnum.equals(TemplateTypeEnum.IS2013GX)
				|| templateTypeEnum.equals(TemplateTypeEnum.IS2013ZY)) {
			category = CATEGORY1302;
			title = INCOMESTATEMENTS;
		}
		TableHeadInfo headInfo = new TableHeadInfo(TextAlignment.RIGHT, category);
		headInfos.add(headInfo);
		headInfo = new TableHeadInfo(TextAlignment.LEFT, CREATEUNIT + getTemplateHeadInfo().getOrgName());
		headInfos.add(headInfo);
		headInfo = new TableHeadInfo(TextAlignment.CENTER, getTemplateHeadInfo().getDate());
		headInfos.add(headInfo);
		headInfo = new TableHeadInfo(TextAlignment.RIGHT, UNIT);
		headInfos.add(headInfo);
		templateHeadInfo = new TableHeadInfo(title, headInfos);
		return templateHeadInfo;
	}

	@Override
	public byte[] getPdfByteArray(List<Table> tables, float contentTopPadding, float contentLeftPadding,
			float contentBottomPadding, float headingTopPadding, float rowHeight, boolean isLandscape,
			boolean includePageNumber,float ...headFontSize) throws IOException {
		if (tables == null || tables.size() == 0)
			return null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PageablePdf pdf = init(isLandscape, contentLeftPadding, contentTopPadding, contentBottomPadding,
				includePageNumber, headingTopPadding, rowHeight);
		if(headFontSize.length>0){
			pdf.setHeadingFontSize(headFontSize[0]);
		}
		setTemplateType(TemplateTypeEnum.EMPTY);
		byte[] byteArray = invokeToByte(pdf, tables);
		if (byteArray != null && byteArray.length > 0) {
			output.write(byteArray, 0, byteArray.length);
		}
		return output.toByteArray();
	}

	@Override
	public byte[] getPrintFromMetadata(Map<String, String> headMap, List<?> dataList, String templateName,
			int sheetIndex, List<String> unFixCols,boolean ... param) throws PrinterException, IOException {
		try {
			Map<TableHeadInfo, Table> map = ExcelMetadataHelper.createInstance().printFromMetadata(headMap, dataList,
					templateName, sheetIndex, unFixCols,param);
			setTemplateType(TemplateTypeEnum.EMPTY);
			for (Entry<TableHeadInfo, Table> key2Value : map.entrySet()) {
				return getPdfByteArray(key2Value.getKey(), key2Value.getValue(), true, "");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new byte[1];
	}

	@Override
	public byte[] getPrintFromMetaData(Map<String, String> formMap, String templateName)
			throws PrinterException, IOException {
		/// 判断参数是否有效
		if (formMap.isEmpty() || null == templateName || templateName.trim().length() == 0) {
			// 参数无效－返回NULL
			return null;
		}
		setTemplateType(TemplateTypeEnum.EMPTY);
		PdfReader reader;
		ByteArrayOutputStream bos;
		PdfStamper stamper = null;
		reader = new PdfReader(templateName);// 读取pdf模板
		int pagecount = reader.getNumberOfPages();
		bos = new ByteArrayOutputStream();
		try {
			stamper = new PdfStamper(reader, bos);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error("打印数据异常！", e);
		}
		AcroFields form = stamper.getAcroFields();
		// 使用中文字体
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error("打印数据异常！", e);
		}
		ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
		fontList.add(bf);
		// 设置字体
		form.setSubstitutionFonts(fontList);
		// 取得PDF模板页面字段
		Map<String, Item> fieldNameMap = form.getFields();
		// 遍历数据并插入到模板
		for (Entry<String, String> entry : formMap.entrySet()) {
			// 判断字段在模板中是否存在
			if (fieldNameMap.containsKey(entry.getKey())) {
				try {
					form.setField(entry.getKey(), entry.getValue());
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					logger.error("打印数据异常！", e);
				}
			}
		}
		// 如果为false那么生成的PDF文件还能编辑，一定要设为true
		stamper.setFormFlattening(true);
		try {
			stamper.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error("打印数据异常！", e);
		}

		// 定义文档对象
		Document doc = new Document();
		PdfCopy copy = null;
		try {
			copy = new PdfCopy(doc, bos);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error("打印数据异常！", e);
		}
		doc.open();
		PdfImportedPage importPage;
		for (int page = 1; page <= pagecount; page++) {
			importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), page);
			try {
				copy.addPage(importPage);
			} catch (BadPdfFormatException e) {
				// TODO Auto-generated catch block
				logger.error("打印数据异常！", e);
			}
		}
		doc.close();
		// 返回字节流
		return bos.toByteArray();
	}

	@Override
	public byte[] getPrintFromMetadata(List<Map<String, String>> headMaps, List<?> dataLists, String templateName,
			int sheetIndex,boolean isSampPageContinuePrint,boolean isNeedForwardAndOverPage, List<String> unFixCols, boolean... param) throws PrinterException, IOException {
		try {
			List<Table> tables= ExcelMetadataHelper.createInstance().printFromMetadata(headMaps, dataLists,
					templateName, sheetIndex, unFixCols,isSampPageContinuePrint,param);
			setSampPageContinuePrint(isSampPageContinuePrint);
			setNeedForwardAndOverPage(isNeedForwardAndOverPage);
			setSrcCertificate(false);
			setTemplateType(TemplateTypeEnum.EMPTY);
			byte[] byteArray = getPdfByteArray(tables, 5f, 20f, 40f, 2, 0, param.length>0?param[0]:true, true,12f);
			return byteArray;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new byte[1];
	}

}
