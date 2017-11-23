package com.mk.eap.component.pdf.adapter;

import java.util.LinkedHashMap;

/**
 * 适配中的目标角色
 * @author thinkpad
 *
 */
public interface Target {
	
	public final static String KEY_VALUE =":";
	
	public final static String KEY_SPLIT="##";
	
	//***************联合主键*****************
	public final static String PRIMARY_ORGID="orgId";
	
	public final static String PRIMARY_DOCID="docId";
	//******************end**************
	
	
	public final static String DOCTYPE="docType";
	
	public final static String CODE="code";
	
	public final static String VOUCHERDATE = "voucherDate";
	
	public final static String DOCNUM="attachedVoucherNum";
	
	public final static String UNIT = "orgName";
	
	public final static String MARKER="maker";//制单人
	
	public final static String SUMMARY="summary";//摘要
	
	public final static String ACCOUNT="accountName";//科目
	
	public final static String AMOUNTDR= "amountDr";//借方本币
	
	public final static String AMOUNTCR= "amountCr";//贷方本币
	
	public final static String  AUDITOR= "auditor";//审核人
	
	public LinkedHashMap<String, Object[][]> dataTransfer();

}
