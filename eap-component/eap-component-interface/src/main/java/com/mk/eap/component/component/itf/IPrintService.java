package com.mk.eap.component.component.itf;


import com.mk.eap.component.common.pdfbox.template.TemplateTypeEnum;
import com.mk.eap.component.pdfboxtable.table.TableColumn;
import com.mk.eap.component.pdfboxtable.table.TableHeadInfo;
import com.mk.eap.component.pdfboxtable.table.Table;
import com.mk.eap.component.pdfboxtable.table.TemplateHeadInfo;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 打印服务接口
 * @author thinkpad
 *
 */
public interface IPrintService {
	/**全局常量**/
	public final static String ASSET="资产";
	public final static String LINENUM="行次";
	public final static String ENDBALANCE="期末余额";
	public final static String BEGINBALANCE="年初余额";
	public final static String LIABIANDCAPT="负债及所有者权益";
	public final static String BALNACESHEET="资产负债表";
	public final static String PROJECT="项目";
	public final static String CURRENTPERIODAMOUNT="本期金额";
	public final static String LASTPERIODAMOUNT="上年同期金额";
	public final static String YEARTOTALAMOUNT="本年累计金额";
	public final static String LASTYEARTOTALAMOUNT="上年累计金额";
	public final static String YEARTOENDPERIODAMOUNT = "年初至报告期期末金额";
	public final static String LASTYEARTOENDPERIODAMOUNT = "上年初至报告期期末金额";
	public final static String CASHFLOWSHEET="现金流量表";
	public final static String INCOMESTATEMENTS="利润表";
	public final static String CREATEUNIT="编制单位:";
	public final static String UNIT="单位：元";
	public final static String CATEGORY0701="会企01表";
	public final static String CATEGORY1301="会小企01表";
	public final static String CATEGORY0702="会企02表";
	public final static String CATEGORY1302="会小企02表";
	public final static String CATEGORY0703="会企03表";
	public final static String CATEGORY1303="会小企03表";
	
	
	public byte[] getPdfByteArray(TemplateHeadInfo templateHeadInfo, String[][] content, TemplateTypeEnum templateType) throws Exception;
	
	
	public byte[] getPdfByteArray(TableHeadInfo templateHeadInfo, String[][] content, TemplateTypeEnum templateType, List<TableColumn> tableColumns) throws Exception;
	
	/**
	 * 适用于集合的调用，带有表头,只适用于一个表头（只打出tables【0】的表头，其他table只打印内容）
	 * @param tables 内容集合
	 * @param srcFileName 名字 暂时无效
	 * @return
	 * @throws IOException 
	 */
	public byte[] getPdfByteArray(List<Table> tables, String srcFileName) throws IOException;
	
	/**
	 *  适用于集合，每个Table可以包含一个表头和一个表尾,每个table中的数据都会打印，适用于精细控制，如果没有特殊控制建议调用{@link--getPdfByteArray(List<Table> tables),根据默认规则处理
	 * @param tables :Table集合（主要包含：List<TableColumn>和Object[][] 内容等等）
	 * @param contentTopPadding ：内容上边距
	 * @param contentLeftPadding：内容左边距
	 * @param contentBottomPadding：内容下边距
	 * @param headingTopPadding：表头内容上边距
	 * @param rowHeight：表体每行的高度：传入0，用默认高度（注意****）
	 * @param isLandscape：是否横向打印
	 * @param includePageNumber：是否包含页码
	 * @param headFontSize 可变参数，可以控制title大小
	 * @return
	 * @throws IOException
	 */
	public byte[] getPdfByteArray(List<Table> tables, float contentTopPadding, float contentLeftPadding, float contentBottomPadding, float headingTopPadding, float rowHeight, boolean isLandscape, boolean includePageNumber, float... headFontSize)throws IOException;
	/**
	 * 适用于集合，每个Table可以包含一个表头和一个表尾,每个table中的数据都会打印
	 * @param tables
	 * @return
	 * @throws IOException
	 */
	public byte[] getPdfByteArray(List<Table> tables) throws IOException;
			
	/**
	 * 适用于一单的调用，带有表头
	 * @param tableHeadInfo
	 * @param table
	 * @param isOpen
	 * @param filename
	 * @return
	 * @throws IOException 
	 * @throws BusinessException
	 */
	public byte[] getPdfByteArray(TableHeadInfo tableHeadInfo, Table table, boolean isOpen, String filename) throws IOException ;
	
	/**
	 * 适用于一单的调用，不带表头
	 * @param table
	 * @param isOpen
	 * @param filename
	 * @return
	 * @throws IOException 
	 */
	public byte[] getPdfByteArray(Table table, boolean isOpen, String filename) throws IOException;
	
	/**
	 * 凭证打印接口
	 * @param srcObject:数据信息
	 * @param srcFileName 名称，不用传，暂时没用
	 * @param templateType：两版还是三版有对应的枚举类型
	 * @param maxLineNum 两版时每版最大行数（暂时支持5、6、7），可配置，不传入的话默认5行
	 * @return
	 * @throws PrinterException
	 * @throws IOException
	 */
	public byte[] getPdfByteArray(List<List<Map<String, Object>>> srcObject, String srcFileName, TemplateTypeEnum templateType, Integer... maxLineNum) throws PrinterException, IOException;
	/**
	 * 通过导入excel模板元数据打印，模板设置见/template/fi/balanceSumAuxRpt.xls
	 * @param headMap 要替换的map类型 替换excle中{orgName}类型
	 * @param dataList 要填充的list数据
	 * @param templateName 全名，包含路径及其文件扩展名.xls(例如/template/fi/balanceSumAuxRpt.xls),请按不同的领域创建例如/template/xx/xxx.xls
	 * @param sheetIndex 那个sheet，即索引值从0开始
	 * @param unFixCols 动态显示的集合,如果模板中没有动态显示直接赋值为null
	 * @param 可变参数，现在只支持是否横向打印,如果正常打印请值为false,如果横向打印true ,或者不传此参数
	 * @return byte[]
	 * @throws PrinterException
	 * @throws IOException
	 */
	public byte[] getPrintFromMetadata(Map<String, String> headMap, List<?> dataList, String templateName, int sheetIndex, List<String> unFixCols, boolean... param) throws PrinterException, IOException;
	
	/***
	 * 通过导入excel模板元数据打印，模板设置见/template/fi/balanceSumAuxRpt.xls
	 * @param headMaps  要替换的map类型 替换excle中{}类型，List类型，所以一个headMap 对应第二个参数中的list中的值：即：headMaps[i]对应一个dataLists[i]
	 * @param dataLists headMaps对应的数据集合
	 * @param templateName 全名，包含路径及其文件扩展名.xls(例如/template/fi/balanceSumAuxRpt.xls),请按不同的领域创建例如/template/xx/xxx.xls
	 * @param sheetIndex 那个sheet，即索引值从0开始
	 * @param isSampPageContinuePrint 是否在一页上打印多个科目
	 * @param isNeedForwardAndOverPage 是否需要承前过次页，现在只适用于总账、明细账
	 * @param unFixCols 动态显示的集合,如果模板中没有动态显示直接赋值为null
	 * @param 可变参数，现在只支持是否横向打印,如果正常打印请值为false,如果横向打印true ,或者不传此参数
	 * @return byte[]
	 * @throws PrinterException
	 * @throws IOException
	 */
	public byte[] getPrintFromMetadata(List<Map<String, String>> headMaps, List<?> dataLists, String templateName, int sheetIndex, boolean isSampPageContinuePrint, boolean isNeedForwardAndOverPage, List<String> unFixCols, boolean... param)throws PrinterException, IOException;
	
	/**
	 * 通过pdf模板填充对应的数据,针对一些特殊模板例如：增值税这种类型模板，如果使用excle作为模板更加复杂化。
	 * @param formMap 要填充的map类型
	 * @param templateName 全名，包含路径及其文件扩展名.pdf(例如/template/fi/balanceSumAuxRpt.pdf),请按不同的领域创建例如/template/xx/xxx.pdf
	 * @return
	 * @throws PrinterException
	 * @throws IOException
	 */
	public byte[] getPrintFromMetaData(Map<String, String> formMap, String templateName)throws PrinterException, IOException;


}
