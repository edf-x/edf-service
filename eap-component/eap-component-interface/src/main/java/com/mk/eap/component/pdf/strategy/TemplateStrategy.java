package com.mk.eap.component.pdf.strategy;

import com.mk.eap.component.common.pdfbox.template.TemplateTypeEnum;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Map;


/**
 * 模板策略
 * @author thinkpad
 *
 */
public interface TemplateStrategy {
	
	
	public final static String KEY_SPLIT="##";
	
	public final static String ACOUNT="科目";
	
	public final static String SUMMARY="摘要";
	
	public final static String DEBIT="借方";
	
	public final static String CREDITOR="贷方";
	
	public final static String RECORDINGVOUCHER="记账凭证";
	
	public final static String ORGNAME="核算单位:";
	
	public final static String DATE="日期:";
	
	public final static String CERTIFICATENO="凭证号:";
	
	public final static String ATTACHMENTNUM ="附件数:";
	
	public final static String CREATER="制单:";
	
	public final static String AUDITING="审核:";
	
	public final static String SUM="合计:";
	
	
	public final static String UNIT="单位:元";
	
	public final static String CREATUNIT="编制单位:";
	
	public final static String  COLON=":";
	
	public final static String GLTITLE="总账";
	
	public final static String SGLTITLE="辅助总账";
	
	public final static String DATITLE="明细账";
	
	public final static String SLTITLE="辅助明细账";
	
	public final static String BSTITLE = "余额表";
	
	public final static String ASBSTITLE="科目辅助余额表";
	
	public final static String SABSTITLE="辅助科目余额表";
	
	
	public final static String SAVEFILE="temp";
	
	public final static String TEMPLATEPATH="/template/fi";
	
	public final static String FIXCODE1="00001000";
	
	public final static String FIXCODE2="00";
	
	
	public String getPdfPath();
	
	public byte[] getByteArray();
	
	
	public void operate(Map<String, Object[][]> dataMap, String srcFileName, TemplateTypeEnum templateType, Integer... maxLineNum) throws IOException, PrinterException;
	
	public void batchOperate(Map<String, Object[][]> dataMap, String srcFileName, TemplateTypeEnum templateType, Integer... maxLineNum) throws IOException, PrinterException;
	

}


