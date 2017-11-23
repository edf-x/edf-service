package com.mk.eap.component.pdf.strategy;

import com.mk.eap.component.pdf.convert.NumberToChineseBig;
import com.mk.eap.component.pdfboxtable.table.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 主要实现对传入的数据进行拆分，组成每个凭证table的数据
 * @author thinkpad
 *
 */
public class CertificatePubHelper {
	public  static  Integer TABLE_ROW = 5;
	
	public static   Integer TABLE_ALLROW =6;
	
	public final static String ACOUNT="科目";
	
	public final static String SUMMARY="摘要";
	
	public final static String DEBIT="借方";
	
	public final static String CREDITOR="贷方";
	
	public final static String RECORDINGVOUCHER="记账凭证";
	
	public final static String ORGNAME="核算单位:";
	
	public final static String DATE="日期:";
	
	public final static String CERTIFICATENO="凭证号:";
	
	public final static String ATTACHMENTNUM ="附件数:";
	
	public final static String CREATER="制单人:";
	
	public final static String AUDITING="审核人:";
	
	public final static String SUM="合计:";
	
	public static List<Table> buildTables(Map<String, Object[][]> dataMap,Integer ... maxLineNum){
		List<Table> tables = new ArrayList<>();
		List<TableColumn> tableColumns = new ArrayList<>();
		tableColumns.add(new TableColumn(SUMMARY, 140f, TextAlignment.LEFT));
		tableColumns.add(new TableColumn(ACOUNT, 195f, TextAlignment.LEFT));
		tableColumns.add(new TableColumn(DEBIT, 90f, TextAlignment.RIGHT));
		tableColumns.add(new TableColumn(CREDITOR, 90f, TextAlignment.RIGHT));
		if(maxLineNum.length>0){
			TABLE_ROW = maxLineNum[0];
			TABLE_ALLROW =TABLE_ROW+1; 
		}else{
			TABLE_ROW = 5;
			TABLE_ALLROW =6;
		}

		
		for (Entry<String, Object[][]> key2Value : dataMap.entrySet()) {
			double perTableSumDr = 0, perTableSumCr = 0;// 初始化借方和贷方的合计
			Object[][] content = key2Value.getValue();
			NumberFormat nFormat = NumberFormat.getNumberInstance();
	        nFormat.setRoundingMode(RoundingMode.HALF_UP);
	        nFormat.setMaximumFractionDigits(2);
	        nFormat.setMinimumFractionDigits(2);
			int length = content.length;
			if (length < TABLE_ALLROW) {
				Object[][] perTableContent = new Object[TABLE_ALLROW][];
				
				for (Object[] columns : content) {

					perTableSumDr += Double.valueOf(columns[2] == null ? "0.00" : columns[2].toString()).doubleValue();
					perTableSumCr += Double.valueOf(columns[3] == null ? "0.00" : columns[3].toString()).doubleValue();
					if (columns[2] != null) {
						columns[2] = nFormat.format( new BigDecimal(columns[2].toString()));

					}
					if (columns[3] != null) {
						columns[3] =nFormat.format( new BigDecimal(columns[3].toString()));
					}
				}
				System.arraycopy(content, 0, perTableContent, 0, length);
				perTableContent[TABLE_ROW] = new Object[content[0].length];
				System.arraycopy(content[0], 0, perTableContent[TABLE_ROW], 0, content[0].length);//合计行处理
				perTableContent[TABLE_ROW][0]= SUM+NumberToChineseBig.number2CNMontrayUnit(new BigDecimal(perTableSumDr));
				perTableContent[TABLE_ROW][1] = "";
				perTableContent[TABLE_ROW][2] =nFormat.format( new BigDecimal(perTableSumDr));
				perTableContent[TABLE_ROW][3] = nFormat.format(new BigDecimal(perTableSumCr));	
				
				Table table = createTable(tableColumns, content, perTableContent,maxLineNum.length>0?maxLineNum[0]:null);
				tables.add(table);
			} else {
				int tableCount = (int) Math.ceil((double) length / TABLE_ROW);
				Object[][] perTableContent = null;
				for (int i = 0; i < tableCount; i++) {
					if (i + 1 == tableCount) {
						perTableContent = new Object[TABLE_ALLROW][];
	
						for (int j = (i * TABLE_ROW); j < (i * TABLE_ROW+length - (tableCount - 1) * TABLE_ROW); j++) {

							perTableSumDr += Double.valueOf(content[j][2] == null ? "0.00" : content[j][2].toString()).doubleValue();
							perTableSumCr += Double.valueOf(content[j][3] == null ? "0.00" : content[j][3].toString()).doubleValue();	
							if (content[j][2]!= null) {
								content[j][2] =nFormat.format( new BigDecimal(content[j][2].toString()));
							}
							if (content[j][3] != null) {
								content[j][3] =nFormat.format( new BigDecimal(content[j][3].toString()));
							}
						}
						System.arraycopy(content, i * TABLE_ROW, perTableContent, 0,
								length - (tableCount - 1) * TABLE_ROW);
						perTableContent[TABLE_ROW] = new Object[content[0].length];
						System.arraycopy(content[0], 0, perTableContent[TABLE_ROW], 0, content[0].length);//合计行处理
						perTableContent[TABLE_ROW][0]= SUM+NumberToChineseBig.number2CNMontrayUnit(new BigDecimal(perTableSumDr));
						perTableContent[TABLE_ROW][1] = "";
						perTableContent[TABLE_ROW][2] = nFormat.format(new BigDecimal(perTableSumDr));
						perTableContent[TABLE_ROW][3] =nFormat.format( new BigDecimal(perTableSumCr));						
						Table table = createTable(tableColumns, content, perTableContent,maxLineNum.length>0?maxLineNum[0]:null, (i + 1)+"/"+tableCount);
						tables.add(table);
					} else {
						perTableContent = new Object[TABLE_ALLROW][];
						
						for (int j = (i * TABLE_ROW); j < (i+1)*TABLE_ROW; j++) {

							perTableSumDr += Double.valueOf(content[j][2] == null ? "0.00" : content[j][2].toString()).doubleValue();
							perTableSumCr += Double.valueOf(content[j][3] == null ? "0.00" : content[j][3].toString()).doubleValue();	
							if (content[j][2]!= null) {
								content[j][2] = nFormat.format(new BigDecimal(content[j][2].toString()));
							}
							if (content[j][3] != null) {
								content[j][3] =nFormat.format( new BigDecimal(content[j][3].toString()));
							}
						}
						System.arraycopy(content, i * TABLE_ROW, perTableContent, 0, TABLE_ROW);
						perTableContent[TABLE_ROW] = new Object[content[0].length];
						System.arraycopy(content[0], 0, perTableContent[TABLE_ROW], 0, content[0].length);//合计行处理
						perTableContent[TABLE_ROW][0]= SUM;
						perTableContent[TABLE_ROW][1] = "";
						perTableContent[TABLE_ROW][2] =nFormat.format( new BigDecimal(perTableSumDr));
						perTableContent[TABLE_ROW][3] =nFormat.format( new BigDecimal(perTableSumCr));	
						
						Table table = createTable(tableColumns, content, perTableContent,maxLineNum.length>0?maxLineNum[0]:null,(i + 1)+"/"+tableCount);
						tables.add(table);
					}
				}

			}

		}
		return tables;
	}

	private static Table createTable(List<TableColumn> tableColumns, Object[][] content, Object[][] perTableContent,Integer maxLineNum,String ... percent) {

		//表头构建
		StringBuilder str = new StringBuilder();
		List<TableHeadInfo> tableHeadInfos = new ArrayList<>();
		tableHeadInfos.add(new TableHeadInfo(TextAlignment.LEFT, ""));
		tableHeadInfos.add(new TableHeadInfo(TextAlignment.CENTER, ""));
		str.append(ATTACHMENTNUM);
		if(content[0][7] != null && content[0][7].toString()!="0")
		str.append(content[0][7].toString());
		tableHeadInfos.add(new TableHeadInfo(TextAlignment.RIGHT,str.toString()));
		str = new StringBuilder();
		str.append(ORGNAME);
		str.append(content[0][8].toString());
		tableHeadInfos.add(new TableHeadInfo(TextAlignment.LEFT,str.toString()));
		str = new StringBuilder();
		str.append(DATE);
		str.append(content[0][6].toString());
		tableHeadInfos.add(new TableHeadInfo(TextAlignment.CENTER,str.toString()));
		str = new StringBuilder();
		str.append(CERTIFICATENO);
		str.append(content[0][4].toString());
		str.append("-");
		str.append(content[0][5].toString());
		if(percent.length >0){
			str.append("-");
			str.append(percent[0]);
		}
		tableHeadInfos.add(new TableHeadInfo(TextAlignment.RIGHT, str.toString()));
		
		//表尾构建
		List<TableColumn> footerColumns = new ArrayList<>();
		footerColumns.add(new TableColumn("", 277f,TextAlignment.LEFT, TextVerticalAlignment.TOP));
		footerColumns.add(new TableColumn("", 277f,TextAlignment.LEFT,TextVerticalAlignment.TOP));
		Object[][] footerContent = new Object[1][2];
		for (Object[] Columns : footerContent) {	
			Columns[0] = AUDITING+(content[0][10]==null?"":content[0][10]);
			Columns[1] = CREATER+(content[0][9]==null?"":content[0][9]);	
		}
		TableFooterInfo tableFooterInfo = new TableFooterInfo(true,footerColumns, footerContent);
		//构造Table数据
		Table table = new Table(tableColumns, perTableContent,RECORDINGVOUCHER,tableHeadInfos, tableFooterInfo,maxLineNum);
		return table;
	}
	
	public static void buildTable(Map<String, Object[][]> dataMap){
		int rowCount = 0;// 需要的行数
		//for循环得到多少行数据
		for (Entry<String, Object[][]> key2Value : dataMap.entrySet()) {
			Object[][] content = key2Value.getValue();
			int length = content.length;
			int tableCount = (int) Math.ceil((double) length / TABLE_ROW);
			rowCount += tableCount * TABLE_ALLROW;
		}
		Object[][] allDocContent = new Object[rowCount][];// 所以单据行集合（包含table中空的数据）
		int insertIndex = 0;
		for (Entry<String, Object[][]> key2Value : dataMap.entrySet()) {
			double perTableSumDr = 0, perTableSumCr = 0;// 初始化借方和贷方的合计
			Object[][] content = key2Value.getValue();
			int length = content.length;
			if (length < TABLE_ALLROW) {
				System.arraycopy(content, 0, allDocContent, insertIndex, length);
				insertIndex += TABLE_ALLROW;
				for (Object[] columns : content) {
					perTableSumDr += Double.valueOf(columns[2] == null ? "0.00" : columns[2].toString()).doubleValue();
					perTableSumCr += Double.valueOf(columns[3] == null ? "0.00" : columns[3].toString()).doubleValue();
				}
				allDocContent[insertIndex-1] = new Object[content[0].length];
				System.arraycopy(content[0], 0, allDocContent[insertIndex-1], 0, content[0].length);//合计行处理
				allDocContent[insertIndex-1][0]= SUM+NumberToChineseBig.number2CNMontrayUnit(new BigDecimal(perTableSumDr));
				allDocContent[insertIndex-1][1] = null;
				allDocContent[insertIndex-1][2] = perTableSumDr;
				allDocContent[insertIndex-1][3] = perTableSumCr;
			} else {
				int tableCount = (int) Math.ceil((double) length / TABLE_ROW);
				for (int i = 0; i < tableCount; i++) {
					if (i + 1 == tableCount) {
						System.arraycopy(content, i * TABLE_ROW, allDocContent, insertIndex,
								length - (tableCount - 1) * TABLE_ROW);
						for (int j = (i * TABLE_ROW); j < (i * TABLE_ROW+length - (tableCount - 1) * TABLE_ROW); j++) {
							perTableSumDr += Double.valueOf(content[j][2] == null ? "0.00" : content[j][2].toString()).doubleValue();
							perTableSumCr += Double.valueOf(content[j][3] == null ? "0.00" : content[j][3].toString()).doubleValue();
							
						}
						insertIndex += TABLE_ALLROW;
						allDocContent[insertIndex-1] = new Object[content[0].length];
						System.arraycopy(content[0], 0, allDocContent[insertIndex-1], 0, content[0].length);//合计行处理
						allDocContent[insertIndex-1][0]= SUM+NumberToChineseBig.number2CNMontrayUnit(new BigDecimal(perTableSumDr));
						allDocContent[insertIndex-1][1] = null;
						allDocContent[insertIndex-1][2] = perTableSumDr;
						allDocContent[insertIndex-1][3] = perTableSumCr;
					} else {
						System.arraycopy(content, i * TABLE_ROW, allDocContent, insertIndex, TABLE_ROW);
						for (int j = (i * TABLE_ROW); j < (i+1)*TABLE_ROW; j++) {
							perTableSumDr += Double.valueOf(content[j][2] == null ? "0.00" : content[j][2].toString()).doubleValue();
							perTableSumCr += Double.valueOf(content[j][3] == null ? "0.00" : content[j][3].toString()).doubleValue();	
						}
						insertIndex += TABLE_ALLROW;
						allDocContent[insertIndex-1] = new Object[content[0].length];
						System.arraycopy(content[0], 0, allDocContent[insertIndex-1], 0, content[0].length);//合计行处理
						allDocContent[insertIndex-1][0]= SUM;
						allDocContent[insertIndex-1][1] = null;
						allDocContent[insertIndex-1][2] = perTableSumDr;
						allDocContent[insertIndex-1][3] = perTableSumCr;
					}


				}

			}

		}
		
	}

}
